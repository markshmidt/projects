namespace groupApp.UI.Bookings
{
    internal class BookingCollectionMenu : BaseListMenu
    {
        public BookingCollectionMenu(Action<int> handler)
        {
            foreach (KeyValuePair<int, string> kvp in UIController.Coordinator.GetAllBookings())
            {
                string name = $"{kvp.Key}: {kvp.Value}";
                this.options.Add(new MenuOption(name, () => handler(kvp.Key)));
            }

            this.OnUpdate = () =>
            {
                this.options.Clear();
                foreach (KeyValuePair<int, string> kvp in UIController.Coordinator.GetAllBookings())
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
