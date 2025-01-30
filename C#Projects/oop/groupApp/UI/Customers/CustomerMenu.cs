using groupApp.UI.Customers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace groupApp.UI.Customers
{
    internal class CustomerMenu : BaseListMenu
    {
        public CustomerMenu()
        {
            this.AddOption("Add Customer", AddCustomer);
            this.AddOption("View Customers", ViewCustomers);
            this.AddOption("Delete Customer", DeleteCustomer);
            this.AddOption("Back", GoBack);
        }

        private void AddCustomer()
        {
            UIController.AddPage(new AddCustomerPage());
        }

        private void ViewCustomers()
        {
            UIController.AddPage(new CustomerCollectionMenu((customerId) => UIController.AddPage(new CustomerInfoPage(customerId))));
        }

        private void DeleteCustomer()
        {
            const string confirmationMsg = "Are you sure you want to delete this customer?";
            void deleteCustomer(int customerId) { UIController.Coordinator.RemoveCustomer(customerId); }
            UIController.AddPage(new CustomerCollectionMenu((customerId) => UIController.AddPage(new ConfirmationUIPage(confirmationMsg, () => IOUtils.SafeExecute(() => deleteCustomer(customerId))))));
        }

        private void GoBack()
        {
            Terminate();
        }
    }
}
