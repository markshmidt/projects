using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI
{
    internal abstract class BaseListMenu : BaseUIPage
    {
        protected List<MenuOption> options = [];
        protected int selectedOption = 0;
        protected int offset = 0;
        protected int countToPrint = 10;

        // This is a delegate that will be called every time the menu is updated
        // TODO: Problem if option count was reduced, the selected option may be out of bounds
        protected Action OnUpdate = () => { };

        protected String output = "";

        public override void Run()
        {
            while (!this.terminated)
            {
                Console.Clear();
                this.PrintHeader();
                this.PrintOptions();
                this.HandleInput();
                this.OnUpdate();
            }
        }

        private void PrintOptions()
        {
            Console.WriteLine(this.GetType().Name);
            for (int i = this.offset; i < Math.Min(this.offset + this.countToPrint, this.options.Count); i++)
            {
                if (i == this.selectedOption)
                {
                    Console.WriteLine("-> " + this.options[i]);
                }
                else
                {
                    Console.WriteLine("   " + this.options[i]);
                }
            }

            Console.WriteLine("Press Backspace to get back");
        }

        private void HandleInput()
        {
            // Read the key from the console
            ConsoleKeyInfo key = Console.ReadKey();

            // If the key is the up arrow
            if (key.Key == ConsoleKey.UpArrow)
            {
                this.selectedOption--;
                if (this.selectedOption < 0)
                {
                    this.selectedOption = this.options.Count - 1;
                }

                this.UpdateOffset();

                return;
            }

            // If the key is the down arrow
            if (key.Key == ConsoleKey.DownArrow)
            {
                this.selectedOption++;
                if (this.selectedOption >= this.options.Count)
                {
                    this.selectedOption = 0;
                }

                this.UpdateOffset();

                return;
            }

            // If the key is the enter key
            if (key.Key == ConsoleKey.Enter)
            {
                this.options[this.selectedOption].Resolve();
                this.output = "Command: " + this.selectedOption + " executed";
            }

            if (key.Key == ConsoleKey.Backspace)
            {
                this.Terminate();
            }
        }

        private void UpdateOffset()
        {
            if (this.selectedOption < this.offset)
            {
                this.offset = this.selectedOption;
            }

            if (this.selectedOption >= this.offset + this.countToPrint)
            {
                this.offset = this.selectedOption - this.countToPrint + 1;
            }
        }

        protected void AddOption(string optionName, Action callBack)
        {
            this.options.Add(new MenuOption(optionName, callBack));
        }
    }

    internal class MenuOption
    {
        private readonly string optionName;
        private readonly Action callBack;

        public MenuOption(string optionName, Action callBack)
        {
            this.optionName = optionName;
            this.callBack = callBack;
        }

        public override string ToString()
        {
            return this.optionName;
        }

        public void Resolve()
        {
            this.callBack();
        }
    }
}