using ProiectDAW.DAL.Entities.Base;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Entities
{
    public class Sponsor : BaseEntity
    {
        [Required]
        public string Name { get; set; }
        public virtual ICollection<Constructor> Constructors { get; set; }

    }
}
