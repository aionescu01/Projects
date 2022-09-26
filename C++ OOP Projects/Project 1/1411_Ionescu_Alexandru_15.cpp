#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <cmath>
using namespace std;

class matrice
{
private:
    int n, m;
    double  **v;
public:
    matrice(int,int,double);///constructor pentru initializare
    matrice(matrice&);///constructor pentru copiere
    ~matrice();///destructor
    friend double nrcoloane(matrice &z){return z.m;};///functie friend care furnizeaza numarul coloanelor
    friend double nrlinii(matrice &z){return z.n;};///functie friend care furnizeaza numarul liniilor
    friend double nrelem(matrice &z){return z.m*z.n;};///functie friend care furnizeaza numarul elementelor din matrice
    void afisare(ostream &out);///metoda publica pentru afisare
    void citire(istream &in);///metoda publica pentru citire
    void citireFisier (ifstream& in);///metoda publica pentru citire din fisier
    friend ifstream& operator>>(ifstream& in, matrice& z);///operator pentru citire din fisier
    friend istream& operator>>(istream &in, matrice& z);///operator pentru citirea de la tastatura
    friend ostream& operator<<(ostream &out, matrice& z);///operator pentru afisare
    friend matrice& operator+(matrice& z1, matrice& z2);///operator suma
    friend matrice& operator-(matrice &z1, matrice& z2);///operator diferenta
    friend matrice& operator*(matrice &z1, matrice& z2);///operator inmultire matrice
    friend matrice& operator*(int x, matrice& z2);///operator inmultire matrice cu numar
    matrice& operator=(matrice& z);///operator egal pentru egalarea matricelor
    friend bool operator==(matrice &z1, matrice& z2);///operator pentru testarea egalitatii
    void schlicol(int lin, int col, double x);///metoda publica pentru reactualizarea numarului de linii si coloane si initializarea componentelor cu un numar dat
    friend double determinant(matrice &z);///functie friend pentru determinant
    friend void transpusa(matrice &z);///functie friend pentru transpusa matricei
    friend void getCofactor(double **v, int n, matrice &temp, int p, int q);///functie friend pentru aflarea matricei A*
    friend void inversa(matrice &z, matrice &b);///functie friend pentru aflarea inversei matricei
    void setnrlinii(int n);//setter n
    void setnrcol(int n);//setter m
    void setzero();///setter toate elementele din matrice cu 0
    void setelem(int i,int j,double x);//setter un element din matrice
};

void matrice::setnrlinii(int a)
{
    n=a;
}

void matrice::setnrcol(int a)
{
    m=a;
}

void matrice::setzero()
{
    this->n=n;
    this->m=m;
    delete v;
    v = new double *[n];
    for (int i = 0; i < n; ++i)
    {
        v[i] = new double [m];
        for (int j = 0; j < m; ++j)
            v[i][j] = 0;
    }
}

matrice::matrice(int row=0,int col=0,double x=0)
{

    this->n=row;
    this->m=col;
    n = row;
    m = col;
    v = new double *[n];
    for (int i = 0; i < n; ++i)
    {
        v[i] = new double [m];
        for (int j = 0; j < m; ++j)
            v[i][j] = x;
    }

}

matrice::matrice(matrice &z)
{
    this->n = z.n;
    this->m = z.m;
    v = new double *[n];
    for (int i = 0; i < n; ++i)
    {
        v[i] = new double [m];
        for (int j = 0; j < m; ++j)
            v[i][j] = z.v[i][j];
    }
}

matrice::~matrice()
{
    this->n=0;
    this->m=0;
    delete v;
}

void matrice::citire(istream &in)
{
    for(int i = 0; i < n; i++)
        for(int j = 0; j < m; j++)
            in >> v[i][j];
}

istream& operator>>(istream& in, matrice& z)
{

    z.citire(in);
    return in;

}

void matrice::afisare(ostream &out)
{
    out<<"\nn si m sunt:\n";
    out<<n<<" "<<m<<endl;
    out<<"Matricea este: \n";
    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < m; j++)
        {
            out << v[i][j] <<" ";
        }
        out <<endl;
    }
}

ostream& operator<<(ostream& out, matrice& z)
{
    z.afisare(out);
    return out;
}

void matrice::citireFisier(ifstream &in)
{
    for(int i = 0; i < n; i++)
        for(int j = 0; j < m; j++)
            in >> v[i][j];
}

ifstream& operator>>(ifstream& in, matrice& z)
{
    z.citireFisier(in);
    return in;
}

