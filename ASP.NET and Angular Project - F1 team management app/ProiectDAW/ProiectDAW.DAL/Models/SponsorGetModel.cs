using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;

namespace ProiectDAW.DAL.Models
{
    public class SponsorGetModel
    {
        public int Id { get; set; }
        public string Name { get; set; }


        public static Expression<Func<Entities.Sponsor, SponsorGetModel>> Projection => sponsor => new SponsorGetModel()
        {
            Id = sponsor.Id,
            Name = sponsor.Name
        };


    }
}
