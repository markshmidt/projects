using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI.Customers
{
    internal class CustomerInfoPage : BaseUIPage
    {
        private readonly int customerId;

        public CustomerInfoPage(int customerId)
        {
            this.customerId = customerId;
        }
        public override void Run()
        {
            Console.Clear();
            PrintHeader();
            Console.WriteLine("Customer Info Page");
            Console.WriteLine("");
            Console.WriteLine(UIController.Coordinator.GetCustomerById(this.customerId));
            Console.WriteLine("");
            Console.WriteLine("Press Backspace to return to the main menu");
            while (!this.terminated)
            {
                ConsoleKeyInfo key = Console.ReadKey();

                if (key.Key == ConsoleKey.Backspace)
                {
                    this.Terminate();
                }
            }
        }
    }
}
