import React, { useState, useEffect } from 'react';
import Header from './Header';
import Footer from './Footer';
import './Layout.css';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  MenuItem,
  IconButton,
  Typography,
  Box,
  Container,
  Stack
} from '@mui/material';
import {
  Edit as EditIcon,
  Delete as DeleteIcon
} from '@mui/icons-material';

const ClientManagement = () => {
  const [clients, setClients] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [selectedClient, setSelectedClient] = useState(null);
  const [formData, setFormData] = useState({
    nume: '',
    prenume: '',
    nr_telefon: '',
    apelativ: '',
    data_nastere: '',
    nota: ''
  });

  useEffect(() => {
    fetchClients();
  }, []);

  const fetchClients = async () => {
    try {
      const response = await fetch('http://localhost:3001/api/oltp/client');
      const data = await response.json();
      setClients(data);
    } catch (error) {
      console.error('Error fetching clients:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async () => {
    try {
      const url = selectedClient 
        ? `http://localhost:3001/api/oltp/client/${selectedClient.cod_client}`
        : 'http://localhost:3001/api/oltp/client';
      
      const method = selectedClient ? 'PUT' : 'POST';
      
      await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });
      
      setOpenDialog(false);
      setSelectedClient(null);
      setFormData({
        nume: '',
        prenume: '',
        nr_telefon: '',
        apelativ: '',
        data_nastere: '',
        nota: ''
      });
      fetchClients();
    } catch (error) {
      console.error('Error saving client:', error);
    }
  };

  const handleEdit = (client) => {
    setSelectedClient(client);
    setFormData(client);
    setOpenDialog(true);
  };

  const handleDelete = async (id) => {
    try {
      await fetch(`http://localhost:3001/api/oltp/client/${id}`, {
        method: 'DELETE',
      });
      setOpenDeleteDialog(false);
      setSelectedClient(null);
      fetchClients();
    } catch (error) {
      console.error('Error deleting client:', error);
    }
  };

  return (
    <div className="parent">
      <Header/>
    
    <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
          <Typography variant="h5" component="h2">
            Management Clienți
          </Typography>
          <Button
            variant="contained"
            onClick={() => {
              setSelectedClient(null);
              setFormData({
                nume: '',
                prenume: '',
                nr_telefon: '',
                apelativ: '',
                data_nastere: '',
                nota: ''
              });
              setOpenDialog(true);
            }}
          >
            Adaugă Client
          </Button>
        </Box>

        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nume</TableCell>
                <TableCell>Prenume</TableCell>
                <TableCell>Telefon</TableCell>
                <TableCell>Apelativ</TableCell>
                <TableCell>Data Nașterii</TableCell>
                <TableCell>Notă</TableCell>
                <TableCell>Acțiuni</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {clients.map((client) => (
                <TableRow key={client.cod_client}>
                  <TableCell>{client.nume}</TableCell>
                  <TableCell>{client.prenume}</TableCell>
                  <TableCell>{client.nr_telefon}</TableCell>
                  <TableCell>{client.apelativ}</TableCell>
                  <TableCell>
                    {new Intl.DateTimeFormat('en-GB', {
                      day: '2-digit',
                      month: 'short',
                      year: 'numeric',
                    }).format(new Date(client.data_nastere))}
                  </TableCell>
                  <TableCell>{Math.round(parseFloat(client.nota) * 10) / 10}</TableCell>
                  <TableCell>
                    <Stack direction="row" spacing={1}>
                      <IconButton
                        size="small"
                        color="primary"
                        onClick={() => handleEdit(client)}
                      >
                        <EditIcon />
                      </IconButton>
                      <IconButton
                        size="small"
                        color="error"
                        onClick={() => {
                          setSelectedClient(client);
                          setOpenDeleteDialog(true);
                        }}
                      >
                        <DeleteIcon />
                      </IconButton>
                    </Stack>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>

        {/* Add/Edit Dialog */}
        <Dialog 
          open={openDialog} 
          onClose={() => setOpenDialog(false)}
          maxWidth="md"
          fullWidth
        >
          <DialogTitle>
            {selectedClient ? 'Editează Client' : 'Adaugă Client Nou'}
          </DialogTitle>
          <DialogContent>
            <Box
              component="form"
              sx={{
                '& .MuiTextField-root': { m: 1 },
                mt: 2,
                display: 'grid',
                gridTemplateColumns: 'repeat(2, 1fr)',
                gap: 2
              }}
            >
              <TextField
                label="Nume"
                name="nume"
                value={formData.nume}
                onChange={handleInputChange}
                fullWidth
              />
              <TextField
                label="Prenume"
                name="prenume"
                value={formData.prenume}
                onChange={handleInputChange}
                fullWidth
              />
              <TextField
                label="Număr Telefon"
                name="nr_telefon"
                value={formData.nr_telefon}
                onChange={handleInputChange}
                fullWidth
              />
              <TextField
                select
                label="Apelativ"
                name="apelativ"
                value={formData.apelativ}
                onChange={handleInputChange}
                fullWidth
              >
                <MenuItem value="Dl.">Domn</MenuItem>
                <MenuItem value="Dna.">Doamnă</MenuItem>
                
              </TextField>
              <TextField
                label="Data Nașterii"
                name="data_nastere"
                type="date"
                value={formData.data_nastere ? formData.data_nastere.split('T')[0] : ''}
                onChange={handleInputChange}
                InputLabelProps={{ shrink: true }}
                fullWidth
              />
              <TextField
                label="Notă"
                name="nota"
                type="number"
                inputProps={{ step: "0.1", min: "1", max: "10" }}
                value={ Math.round(parseFloat(formData.nota) * 10) / 10 }
                onChange={handleInputChange}
                fullWidth
              />
            </Box>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setOpenDialog(false)}>Anulează</Button>
            <Button onClick={handleSubmit} variant="contained">
              {selectedClient ? 'Salvează' : 'Adaugă'}
            </Button>
          </DialogActions>
        </Dialog>

        {/* Delete Confirmation Dialog */}
        <Dialog
          open={openDeleteDialog}
          onClose={() => setOpenDeleteDialog(false)}
        >
          <DialogTitle>Confirmă ștergerea</DialogTitle>
          <DialogContent>
            <Typography>
              Ești sigur că vrei să ștergi acest client? Această acțiune nu poate fi anulată.
            </Typography>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setOpenDeleteDialog(false)}>Anulează</Button>
            <Button 
              onClick={() => handleDelete(selectedClient?.cod_client)}
              color="error"
              variant="contained"
            >
              Șterge
            </Button>
          </DialogActions>
        </Dialog>
      </Paper>
    </Container>
    <Footer/>
    </div>
  );
};

export default ClientManagement;