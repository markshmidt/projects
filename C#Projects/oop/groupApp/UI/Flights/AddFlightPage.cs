using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI.Flights
{
    internal class AddFlightPage : BaseUIPage
    {
        public override void Run()
        {
            Console.Clear();
            PrintHeader();
            Console.WriteLine("Add Flight Origin");
            string origin = IOUtils.ReadString([IOUtils.NotEmpty]);
            Console.WriteLine("Enter Flight Destination:");
            string destination = IOUtils.ReadString([IOUtils.NotEmpty]);
            Console.WriteLine("Enter Flight Date:");
            string date = IOUtils.ReadString([IOUtils.NotEmpty]);
            Console.WriteLine("Enter Flight Seats:");
            int seats = IOUtils.ReadInt(SeatsNumberVaslidators());

            IOUtils.SafeExecute(() => UIController.Coordinator.AddFlight(origin, destination, date, seats));

            Terminate();
        }

        private Action<int>[] SeatsNumberVaslidators()
        {
            static void MinCount(int count)
            {
                if (count <= 0)
                {
                    throw new Exception("Flight seats must be at least 1.");
                }
            }

            return [MinCount];
        }
    }
}
