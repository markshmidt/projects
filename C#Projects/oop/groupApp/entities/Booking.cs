using System.ComponentModel;
using System.Text.Json.Serialization;

namespace groupApp.entities;

public class Booking : BaseEntity
{
    [JsonInclude]
    private readonly int flightId;

    [JsonInclude]
    private readonly int customerId;

    [JsonInclude]
    private readonly string date;

    private Flight? flight;
    private Customer? customer;

    public override event PropertyChangedEventHandler? PropertyChanged;

    [JsonConstructor]
    public Booking(int id, int flightId, int customerId, string date) : base(id)
    {
        this.flightId = flightId;
        this.customerId = customerId;
        this.date = date;
    }


    public Booking(int id, Flight flight, Customer customer, string date) : base(id)
    {
        this.flight = flight;
        this.customer = customer;
        this.flightId = flight.GetId();
        this.customerId = customer.GetId();
        this.date = date;
    }

    public override string ToString()
    {
        return $"Booking ID: {GetId()}\n" +
               $"Flight: {GetFlight()?.GetOrigin()} -> {GetFlight()?.GetDestination()}\n" +
               $"Customer: {GetCustomer()?.GetFirstName()} {GetCustomer()?.GetLastName()}\n";
    }

    public string ToStringShort()
    {
        string flightString = GetFlight()?.ToStringShort() ?? "~";
        string customerString = GetCustomer()?.ToStringShort() ?? "~";
        return $"Flight: {flightString} | {customerString}";
    }

    public int GetFlightId()
    {
        return flightId;
    }

    public int GetCustomerId()
    {
        return customerId;
    }

    public Flight? GetFlight()
    {
        return flight;
    }

    public void SetFlight(Flight? value)
    {
        this.flight = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(null));
    }

    public Customer? GetCustomer()
    {
        return customer;
    }

    public void SetCustomer(Customer? value)
    {
        this.customer = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(null));
    }
}