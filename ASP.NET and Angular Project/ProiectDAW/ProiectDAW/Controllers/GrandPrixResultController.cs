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

namespace ProiectDAW.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GrandPrixResultController : ControllerBase
    {
        private readonly AppDbContext _context;

        public GrandPrixResultController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost]
        public async Task<IActionResult> CreateGrandPrixResult(GrandPrixResultPostModel model)
        {
            var gp = new GrandPrixResult()
            {
                GrandPrixId = model.GrandPrixId,
                DriverId = model.DriverId,
                Position = model.Position,
                Points = model.Points,
                Stints = model.Stints
    };

            await _context.GrandPrixResults.AddRangeAsync(gp);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet]
        public async Task<IActionResult> GetGrandPrixResult()
        {
            var gp = await _context.GrandPrixResults.Select(GrandPrixResultGetModel.Projection).ToListAsync();

            return Ok(gp);
        }


        [HttpPut]
        public async Task<IActionResult> EditGrandPrixResult(int id, GrandPrixResultPostModel model)
        {
            var gp = await _context.GrandPrixResults.FirstOrDefaultAsync(gp => gp.Id == id);

            if (gp == null)
            {
                return BadRequest($"The grand prix with id {id} does not exist");
            }

                gp.GrandPrixId = model.GrandPrixId;
                gp.DriverId = model.DriverId;
                gp.Position = model.Position;
                gp.Points = model.Points;
                gp.Stints = model.Stints;

            await _context.SaveChangesAsync();

            return Ok();
        }
        [HttpDelete]
        public async Task<IActionResult> DeleteGrandPrixResult(int id)
        {
            var gp = await _context.GrandPrixResults.FirstOrDefaultAsync(gp => gp.Id == id);

            _context.GrandPrixResults.Remove(gp);
            await _context.SaveChangesAsync();

            return Ok();
        }


    }
}
