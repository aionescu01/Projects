import { Box, TextField, Button } from '@mui/material';
import { sendPasswordResetEmail } from 'firebase/auth';
import React, { useState } from 'react';
import { auth } from './DatabaseConnection';

import EmailOutlinedIcon from '@mui/icons-material/EmailOutlined';

import "./Controlere/Stil.css";


function Reset(){
    const [email, setEmail] = useState("");
    const [emailError, setEmailError] = useState(false);

    const sendPasswordReset = async (event) => {
        try {
            event.preventDefault();

            await sendPasswordResetEmail(auth, email);
            
            setEmail('');

            alert("Password reset link sent!");
        } catch (error) {
            if(error.code === "auth/invalid-email"){
                setEmailError(true);
            }
        }
    };

    return(
        <div className = "reset">

            <label>RESET PASSWORD</label>

            <Box
                className = "field"
                sx = {{ display: 'flex', alignItems: 'flex-start' }}
            >
                <EmailOutlinedIcon sx = {{ color: '#E3F6F5', mr: 2, mt: 2 }} />
                <TextField
                    variant = "outlined"
                    className="scriere"
                    error = {emailError}
                    helperText = {emailError ? "Email inexistent" : ""}
                    placeholder = "Email"
                    onChange = {(event) => {
                        if(emailError){
                            setEmailError(false);
                        }
                        setEmail(event.target.value);
                    }}
                    value = {email}
                />
            </Box>

            <Button 
                className = "butonlogin"
                variant = "contained"
                onClick={(event) => {
                    sendPasswordReset(event);
                    }}>
                        Reset
            </Button>
        </div>
    );
}

export default Reset;