using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ProiectDAW.DAL.Entities;
using ProiectDAW.DAL.Models.MappingModels;
using AutoMapper;

namespace ProiectDAW.DAL.Mappings
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<Sponsor, SponsorMappingPostModel>().ReverseMap();

        }
    }
}
