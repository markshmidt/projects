using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace InventoryManagementSystem.Models;

public class OrderItem
{
    public OrderItem()
    {
        
    }

    public OrderItem(CartItem cartItem)
    {
        ProductId = cartItem.ProductId;
        Product  = cartItem.Product;
        Quantity = cartItem.Quantity;
    }
    
    [Required]
    [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; }
    
    [Required]
    public string OrderId { get; set; }
    
    [Required]
    [ForeignKey("OrderId")]
    public Order Order { get; set; }
    
    [Required]
    public int ProductId { get; set; }
    
    [Required]
    [ForeignKey("ProductId")]
    public Product Product { get; set; }
    
    [Required, MinLength(1)]
    public int Quantity { get; set; }
    
    [Required]
    public double Price { get; set; }
}