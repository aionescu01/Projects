using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class UIScript : MonoBehaviour
{
    //public Weapon Fists;
    public Weapon Fists;
    public Weapon Pistol;
    public Weapon SMG;
    public Weapon Shotgun;

    public PlayerController player;


    public TextMeshProUGUI PistolAmmoText;
    public TextMeshProUGUI SMGAmmoText;
    public TextMeshProUGUI ShotgunAmmoText;


    public GameObject[] fistsImages;
    public GameObject[] pistolImages;
    public GameObject[] SMGImages;
    public GameObject[] shotgunImages;

    void Awake()
    {
        player = GameObject.FindGameObjectWithTag("Player").GetComponent<PlayerController>();
        pistolImages[0].gameObject.SetActive(false);
        pistolImages[1].gameObject.SetActive(true);

        if (!player.weaponsEnabled[0])
            fistsImages[2].gameObject.SetActive(true);
        if (!player.weaponsEnabled[1])
            pistolImages[2].gameObject.SetActive(true);
        if (!player.weaponsEnabled[2]) 
            SMGImages[2].gameObject.SetActive(true);
        if (!player.weaponsEnabled[3])
            shotgunImages[2].gameObject.SetActive(true);

        player = null;
    }
    void Update()
    {
        if (Fists.isActiveAndEnabled)
        {
            fistsImages[0].gameObject.SetActive(false);
            fistsImages[1].gameObject.SetActive(true);
        }
        else
        {
            fistsImages[0].gameObject.SetActive(true);
            fistsImages[1].gameObject.SetActive(false);
        }

        if (Pistol.isActiveAndEnabled)
        {
            pistolImages[0].gameObject.SetActive(false);
            pistolImages[1].gameObject.SetActive(true);
        }
        else
        {
            pistolImages[0].gameObject.SetActive(true);
            pistolImages[1].gameObject.SetActive(false);
        }
        

        if (Pistol.infiniteAmmo)
        {
            PistolAmmoText.text = "\u221E";
            PistolAmmoText.fontSize = 20f;
        }
        else
        {
            if (Pistol.reloading)
            {
                PistolAmmoText.text = "Reloading...";
                PistolAmmoText.fontSize = 10f;
            }
            else
            {
                PistolAmmoText.fontSize = 18f;
                PistolAmmoText.text = Pistol.magAmmo.ToString() + "/" + Pistol.totalAmmo.ToString();

            }
        }


        if (SMG.isActiveAndEnabled)
        {
            SMGImages[0].gameObject.SetActive(false);
            SMGImages[1].gameObject.SetActive(true);
        }
        else
        {
            SMGImages[0].gameObject.SetActive(true);
            SMGImages[1].gameObject.SetActive(false);
        }

        if (SMG.infiniteAmmo)
        {
            SMGAmmoText.text = "\u221E";
            SMGAmmoText.fontSize = 20f;
        }
        else
        {
            if (SMG.reloading)
            {
                SMGAmmoText.text = "Reloading...";
                SMGAmmoText.fontSize = 10f;
            }
            else
            {
                SMGAmmoText.fontSize = 18f;
                SMGAmmoText.text = SMG.magAmmo.ToString() + "/" + SMG.totalAmmo.ToString();

            }
        }
        


        if (Shotgun.isActiveAndEnabled)
        {
            shotgunImages[0].gameObject.SetActive(false);
            shotgunImages[1].gameObject.SetActive(true);
        }
        else
        {
            shotgunImages[0].gameObject.SetActive(true);
            shotgunImages[1].gameObject.SetActive(false);
        }

        
        if (Shotgun.infiniteAmmo)
        {
            ShotgunAmmoText.text = "\u221E";
            ShotgunAmmoText.fontSize = 20f;
        }
        else
        {
            if (Shotgun.reloading)
            {
                ShotgunAmmoText.fontSize = 10f;
                ShotgunAmmoText.text = "Reloading...";
            }
            else
            {
                ShotgunAmmoText.fontSize = 18f;
                ShotgunAmmoText.text = Shotgun.magAmmo.ToString() + "/" + Shotgun.totalAmmo.ToString();

            }
        }

        

        

    }

}
