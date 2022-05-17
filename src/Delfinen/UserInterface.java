package Delfinen;

import Delfinen.Enums.Gender;
import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;
import Delfinen.Enums.SortDirection;

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
        case 1 -> startMemberDatabase();
        case 2 -> System.out.println("PLACEHOLDER: VIEW FINANCIAL DATA");
        case 3 -> System.out.println("PLACEHOLDER: EDIT TRAINING/COMPETITIVE DATA");
      }
    }
  }

  public int mainMenu() {
    System.out.println("""
        Main menu
        ---------
        1) Edit member-database
        2) View financial data
        3) Edit training/competitive data
        0) Exit application
        """);
    Scanner input = new Scanner(System.in);
    int choice = input.nextInt();
    while (choice < 0 || choice > 3) {
      System.out.println("Only values 0-3 allowed");
      choice = input.nextInt();
    }

    return choice;
  }

  public void startMemberDatabase() {
    boolean loop = true;
    //System.out.println("Welcome to Dolphinbase 2022");
    System.out.println("==========================");

    while (loop) {
      switch (menuMemberDatabase()) {
        case 0 -> loop = exitMemberDatabase();
        case 1 -> list();
        case 2 -> edit();
        case 3 -> sort();
        case 4 -> create();
        case 5 -> delete();
        case 6 -> load();
        case 7 -> save();
      }
    }
  }

  public int menuMemberDatabase() {
    System.out.println("""
        Menu
        ---------
        1) List all members
        2) Edit master data
        3) Sort list of members
        4) Create new member
        5) Delete member
        6) Load members from file
        7) Save members to file
        0) Exit to main menu
        """);
    Scanner input = new Scanner(System.in);
    int choice = input.nextInt();
    while (choice < 0 || choice > 7) {
      System.out.println("Only values 0-7 allowed");
      choice = input.nextInt();
    }

    return choice;
  }

  private boolean exitMemberDatabase() {
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    System.out.println("Saving database completed successfully");
    return false;
  }

  private void exit() {
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

  private void edit() {
    System.out.println("Edit member");
    System.out.println("-------------");
    System.out.println("Please enter the ID of the member to be edited: ");
    Scanner console = new Scanner(System.in);
    String id = console.nextLine();
    boolean editable = false;

    for (Member member : application.getMembers()) {
      if (member.getId().equals(id)) {
        System.out.println(member);
        System.out.println("""
        Editable data:
        1) Name
        2) Age
        3) Email
        4) Telephone
        5) Address
        6) Gender
        7) Type (Membership)
        8) Status (Membership)""");

        System.out.println("Please enter the data to be edited: ");
        String data = console.nextLine().trim().toLowerCase();
        switch (data) {
          case "1", "name" -> {
            System.out.print("Name: ");
            member.setName(console.nextLine());
          }
          case "2", "age" -> {
            System.out.print("Age: ");
            Integer tempAge = application.tryParseInt(console.nextLine());
            if(tempAge!=null)
              member.setAge(tempAge);
          }
          case "3", "email" -> {
            System.out.print("Email: ");
            member.setEmail(console.nextLine());
          }
          case "4", "telephone" -> {    System.out.print("Telephone: ");
            member.setTelephone(console.nextLine());}
          case "5", "address" -> {    System.out.print("Address: ");
            member.setAddress(console.nextLine());}
          case "6", "gender" -> {
            System.out.print("Gender (Male/Female): ");
            char genderChar = console.nextLine().toLowerCase().trim().charAt(0);
            if (genderChar == 'f') {
              member.setGender(Gender.FEMALE);
            } else if (genderChar == 'm') {
              member.setGender(Gender.MALE);
            }
          }
          case "7", "type" -> {
            System.out.print("Membership type (Competitive/Exercise): ");
            char typeChar = console.nextLine().toLowerCase().trim().charAt(0);
            if (typeChar == 'c') {
              member.setType(MembershipType.COMPETITIVE);
            } else if (typeChar == 'e') {
              member.setType(MembershipType.EXERCISE);
            }
          }
          case "8", "status" -> {
            System.out.print("Membership status (Active/Passive): ");
            char statusChar = console.nextLine().toLowerCase().trim().charAt(0);
            if (statusChar == 'a') {
              member.setStatus(MembershipStatus.ACTIVE);
            } else if (statusChar == 'p') {
              member.setStatus(MembershipStatus.PASSIVE);
            }
          }
        }
        System.out.println("The member with ID '" + id + "' has been edited");
        editable = true;
      }
    }
    if (!editable)
      System.out.println("Member with ID '" + id + "' does not exist, and cannot be edited");
  }

  private void sort() {
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
      case 'd' -> SortDirection.DESC;
      case 't' -> SortDirection.TOGGLE;
      default -> SortDirection.ASC;
    };

    switch (sortBy) {
      case 'a' -> application.sortBy("age", direction);
      case 'i' -> application.sortBy("id", direction);
      default -> application.sortBy("name", direction);
    }

    // When sorted, show the list again
    list();
  }

  private void create() {
    String ID = application.createUID();
    String dateRegistration = LocalDateTime.now().toString();
    Gender gender = null;
    MembershipStatus status = null;
    MembershipType type = null;

    System.out.println("Create new member\n-----------------");
    Scanner input = new Scanner(System.in);
    System.out.print("Name: ");
    String name = input.nextLine();
    System.out.print("Age: ");
    Integer age = application.tryParseInt(input.nextLine());
    System.out.print("Address: ");
    String address = input.nextLine();
    System.out.print("Email: ");
    String email = input.nextLine();
    System.out.print("Telephone number: ");
    String telephone = input.nextLine();
    System.out.print("Gender (Male/Female): ");
    char genderChar = input.nextLine().toLowerCase().trim().charAt(0);
    if (genderChar == 'f') {
      gender = Gender.FEMALE;
    } else if (genderChar == 'm') {
      gender = Gender.MALE;
    }
    System.out.print("Membership type (Competitive/Exercise): ");
    char typeChar = input.nextLine().toLowerCase().trim().charAt(0);
    if (typeChar == 'c') {
      type = MembershipType.COMPETITIVE;
    } else if (typeChar == 'e') {
      type = MembershipType.EXERCISE;
    }
    System.out.print("Membership status (Active/Passive): ");
    char statusChar = input.nextLine().toLowerCase().trim().charAt(0);
    if (statusChar == 'a') {
      status = MembershipStatus.ACTIVE;
    } else if (statusChar == 'p') {
      status = MembershipStatus.PASSIVE;
    }

    if (type == MembershipType.COMPETITIVE) {
      application.createNewCompetitiveMember(age, name, address, email, telephone, dateRegistration, ID, gender, type, status, new ArrayList<>(), new ArrayList<>());
    } else {
      application.createNewExerciseMember(age, name, address, email, telephone, dateRegistration, ID, gender, type, status);
    }

    // When created a new member, show the list again
    list();
  }

  private void delete() {
    System.out.println("Delete member");
    System.out.println("-------------");
    System.out.println("Please enter the ID of the member to be deleted: ");
    Scanner input = new Scanner(System.in);
    String id = input.nextLine();

    boolean success = application.deleteMember(id);
    if (success) {
      System.out.println("The member with ID '" + id + "' has been deleted");
    } else {
      System.out.println("Member with ID '" + id + "' does not exist, and cannot be deleted");
    }
  }


  private void load() {
    System.out.println("Loading the database ...");
    application.setMembers(application.deserializingJson(application.loadFromFile("MemberList.txt")));
    System.out.println("Loading database completed successfully");
  }

  private void save() {
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    System.out.println("Saving database completed successfully");
    //System.out.println("You can now exit the application");
  }

}
