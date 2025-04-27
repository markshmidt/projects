using System.ComponentModel.DataAnnotations;

namespace InventoryManagementSystem.Models;

public class Product
{   
    public int Id { get; set; }
    
    [Required]
    public string Name { get; set; }

    [Required]
    public string Description { get; set; }

    [Required]
    public double Price { get; set; }

    [Required]
    public int Stock { get; set; } = 0;
    
    [Required]
    public int CategoryId { get; set; }  // Foreign key property

    public Category? Category { get; set; }  // Navigation property - make nullable
    
    public ICollection<OrderItem>? OrderItems { get; set; }
}