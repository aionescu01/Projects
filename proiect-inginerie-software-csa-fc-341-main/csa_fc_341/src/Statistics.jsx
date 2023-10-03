import React from 'react';
import Read_Statistics from './Controlere/Crud_Statistics/Read_Statistics';


export function Statistics() {
  return (
    <div>

      <svg viewBox="0 0 1320 200">
        <text x="50%" y="70%" dy=".35em" textAnchor="middle">
          Statistici 
        </text>
      </svg>

      <div>
        <Read_Statistics/>
      </div>

    </div>
  );
}
