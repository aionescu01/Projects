using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LastMissionPanel : MonoBehaviour
{
    public string mainMenu = "MainMenu";
    public void MainMenu()
    {
        SceneManager.LoadScene(mainMenu);
    }
}
