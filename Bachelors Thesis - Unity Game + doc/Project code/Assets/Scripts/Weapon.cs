using System.Collections;
using System.Collections.Generic;
using System.Numerics;
using TMPro;
using UnityEngine;
using static UnityEngine.GraphicsBuffer;
using Quaternion = UnityEngine.Quaternion;
using Vector3 = UnityEngine.Vector3;

public class Weapon : MonoBehaviour
{

    public GameObject bulletPrefab;

    public Transform firePoint;

    public float fireForce = 20f;

    public float timer;

    public float firerate;

    public float magAmmo;
    public float totalAmmo;
    public float ammoCapacity;
    public float maxAmmo;
    public bool infiniteAmmo = false;
    public bool reloading = false;
    public float damage;
    public float reloadTime;
    public GameObject parent;
    public bool isShotgun = false;
    public float damageMultiplier = 1f;
    public bool canShoot;
    public PlayerController player;

    void Start()
    {
        magAmmo = ammoCapacity;
        totalAmmo = maxAmmo;
        canShoot = true;
        player = GameObject.FindGameObjectWithTag("Player").GetComponent<PlayerController>();
    }
    
    void Update()
    {
        //timer += Time.deltaTime;


        if (Input.GetKeyDown(KeyCode.R) && Time.timeScale.Equals(1f) && !magAmmo.Equals(ammoCapacity))
        {
            if (infiniteAmmo == false)
            {
                reloading = true;
                Reload();
            }
        }

    }

    public void Fire()
    {
        
        if (reloading == false)
        {
            if (player.timer > firerate)
            //if(canShoot)
            {
                if (magAmmo > 0)
                {
                    if (infiniteAmmo == false)
                        magAmmo--;

                    if (isShotgun == false)
                    {
                        GameObject bullet = Instantiate(bulletPrefab, firePoint.position, firePoint.rotation,
                            parent.transform);

                        bullet.GetComponent<Bullet>().startingPosition = firePoint.position;
                        bullet.GetComponent<Bullet>().hasRange = false;

                    bullet.GetComponent<Bullet>().defaultDamage = damage * damageMultiplier;
                    bullet.GetComponent<Rigidbody2D>().AddForce(firePoint.up * fireForce, ForceMode2D.Impulse);


                   // Debug.Log("aa");
                        //player.timer = 0;

                    }
                    else
                    {

                        GameObject bullet = Instantiate(bulletPrefab, firePoint.position, firePoint.rotation,
                            parent.transform);
                        bullet.GetComponent<Bullet>().startingPosition = firePoint.position;
                        bullet.GetComponent<Bullet>().hasRange = true;
                        bullet.GetComponent<Bullet>().defaultDamage = damage * damageMultiplier;
                        bullet.GetComponent<Rigidbody2D>().AddForce(firePoint.up * fireForce, ForceMode2D.Impulse);


                        GameObject bullet2 = Instantiate(bulletPrefab, firePoint.position, firePoint.rotation,
                            parent.transform);
                        bullet2.GetComponent<Bullet>().startingPosition = firePoint.position;
                        bullet2.GetComponent<Bullet>().hasRange = true;
                        bullet2.GetComponent<Bullet>().defaultDamage = damage * damageMultiplier;
                        bullet2.GetComponent<Rigidbody2D>().AddForce(Quaternion.Euler(0f, 0f, 5f) * firePoint.up  * fireForce, ForceMode2D.Impulse);

                        
                        GameObject bullet3 = Instantiate(bulletPrefab, firePoint.position, firePoint.rotation,
                            parent.transform);
                        bullet3.GetComponent<Bullet>().startingPosition = firePoint.position;
                        bullet3.GetComponent<Bullet>().hasRange = true;
                        bullet3.GetComponent<Bullet>().defaultDamage = damage * damageMultiplier;
                        bullet3.GetComponent<Rigidbody2D>().AddForce(Quaternion.Euler(0f, 0f, -5f) * firePoint.up * fireForce, ForceMode2D.Impulse);

                        //Debug.Log("aa");
                        //player.timer = 0;

                    }
                   
                    //player.timer = 0;
                    //timer = 0;
                    //canShoot = false;
                    //StartCoroutine(FireWait(firerate));
                }   
            }
        }

    }

    public void Reload()
    {
        if (infiniteAmmo == true)
        {
            magAmmo = ammoCapacity;
        }

        if (ammoCapacity-magAmmo > totalAmmo)
            magAmmo = totalAmmo;

        else
        {
            totalAmmo = totalAmmo - ammoCapacity + magAmmo;
            magAmmo = ammoCapacity;
        }
        StartCoroutine(ReloadWait(reloadTime));
    }


    IEnumerator ReloadWait(float time)
    {
        yield return new WaitForSeconds(time);
        reloading = false;
    }

    IEnumerator FireWait(float time)
    {
        yield return new WaitForSeconds(time);
        canShoot = true;
    }

}
