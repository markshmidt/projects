namespace groupApp.storage;

public static class LocalStorage
{
    private static object[] instances = [];

    public static StorageInstance<T> GetInstance<T>()
    {
        try
        {
            var instance = instances.First(i =>
            {
                return i.GetType() == typeof(StorageInstance<T>);
            });
            return (StorageInstance<T>)instance;
        }
        catch (InvalidOperationException)
        {
            var instance = new StorageInstance<T>(GeneratePath(typeof(T)));
            var nLen = instances.Length + 1;
            var nInstances = new object[nLen];
            Array.Copy(instances, nInstances, instances.Length);
            nInstances[nLen-1] = instance;
            instances = nInstances;
            return instance;
        }
    }

    private static string GeneratePath(Type t) => "data/" + t.Name + ".json";
}