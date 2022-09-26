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
    public class Car
    {

        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int Id { get; set; }
        public int EngineNr { get; set; }
        public int GearboxNr { get; set; }
        public bool IsDeleted { get; set; }
        [Required]
        public int DriverId { get; set; }
        public virtual Driver Driver{ get; set; }
        [Required]
        public virtual Constructor Constructor { get; set; }

    }
}
