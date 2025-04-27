using groupApp.UI.Customers;
using groupApp.UI.Flights;

namespace groupApp.UI.Bookings
{
    internal class AddBookingSelector : BaseUIPage
    {   
        private int customerId;
        private int flightId;
        public override void Run()
        {
            UIController.AddPage(new CustomerCollectionMenu(CustomerSelectorHandler));
            UIController.AddPage(new FlightCollectionMenu(FlightSelectorHandler));


            IOUtils.SafeExecute(() => UIController.Coordinator.AddBooking(flightId, customerId));

            Terminate();
        }

        private void CustomerSelectorHandler(int customerId)
        {
            this.customerId = customerId;

            string message = $"Selected Customer: {customerId}";
            UIController.StatusMessage = new StatusMessage(message, ConsoleColor.Green);
            UIController.StatusMessage.Display();
            UIController.RemovePage();
        }

        private void FlightSelectorHandler(int flightId)
        {
            this.flightId = flightId;

            string message = $"Selected Flight: {flightId}";
            UIController.StatusMessage = new StatusMessage(message, ConsoleColor.Green);
            UIController.StatusMessage.Display();
            UIController.RemovePage();
        }
    }
}
