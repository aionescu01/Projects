using ProiectDAW.DAL.Entities.Base;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Entities
{
    public class GrandPrix
    {
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int Id{ get; set; }
        [Required]
        public string Name { get; set; }
        [Required]
        public string Location { get; set; }
        [Required]
        public string Country { get; set; }
        public DateTime Date { get; set; }
        public int Laps { get; set; }

    }
}
