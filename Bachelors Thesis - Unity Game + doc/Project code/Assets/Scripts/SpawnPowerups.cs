using Pathfinding;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SpawnPowerups : MonoBehaviour
{
    public GameController controller;
    private bool hasSpawned = false;
    private int oldScore = 0;
    public int scoreMultiple = 5;
    public float x = 19;
    public float y = 9;
    public float y_min;
    public float y_max;
    public float x_min;
    public float x_max;
    public GameObject powerup1prefab;
    public GameObject powerup2prefab;
    public GameObject powerup3prefab;
    public GameObject ammoPowerupPrefab;
    public GameObject player;
    private GameObject obj;
    

    void Start()
    {
        controller = GameObject.FindGameObjectWithTag("GameController").GetComponent<GameController>();
        player = GameObject.FindGameObjectWithTag("Player");
    }

    void Update()
    {
        if (controller.score % scoreMultiple == 0 && controller.score > 0  && controller.score != oldScore)
        {
            hasSpawned = true;
            oldScore = controller.score;
            SpawnAmmo();
            //Vector3 randomSpawnPos = new Vector3(Random.Range(x, -x), Random.Range(y, -y));
            Vector3 randomSpawnPos = new Vector3(Random.Range(x_max, x_min), Random.Range(y_max, y_min));
            randomSpawnPos = (Vector3)AstarPath.active.data.graphs[0].GetNearestForce(randomSpawnPos, NNConstraint.Default).node.position;
            int nr = Random.Range(1, 4);
            if (nr == 1)
            {
                Instantiate(powerup1prefab, randomSpawnPos, Quaternion.identity);
            }
            else
                if(nr==2)
            {
                Instantiate(powerup2prefab, randomSpawnPos, Quaternion.identity);
                //obj.GetComponent<Enemy2Script>().player = player;
                //obj.GetComponent<Enemy2Script>().weapon.parent = obj.GetComponent<Enemy2Script>().player;
            }else {
                    Instantiate(powerup3prefab, randomSpawnPos, Quaternion.identity);
            }
        }
        
    }

    void SpawnAmmo()
    {
        Vector3 randomSpawnPos = new Vector3(Random.Range(x_max, x_min), Random.Range(y_max, y_min));
        randomSpawnPos = (Vector3)AstarPath.active.data.graphs[0].GetNearestForce(randomSpawnPos, NNConstraint.Default).node.position;
        Instantiate(ammoPowerupPrefab, randomSpawnPos, Quaternion.identity);
    }
}
