import React, { useState,useEffect } from 'react';
import { app, auth } from '../../DatabaseConnection';
import { getFirestore, doc, getDoc, updateDoc } from "firebase/firestore";
import { useAuthState } from "react-firebase-hooks/auth";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";


const db = getFirestore(app);
var date;


export default function Update_Stadium() {
    const [stadiumId, setstadiumid] = useState();
    const [name, setname] = useState();
    const [capacity, setcapacity] = useState();
    const [surface, setsurface] = useState();
    const [address, setaddress] = useState();

    let navigate = useNavigate();

    var id = localStorage.getItem('stadium_id');
    
    
    const update=()=>{
        
        if(id){
            getDoc(doc(db, "stadion", id)).then(docSnap =>{
                
                date = docSnap.data(); 
                
                setstadiumid(date.id)
                setname(date.denumire);
                setcapacity(date.capacitate);
                setsurface(date.tip_gazon)
                setaddress(date.adresa);
                
            });
        } else {
            navigate("/");
        }
    }

    useEffect(()=>{
        update();
    },[])
    
    const handleSubmit = (event) => {
        const washingtonRef = doc(db, "stadion", id);

        updateDoc(washingtonRef, {
            denumire: name,
            adresa: address,
            capacitate: capacity,
            tip_gazon: surface
        });
        
        event.preventDefault();
        alert(`The stadium you updated was: ${name}`);
        window.location.href = "http://localhost:3000/tostadion";
    }
    
    const [user, loading, error] = useAuthState(auth);
    const [rol_user, setRol_user] = useState("");


    async function get_detalii_user(docID){
        const ref = doc(db, "users", docID);

        await getDoc(ref)
        .then(async (response) => {
            let res = response.data();
            
            setRol_user(res.rol);
            if(res.rol !== "admin" && res.rol !== "staff"){
                return navigate("/");
            }
        })
        .catch((e) => console.log(e));
    }

    useEffect(() => {
        if (loading){
            return;
        } else if(user){
            get_detalii_user(user.uid);
        } else {
            return navigate("/");
        }
    }, [loading, user]);
    

    return (
        <div className='form'>
            {
                rol_user !== ""
                    ?
                        <form className='create-form1' onSubmit={handleSubmit}>
                            
                            <div>

                                <h2 className="bt2">Update stadium</h2>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Nume</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {name}
                                        onChange = {(e) => {
                                            setname(e.target.value);
                                        }}
                                        value = {name}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Capacitate</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {capacity}
                                        onChange = {(e) => {
                                            setcapacity(e.target.value);
                                        }}
                                        value = {capacity}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Suprafata</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {surface}
                                        onChange = {(e) => {
                                            setsurface(e.target.value);
                                        }}
                                        value = {surface}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Adresa</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {address}
                                        onChange = {(e) => {
                                            setaddress(e.target.value);
                                        }}
                                        value = {address}
                                    />
                                </Box>
                            
                            </div>

                            <Button className='b1' type='submit' >Update</Button>
                            
                        </form>
                    :
                        <></>
            }
        </div>
    )
}