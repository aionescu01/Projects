using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using TMPro;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.Audio;
using UnityEngine.SceneManagement;
using UnityEngine.SocialPlatforms.Impl;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public float health;
    public float moveSpeed;
    public float damageMultiplier;
    public String name;
    public bool[] weaponsEnabled;
    //public bool fists;
    //public bool pistol;
    //public bool smg;
    //public bool shotgun;
    public int pistolMagAmmo;
    public int pistolMaxAmmo;
    public int smgMagAmmo;
    public int smgMaxAmmo;
    public int shotgunMagAmmo;
    public int shotgunMaxAmmo;
    public Sprite[] character;
    public Sprite[] characterReload;
    public int[] unlockedLevels;
    public int[] lockedLevels;
    public int currentLevelIndex;
    public int lastLevelUnlocked;
    public int[] highScores;
    public float[] times;
    public int endlessHighScore;
    public float endlessTime;
    public bool gameFinished;
    public int lastLevelNr = 15;
    public GameObject gameFinishedScreen;
    public Sprite[] blueMan;
    public Sprite[] brownMan;
    public Sprite[] blueManReload;
    public Sprite[] brownManReload;
    //public SettingsMenuScript settingsMenuScript;
    //public AudioMixer audioMixer;

    //public Resolution[] resolutions;

    //public TMP_Dropdown resolutionDropdown;
    //public TMP_Dropdown qualityDropdown;
    //public Toggle fullscreenToggle;
    //public Slider masterVolumeSlider;
    //public Slider musicVolumeSlider;
    void Awake()
    {
        GameObject[] objs = GameObject.FindGameObjectsWithTag("GameManager");

        if (objs.Length > 1)
        {
            Destroy(this.gameObject);
        }

        DontDestroyOnLoad(this.gameObject);
        lastLevelUnlocked = PlayerPrefs.GetInt("lastLevelUnlocked");
        if (lastLevelUnlocked > 1)
        {
            for (int i = 2; i <= lastLevelUnlocked; i++)
            {
                AddLevel(i);
            }
        }
        
    }
    void Start()
    {




        //resolutions = Screen.resolutions;
        //resolutions = Screen.resolutions.Where(resolution => resolution.refreshRate == Screen.currentResolution.refreshRate).ToArray();
        //resolutionDropdown.ClearOptions();

        //List<string> options = new List<string>();
        //int currentResolutionIndex = 0;

        //for (int i = 0; i < resolutions.Length; i++)
        //{
        //    string option = resolutions[i].width + "*" + resolutions[i].height + " " + resolutions[i].refreshRate + "Hz";

        //    options.Add(option);

        //    if (resolutions[i].width == Screen.width && resolutions[i].height == Screen.height)
        //    {
        //        currentResolutionIndex = i;
        //    }
        //}

        //resolutionDropdown.AddOptions(options);
        ////resolutionDropdown.value = currentResolutionIndex;
        //resolutionDropdown.value = PlayerPrefs.GetInt("resolutionIndex");
        //resolutionDropdown.RefreshShownValue();

        //int qualitySetting = PlayerPrefs.GetInt("Quality");
        //qualityDropdown.value = qualitySetting;

        //int fullscreenSetting = PlayerPrefs.GetInt("isFullscreen");

        //if (fullscreenSetting == 0)
        //{
        //    fullscreenToggle.isOn = false;
        //}
        //else
        //{
        //    fullscreenToggle.isOn = true;
        //}


        //float masterVolumeLevel = PlayerPrefs.GetFloat("masterVolume");
        //masterVolumeSlider.value = masterVolumeLevel;

        //float musicVolumeLevel = PlayerPrefs.GetFloat("musicVolume");
        //musicVolumeSlider.value = musicVolumeLevel;









        for (int i = 0; i < highScores.Length; i++)
        {
            int j = i + 1;
            highScores[i] = PlayerPrefs.GetInt("highscore" + j);
 
        }

        for (int i = 0; i < times.Length; i++)
        {
            int j = i + 1;
            times[i] = PlayerPrefs.GetFloat("time" + j);

        }

        int aux = PlayerPrefs.GetInt("gameFinished");
        if (aux == 0)
            gameFinished = false;
        else
            gameFinished = true;

        if (PlayerPrefs.GetString("characterName") == "Blue Man")
            ChooseBlueMan();
        else
            ChooseBrownMan();

    }

    void Update()
    {
        //resetare nivele unlocked
        if (Input.GetKeyDown(KeyCode.L))
        {
            PlayerPrefs.SetInt("lastLevelUnlocked",1);
        }

        //resetare nivele locked
        if (Input.GetKeyDown(KeyCode.K))
        {
            PlayerPrefs.SetInt("lastLevelUnlocked", 15);
        }

        //resetare panou de joc terminat
        if (Input.GetKeyDown(KeyCode.J))
        {
            PlayerPrefs.SetInt("gameFinished", 0);
        }
    }



    public void AddLevel(int levelNr)
    {


        //intai trb verificat daca nivelul ala este ultimul in lista unlocked


        if (lockedLevels.Length > 0)
        {

        
            int unlockedLength = unlockedLevels.Length + 1;


            int[] aux = new int[unlockedLength];
            for (int i = 0; i < unlockedLevels.Length; i++)
            {
                aux[i] = unlockedLevels[i];
            }

            aux[unlockedLevels.Length] = lockedLevels[0]; //adaug primul nivel nedeblocat la deblocate

            unlockedLevels = new int[unlockedLength];
            unlockedLevels = aux;

            if (lockedLevels.Length <= 1)
            {
                lockedLevels = new int[0];
            }
            else
            {
                int lockedLength = lockedLevels.Length - 1;
                aux = new int[lockedLength];
                for (int i = 1; i < lockedLevels.Length; i++)
                {
                    aux[i - 1] = lockedLevels[i];
                }

                lockedLevels = new int[lockedLength];
                lockedLevels = aux;
            }

        }


    }

    public void AddCompleteLevel(int levelNr)
    {


        //intai trb verificat daca nivelul ala este ultimul in lista unlocked

        if (unlockedLevels[unlockedLevels.Length-1]==levelNr && lockedLevels.Length>0)
        {

            lastLevelUnlocked = levelNr+1;
            PlayerPrefs.SetInt("lastLevelUnlocked", lastLevelUnlocked);
            int unlockedLength = unlockedLevels.Length+1;
            

            int[] aux = new int[unlockedLength];
            for (int i = 0; i < unlockedLevels.Length; i++)
            {
                aux[i] = unlockedLevels[i];
            }
            aux[unlockedLevels.Length] = lockedLevels[0]; //adaug primul nivel nedeblocat la deblocate

            unlockedLevels = new int[unlockedLength];
            unlockedLevels = aux;

            if (lockedLevels.Length <= 1)
            {
                lockedLevels = new int[0];
            }
            else
            {
                int lockedLength = lockedLevels.Length - 1;
                aux = new int[lockedLength];
                for (int i = 1; i < lockedLevels.Length; i++)
                {
                    aux[i - 1] = lockedLevels[i];
                }

                lockedLevels = new int[lockedLength];
                lockedLevels = aux;
            }
            


        }

    }

    public void ChooseBlueMan()
    {

        name = "Blue Man";
        PlayerPrefs.SetString("characterName", name);
        health = 150f;
        moveSpeed = 5f;
        damageMultiplier = 1f;
        //fists = true;
        pistolMagAmmo = 12;
        pistolMaxAmmo = -1;
        smgMagAmmo = 50;
        smgMaxAmmo = 150;
        shotgunMagAmmo = 7;
        shotgunMaxAmmo = 42;
        character = blueMan;
        characterReload = blueManReload;
        bool[] characterWeaponsEnabled = { true, true, true, true };
        weaponsEnabled = characterWeaponsEnabled;

    }

    public void ChooseBrownMan()
    {
        name = "Brown Man";
        PlayerPrefs.SetString("characterName", name);
        health = 100f;
        moveSpeed = 7f;
        damageMultiplier = 0.9f;
        //fists = false;
        pistolMagAmmo = 12;
        pistolMaxAmmo = -1;
        smgMagAmmo = 50;
        smgMaxAmmo = 100;
        shotgunMagAmmo = 7;
        shotgunMaxAmmo = 35;
        character = brownMan;
        characterReload = brownManReload;
        bool[] characterWeaponsEnabled = { false, true, true, true };
        weaponsEnabled = characterWeaponsEnabled;

    }

}
