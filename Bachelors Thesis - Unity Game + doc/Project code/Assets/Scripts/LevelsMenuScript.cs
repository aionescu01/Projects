using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class LevelsMenuScript : MonoBehaviour
{
    public GameManager gameManager;
    public Button[] levels;
    private Button lastClicked;

    void Start()
    {
        gameManager = GameObject.FindGameObjectWithTag("GameManager").GetComponent<GameManager>();
        foreach (int i in gameManager.lockedLevels)
        {
            levels[i-1].gameObject.SetActive(false);
        }
    }

    void Update()
    {
        foreach (int i in gameManager.unlockedLevels)
        {
            levels[i-1].gameObject.SetActive(true);
        }
    }

    public void LoadLevel(int levelNr)
    {
        gameManager.currentLevelIndex = levelNr;

        SceneManager.LoadScene("Level"+levelNr);
    }

    

}
