using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ProiectDAW.DAL;
using ProiectDAW.DAL.Entities;
using ProiectDAW.DAL.Models;
using Microsoft.AspNetCore.Authorization;
using ProiectDAW.Services.SponsorServices;
using ProiectDAW.DAL.Models.MappingModels;

namespace ProiectDAW.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SponsorController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly ISponsorService _sponsorService;

        public SponsorController(AppDbContext context, ISponsorService sponsorService)
        {
            _sponsorService = sponsorService;
            _context = context;
        }

        
        [HttpPost, Authorize(Roles = "Admin")]
        public async Task<IActionResult> CreateSponsor(SponsorPostModel model)
        {
            var sponsor = new Sponsor()
            {
                Name = model.Name
            };

            await _context.Sponsors.AddRangeAsync(sponsor);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet]
        public async Task<IActionResult> GetSponsors()
        {
            var constr = await _context.Sponsors.Select(SponsorGetModel.Projection).ToListAsync();

            return Ok(constr);
        }

        [HttpGet("get-by-id/{id}")]
        public async Task<IActionResult> GetSponsorsById(int id)
        {
            var sponsor = await _context.Sponsors.Select(SponsorGetModel.Projection).FirstOrDefaultAsync(sponsor => sponsor.Id == id);

            return Ok(sponsor);
        }

        [HttpPut, Authorize(Roles = "Admin")]
        public async Task<IActionResult> EditSponsor(SponsorPostModel model)
        {
            var sponsor = await _context.Sponsors.FirstOrDefaultAsync(sponsor => sponsor.Id == model.Id);

            if (sponsor == null)
            {
                return BadRequest($"The sponsor with name {model.Name} does not exist");
            }


            sponsor.Name = model.Name;

            await _context.SaveChangesAsync();

            return Ok();
        }
        [HttpDelete("delete-by-name/{name}"), Authorize(Roles = "Admin")]
        public async Task<IActionResult> DeleteSponsor(string name)
        {
            var sponsor = await _context.Sponsors.FirstOrDefaultAsync(sponsor => sponsor.Name == name);

            _context.Sponsors.Remove(sponsor);
            await _context.SaveChangesAsync();

            return Ok();
        }

        
        [HttpGet("get-number-of-sponsors")]
        public async Task<IActionResult> GetNumberOfSponsors() => Ok(await _sponsorService.GetNumberOfSponsors());

        [HttpPost("create-sponsor-with-mapper")]
        public async Task CreateSponsorMapper(SponsorMappingPostModel model) => await _sponsorService.CreateSponsorMapper(model);

    }
}
