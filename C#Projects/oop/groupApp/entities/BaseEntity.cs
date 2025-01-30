using System.ComponentModel;
using System.Text.Json.Serialization;

namespace groupApp.entities;

abstract public class BaseEntity(int id) : INotifyPropertyChanged
{
    [JsonInclude]
    private readonly int id = id;
    abstract public event PropertyChangedEventHandler? PropertyChanged;

    public int GetId() => id;
}