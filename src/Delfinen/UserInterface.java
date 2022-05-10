package Delfinen;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
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
        //case 6 -> load();
        //case 7 -> save();
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
    // TODO: Maybe save before exiting???
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
    System.out.println("-- filter not yet implemented ---");
  }

  private void sort() {
    System.out.println("""
                Sort the list of members by
                n) Name
                t) Type
                a) Age
                w) Weight
                """);
    Scanner input = new Scanner(System.in);
    char sortBy = input.next().trim().toLowerCase().charAt(0);
    while (sortBy != 'n' && sortBy != 't' && sortBy != 'a' && sortBy != 'w') {
      System.out.println("Please type 'n', 't', 'a' or 'w'");
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

    if (sortBy == 'n') {
      application.sortBy("name", direction);
    } else if (sortBy == 't') {
      application.sortBy("type", direction);
    } else if (sortBy == 'a') {
      application.sortBy("age", direction);
    }

    // When sorted, show the list again
    list();
  }

  private void create() {
    System.out.println("Create new member\n-----------------");
    Scanner input = new Scanner(System.in);
    System.out.print("Age: ");
    int age = input.nextInt();
    input.nextLine(); // ScannerBug fix
    System.out.print("Name: ");
    String name = input.nextLine();
    String dateRegistration = LocalDateTime.now().toString();
    System.out.print("Male Gender (true/false): ");
    boolean genderMale = Boolean.parseBoolean(input.nextLine());
    System.out.print("Active membership (true/false): ");
    boolean membershipActive = Boolean.parseBoolean(input.nextLine());
    System.out.print("Junior membership (true/false): ");
    boolean membershipJunior = Boolean.parseBoolean(input.nextLine());
    System.out.print("Competitive membership (true/false): ");
    boolean membershipCompetitive = Boolean.parseBoolean(input.nextLine());

    application.createNewMember(age, name, dateRegistration, genderMale, membershipActive, membershipJunior, membershipCompetitive);

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

  /*
  private void load() throws FileNotFoundException {
    System.out.println("Loading the database ...");
    application.loadDatabase();
  }

  private void save() throws FileNotFoundException {
    System.out.println("Saving the database ...");
    application.saveDatabase();
    System.out.println("Saving database completed succesfully");
    System.out.println("You can now exit the application");
  }

   */
}
