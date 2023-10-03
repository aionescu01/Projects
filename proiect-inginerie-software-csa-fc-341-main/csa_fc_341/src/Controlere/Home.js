import React from "react";
import './Home.css';


function Home(){
    return (
        <div className="home">

            <svg viewBox="0 0 1320 300">
                <text x="50%" y="30%" dy=".35em" textAnchor="middle">
                    SQUAD 
                </text>
                <text x="50%" y="80%" dy=".35em" textAnchor="middle">
                    MANAGER
                </text>
            </svg> 
            
            <div className="divHome">
                <div>
                    <h1> Despre aplicatie </h1>

                    <p>“Squad Manager” este o aplicatie web pentru gestionarea unui club sportiv. Aceasta este adresata in mod special cluburilor care isi desfasoara activitatea in campionatele de fotbal.</p>
                    <p>Aplicatia a fost dezvoltata special pentru a usura munca cluburilor sportive, ajutandu-i pe oamenii ce activeaza in industria sportiva sa gestioneze mai facil clubul, sa aiba acces oricand la toate informatiile echipei.</p>
                    <p>Squad Manager, dupa cum sugereaza si denumirea, preia din atributiile managerului clubului. Memorand toate datele importante de la nivelul clubului, aplicatia este oriand pregatita sa iti ofere orice informatie ai nevoie referitoare la echipa ta. Cu aplicatia Squad Manager ai un asistent pregatit in permanenta sa raspunda cautarilor tale.</p>
                </div>

                <div >
                    <h1> Echipa developeri </h1>
                    
                    <ol>
                        <li><span className="text-wrapper">Avian Silviu</span></li>
                        <li><span className="text-wrapper">Dijmarescu Cristina</span></li>    
                        <li><span className="text-wrapper">Haiducu Stefan</span></li>
                        <li><span className="text-wrapper">Ioan Tudor</span></li>    
                        <li><span className="text-wrapper">Ionescu Alexandru</span></li>
                        <li><span className="text-wrapper">Stan Ana</span></li>
                    </ol>
                </div>
            </div>
        </div> 
    );
}

export default Home;