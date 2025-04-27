using System.ComponentModel.DataAnnotations;

namespace InventoryManagementSystem.Models;

public class CheckoutModel
{
    [Required, MinLength(5), MaxLength(50)]
    public required string Name { get; set; }
    
    [Required, MinLength(5), MaxLength(100)]
    public required string Address { get; set; }
    
    [Required]
    [RegularExpression(@"^\+?[1-9]\d{1,14}$", ErrorMessage = "Enter a valid phone number.")]
    public required string PhoneNumber { get; set; }
    
    [Required, EmailAddress]
    public required string Email { get; set; }
}
