using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LevelCompleteScript : MonoBehaviour
{
    private GameController gameController;
    public Canvas inGameUI;
    public TextMeshProUGUI timerText;
    public TextMeshProUGUI scoreText;
    public GameManager gameManager;
    public GameObject spawner;
    void Start()
    {
        gameController = GameObject.FindGameObjectWithTag("GameController").GetComponent<GameController>();
        gameController.timerController.EndTimer();
        Time.timeScale = 0f;
        timerText.text = GameObject.FindGameObjectWithTag("Timer").GetComponent<TextMeshProUGUI>().text;
        scoreText.text = gameController.scoreText.text;
        gameController.gameObject.SetActive(false);
        spawner.gameObject.SetActive(false);
        Destroy(inGameUI.gameObject);
        timerText.tag = "Timer";
        gameManager = GameObject.FindGameObjectWithTag("GameManager").GetComponent<GameManager>();
    }

    void Update()
    {
 
    }

    public void RestartLevel()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        Time.timeScale = 1f;
    }

    public void NextLevel()
    {
        int lv = gameManager.currentLevelIndex + 1;
        string levelName = "Level" + lv;

        SceneManager.LoadScene(levelName);
        Time.timeScale = 1f;
        gameManager.currentLevelIndex++;
    }

    public void MainMenu()
    {
        SceneManager.LoadScene("Menu");
        Time.timeScale = 1f;
    }
}
