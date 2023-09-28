using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

public class EndlessLevel : MonoBehaviour
{
    public GameController gameController;
    public int highScore;
    public float timeRecord;
    public GameObject scoreRecordText;
    public GameObject timeRecordText;
    public GameObject scoreRecordText2;
    public GameObject timeRecordText2;
    void Start()
    {
        highScore = PlayerPrefs.GetInt("endlessHighScore");
        timeRecord = PlayerPrefs.GetFloat("endlessTimeRecord");
    }

    void Update()
    {
        if (gameController.score > highScore)
        {
            highScore = gameController.score;
            PlayerPrefs.SetInt("endlessHighScore",highScore);
            scoreRecordText.SetActive(true);
            scoreRecordText2.SetActive(true);
        }

        if (gameController.timerController.elapsedTime > timeRecord)
        {
            timeRecordText.SetActive(true);
            timeRecordText2.SetActive(true);
            timeRecord = gameController.timerController.elapsedTime;
            PlayerPrefs.SetFloat("endlessTimeRecord",timeRecord);
        }
    }

    public void GiveUp()
    {

        SceneManager.LoadScene("Menu");
        Time.timeScale = 1f;

    }

}
