import React, { useState, useEffect } from 'react';
import { app, auth } from '../../DatabaseConnection';
import { getFirestore, doc, getDoc, updateDoc } from "firebase/firestore";
import { useAuthState } from "react-firebase-hooks/auth";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";


const db = getFirestore(app);
var date;


export default function Update_Stadium() {
    const [staffId, setstaffid] = useState();
    const [nume, setnume] = useState('');
    const [prenume, setprenume] = useState('');
    const [data_nastere, setdata_nastere] = useState('');
    const [rol, setrol] = useState('');
    const [email, setemail] = useState('');
    const [telefon, settelefon] = useState('');

    let navigate = useNavigate();
    
    var id = localStorage.getItem('staff_id')
    
    
    const update=()=>{
        
        if(id){
            getDoc(doc(db, "staff", id)).then(docSnap =>{
            
                date = docSnap.data();

                setstaffid(date.id);
                setnume(date.nume);
                setprenume(date.prenume);
                setdata_nastere(date.data_nastere);
                setrol(date.rol);
                setemail(date.email);
                settelefon(date.telefon);
                
            });
        } else {
            navigate("/");
        }
    }
    

    useEffect(()=>{
        update();
    },[])
    
    const handleSubmit = (event) => {
        const washingtonRef = doc(db, "staff", id);

        updateDoc(washingtonRef, {
            nume: nume,
            prenume: prenume,
            data_nastere: data_nastere,
            rol: rol,
            email: email,
            telefon: telefon

        });
        
        event.preventDefault();
        alert(`The staff you updated was: ${nume}`);
        window.location.href = "http://localhost:3000/tostaff";
    }
    
    const [user, loading, error] = useAuthState(auth);
    const [rol_user, setRol_user] = useState("");


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
                        <form className='create-form1' onSubmit={handleSubmit}>
                            
                            <div>

                                <h2 className="bt2">Update staff member</h2>

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
                                    <label className='scris'>Data nastere</label>
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

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Rol</label>
                                    <TextField
                                        className="raspuns"
                                        variant = "outlined"
                                        placeholder = {rol}
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
                                        placeholder = {email}
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
                                        placeholder = {telefon}
                                        onChange = {(e) => {
                                            settelefon(e.target.value);
                                        }}
                                        value = {telefon}
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