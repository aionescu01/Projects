using System.Collections;
using System.Collections.Generic;
using System.Linq;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameFinishedPanel : MonoBehaviour
{
    public GameManager gameManager;
    public TextMeshProUGUI gameFinishedText;
    public TextMeshProUGUI levelsCompletedText;
    public TextMeshProUGUI scoreText;
    public TextMeshProUGUI timesText;
    public TextMeshProUGUI endlessScoreText;
    public TextMeshProUGUI endlessTimesText;
    public int highScores = 0;
    public float times = 0;
    void Start()
    {
        gameManager = GameObject.FindGameObjectWithTag("GameManager").GetComponent<GameManager>();
        
        
    }

    void Update()
    {
        //for (int i = 0; i < gameManager.highScores.Length; i++)
            //highScores += gameManager.highScores[i];

            highScores = gameManager.highScores.Sum();

        scoreText.text = "Total score: " + highScores;
        if (gameManager.gameFinished)
        {
            gameFinishedText.text = "Story mode is finished";
        }
        else
        {
            gameFinishedText.text = "Story mode is not finished";
        }

        levelsCompletedText.text = "Levels completed: " + gameManager.unlockedLevels.Length.ToString() + "/15";

        //for (int i = 0; i < gameManager.times.Length; i++)
            //times += gameManager.times[i];
        

        times = gameManager.times.Sum();

        timesText.text = "Total time: " + times + " seconds";

        endlessScoreText.text = "Endless high score: " + PlayerPrefs.GetInt("endlessHighScore");

        endlessTimesText.text = "Endless record time lasted: " + PlayerPrefs.GetFloat("endlessTimeRecord") + " seconds";


    }

    public void ShowPanel()
    {
        Time.timeScale = 0f;
        

    }

    public void ResetPanel()
    {
        highScores = 0;
        times = 0;
    }

}