matrice& operator+(matrice &z1, matrice& z2)
{
    matrice *m = new matrice(z1.n,z1.m);
    double s;
    double row = z1.n, col = z1.m;

    
    if(z1.n==z2.n&&z1.m==z2.m)
    {
    for(int i = 0; i < z1.n; i++)
            for(int j = 0; j < z1.m; j++)
            {
                s = z1.v[i][j] + z2.v[i][j];
                m->setelem(i,j,s);
            }
        return *m;
    }
    else
    {
        cout<<"Matricele nu se pot aduna deoarece nu au nr de linii si coloane egale!";
        matrice m(0,0);
        z1=m;
        return z1;
    }
}

matrice& operator-(matrice &z1, matrice& z2)
{
    if(z1.n==z2.n&&z1.m==z2.m)
    {
        double s;
        double row = z1.n, col = z1.m;
    matrice *m = new matrice(z1.n,z1.m);
        for(int i = 0; i < z1.n; i++)
            for(int j = 0; j < z1.m; j++)
            {
                s = z1.v[i][j] - z2.v[i][j];
                //m.v[i][j]=s;
                m->setelem(i,j,s);
            }

       return *m;
    }
    else
    {
        cout<<"Matricele nu se pot aduna deoarece nu au nr de linii si coloane egale!";
        matrice m(0,0);
        z1=m;
        return z1;
    }
}

matrice& operator*(matrice &z1, matrice& z2)
{
    if(z1.m==z2.n)
    {
        double row = z1.n, col = z2.m;
        double s;
    matrice *m = new matrice(z1.n,z1.m);
        for(int i=0; i< z1.n; i++)
            {   
                for(int j=0; j<z2.m; j++)
                {s=0;
                for(int k=0; k<z1.m; k++)
                    {
                    s=s+z1.v[i][k]*z2.v[k][j];
                    m->setelem(i,j,s);
                    }
                }
            }
        return *m;
    }
    else
    {
        cout<<"Nu se pot inmulti.";
        matrice m(0,0);
        z1=m;
        return z1;
    }
}

matrice& operator*(int x, matrice& z)
{
    double row = z.n, col = z.m, s;
    matrice *m = new matrice(z.n,z.m);
    for(int i=0; i<row; i++)
        for(int j=0; j<col; j++)
            if (z.v[i][j]!=0)
               {s=x*z.v[i][j];
                m->setelem(i,j,s);}

    return *m;
}

matrice& matrice::operator=(matrice &z)
{

    n = z.n;
    m = z.m;
    for(int i = 0; i < z.n; i++)
        for(int j = 0; j < z.m; j++)
            v[i][j]=z.v[i][j];
    return z;

}

bool operator==(matrice &z1, matrice& z2)
{
    for(int i = 0; i < z1.n; i++)
        for(int j = 0; j < z1.m; j++)
            if(z1.v[i][j]!=z2.v[i][j])
                return false;
    return true;
}

void matrice::schlicol(int lin, int col, double nr)
{
    matrice a(lin,col,nr);
    n=lin;
    m=col;
    v = new double *[n];
    for (int i = 0; i < n; ++i)
    {
        v[i] = new double [m];
        for (int j = 0; j < m; ++j)
            v[i][j] = nr;
    }
}

void transpusa(matrice &z)
{
    if(z.n!=z.m)
    {
        matrice x(z.m,z.n);
        for (int i = 0; i < z.n; i++)
            for (int j = 0; j < z.m; j++)
                x.v[j][i] = z.v[i][j];
        z.schlicol(z.m,z.n,0);
        z=x;
    }
    else
    {
        matrice x(z.n,z.m);
        for (int i = 0; i < z.n; i++)
            for (int j = 0; j < z.m; j++)
                x.v[j][i] = z.v[i][j];
        x.n=z.n;
        x.m=z.m;
        z=x;
    }
}

//functie preluata de pe site-ul https://www.geeksforgeeks.org/adjoint-inverse-matrix/
void getCofactor(double **v, int n, matrice &temp, int p, int q)
{
    int i = 0, j = 0;
    temp.schlicol(n-1,n-1,0);
    // Looping for each element of the matrix
    for (int row = 0; row < n; row++)
    {
        for (int col = 0; col < n; col++)
        {
            //  Copying into temporary matrix only those element
            //  which are not in given row and column
            if (row != p && col != q)
            {
                temp.v[i][j++] = v[row][col];

                // Row is filled, so increase row index and
                // reset col index
                if (j == n - 1)
                {
                    j = 0;
                    i++;
                }
            }
        }
    }
}

