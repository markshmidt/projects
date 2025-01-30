using groupApp.entities;

namespace groupApp.managers
{
    public class FlightManager(int max) : BaseManager<Flight>(max)
    {
        private int currentId = 0;

        public void AddFlight(Flight flight)
        {
            this.AddEntity(flight);
            this.currentId = int.Max(currentId, flight.GetId());
        }

        public void AddFlight(string origin, string destination, string date, int maxPass)
        {
            var flight = new Flight(++currentId, origin, destination, date, maxPass, 0);
            this.AddEntity(flight);
        }

        public void RemoveFlight(int id)
        {
            var flight = this.GetById(id) ?? throw new InvalidOperationException("Flight not found.");
            if (flight.GetNumPass() > 0)
            {
                throw new InvalidOperationException("Cannot delete a flight with passengers booked.");
            }
            this.RemoveEntity(flight);
        }
    }
}
