using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PauseMenuScript : MonoBehaviour
{
    public Canvas inGameUI;
    public TextMeshProUGUI timerText;
    public GameController gameController;
    public TextMeshProUGUI moveSpeed;
    public TextMeshProUGUI healthStat;
    public TextMeshProUGUI damageMultiplier;
    public PlayerController player;
    public TextMeshProUGUI scoreText;
    public GameObject[] menus;
    void Start()
    {
        
        //gameController = GameObject.FindGameObjectWithTag("GameController").GetComponent<GameController>();
        //player = GameObject.FindGameObjectWithTag("Player").GetComponent<PlayerController>();
        //inGameUI = GameObject.FindGameObjectWithTag("LevelUI").GetComponent<Canvas>();
    }

    void Update()
    {

    }

    public void Pause()
    {

        moveSpeed.text =
            "Move speed: "+ player.moveSpeed;
        timerText.text = GameObject.FindGameObjectWithTag("Timer").GetComponent<TextMeshProUGUI>().text;
        healthStat.text = "Health: " + player.currentHealth.ToString() + "/" + player.maxHealth.ToString();
        damageMultiplier.text = "Damage multiplier: " + player.damageMultiplier + "x";
        gameController.timerController.gamePaused = true;
        Time.timeScale = 0f;
        inGameUI.gameObject.SetActive(false);
        scoreText.text = "Score: " + gameController.score;

    }

    public void Unpause()
    {
        gameController.timerController.gamePaused = false;
        inGameUI.gameObject.SetActive(true);
        foreach (var menu in menus)
            menu.SetActive(false);
        Time.timeScale = 1f;
    }

    public void RestartLevel()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        Time.timeScale = 1f;
    }

    public void MainMenu()
    {
        SceneManager.LoadScene("Menu");
        Time.timeScale = 1f;
    }
}
