using System.Collections.Generic;
using System.Linq;
using TMPro;
using UnityEngine;
using UnityEngine.Audio;
using UnityEngine.UI;


public class SettingsMenuScript : MonoBehaviour
{
    public AudioMixer audioMixer;

    public Resolution[] resolutions;

    public TMP_Dropdown resolutionDropdown;
    public TMP_Dropdown qualityDropdown;
    public Toggle fullscreenToggle;
    public Slider masterVolumeSlider;
    public Slider musicVolumeSlider;


    //public GameManager gameManager;



    void Awake()
    {
        ///gameManager = GameObject.FindGameObjectWithTag("GameManager").GetComponent<GameManager>();

        resolutions = Screen.resolutions;
        resolutions = Screen.resolutions.Where(resolution => resolution.refreshRate == Screen.currentResolution.refreshRate).ToArray();
        resolutionDropdown.ClearOptions();

        List<string> options = new List<string>();
        int currentResolutionIndex = 0;

        for (int i = 0; i < resolutions.Length; i++)
        {
            string option = resolutions[i].width + "*" + resolutions[i].height + " " + resolutions[i].refreshRate + "Hz";

            options.Add(option);

            if (resolutions[i].width == Screen.width && resolutions[i].height == Screen.height)
            {
                currentResolutionIndex = i;
            }
        }

        resolutionDropdown.AddOptions(options);
        //resolutionDropdown.value = currentResolutionIndex;

        resolutionDropdown.value = PlayerPrefs.GetInt("resolutionIndex");
        resolutionDropdown.RefreshShownValue();

        int qualitySetting = PlayerPrefs.GetInt("Quality");
        qualityDropdown.value = qualitySetting;

        int fullscreenSetting = PlayerPrefs.GetInt("isFullscreen");

        if (fullscreenSetting == 0)
        {
            fullscreenToggle.isOn = false;
        }
        else
        {
            fullscreenToggle.isOn = true;
        }


        float masterVolumeLevel = PlayerPrefs.GetFloat("masterVolume");
        masterVolumeSlider.value = masterVolumeLevel;

        float musicVolumeLevel = PlayerPrefs.GetFloat("musicVolume");
        musicVolumeSlider.value = musicVolumeLevel;
    }

    void OnEnable()
    {
        resolutionDropdown.value = PlayerPrefs.GetInt("resolutionIndex");
        resolutionDropdown.RefreshShownValue();
        int qualitySetting = PlayerPrefs.GetInt("Quality");
        qualityDropdown.value = qualitySetting;

        int fullscreenSetting = PlayerPrefs.GetInt("isFullscreen");

        if (fullscreenSetting == 0)
        {
            fullscreenToggle.isOn = false;
        }
        else
        {
            fullscreenToggle.isOn = true;
        }

        SetQuality(qualityDropdown.value);
        SetFullscreen(fullscreenToggle.isOn);
        SetResolution(resolutionDropdown.value);

        float masterVolumeLevel = PlayerPrefs.GetFloat("masterVolume");
        masterVolumeSlider.value = masterVolumeLevel;

        SetMasterVolume(masterVolumeLevel);

        float musicVolumeLevel = PlayerPrefs.GetFloat("musicVolume");
        musicVolumeSlider.value = musicVolumeLevel;

        SetMusicVolume(musicVolumeLevel);
    }

    void Start()
    {


        SetMasterVolume(masterVolumeSlider.value);
        SetMusicVolume(musicVolumeSlider.value);
        SetQuality(qualityDropdown.value);
        SetFullscreen(fullscreenToggle.isOn);
        resolutionDropdown.value = PlayerPrefs.GetInt("resolutionIndex");
        resolutionDropdown.RefreshShownValue();
        SetResolution(resolutionDropdown.value);
    }

    void Update()
    {
        
    }

    public void SetMasterVolume(float volume)
    {
        audioMixer.SetFloat("masterVolParameter", Mathf.Log10(volume) * 20);
        PlayerPrefs.SetFloat("masterVolume", volume);
    }

    public void SetMusicVolume(float volume)
    {
        audioMixer.SetFloat("musicVolParameter", Mathf.Log10(volume) * 20);
        PlayerPrefs.SetFloat("musicVolume", volume);
        audioMixer.FindMatchingGroups("");
    }

    public void SetQuality(int qualityIndex)
    {
        QualitySettings.SetQualityLevel(qualityIndex);
        PlayerPrefs.SetInt("Quality", qualityIndex);
    }

    public void SetFullscreen(bool isFullscreen)
    {
        Screen.fullScreen = isFullscreen;

        if (isFullscreen)
        {
            PlayerPrefs.SetInt("isFullscreen",1);
        }
        else
        {
            PlayerPrefs.SetInt("isFullscreen", 0);
        }
    }

    public void SetResolution(int resolutionIndex)
    {
        
        Resolution resolution = resolutions[resolutionIndex];
        Screen.SetResolution(resolution.width, resolution.height, Screen.fullScreen);
        PlayerPrefs.SetInt("resolutionIndex",resolutionIndex);
    }


}