import React, { useState, useEffect } from 'react'
import { Form } from 'semantic-ui-react'
import { getFirestore, collection, doc, getDoc, addDoc } from "firebase/firestore";
import { app, auth } from '../../DatabaseConnection';
import { useAuthState } from "react-firebase-hooks/auth";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";


const db = getFirestore(app);


export default function Create_Staff() {
    const [nume, setnume] = useState('');
    const [prenume, setprenume] = useState('');
    const [data_nastere, setdata_nastere] = useState('');
    const [pozitie, setpozitie] = useState('');
    const [picior, setpicior] = useState('');
    const [nationalitate, setnationalitate] = useState('');
    const [inaltime, setinaltime] = useState(0);

    async function add_player(event) {
        
        var a = await addDoc(collection(db, "jucator"), {
            nume: nume,
            prenume: prenume,
            data_nastere: data_nastere,
            pozitie: pozitie,
            picior: picior,
            nationalitate: nationalitate,
            inaltime: inaltime
        }).then(alert(`The player you added is: ${nume}`));
          
        await Promise.all([a]);
        window.location.href = "http://localhost:3000/tojucator";
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
                            
                            <h2 className="bt2">Add a player</h2>
                            
                            <Box
                                className = "field"
                                sx = {{ display: 'flex', alignItems: 'flex-start' }}
                            >
                                <label className='scris'>Nume</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = 'Nume'
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
                                    placeholder = 'Prenume'
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
                                <label className='scris'>Data nastere</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = 'Data nastere'
                                    onChange = {(e) => {
                                        setdata_nastere(e.target.value);
                                    }}
                                    value = {data_nastere}
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
                                    placeholder = 'Pozitie'
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
                                <label className='scris'>Picior</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = 'Picior'
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
                                <label className='scris'>Nationalitate</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = 'Nationalitate'
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
                                    placeholder = 'Inaltime'
                                    onChange = {(e) => {
                                        setinaltime(e.target.value);
                                    }}
                                    value = {inaltime}
                                />
                            </Box>
                            
                            <Button className='bt2' onClick={add_player} type = 'submit'>Submit</Button>

                        </Form>
                    :
                        <></>
            }
        </div>
    )
}