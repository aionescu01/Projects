#include <iostream>
#include <time.h>
#include <string>
#include <vector>
#include <map>
#include <exception>

using namespace std;

time_t now = time(0);
tm *ltm = localtime(&now);
string datacurenta = datacurenta + to_string(ltm->tm_mday) + "/" + to_string(1 + ltm->tm_mon) + "/" + to_string(1900 + ltm->tm_year);

class myexception : public exception
{
    virtual const char *what() const throw()
    {
        return "Data este incorecta sau este scrisa in formatul gresit, rescrieti in formatul corect.";
    }
} excep;

class cont
{
protected:
    double sold;
    string data_deschidere, detinator;
    static int numar_conturi;
    const string numebanca = "X";

public:
    cont(double, string);
    ~cont();
    double get_sold()
    {
        return sold;
    }
    string get_datadeschidere()
    {
        return data_deschidere;
    }
    string get_detinator()
    {
        return detinator;
    }
    void set_sold(double x)
    {
        sold = x;
    }
    void set_datadeschidere(string data)
    {
        data_deschidere = data;
    }
    void set_detinator(string pers)
    {
        detinator = pers;
    }
    int numardeconturi()
    {
        return numar_conturi;
    }
    const string nume_banca()
    {
        return numebanca;
    }
    friend istream &operator>>(istream &, cont &);
    friend ostream &operator<<(ostream &, cont &);
    virtual void afisare(ostream &out);
    virtual void citire(istream &in);
    cont &operator=(cont &m);
};
int cont::numar_conturi;

cont::cont(double x = 0, string persoana = "")
{
    this->sold = x;
    this->detinator = persoana;
    numar_conturi++;
}

cont::~cont()
{
    this->sold = 0;
    this->data_deschidere = "";
    this->detinator = "";
    numar_conturi--;
}

void cont::citire(istream &in)
{

    int y = 1;
    string s;
    do
    {
        cout << "\nNume detinator: ";
        getline(in, s);
        try
        {
            y = 0;
            for (int i = 0; i < s.length(); i++)
                if (s[i] < 'a' || s[i] > 'z')
                    if (s[i] < 'A' || s[i] > 'Z')
                        if (s[i] != ' ')
                            if (s[i] != '-')
                                throw 100;
            y = 1;
        }
        catch (int u)
        {
            cout << "Nume invalid, introduceti un nume valid\n";
        }
    } while (y == 0);
    detinator = s;

    string r;
    int x;
    y = 1;
    do
    {
        cout << "Suma depusa initial in cont: ";
        cin >> r;
        try
        {
            y = 0;

            if (r == "exit")
                throw 'a';

            for (int i = 0; i < r.length(); i++)
                if (r[i] < '0' || r[i] > '9')
                    if (r[i] != '-')
                        throw 0.5;

            if (stoi(r) < 0)
                throw -1;

            y = 1;
        }
        catch (int x)
        {
            cout << "Soldul nu poate fi negativ, introduceti o suma valida sau 'exit' pentru a anula\n";
        }
        catch (double x)
        {
            cout << "Soldul nu poate contine alte caractere in afara de cifre, introduceti o suma valida sau 'exit' pentru a anula\n";
        }
        catch (char x)
        {
            exit(EXIT_FAILURE);
        }

    } while (y == 0);
    sold = stoi(r);

    string zi, luna, an;
    y = 1;
    do
    {
        cout << "Data deschidere(dd/mm/yyyy sau 'astazi' pentru a seta data de azi): ";
        cin >> r;
        try
        {
            y = 0;

            if (r == "exit")
                throw 'a';

            if (r == "astazi")
            {
                data_deschidere = data_deschidere + to_string(ltm->tm_mday) + "/" + to_string(1 + ltm->tm_mon) + "/" + to_string(1900 + ltm->tm_year);
                break;
            }

            int nrslash = 0, j, i;
            for (i = 0; i < r.length(); i++)
            {
                if (r[i] == '/')
                {
                    if (nrslash == 0)
                    {
                        j = 0;
                        while (j <= i - 1)
                        {
                            zi = zi + r[j];
                            j++;
                        }
                    }
                    if (nrslash == 1)
                    {
                        j++;
                        while (r[j] != '/')
                        {
                            luna = luna + r[j];
                            j++;
                        }
                        break;
                    }
                    nrslash++;
                }
            }
            j = i + 1;
            while (j < r.length())
            {
                an = an + r[j];
                j++;
            }

            if (zi.empty() == 1 || luna.empty() == 1 || an.empty() == 1)
                throw excep;

            if (stoi(zi) > 31)
                throw 0;

            if (stoi(luna) > 12)
                throw 1;

            if ((stoi(an) > stoi(to_string(ltm->tm_year)) + 1900))
                throw 2;

            if (stoi(an) == (stoi(to_string(ltm->tm_year)) + 1900))
                if (stoi(luna) >= (stoi(to_string(ltm->tm_mon)) + 1))
                    if (stoi(zi) > stoi(to_string(ltm->tm_mday)))
                        throw 3;

            if (stoi(an) == (stoi(to_string(ltm->tm_year)) + 1900))
                if (stoi(luna) > (stoi(to_string(ltm->tm_mon)) + 1))
                    throw 4;

            y = 1;
        }

        catch (int x)
        {
            cout << "Data invalida, introduceti o data valida sau 'exit' pentru a anula\n";
            zi = "";
            luna = "";
            an = "";
        }
        catch (exception &e)
        {
            cout << e.what() << "\n";
            zi = "";
            luna = "";
            an = "";
        }
        catch (char x)
        {
            exit(EXIT_FAILURE);
        }

    } while (y == 0);
    if (r != "astazi")
        data_deschidere = zi + '/' + luna + '/' + an;

    cin.get();
}

