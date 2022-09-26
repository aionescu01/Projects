using Microsoft.Extensions.DependencyInjection;
using System;
using ProiectDAW.Repositories.SponsorRepo;


namespace ProiectDAW.Repositories
{
    public static class RepositoryExtension
    {
        public static void AddRepositories(this IServiceCollection services)
        {
            services.AddTransient<ISponsorRepository, SponsorRepository>();

        }
    }
}
