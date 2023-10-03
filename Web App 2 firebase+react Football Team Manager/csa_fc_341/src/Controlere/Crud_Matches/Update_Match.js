import React, { useState, useEffect } from 'react';
import { app, auth } from '../../DatabaseConnection';
import { getFirestore, doc, getDoc, updateDoc } from "firebase/firestore";
import { useAuthState } from "react-firebase-hooks/auth";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";


const db = getFirestore(app);
var date ;


export default function Update_Match() {
    const [matchId, setmatchid] = useState();
    const [adversar, setadversar] = useState();
    const [arbitru, setarbitru] = useState();
    const [competitie, setcompetitie] = useState();
    const [data, setdata] = useState();
    const [ora, setora] = useState();
    const [scor, setscor] = useState();

    let navigate = useNavigate();
    
    var id = localStorage.getItem('match_id')
    

    const update = () => {
        
        if(id){
            getDoc(doc(db, "meci", id)).then(docSnap =>{
                date = docSnap.data();

                setmatchid(date.id);
                setadversar(date.adversar);
                setarbitru(date.arbitru);
                setcompetitie(date.competitie);
                setdata(date.data);
                setora(date.ora);
                setscor(date.scor); 
            });
        } else {
            navigate('/');
        }
    }
    

    useEffect(()=>{
        update();
    },[])
    
    const handleSubmit = (event) => {
        const washingtonRef = doc(db, "meci", id);

        updateDoc(washingtonRef, {
            adversar:adversar,
            arbitru:arbitru,
            competitie:competitie,
            data:data,
            ora:ora,
            scor: scor
        });     
        
        event.preventDefault();
        alert(`S-a modificat meciul cu: ${adversar}`);
        window.location.href = "http://localhost:3000/tomeci";
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
                                <h2 className="bt2">Update match</h2>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Adversar</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {adversar}
                                        onChange = {(e) => {
                                            setadversar(e.target.value);
                                        }}
                                        value = {adversar}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Arbitru</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {arbitru}
                                        onChange = {(e) => {
                                            setarbitru(e.target.value);
                                        }}
                                        value = {arbitru}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Competitie</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {competitie}
                                        onChange = {(e) => {
                                            setcompetitie(e.target.value);
                                        }}
                                        value = {competitie}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Data</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {data}
                                        onChange = {(e) => {
                                            setdata(e.target.value);
                                        }}
                                        value = {data}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Ora</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {ora}
                                        onChange = {(e) => {
                                            setora(e.target.value);
                                        }}
                                        value = {ora}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Scor</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {scor}
                                        onChange = {(e) => {
                                            setscor(e.target.value);
                                        }}
                                        value = {scor}
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