//functie preluata de pe site-ul https://www.geeksforgeeks.org/adjoint-inverse-matrix/
double determinant(double **v, int n)
{
    double det = 1;

    // Row operations for i = 0, ,,,, n - 2 (n-1 not needed)
    for ( int i = 0; i < n - 1; i++ )
    {
        // Partial pivot: find row r below with largest element in column i
        int r = i;
        double maxA = abs( v[i][i] );
        for ( int k = i + 1; k < n; k++ )
        {
            double val = abs( v[k][i] );
            if ( val > maxA )
            {
                r = k;
                maxA = val;
            }
        }
        if ( r != i )
        {
            for ( int j = i; j < n; j++ ) swap( v[i][j], v[r][j] );
            det = -det;
        }

        // Row operations to make upper-triangular
        double pivot = v[i][i];
        for ( int r = i + 1; r < n; r++ )                    // On lower rows
        {
            double multiple = v[r][i] / pivot;                // Multiple of row i to clear element in ith column
            for ( int j = i; j < n; j++ ) v[r][j] -= multiple * v[i][j];
        }
        det *= pivot;                                        // Determinant is product of diagonal
    }

    det *= v[n-1][n-1];

    return det;
}

void inversa(matrice& x, matrice& b)
{
    matrice temp(x.n,x.n), cof(x.n,x.n), nec(x.n,x.n), inv(x.n,x.n);
    int row=x.n, col=x.n, i, j;
    for(i=0; i<row; i++)
        for(j=0; j<col; j++)
        {
            getCofactor(x.v, x.n, temp,i,j);
            if((i+1+j+1)%2==0)
                cof.v[i][j]=determinant(temp.v,temp.n);
            else cof.v[i][j]=-determinant(temp.v,temp.n);
        }
    transpusa(cof);
    double det = determinant(x.v,x.n);
    for(i=0; i<inv.n; i++)
        for(j=0; j<inv.n; j++)
            inv.v[i][j]=cof.v[i][j]/det;
    b=(-1)*b;
    nec = inv * b;
    if(det!=0)
    cout<<nec;
    else cout<<"Determinantul e 0, deci nu are inversa";

}

void matrice::setelem(int i,int j,double x)
{
    this->v[i][j]=x;
}

