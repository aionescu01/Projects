import { getFirestore, collection, getDocs, doc, deleteDoc } from "firebase/firestore";
import { Table,Button } from 'semantic-ui-react'
import { Link } from 'react-router-dom';
import { app } from '../../DatabaseConnection';
import React, { useState, useEffect } from 'react';

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


function Read_Contracts(){

  const docRef = doc(db, "contract", "id");

  deleteDoc(docRef)
  .then(() => {
      //console.log("Entire Document has been deleted successfully.")
  })
  .catch(error => {
      console.log(error);
  })
    
  function update(x){    
    localStorage.setItem('contract_id',x)
  }

  function onDelete(id) {
    deleteDoc(doc(db, "contract", id));
    window.location.reload();
  }

  function add_contract(){
    window.open('http://localhost:3000/add_contract','_parent','Add a contract', param); 
  }

  const [contracts, setContracts] = useState([]);
  
  const fetchContracts = async()=>{
    let response=collection(db, 'contract');

    var a = getDocs(response).then((querySnapshot) => {

      querySnapshot.forEach(element => {
        var date = element.data();
        date.id = element.id;
        
        setContracts(arr => [...arr , date]);  
      });
      
    });

    await Promise.all([a]);

  }

  useEffect(()=>{
    fetchContracts();
  },[])
  
  const { items, requestSort, sortConfig } = useSortableData(contracts);

  const getClassNamesFor = (impresar) => {
    if (!sortConfig) {
        return;
    }
  
    return sortConfig.key === impresar ? sortConfig.direction : undefined;
  };


  return(
    <div>

      <Button type="button" className="bt4" id="butonAdd" onClick={()=>add_contract()}>
        Add a contract
      </Button>

      <Table singleLine className='tabel'>

        <Table.Header className='tt1'>

            <Table.Row>

              <Table.HeaderCell className='titlu'>
                <button type="button"
                        onClick={() => requestSort('data_inceput')}
                        className={getClassNamesFor('data_inceput')}
                          >Data semnare
                </button>
              </Table.HeaderCell>

              <Table.HeaderCell className='titlu'>
                <button type="button"
                        onClick={() => requestSort('data_final')}
                        className={getClassNamesFor('data_final')}
                          >Data expirare
                </button>
              </Table.HeaderCell>
                
              <Table.HeaderCell className='titlu'>
                <button type="button"
                        onClick={() => requestSort('impresar')}
                        className={getClassNamesFor('impresar')}
                          >Impresar
                </button>
              </Table.HeaderCell>

              <Table.HeaderCell className='titlu'>
                <button type="button"
                        onClick={() => requestSort('salariu')}
                        className={getClassNamesFor('salariu')}
                          >Salariu
                </button>
              </Table.HeaderCell>

              <Table.HeaderCell className='titlu'>
                <button type="button"
                        onClick={() => requestSort('bonusuri')}
                        className={getClassNamesFor('bonusuri')}
                          >Bonusuri
                </button>
              </Table.HeaderCell>

              <Table.HeaderCell className='titlu' />

              <Table.HeaderCell className='titlu' />
            
            </Table.Row>

        </Table.Header>

        <Table.Body>
        
          {
            items.map((data) =>  {
            
              return (
                <Table.Row key = {data.id}>

                  <Table.Cell >{data.data_inceput}</Table.Cell>
                  <Table.Cell >{data.data_final}</Table.Cell>
                  <Table.Cell >{data.impresar}</Table.Cell>
                  <Table.Cell >{data.salariu}</Table.Cell>
                  <Table.Cell >{data.bonusuri}</Table.Cell>

                  <Table.Cell>
                    <Button onClick={() =>onDelete(data.id)}>Delete</Button>
                  </Table.Cell> 
                
                  <Table.Cell> 
                    <Link to='/update_contract'>
                      <Button onClick={() =>update(data.id)}>Update</Button>
                    </Link>
                  </Table.Cell>
                      
                </Table.Row>
              )
            })
          }

        </Table.Body>

      </Table>
    </div>
  );
}

export default Read_Contracts;
