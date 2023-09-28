using System.Collections;
using System.Collections.Generic;
using Pathfinding;
using TMPro;
using UnityEngine;

public class GameController : MonoBehaviour
{
    public TextMeshProUGUI scoreText;
    public int score = 0;
    public int highscore = 0;
    public Transform playerbullet;
    public Transform enemybullet;
    public TimerController timerController;
    public GameOverScript gameOverUI;
    public PauseMenuScript pauseMenu;

    private bool gamePaused = false;
    private GameManager gameManager;
    public PlayerController player;
    public UIScript uiScript;
    public LevelCompleteScript levelCompleteUI;
    private SpawnEnemies enemySpawner;
    private SpawnPowerups powerupSpawner;
    //la spawnerul de powerup sa pot modifica la cate killuri se spawneaza powerup
    public int enemiesToSpawn;
    public int secondsToLast;
    public bool timedVictory;
    public bool enemyKillVictory;
    public bool timedVictoryAchieved = false;
    public bool enemyKillVictoryAchieved = false;
    public bool levelCompleteDone = false;
    public bool isHighscore;
    public bool endlessLevel;

    //public GameObject go;

    void Awake()
    {
        isHighscore = false;
       
        uiScript = GameObject.FindGameObjectWithTag("LevelUI").GetComponent<UIScript>();
        scoreText = GameObject.FindGameObjectWithTag("ScoreText").GetComponent<TextMeshProUGUI>();

        timerController = gameObject.GetComponent<TimerController>();

        timerController.timeCounter = GameObject.FindGameObjectWithTag("Timer").GetComponent<TextMeshProUGUI>();

        timedVictoryAchieved = false;
        enemyKillVictoryAchieved = false;

        enemySpawner = GameObject.FindGameObjectWithTag("Spawner").GetComponent<SpawnEnemies>();
        powerupSpawner = GameObject.FindGameObjectWithTag("Spawner").GetComponent<SpawnPowerups>();

        if (enemyKillVictory)
        {
            enemySpawner.enemyMaxNumber = enemiesToSpawn;

        }

        if (timedVictory)
        {
            enemySpawner.enemyMaxNumber = -1;
        }

        if (endlessLevel)
        {
            enemySpawner.enemyMaxNumber = -1;
        }

        gameManager = GameObject.FindGameObjectWithTag("GameManager").GetComponent<GameManager>();
        player.name = gameManager.name;
        player.defaultSpeed = gameManager.moveSpeed;
        player.damageMultiplier = gameManager.damageMultiplier;
        player.maxHealth = gameManager.health;
        player.weaponsEnabled = gameManager.weaponsEnabled;
        player.sprites = gameManager.character;
        player.reloadSprites = gameManager.characterReload;

        //fists
        //if (!gameManager.fists)
        //    player.weaponsEnabled[0] = false;

        //pistol
        if (gameManager.pistolMaxAmmo > 0)
        {
            player.weapons[1].maxAmmo = gameManager.pistolMaxAmmo;
            player.weapons[1].totalAmmo = gameManager.pistolMaxAmmo;
            player.weapons[1].magAmmo = gameManager.pistolMagAmmo;
            player.weapons[1].ammoCapacity = gameManager.pistolMagAmmo;

            uiScript.PistolAmmoText.text = uiScript.Pistol.magAmmo.ToString() + "/" + uiScript.Pistol.totalAmmo.ToString();
        }
        else if (gameManager.pistolMaxAmmo == -1)
        {
            player.weapons[1].infiniteAmmo = true;
            uiScript.PistolAmmoText.text = "\u221E";
        }
        else
            player.weaponsEnabled[1] = false;

        //smg
        if (gameManager.smgMaxAmmo > 0)
        {

            player.weapons[2].maxAmmo = gameManager.smgMaxAmmo;
            player.weapons[2].totalAmmo = gameManager.smgMaxAmmo;
            player.weapons[2].magAmmo = gameManager.smgMagAmmo;
            player.weapons[2].ammoCapacity = gameManager.smgMagAmmo;

            uiScript.SMGAmmoText.text = uiScript.SMG.magAmmo.ToString() + "/" + uiScript.SMG.totalAmmo.ToString();
            
        }
        else if (gameManager.smgMaxAmmo == -1)
        {
            player.weapons[2].infiniteAmmo = true;
            uiScript.SMGAmmoText.text = "\u221E";
        }
        else
            player.weaponsEnabled[2] = false;

        //shotgun
        if (gameManager.shotgunMaxAmmo > 0)
        {
            player.weapons[3].maxAmmo = gameManager.shotgunMaxAmmo;
            player.weapons[3].totalAmmo = gameManager.shotgunMaxAmmo;
            player.weapons[3].magAmmo = gameManager.shotgunMagAmmo;
            player.weapons[3].ammoCapacity = gameManager.shotgunMagAmmo;

            uiScript.ShotgunAmmoText.text = uiScript.Shotgun.magAmmo.ToString() + "/" + gameManager.shotgunMaxAmmo.ToString();
        }
        else if (gameManager.shotgunMaxAmmo == -1)
        {
            player.weapons[3].infiniteAmmo = true;
            uiScript.ShotgunAmmoText.text = "\u221E";
        }
        else
            player.weaponsEnabled[3] = false;

    }
    void Start()
    {
        //foreach (var powerup in GameObject.FindGameObjectsWithTag("Powerup"))
        //{
        //    Debug.Log(powerup);
        //    Physics2D.IgnoreCollision(playerbullet.GetComponent<Collider2D>(),powerup.GetComponent<Collider2D>(), true);
        //    Physics2D.IgnoreCollision(enemybullet.GetComponent<Collider2D>(),powerup.GetComponent<Collider2D>(), true);
        //}

        gameOverUI.gameObject.SetActive(false);

        highscore = PlayerPrefs.GetInt("highscore"+gameManager.currentLevelIndex, 0);
        timerController.BeginTimer();
        scoreText.text = "Score: "+ score.ToString();





    }

