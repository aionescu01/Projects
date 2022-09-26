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

namespace ProiectDAW.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ConstructorSponsorsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public ConstructorSponsorsController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost, Authorize(Roles = "Admin")]
        public async Task<IActionResult> CreateConstructorSponsors(ConstructorSponsorsPostModel model)
        {
            var constructorSponsors = new ConstructorSponsors()
            {
                SponsorId = model.SponsorId,
                ConstructorName = model.ConstructorName
            };
            await _context.ConstructorsSponsors.AddAsync(constructorSponsors);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet("get-by-name/{name}")]
        public async Task<IActionResult> GetTeamSponsorsById(string name)
        {
            var constructorsponsors = await _context.ConstructorsSponsors.Select(ConstructorSponsorsGetModel.Projection).FirstOrDefaultAsync(constructorsponsors => constructorsponsors.ConstructorName == name);
            return Ok(constructorsponsors);
        }

        [HttpGet]
        public async Task<IActionResult> GetTeamSponsors()
        {
            var constructorsponsors = await _context.ConstructorsSponsors.Select(ConstructorSponsorsGetModel.Projection).ToListAsync(); ;
            return Ok(constructorsponsors);
        }

        [HttpGet("get-by-id/{id}")]
        public async Task<IActionResult> GetSponsorsTeamsById(int id)
        {
            var constructorsponsors = await _context.ConstructorsSponsors.Select(ConstructorSponsorsGetModel.Projection).FirstOrDefaultAsync(constructorsponsors => constructorsponsors.SponsorId == id);
            return Ok(constructorsponsors);
        }

        [HttpGet("get-by-id-name/{id}/{name}")]
        public async Task<IActionResult> GetSponsors(int id, string name)
        {
            var constructorsponsors = await _context.ConstructorsSponsors.Select(ConstructorSponsorsGetModel.Projection).FirstOrDefaultAsync(constructorsponsors => constructorsponsors.SponsorId == id && constructorsponsors.ConstructorName == name);
            return Ok(constructorsponsors);
        }

        [HttpDelete("delete-by-id/{id}/{name}"), Authorize(Roles = "Admin")]
        public async Task<IActionResult> DeleteeConstructorSponsors(int id, string name)
        {
            var constructorsponsors = await _context.ConstructorsSponsors.FirstOrDefaultAsync(constructorsponsors => constructorsponsors.SponsorId == id && constructorsponsors.ConstructorName==name);

            _context.ConstructorsSponsors.Remove(constructorsponsors);
            await _context.SaveChangesAsync();

            return Ok();
        }

    }
}

