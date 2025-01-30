using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI
{
    internal static class UIController
    {
        static private readonly Stack<BaseUIPage> Stack = new();
        static public Coordinator Coordinator { get; set; }

        static public StatusMessage StatusMessage = new("", ConsoleColor.White);

        static public void Init()
        {
            //Console.CursorVisible = false;
            Console.Clear();
            UIController.AddPage(new MainMenu());
        }

        static public void AddPage(BaseUIPage page)
        {
            Stack.Push(page);
            Stack.Peek().Run();
        }

        static public void RemovePage()
        {
            Stack.Pop().Terminate();
        }
    }

    internal class StatusMessage
    {
        public string Message { get; }
        public ConsoleColor Color { get; }

        public StatusMessage(string message, ConsoleColor color = ConsoleColor.White)
        {
            this.Message = message;
            this.Color = color;
        }

        public void Display()
        {
            var (Left, Top) = Console.GetCursorPosition();
            // Second line of the UI
            Console.SetCursorPosition(0, 1);
            Console.Write("Status: ");
            Console.ForegroundColor = UIController.StatusMessage.Color;
            Console.WriteLine(UIController.StatusMessage.Message);
            Console.ForegroundColor = ConsoleColor.White;
            // Reset the cursor position
            Console.SetCursorPosition(Left, Top);
        }
    }
}
