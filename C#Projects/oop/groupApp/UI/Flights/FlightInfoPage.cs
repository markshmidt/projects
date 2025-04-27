using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI.Flights
{
    internal class FlightInfoPage : BaseUIPage
    {
        private readonly int flightid;

        public FlightInfoPage(int flightid)
        {
            this.flightid = flightid;
        }
        public override void Run()
        {
            Console.Clear();
            PrintHeader();
            Console.WriteLine("Flight Info Page");

            Console.WriteLine("");
            Console.WriteLine(UIController.Coordinator.GetFlightById(this.flightid));
            Console.WriteLine("");
            var passengers = UIController.Coordinator.GetFlightPassengers(this.flightid);

            Console.WriteLine("Passegers:");
            foreach (var kvp in passengers)
            {
                Console.WriteLine($"{kvp.Key}: {kvp.Value}");
            }
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
