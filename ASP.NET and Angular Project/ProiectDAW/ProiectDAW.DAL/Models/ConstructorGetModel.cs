using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;

namespace ProiectDAW.DAL.Models
{
    public class ConstructorGetModel
    {
        //public int Id { get; set; }
        public string Name { get; set; }
        public string Country { get; set; }
        public string BaseLocation { get; set; }

        public static Expression<Func<Entities.Constructor, ConstructorGetModel>> Projection => constructor => new ConstructorGetModel()
        {
            //Id = constructor.Id,
            Name = constructor.Name,
            Country = constructor.Country,
            BaseLocation = constructor.BaseLocation
        };


    }
}
