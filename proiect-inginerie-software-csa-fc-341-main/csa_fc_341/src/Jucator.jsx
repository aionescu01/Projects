import * as React from 'react';
import Read_Player from './Controlere/Crud_Players/Read_Player';


export function Jucator() {
  return (
    <div className="main">
          
      <div className="container_coperta">
        <img src="https://www.freewebheaders.com/wp-content/gallery/football//world-cup-goal-keeper-sport-website-header.jpg" alt="imagine jucatori" id="poza_coperta"/>
        <div className="centered"> &#x2605;    LISTA JUCATORI    &#x2605; </div>
      </div>  

      <div className="pls">
        <Read_Player/>
      </div>

    </div>
  );
}