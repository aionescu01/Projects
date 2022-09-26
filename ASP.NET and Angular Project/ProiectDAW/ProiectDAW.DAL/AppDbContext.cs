using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using ProiectDAW.DAL.Entities;
using ProiectDAW.DAL.Entities.Auth;

namespace ProiectDAW.DAL
{

    public class AppDbContext : IdentityDbContext<
       User,
       Role,
       int,
       IdentityUserClaim<int>,
       UserRole,
       IdentityUserLogin<int>,
       IdentityRoleClaim<int>,
       IdentityUserToken<int>>
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Car> Cars { get; set; }
        public DbSet<Constructor> Constructors { get; set; }
        public DbSet<Sponsor> Sponsors { get; set; }
        public DbSet<ConstructorSponsors> ConstructorsSponsors { get; set; }
        public DbSet<Driver> Drivers { get; set; }
        public DbSet<DriverStandings> DriversStandings { get; set; }
        public DbSet<GrandPrix> GrandPrix { get; set; }
        public DbSet<GrandPrixResult> GrandPrixResults { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

        }
    }
}