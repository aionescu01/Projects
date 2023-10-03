import { getFirestore, collection, getDocs, doc, getDoc, deleteDoc } from "firebase/firestore";
import { Table, Button } from 'semantic-ui-react'
import { Link } from 'react-router-dom';
import { app, auth } from '../../DatabaseConnection';
import React, { useState, useEffect } from 'react';
import { useAuthState } from "react-firebase-hooks/auth";
import { ExportExcel } from '../Export/Export_Excel_Players';

import "../Stil.css";


const db = getFirestore(app);
let param ="width=500,height=500";

const useSortableData = (items, config = null) => {
  const [sortConfig, setSortConfig] = React.useState(config);

  const sortedItems = React.useMemo(() => {

    let sortableItems = [...items];

    if (sortConfig !== null) {
      sortableItems.sort((a, b) => {
        if (a[sortConfig.key] < b[sortConfig.key]) {
          return sortConfig.direction === 'ascending' ? -1 : 1;
        }
        if (a[sortConfig.key] > b[sortConfig.key]) {
          return sortConfig.direction === 'ascending' ? 1 : -1;
        }
        return 0;
      });
    }

    return sortableItems;
    
  }, [items, sortConfig]);

  const requestSort = (key) => {

    let direction = 'ascending';

    if (
      sortConfig &&
      sortConfig.key === key &&
      sortConfig.direction === 'ascending'
    ) {
      direction = 'descending';
    }

    setSortConfig({ key, direction });
  };

  return { items: sortedItems, requestSort, sortConfig };
};


function Read_Players(){

  const docRef = doc(db, "jucator", "id");

  deleteDoc(docRef)
  .then(() => {
      //console.log("Entire Document has been deleted successfully.")
  })
  .catch(error => {
      console.log(error);
  })

  function update(x){
    localStorage.setItem('player_id',x)
  }

  function onDelete(id) {
    deleteDoc(doc(db, "jucator", id));
    window.location.reload();
  }

  function add_player(){
    window.open('http://localhost:3000/add_player','_parent','Add a player',param);
  }

  const [jucatori, setJucatori] = useState([]);
  

  const fetchJucatori = async()=>{
    let response=collection(db, 'jucator');

    await getDocs(response).then((querySnapshot) => {

      querySnapshot.forEach(element => {
        var date = element.data();
        date_tabel.push(date);
        SetDate(date_tabel);
        date.id = element.id;

        setJucatori(arr => [...arr , date]);  
      });
    });
  }

  useEffect(()=>{
    fetchJucatori();
  },[])
    
  const { items, requestSort, sortConfig } = useSortableData(jucatori);
  
  const getClassNamesFor = (nume) => {
    if (!sortConfig) {
        return;
    }
    
    return sortConfig.key === nume ? sortConfig.direction : undefined;
  };

  const [user, loading, error] = useAuthState(auth);
  const [rol_user, setRol_user] = useState("");
    
    
  async function get_detalii_user(docID){
    const ref = doc(db, "users", docID);

    await getDoc(ref)
    .then((response) => {
        let res = response.data();
        
        setRol_user(res.rol);
    })
    .catch((e) => console.log(e));
  }

  const [date_tabel, SetDate] = useState([]);

  
  useEffect(() => {
    if (loading){
      return;
    } else if(user){
      get_detalii_user(user.uid)
    } else {
      setRol_user("guest");
    }
  }, [loading, user]);


  return(
    <div>
      {
        rol_user === "admin" || rol_user === "staff"
          ?
            <Button type="button" className="bt4" id="butonAdd" onClick={()=>add_player()}>
                Add a player
            </Button>
          :
            <></>
      }

      <Table singleLine className='tabel'>

        <Table.Header className='tt1'>

          <Table.Row>
          
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('nume')}
                      className={getClassNamesFor('nume')}
                        >Nume
              </button>
            </Table.HeaderCell>
            
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('prenume')}
                      className={getClassNamesFor('prenume')}
                        >Prenume
              </button>
            </Table.HeaderCell>
            
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('picior')}
                      className={getClassNamesFor('picior')}
                        >Picior
              </button>
            </Table.HeaderCell>

            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('pozitie')}
                      className={getClassNamesFor('pozitie')}
                        >Pozitie
              </button>
            </Table.HeaderCell>
            
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('nationalitate')}
                      className={getClassNamesFor('nationalitate')}
                        >Nationalitate
              </button>
            </Table.HeaderCell>

            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('inaltime')}
                      className={getClassNamesFor('inaltime')}
                        >Inaltime
              </button>
            </Table.HeaderCell>

            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('data_nastere')}
                      className={getClassNamesFor('data_nastere')}
                        >Data Nasterii
              </button>
            </Table.HeaderCell>

            <Table.HeaderCell className='titlu'></Table.HeaderCell>

            <Table.HeaderCell className='titlu'></Table.HeaderCell>
              
          </Table.Row>
            
        </Table.Header>

        <Table.Body>
        
          {
            items.map((data) =>  {
            
              return (
                <Table.Row key = {data.id}>

                  <Table.Cell >{data.nume}</Table.Cell>
                  <Table.Cell >{data.prenume}</Table.Cell>
                  <Table.Cell >{data.picior}</Table.Cell>
                  <Table.Cell >{data.pozitie}</Table.Cell>
                  <Table.Cell >{data.nationalitate}</Table.Cell>
                  <Table.Cell >{data.inaltime}</Table.Cell>
                  <Table.Cell >{(data.data_nastere).toString()}</Table.Cell>
                  {
                    rol_user === "admin" || rol_user === "staff"
                      ?
                        <>
                          <Table.Cell>
                            <Button onClick={() =>onDelete(data.id)}>Delete</Button>
                          </Table.Cell> 
                          
                          <Table.Cell>
                            <Link to='/update_player'> 
                              <Button onClick={() =>update(data.id)}>Update</Button>
                            </Link>
                          </Table.Cell>
                        </>
                      :
                        <></>
                  }

                </Table.Row>
              )
            })
          }

        </Table.Body>

      </Table>

      { items === [] ? <></> : <ExportExcel date_export={date_tabel} /> }
      
    </div>
  );
}

export default Read_Players;
