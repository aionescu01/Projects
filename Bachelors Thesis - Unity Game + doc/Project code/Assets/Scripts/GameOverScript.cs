using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.SocialPlatforms.Impl;

public class GameOverScript : MonoBehaviour
{
    private GameController gameController;
    public Canvas inGameUI;
    public TextMeshProUGUI timerText;
    public TextMeshProUGUI scoreText;
    void Start()
    {
        gameController = GameObject.FindGameObjectWithTag("GameController").GetComponent<GameController>();
        gameController.timerController.EndTimer();
        Time.timeScale = 0f;
        timerText.text = GameObject.FindGameObjectWithTag("Timer").GetComponent<TextMeshProUGUI>().text;
        scoreText.text = gameController.scoreText.text;
        Destroy(inGameUI.gameObject);
        timerText.tag = "Timer";
        
    }

    void Update()
    {
        
    }

    public void RetryLevel()
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
