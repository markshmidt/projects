using groupApp.UI;
using System;
using System.Linq.Expressions;

namespace groupApp;

public abstract class BaseUIPage {
    protected bool terminated = false;
    public abstract void Run();

    protected void PrintHeader() {
        Console.WriteLine("AIRLINE RESERVATION SYSTEM");
        Console.Write(new string(' ', Console.WindowWidth));
        UIController.StatusMessage.Display();
        Console.WriteLine("");
    }
    public void Terminate() {
        this.terminated = true;
    }

}