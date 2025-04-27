namespace groupApp.UI.Bookings
{
    internal class BookingInfoPage : BaseUIPage
    {
        private readonly int bookingid;

        public BookingInfoPage(int bookingid)
        {
            this.bookingid = bookingid;
        }
        public override void Run()
        {
            Console.Clear();
            PrintHeader();
            Console.WriteLine("Booking Info Page");
            Console.WriteLine("");
            Console.WriteLine(UIController.Coordinator.GetBookingById(this.bookingid));
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
