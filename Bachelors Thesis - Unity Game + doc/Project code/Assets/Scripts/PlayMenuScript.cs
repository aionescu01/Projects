using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class PlayMenuScript : MonoBehaviour
{
    public string endlessLevelName = "LevelEndless";
    public Button[] buttons;
    public void PlayEndless()
    {
        SceneManager.LoadScene(endlessLevelName);
    }

    public void EnableButtons()
    {
        foreach (Button button in buttons)
        {
            button.interactable = true;
        }
    }

    public void DisableButtons()
    {
        foreach (Button button in buttons)
        {
            button.interactable = false;
        }

    }
}
