using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI.Flights
{
    internal class FlightCollectionMenu : BaseListMenu
    {
        public FlightCollectionMenu(Action<int> handler)
        {
            foreach (KeyValuePair<int, string> kvp in UIController.Coordinator.GetAllFlights())
            {
                string name = $"{kvp.Key}: {kvp.Value}";
                this.options.Add(new MenuOption(name, () => handler(kvp.Key)));
            }

            this.OnUpdate = () =>
            {
                this.options.Clear();
                foreach (KeyValuePair<int, string> kvp in UIController.Coordinator.GetAllFlights())
                {
                    string name = $"{kvp.Key}: {kvp.Value}";
                    this.options.Add(new MenuOption(name, () => handler(kvp.Key)));
                }
                // Dirty hack
                // TODO: redo the OnOpdate logic
                if (this.selectedOption >= this.options.Count)
                {
                    this.selectedOption = this.options.Count - 1;
                }
            };
        }

    }
}
