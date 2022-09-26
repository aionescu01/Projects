using ProiectDAW.DAL.Entities.Base;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Entities
{
    public class ConstructorSponsors : BaseEntity
    {
        public int SponsorId { get; set; }
        [Required]

        public string ConstructorName { get; set; }

        public virtual Constructor Constructor{ get; set; }
        public virtual Sponsor Sponsor { get; set; }


    }
}
