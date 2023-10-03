import { getFirestore, collection, getDocs, doc, getDoc, deleteDoc } from "firebase/firestore";
import { Table, Button } from 'semantic-ui-react'
import { Link } from 'react-router-dom';
import { app, auth } from '../../DatabaseConnection';
import React, { useState, useEffect } from 'react';
import { useAuthState } from "react-firebase-hooks/auth";
import { ExportExcel } from '../Export/Export_Excel_Matches';

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


function Read_Matches(){

  const docRef = doc(db, "meci", "id");

  deleteDoc(docRef)
  .then(() => {
      //console.log("Entire Document has been deleted successfully.")
  })
  .catch(error => {
      console.log(error);
  })

  function update(x){
    localStorage.setItem('match_id',x)
  }

  function onDelete(id) {
    deleteDoc(doc(db, "meci", id));
    window.location.reload();
  }

  function add_match(){
    window.open('http://localhost:3000/add_match','_parent','Add a match',param);
  }

  const [meciuri, setMeciuri] = useState([]);

  
  const fetchMeciuri = async()=>{
    let response=collection(db, 'meci');

    await getDocs(response).then((querySnapshot) => {

      querySnapshot.forEach(element => {
          var date = element.data();
          date_tabel.push(date);
          SetDate(date_tabel);
          date.id = element.id;

          setMeciuri(arr => [...arr , date]);  
      });
    })
  }

  useEffect(()=>{
    fetchMeciuri();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  },[])
    
  const { items, requestSort, sortConfig } = useSortableData(meciuri);
    
  const getClassNamesFor = (adversar) => {
      if (!sortConfig) {
          return;
      }
      return sortConfig.key === adversar ? sortConfig.direction : undefined;
      };

  const [user, loading] = useAuthState(auth);
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
            <Button type="button" className="bt4" id="butonAdd" onClick={()=>add_match()}>
                Adauga un nou meci
            </Button>
          :
            <></>
      }
      
      <Table singleLine className='tabel'>

        <Table.Header className='tt1'>

            <Table.Row>
                <Table.HeaderCell className='titlu'>
                  <button type="button"
                          onClick={() => requestSort('adversar')}
                          className={getClassNamesFor('adversar')}
                            >Adversar
                  </button>
                </Table.HeaderCell>

                <Table.HeaderCell className='titlu'>
                  <button type="button"
                          onClick={() => requestSort('arbitru')}
                          className={getClassNamesFor('arbitru')}
                            >Arbitru
                  </button>
                </Table.HeaderCell>

                <Table.HeaderCell className='titlu'>
                  <button type="button"
                          onClick={() => requestSort('competitie')}
                          className={getClassNamesFor('competitie')}
                            >Competitie
                  </button>
                </Table.HeaderCell>

                <Table.HeaderCell className='titlu'>
                  <button type="button"
                          onClick={() => requestSort('data')}
                          className={getClassNamesFor('data')}
                            >Data
                  </button>
                </Table.HeaderCell>

                <Table.HeaderCell className='titlu'>
                  <button type="button"
                          onClick={() => requestSort('ora')}
                          className={getClassNamesFor('ora')}
                            >Ora
                  </button>
                </Table.HeaderCell>

                <Table.HeaderCell className='titlu'>
                  <button type="button"
                          onClick={() => requestSort('scor')}
                          className={getClassNamesFor('scor')}
                            >Scor
                  </button>
                </Table.HeaderCell>

                <Table.HeaderCell className='titlu' />

                <Table.HeaderCell className='titlu' />

                <Table.HeaderCell className='titlu' />

            </Table.Row>

        </Table.Header>

        <Table.Body>
          {
            items.map((data) =>  {

              return (
                <Table.Row key = {data.id} >

                  <Table.Cell >{data.adversar}</Table.Cell>
                  <Table.Cell >{data.arbitru}</Table.Cell>
                  <Table.Cell >{data.competitie}</Table.Cell>
                  <Table.Cell >{data.data}</Table.Cell>
                  <Table.Cell >{data.ora}</Table.Cell>
                  <Table.Cell >{data.scor}</Table.Cell>
                  {
                    rol_user === "admin" || rol_user === "staff"
                      ?
                        <>
                          <Table.Cell>
                            <Button onClick={() =>onDelete(data.id)}>Delete</Button>
                          </Table.Cell> 

                          <Table.Cell> 
                            <Link to='/update_match'>
                                <Button onClick={() =>update(data.id)}>Update</Button>
                            </Link> 
                          </Table.Cell>
                        </>
                      :
                        <></>
                  }
                  <Table.Cell>
                    <Link to={`/tomeci/meci/${data.id}`} params={{id: data.id}}>
                      <Button onClick={() =>update(data.id)}>Detalii</Button>
                    </Link>
                  </Table.Cell>
                  
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

export default Read_Matches;
