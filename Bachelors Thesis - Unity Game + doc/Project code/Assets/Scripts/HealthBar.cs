using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class HealthBar : MonoBehaviour
{
    public Gradient gradient;
    public Image fill;
    private float maxHp;
    private float currentHp;

    public void SetMaxHealth(float health)
    {
        currentHp = health;
        maxHp = health;

        fill.color = gradient.Evaluate(1f);
    }
    public void SetHealth(float health)
    {
        currentHp = health;

        fill.color = gradient.Evaluate(currentHp / maxHp);
        fill.fillAmount = currentHp / maxHp;
    }
}
