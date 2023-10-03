import * as React from 'react';
import Read_Stadium from './Controlere/Crud_Stadiums/Read_Stadium';
import './Stadion.css'; 


export function Stadion() {
  return (
    <div className="main">
      
      <div className="container_coperta">
        <img src="https://www.freewebheaders.com/wp-content/gallery/football/awseme-covered-football-stadium-website-header.jpg" alt="poza stadion" id="poza_coperta"/>
        <div className="centered"> &#x2605;    LISTA STADIOANE    &#x2605; </div>
      </div>        
    
      <div className="pls">
        <Read_Stadium/>
      </div>

    </div>
  );
}
