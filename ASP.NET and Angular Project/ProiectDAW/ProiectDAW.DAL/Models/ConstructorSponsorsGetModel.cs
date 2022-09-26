using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Models
{
    public class ConstructorSponsorsGetModel
    {

        public int SponsorId { get; set; }
        public string ConstructorName { get; set; }
        public string SponsorName { get; set; }
        
        public static Expression<Func<Entities.ConstructorSponsors, ConstructorSponsorsGetModel>> Projection => constructorsponsor => new ConstructorSponsorsGetModel()
        {
            SponsorId = constructorsponsor.SponsorId,
            SponsorName = constructorsponsor.Sponsor.Name,
            ConstructorName = constructorsponsor.ConstructorName
        };
    }
}