    void Update()
    {

        if (enemyKillVictory)
        {
            if (enemySpawner.allEnemiesSpawned)
            {


                if (GameObject.FindGameObjectsWithTag("Enemy1").Length == 0 &&
                    GameObject.FindGameObjectsWithTag("Enemy2").Length == 0)
                    enemyKillVictoryAchieved = true;
            }
        }

        if (timedVictory)
        {
            if (timerController.elapsedTime >= secondsToLast)
            {
                if (GameObject.FindGameObjectsWithTag("Enemy1").Length > 0)
                {
                    GameObject[] enemies = GameObject.FindGameObjectsWithTag("Enemy1");
                    foreach (var en in enemies)
                    {
                        Destroy(en.transform.parent.gameObject);   
                    }
                }

                if (GameObject.FindGameObjectsWithTag("Enemy2").Length > 0)
                {
                    GameObject[] enemies = GameObject.FindGameObjectsWithTag("Enemy2");
                    foreach (var en in enemies)
                    {
                        Destroy(en.transform.parent.gameObject);
                    }
                }
                timedVictoryAchieved = true;
            }
        }

        if ((enemyKillVictoryAchieved || timedVictoryAchieved) && levelCompleteDone == false)
        {
            levelCompleteDone = true;
            gameManager.AddCompleteLevel(gameManager.currentLevelIndex);

            if (isHighscore)
            {
                gameManager.highScores[gameManager.currentLevelIndex-1] = score;
                PlayerPrefs.SetInt("highscore"+gameManager.currentLevelIndex, score);
            }

            if(enemyKillVictory){
            if (timerController.elapsedTime < PlayerPrefs.GetFloat("time" + gameManager.currentLevelIndex) || gameManager.times[gameManager.currentLevelIndex-1]==0)
            {
                gameManager.times[gameManager.currentLevelIndex - 1] = timerController.elapsedTime;
                PlayerPrefs.SetFloat("time"+gameManager.currentLevelIndex,timerController.elapsedTime);
            }
            }
            else
            {
                gameManager.times[gameManager.currentLevelIndex - 1] = secondsToLast;
                PlayerPrefs.SetFloat("time"+gameManager.currentLevelIndex, secondsToLast);
            }

            if (gameManager.currentLevelIndex == gameManager.lastLevelNr && gameManager.gameFinished==false)
            {
                gameManager.gameFinished = true;
                Time.timeScale = 0f;
                PlayerPrefs.SetInt("gameFinished",1);
                Instantiate(gameManager.gameFinishedScreen, uiScript.gameObject.transform);
                Destroy(gameObject);
            }
            else
            { 
                levelCompleteUI.gameObject.SetActive(true);
            }
            
        }

        //metoda adaugat puncte
        if (Input.GetKeyDown(KeyCode.M))
            AddPoint();

        //resetare high score
        if (Input.GetKeyDown(KeyCode.N))
            PlayerPrefs.SetInt("highscore" + gameManager.currentLevelIndex, 0);

        if (Input.GetKeyDown(KeyCode.Escape) && !gameOverUI.isActiveAndEnabled)
        {
            if (gamePaused == false)
            {
                pauseMenu.gameObject.SetActive(true);
                pauseMenu.Pause();
                gamePaused = true;

            }
            else
            {
                pauseMenu.Unpause();
                pauseMenu.gameObject.SetActive(false);
                gamePaused = false;
            }
        }
    }

    public void AddPoint()
    {
        score += 1;
        scoreText.text = "Score: " + score.ToString();

        if (score > highscore)
        {
            //Debug.Log("highscore "+score);
            isHighscore = true;
            //PlayerPrefs.SetInt("highscore", score);
        }

    }
}
