using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;

namespace ProiectDAW.DAL.Models
{
    public class DriverStandingsGetModel
    {
        public int Number { get; set; }
        public int Place { get; set; }
        public string Name { get; set; }
        public int Points { get; set; }
        public string TeamName { get; set; }


        public static Expression<Func<Entities.DriverStandings, DriverStandingsGetModel>> Projection => standings => new DriverStandingsGetModel()
        {
            Number = standings.Driver.Id,
            Name = $"{standings.Driver.FirstName} {standings.Driver.LastName}",
            Place = standings.Place,
            Points = standings.Points,
            TeamName = standings.Driver.TeamName
        };


    }
}
