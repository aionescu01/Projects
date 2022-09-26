using ProiectDAW.DAL.Entities.Base;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Entities
{
    public class GrandPrixResult : BaseEntity
    {
        [Required]
        public int GrandPrixId { get; set; }
        [Required]
        public int DriverId { get; set; }
        public int Position { get; set; }
        public int Points { get; set; }
        [Required]
        public string Stints { get; set; }
        [Required]
        public virtual GrandPrix GrandPrix { get; set; }
        [Required]
        public virtual Driver Driver { get; set; }

    }
}
