using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI.Customers
{
    internal class AddCustomerPage : BaseUIPage
    {
        public override void Run()
        {
            Console.Clear();
            PrintHeader();
            Console.WriteLine("Add Customer Page");
            Console.WriteLine("Enter Customer First Name:");
            String firstName = IOUtils.ReadString(NameValidators());
            Console.WriteLine("Enter Customer Last Name:");
            String lastName = IOUtils.ReadString(NameValidators());
            Console.WriteLine("Enter Customer Phone Number:");
            String phoneNumber = IOUtils.ReadString(PhoneNumberValidators());

            try
            {
                UIController.Coordinator.AddCustomer(firstName, lastName, phoneNumber);
            }
            catch (Exception e)
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine("Error adding customer: " + e.Message);
                Console.ForegroundColor = ConsoleColor.White;
                IOUtils.AwaitEnterToContinue();
            }

            this.Terminate();
        }

        private Action<string>[] NameValidators()
        {
            static void NameLength(string name)
            {
                if (name.Length < 2)
                {
                    throw new Exception("Name must be at least 2 characters long.");
                }
            }

            return [NameLength];
        }

        private Action<string>[] PhoneNumberValidators()
        {
            static void PhoneNumberLength(string phoneNumber)
            {
                if (phoneNumber.Length != 10)
                {
                    throw new Exception("Phone number must be 10 digits long.");
                }
            }

            static void PhoneNumberDigits(string phoneNumber)
            {
                if (!BigInteger.TryParse(phoneNumber, out _))
                {
                    throw new Exception("Phone number must contain only digits.");
                }
            }

            return [PhoneNumberLength, PhoneNumberDigits];
        }
    }
}
