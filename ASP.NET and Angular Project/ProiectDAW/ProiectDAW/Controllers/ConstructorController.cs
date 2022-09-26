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
    public class ConstructorController : ControllerBase
    {
        private readonly AppDbContext _context;

        public ConstructorController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost, Authorize(Roles = "Admin")]
        public async Task<IActionResult> CreateConstructor(ConstructorPostModel model)
        {
            var constr = new Constructor()
            {
                Name = model.Name,
                Country = model.Country,
                BaseLocation = model.BaseLocation
            };

            await _context.Constructors.AddRangeAsync(constr);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet]
        public async Task<IActionResult> GetConstructors()
        {
            var constr = await _context.Constructors.Select(ConstructorGetModel.Projection).ToListAsync();

            return Ok(constr);
        }

        [HttpGet("get-by-name/{name}")]
        public async Task<IActionResult> GetConstructorByName(string name)
        {
            var constr = await _context.Constructors.Select(ConstructorGetModel.Projection).FirstOrDefaultAsync(constr => constr.Name == name);
            return Ok(constr);
        }
        [HttpPut, Authorize(Roles = "Admin")]
        public async Task<IActionResult> EditConstructor(string name, ConstructorPostModel model)
        {
            var constr = await _context.Constructors.FirstOrDefaultAsync(constr => constr.Name == name);

            if (constr == null)
            {
                return BadRequest($"The constructor with name {name} does not exist");
            }


            constr.Name = model.Name;
            constr.Country = model.Country;
            constr.BaseLocation = model.BaseLocation;

            await _context.SaveChangesAsync();

            return Ok();
        }
        /*
        [HttpPut]
        public async Task<IActionResult> EditConstructor(int id, ConstructorPostModel model)
        {
            var constr = await _context.Constructors.FirstOrDefaultAsync(constr => constr.Id == id);

            if (constr == null)
            {
                return BadRequest($"The constructor with number {id} does not exist");
            }


            constr.Name = model.Name;
            constr.Country = model.Country;
            constr.BaseLocation = model.BaseLocation;

            await _context.SaveChangesAsync();

            return Ok();
        }*/
        /*
        [HttpDelete]
        public async Task<IActionResult> DeleteConstructor(int id)
        {
            var constr = await _context.Constructors.FirstOrDefaultAsync(constr => constr.Id == id);

            _context.Constructors.Remove(constr);
            await _context.SaveChangesAsync();

            return Ok();
        }*/
        [HttpDelete("delete-by-name/{name}"), Authorize(Roles = "Admin")]
        public async Task<IActionResult> DeleteConstructor(string name)
        {
            var constr = await _context.Constructors.FirstOrDefaultAsync(constr => constr.Name == name);

            _context.Constructors.Remove(constr);
            await _context.SaveChangesAsync();

            return Ok();
        }

    }
}
