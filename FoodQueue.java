package Example;

import java.io.FileWriter;
import java.io.IOException;

public class FoodQueue {

    Customer[] queue;
    private int maxSize;
    private int currentSize;
    private int currentIncome;
    public FoodQueue(int qSize) {
        this.maxSize = qSize;
        this.currentSize = 0;
        this.currentIncome = 0;
        this.queue = new Customer[maxSize];
    }

    public void addCustomer(Customer customer) {
        for (int i = 0; i < maxSize; i++) {
            if (this.queue[i] == null) {
                this.queue[i] = customer;
                currentSize++;
                return;
            }
        }
    }

    public Customer removeCustomer(int position) {
        try {
            Customer removedCustomer = this.queue[position];
            this.queue[position] = null;
            currentSize--;

            for (int i = position; i < currentSize; i++) {
                this.queue[i] = this.queue[i+1];
            }

            this.queue[currentSize] = null;

            return removedCustomer;
        } catch (Exception e) {
            return null;
        }
    }

    public Customer getCustomer(int position) {
        if (this.queue[position] == null) {
            return null;
        }
        return this.queue[position];
    }

    public String[] getAllCustomerNames() {
        String[] customerArray = new String[this.currentSize];
        for (int i = 0; i < this.currentSize; i++) {
            customerArray[i] = this.queue[i].getFullName();
        }
        return customerArray;
    }

    public void writeQueueToFile(String queueNumber) {
        try {
            FileWriter writer = new FileWriter("data.txt", true);

            writer.write(queueNumber + ":");
            if (this.currentSize == 0) {
                writer.write("\n");
                writer.close();
                return;
            }
            // Write queue
            for (int i = 0; i < this.currentSize; i++) {
                writer.write(this.queue[i].getFirstName() + "," + this.queue[i].getLastName() + "," + this.queue[i].getOrderSize() + ";");
            }
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error has occurred while writing your data to the file");
        }
    }

    public int calculateIncome(int orderSize) {
        this.currentIncome += orderSize * 650;
        return this.currentIncome;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public void printItemsInQueue() {
        for (int i = 0; i < this.queue.length; i++) {
            if (this.queue[i] != null) {
                System.out.println(this.queue[i]);
            }
        }
    }

    public boolean isOccupied(int i) {
        return (this.queue[i] != null) ? true : false;
    }
}

