import * as React from 'react';
import Read_Match from './Controlere/Crud_Matches/Read_Match';


export function Meci() {
  return (
    <div className="main">

      <div className="container_coperta">
        <img src="https://www.freewpheaders.com/wp-content/gallery/football/football-plays-header.jpg" id="poza_coperta"/>
        <div className="centered"> &#x2605;    LISTA MECIURI    &#x2605; </div>
      </div>        

      <div className="pls">
        <Read_Match/>
      </div>

    </div>
  );
}
