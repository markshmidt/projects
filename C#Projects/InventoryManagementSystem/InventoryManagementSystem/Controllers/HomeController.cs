using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using InventoryManagementSystem.Models;

namespace InventoryManagementSystem.Controllers;

public class HomeController : Controller
{
    private readonly ILogger<HomeController> _logger;

    public HomeController(ILogger<HomeController> logger)
    {
        _logger = logger;
    }

    public IActionResult Index()
    {
        //IMPORTANT
        //session needs to be initialized with any data on the home page
        //(or wherever the user will land to select products)
        HttpContext.Session.SetString("init", "1"); 
        return View();
    }

    public IActionResult Privacy()
    {
        return View();
    }

    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
    public IActionResult Error()
    {
        return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
    }
}