using System.Text.Json;

namespace groupApp.storage;

public class StorageInstance<T>
{
    private readonly string path;
    private FileStream? _fileStream;

    public StorageInstance(string path)
    {
        this.path = path;
        var directoryPath = Path.GetDirectoryName(this.path);

        if (string.IsNullOrEmpty(directoryPath))
        {
            throw new ArgumentNullException(nameof(path), "The path cannot be null or empty.");
        }

        if (!Directory.Exists(directoryPath))
        {
            Directory.CreateDirectory(directoryPath);
        }

        if (!File.Exists(path))
        {
            File.WriteAllText(path, "[]");
        }
        _fileStream = new FileStream(
            this.path,
            FileMode.Open,
            FileAccess.ReadWrite,
            FileShare.None
        );
    }
    ~StorageInstance()
    {
        if (_fileStream == null)
            return;

        _fileStream.Close();
        _fileStream = null;
    }

    public void Write(T[] input)
    {
        if (_fileStream == null)
            throw new IOException("File stream not found");
        _fileStream.SetLength(0);
        _fileStream.Position = 0;
        var writer = new Utf8JsonWriter(_fileStream);
        JsonSerializer.Serialize(writer, input);
        writer.Flush();
        writer.Dispose();
    }

    public T[] Read()
    {
        if (_fileStream == null)
            throw new IOException("File stream not found");
        _fileStream.Position = 0;
        var reader = new StreamReader(_fileStream, leaveOpen: true);
        string jsonString = reader.ReadToEnd();
        var list = JsonSerializer.Deserialize<T[]>(jsonString);
        reader.Dispose();
        return list ?? [];
    }
}
