using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace groupApp.UI
{
    internal static class IOUtils
    {
        public static int ReadInt()
        {
            int result;
            while (!int.TryParse(Console.ReadLine(), out result))
            {
                Console.WriteLine("Invalid input. Please enter a number.");
            }
            return result;
        }

        public static int ReadInt(Action<int>[] validators)
        {
            while (true)
            {
                if (int.TryParse(Console.ReadLine(), out int result))
                {
                    try
                    {
                        foreach (var validator in validators)
                        {
                            validator(result);
                        }
                        return result;
                    }
                    catch (Exception e)
                    {
                        UIController.StatusMessage = new StatusMessage(e.Message, ConsoleColor.Yellow);
                        UIController.StatusMessage.Display();
                    }
                }
                else
                {
                    Console.WriteLine("Invalid input. Please enter a number.");
                }
                int currentLine = Console.CursorTop;
                Console.SetCursorPosition(0, currentLine - 1);
                Console.Write(new string(' ', Console.WindowWidth));
                Console.SetCursorPosition(0, currentLine - 1);
            }
        }

        public static double ReadDouble()
        {
            double result;
            while (!double.TryParse(Console.ReadLine(), out result))
            {
                Console.WriteLine("Invalid input. Please enter a number.");
            }
            return result;
        }

        public static string ReadString()
        {
            string? result = Console.ReadLine();
            return result ?? "";
        }

        public static string ReadString(Action<string>[] validators)
        {
            while (true)
            {
                string? result = Console.ReadLine();
                if (result != null)
                {
                    try
                    {
                        foreach (var validator in validators)
                        {
                            validator(result);
                        }
                        return result;
                    }
                    catch (Exception e)
                    {
                        UIController.StatusMessage = new StatusMessage(e.Message, ConsoleColor.Yellow);
                        UIController.StatusMessage.Display();
                    }
                }
                else
                {
                    Console.WriteLine("Invalid input. Please enter a string.");
                }

                int currentLine = Console.CursorTop;
                Console.SetCursorPosition(0, currentLine - 1);
                Console.Write(new string(' ', Console.WindowWidth));
                Console.SetCursorPosition(0, currentLine - 1);
            }
        }

        public static void AwaitEnterToContinue()
        {
            Console.WriteLine("Press Enter to continue...");
            Console.ReadLine();
        }

        public static void SafeExecute(Action action)
        {
            try
            {
                action();
                UIController.StatusMessage = new StatusMessage("Operation successful", ConsoleColor.Green);
                UIController.StatusMessage.Display();
            }
            catch (Exception e)
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine("Error: " + e.Message);
                Console.ForegroundColor = ConsoleColor.White;
                AwaitEnterToContinue();
                UIController.StatusMessage = new StatusMessage("Operation failed", ConsoleColor.Red);
                UIController.StatusMessage.Display();
            }
        }


        static public void NotEmpty(string input)
        {
            if (string.IsNullOrWhiteSpace(input))
            {
                throw new Exception("Input cannot be empty.");
            }
        }
    }
}
