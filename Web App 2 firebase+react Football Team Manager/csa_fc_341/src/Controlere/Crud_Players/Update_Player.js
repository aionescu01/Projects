import React, { useState,useEffect } from 'react';
import { app, auth } from '../../DatabaseConnection';
import { getFirestore, doc, getDoc, updateDoc } from "firebase/firestore";
import { useAuthState } from "react-firebase-hooks/auth";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";


const db = getFirestore(app);
var date;


export default function Update_Player() {
    const [playerId, setplayerid] = useState();
    const [nume, setnume] = useState();
    const [prenume, setprenume] = useState();
    const [picior, setpicior] = useState();
    const [pozitie, setpozitie] = useState();
    const [nationalitate, setnationalitate] = useState();
    const [inaltime, setinaltime] = useState();
    const [data_nastere, setdata_nastere] = useState();

    let navigate = useNavigate();

    var id = localStorage.getItem('player_id');


    const update = () =>{

        if(id){
            getDoc(doc(db, "jucator", id)).then(docSnap =>{
                date = docSnap.data();
                
                setplayerid(date.id);
                setnume(date.nume);
                setprenume(date.prenume);
                setpicior(date.picior);
                setpozitie(date.pozitie);
                setnationalitate(date.nationalitate);
                setinaltime(date.inaltime);
                setdata_nastere(date.data_nastere);
            });
        } else {
            navigate('/');
        }
    }
    


    useEffect(()=>{
        update();
      },[])
    
    const handleSubmit = (event) => {
        const washingtonRef = doc(db, "jucator", id);

        updateDoc(washingtonRef, {
            nume: nume,
            prenume: prenume,
            picior: picior,
            pozitie: pozitie,
            nationalitate: nationalitate,
            inaltime: inaltime,
            data_nastere: data_nastere

        });     
        
        event.preventDefault();
        alert(`S-a modificat jucatorul: ${nume}`);
        window.location.href = "http://localhost:3000/tojucator";
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

                                <h2 className="bt2">Update player</h2>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Nume</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {nume}
                                        onChange = {(e) => {
                                            setnume(e.target.value);
                                        }}
                                        value = {nume}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Prenume</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {prenume}
                                        onChange = {(e) => {
                                            setprenume(e.target.value);
                                        }}
                                        value = {prenume}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Picior</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {picior}
                                        onChange = {(e) => {
                                            setpicior(e.target.value);
                                        }}
                                        value = {picior}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Pozitie</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {pozitie}
                                        onChange = {(e) => {
                                            setpozitie(e.target.value);
                                        }}
                                        value = {pozitie}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Nationalitate</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {nationalitate}
                                        onChange = {(e) => {
                                            setnationalitate(e.target.value);
                                        }}
                                        value = {nationalitate}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Inaltime</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {inaltime}
                                        onChange = {(e) => {
                                            setinaltime(e.target.value);
                                        }}
                                        value = {inaltime}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Data nasterii</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {data_nastere}
                                        onChange = {(e) => {
                                            setdata_nastere(e.target.value);
                                        }}
                                        value = {data_nastere}
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