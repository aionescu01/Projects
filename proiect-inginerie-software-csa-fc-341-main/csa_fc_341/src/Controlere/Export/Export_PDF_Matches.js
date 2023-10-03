import { jsPDF } from "jspdf";

import "../Stil.css";


var doc = new jsPDF('l', 'mm', [500, 210]);

doc.text("\tCompetitie\t\t\t\tData\t\tOra\tAdversar\t\tArbitru\t\t\tScor", 20, 10);


export function Export_To_PDF(exp_sorted_date_export){
    exp_sorted_date_export.forEach(function(e, i){
        if(20 + (i * 10) > 200){
            doc.addPage();
            doc.text("\tCompetitie\t\t\t\tData\t\tOra\tAdversar\t\tArbitru\t\t\tScor", 20, 10);
            i = 0;
        }
        doc.text(
            e.Competitie, 20, 20 + (i * 10));
        doc.text(
            e.Data, 100, 20 + (i * 10));
        doc.text(
            e.Ora, 145, 20 + (i * 10));
        doc.text(
            e.Adversar, 168, 20 + (i * 10));
        doc.text(
            e.Arbitru, 210, 20 + (i * 10));
        doc.text(
            e.Scor, 272, 20 + (i * 10));
        
    });
    
    doc.save('exported_matches.pdf');
}