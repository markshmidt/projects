using InventoryManagementSystem.Data;
using InventoryManagementSystem.Models;
using Microsoft.EntityFrameworkCore;

namespace InventoryManagementSystem.Services;

public class CartService(ApplicationDbContext dbContext, IHttpContextAccessor httpContext)
{
    private string? GetSessionId() => httpContext.HttpContext?.Session.Id;

    public List<CartItem> GetCartItems(bool loadProducts = false)
    {
        var sessionId = GetSessionId();
        if (sessionId == null) return [];
        if (loadProducts)
            return dbContext.CartItems.Include(ci => ci.Product).Where(ci => ci.SessionId == sessionId).ToList();
        return dbContext.CartItems.Where(ci => ci.SessionId == sessionId).ToList();
    }

    public void AddToCart(Product product, int quantity)
    {
        if (quantity <= 0) return;
        var sessionId = GetSessionId();
        if (sessionId == null) return;
        var existingCartItem =
            dbContext.CartItems.FirstOrDefault(ci => ci.SessionId == sessionId && ci.ProductId == product.Id);
        if (existingCartItem != null)
            existingCartItem.Quantity += quantity;
        else
        {
            var newCartItem = new CartItem
            {
                SessionId = sessionId,
                ProductId = product.Id,
                Product = product,
                Quantity = quantity
            };
            dbContext.CartItems.Add(newCartItem);
        }

        dbContext.SaveChanges();
    }

    public void RemoveFromCart(Product product, int quantity)
    {
        if (quantity <= 0) return;
        var sessionId = GetSessionId();
        if (sessionId == null) return;
        var toRemove =
            dbContext.CartItems.FirstOrDefault(ci =>
                ci.SessionId == sessionId && ci.ProductId == product.Id);
        if (toRemove == null) return;
        if (toRemove.Quantity > quantity)
            toRemove.Quantity -= quantity;
        else
            dbContext.CartItems.Remove(toRemove);
        dbContext.SaveChanges();
    }

    public void ClearCart()
    {
        var sessionId = GetSessionId();
        var toRemove = dbContext.CartItems.Where(ci => ci.SessionId == sessionId).ToList();
        dbContext.CartItems.RemoveRange(toRemove);
        dbContext.SaveChanges();
    }
}