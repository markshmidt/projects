using InventoryManagementSystem.Data;
using InventoryManagementSystem.Services;
using Microsoft.AspNetCore.Mvc;

namespace InventoryManagementSystem.Controllers;

public class CartController(CartService cartService, ApplicationDbContext dbContext) : Controller
{
    [HttpGet("/cart")]
    public IActionResult Index()
    {
        HttpContext.Session.SetString("init", "1");
        var cartItems = cartService.GetCartItems(true);
        ViewBag.Total = cartItems.Aggregate(0.0, (total, item)=> total + item.Product.Price * item.Quantity);
        return View(cartItems);
    }

    [HttpPost("/cart/add")]
    public IActionResult Add(int productId, int quantity)
    {
        if (productId <= 0 || quantity <= 0)
        {
            return BadRequest();
        }
        
        var product = dbContext.Products.FirstOrDefault(p => p.Id == productId);
        if (product != null)
        {
            cartService.AddToCart(product, quantity);
        }
        
        return Redirect("/cart");
    }
    
    [HttpPost("/cart/delete")]
    public IActionResult Delete(int productId, int quantity)
    {
        if (productId <= 0 || quantity <= 0)
        {
            return BadRequest();
        }
        
        var product = dbContext.Products.FirstOrDefault(p => p.Id == productId);
        if (product != null)
        {
            cartService.RemoveFromCart(product, quantity);
        }
        
        return Redirect("/cart");
    }
}