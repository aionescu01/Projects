import { jsPDF } from "jspdf";

import "../Stil.css";


var doc = new jsPDF('l', 'mm', [500, 210]);

doc.text("Nume\t\tPrenume\t\tPicior\t\tPozitie\t\t\t\tNationalitate\tInaltime\tData nasterii", 20, 10);


export function Export_To_PDF(exp_sorted_date_export){
    exp_sorted_date_export.forEach(function(e, i){
        if(20 + (i * 10) > 200){
            doc.addPage();
            doc.text("Nume\t\tPrenume\t\tPicior\t\tPozitie\t\t\t\tNationalitate\tInaltime\tData nasterii", 20, 10);
            i = 0;
        }
        doc.text(
            e.Nume, 20, 20 + (i * 10));
        doc.text(
            e.Prenume, 57.5, 20 + (i * 10));
        doc.text(
            e.Picior, 107.5, 20 + (i * 10));
        doc.text(
            e.Pozitie, 140, 20 + (i * 10));
        doc.text(
            e.Nationalitate, 215, 20 + (i * 10));
        doc.text(
            e.Inaltime.toString(), 262.5, 20 + (i * 10));
        doc.text(
            e.Data_Nastere, 291, 20 + (i * 10));
        
    });
    
    doc.save('exported_players.pdf');
}