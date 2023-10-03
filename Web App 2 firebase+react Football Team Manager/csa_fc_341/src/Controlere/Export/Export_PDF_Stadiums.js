import { jsPDF } from "jspdf";

import "../Stil.css";


var doc = new jsPDF('l', 'mm', [500, 210]);

doc.text("\tNume\t\t\t\t\t\tCapacitate\t\tSuprafata\t\t\t\tAdresa", 20, 10);


export function Export_To_PDF(exp_sorted_date_export){
    exp_sorted_date_export.forEach(function(e, i){
        if(20 + (i * 10) > 200){
            doc.addPage();
            doc.text("\tNume\t\t\t\t\t\tCapacitate\t\tSuprafata\t\t\t\tAdresa", 20, 10);
            i = 0;
        }
        doc.text(
            e.Nume, 20, 20 + (i * 10));
        doc.text(
            e.Capacitate.toString(), 125, 20 + (i * 10));
        doc.text(
            e.Suprafata, 170, 20 + (i * 10));
        doc.text(
            e.Adresa, 240, 20 + (i * 10));
        
    });
    
    doc.save('exported_stadiums.pdf');
}