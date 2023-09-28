using System;
using System.Collections;
using System.Collections.Generic;
using Pathfinding;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.SceneManagement;
using Pathfinding;

public class Enemy1Script : MonoBehaviour
{

    public float maxHealth = 100;
    public float currentHealth;
    public float movementSpeed = 2f;
    [SerializeField]
    private float rotationSpeed;

    public Rigidbody2D rb;
    private Vector2 targetDirection;
    public float damage;
    private float timer;
    public float damagerate;
    //public GameObject gameController;
    private GameController playerScoreManager;
    private bool pointAdded = false;
    public HealthBar healthBar;
    public bool canHit = true;


    void Start()
    {
       // healthBar = transform.Find("Canvas").Find("Health Bar").gameObject.GetComponent<HealthBar>(); //trebuie schimbat cum se gaseste health barul
        playerScoreManager = GameObject.FindGameObjectWithTag("GameController").GetComponent<GameController>();
        currentHealth = maxHealth;
        healthBar.SetMaxHealth(maxHealth);
        // healthBar.SetMaxHealth(maxHealth);
    }

    void Update()
    {
        healthBar.SetHealth(currentHealth);
        if (currentHealth <= 0)
        {
            
            movementSpeed = 0.0f;
           // Destroy(gameObject);
            Destroy(gameObject.transform.parent.gameObject);
            if (pointAdded == false)
            {
                playerScoreManager?.AddPoint();
                pointAdded = true;
            }
        }

        timer += Time.deltaTime;
    }

    void FixedUpdate()
    {
        //Vector2 enemyToPlayerVector = player.transform.position - transform.position;
        //directionToPlayer = enemyToPlayerVector.normalized;
        //targetDirection = (player.transform.position - transform.position).normalized;
        //RotateTowardsTarget();
        //SetVelocity();

    }

    //void OnCollisionEnter2D(Collision2D collision)
    //{
    //    if (collision.gameObject.CompareTag("Player") && timer>damagerate)
    //    {
    //        collision.gameObject.GetComponent<PlayerController>().TakeDamage(damage);
    //        timer = 0;
    //    }

    //}

    void OnCollisionStay2D(Collision2D collision)
    {
        //if (collision.gameObject.CompareTag("Player") && timer > damagerate)
        if (collision.gameObject.CompareTag("Player") && canHit)
        {
            collision.gameObject.GetComponent<PlayerController>().TakeDamage(damage);
            canHit = false;
            StartCoroutine(WaitBetweenHits(damagerate));
            //timer = 0;
        }

    }

    public void TakeDamage(float damage)
    {
        currentHealth -= damage;
        healthBar.SetHealth(currentHealth);
    }

    private void RotateTowardsTarget()
    {
        if (targetDirection == Vector2.zero)
        {
            return;
        }

        Quaternion targetRotation = Quaternion.LookRotation(transform.forward, targetDirection);
        Quaternion rotation =
            Quaternion.RotateTowards(transform.rotation, targetRotation, rotationSpeed * Time.fixedDeltaTime);
        rb.SetRotation(rotation);

    }

    private void SetVelocity()
    {
        if (targetDirection == Vector2.zero)
        {
            rb.velocity = Vector2.zero;
        }
        else
        {
            rb.velocity = transform.up * movementSpeed;
        }
    }

    IEnumerator WaitBetweenHits(float time)
    {
        yield return new WaitForSeconds(time);
        canHit = true;
    }
}
