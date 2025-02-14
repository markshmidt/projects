using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace InventoryManagementSystem.Models;

public class CartItem
{
    [Required]
    [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; }
    
    [Required]
    public int ProductId { get; set; }
    
    [Required]
    public string SessionId { get; set; }
    
    [Required, MinLength(1)]
    public int Quantity { get; set; }
    
    [Required]
    [ForeignKey("ProductId")]
    public Product Product { get; set; }
}