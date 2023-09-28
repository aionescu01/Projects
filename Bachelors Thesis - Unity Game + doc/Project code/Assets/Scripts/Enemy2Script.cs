using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Pathfinding;

public class Enemy2Script : MonoBehaviour
{
    public float maxHealth = 100;
    public float currentHealth;
    public float accuracy;
    [SerializeField]
    public float movementSpeed = 2f;
    [SerializeField] 
    private float rotationSpeed;

    private PlayerAwarenessScript playerAwarenessController;
    //private Vector2 directionToPlayer;
    public Weapon weapon;
    public GameObject player;

    public AIPath aiPath;
    //private GameObject gameController;
    private GameController playerScoreManager;
    public bool pointAdded = false;
    public HealthBar healthBar;
    public float timer;

    void Awake()
    {
        
        playerAwarenessController = GetComponent<PlayerAwarenessScript>();

    }
    void Start()
    {
        weapon.parent = GameObject.FindGameObjectWithTag("EnemyBullets");
        player = GameObject.FindGameObjectWithTag("Player");
        playerScoreManager = GameObject.FindGameObjectWithTag("GameController").GetComponent<GameController>();
        // playerScoreManager = gameController.GetComponent<GameController>();
        gameObject.GetComponent<AIDestinationSetter>().target = player.transform;

        currentHealth = maxHealth;
        healthBar.SetMaxHealth(maxHealth);
        timer = 0;
    }

    void Update()
    {
        healthBar.SetHealth(currentHealth);
        if (currentHealth <= 0)
        {
            movementSpeed = 0.0f;
            //Destroy(gameObject);
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
        if (aiPath.reachedEndOfPath)
        {
            float dot = Vector2.Dot(transform.up.normalized, (player.transform.position - transform.position).normalized);
            Rotate();
            if (dot > accuracy)
            {
                if (timer > weapon.firerate)
                {
                    weapon.Fire();
                    timer = 0;
                }
                //weapon.Fire();
            }
        }
    }

    public void TakeDamage(float damage)
    {
        currentHealth -= damage;
        healthBar.SetHealth(currentHealth);
    }

    private void Rotate()
    {
        Vector2 targetDirection = player.transform.position - transform.position;
        float angle = Mathf.Atan2(targetDirection.y, targetDirection.x) * Mathf.Rad2Deg - 90f;
        //float angle = Mathf.Atan2(targetDirection.y, targetDirection.x) * Mathf.Rad2Deg;
        Quaternion q = Quaternion.Euler(new Vector3(0,0,angle));
        transform.localRotation = Quaternion.Slerp(transform.localRotation, q ,rotationSpeed);
    }

}
