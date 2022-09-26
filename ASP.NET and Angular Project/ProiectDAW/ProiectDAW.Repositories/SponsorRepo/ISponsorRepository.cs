using ProiectDAW.DAL.Entities;
using ProiectDAW.Repositories.GenericRepo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.Repositories.SponsorRepo
{
    public interface ISponsorRepository : IGenericRepository<Sponsor>
    {
        Task<int> GetNumberOfSponsors();
    }
}
