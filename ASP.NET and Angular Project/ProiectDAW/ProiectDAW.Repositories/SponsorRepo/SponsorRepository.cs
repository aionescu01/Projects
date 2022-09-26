using Microsoft.EntityFrameworkCore;
using ProiectDAW.DAL;
using ProiectDAW.DAL.Entities;
using ProiectDAW.Repositories.GenericRepo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.Repositories.SponsorRepo
{
    public class SponsorRepository : GenericRepository<Sponsor>, ISponsorRepository
    {


        public SponsorRepository(AppDbContext context) : base(context) { }

        public async Task<int> GetNumberOfSponsors()
        {
            var sponsors = await _table.ToListAsync();
            return sponsors.Count;
        }
    }
}
