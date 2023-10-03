import { getFirestore, collection, getDocs } from "firebase/firestore";
import { app } from '../../DatabaseConnection';
import React, { useEffect } from 'react';
import Chart_Picior from "./Chart_Picior";
import Chart_Pozitie from "./Chart_Pozitie";
import Chart_Varsta from "./Chart_Varsta";

import '../Home.css';
import "../Stil.css";

const db = getFirestore(app);
let response = collection(db, 'jucator');

const months = {
  Jan: '01',
  Feb: '02',
  Mar: '03',
  Apr: '04',
  May: '05',
  Jun: '06',
  Jul: '07',
  Aug: '08',
  Sep: '09',
  Oct: '10',
  Nov: '11',
  Dec: '12',
}

function add(acc, a){
  return acc+a;
}


export async function fetchJucatori(){
  try{
    await getDocs(response)
    .then((querySnapshot) => {

      let picior_preferat = [0, 0, 0];  //array for Chart_picior(stanga, dreapta, ambele)
      let pozitie = [0, 0, 0, 0];       //array for player positions(GK, DEF, MID, ATT)
      let catVarsta = [0, 0, 0, 0];     //array for player ages categories(-20, 21-25, 26-30, 31+)
      let varste_juc = [];              //array for player ages
      let inaltime_juc = [];            //array for player heights

      querySnapshot.forEach(element => {

        var date = element.data();

        if(date?.picior === "Stang"){
          picior_preferat[0]+=1;
        } else if(date?.picior === "Drept"){
          picior_preferat[1]+=1;
        } else {
          picior_preferat[2]+=1;
        }

        if(date?.pozitie.substring(0,10) === "Goalkeeper"){
          pozitie[0]+=1;
        } else if(date?.pozitie.substring(0,8) === "Defender"){
          pozitie[1]+=1;
        } else if(date?.pozitie.substring(0,6) === "Attack"){
          pozitie[3]+=1;
        } else{
          pozitie[2]+=1;
        }

        const [month, day, year] = date.data_nastere.split(/[, ]+/);
        const data_nastere_jucator = new Date(+year, months[month]-1, +day);
        const varsta_jucator = Math.abs(new Date().getFullYear() - data_nastere_jucator.getFullYear());
        
        if(varsta_jucator <= 20){
          catVarsta[0]+=1;
        } else if(varsta_jucator <= 25){
          catVarsta[1]+=1;
        } else if(varsta_jucator <= 30){
          catVarsta[2]+=1;
        } else{
          catVarsta[3]+=1;
        }

        varste_juc.push(varsta_jucator);
        inaltime_juc.push(JSON.parse(date.inaltime));
      });
      
      localStorage.setItem("picior_preferat", JSON.stringify(picior_preferat));
      localStorage.setItem("pozitie", JSON.stringify(pozitie));
      localStorage.setItem("varsta", JSON.stringify(catVarsta));
      localStorage.setItem("varsta_medie", JSON.stringify((varste_juc.reduce(add, 0))/varste_juc.length));
      localStorage.setItem("inaltime_medie", JSON.stringify((inaltime_juc.reduce(add, 0))/inaltime_juc.length));
    })
    .catch((e) => console.log(e));
  }
  catch(e){
    console.log(e);
  }
}

function Read_Statistics(){

  useEffect(() =>{
    fetchJucatori();
    
    if(localStorage.getItem("reloadedPage") === null){
      setTimeout(window.location.reload(), 100);
      localStorage.setItem("reloadedPage", true);
    } else {
      localStorage.removeItem("reloadedPage");
    }
  });

  return(
    <div>
      <div className="divHome divStats">
        <div>

          <h1> Picior Preferat </h1>

          <Chart_Picior/>
        </div>
        <div>

          <h1> Pozitie </h1>

          <Chart_Pozitie/>
        </div>
        <div>
          
          <h1> Varsta </h1>

          <Chart_Varsta/>
        </div>
      </div>
      <div className="divHome divStatsExtra">
        <div>
          <ol>
            <li>
              <h1>
                Varsta medie : {Math.round(JSON.parse(localStorage.getItem("varsta_medie")) * 100) / 100} ani.
              </h1>
            </li>
            <li>
              <h1>
                Inaltime medie : {Math.round(JSON.parse(localStorage.getItem("inaltime_medie")) * 100) / 100} cm.
              </h1>
            </li>
          </ol>
        </div>
      </div>
    </div>
  );
}

export default Read_Statistics;
