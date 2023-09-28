using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;

namespace ProiectDAW.DAL.Models
{
    public class GrandPrixGetModel
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Location { get; set; }
        public string Country { get; set; }
        public DateTime Date { get; set; }
        public int Laps { get; set; }


        public static Expression<Func<Entities.GrandPrix, GrandPrixGetModel>> Projection => gp => new GrandPrixGetModel()
        {
            Id = gp.Id,
            Name = gp.Name,
            Location = gp.Location,
            Country = gp.Country,
            Date = gp.Date,
            Laps = gp.Laps
        };


    }
}