void cont::afisare(ostream &out)
{

    out << "\nNume detinator: " << detinator;
    out << "\nData deschidere: " << data_deschidere;
    out << "\nSuma din cont: " << sold << "\n";
}

istream &operator>>(istream &in, cont &c)
{
    c.citire(in);
    return in;
}
ostream &operator<<(ostream &out, cont &c)
{
    c.afisare(out);
    return out;
}

cont &cont::operator=(cont &c)
{
    detinator = c.detinator;
    data_deschidere = c.data_deschidere;
    sold = c.sold;
    return c;
}

class cont_economii : public cont
{
private:
    int perioada, contor_istoric;
    double rata_dobanda, *istoric_sold;

public:
    cont_economii(int, string, double, int);
    ~cont_economii();
    int get_ratadobanda()
    {
        return rata_dobanda;
    }
    int get_perioada()
    {
        return perioada;
    }
    double *get_istoricsold()
    {
        return istoric_sold;
    }
    friend istream &operator>>(istream &, cont_economii &);
    friend ostream &operator<<(ostream &, cont_economii &);
    void afisare(ostream &out);
    void citire(istream &in);
    cont_economii &operator=(cont_economii &m);
    void schimbare_sold(double x);
    void afisare_istoric();
};

cont_economii::cont_economii(int x = 0, string persoana = "", double dobanda = 0, int luni = 0) : cont(x, persoana)
{
    this->rata_dobanda = dobanda;
    this->perioada = luni;
    this->contor_istoric = 1;
    this->istoric_sold = new double[1];
}

cont_economii::~cont_economii()
{
    this->rata_dobanda = 0;
    this->perioada = 0;
    this->contor_istoric = 0;
    istoric_sold = NULL;
    delete[] istoric_sold;
}

istream &operator>>(istream &in, cont_economii &c)
{
    c.citire(in);
    return in;
}

ostream &operator<<(ostream &out, cont_economii &c)
{
    c.afisare(out);
    return out;
}

