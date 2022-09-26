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
    public class DriverController : ControllerBase
    {
        private readonly AppDbContext _context;

        public DriverController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost, Authorize(Roles = "Admin")]
        public async Task<IActionResult> CreateDriver(DriverPostModel model)
        {
            if ((model.number < 1) || (model.number > 99))
            {
                return BadRequest("The driver number is invalid");
            }

            var driver = new Driver()
            {
                Id = model.number,
                FirstName = model.firstName,
                LastName = model.lastName,
                Age = model.age,
                TeamName = model.teamName
            };

            await _context.Drivers.AddRangeAsync(driver);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet]
        public async Task<IActionResult> GetDrivers()
        {
            var driver = await _context.Drivers.Select(DriverGetModel.Projection).ToListAsync();

            return Ok(driver);
        }

        [HttpGet("get-by-number/{id}")]
        public async Task<IActionResult> GetDriverById(int id)
        {
            var driver = await _context.Drivers.Select(DriverGetModel.Projection).FirstOrDefaultAsync(driver => driver.Number == id);
            return Ok(driver);
        }
        [HttpPut("put-by-id/{id}"), Authorize(Roles = "Admin")]
        public async Task<IActionResult> EditDriver(int id, DriverPostModel model)
        {
            var driver = await _context.Drivers.FirstOrDefaultAsync(driver => driver.Id == id);

            if (driver == null)
            {
                return BadRequest($"The driver with number {id} does not exist");
            }


            driver.Id = model.number;
            driver.FirstName = model.firstName;
            driver.LastName = model.lastName;
            driver.Age = model.age;
            driver.TeamName = model.teamName;

            await _context.SaveChangesAsync();

            return Ok();
        }
        [HttpPut, Authorize(Roles = "Admin")]
        public async Task<IActionResult> UpdateDriver(DriverPostModel model)
        {
            var driver = await _context.Drivers.FirstOrDefaultAsync(driver => driver.Id == model.number);

            if (driver == null)
            {
                return BadRequest($"The driver with number {model.number} does not exist");
            }


            driver.Id = model.number;
            driver.FirstName = model.firstName;
            driver.LastName = model.lastName;
            driver.Age = model.age;
            driver.TeamName = model.teamName;

            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpDelete("delete-by-id/{id}"), Authorize(Roles = "Admin")]
        public async Task<IActionResult> DeleteDriver(int id)
        {
            var driver = await _context.Drivers.FirstOrDefaultAsync(driver => driver.Id == id);

            _context.Drivers.Remove(driver);
            await _context.SaveChangesAsync();

            return Ok();
        }

    }
}