using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerAwarenessScript : MonoBehaviour
{
    public bool awareOfPlayer { get; private set; }

    private Vector2 directionToPlayer;

    [SerializeField]
    private float _playerAwarenessDistance;

    private Transform _player;


    private void Awake()
    {
        _player = FindObjectOfType<PlayerController>().transform;

    }

    void Update()
    {
        if(_player!=null) 
        {Vector2 enemyToPlayerVector = _player.position - transform.position;
        directionToPlayer = enemyToPlayerVector.normalized;
        
        if (enemyToPlayerVector.magnitude <= _playerAwarenessDistance)
        {
            awareOfPlayer = true;
        }
        else
        {
            awareOfPlayer = false;
        }
        }
    }
}