void cont_economii::citire(istream &in)
{
    cont::citire(in);
    istoric_sold[0] = cont::sold;

    string r;
    int y = 1;
    do
    {
        cout << "Introduceti rata dobanzii: ";
        cin >> r;
        try
        {
            y = 0;

            if (r == "exit")
                throw 'a';

            for (int i = 0; i < r.length(); i++)
                if (r[i] < '0' || r[i] > '9')
                    if (r[i] != '.')
                        throw 0.5;

            y = 1;
        }

        catch (double x)
        {
            cout << "Dobanda nu poate contine alte caractere in afara de cifre, introduceti o suma valida sau 'exit' pentru a anula\n";
        }
        catch (char x)
        {
            exit(EXIT_FAILURE);
        }

    } while (y == 0);
    rata_dobanda = stod(r);

    y = 1;
    do
    {
        cout << "Introduceti perioada, doar numarul de luni (3, 6 sau 12 luni): ";
        cin >> r;
        try
        {
            y = 0;

            if (r == "exit")
                throw 'a';

            for (int i = 0; i < r.length(); i++)
                if (r[i] < '0' || r[i] > '9')
                    throw 0.5;

            if (stoi(r) != 3 && stoi(r) != 6 && stoi(r) != 12)
                throw 1;

            y = 1;
        }
        catch (int x)
        {
            cout << "Perioada poate fi doar 3, 6 sau 12 luni!\n";
        }
        catch (double x)
        {
            cout << "Perioada nu poate contine alte caractere in afara de cifre, introduceti o suma valida sau 'exit' pentru a anula\n";
        }
        catch (char x)
        {
            exit(EXIT_FAILURE);
        }

    } while (y == 0);
    perioada = stoi(r);
    cin.get();
}

void cont_economii::afisare(ostream &out)
{
    cont::afisare(out);

    cout << "Rata dobanzii: " << rata_dobanda << "%";
    cout << "\nPerioada: " << perioada << " luni";
    cout << "\nIstoric cont: \n";
    for (int i = 0; i < contor_istoric; i++)
        cout << istoric_sold[i] << " ";
    cout << "\n";
}

cont_economii &cont_economii::operator=(cont_economii &c)
{
    detinator = c.detinator;
    data_deschidere = c.data_deschidere;
    sold = c.sold;
    perioada = c.perioada;
    istoric_sold = c.istoric_sold;
    contor_istoric = c.contor_istoric;
    rata_dobanda = c.rata_dobanda;
    return c;
}

void cont_economii::afisare_istoric()
{
    cout << "\n";
    for (int i = 0; i < contor_istoric; i++)
        cout << istoric_sold[i] << " ";
}

void cont_economii::schimbare_sold(double x)
{

    cont::sold = cont::sold + x;

    double *aux = istoric_sold;
    contor_istoric++;
    istoric_sold = new double[contor_istoric];

    for (int i = 0; i < contor_istoric - 1; i++)
        istoric_sold[i] = aux[i];
    istoric_sold[contor_istoric - 1] = cont::sold;
}

class cont_curent : public cont
{
private:
    int tranzactii_gratis;
    const double comision = 15;

public:
    cont_curent(int, string);
    ~cont_curent();
    int get_tranzactiigratis()
    {
        return tranzactii_gratis;
    }
    void set_tranzactiigratis(int x)
    {
        tranzactii_gratis = x;
    }
    friend istream &operator>>(istream &, cont_curent &);
    friend ostream &operator<<(ostream &, cont_curent &);
    void afisare(ostream &out);
    void citire(istream &in);
    cont_curent &operator=(cont_curent &m);
    void tranzactie();
};

cont_curent::cont_curent(int x = 0, string persoana = "") : cont(x, persoana)
{
    this->tranzactii_gratis = 3;
}

cont_curent::~cont_curent()
{
    this->tranzactii_gratis = 0;
}

istream &operator>>(istream &in, cont_curent &c)
{
    c.citire(in);
    return in;
}

