using groupApp.entities;

namespace groupApp.managers;

public class CustomerManager(int max) : BaseManager<Customer>(max)
{
    private int currentId = 0;

    public void AddCustomer(Customer customer)
    {
        this.AddEntity(customer);
        currentId = int.Max(currentId, customer.GetId());
    }

    public void AddCustomer(string firstName, string lastName, string phone, int bookCount = 0)
    {
        if (Entities.Any(c =>
                c.GetFirstName() == firstName &&
                c.GetLastName() == lastName &&
                c.GetPhone() == phone))
        {
            throw new InvalidOperationException("Customer with the same name and phone already exists.");
        }

        var customer = new Customer(++currentId, firstName, lastName, phone, bookCount);
        this.AddEntity(customer);
    }


    public void RemoveCustomer(int id)
    {
        var customer = this.GetById(id) ?? throw new InvalidOperationException("Customer not found.");
        if (customer.GetBookCount() > 0)
        {
            throw new InvalidOperationException("Cannot remove a customer with bookings.");
        }
        this.RemoveEntity(customer);
    }

}
