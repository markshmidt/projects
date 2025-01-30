using System.ComponentModel;
using System.Text.Json.Serialization;

namespace groupApp.entities;

public class Flight : BaseEntity
{
    [JsonInclude]
    private string origin;
    [JsonInclude]
    private string destination;
    [JsonInclude]
    private int maxPass;
    [JsonInclude]
    private int numPass;
    [JsonInclude]
    private string date;

    public override event PropertyChangedEventHandler? PropertyChanged;

    [JsonConstructor]
    public Flight(int id, string origin, string destination, string date, int maxPass, int numPass) : base(id)
    {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.maxPass = maxPass;
        this.numPass = numPass;
    }

    public override string ToString()
    {
        return $"Flight ID: {GetId()}\nOrigin: {origin}\nDestination: {destination}\nMaximum number of passengers: {maxPass}\nNumber of passengers: {numPass}";
    }

    public string ToStringShort()
    {
        return $"{origin} -> {destination}";
    }

    public string GetOrigin()
    {
        return origin;
    }

    public void SetOrigin(string value)
    {
        this.origin = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(null));
    }

    public string GetDestination()
    {
        return destination;
    }

    public void SetDestination(string value)
    {
        this.destination = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(null));
    }

    public int GetMaxPass()
    {
        return maxPass;
    }

    public void SetMaxPass(int value)
    {
        this.maxPass = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(null));
    }

    public int GetNumPass()
    {
        return numPass;
    }

    public void SetNumPass(int value)
    {
        this.numPass = value;
        this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(null));
    }
}