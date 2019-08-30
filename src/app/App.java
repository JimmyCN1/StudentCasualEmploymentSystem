package app;

import driver.ManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class App {
  public ManagementSystem managementSystem;
  public Scanner scanner = new Scanner(System.in);
  
  public List<String> createUser() {
    List<String> userDetails = new ArrayList<>();
    System.out.println("What is your first name?");
    String firstName = scanner.nextLine();
    userDetails.add(firstName);
    System.out.println("What is your last name?");
    String lastName = scanner.nextLine();
    userDetails.add(lastName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    userDetails.add(password);
    return userDetails;
  }
}
