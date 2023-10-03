import React, { useState, useEffect } from 'react'
import { Form } from 'semantic-ui-react'
import { getFirestore, collection, addDoc, doc, getDoc } from "firebase/firestore";
import { app, auth } from '../../DatabaseConnection';
import { useAuthState } from "react-firebase-hooks/auth";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";


const db = getFirestore(app);


export default function Create_Match() {
    const [adversar, setadversar] = useState('');
    const [arbitru, setarbitru] = useState('');
    const [competitie, setcompetitie] = useState('');
    const [data, setdata] = useState('');
    const [ora, setora] = useState('');
    const [scor, setscor] = useState('');

    async function add_match(event) {
        
        var a = await addDoc(collection(db, "meci"), {
            adversar: adversar,
            arbitru: arbitru,
            competitie: competitie,
            data: data,
            ora: ora,
            scor: scor
        }).then(alert(`The match you added is: ${adversar}`));
        
        await Promise.all([a]);
        window.location.href = "http://localhost:3000/tomeci";
    }

    const [user, loading, error] = useAuthState(auth);
    const [rol_user, setRol_user] = useState("");

    let navigate = useNavigate();


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
                        <Form className="create-form1">

                            <h2 className="bt2">Add a match</h2>
                            
                            <Box
                                className = "field"
                                sx = {{ display: 'flex', alignItems: 'flex-start' }}
                            >
                                <label className='scris'>Adversar</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = 'Adversar'
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
                                    placeholder = 'Arbitru'
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
                                    placeholder = 'Competitie'
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
                                    placeholder = 'Data'
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
                                    placeholder = 'Ora'
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
                                    placeholder = 'Scor'
                                    onChange = {(e) => {
                                        setscor(e.target.value);
                                    }}
                                    value = {scor}
                                />
                            </Box>
                            
                            <Button className='bt2' onClick={add_match} type = 'submit'>Submit</Button>
                            
                        </Form>
                    :
                        <></>
            }
        </div>
    )
}