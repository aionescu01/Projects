using ProiectDAW.DAL.Entities.Base;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Entities
{
    public class Constructor
    {
        [Key]
        public string Name { get; set; }
        [Required]

        public string Country { get; set; }
        [Required]

        public string BaseLocation { get; set; }

    }
}
