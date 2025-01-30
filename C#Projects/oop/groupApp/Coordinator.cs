using groupApp.entities;
using groupApp.managers;
using groupApp.storage;

namespace groupApp
{
    public class Coordinator
    {
        private readonly CustomerManager customerManager;
        private readonly FlightManager flightManager;
        private readonly BookingManager bookingManager;

        public Coordinator(int customerCapacity, int flightCapacity, int bookingCapacity)
        {
            customerManager = new CustomerManager(customerCapacity);
            flightManager = new FlightManager(flightCapacity);
            bookingManager = new BookingManager(bookingCapacity);

            // Reading storage once on init
            try
            {
                var persistedCustomers = LocalStorage.GetInstance<Customer>().Read().Where(i => i != null).ToArray();
                foreach (var item in persistedCustomers)
                {
                    customerManager.AddCustomer(item);
                }

                var persistedFlights = LocalStorage.GetInstance<Flight>().Read().Where(i => i != null).ToArray();
                foreach (var item in persistedFlights)
                {
                    flightManager.AddFlight(item);
                }

                var persistedBookings = LocalStorage.GetInstance<Booking>().Read().Where(i => i != null).ToArray();
                foreach (var item in persistedBookings)
                {
                    var flight = flightManager.GetById(item.GetFlightId()) ?? throw new InvalidDataException($"Booking had flight id: {item.GetId()}, was not found. Corrupted data possible.");
                    var customer = customerManager.GetById(item.GetCustomerId()) ?? throw new InvalidDataException($"Booking had customer id: {item.GetId()}, was not found. Corrupted data possible.");
                    item.SetCustomer(customer);
                    item.SetFlight(flight);
                    bookingManager.AddBooking(item);
                }
            }
            catch (InvalidDataException)
            {
                throw new InvalidDataException("Managers cannot accept persisted values. Increase manager max limit or check if data corrupted");
            }
        }

        public void AddCustomer(string firstName, string lastName, string phone)
        {
            customerManager.AddCustomer(firstName, lastName, phone);
        }

        public void RemoveCustomer(int id)
        {
            customerManager.RemoveCustomer(id);
        }

        public string? GetCustomerById(int id)
        {
            return customerManager.GetById(id)?.ToString();
        }

        public List<KeyValuePair<int, string>> GetAllCustomers()
        {
            List<KeyValuePair<int, string>> customers = [];
            foreach (var customer in customerManager.Entities)
            {
                customers.Add(new KeyValuePair<int, string>(customer.GetId(), customer.ToStringShort()));
            }
            return customers;
        }


        //Flight
        public void AddFlight(string origin, string destination, string date, int maxPass)
        {
            flightManager.AddFlight(origin, destination, date, maxPass);
        }

        public void RemoveFlight(int id)
        {
            flightManager.RemoveFlight(id);
        }


        public void RemoveBooking(int id)
        {
            bookingManager.RemoveBooking(id);
        }

        public string? GetFlightById(int id)
        {
            return flightManager.GetById(id)?.ToString();
        }
        public List<KeyValuePair<int, string>> GetAllFlights()
        {
            List<KeyValuePair<int, string>> flights = [];
            foreach (var flight in flightManager.Entities)
            {
                flights.Add(new KeyValuePair<int, string>(flight.GetId(), flight.ToStringShort()));
            }
            return flights;
        }

        public List<KeyValuePair<int, string>> GetFlightPassengers(int flightId)
        {
            Booking[] bookingsForFlight = bookingManager.GetBookingByFlightId(flightId);

            List<KeyValuePair<int, string>> customers = [];
            foreach (var booking in bookingsForFlight)
            {
                customers.Add(new KeyValuePair<int, string>(booking.GetId(), booking.GetCustomer()?.ToStringShort() ?? "~"));
            }
            return customers;
        }

        //Booking
        public void AddBooking(int flightId, int customerId)
        {
            var flight = flightManager.GetById(flightId) ?? throw new InvalidOperationException("Flight not found");
            var customer = customerManager.GetById(customerId) ?? throw new InvalidOperationException("Customer not found.");
            bookingManager.AddBooking(flight, customer);
        }

        public Booking? GetBookingById(int id)
        {
            return bookingManager.GetById(id);
        }
        public List<KeyValuePair<int, string>> GetAllBookings()
        {
            List<KeyValuePair<int, string>> bookings = [];
            foreach (var booking in this.bookingManager.Entities)
            {
                bookings.Add(new KeyValuePair<int, string>(booking.GetId(), booking.ToStringShort()));
            }
            return bookings;
        }

    }
}
