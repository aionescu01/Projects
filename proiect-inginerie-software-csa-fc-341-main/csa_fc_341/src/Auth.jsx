import { signOut } from 'firebase/auth';
import React, { useEffect, useState } from 'react';
import { getFirestore, doc, getDoc } from "firebase/firestore";
import { useAuthState } from "react-firebase-hooks/auth";
import { Link } from 'react-router-dom';
import { app, auth } from './DatabaseConnection';
import { Button } from '@mui/material';

import Login from './Login';
import Register from './Register';
import Reset from './Reset';

import "./Controlere/Stil.css";


const db = getFirestore(app);

function Auth(){
    const logout = () => {
        signOut(auth);
    }

    const [user, loading] = useAuthState(auth);
    const [rol_user, setRol_user] = useState("");
    const [det_user, setDet_user] = useState({});

    async function get_detalii_user(docID){
        const ref = doc(db, "users", docID);

        await getDoc(ref)
        .then(async (response) => {
            let res = response.data();
            
            setRol_user(res.rol);

            if (res.rol !== "admin"){
                await getDoc(res.user)
                .then((response2) => {
                    let res2 = response2.data();

                    setDet_user(res2);
                })
                .catch((e) => console.log(e));
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
            setRol_user("guest");
        }
    }, [loading, user]);

    return (
        <div>
            {
                rol_user === "guest"
                    
                    ?

                        <div className='test'>
                            
                            <Login />

                            <Register />

                            <Reset />
                            
                        </div>

                    : user 
                        
                        ?

                            rol_user === "admin"
                                
                                ?

                                    <div className = "logged">
                                        <div className = "text">
                                            <div display="flex">
                                                <h3 style={{paddingRight: "25em", paddingLeft: "42em"}}>Logged in as: Gigi Becali</h3>
                                                <img src="https://i.ytimg.com/vi/ibg1Bz5ykFY/mqdefault.jpg" alt="imagine gigi" width="300" height="150"/>
                                            </div>
                                            <p></p>
                                            <h3>Role: admin</h3>
                                        </div>

                                        <div>
                                            <Link to="/">
                                                <Button 
                                                    className = "buton"
                                                    variant = "contained"
                                                    onClick={() => {
                                                        logout();
                                                        }}>
                                                            Logout
                                                </Button>
                                            </Link>
                                        </div>
                                    </div>
                                
                                :

                                    rol_user === "jucator"

                                    ?
                                    
                                        <div className = "logged">
                                            <div className = "text">
                                                    <h3>Logged in as: {det_user.prenume} {det_user.nume}</h3>
                                                    <br/>
                                                    <h3>Pozitie: {det_user.pozitie}</h3>
                                                    <br/>
                                                    <h3>Nationalitate: {det_user.nationalitate}</h3>
                                                    <br/>
                                                    <h3>Data nasterii: {det_user.data_nastere}</h3>
                                                    <br/>
                                                    <br/>
                                                    <h3>Role: {rol_user}</h3>
                                            </div>

                                            <div>
                                                <Link to="/">
                                                    <Button 
                                                        className = "buton"
                                                        variant = "contained"
                                                        onClick={() => {
                                                            logout();
                                                            }}>
                                                                Logout
                                                    </Button>
                                                </Link>
                                            </div>
                                        </div>

                                    :
                                        <div className = "logged">
                                            <div className = "text">
                                                    <h3>Logged in as: {det_user.prenume} {det_user.nume}</h3>
                                                    <br/>
                                                    <h3>Rol: {det_user.rol}</h3>
                                                    <br/>
                                                    <h3>Data nasterii: {det_user.data_nastere}</h3>
                                                    <br/>
                                                    <h3>Email: {det_user.email}</h3>
                                                    <br/>
                                                    <h3>Telefon: {det_user.telefon}</h3>
                                                    <br/>
                                                    <br/>
                                                    <h3>Role: {rol_user}</h3>
                                            </div>

                                            <div>
                                                <Link to="/">
                                                    <Button 
                                                        className = "buton"
                                                        variant = "contained"
                                                        onClick={() => {
                                                            logout();
                                                            }}>
                                                                Logout
                                                    </Button>
                                                </Link>
                                            </div>
                                        </div>
                        
                        :

                            <></>
            }
        </div>
    )
}

export default Auth;