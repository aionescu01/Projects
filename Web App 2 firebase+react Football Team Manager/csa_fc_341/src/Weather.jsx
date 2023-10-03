import { useState, useEffect } from "react";
import { Typography } from "@mui/material";
import { getFirestore, doc, getDoc} from "firebase/firestore";
import { app } from "./DatabaseConnection";

import './Weather.css';

const cities = {
    "Bucuresti": "Bucharest",
    "Constanta": "Constanta",
    "Cluj-Napoca": "Cluj",
    "Craiova": "Craiova",
    "Sfantu Gheorghe": "Sfantu Gheorghe",
    "Ploiesti": "Ploiesti",
    "Voluntari": "Voluntari",
    "Sibiu": "Sibiu",
    "Pitesti": "Pitesti",
    "Botosani": "Botosani",
    "Arad": "Arad",
    "Mioveni": "Mioveni",
    "Antalya": "Antalya",
    "Medias": "Medias",
};

let data = "";


export function Weather(oras) {
    const [weather, setWeather] = useState({});

    const db = getFirestore(app);

    let api = {
        key: "",
        base: "https://api.openweathermap.org/data/2.5/",
    }

    async function getKey() {
        const docRef = doc(db, "api-keys", "open-weather-api");
        const snapshot = await getDoc(docRef);
        api.key = await snapshot.data()['key'];

        let current = new Date();
        let matchDate = new Date();

        let dataList = localStorage.getItem("data").split(",");

        for(let i=0; i<dataList.length; i++){
            dataList[i] = parseInt(dataList[i]);
        }

        matchDate.setFullYear(dataList[0]);
        matchDate.setMonth(dataList[1] - 1);
        matchDate.setDate(dataList[2]);
        matchDate.setHours(dataList[3]);
        matchDate.setMinutes(dataList[4]);
        matchDate.setSeconds(0);
        matchDate.setMilliseconds(0);

        const daysDif = Math.floor((matchDate.getTime() - current.getTime()) / (1000 * 3600 * 24));

        if(daysDif >= 0 && daysDif < 5)
            searchPressedForecast();
    }

    useEffect (() => {
        // eslint-disable-next-line react-hooks/exhaustive-deps
        oras = oras.oras;
        data = localStorage.getItem("dataString");
        getKey();
    }, [oras]);

    // vremea acum
    // const searchPressed = () => {
    //     fetch(`${api.base}weather?q=${search}&units=metric&APPID=${api.key}&lang=ro`)
    //         .then((res) => res.json())
    //         .then((result) => {
    //             setWeather(result);
    //             console.log(result);
    //         });
    // };

    // forecast pe 5 zile pt vreme
    const searchPressedForecast = () => {
        fetch(`${api.base}forecast?q=${cities[oras]}&units=metric&APPID=${api.key}&lang=ro`)
            .then((res) => res.json())
            .then((result) => {
                let filteredResult = result?.list?.filter(
                    obj => obj.dt_txt === data
                )[0];
                if (filteredResult !== undefined){
                    filteredResult.name = result?.city?.name;
                    setWeather(filteredResult);
                }
            });
    };

    return (
        <div>
            { 
                weather?.name !== undefined
                    ?
                        <div className="weather">

                                <div className="weather-widget">
                                    <h4>Prognoza meteo</h4>
                                    
                                    <Typography variant="body1">
                                        {weather?.name}
                                    </Typography>

                                    <br></br>

                                    <div>
                                        {typeof weather.main === 'undefined' 
                                            ? null 
                                            :   <Typography variant="body1">
                                                    {Math.round(weather?.main?.temp)}°C
                                                </Typography>
                                        }
                                    </div>

                                    <div>
                                        {typeof weather.main === 'undefined' 
                                            ? null 
                                            :   <Typography variant="body2">
                                                    {weather?.weather[0]?.description}
                                                </Typography>
                                        }
                                    </div>

                                    <br></br>

                                    <div className="bottom">
                                        <div>
                                            {typeof weather.main === 'undefined' 
                                                ? null 
                                                :   <div>
                                                        <Typography variant="body2">
                                                            {Math.round(weather?.main?.feels_like)}°C<br/>
                                                            Feels Like
                                                        </Typography>
                                                    </div>
                                            }
                                        </div>
                                        <div>
                                            {typeof weather.main === 'undefined' 
                                                ? null 
                                                :   <div>
                                                        <Typography variant="body2">
                                                            {Math.round(weather?.main?.humidity)}%<br/>
                                                            Umiditate
                                                        </Typography>
                                                    </div>
                                            }
                                        </div>
                                        <div>
                                            {typeof weather.main === 'undefined' 
                                                ? null 
                                                :   <div>
                                                        <Typography variant="body2">
                                                            {Math.round(weather?.wind?.speed)}Km/h<br/>
                                                            Viteza vant
                                                        </Typography>
                                                    </div>
                                            }
                                        </div>
                                    </div>
                                </div>
                            
                        </div>
                    : 
                        <div className="weatheroff"></div>
            }
        </div>
    );
}