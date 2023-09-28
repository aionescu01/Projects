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
    public class GrandPrixController : ControllerBase
    {
        private readonly AppDbContext _context;

        public GrandPrixController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost, Authorize(Roles = "Admin")]
        public async Task<IActionResult> CreateGrandPrix(GrandPrixPostModel model)
        {
            var gp = new GrandPrix()
            {
                Id = model.Id,
                Name = model.Name,
                Location = model.Location,
                Country = model.Country,
                Date = model.Date,
                Laps = model.Laps
    };

            await _context.GrandPrix.AddRangeAsync(gp);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet]
        public async Task<IActionResult> GetGrandPrix()
        {
            var gp = await _context.GrandPrix.Select(GrandPrixGetModel.Projection).ToListAsync();

            return Ok(gp);
        }

        [HttpGet("get-by-id/{id}")]
        public async Task<IActionResult> GetGrandPrixById(int id)
        {

            var gp = await _context.GrandPrix.Select(GrandPrixGetModel.Projection).FirstOrDefaultAsync(gp => gp.Id == id);

            return Ok(gp);
        }

        [HttpPut, Authorize(Roles = "Admin")]
        public async Task<IActionResult> EditGrandPrix(GrandPrixPostModel model)
        {
            var gp = await _context.GrandPrix.FirstOrDefaultAsync(gp => gp.Id == model.Id);

            if (gp == null)
            {
                return BadRequest($"The grand prix with id {model.Id} does not exist");
            }

            gp.Id = model.Id;
            gp.Name = model.Name;
            gp.Location = model.Location;
            gp.Country = model.Country;
            gp.Date = model.Date;
            gp.Laps = model.Laps;

            await _context.SaveChangesAsync();

            return Ok();
        }
        [HttpDelete("delete-by-id/{id}"), Authorize(Roles = "Admin")]
        public async Task<IActionResult> DeleteGrandPrix(int id)
        {
            var gp = await _context.GrandPrix.FirstOrDefaultAsync(gp => gp.Id == id);

            _context.GrandPrix.Remove(gp);
            await _context.SaveChangesAsync();

            return Ok();
        }


    }
}
