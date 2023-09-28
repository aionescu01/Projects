using System;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class TimerController : MonoBehaviour
{

    public static TimerController instance;
    public TextMeshProUGUI timeCounter;
    private TimeSpan timePlaying;
    private bool timerStarted;
    public bool gamePaused;
    public float elapsedTime;
    private void Awake()
    {
        instance = this;
    }
    void Start()
    {
        timeCounter.text = "Time: 00:00.00";
        timerStarted = false;
    }

    public void BeginTimer()
    {
        timerStarted = true;
        elapsedTime = 0f;

        StartCoroutine(UpdateTimer());
    }

    public void EndTimer()
    {
        timerStarted = false;
    }

    private IEnumerator UpdateTimer()
    {
        while (timerStarted)
        {
            if(!gamePaused)
            { 
                elapsedTime += Time.deltaTime;
                timePlaying = TimeSpan.FromSeconds(elapsedTime);
                string timePlayingStr = "Time: " + timePlaying.ToString("mm':'ss'.'ff");
                timeCounter.text = timePlayingStr;
            }
            yield return null;
        }
    }
}
