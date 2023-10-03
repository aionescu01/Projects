import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router, Navigate, Route, Routes } from "react-router-dom";

import './index.css';

import { Stadion } from './Stadion';
import { Meci } from './Meci';
import { Jucator } from './Jucator';
import { Weather } from './Weather';
import { Staff } from './Staff';
import { Contract } from './Contract';
import { Statistics } from './Statistics';

import Add_Stadium from './Controlere/Crud_Stadiums/Add_Stadium'
import Add_Player from './Controlere/Crud_Players/Add_Player'
import Add_Staff from './Controlere/Crud_Staffs/Add_Staff'
import Add_Match from './Controlere/Crud_Matches/Add_Match'
import Add_Contract from './Controlere/Crud_Contracts/Add_Contract'

import Update_Match from './Controlere/Crud_Matches/Update_Match';
import Update_Contract from './Controlere/Crud_Contracts/Update_Contract'
import Update_Stadium from './Controlere/Crud_Stadiums/Update_Stadium';
import Update_Staff from './Controlere/Crud_Staffs/Update_Staff';
import Update_Player from './Controlere/Crud_Players/Update_Player';

import Navigation  from "./Controlere/Navigation";
import Home from "./Controlere/Home";
import Match from './Match';
import Auth from './Auth';


const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <Router>
    
    <Navigation />

    <Routes>

      {/* Ruta pentru pagina de Home */}
      <Route path="/" element={<Home />} />

      {/* Ruta pentru pagina de Autentificare */}
      <Route path="/toauth" element={<Auth />} />

      {/* Ruta pentru pagina de Stadioane */}
      <Route path="/tostadion" element={<Stadion />} />
      <Route path="/toweather" element={<Weather />} />
      <Route path="/update_stadium" element={<Update_Stadium />} />
      <Route path="/add_stadium" element={<Add_Stadium />} />

      {/* Ruta pentru pagina de Meciuri */}
      <Route path="/tomeci" element={<Meci />} />
      <Route path="/update_match" element={<Update_Match />} />
      <Route path="/add_match" element={<Add_Match />} />

      {/* Ruta pentru pagina fiecarui meci */}
      <Route path="/tomeci/meci/:id" element={<Match/>}/>


      {/* Ruta pentru pagina de Jucatori */}
      <Route path="/tojucator" element={<Jucator />} />
      <Route path="/update_player" element={<Update_Player />} />
      <Route path="/add_player" element={<Add_Player />} />


        {/* Ruta pentru pagina de Staff */}
      <Route path="/tostaff" element={<Staff />} />
      <Route path="/update_staff" element={<Update_Staff />} />
      <Route path="/add_staff" element={<Add_Staff />} />

       {/* Ruta pentru pagina de Contracte */}
      <Route path="/tocontract" element={<Contract />} />
      <Route path="/update_contract" element={<Update_Contract />} />
      <Route path="/add_contract" element={<Add_Contract />} />

      {/* Ruta pentru pagina de Statistici */}
      <Route path="/tostatistics" element={<Statistics />} />

      {/* Ruta default */}
      <Route path="*" element={<Navigate to="/" />} />
      
    </Routes>

  </Router>
);
