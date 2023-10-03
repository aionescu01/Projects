import React, { useEffect, useState } from "react";
import { app, auth } from "./DatabaseConnection";
import { getFirestore, doc, getDoc } from "firebase/firestore";
import { useAuthState } from "react-firebase-hooks/auth";
import Read_Contract from './Controlere/Crud_Contracts/Read_Contract';
import { useNavigate } from "react-router-dom";

const db = getFirestore(app);


export function Contract() {

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
    <div>
      {
        rol_user !== ""
          ?
            <div className="main">

              <div className="container_coperta">
                <img src="https://www.freewebheaders.com/wp-content/gallery/office-finance/currency-bank-notes-and-coins-website-header.jpg" alt="poza contracte" id="poza_coperta"/>
                <div className="centered"> &#x2605;    LISTA CONTRACTE    &#x2605; </div>
              </div>   
          
              <div className="pls">
                <Read_Contract/>
              </div>

            </div>
          : 
            <></>
      }
    </div>
  );
}
