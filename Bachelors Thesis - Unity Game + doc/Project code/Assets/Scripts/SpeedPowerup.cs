using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpeedPowerup : MonoBehaviour
{
    private PlayerController playerController;

    public float speed = 5;
    public float speedBuffTime;

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
        playerController.SetMoveSpeed(speed,speedBuffTime);
        Destroy(gameObject);
    }

}
