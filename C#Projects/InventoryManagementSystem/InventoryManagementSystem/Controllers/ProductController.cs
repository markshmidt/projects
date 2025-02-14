using InventoryManagementSystem.Data;
using InventoryManagementSystem.Models;
using Microsoft.AspNetCore.Mvc;
using InventoryManagementSystem.Data;

using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;


namespace InventoryManagementSystem.Controllers;

public class ProductController : Controller
{   
    private readonly ApplicationDbContext _context;
    public ProductController(ApplicationDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public IActionResult Index(string search, int? categoryId, decimal? minPrice, decimal? maxPrice, bool? lowStock, string sortOrder)
    {
        var products = _context.Products.Include(p => p.Category).AsQueryable();

        // filter by name
        if (!string.IsNullOrEmpty(search))
        {
            products = products.Where(p => p.Name.Contains(search));
        }

        // filter by category
        if (categoryId.HasValue)
        {
            products = products.Where(p => p.CategoryId == categoryId.Value);
        }

        // filter by price
        if (minPrice.HasValue)
        {
            products = products.Where(p => p.Price >= (double)minPrice.Value);
        }

        if (maxPrice.HasValue)
        {
            products = products.Where(p => p.Price <= (double)maxPrice.Value);
        }
        
        // filter low-stock
        if (lowStock.HasValue && lowStock.Value)
        {
            products = products.Where(p => p.Stock < 10);
        }


        
        // sorting
        products = sortOrder switch
        {
            "name_asc" => products.OrderBy(p => p.Name),
            "name_desc" => products.OrderByDescending(p => p.Name),
            "price_asc" => products.OrderBy(p => p.Price),
            "price_desc" => products.OrderByDescending(p => p.Price),
            _ => products.OrderBy(p => p.Name) 
        };

        ViewBag.Categories = _context.Categories.ToList();
        return View(products.ToList());
    }
}