ostream &operator<<(ostream &out, cont_curent &c)
{
    c.afisare(out);
    return out;
}

void cont_curent::citire(istream &in)
{
    cont::citire(in);
}

void cont_curent::afisare(ostream &out)
{
    cont::afisare(out);
    cout << "Numar de tranzactii gratuite ramase: " << tranzactii_gratis;
}

cont_curent &cont_curent::operator=(cont_curent &c)
{
    detinator = c.detinator;
    data_deschidere = c.data_deschidere;
    sold = c.sold;
    tranzactii_gratis = c.tranzactii_gratis;
    return c;
}

void cont_curent::tranzactie()
{
    double suma;
    string numebanca, operatie;

    cout << "\nIntroduceti tipul tranzactiei (depunere, retragere, plata sau anulare pentru a anula): ";
    cin >> operatie;
    cout << "Tranzactii fara comison ramase: " << tranzactii_gratis << "\n";

    if (operatie == "depunere")
    {
        cout << "Introduceti suma depusa: \n";
        cin >> suma;
        cont::sold = cont::sold + suma;
    }
    else if (operatie == "retragere")
    {
        cout << "Introduceti numele bancii unde s-a efectuat retragerea: \n";
        cin >> numebanca;
        if (numebanca == "X")
        {
            cout << "Introduceti suma retrasa: \n";
            cin >> suma;
            cont::sold = cont::sold - suma;
        }
        else
        {

            if (tranzactii_gratis > 0)
            {
                cout << "\nIntroduceti suma retrasa: \n";
                cin >> suma;
                if (cont::sold - suma < 0)
                {
                    cout << "Fonduri insuficiente! Tranzactie nefinalizata!\n";
                    return;
                }
                cont::sold = cont::sold - suma;
                tranzactii_gratis--;
            }
            else
            {
                cout << "\nIntroduceti suma retrasa (operatia are comision): \n";
                cin >> suma;
                if (cont::sold - suma < 0)
                {
                    cout << "Fonduri insuficiente! Tranzactie nefinalizata!\n";
                    return;
                }
                cont::sold = cont::sold - suma;
                cont::sold = cont::sold - comision;
            }
        }
    }
    else if (operatie == "plata")
    {
        cout << "\nIntroduceti suma platita (operatia are comision): \n";
        cin >> suma;
        if (cont::sold - suma < 0)
        {
            cout << "Fonduri insuficiente! Tranzactie nefinalizata!\n";
            return;
        }
        cont::sold = cont::sold - suma;
        cont::sold = cont::sold - comision;
    }
    else if (operatie == "anulare")
        return;
    else
        cout << "Tranzactie invalida!\n";
}

template <class t>
class conturi
{
private:
    vector<t *> v;
    int nr;

public:
    conturi()
    {
        nr = 0;
    }
    ~conturi()
    {
        v.clear();
    }
    friend ostream &operator<<(ostream &out, conturi<t> &g)
    {
        out << "La banca sunt dechise urmatoarele " << g.nr << " conturi:"
            << "\n";
        for (int i = 0; i < g.nr; i++)
            cout << *g.v[i];

        return out;
    }
};

template <>
class conturi<cont_economii>
{
private:
    vector<cont_economii *> v;
    int nr;

public:
    conturi()
    {
        nr = 0;
    }
    ~conturi()
    {
        v.clear();
    }
    friend ostream &operator<<(ostream &out, conturi<cont_economii> &g)
    {
        out << "\nLa banca sunt dechise urmatoarele conturi de economii pe 12 luni:"
            << "\n";
        for (int i = 0; i < g.nr; i++)
        {
            cout << *g.v[i];
        }
        return out;
    }
    friend conturi &operator+=(conturi<cont_economii> &c, cont_economii *&x)
    {

        if (x->get_perioada() == 12)
        {
            c.v.push_back(x);
            c.nr++;
        }
        else
            cout << "Contul adaugat nu este pe 12 luni!\n";
        return c;
    }
};

