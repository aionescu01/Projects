using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DamagePowerup : MonoBehaviour
{
    private PlayerController playerController;
    public float multiplier = 2f;
    public float buffTime=5f;
    void Start()
    {
        playerController = GameObject.FindGameObjectWithTag("Player").GetComponent<PlayerController>();

    }

    void OnTriggerEnter2D(Collider2D collider)
    {
        if (collider.CompareTag("Player"))
        {
            Pickup();
        }
    }

    void Pickup()
    {
        //playerController.SetAutomaticFire(buffTime);
        playerController.SetDamageMultiplier(multiplier, buffTime);
        Destroy(gameObject);
    }
}
