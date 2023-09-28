using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class CharacterPickMenu : MonoBehaviour
{
    private GameManager gameManager;
    public Sprite[] blueMan;
    public Sprite[] brownMan;
    public TextMeshProUGUI blueButtonText;
    public TextMeshProUGUI brownButtonText;
    public string character;
    void Start()
    {
        gameManager = GameObject.FindGameObjectWithTag("GameManager").GetComponent<GameManager>();
        character = PlayerPrefs.GetString("characterName");
        //if (character == "Blue Man")
        //{
        //    gameManager.ChooseBlueMan();
        //    blueButtonText.text = "SELECTED";
        //}
        //else
        //    blueButtonText.text = "SELECT";

        //if (character == "Brown Man")
        //{
        //    gameManager.ChooseBrownMan();
        //    brownButtonText.text = "SELECTED";
        //}
        //else
        //    brownButtonText.text = "SELECT";
    }

    void Update()
    {
        if (gameManager.name == "Blue Man")
        {
            //gameManager.ChooseBlueMan();
            blueButtonText.text = "SELECTED";
        }
        else
            blueButtonText.text = "SELECT";

        if (gameManager.name == "Brown Man")
        {
            //gameManager.ChooseBrownMan();
            brownButtonText.text = "SELECTED";
        }
        else
            brownButtonText.text = "SELECT";
    }

    

    //public void ChooseBlueMan()
    //{
        
    //    gameManager.name = "Blue Man";
    //    PlayerPrefs.SetString("characterName", gameManager.name);
    //    gameManager.health = 150f;
    //    gameManager.moveSpeed = 5f;
    //    gameManager.damageMultiplier = 1f;
    //    //gameManager.fists = true;
    //    gameManager.pistolMagAmmo = 12;
    //    gameManager.pistolMaxAmmo = -1;
    //    gameManager.smgMagAmmo = 50;
    //    gameManager.smgMaxAmmo = 150;
    //    gameManager.shotgunMagAmmo = 7;
    //    gameManager.shotgunMaxAmmo = 42;
    //    gameManager.character = blueMan;
    //    bool[] weaponsEnabled = { true, true, true, true };
    //    gameManager.weaponsEnabled = weaponsEnabled;
    //    blueButtonText.text = "SELECTED";
    //    brownButtonText.text = "SELECT";
    //}

    //public void ChooseBrownMan()
    //{
    //    gameManager.name = "Brown Man";
    //    PlayerPrefs.SetString("characterName", gameManager.name);
    //    gameManager.health = 100f;
    //    gameManager.moveSpeed = 7f;
    //    gameManager.damageMultiplier = 0.9f;
    //    //gameManager.fists = false;
    //    gameManager.pistolMagAmmo = 12;
    //    gameManager.pistolMaxAmmo = -1;
    //    gameManager.smgMagAmmo = 50;
    //    gameManager.smgMaxAmmo = 100;
    //    gameManager.shotgunMagAmmo = 7;
    //    gameManager.shotgunMaxAmmo = 35; 
    //    gameManager.character = brownMan;
    //    bool[] weaponsEnabled = { false, true, true, true };
    //    gameManager.weaponsEnabled = weaponsEnabled;
    //    blueButtonText.text = "SELECT";
    //    brownButtonText.text = "SELECTED";
    //}

}
