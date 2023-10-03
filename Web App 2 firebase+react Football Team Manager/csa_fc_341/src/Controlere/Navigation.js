import React, { useEffect, useState } from "react";
import { app, auth } from "../DatabaseConnection";
import { getFirestore, doc, getDoc } from "firebase/firestore";
import { useAuthState } from "react-firebase-hooks/auth";
import { NavLink } from "react-router-dom";

import './Navigation.scss';

const db = getFirestore(app);


function Navigation() {
  const [user, loading, error] = useAuthState(auth);
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
        get_detalii_user(user.uid)
    } else {
        setRol_user("guest");
    }
  }, [loading, user]);


  return (
    <div>
     
      <div className="navigation ">
        <nav className="navbar navbar-expand  ">
          <div className="container">

            <div>
              <ul className="navbar-nav ml-auto menu">

                <li className="nav-item">
                  <NavLink className="nav-link" to="/">
                    Home
                  </NavLink>
                </li>

                {
                  rol_user === "guest"
                    ?
                      <li className="nav-item">
                        <NavLink className="nav-link" to="/toauth">
                          Auth
                        </NavLink>
                      </li>
                    :
                      <li className="nav-item">
                        <NavLink className="nav-link" to="/toauth">
                          Profil
                        </NavLink>
                      </li>
                }

                <li className="nav-item">
                  <NavLink className="nav-link" to="/tostadion">
                    Stadioane
                  </NavLink>
                </li>

                <li className="nav-item">
                  <NavLink className="nav-link" to="/tomeci">
                    Meciuri
                  </NavLink>
                </li>

                <li className="nav-item">
                  <NavLink className="nav-link" to="/tojucator">
                    Jucatori
                  </NavLink>
                </li>

                <li className="nav-item">
                  <NavLink className="nav-link" to="/tostaff">
                    Staff
                  </NavLink>
                </li>

                {
                  rol_user !== "admin"
                    ?
                      <></>
                    : 
                      <li className="nav-item">
                        <NavLink className="nav-link" to="/tocontract">
                          Contracte
                        </NavLink>
                      </li>
                }

                <li className="nav-item">
                  <NavLink className="nav-link" to="/tostatistics">
                    Statistici
                  </NavLink>
                </li>
                
              </ul>
            </div>
            
          </div>
        </nav>
      </div>

    </div>
  );
}

export default Navigation;