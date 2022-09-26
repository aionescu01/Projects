using Microsoft.Extensions.DependencyInjection;
using System;
using ProiectDAW.DAL.Seeders;
using ProiectDAW.Services.AuthServices;
using ProiectDAW.Services.SponsorServices;

namespace ProiectDAW.Services
{
    public static class ServiceExtension
    {
        public static void AddServices(this IServiceCollection services)
        {

            services.AddTransient<IAuthService, AuthService>();
            services.AddTransient<ISponsorService, SponsorService>();
        }

        public static void AddSeeders(this IServiceCollection services)
        {
            services.AddTransient<InitialSeed>();
        }
    }
}
