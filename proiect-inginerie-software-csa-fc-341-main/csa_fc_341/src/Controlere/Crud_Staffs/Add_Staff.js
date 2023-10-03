import React, { useState, useEffect } from 'react'
import { Form } from 'semantic-ui-react'
import { getFirestore, collection, doc, getDoc, addDoc} from "firebase/firestore";
import { app, auth } from '../../DatabaseConnection';
import { useNavigate } from "react-router-dom";
import { useAuthState } from "react-firebase-hooks/auth";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";


const db = getFirestore(app);


export default function Create_Staff() {
    const [nume, setnume] = useState('');
    const [prenume, setprenume] = useState('');
    const [data_nastere, setdata_nastere] = useState('');
    const [rol, setrol] = useState('');
    const [email, setemail] = useState('');
    const [telefon, settelefon] = useState('');

    async function add_staff(event) {
        
        var a = await addDoc(collection(db, "staff"), {
            nume: nume,
            prenume: prenume,
            data_nastere: data_nastere,
            rol: rol,
            email: email,
            telefon: telefon
        })
        .then(alert(`The staff member you added is: ${nume}`));

        await Promise.all([a]);
        window.location.href = "http://localhost:3000/tostaff";
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
            if(res.rol !== "admin"){
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

                            <h2 className="bt2">Add a staff member</h2>

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
                                <label className='scris'>Rol</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = 'Rol'
                                    onChange = {(e) => {
                                        setrol(e.target.value);
                                    }}
                                    value = {rol}
                                />
                            </Box>

                            <Box
                                className = "field"
                                sx = {{ display: 'flex', alignItems: 'flex-start' }}
                            >
                                <label className='scris'>Email</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = 'Email'
                                    onChange = {(e) => {
                                        setemail(e.target.value);
                                    }}
                                    value = {email}
                                />
                            </Box>

                            <Box
                                className = "field"
                                sx = {{ display: 'flex', alignItems: 'flex-start' }}
                            >
                                <label className='scris'>Telefon</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = 'Telefon'
                                    onChange = {(e) => {
                                        settelefon(e.target.value);
                                    }}
                                    value = {telefon}
                                />
                            </Box>

                            <Button className='bt2' onClick={add_staff} type = 'submit'>Submit</Button>
                            
                        </Form>
                    :
                        <></>
            }
        </div>
    )
}