using groupApp.entities;
using groupApp.storage;

namespace groupApp.managers;

public abstract class BaseManager<T>(int max) where T : BaseEntity
{
    private readonly T[] _entities = new T[max];
    private readonly int _max = max;
    private int _last = 0;

    public int Count => _last;
    public T[] Entities => _entities[.._last];
    public int Max => _max;

    protected void AddEntity(T entity)
    {
        if (this._last >= this._max)
        {
            throw new InvalidOperationException("Entities limit reached");
        }
        if (this.GetById(entity.GetId()) != null)
        {
            throw new ArgumentException("Trying to add entity with duplicate id");
        }
        this.ApplyChangeWatcher(entity);
        this._entities[_last++] = entity;
        LocalStorage.GetInstance<T>().Write(_entities);
    }

    protected void RemoveEntity(T entity)
    {
        ArgumentNullException.ThrowIfNull(entity);

        int index = Array.IndexOf(_entities, entity, 0, _last);
        if (index == -1)
            throw new InvalidOperationException("Entity not found");

        this.RemoveAtIndex(index);
        LocalStorage.GetInstance<T>().Write(_entities);
    }

    private void RemoveAtIndex(int index)
    {
        if (index < 0 || index > _last)
            throw new ArgumentOutOfRangeException(nameof(index));
        if (index == _last)
        {
            this._entities[index] = default!;
        }
        else
        {
            this._entities[index] = this._entities[--_last];
            this._entities[_last] = default!;
        }
        LocalStorage.GetInstance<T>().Write(_entities);
    }

    public T? GetById(int id)
    {
        return this._entities.FirstOrDefault(e => e != null && e.GetId() == id);
    }

    private void ApplyChangeWatcher(T entity)
    {
        entity.PropertyChanged += (sender, e) =>
                {
                    LocalStorage.GetInstance<T>().Write(_entities);
                };
    }
}
