using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ProiectDAW.DAL;
using ProiectDAW.DAL.Entities;
using ProiectDAW.DAL.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace ProiectDAW.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DriverStandingsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public DriverStandingsController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost]
        public async Task<IActionResult> CreateDriverStandings(DriverStandingsPostModel model)
        {
            var standing = new DriverStandings()
            {
                DriverId = model.Number,
                Place = model.Place,
                Points = model.Points,
                Races = model.Races,
                //DriverId = model.DriverId
            };

            await _context.DriversStandings.AddRangeAsync(standing);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet]
        public async Task<IActionResult> GetDriverStandings()
        {
            var standing = await _context.DriversStandings.Include(x => x.Driver).Select(DriverStandingsGetModel.Projection).ToListAsync();

            return Ok(standing);
        }


        [HttpPut]
        public async Task<IActionResult> EditDriverStandings(int number, DriverStandingsPostModel model)
        {
            var standing = await _context.DriversStandings.FirstOrDefaultAsync(standing => standing.DriverId == number);

            if (standing == null)
            {
                return BadRequest($"The driver with number {number} does not exist");
            }


            standing.DriverId = model.Number;
            standing.Place = model.Place;
            standing.Points = model.Points;
            standing.Races = model.Races;
            //standing.DriverId = model.DriverId;

            await _context.SaveChangesAsync();

            return Ok();
        }
        [HttpDelete]
        public async Task<IActionResult> DeleteDriverStandings(int number)
        {
            var standing = await _context.DriversStandings.FirstOrDefaultAsync(standing => standing.DriverId == number);

            _context.DriversStandings.Remove(standing);
            await _context.SaveChangesAsync();

            return Ok();
        }

    
    }
}
