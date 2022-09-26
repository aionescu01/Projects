using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ProiectDAW.DAL.Models.Auth;
using ProiectDAW.DAL.Models.User;

namespace ProiectDAW.Services.AuthServices
{
    public interface IAuthService
    {
        Task<UserAuthenticatedModel> Register(RegisterModel model);
        Task<UserAuthenticatedModel> Login(LoginModel model);
    }
}
