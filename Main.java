package Example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String menu() {
        String[] menuList = {"VFQ or 100: View all Queues", "VEQ or 101: View all Empty Queues", "ACQ or 102: Add customer to a Queue", "RCQ or 103: Remove customer from a Queue", "PCQ or 104: Remove a served Customer", "VCS or 105: View Customers Sorted in alphabetical order", "SPD or 106: Store Program Data into file", "LPD or 107: Load Program Data from file", "STK or 108: View Remaining pizza Stock", "AFS or 109: Add pizza to the Stock", "IFQ or 110: Income of Queue", "EXT or 999: Exit the Program"};
        for (int i = 0; i < menuList.length; i++) {
            System.out.println(menuList[i]);
        }

        Scanner input = new Scanner(System.in);

        System.out.println("Your desired option: ");
        String currentOption = input.nextLine();
        return currentOption;
    }

    static void VFQ(FoodQueue queue1, FoodQueue queue2, FoodQueue queue3) {
        // Implementation to view all queues
        System.out.println("************");
        System.out.print("*");
        System.out.print(" Cashiers ");
        System.out.print("*\n");
        System.out.println("************");
        System.out.println("  " + ((queue1.isOccupied(0))?"O":"X") + "  "       + ((queue2.isOccupied(0))?"O":"X") + "  " + ((queue3.isOccupied(0))?"O":"X"));
        System.out.println("  " + ((queue1.isOccupied(1))?"O":"X") + "  "       + ((queue2.isOccupied(1))?"O":"X") + "  " + ((queue3.isOccupied(1))?"O":"X"));
        System.out.println("  " + " "                                 + "  "       + ((queue2.isOccupied(2))?"O":"X") + "  " + ((queue3.isOccupied(2))?"O":"X"));
        System.out.println("  " + " "                                 + "  "       + " "                                 + "  " + ((queue3.isOccupied(3))?"O":"X"));
        System.out.println("  " + " "                                 + "  "       + " "                                 + "  " + ((queue3.isOccupied(4))?"O":"X"));
        System.out.println("X - Not Occupied O - Occupied");
    }


    static void VEQ(FoodQueue queue1, FoodQueue queue2, FoodQueue queue3) {
        // Implementation to view all empty queues
        System.out.println("Viewing all empty queues...");
        System.out.println("************");
        System.out.print("*");
        System.out.print(" Cashiers ");
        System.out.print("*\n");
        System.out.println("************");
        System.out.println(" " + ((queue1.isOccupied(0))?" ":"Q1") + " " + ((queue3.isOccupied(0))?" ":"Q2") + " " + ((queue3.isOccupied(0))?" ":"Q3"));
        System.out.println("  " + ((queue1.isOccupied(0))?" ":"X") + "  "       + ((queue2.isOccupied(0))?" ":"X") + "  " + ((queue3.isOccupied(0))?" ":"X"));
        System.out.println("  " + ((queue1.isOccupied(0))?" ":"X") + "  "       + ((queue2.isOccupied(0))?" ":"X") + "  " + ((queue3.isOccupied(0))?" ":"X"));
        System.out.println("  " + " "                                 + "  "       + ((queue2.isOccupied(0))?" ":"X") + "  " + ((queue3.isOccupied(0))?" ":"X"));
        System.out.println("  " + " "                                 + "  "       + " "                                 + "  " + ((queue3.isOccupied(0))?" ":"X"));
        System.out.println("  " + " "                                 + "  "       + " "                                 + "  " + ((queue3.isOccupied(0))?" ":"X"));
    }

    static void ACQ(FoodQueue queue1, FoodQueue queue2, FoodQueue queue3, List waitingList) {
        // Implementation to add a customer to a queue
        System.out.println("Adding a customer to a queue...");

        Scanner input = new Scanner(System.in);

        // Get customer first name
        System.out.println("Enter customer first name: ");
        String firstName = input.nextLine();

        // Get customer last name
        System.out.println("Enter customer last name: ");
        String lastName = input.nextLine();

        // Get customer orderSize
        System.out.println("Enter customer order size: ");
        int orderSize = input.nextInt();

        // Create customer
        Customer customer = new Customer(firstName, lastName, orderSize);

        String selectedQueue;

        // Add customer to queue based on length
        if (queue1.getCurrentSize() != queue1.getMaxSize()) {
            selectedQueue = "queue1";
            if (queue1.getCurrentSize() < queue1.getMaxSize()) {
                queue1.addCustomer(customer);
            }
        } else if (queue2.getCurrentSize() != queue2.getMaxSize()) {
            selectedQueue = "queue2";
            if (queue2.getCurrentSize() < queue2.getMaxSize()) {
                queue2.addCustomer(customer);
            }
        } else if (queue3.getCurrentSize() != queue3.getMaxSize()) {
            selectedQueue = "queue3";
            if (queue3.getCurrentSize() < queue3.getMaxSize()) {
                queue3.addCustomer(customer);
            }
        } else {
            waitingList.add(customer);
            System.out.println("All queues are currently at maximum capacity. Customer has been added to the waiting list.");
            return;
        }

        System.out.println(customer.firstName + " " + customer.lastName + " has been added to " + selectedQueue);
    }

    static void RCQ(Scanner input, FoodQueue queue) {
        // Implementation to remove a customer from a queue

        // Enter the position of the customer to be removed
        System.out.println("Enter the position of the customer to be removed: ");
        int customerPosition = input.nextInt();

        Customer removedCustomer;

        // Remove the customer from the selected queue
        removedCustomer = queue.removeCustomer(customerPosition);

        if (removedCustomer != null) {
            System.out.println(removedCustomer.getFullName() + " has been removed.");
        } else {
            System.out.println("Operation was unsuccessful. Please recheck your inputs.");
        }
    }

    static int PCQ(int pizzStock, Scanner input, FoodQueue queue, int queueNumber, List waitingList) {
        // Implementation to remove a served customer
        System.out.println("Removing a served customer...");

        // Check if the queue is empty
        if (queue.getCurrentSize() == 0) {
            System.out.println("The queue is empty.");
            return -1;
        }

        // Get the customer to be served and removed
        Customer servedCustomer = queue.getCustomer(0);
        // Get the orderSize of the customer to be served and removed
        int customerOrderSize = servedCustomer.getOrderSize();

        // Check if the order can be served
        if (pizzStock < customerOrderSize) {
            System.out.println("Pizza stock is too low. Cannot serve the customer. Please add more pizzas to the stock.");
            return -1;
        }

        // Remove the served customer at index 0
        queue.removeCustomer(0);

        // Reduce the pizza stock according to customers order size
        pizzStock -= customerOrderSize;

        // Calculate the income of serving the customer
        queue.calculateIncome(customerOrderSize);

        System.out.println(servedCustomer.getFullName() + " has been served " + customerOrderSize + " pizzas and removed from queue" + queueNumber + " successfully." );

        // Check if there are customers in the waiting list
        if (!waitingList.isEmpty()) {
            Customer newCustomer = (Customer) waitingList.get(0);
            queue.addCustomer(newCustomer);
            System.out.println(newCustomer.getFullName() + " in the waiting list has been added to queue" + queueNumber + " successfully.");
        }

        return pizzStock;
    }

    static void VCS(FoodQueue queue1, FoodQueue queue2, FoodQueue queue3) {
        // Implementation to view customers sorted in alphabetical order
        System.out.println("Viewing customers sorted in alphabetical order...");

        List list = new ArrayList(Arrays.asList(queue1.getAllCustomerNames()));
        list.addAll(Arrays.asList(queue2.getAllCustomerNames()));
        list.addAll(Arrays.asList(queue3.getAllCustomerNames()));
        Object[] customers = list.toArray();
        Arrays.sort(customers);
        for (int i = 0; i < customers.length; i++) {
            System.out.println(customers[i]);
        }
        System.out.println("************");
    }

    static void SPD(FoodQueue queue1, FoodQueue queue2, FoodQueue queue3, int pizzStock, List waitingList) {
        // Implementation to store program data into a file
        System.out.println("Storing program data into a file...");

        // Write queue to file
        try {
            FileWriter writer = new FileWriter("data.txt");
            writer.write("---------------------------------\n");
            writer.write("Food queues\n");
            writer.write("---------------------------------\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
        queue1.writeQueueToFile("Queue1");
        queue2.writeQueueToFile("Queue2");
        queue3.writeQueueToFile("Queue3");

        // Write waiting queue to file
        try {
            FileWriter writer = new FileWriter("data.txt", true);
            writer.write("\n---------------------------------\n");
            writer.write("Waiting Queue\n");
            writer.write("---------------------------------\n");
            for (int  i = 0; i < waitingList.size(); i++) {
                Customer waitingListCustomer = (Customer) waitingList.get(i);
                writer.write(waitingListCustomer.getFullName() + "," + waitingListCustomer.getLastName() + "," + waitingListCustomer.getOrderSize() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error occurred while writing data.");
        }


        // Write customers to file
        try {
            FileWriter writer = new FileWriter("data.txt", true);
            writer.write("\n---------------------------------\n");
            writer.write("Customers\n");
            writer.write("---------------------------------\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error");
        }

        for (int i = 0; i < queue1.getCurrentSize(); i++) {
            Customer currentCustomer = queue1.getCustomer(i);
            currentCustomer.writeCustomerToFile();
        }

        for (int i = 0; i < queue2.getCurrentSize(); i++) {
            Customer currCustomer = queue2.getCustomer(i);
            currCustomer.writeCustomerToFile();
        }

        for (int i = 0; i < queue3.getCurrentSize(); i++) {
            Customer currCustomer = queue3.getCustomer(i);
            currCustomer.writeCustomerToFile();
        }

        try {
            FileWriter writer = new FileWriter("data.txt", true);
            // Write burger stock to file
            writer.write("\n---------------------------------\n");
            writer.write("Pizza Stock\n");
            writer.write("---------------------------------\n");
            writer.write(pizzStock+"\n");

            // Write queue income to file
            writer.write("\n---------------------------------\n");
            writer.write("Income\n");
            writer.write("---------------------------------\n");
            writer.write("Queue1:"+queue1.getCurrentIncome()+"\n");
            writer.write("Queue2:"+queue2.getCurrentIncome()+"\n");
            writer.write("Queue3:"+queue3.getCurrentIncome()+"\n");
            writer.write("---------------------------------\n");
            writer.write("Total Income:"+(queue1.getCurrentIncome()+ queue2.getCurrentIncome()+ queue3.getCurrentIncome()));
            writer.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    static int LPD(FoodQueue queue1, FoodQueue queue2, FoodQueue queue3, int pizzStock, List waitingList) {
        // Implementation to load program data from a file
        System.out.println("Loading program data from a file...");

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));

            // Read and ignore the "Food queues" line
            reader.readLine();
            reader.readLine();
            reader.readLine();

            // Read the queue data
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                if (line.equals("----------------------------------")) {
                    break;
                }
                String[] queueData = line.split(":");
                try {
                    String[] customerData = queueData[1].split(";");
                    if (queueData[0].equals("Queue1")) {
                        for (int i = 0; i < customerData.length; i++) {
                            String[] currCustomerData;
                            currCustomerData = customerData[i].split(",");
                            queue1.addCustomer(new Customer(currCustomerData[0], currCustomerData[1], Integer.parseInt(currCustomerData[2])));
                        }
                    } else if (queueData[0].equals("Queue2")) {
                        for (int i = 0; i < customerData.length; i++) {
                            String[] currCustomerData;
                            currCustomerData = customerData[i].split(",");
                            queue2.addCustomer(new Customer(currCustomerData[0], currCustomerData[1], Integer.parseInt(currCustomerData[2])));
                        }
                    } else {
                        for (int i = 0; i < customerData.length; i++) {
                            String[] currCustomerData;
                            currCustomerData = customerData[i].split(",");
                            queue3.addCustomer(new Customer(currCustomerData[0], currCustomerData[1], Integer.parseInt(currCustomerData[2])));
                        }
                    }
                } catch (Exception e) {}

                line = reader.readLine();
            }

            line = reader.readLine();

            // Read waiting list data
            while (!line.equals("Waiting Queue")) {
                line = reader.readLine();
            }
            reader.readLine();
            line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                if (line.equals("---------------------------------")) {break;}

                String[] waitingListData = line.split(",");
                Customer newCustomer = new Customer(waitingListData[0], waitingListData[1], Integer.parseInt(waitingListData[2]));
                waitingList.add(newCustomer);

                line = reader.readLine();
            }

            // Read pizzStock data
            while (!line.equals("Pizza Stock")) {
                line = reader.readLine();
            }
            reader.readLine();
            line = reader.readLine();
            int currentPizzStock = Integer.parseInt(line);

            System.out.println("Stock data entered");

            // Read income data
            while (!line.equals("Income")) {
                line = reader.readLine();
            }
            reader.readLine();
            line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                if (line.equals("---------------------------------")) {break;}
                String[] incomeData = line.split(":");

                if (incomeData[0].equals("Queue1")) {
                    queue1.setCurrentIncome(Integer.parseInt(incomeData[1]));
                } else if (incomeData[0].equals("Queue2")) {
                    queue2.setCurrentIncome(Integer.parseInt(incomeData[1]));
                } else {
                    queue3.setCurrentIncome(Integer.parseInt(incomeData[1]));
                }
                line = reader.readLine();
            }

            reader.close();
            System.out.println("Program data loaded successfully.");
            return currentPizzStock;
        } catch (IOException e) {
            System.out.println("Error occurred while loading program data.");
            e.printStackTrace();
        }
        return -1;
    }

    static void STK(int pizzStock) {
        // Implementation to view remaining pizza stock
        System.out.println("Viewing remaining pizza stock...");
        System.out.println("Current pizza stock: " + pizzStock);
    }

    static int AFS(Scanner input, int pizzStock) {
        // Implementation to add pizza to stock
        System.out.println("Adding pizza to stock...");

        if (pizzStock == 100) {
            System.out.println("You cannot add more pizza to stock currently. Pizza stock is at maximum capacity (" + pizzStock + ").");
            return -1;
        }

        System.out.println("Enter the number of pizza to be added to the stock: ");
        int newPizza = input.nextInt();
        pizzStock += newPizza;
        System.out.println(newPizza + " has been added to the pizza stock. Current pizza stock is " + pizzStock);
        return pizzStock;

    }
    static void IFQ(FoodQueue queue, int queueNumber) {
        // Implementation to print the income of each queue
        System.out.println("Viewing the income of a queue...");
        System.out.println("Current income of queue" + queueNumber + ": " + queue.getCurrentIncome());

    }


    static Customer getCustomer(FoodQueue queue) {
        return queue.getCustomer(0);
    }
    public static void main(String[] args) {

        FoodQueue queue1 = new FoodQueue(2);
        FoodQueue queue2 = new FoodQueue(3);
        FoodQueue queue3 = new FoodQueue(5);
        List<Customer> waitingList = new ArrayList<>();
        int pizzStock = 100;

        Scanner input = new Scanner(System.in);

        boolean run = true;
        while (run) {

            // Warning when pizza stock is less than 20.
            if (pizzStock < 20) {
                System.out.println("\n********** WARNING !!!! **********");
                System.out.println("pizza stock is less than 20. Current burger stock: " + pizzStock);
                System.out.println("********** WARNING !!!! **********\n");
            }

            String option = menu();

            switch (option.toUpperCase()) {
                case "VFQ":
                case "100":
                    VFQ(queue1, queue2, queue3);
                    break;
                case "VEQ":
                case "101":
                    VEQ(queue1, queue2, queue3);
                    break;
                case "ACQ":
                case "102":
                    ACQ(queue1, queue2, queue3, waitingList);
                    System.out.println("************");
                    break;
                case "RCQ":
                case "103":
                    System.out.println("Removing a customer from a queue...");
                    FoodQueue queue;
                    System.out.println("Enter the queue to remove a customer from (1, 2, 3): ");
                    int queueNumber = input.nextInt();

                    if (queueNumber == 1) {
                        queue = queue1;
                    } else if (queueNumber == 2) {
                        queue = queue2;
                    }
                    else {
                        queue = queue3;
                    }
                    RCQ(input, queue);
                    System.out.println("************");
                    break;
                case "PCQ":
                case "104":
                    System.out.println("Enter the queue to remove a served customer from (1, 2, 3): ");
                    queueNumber = input.nextInt();
                    if (queueNumber == 1) {
                        queue = queue1;
                    } else if (queueNumber == 2) {
                        queue = queue2;
                    }
                    else {
                        queue = queue3;
                    }
                    int newPizzaStock = PCQ(pizzStock, input, queue, queueNumber, waitingList);
                    if (newPizzaStock != -1) {
                        pizzStock = newPizzaStock;
                    }
                    System.out.println("************");
                    break;
                case "VCS":
                case "105":
                    VCS(queue1, queue2, queue3);
                    System.out.println("************");
                    break;
                case "SPD":
                case "106":
                    SPD(queue1,queue2,queue3,pizzStock, waitingList);
                    break;
                case "LPD":
                case "107":
                    newPizzaStock = LPD(queue1,queue2,queue3,pizzStock, waitingList);
                    if (newPizzaStock != -1) {
                        pizzStock = newPizzaStock;
                    }
                    break;
                case "STK":
                case "108":
                    STK(pizzStock);
                    System.out.println("************");
                    break;
                case "AFS":
                case "109":
                    newPizzaStock = AFS(input, pizzStock);
                    if (newPizzaStock != -1) {
                        pizzStock = newPizzaStock;
                    }
                    System.out.println("************");
                    break;
                case "IFQ":
                case "110":
                    System.out.println("Enter the queue of which the income should be calculated (1, 2, 3): ");
                    queueNumber = input.nextInt();
                    if (queueNumber == 1) {
                        queue = queue1;
                    } else if (queueNumber == 2) {
                        queue = queue2;
                    } else {
                        queue = queue3;
                    }
                    IFQ(queue, queueNumber);
                    break;
                case "EXT":
                case "999":
                    System.out.println("Exiting the program...");
                    run = false;
                    System.exit(0);
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }

        return;
    }
}
