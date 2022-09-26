using ProiectDAW.DAL.Entities.Base;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Entities
{
    public class DriverStandings : BaseEntity
    {
        [Required]
        public int DriverId { get; set; }
        public int Place { get; set; }
        public int Points { get; set; }
        public int Races { get; set; }
        [Required]
        public virtual Driver Driver { get; set; }

    }
}
