using InventoryManagementSystem.Data;
using InventoryManagementSystem.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;

namespace InventoryManagementSystem.Controllers;

public class AdminProductController : Controller
{
    private readonly ApplicationDbContext _context;
    public AdminProductController(ApplicationDbContext context)
    {
        _context = context;
    }
    
    [HttpGet]
    public IActionResult Index(string sortBy, string sortOrder, int? category)
    {
        var query = _context.Products.Include(p => p.Category).AsQueryable();
        
        // Apply category filter
        if (category.HasValue)
        {
            query = query.Where(p => p.CategoryId == category);
        }

        // Apply sorting
        query = sortBy?.ToLower() switch
        {
            "name" => sortOrder == "desc" ? query.OrderByDescending(p => p.Name) : query.OrderBy(p => p.Name),
            "price" => sortOrder == "desc" ? query.OrderByDescending(p => p.Price) : query.OrderBy(p => p.Price),
            _ => query.OrderBy(p => p.Name)
        };

        ViewBag.Categories = _context.Categories.ToList();
        return View(query.ToList());
    }

    [HttpGet]
    public IActionResult Edit(int id)
    {
        if (!ProductExists(id))
        {
            return NotFound();
        }
        
        var product = _context.Products.Find(id);
        if (product == null)
        {
            return NotFound();
        }
        ViewBag.Categories = new SelectList(_context.Categories, "Id", "Name");
        return View(product);
    }
    
    // GET: Product/Create
    [HttpGet]
    public IActionResult Create()
    {
        ViewBag.Categories = new SelectList(_context.Categories, "Id", "Name");
        return View();
    }

    // GET: Product/Details
    [HttpGet]
    public IActionResult Details(int id)
    {
        var product = _context.Products.Find(id);
        if (product == null)
        {
            return NotFound();
        }
        return View(product);
    }

    // GET: Product/LowStock
    [HttpGet]
    public IActionResult LowStock()
    {
        var products = _context.Products.Where(p => p.Stock <= 10).ToList();
        return View(products);
    }

    // GET: Product/Delete
    [HttpGet]
    public IActionResult Delete(int id)
    {
        if (!ProductExists(id))
        {
            return NotFound();
        }
        var product = _context.Products.Find(id);
        return View(product);
    }

    // POST: Product/Delete
    [HttpPost]
    [ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public IActionResult DeleteConfirmed(int id)
    {
        if (!ProductExists(id))
        {
            return NotFound();
        }

        // Load the product with its current state from the database
        var productToDelete = _context.Products.Find(id);
        if (productToDelete == null)
        {
            return NotFound();
        }

        try
        {
            _context.Products.Remove(productToDelete);
            _context.SaveChanges();
            return RedirectToAction(nameof(Index));
        }
        catch (DbUpdateException ex) when (ex.InnerException?.Message.Contains("FK_CartItems_Products_ProductId") == true)
        {
            ModelState.AddModelError("", "Cannot delete this product because it exists in customer carts. Remove it from all carts first.");
            return View("Delete", productToDelete);
        }
        catch (Exception ex)
        {
            ModelState.AddModelError("", "An error occurred while deleting the product.");
            return View("Delete", productToDelete);
        }
    }

    // POST: Product/Create
    [HttpPost]
    [ValidateAntiForgeryToken]
    public IActionResult Create(Product product)
    {   
        if (!ModelState.IsValid)
        {
            ViewBag.Categories = new SelectList(_context.Categories, "Id", "Name");
            return View(product);
        }

        try
        {
            _context.Products.Add(product);
            _context.SaveChanges();
            return RedirectToAction(nameof(Index));
        }
        catch (Exception ex)
        {
            ModelState.AddModelError("", "Unable to save changes. Try again.");
            ViewBag.Categories = new SelectList(_context.Categories, "Id", "Name");
            return View(product);
        }
    }

    // POST: Product/Edit
    [HttpPost]
    [ValidateAntiForgeryToken]
    public IActionResult Edit(int id, Product product)
    {   
        if (!ProductExists(id))
        {
            return NotFound();
        }

        if (id != product.Id)
        {
            return BadRequest();
        }

        if (!ModelState.IsValid)
        {
            ViewBag.Categories = new SelectList(_context.Categories, "Id", "Name");
            return View(product);
        }
        
        try
        {
            _context.Products.Update(product);
            _context.SaveChanges();
            return RedirectToAction(nameof(Index));
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!ProductExists(product.Id))
            {
                return NotFound();
            }
            throw;
        }
    }
    

    private bool ProductExists(int id)
    {
        return _context.Products.Any(e => e.Id == id);
    }
    
    
}