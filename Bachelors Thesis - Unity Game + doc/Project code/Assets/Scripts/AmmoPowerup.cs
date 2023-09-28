using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AmmoPowerup : MonoBehaviour
{
    private PlayerController playerController;
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

        foreach (Weapon wep in playerController.weapons)
        {
            wep.totalAmmo = wep.maxAmmo;
            wep.magAmmo = wep.ammoCapacity;
        }
        Destroy(gameObject);
    }
}
