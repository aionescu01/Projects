using ProiectDAW.DAL.Models.MappingModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProiectDAW.Services.SponsorServices
{
    public interface ISponsorService
    {
        Task<int> GetNumberOfSponsors();
        Task CreateSponsorMapper(SponsorMappingPostModel model);
    }
}
