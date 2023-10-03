import { getFirestore, collection, getDocs, getDoc, doc, deleteDoc } from "firebase/firestore";
import { Table, Button } from 'semantic-ui-react'
import { Link } from 'react-router-dom';
import { app, auth } from '../../DatabaseConnection';
import React, { useState, useEffect } from 'react';
import { useAuthState } from "react-firebase-hooks/auth";
import { ExportExcel } from '../Export/Export_Excel_Stadiums';

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


function Read_Stadiums(){

  const docRef = doc(db, "stadion", "id");

  deleteDoc(docRef)
  .then(() => {
      //console.log("Entire Document has been deleted successfully.")
  })
  .catch(error => {
      console.log(error);
  })

  function update(x){  
    localStorage.setItem('stadium_id',x)
  }

  function onDelete(id) {
    deleteDoc(doc(db, "stadion", id));
    window.location.reload();
  }

  function add_stadium(){ 
    window.open('http://localhost:3000/add_stadium','_parent','Add a stadium',param);
  }

  const [stadioane, setStadioane] = useState([]);
  

  const fetchStadioane = async()=>{
    let response=collection(db, 'stadion');

    await getDocs(response).then((querySnapshot) => {

      querySnapshot.forEach(element => {
        var date = element.data();
        date_tabel.push(date);
        SetDate(date_tabel);
        date.id = element.id;
        
        setStadioane(arr => [...arr , date]);
      });
    });
  }

  useEffect(()=>{
    fetchStadioane();
  },[])
    
  const { items, requestSort, sortConfig } = useSortableData(stadioane);

  const getClassNamesFor = (denumire) => {
    if (!sortConfig) {
        return;
    }

    return sortConfig.key === denumire ? sortConfig.direction : undefined;
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
            <Button type="button" className="bt4" id="butonAdd" onClick={()=>add_stadium()}>
                Add a stadium
            </Button>
          :
            <></>
      }

      <Table singleLine className='tabel'>

        <Table.Header className='tt1'>
          
          <Table.Row>
            
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('denumire')}
                      className={getClassNamesFor('denumire')}
                        >Nume
              </button>
            </Table.HeaderCell>
            
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('capacitate')}
                      className={getClassNamesFor('capacitate')}
                        >Capacitate
              </button>
            </Table.HeaderCell>
            
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('tip_gazon')}
                      className={getClassNamesFor('tip_gazon')}
                        >Suprafata
              </button>
            </Table.HeaderCell>
            
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('adresa')}
                      className={getClassNamesFor('adresa')}
                        >Adresa
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

                <Table.Row key = {data.denumire}>

                  <Table.Cell >{data.denumire}</Table.Cell>
                  <Table.Cell >{data.capacitate}</Table.Cell>
                  <Table.Cell >{data.tip_gazon}</Table.Cell>
                  <Table.Cell >{data.adresa}</Table.Cell>
                  {
                    rol_user === "admin" || rol_user === "staff"
                      ?
                        <>
                          <Table.Cell>
                            <Button onClick={() =>onDelete(data.id)}>Delete</Button>
                          </Table.Cell> 
                          
                          <Table.Cell> 
                            <Link to='/update_stadium'>
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

export default Read_Stadiums;
