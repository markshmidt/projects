using groupApp.entities;

namespace groupApp.managers
{
    public class BookingManager(int max) : BaseManager<Booking>(max)
    {
        private int currentId = 0;

        public void AddBooking(Booking booking)
        {
            this.AddEntity(booking);
            this.currentId = int.Max(currentId, booking.GetId());
        }
        public void AddBooking(Flight flight, Customer customer)
        {
            if (flight.GetNumPass() >= flight.GetMaxPass())
            {
                throw new InvalidOperationException("Flight is at full capacity.");
            }

            string date = DateTime.Now.ToString(@"MM\/dd\/yyyy h\:mm tt");

            var booking = new Booking(++currentId, flight, customer, date);
            this.AddEntity(booking);
            flight.SetNumPass(flight.GetNumPass() + 1);
            customer.SetBookCount(customer.GetBookCount() + 1);
        }

        public Booking[] GetBookingByFlightId(int flightId)
        {
            return Entities.Where(b => b.GetFlight()?.GetId() == flightId).ToArray();
        }

        public void RemoveBooking(int id)
        {
            var booking = this.GetById(id) ?? throw new InvalidOperationException("Booking not found.");
            booking.GetCustomer()?.SetBookCount((booking.GetCustomer()?.GetBookCount() ?? 1) - 1);
            booking.GetFlight()?.SetNumPass((booking.GetFlight()?.GetNumPass() ?? 1) - 1);
            RemoveEntity(booking);
        }
    }
}
