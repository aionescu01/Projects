import React, { useEffect, useState } from "react";
import { Chart as ChartJS, Tooltip, ArcElement, Legend } from "chart.js";
import { Pie } from "react-chartjs-2";

ChartJS.register(
    Tooltip,
    Legend,
    ArcElement
);


const Chart_Picior = () => {
    var [chart, setChart] = useState([]);

    useEffect(() =>{
        setChart(JSON.parse(localStorage.getItem("picior_preferat")));
    }, []);

    var data = {
        labels: ["Stang", "Drept", "Ambele"],
        datasets: [{
            label: "Picior preferat",
            data: chart,
            backgroundColor: ["#003f5c", "#58508d", "#bc5090"],
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

export default Chart_Picior;