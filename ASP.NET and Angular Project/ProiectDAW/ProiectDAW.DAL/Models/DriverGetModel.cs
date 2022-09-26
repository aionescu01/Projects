using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;

namespace ProiectDAW.DAL.Models
{
    public class DriverGetModel
    {
        public int Number { get; set; }
        public string Name { get; set; }
        public int Age { get; set; }
        public string TeamName { get; set; }


        public static Expression<Func<Entities.Driver, DriverGetModel>> Projection => driver => new DriverGetModel()
        {
            Number = driver.Id,
            Name = $"{driver.FirstName} {driver.LastName}",
            Age = driver.Age,
            TeamName = driver.TeamName
        };


    }
}
