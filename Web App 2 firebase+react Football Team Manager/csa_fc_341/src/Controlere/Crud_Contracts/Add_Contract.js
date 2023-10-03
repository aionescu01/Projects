import React, { useState, useEffect } from 'react'
import { Form } from 'semantic-ui-react'
import { getFirestore, collection, addDoc, doc, getDoc } from "firebase/firestore";
import { app, auth } from '../../DatabaseConnection';
import { useNavigate } from "react-router-dom";
import { useAuthState } from "react-firebase-hooks/auth";
import { Box, TextField, Button } from '@mui/material';

import "../Stil.css";

const db = getFirestore(app);

export default function Create_Contract() {
    const [impresar, setimpresar] = useState('');
    const [salariu, setsalariu] = useState(0);
    const [data_inceput, setdata_inceput] = useState('');
    const [data_final, setdata_final] = useState('');
    const [bonusuri, setbonusuri] = useState(0);


    async function add_contract(event) {
        
        var a = await addDoc(collection(db, "contract"), {
            impresar: impresar,
            salariu: salariu,
            data_inceput: data_inceput,
            data_final: data_final,
            bonusuri: bonusuri
        }).then(alert(`The contract you added is: ${data_inceput}`));
          
        await Promise.all([a]);
        window.location.href = "http://localhost:3000/tocontract";
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

                            <h1 className="bt2">Add a contract</h1>

                            <Box
                                className = "field"
                                sx = {{ display: 'flex', alignItems: 'flex-start' }}
                            >
                                <label className='scris'>Impresar</label> 
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = "Impresar"
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
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = "Salariu"
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
                                <label className='scris'>Data inceput</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = "Data_inceput"
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
                                <label className='scris'>Data final</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = "Data_final"
                                    onChange = {(e) => {
                                        setdata_final(e.target.value);
                                    }}
                                    value = {data_final}
                                />
                            </Box>

                            <Box
                                className = "field"
                                sx = {{ display: 'flex', alignItems: 'flex-start'}}
                            >
                                <label className='scris'>Bonusuri</label>
                                <TextField
                                    className="raspuns"
                                    variant = "outlined"
                                    placeholder = "Bonusuri"
                                    onChange = {(e) => {
                                        setbonusuri(e.target.value);
                                    }}
                                    value = {bonusuri}
                                />
                            </Box>
                            
                            <Button className= 'bt2' onClick={add_contract} type = 'submit'>Submit</Button>
                            
                        </Form>
                    :
                        <></>
            }
        </div>
    )
}