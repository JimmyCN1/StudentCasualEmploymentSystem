package app;

//import employer.Employer;

import enums.Entity;

import java.util.Scanner;

public class Application {
  private static final int EMPLOYER = 1;
  private static final int STUDENT = 2;
  private static final int SYSTEM_MAINTENANCE_STAFF = 3;
  
  public static void main(String[] args) {
    
    StudentApp studentApp = new StudentApp();
    Scanner scanner = new Scanner(System.in);
    
    
    boolean validResonse = false;
    while (true) {
      System.out.println("~~~Welcome to the Student Casual Employment System~~~\n");
      System.out.println("Please type the corresponding number to determine who you are:");
      System.out.println("1. Employer\n2. Student\n3.System Maintenance Staff");
      
      int response = 0;
      response = scanner.nextInt();
      
      switch (response) {
        case (EMPLOYER):
          
          break;
        case (STUDENT):
          studentApp.selectStudentType();
          break;
        case (SYSTEM_MAINTENANCE_STAFF):
          
          break;
        default:
          
          break;
      }
    }
    
    
  }
  
  
}
