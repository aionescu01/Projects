using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PanelScript : MonoBehaviour
{
    public TimerController timerController;
    void Awake()
    {
        Time.timeScale = 0f;
        timerController.gamePaused = true;
    }

    void Update()
    {

    }

    public void Unpause()
    {
        Time.timeScale = 1f;
        timerController.gamePaused = false;
        Destroy(gameObject);
    }



}
