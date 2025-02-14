using System.ComponentModel.DataAnnotations;

namespace InventoryManagementSystem.Models;

public class Order
{

    public Order()
    {
        
    }

    public Order(CheckoutModel checkoutModel)
    {
        Id = Guid.NewGuid().ToString();
        Name = checkoutModel.Name;
        Address = checkoutModel.Address;
        PhoneNumber = checkoutModel.PhoneNumber;
        Email = checkoutModel.Email;
        Date = DateTime.UtcNow;
        OrderItems = new List<OrderItem>();
    }
    
    [Required]
    [Key]
    public string? Id { get; set; }
    
    public DateTime Date { get; set; }
    
    [Required, MinLength(5), MaxLength(50)]
    public string Name { get; set; }
    
    [Required, MinLength(5), MaxLength(100)]
    public string Address { get; set; }
    
    [Required, Phone]
    public string PhoneNumber { get; set; }
    
    [Required, EmailAddress]
    public string Email { get; set; }
    
    [Required]
    public double TotalPrice { get; set; }
    
    public ICollection<OrderItem> OrderItems { get; set; }

}