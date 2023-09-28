using System;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class PlayerController : MonoBehaviour
{

    public String name;
    public float maxHealth = 100;
    public float currentHealth;
    public float defaultSpeed = 5f;
    public float moveSpeed = 5f;
    public float damageMultiplier = 1f;
    public Rigidbody2D rb;
    public Camera cam;
    private Vector2 moveDirection;
    private Vector2 mousePosition;
    public Weapon weapon;
    public bool automaticFire = false;


    public HealthBar healthBar;
    public TextMeshProUGUI healthText;

    public Sprite[] sprites = null;
    public Sprite[] reloadSprites = null;
    public Weapon[] weapons = null;
    private int Fists = 0;
    private int Pistol = 1;
    private int Rifle = 2;
    private int Shotgun = 3;
    private int currentWeapon = 1;
    public bool[] weaponsEnabled ={true,true,true,true};

    private Vector2 movement;
    public GameOverScript gameOverScript;

    private float newSpeed = 5f;
    private bool speedBuff = false;
    private bool changedSpeed = false;

    public float timer;
    public bool hasFired;

    void Start()
    {
        currentHealth = maxHealth;
        moveSpeed = defaultSpeed;
        SelectWeapon(Pistol);
        healthBar.SetMaxHealth(maxHealth);
        healthText.text = currentHealth.ToString();
        hasFired = true;
        cam = GameObject.FindGameObjectWithTag("MainCamera").GetComponent<Camera>();

    }

    void Update()
    {
        

        healthBar.SetHealth(currentHealth);
        healthText.text = currentHealth.ToString();
        movement.x = Input.GetAxisRaw("Horizontal");
        movement.y = Input.GetAxisRaw("Vertical");


        if (currentHealth <= 0)
        {
            moveSpeed = 0.0f;
            gameOverScript.gameObject.SetActive(true);
            Destroy(gameObject.transform.parent.gameObject);
        }

        foreach (Weapon weapon in weapons)
        {
            weapon.damageMultiplier = damageMultiplier;
        }


        if (weapons[currentWeapon].reloading == false)
        {
            if (Input.GetKeyDown(KeyCode.Alpha1) && Time.timeScale.Equals(1f) && weaponsEnabled[Fists])
            {
                SelectWeapon(Fists);
                currentWeapon = Fists;
                //moveSpeed = 10f;
                moveSpeed = defaultSpeed + 5f;
                changedSpeed = false;
                disableWeapons();
                weapons[Fists].gameObject.SetActive(true);
            }

            if (Input.GetKeyDown(KeyCode.Alpha2) && Time.timeScale.Equals(1f) && weaponsEnabled[Pistol])
            {
                SelectWeapon(Pistol);
                currentWeapon = Pistol;
                automaticFire = false;
                //moveSpeed = 5f;
                moveSpeed = defaultSpeed;
                changedSpeed = false;
                weapon = weapons[Pistol];
                //weapon.damage *= damageMultiplier;
                disableWeapons();
                weapons[Pistol].gameObject.SetActive(true);
            }

            if (Input.GetKeyDown(KeyCode.Alpha3) && Time.timeScale.Equals(1f) && weaponsEnabled[Rifle])
            {
                SelectWeapon(Rifle);
                currentWeapon = Rifle;
                automaticFire = true;
                //moveSpeed = 3f;
                moveSpeed = defaultSpeed -1f;
                changedSpeed = false;
                weapon = weapons[Rifle];
                //weapon.damage *= damageMultiplier;
                disableWeapons();
                weapons[Rifle].gameObject.SetActive(true);

            }

            if (Input.GetKeyDown(KeyCode.Alpha4) && Time.timeScale.Equals(1f) && weaponsEnabled[Shotgun])
            {
                SelectWeapon(Shotgun);
                currentWeapon = Shotgun;
                automaticFire = false;
                //moveSpeed = 2.5f;
                moveSpeed = defaultSpeed - 2f;
                changedSpeed = false;
                weapon = weapons[Shotgun];
                //weapon.damage *= damageMultiplier;
                disableWeapons();
                weapons[Shotgun].gameObject.SetActive(true);

            }

            if (speedBuff)
            {
                if (!changedSpeed)
                {
                    moveSpeed += newSpeed;
                    changedSpeed = true;
                }
            }
            else
            {
                if (changedSpeed)
                {
                    moveSpeed -= newSpeed;
                    changedSpeed = false;
                }
            }
        }
        
        if (weapons[currentWeapon].reloading)
        {
            gameObject.GetComponentInChildren<SpriteRenderer>().sprite = reloadSprites[currentWeapon];
        }
        else
        {
            gameObject.GetComponentInChildren<SpriteRenderer>().sprite = sprites[currentWeapon];
        }


        timer += Time.deltaTime;

        if (automaticFire==false && currentWeapon!=Fists )
        {
            
            if (Input.GetMouseButtonDown(0) && Time.timeScale.Equals(1f))
            {
                if (timer > weapon.firerate)
                {
                    weapon.Fire();
                    timer = 0;
                }

            }
        }

        if (automaticFire == true && currentWeapon != Fists)
        {
            if (Input.GetMouseButton(0) && Time.timeScale.Equals(1f))
            {
                weapon.firerate = 0.1f;
                //weapon.Fire();

                if (timer > weapon.firerate)
                {
                    weapon.Fire();
                    timer = 0;
                }

            }
        }



        //timer += Time.deltaTime;
    }

    private void FixedUpdate()
    {
        //moveDirection = new Vector2(moveX, moveY).normalized;
        //rb.velocity = new Vector2(moveDirection.x * moveSpeed, moveDirection.y * moveSpeed);
        //float aimAngle = Mathf.Atan2(aimDirection.y, aimDirection.x) * Mathf.Rad2Deg ;

        //movement.x = Input.GetAxisRaw("Horizontal");
        //movement.y = Input.GetAxisRaw("Vertical");


        moveDirection.x = movement.x;
        moveDirection.y = movement.y;
        mousePosition = cam.ScreenToWorldPoint(Input.mousePosition);


        
        rb.MovePosition(rb.position + moveDirection.normalized * moveSpeed * Time.fixedDeltaTime);

        Vector2 aimDirection = mousePosition - rb.position;
        
        float aimAngle = Mathf.Atan2(aimDirection.y, aimDirection.x) * Mathf.Rad2Deg - 90f;
        rb.rotation = aimAngle;
    }

    public void TakeDamage(float damage)
    {
        currentHealth -= damage;
        healthBar.SetHealth(currentHealth);
    }

    public void AddHealth(float hp)
    {
        if (currentHealth + hp > maxHealth)
        {
            currentHealth = maxHealth;
        }
        else
        {
            currentHealth += hp;
        }
        
    }

    public void SetMoveSpeed(float speed, float time)
    {
        StartCoroutine(SetSpeed(speed, time));
    }

    IEnumerator SetSpeed(float speed, float time)
    {
        newSpeed = speed;
        speedBuff = true;
        yield return new WaitForSeconds(time);
        speedBuff = false;
    }

    public void SetAutomaticFire(float time)
    {
        StartCoroutine(SetFireRate(time));
    }

    IEnumerator SetFireRate(float time)
    {
        automaticFire = true;
        yield return new WaitForSeconds(time);
        automaticFire = false;
    }

    public void SetDamageMultiplier(float multiplier, float time)
    {
        StartCoroutine(SetMultiplier(multiplier,time));
    }

    IEnumerator SetMultiplier(float multiplier, float time)
    {
        float oldMultiplier = damageMultiplier;
        damageMultiplier = multiplier;
        yield return new WaitForSeconds(time);
        damageMultiplier = oldMultiplier;
    }

    void SelectWeapon(int nr)
    {
        currentWeapon = nr;
        Sprite currentWeaponSprite = sprites[nr];
        gameObject.GetComponentInChildren<SpriteRenderer>().sprite = currentWeaponSprite;
        weapons[currentWeapon].timer = 0;

    }

    void disableWeapons()
    {
        foreach (var wep in weapons)
        {
            wep.gameObject.SetActive(false);   
        }
    }


}
