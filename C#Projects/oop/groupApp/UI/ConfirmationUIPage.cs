using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI
{
    internal class ConfirmationUIPage : BaseUIPage
    {
        private readonly Action handler;
        private readonly string msg;
        public ConfirmationUIPage(string msg, Action handler)
        {
            this.msg = msg;
            this.handler = handler;
        }

        public override void Run()
        {
            Console.Clear();
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine(msg);
            Console.ForegroundColor = ConsoleColor.White;
            Console.WriteLine("Press Y to confirm or N to cancel");
            while (!this.terminated)
            {
                this.HandleInput();
            }
        }

        private void HandleInput()
        {
            ConsoleKeyInfo key = Console.ReadKey();

            if (key.Key == ConsoleKey.Y)
            {
                handler();
                this.Terminate();
            }
            else if (key.Key == ConsoleKey.N)
            {
                this.Terminate();
            }
        }
    }
}
