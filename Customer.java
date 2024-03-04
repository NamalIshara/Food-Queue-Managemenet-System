package Example;

import java.io.FileWriter;
import java.io.IOException;

public class Customer {
    String firstName;
    String lastName;
    private int orderSize;
    public Customer(String firstName, String lastName, int orderSize) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderSize = orderSize;
    }


    public String getFullName() {
        return (this.firstName + " " + this.lastName);
    }

    public int getOrderSize() {
        return (this.orderSize); }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void writeCustomerToFile() {
        try {
            FileWriter writer = new FileWriter("data.txt", true);

            // Write queue
            writer.write(getFullName());
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error has occurred while writing your data to the file");
        }
    }
}

