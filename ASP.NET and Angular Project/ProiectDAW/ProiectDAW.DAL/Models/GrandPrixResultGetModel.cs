using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;

namespace ProiectDAW.DAL.Models
{
    public class GrandPrixResultGetModel
    {
        public string GrandPrixName { get; set; }
        public string DriverName { get; set; }
        public int Position { get; set; }
        public int Points { get; set; }
        public string Stints { get; set; }

        public static Expression<Func<Entities.GrandPrixResult, GrandPrixResultGetModel>> Projection => gp => new GrandPrixResultGetModel()
        {

            DriverName = $"{gp.Driver.FirstName} {gp.Driver.LastName}",
            Points = gp.Points,
            GrandPrixName = gp.GrandPrix.Location,
            Stints = gp.Stints,
            Position = gp.Position
        };


    }
}
