using InventoryManagementSystem.Data;
using InventoryManagementSystem.Models;
using Microsoft.AspNetCore.Mvc;


namespace InventoryManagementSystem.Controllers;

public class AdminCategoryController : Controller
{
    private readonly ApplicationDbContext _context;

    public AdminCategoryController(ApplicationDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public IActionResult Index()
    {
        var categories = _context.Categories.ToList();
        return View(categories);
    }

    [HttpGet]
    public IActionResult AddCategory()
    {
        return View();
    }

    [HttpPost]
    [ValidateAntiForgeryToken]
    public IActionResult AddCategory(Category category)
    {
        if (!ModelState.IsValid)
        {
            return RedirectToAction("Index");
        }

        try
        {
            _context.Categories.Add(category);
            _context.SaveChanges();
        }
        catch (Exception ex)
        {
            return RedirectToAction("Index");
        }

        return RedirectToAction(nameof(Index));
    }
    
    
    [HttpGet]
    public IActionResult DeleteCategory(int id)
    {
        var category = _context.Categories.Find(id);
        if (category == null)
        {
            return NotFound();
        }

        return View(category);
    }

    [HttpPost]
    [ValidateAntiForgeryToken]
    public IActionResult DeleteCategory(int id, [Bind("Id, Name")] Category project)
    {
        var category = _context.Categories.Find(id);
        if (category == null)
        {
            return NotFound();
        }

        try 
        {
            _context.Categories.Remove(category);
            _context.SaveChanges();
        }
        catch (Exception ex)
        {
            return RedirectToAction("Index");
        }

        return RedirectToAction("Index");
    }
}