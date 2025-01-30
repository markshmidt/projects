namespace groupApp.UI.Bookings
{
    internal class BookingMenu : BaseListMenu
    {
        public BookingMenu()
        {
            AddOption("Add Booking", AddBookingSelector);
            AddOption("Add Booking By Ids", AddBooking);
            AddOption("View Bookings", ViewBookings);
            AddOption("Delete Booking", DeleteBooking);
            AddOption("Back", GoBack);
        }

        private void AddBookingSelector(){
            UIController.AddPage(new AddBookingSelector());
        }

        private void AddBooking()
        {
            UIController.AddPage(new AddBookingPage());
        }

        private void ViewBookings()
        {
            UIController.AddPage(new BookingCollectionMenu((bookingId) => UIController.AddPage(new BookingInfoPage(bookingId))));
        }

        private void DeleteBooking()
        {
            const string confirmationMsg = "Are you sure you want to delete this booking?";
            void deleteBooking(int bookingId) => UIController.Coordinator.RemoveBooking(bookingId);
            UIController.AddPage(new BookingCollectionMenu((bookingId) => UIController.AddPage(new ConfirmationUIPage(confirmationMsg, () => IOUtils.SafeExecute( () => deleteBooking(bookingId))))));
        }
        private void GoBack()
        {
            Terminate();
        }
    }
}