template <class t>
class GestionareConturi
{
private:
    int id;
    vector<cont *> conturi;

public:
    GestionareConturi()
    {
        id = 0;
    }
    ~GestionareConturi()
    {
        id = 0;
        conturi.clear();
    }
    friend ostream &operator<<(ostream &out, GestionareConturi<t> &c)
    {

        for (int i = 0; i < c.id; i++)
        {
            cout << "\nDatele contului cu id-ul " << i + 1 << " sunt:";
            cout << *c.conturi[i];
        }
        return out;
    }
    friend GestionareConturi &operator+=(GestionareConturi<t> &c, cont *&x)
    {
        c.id++;
        c.conturi.push_back(dynamic_cast<cont *>(x));
        return c;
    }

    int get_id()
    {
        return id;
    }
};

void menu()
{
    GestionareConturi<cont *> conturi;
    map<int, cont *> banca;
    int option = 0, n = 0;
    bool cititecon = 0, cititcurent = 0;
    cont *cnt = new cont;
    cont_economii *econ = new cont_economii;
    cont_curent *crnt = new cont_curent;

    do
    {

        cout << "\n Ionescu Alexandru 141 - Proiect 3 - Tema 7: \n";
        cout << "\n\t MENIU:";
        cout << "\n=================== Bun venit la banca '" << cnt->nume_banca() << "' ========================\n";
        cout << "\n";
        cout << "1. Citire n conturi";
        cout << "\n";
        cout << "2. Citire cont economii";
        cout << "\n";
        cout << "3. Citire cont curent";
        cout << "\n";
        cout << "4. Afisare cont economii";
        cout << "\n";
        cout << "5. Afisare cont curent";
        cout << "\n";
        cout << "6. Afisare nr de conturi";
        cout << "\n";
        cout << "7. Schimbare sold cont economii";
        cout << "\n";
        cout << "8. Afisare istoric sold cont economii";
        cout << "\n";
        cout << "9. Tranzactie pe cont curent";
        cout << "\n";
        cout << "10. Citire cont si apoi tipul acestuia(economii/curent) (upcast)";
        cout << "\n";
        cout << "11. Afisare suma cont economii dupa perioada cat a fost depusa";
        cout << "\n";
        cout << "12. Afisare GestionareConturi";
        cout << "\n";
        cout << "0. Iesire.";
        cout << "\n";
        cout << "\nIntroduceti numarul actiunii: ";
        cin >> option;
        cin.get();
        if (option == 1)
        {
            int id_cont = 1;
            map<int, cont *>::iterator itr;
            int tip;

            cout << "Introduceti numarul de conturi citite: ";
            cin >> n;

            for (int i = 0; i < n; i++)
            {
                cout << "\nIntroduceti 1 pentru cont simplu, 2 pentru cont de economii, 3 pentru cont curent: ";
                cin >> tip;
                cin.get();
                if (tip == 1)
                {
                    cont *a = new cont;
                    cin >> *a;
                    cont *p1 = dynamic_cast<cont *>(a);
                    banca.insert(pair<int, cont *>(id_cont++, p1));
                    conturi += p1;
                }
                else if (tip == 2)
                {
                    cont_economii *b = new cont_economii;
                    cin >> *b;
                    cont *p1 = dynamic_cast<cont *>(b);
                    banca.insert(pair<int, cont *>(id_cont++, p1));
                    conturi += p1;
                }
                else if (tip == 3)
                {
                    cont_curent *c = new cont_curent;
                    cin >> *c;
                    cont *p1 = dynamic_cast<cont *>(c);
                    banca.insert(pair<int, cont *>(id_cont++, p1));
                    conturi += p1;
                }
            }
            cout << "\n------------------------------------------\nAfisare conturi\n";
            for (itr = banca.begin(); itr != banca.end(); ++itr)
            {
                cout << "Contul numarul " << itr->first << *itr->second << '\n';
            }
        }
        if (option == 2)
        {
            cin >> *econ;
            cont *p1 = dynamic_cast<cont *>(econ);
            conturi += p1;
            cititecon = 1;
        }
        if (option == 3)
        {
            cin >> *crnt;
            cont *p1 = dynamic_cast<cont *>(crnt);
            conturi += p1;
            cititcurent = 1;
        }
        if (option == 4)
        {
            if (cititecon == 1)
                cout << *econ;
            else
                cout << "Contul de economii nu a fost citit. Reveniti la actiunea 2.";
        }
        if (option == 5)
        {
            if (cititcurent == 1)
                cout << *crnt;
            else
                cout << "Contul de economii nu a fost citit. Reveniti la actiunea 3.";
        }
        if (option == 6)
        {
            int nr_conturi = 0, nr_economii = 0, nr_curente = 0;
            if (n > 0)
            {
                map<int, cont *>::iterator i;

                for (i = banca.begin(); i != banca.end(); ++i)
                {
                    cont *c1 = dynamic_cast<cont *>(i->second);
                    cont_economii *c2 = dynamic_cast<cont_economii *>(i->second);
                    cont_curent *c3 = dynamic_cast<cont_curent *>(i->second);
                    if (c1)
                        nr_conturi++;
                    if (c2)
                        nr_economii++;
                    if (c3)
                        nr_curente++;
                }
                cout << "Numarul de conturi: " << nr_conturi << "\n";
                cout << "Numarul de conturi de economii: " << nr_economii << "\n";
                cout << "Numarul de conturi curente: " << nr_curente << "\n";
            }
            else
            {
                cout << "Nu s-au citit conturi. Reveniti la actiunea 1.\n";
            }
        }
        if (option == 7)
        {
            if (cititecon == 1)
            {
                double suma;
                cout << "Scrieti suma cu care se va modifica soldul, '-' inainte pentru a scadea soldul cu suma respectiva: ";
                cin >> suma;
                econ->schimbare_sold(suma);
            }
            else
                cout << "Nu s-a citit contul de economii. Reveniti la actiunea 2.\n";
        }
        if (option == 8)
        {
            if (cititecon == 1)
            {
                econ->afisare_istoric();
            }
            else
                cout << "Nu s-a citit contul de economii. Reveniti la actiunea 2.\n";
        }
        if (option == 9)
        {
            if (cititcurent == 1)
            {
                crnt->tranzactie();
            }
            else
                cout << "Nu s-a citit contul curent. Reveniti la actiunea 3.\n";
        }
        if (option == 10)
        {
            string tip;
            cout << "Introduceti economii/curent: ";
            cin >> tip;
            cin.get();
            if (tip == "economii")
            {
                cnt = new cont_economii;
                cin >> *cnt;
            }
            else if (tip == "curent")
            {
                cnt = new cont_curent;
                cin >> *cnt;
            }

            cout << "=================AFISARE CONT=================\n"
                 << *cnt;
        }
        if (option == 11)
        {
            if (cititecon == 1)
            {
                double dob, sum, sumafinala, per;
                per = econ->get_perioada();
                dob = econ->get_ratadobanda();
                sum = econ->get_sold();
                dob = dob / 12;
                per = per / 100;
                sumafinala = sum * (1 + dob * per);
                cout << "Suma finala va fi " << sumafinala;
            }
            else
                cout << "Nu s-a citit contul de economii. Reveniti la actiunea 2.\n";
        }
        if (option == 12)
        {
            cout << conturi;
        }
        if (option == 0)
        {
            cout << "\nEXIT!\n";
        }
        if (option < 0 || option > 12)
        {
            cout << "\nSelectie invalida\n";
        }
        cout << "\n";
        system("pause");
        system("cls");
    } while (option != 0);
}

int main()
{
    menu();
    return 0;
}
