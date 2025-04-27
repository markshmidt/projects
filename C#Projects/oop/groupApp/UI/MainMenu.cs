using groupApp.UI.Bookings;
using groupApp.UI.Customers;
using groupApp.UI.Flights;

namespace groupApp.UI
{
    internal class MainMenu : BaseListMenu
    {
        public MainMenu()
        {
            this.options.Add(new MenuOption("Customers", () => UIController.AddPage(new CustomerMenu())));
            this.options.Add(new MenuOption("Flights", () => UIController.AddPage(new FlightMenu())));
            this.options.Add(new MenuOption("Bookings", () => UIController.AddPage(new BookingMenu())));
            this.options.Add(new MenuOption("Exit", () => Terminate()));
        }
    }
}
