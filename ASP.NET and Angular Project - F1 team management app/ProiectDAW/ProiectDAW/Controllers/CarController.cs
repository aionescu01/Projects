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
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Authorization;

namespace ProiectDAW.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CarController : ControllerBase
    {
        private readonly AppDbContext _context;

        public CarController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost, Authorize(Roles = "Admin")]
        public async Task<IActionResult> CreateCar(CarPostModel model)
        {
            if ((model.number<1)||(model.number > 99))
            {
                return BadRequest("The car number is invalid");
            }
            if ((model.driverNumber < 1) || (model.driverNumber > 99))
            {
                return BadRequest("The driver number is invalid");
            }
            
            var verif = await _context.Cars.Select(CarGetModel.Projection).FirstOrDefaultAsync(verif => verif.Number == model.driverNumber);
            /*
            if(verif!=null)
            {
                var oldcar = await _context.Cars.FirstOrDefaultAsync(oldcar => oldcar.Id == model.number);
                _context.Cars.Remove(oldcar);
                //return BadRequest("The driver already has a car assigned");
            }
            */
            if (verif != null)
            {
                return BadRequest("The driver already has a car assigned");
            }

            var driver = await _context.Drivers.Select(DriverGetModel.Projection).FirstOrDefaultAsync(driver => driver.Number == model.driverNumber);
            if(driver==null)
            { 
                return BadRequest("The driver does not exist");
            }    


            var car = new Car()
            {
                EngineNr = model.EngineNr,
                GearboxNr = model.GearboxNr,
                Id = model.number,
                DriverId = model.driverNumber
            };

            await _context.Cars.AddRangeAsync(car);
            await _context.SaveChangesAsync();

            return Ok();
        }
        

        [HttpGet]
        public async Task<IActionResult> GetCars()
        {
            var car = await _context.Cars.Include(x => x.Driver).Where(x => x.IsDeleted == false).Select(CarGetModel.Projection).ToListAsync();

            return Ok(car);
        }
        [HttpGet("get-by-number/{id}")]
        public async Task<IActionResult> GetCarById(int id)
        {
            var car = await _context.Cars.Select(CarGetModel.Projection).FirstOrDefaultAsync(car => car.Number == id);
            return Ok(car);
        }
        
        [HttpPut("put-by-id/{id}"), Authorize(Roles = "Admin")]
        public async Task<IActionResult> EditCar(int id, CarPostModel model)
        {
            var car = await _context.Cars.FirstOrDefaultAsync(car => car.Id == id);

            if (car == null)
            {
                return BadRequest($"The car with number {id} does not exist");
            }


            car.EngineNr = model.EngineNr;
            car.GearboxNr = model.GearboxNr;
            car.Id = model.number;
            car.DriverId = model.driverNumber;
            await _context.SaveChangesAsync();

            return Ok();
        }
        
        [HttpPut, Authorize(Roles = "Admin")]
        public async Task<IActionResult> UpdateCar(CarPostModel model)
        {
           

            var car = await _context.Cars.FirstOrDefaultAsync(car => car.Id == model.number);
            if (car == null)
            {
                return BadRequest($"The car with number {model.number} does not exist");
            }


            car.EngineNr=model.EngineNr;
            car.GearboxNr = model.GearboxNr;
            car.Id = model.number;
            car.DriverId = model.driverNumber;
            await _context.SaveChangesAsync();

            return Ok();

        }

        /*
        public async Task<IActionResult> Update(Car oldcar)
        {
            var car = await _context.Cars.FirstOrDefaultAsync(car => car.Id == oldcar.Id);

            car = oldcar;
            await _context.SaveChangesAsync();

            return Ok();
        }
        */

        [HttpDelete("delete-by-id/{id}"), Authorize(Roles = "Admin")]
        public async Task<IActionResult> DeleteCar(int id)
        {
            var car = await _context.Cars.FirstOrDefaultAsync(car => car.Id == id);

            _context.Cars.Remove(car);
            await _context.SaveChangesAsync();

            return Ok();
        }



        
        [HttpPut("soft-delete")]
        public async Task<IActionResult> SoftDeleteCar(int id)
        {
            var car = await _context.Cars.FirstOrDefaultAsync(person => person.Id == id);

            car.IsDeleted = true;
            await _context.SaveChangesAsync();

            return Ok();
        }
        

    }
}
