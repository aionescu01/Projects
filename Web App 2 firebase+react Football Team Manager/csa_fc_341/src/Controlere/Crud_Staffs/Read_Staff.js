import { getFirestore, collection, getDocs, doc, deleteDoc, getDoc} from "firebase/firestore";
import { Table,Button } from 'semantic-ui-react'
import { Link } from 'react-router-dom';
import { app, auth } from '../../DatabaseConnection';
import React, { useState, useEffect } from 'react';
import { useAuthState } from "react-firebase-hooks/auth";

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


function Read_Staffs(){

  const docRef = doc(db, "staff", "id");

  deleteDoc(docRef)
  .then(() => {
      //console.log("Entire Document has been deleted successfully.")
  })
  .catch(error => {
      console.log(error);
  })

  function update(x){  
    localStorage.setItem('staff_id',x)
  }

  function onDelete(id) {
    deleteDoc(doc(db, "staff", id));
    window.location.reload();
  }

  function add_staff(){
    window.open('http://localhost:3000/add_staff','_parent','Add a staff member',param);
  }

  const [staffs, setStaffs] = useState([]);
  

  const fetchStaffs = async()=>{
    let response=collection(db, 'staff');

    await getDocs(response).then((querySnapshot) => {

      querySnapshot.forEach(element => {
        
        var date = element.data();
        date.id = element.id;
        
        setStaffs(arr => [...arr , date]);
      });
    });
  }

  useEffect(()=>{
    fetchStaffs();
  },[])
    
  const { items, requestSort, sortConfig } = useSortableData(staffs);
  
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
        rol_user === "admin" 
          ?
            <Button type="button" className="bt4" id="butonAdd" onClick={()=>add_staff()}>
                Add a staff member
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
                      onClick={() => requestSort('data_nastere')}
                      className={getClassNamesFor('data_nastere')}
                        >Data nastere
              </button>
            </Table.HeaderCell>
            
            <Table.HeaderCell className='titlu'>
              <button type="button"
                      onClick={() => requestSort('rol')}
                      className={getClassNamesFor('rol')}
                        >Rol
              </button>
            </Table.HeaderCell>
            
            {
              rol_user === "guest"
                ?
                  <></>
                :
                  <>   
                    <Table.HeaderCell className='titlu'>
                      <button type="button"
                              onClick={() => requestSort('email')}
                              className={getClassNamesFor('email')}
                        >Email
                      </button>
                    </Table.HeaderCell>
                              
                    <Table.HeaderCell className='titlu'>
                      <button type="button"
                              onClick={() => requestSort('telefon')}
                              className={getClassNamesFor('telefon')}
                        >Telefon
                      </button>
                    </Table.HeaderCell>
                  </>
            }

            <Table.HeaderCell className='titlu'></Table.HeaderCell>

            <Table.HeaderCell className='titlu'></Table.HeaderCell>
              
          </Table.Row>

        </Table.Header>

        <Table.Body>
      
          {
            items.map((data) =>  {
              return (
                <Table.Row key = {data.nume}>

                  <Table.Cell >{data.nume}</Table.Cell>
                  <Table.Cell >{data.prenume}</Table.Cell>
                  <Table.Cell >{data.data_nastere}</Table.Cell>
                  <Table.Cell >{data.rol}</Table.Cell>

                  {
                    rol_user === "guest"
                      ?
                        <></>
                      :
                        <>
                          <Table.Cell >{data.email}</Table.Cell>
                          <Table.Cell >{data.telefon}</Table.Cell>
                        </>
                  }

                  {
                    rol_user === "admin" 
                      ?
                        <>
                          <Table.Cell>
                            <Button onClick={() =>onDelete(data.id)}>Delete</Button>
                          </Table.Cell>

                          <Table.Cell> 
                            <Link to='/update_staff'>
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
    </div>
  );
}

export default Read_Staffs;
