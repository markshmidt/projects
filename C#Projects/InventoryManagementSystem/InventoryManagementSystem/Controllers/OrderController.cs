using InventoryManagementSystem.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace InventoryManagementSystem.Controllers;

public class OrderController(ApplicationDbContext dbContext) : Controller
{
    [HttpGet("/order")]
    public IActionResult Index()
    {
        return View();
    }

    [HttpPost("/order")]
    public IActionResult Index(string orderId, string email)
    {
        var order = dbContext.Orders.Include(o => o.OrderItems)
            .ThenInclude(oi => oi.Product)
            .FirstOrDefault(o => o.Id == orderId && o.Email == email);
        if (order == null)
        {
            ViewBag.ErrorMessage = "We couldn't find your order, please double check your information. If the issue persists, please contact us.";
            return View("NotFound");
        }
        return View("Details", order);
    }
}