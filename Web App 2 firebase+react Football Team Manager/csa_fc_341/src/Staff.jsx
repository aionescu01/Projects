import * as React from 'react';
import Read_Staff from './Controlere/Crud_Staffs/Read_Staff';


export function Staff() {
  return (
    <div className="main">

      <div className="container_coperta">
        <img src="https://www.freewebheaders.com/wp-content/gallery/office-finance/business-legal-services-website-header.jpg" alt="poza staff" id="poza_coperta"/>
        <div className="centered"> &#x2605;    MEMBRII STAFF    &#x2605; </div>
      </div>        
   
      <div className="pls">
        <Read_Staff/>
      </div>

    </div>
  );
}
