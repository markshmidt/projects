using InventoryManagementSystem.Data;
using InventoryManagementSystem.Models;
using InventoryManagementSystem.Services;
using Microsoft.AspNetCore.Mvc;

namespace InventoryManagementSystem.Controllers;

public class CheckoutController(CartService cartService, ApplicationDbContext dbContext) : Controller
{
    [HttpGet("/checkout")]
    public IActionResult Index()
    {
        var cartItems = cartService.GetCartItems(true);
        if (cartItems.Count == 0)
        {
            return RedirectToAction("Index", "Cart");
        }
        return View();
    }

    [HttpPost("/checkout")]
    public IActionResult Index(CheckoutModel data)
    {
        if (!ModelState.IsValid)
        {
            return RedirectToAction("Index");
        }

        try
        {
            var cartItems = cartService.GetCartItems(true);
            var outOfStock = cartItems.FindAll(ci => ci.Product.Stock < ci.Quantity);
            if (cartItems.Count == 0)
            {
                return RedirectToAction("Index", "Cart");
            }
            if (outOfStock.Count > 0)
            {
                ViewBag.OutOfStock = outOfStock;
                return View("OutOfStock");
            }

            var newOrder = new Order(data)
            {
                TotalPrice = cartItems.Aggregate(0.0, (total, item) => total + item.Product.Price * item.Quantity)
            };
            
            dbContext.Orders.Add(newOrder);
            dbContext.SaveChanges();
            
            if (string.IsNullOrEmpty(newOrder.Id))
            {
                throw new InvalidOperationException("Order ID generation failed");
            }

            var orderItems = cartItems.Select(ci => new OrderItem(ci)
                {
                    OrderId = newOrder.Id,
                    Order = newOrder,
                    Price = ci.Product.Price,
                }
            ).ToList();
            dbContext.OrderItems.AddRange(orderItems);
            dbContext.SaveChanges();
            ViewBag.OrderId = newOrder.Id;
            return View("Success");
        }
        catch (Exception ex)
        {
            return View("Error");
        }
        
    }
}