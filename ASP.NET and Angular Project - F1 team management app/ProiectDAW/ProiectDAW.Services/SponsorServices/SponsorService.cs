using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AutoMapper;
using ProiectDAW.DAL.Entities;
using ProiectDAW.DAL.Models.MappingModels;
using ProiectDAW.Repositories.SponsorRepo;

namespace ProiectDAW.Services.SponsorServices
{
    public class SponsorService : ISponsorService
    {
        private readonly ISponsorRepository _sponsorRepository;
        private readonly IMapper _mapper;

        public SponsorService(ISponsorRepository sponsorRepository, IMapper mapper)
        {
            _sponsorRepository = sponsorRepository;
            _mapper = mapper;
        }

        public async Task<int> GetNumberOfSponsors() => await _sponsorRepository.GetNumberOfSponsors();

        public async Task CreateSponsorMapper(SponsorMappingPostModel model)
        {
            var sponsor = _mapper.Map<Sponsor>(model);

            await _sponsorRepository.CreateAsync(sponsor);
        }

    }
}
