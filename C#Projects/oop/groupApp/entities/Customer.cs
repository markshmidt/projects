using System.ComponentModel;
using System.Text.Json.Serialization;

namespace groupApp.entities;

public class Customer : BaseEntity
{
    [JsonInclude]
    private string firstName;
    [JsonInclude]
    private string lastName;
    [JsonInclude]
    private string phone;
    [JsonInclude]
    private int bookCount;

    public override event PropertyChangedEventHandler? PropertyChanged;

    public Customer(int id, string firstName, string lastName, string phone, int bookCount = 0) : base(id)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.bookCount = bookCount;
    }

    public override string ToString()
    {
        return $"First Name: {firstName}\nLast Name: {lastName}\nPhone: {phone}";
    }

    public string ToStringShort()
    {
        return $"{firstName} {lastName}";
    }

    public string GetFirstName()
    {
        return firstName;
    }

    public void SetFirstName(string value)
    {
        this.firstName = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("firstName"));
    }

    public string GetLastName()
    {
        return lastName;
    }

    public void SetLastName(string value)
    {
        this.lastName = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("lastName"));
    }

    public string GetPhone()
    {
        return phone;
    }

    public void SetPhone(string value)
    {
        this.phone = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("phone"));
    }

    public int GetBookCount()
    {
        return bookCount;
    }

    public void SetBookCount(int value)
    {
        this.bookCount = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("bookCount"));
    }
}

