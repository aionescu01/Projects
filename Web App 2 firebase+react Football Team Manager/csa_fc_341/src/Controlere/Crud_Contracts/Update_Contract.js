import React, { useState,useEffect } from 'react';
import { app, auth } from '../../DatabaseConnection';
import { getFirestore, doc, getDoc, updateDoc } from "firebase/firestore";
import { useAuthState } from "react-firebase-hooks/auth";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";

const db = getFirestore(app);
var date;


export default function Update_Contract() {
    const [contractId, setcontractid] = useState();
    const [impresar, setimpresar] = useState();
    const [salariu, setsalariu] = useState();
    const [data_inceput, setdata_inceput] = useState();
    const [data_final, setdata_final] = useState();
    const [bonusuri, setbonusuri] = useState();
    const [idpersoana, setidpersoana] = useState();

    let navigate = useNavigate();

    var id = localStorage.getItem('contract_id');
    

    const update = () =>{
        if(id){
            getDoc(doc(db, "contract", id)).then(docSnap =>{
                date = docSnap.data();

                setidpersoana(date.id_persoana);
                setcontractid(date.id);
                setimpresar(date.impresar);
                setsalariu(date.salariu);
                setdata_inceput(date.data_inceput);
                setdata_final(date.data_final);
                setbonusuri(date.bonusuri);
                
            });
        } else {
            navigate('/');
        }
    }
    
    useEffect(()=>{
        update();
    },[])
    
    const handleSubmit = (event) => {

        const washingtonRef = doc(db, "contract", id);

        updateDoc(washingtonRef, {
            impresar: impresar,
            salariu: salariu,
            data_inceput: data_inceput,
            data_final: data_final,
            bonusuri:bonusuri
        });     
        
        event.preventDefault();
        alert(`S-a modificat contractul pentru impresar: ${impresar}`);
        window.location.href = "http://localhost:3000/tocontract";
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

                                <h1 className="bt2">Add a contract</h1>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Impresar</label> 
                                    <TextField
                                        className = "raspuns"
                                        variant = "outlined"
                                        placeholder = {impresar}
                                        onChange = {(e) => {
                                            setimpresar(e.target.value);
                                        }}
                                        value = {impresar}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Salariu</label> 
                                    <TextField
                                        className = "raspuns"
                                        variant = "outlined"
                                        placeholder = {salariu}
                                        onChange = {(e) => {
                                            setsalariu(e.target.value);
                                        }}
                                        value = {salariu}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Data incepere</label> 
                                    <TextField
                                        className = "raspuns"
                                        variant = "outlined"
                                        placeholder = {data_inceput}
                                        onChange = {(e) => {
                                            setdata_inceput(e.target.value);
                                        }}
                                        value = {data_inceput}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Data finalizare</label> 
                                    <TextField
                                        className = "raspuns"
                                        variant = "outlined"
                                        placeholder = {data_final}
                                        onChange = {(e) => {
                                            setdata_final(e.target.value);
                                        }}
                                        value = {data_final}
                                    />
                                </Box>

                                <Box
                                    className = "field"
                                    sx = {{ display: 'flex', alignItems: 'flex-start' }}
                                >
                                    <label className='scris'>Bonusuri</label> 
                                    <TextField
                                        className = "raspuns"
                                        variant = "outlined"
                                        placeholder = {bonusuri}
                                        onChange = {(e) => {
                                            setbonusuri(e.target.value);
                                        }}
                                        value = {bonusuri}
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