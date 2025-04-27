using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI.Flights
{
    internal class FlightMenu : BaseListMenu
    {
        public FlightMenu()
        {
            AddOption("Add Flight", AddFlight);
            AddOption("View Flights", ViewFlights);
            AddOption("Delete Flight", DeleteFlight);
            AddOption("Back", GoBack);
        }

        private void AddFlight()
        {
            UIController.AddPage(new AddFlightPage());
        }

        private void ViewFlights()
        {
            UIController.AddPage(new FlightCollectionMenu((flightId) => UIController.AddPage(new FlightInfoPage(flightId))));
        }

        private void DeleteFlight()
        {
            const string confirmationMsg = "Are you sure you want to delete this flight?";
            void deleteFlight(int flightId) => UIController.Coordinator.RemoveFlight(flightId);
            UIController.AddPage(new FlightCollectionMenu((flightId) => UIController.AddPage(new ConfirmationUIPage(confirmationMsg, () => IOUtils.SafeExecute(() => deleteFlight(flightId))))));
        }

        private void GoBack()
        {
            Terminate();
        }
    }
}
