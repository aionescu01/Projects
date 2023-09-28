using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour
{
    //animatie cand loveste glontul
    public GameObject hitEffect;
    public float defaultDamage=25;
    public float damage;
    public Vector3 startingPosition;
    public float despawnDistance = 7;
    public bool hasRange = false;
    void Start()
    {
        SetDamage(defaultDamage);
    }

    void Update()
    {
        float dist = Vector3.Distance(startingPosition, gameObject.transform.position);
        if(dist>=despawnDistance && hasRange)
            Destroy(gameObject);
    }

    private void OnBecameInvisible()
    {
        Destroy(gameObject);
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        Debug.Log(collision.gameObject.tag);
        if (collision.gameObject.CompareTag("Obstacle"))
        {
            Destroy(gameObject);
        }
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {

        //Debug.Log(collision.gameObject.tag);

        //gameObject.SetActive(false);
        if (!collision.CompareTag("Powerup") && !(collision.CompareTag("Player") && gameObject.CompareTag("Bullet")) &&
            !(collision.CompareTag("Enemy2") && gameObject.CompareTag("EnemyBullet")) && !(collision.CompareTag("Bullet") && gameObject.CompareTag("Bullet"))
            && !(collision.CompareTag("EnemyBullet") && gameObject.CompareTag("EnemyBullet")))
        {
            //Debug.Log(collision.gameObject.tag+" a "+gameObject.tag);
            Destroy(gameObject);
        }

        if (collision.gameObject.CompareTag("Enemy1") && gameObject.CompareTag("Bullet"))
        {
            collision.gameObject.GetComponent<Enemy1Script>().TakeDamage(damage);
        }

        if (collision.gameObject.CompareTag("Enemy2") && gameObject.CompareTag("Bullet"))
        {
            collision.gameObject.GetComponent<Enemy2Script>().TakeDamage(damage);
        }

        if (collision.gameObject.CompareTag("Player") && gameObject.CompareTag("EnemyBullet"))
        {
            collision.gameObject.GetComponent<PlayerController>().TakeDamage(damage);
        }

        //GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);

        //Destroy(effect);
    }

    
    public void SetDamage(float dmg)
    {
        damage = dmg;
    }
}
