import React, { useState } from 'react';
import { CSVLink } from 'react-csv';
import { Export_To_PDF } from "./Export_PDF_Matches";

import "../Stil.css";


export function ExportExcel(date_export){
    let [sorted_date_export, SetDate] = useState([]);

    date_export.date_export.forEach(e=>{
        var aux = {
            Competitie: e["competitie"],
            Data: e["data"],
            Ora: e["ora"],
            Adversar: e["adversar"],
            Arbitru: e["arbitru"],
            Scor: e["scor"],
        }

        if(sorted_date_export.length !== date_export.date_export.length){
            sorted_date_export.push(aux);
            SetDate(sorted_date_export);            
        }
    })

    return(
        <div className="export">
            <button>
                <CSVLink data = {sorted_date_export} filename="exported_matches" className="buton" style={{color: "white", textDecoration: 'none'}}>Export to Excel</CSVLink>
            </button>
            <button className="buton" onClick={()=>Export_To_PDF(sorted_date_export)}>Export to PDF</button>
        </div>
    );
}