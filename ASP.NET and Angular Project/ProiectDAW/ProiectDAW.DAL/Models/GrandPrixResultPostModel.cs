using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.DAL.Models
{
    public class GrandPrixResultPostModel
    {
        public int GrandPrixId { get; set; }
        public int DriverId { get; set; }
        public int Position { get; set; }
        public int Points { get; set; }
        public string Stints { get; set; }

    }
}
