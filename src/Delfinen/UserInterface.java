package Delfinen;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
  private final Controller application;

  public UserInterface(Controller application) {
    this.application = application;
  }

  public void start() {
    System.out.println("Welcome to Dolphinbase 2022");
    System.out.println("==========================");
    System.out.println("Java edition\n");

    while (true) {
      switch (mainMenu()) {
        case 0 -> exit();
        case 1 -> list();
        case 2 -> filter();
        case 3 -> sort();
        case 4 -> create();
        case 5 -> delete();
        case 6 -> load();
        case 7 -> save();
      }
    }
  }

  public int mainMenu() {
    System.out.println("""
        Main menu
        ---------
        1) List all members
        2) Filter list of members
        3) Sort list of members
        4) Create new member
        5) Delete member
        6) Load members from file
        7) Save members to file
        0) Exit application
        """);
    Scanner input = new Scanner(System.in);
    int choice = input.nextInt();
    while (choice < 0 || choice > 7) {
      System.out.println("Only values 0-7 allowed");
      choice = input.nextInt();
    }

    return choice;
  }

  private void exit() {
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    application.saveToFile(String.valueOf(application.getIdCounter()), "IdCounter.txt");
    System.out.println("Saving database completed successfully");
    System.out.println("Thank you for using Dolphinbase 2022");
    System.exit(0);
  }

  private void list() {
    System.out.println("List of all the members");
    System.out.println("-----------------------");
    for (Member member : application.getAllMembers()) {
      System.out.println(member);
    }
    System.out.println("There are " + application.getMemberCount() + " members in the list.");
  }

  private void filter() {
    System.out.println("-- filtering not yet implemented ---");
  }

  private void sort() {
    //System.out.println("-- sorting not yet implemented ---");


    System.out.println("""
        Sort the list of members by
        n) Name
        a) Age
        i) ID
        """);
    Scanner input = new Scanner(System.in);
    char sortBy = input.next().trim().toLowerCase().charAt(0);
    while (sortBy != 'n' && sortBy != 'a' && sortBy != 'i') {
      System.out.println("Please type 'n', 'a' or 'i'");
      sortBy = input.next().trim().toLowerCase().charAt(0);
    }

    System.out.println("""
        Set the sort direction:
        a) Ascending (0-9 a-z)
        d) Descending (9-0 z-a)
        t) Toggle (The opposite of what it was last time)
        """);

    char ch = input.next().trim().toLowerCase().charAt(0);
    while (ch != 'a' && ch != 'd' && ch != 't') {
      System.out.println("Please type 'a', 'd' or 't'");
      ch = input.next().trim().toLowerCase().charAt(0);
    }

    SortDirection direction = switch (ch) {
      case 'a' -> SortDirection.ASC;
      case 'd' -> SortDirection.DESC;
      case 't' -> SortDirection.TOGGLE;
      default -> SortDirection.ASC;
    };
/*
    if (sortBy == 'n') {
      application.sortBy("name", direction);
    } else if (sortBy == 'a') {
      application.sortBy("age", direction);
    } else if (sortBy == 'i') {
      application.sortBy("id", direction);
    }

 */
    switch (sortBy) {
      case 'n' -> application.sortBy("name", direction);
      case 'a' -> application.sortBy("age", direction);
      case 'i' -> application.sortBy("id", direction);
      default -> application.sortBy("name", direction);
    }

    // When sorted, show the list again
    list();
  }

  private void create() {
    System.out.println("Create new member\n-----------------");
    Scanner input = new Scanner(System.in);
    System.out.print("Name: ");
    String name = input.nextLine();
    System.out.print("Age: ");
    int age = input.nextInt();
    input.nextLine(); // ScannerBug fix
    String dateRegistration = LocalDateTime.now().toString();
    System.out.print("Male Gender (true/false): ");
    boolean genderMale = Boolean.parseBoolean(input.nextLine());
    System.out.print("Active membership (true/false): ");
    boolean membershipActive = Boolean.parseBoolean(input.nextLine());
    System.out.print("Competitive membership (true/false): ");
    boolean membershipCompetitive = Boolean.parseBoolean(input.nextLine());
    String ID = application.createID();

    if (membershipCompetitive && membershipActive) {
      application.createNewCompetitiveMember(age, name, dateRegistration, ID, genderMale, new ArrayList<Result>(), new ArrayList<Discipline>());
    } else if (membershipActive) {
      application.createNewActiveMember(age, name, dateRegistration, ID, genderMale);
    } else
      application.createNewMember(age, name, dateRegistration, ID, genderMale);

    // When created a new member, show the list again
    list();
  }

  private void delete() {
    System.out.println("Delete member");
    System.out.println("-------------");
    System.out.println("Please enter the name of the member to be deleted: ");
    Scanner input = new Scanner(System.in);
    String name = input.nextLine();

    boolean success = application.deleteMember(name);
    if (success) {
      System.out.println("The member with name '" + name + "' has been deleted");
    } else {
      System.out.println("Member with name '" + name + "' does not exist, and cannot be deleted");
    }
  }


  private void load() {
    System.out.println("Loading the database ...");
    application.setMembers(application.deserializingJson(application.loadFromFile("MemberList.txt")));
    application.setIdCounter(application.tryParseLong(application.loadFromFile("IdCounter.txt")));
    System.out.println("Loading database completed successfully");
  }

  private void save() {
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    application.saveToFile(String.valueOf(application.getIdCounter()), "IdCounter.txt");
    System.out.println("Saving database completed successfully");
    //System.out.println("You can now exit the application");
  }


}
