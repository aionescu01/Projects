using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Models
{
    public class CarGetModel
    {
        public int Number { get; set; }
        public int DriverNumber { get; set; }

        public int EngineNr { get; set; }
        public int GearboxNr { get; set; }
        public string DriverName { get; set; }
        public string ConstructorName { get; set; }
        public static Expression<Func<Entities.Car, CarGetModel>> Projection => car => new CarGetModel()
        {
            Number = car.Id,
            DriverName = $"{car.Driver.FirstName} {car.Driver.LastName}",
            EngineNr = car.EngineNr,
            GearboxNr = car.GearboxNr,
            DriverNumber = car.Driver.Id,
            ConstructorName = car.Driver.TeamName
        };
    }
}