void menu()
{
    int option, citit=0;///citit devine 1 dupa ce s-au citit matricele, celelalte optiuni nu se pot folosi pana atunci
    option=0;
    int a, b, c, d;
    matrice x, y, z, m;

    do
    {


        cout<<"\n Ionescu Alexandru 141 - Proiect 1 - Tema 15. D6 Clasa matrice: \n";
        cout<<"\n\t MENIU:";
        cout<<"\n===========================================\n";
        cout<<"\n";
        cout<<"1. Citire 2 matrice A si B";
        cout<<"\n";
        cout<<"2. Afisare matrice";
        cout<<"\n";
        cout<<"3. Afisare nr linii si nr coloane";
        cout<<"\n";
        cout<<"4. Adunare cele 2 matrice";
        cout<<"\n";
        cout<<"5. Scadere cele 2 matrice";
        cout<<"\n";
        cout<<"6. Inmultire cele 2 matrice";
        cout<<"\n";
        cout<<"7. Inmultire matrice A cu un numar";
        cout<<"\n";
        cout<<"8. Rezultatul ecuatiei AX+B=0";
        cout<<"\n";
        cout<<"9. Schimbare nr de linii si/sau coloane, toate elementele devin un nr dat";
        cout<<"\n";
        cout<<"0. Iesire.";
        cout<<"\n";
        cout<<"\nIntroduceti numarul actiunii: ";
        cin>>option;

        if (option==1)
        {
            cout<<"Dati nr linii si coloane pt matricea A: ";
            cin>>a>>b;
            x.setnrlinii(a);
            x.setnrcol(b);
            x.setzero();
            cout<<"Dati matricea A: \n";
            cin>>x;

            cout<<"Dati nr linii si coloane pt matricea B: ";
            cin>>c>>d;
            y.setnrlinii(c);
            y.setnrcol(d);
            y.setzero();
            cout<<"Dati matricea B: \n";
            cin>>y;

            citit=1;
        }
        if (option==2)
        {
            if(citit==1)
            {
                cout<<"\nMatricea A: ";
                cout<<x;

                cout<<"\nMatricea B: ";
                cout<<y;
            }
            else
            {
                cout<<"Nu s-au citit matricele!";
            }
        }
        if (option==3)
        {
            if(citit==1)
            {
                cout<<"Nr de linii ale matricei A este: "<<nrlinii(x)<<endl;
                cout<<"Nr de coloane ale matricei A este: "<<nrcoloane(x)<<endl;
                cout<<"Nr de elemente ale matricei este: "<<nrelem(x)<<endl;
                cout<<"Nr de linii ale matricei B este: "<<nrlinii(y)<<endl;
                cout<<"Nr de coloane ale matricei B este: "<<nrcoloane(y)<<endl;
                cout<<"Nr de elemente ale matricei B este: "<<nrelem(y)<<endl;
            }
            else
            {
                cout<<"Nu s-au citit matricele!";
            }

        }
        if (option==4)
        {
            if(citit==1)
            {
                if(a==c&&b==d)
                {
                    cout<<x+y;
                }
                else
                {
                    cout<<"Nu se pot aduna matrice cu numar diferit de linii si coloane!";
                }
            }
            else
            {
                cout<<"Nu s-au citit matricele!";
            }

        }
        if (option==5)
        {
            if(citit==1)
            {
                if(a==c&&b==d)
                {
                    cout<<x-y;
                }
                else
                {
                    cout<<"Nu se pot scadea matrice cu numar diferit de linii si coloane!";
                }
            }

            else
            {
                cout<<"Nu s-au citit matricele!";
            }


        }
        if (option==6)
        {

            if(citit==1)
            {
                if(b==c)
                {
                    cout<<x*y;
                }
                else
                {
                    cout<<"Nu se pot inmulti aceste matrice!";
                }
            }
            else
            {
                cout<<"Nu s-au citit matricele!";
            }



        }
        if (option==7)
        {
            if(citit==1)
            {
                int nr;
                cout<<"Introduceti numarul: ";
                cin>>nr;
                x=nr*x;
                cout<<x;
            }
            else
            {
                cout<<"Nu s-au citit matricele!";
            }



        }
        if (option==8)
        {
            ///rezultatul ecuatiei AX+B=0 este X=-B*A^(-1), deci functia inversa calculeaza inversul matricei A si il inmulteste cu matricea B*(-1) si afiseaza rezultatul
            if(citit==1)
            { 
                if(nrlinii(x)!=nrcoloane(x))
                {
                    cout<<"Matricea are nr de linii si coloane diferit, deci nu are invers, deci ecuatia nu are solutie";
                }
                else if(a!=c||b!=d)
                {
                    cout<<"Matricele A si B au numar diferit de linii si coloane, nu se poate calcula solutia!";
                }
                else
                {
                z.setnrlinii(c);
                z.setnrcol(d);
                z.setzero();

                m.setnrlinii(a);
                m.setnrcol(b);
                m.setzero();
                    z=y;
                    m=x;
                    inversa(x,y);
                    y=z;
                    x=m;
                }
            }
            else
            {
                cout<<"Nu s-au citit matricele!";
            }


        }
        if (option==9)
        {
            if(citit==1)
            {
                char s;
                cout<<"Introduceti A pentru a modifica prima matrice si B pentru a modifica a doua matrice: ";
                cin.get();
                cin>>s;
                if(s=='A')
                {
                    double col,lin,nr;
                    cout<<"Introduceti numarul nou de coloane, de linii, si numarul care va aparea in toata matricea: ";
                    cin>>col>>lin>>nr;
                    x.schlicol(lin,col,nr);
                    cout<<"Noua matrice A: \n";
                    cout<<x;
                }
                else if(s=='B')
                {
                    double col,lin,nr;
                    cout<<"Introduceti numarul nou de coloane, de linii, si numarul care va aparea in toata matricea: ";
                    cin>>col>>lin>>nr;
                    y.schlicol(lin,col,nr);
                    cout<<"Noua matrice B: \n";
                    cout<<y;
                }
                else cout<<"Nume matrice invalid";
            }
            else
            {
                cout<<"Nu s-au citit matricele!";
            }



        }
        if (option==0)
        {
            cout<<"\nEXIT!\n";
        }
        if (option<0||option>9)
        {
            cout<<"\nSelectie invalida\n";
        }
        cout<<"\n";
        system("pause"); ///Pauza - Press any key to continue...
        system("cls");   ///Sterge continutul curent al consolei
    }
    while(option!=0);
}

int main()
{
    menu();
    return 0;
}
