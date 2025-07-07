import React, { useState, useEffect } from "react";
import { motion } from "framer-motion";
import './Home.css';
import './Layout.css';
import { NavLink } from "react-router-dom";
import Header from "./Header"
import Footer from "./Footer"

const Home = () => {
  const tablesOLTP = [
    { name: "Angajati", path: "/angajat" },
    { name: "Clienti", path: "/client" },
    { name: "Curse", path: "/cursa" },
    { name: "Detalii curse", path: "/getcursa"},
    { name: "Discount", path: "/discount"},
    { name: "Facturi", path: "/factura" },
    { name: "Locatii", path: "/locatii" },
    { name: "Masini", path: "/masina" },
    { name: "Istoric soferi", path: "/istoricsoferi" },
    { name: "Lucreaza in", path: "/lucreaza"}
  ];

  const tablesNORD = [
    { name: "Angajati Nord", path: "/angajatNord" },
    { name: "Curse Nord", path: "/cursaNord" },
    { name: "Detalii curse Nord", path: "/getcursaNord"},
    { name: "Locatii Nord", path: "/locatiiNord" },
    { name: "Angajati Contact Nord", path: "/angajatContactNord"},
    { name: "Clienți Contact Nord", path: "/clientContactNord" }
  ];

  const tablesSUD = [
    { name: "Angajati Sud", path: "/angajatSud" },
    { name: "Curse Sud", path: "/cursaSud" },
    { name: "Detalii curse Sud", path: "/getcursaSud"},
    { name: "Locatii Sud", path: "/locatiiSud" },
    { name: "Angajati Contact Sud", path: "/angajatContactSud"},
    { name: "Clienți Contact Sud", path: "/clientContactSud" }
  ];

  const tablesCENTRAL = [
    { name: "Angajati Central", path: "/angajatCentral" },
    { name: "Curse Central", path: "/cursaCentral" },
    { name: "Detalii curse Central", path: "/getcursaCentral"},
    { name: "Locatii Central", path: "/locatiiCentral" },
    { name: "Angajați Identity", path: "/angajatIdentity"},
    { name: "Clienți Identity", path: "/clientIdentity" }
  ];

  const tablesARHIVA = [
    { name: "Angajati HR", path: "/angajatHR" },
    { name: "Clienți Profil", path: "/clientProfil" }
  ];

  const images = [
    "/taxi.jpg",
    "/taxi2.jpg",
    "/taxi3.jpg",
  ];

  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 3000);
    return () => clearInterval(interval);
  }, [images.length]);

  return (
    <div className="page-container">
      <Header />
      <main className="main-content MuiContainer-root">
        <motion.h1
          className="title"
          initial={{ opacity: 0, scale: 0.8 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ duration: 1 }}
        >
          Ride Best
        </motion.h1>

        <motion.p
          className="subtitle"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.5, duration: 1 }}
        >
          Bine ai venit la platforma noastră! Explorează serviciile și rapoartele oferite.
        </motion.p>


        <div className="carousel">
          <img src={images[currentIndex]} alt={`Slide ${currentIndex + 1}`} className="carousel-image" />
        </div>

        {/* Carduri OLTP*/}
        <div className="card-container">
          {tablesOLTP.map((table) => (
            <div key={table.name} className="card">
              <h2>{table.name}</h2>
              <NavLink to={table.path} className="button">Vezi detalii</NavLink>
            </div>
          ))}
        </div>

        <br/>

        <div className="card-subcontainer">
          {tablesNORD.map((table) => (
            <div key={table.name} className="card">
              <h2>{table.name}</h2>
              <NavLink to={table.path} className="button">Vezi detalii</NavLink>
            </div>
          ))}
        </div>

        <br/>

        <div className="card-subcontainer">
          {tablesSUD.map((table) => (
            <div key={table.name} className="card">
              <h2>{table.name}</h2>
              <NavLink to={table.path} className="button">Vezi detalii</NavLink>
            </div>
          ))}
        </div>

        <br/>

        <div className="card-subcontainer">
          {tablesCENTRAL.map((table) => (
            <div key={table.name} className="card">
              <h2>{table.name}</h2>
              <NavLink to={table.path} className="button">Vezi detalii</NavLink>
            </div>
          ))}
        </div>

        <br/>

        <div className="card-subcontainer">
          {tablesARHIVA.map((table) => (
            <div key={table.name} className="card">
              <h2>{table.name}</h2>
              <NavLink to={table.path} className="button">Vezi detalii</NavLink>
            </div>
          ))}
        </div>
        
      </main>
      <Footer />
    </div>
  );
};

export default Home;