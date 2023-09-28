using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HealthPowerup : MonoBehaviour
{
    //public GameController controller;
    private PlayerController playerController;

    public int health=50;
    // Start is called before the first frame update
    void Start()
    {
        //controller = GameObject.FindGameObjectWithTag("GameController").GetComponent<GameController>();
        playerController = GameObject.FindGameObjectWithTag("Player").GetComponent<PlayerController>();
    }

    // Update is called once per frame
    void Update()
    {
        
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
        playerController.AddHealth(health);
        Destroy(gameObject);
    }
}
