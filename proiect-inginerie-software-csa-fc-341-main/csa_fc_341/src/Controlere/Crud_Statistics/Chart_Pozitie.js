import React, { useEffect, useState } from "react";
import { Chart as ChartJS, Tooltip, ArcElement, Legend } from "chart.js";
import { Pie } from "react-chartjs-2";

ChartJS.register(
    Tooltip,
    Legend,
    ArcElement
);


const Chart_Pozitie = () => {
    var [chart, setChart] = useState([]);

    useEffect(() =>{
        setChart(JSON.parse(localStorage.getItem("pozitie")));
    }, []);

    var data = {
        labels: ["Portar", "Fundas", "Mijlocas", "Atacant"],
        datasets: [{
            label: "Pozitiile jucatorilor",
            data: chart,
            backgroundColor: ["#003f5c", "#58508d", "#bc5090", "#00aba9"],
            borderWidth: 1
        }]
    };

    var options = {
        maintainAspectRatio: false,
        legend: {
            labels: {
                fontSize: 26
            }
        }
    }

    return(
        <div>
            <Pie
                data = {data}
                height = {300}
                width = {300}
                options = {options}
            />
        </div>
    )
}

export default Chart_Pozitie;