using System.Collections;
using System.Collections.Generic;
using Pathfinding;
using Unity.VisualScripting;
using UnityEngine;

public class SpawnEnemies : MonoBehaviour
{

    public float y_min;
    public float y_max;
    public float x_min;
    public float x_max;

    public GameObject enemy1prefab;
    public GameObject enemy2prefab;
    public GameObject enemy3prefab;
    public GameObject player;
    private GameObject obj;
    private GameObject rb;
    private float timer;
    public float spawnRate;
    public int enemyMaxNumber;
    public int enemiesSpawned;
    public bool allEnemiesSpawned = false;
    void Start()
    {
        player = GameObject.FindGameObjectWithTag("Player");
        enemiesSpawned = 0;
        allEnemiesSpawned = false;
    }

    // Update is called once per frame
    void Update()
    {

        //Debug.Log(player);

        if(player.IsDestroyed())
            gameObject.SetActive(false);
        timer += Time.deltaTime;
        //if (Input.GetKeyDown(KeyCode.Space))
        if(timer > spawnRate && (enemiesSpawned < enemyMaxNumber || enemyMaxNumber==-1))
        {   
            timer = 0;

            enemiesSpawned++;


            SpawnEnemy();

        }

        if (enemiesSpawned >= enemyMaxNumber)
        {
            allEnemiesSpawned = true;
        }
    }

    void SpawnEnemy()
    {
        Vector3 randomSpawnPos = new Vector3(Random.Range(x_max, x_min), Random.Range(y_max, y_min));


        randomSpawnPos = (Vector3)AstarPath.active.data.graphs[0].GetNearestForce(randomSpawnPos, NNConstraint.Default).node.position;


        int nr = Random.Range(1, 6);
        if (nr == 1 || nr ==2)
        {
            obj = Instantiate(enemy1prefab, randomSpawnPos, Quaternion.identity);
            rb = obj.gameObject.transform.GetChild(0).gameObject;
        }
        else if(nr==3 || nr == 4)
        {
            obj = Instantiate(enemy2prefab, randomSpawnPos, Quaternion.identity);
            rb = obj.gameObject.transform.GetChild(0).gameObject;

            rb.GetComponent<Enemy2Script>().player = player;
            rb.GetComponent<Enemy2Script>().weapon.parent = rb.GetComponent<Enemy2Script>().player;

        }
        else
        {
            obj = Instantiate(enemy3prefab, randomSpawnPos, Quaternion.identity);
            rb = obj.gameObject.transform.GetChild(0).gameObject;

            rb.GetComponent<Enemy2Script>().player = player;
            rb.GetComponent<Enemy2Script>().weapon.parent = rb.GetComponent<Enemy2Script>().player;
        }
        rb.GetComponent<AIDestinationSetter>().target = player.transform;
    }

}
