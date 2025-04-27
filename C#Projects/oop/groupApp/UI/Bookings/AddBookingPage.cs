namespace groupApp.UI.Bookings
{
    internal class AddBookingPage : BaseUIPage
    {
        public override void Run()
        {
            Console.Clear();
            PrintHeader();
            Console.WriteLine("Add Booking Page");
            Console.WriteLine("Enter Customer ID:");
            int customerId = IOUtils.ReadInt();
            Console.WriteLine("Enter Flight ID:");
            int flightId = IOUtils.ReadInt();

            IOUtils.SafeExecute(() => UIController.Coordinator.AddBooking(flightId, customerId));

            Terminate();
        }
    }
}
