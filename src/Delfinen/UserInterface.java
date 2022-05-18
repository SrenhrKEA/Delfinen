package Delfinen;

import Delfinen.Enums.*;

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
    System.out.println("==================================================");
    System.out.println("Java edition\n");
    boolean loop = true;

    while (loop) {
      switch (mainMenu()) {
        case 0 -> loop = exit();
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
        1) View member-database
        2) View financial data
        3) View training/competitive data
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
    System.out.println("==================================================");

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
        case 8 -> createResult((CompetitiveMember) findMember());
        case 9 -> showResult((CompetitiveMember) findMember());
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
    System.out.println("--------------------------------------------------");
    return false;
  }

  private boolean exit() {
    System.out.println("Thank you for using Dolphinbase 2022");
    return false;
  }

  private void list() {
    System.out.println("List of all the members");
    System.out.println("-------------------------");
    for (Member member : application.getAllMembers()) {
      displayMemberData(member, true, false, false);
    }
    System.out.println("There are " + application.getMemberCount() + " members in the list.");
    System.out.println("--------------------------------------------------");
  }

  private void edit() {
    System.out.println("Edit member");
    System.out.println("-------------------------");
    System.out.println("Please enter the ID of the member to be edited: ");
    Scanner console = new Scanner(System.in);
    String id = console.nextLine();
    boolean editable = false;

    for (Member member : application.getMembers()) {
      if (member.getId().equals(id)) {
        displayMemberData(member,true,false, false);
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
            if (tempAge != null)
              member.setAge(tempAge);
          }
          case "3", "email" -> {
            System.out.print("Email: ");
            member.setEmail(console.nextLine());
          }
          case "4", "telephone" -> {
            System.out.print("Telephone: ");
            member.setTelephone(console.nextLine());
          }
          case "5", "address" -> {
            System.out.print("Address: ");
            member.setAddress(console.nextLine());
          }
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
    System.out.println("--------------------------------------------------");
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

    System.out.println("Create new member");
    System.out.println("-------------------------");
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
    System.out.println("-------------------------");
    System.out.println("Please enter the ID of the member to be deleted: ");
    Scanner input = new Scanner(System.in);
    String id = input.nextLine();

    boolean success = application.deleteMember(id);
    if (success) {
      System.out.println("The member with ID '" + id + "' has been deleted");
    } else {
      System.out.println("Member with ID '" + id + "' does not exist, and cannot be deleted");
    }
    System.out.println("--------------------------------------------------");
  }


  private void load() {
    System.out.println("Loading the database ...");
    application.setMembers(application.deserializingJson(application.loadFromFile("MemberList.txt")));
    System.out.println("Loading database completed successfully");
    System.out.println("--------------------------------------------------");
  }

  private void save() {
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    System.out.println("Saving database completed successfully");
    System.out.println("--------------------------------------------------");
  }

  private void displayMemberData(Member member, boolean viewNormal, boolean viewCompetitive, boolean viewRestance) {
    System.out.printf("""
        ID:                      %s
        Name:                    %s
        Age:                     %s
        Gender:                  %s
        """, member.getId(), member.getName(), member.getAge(), member.getGender());
    if (viewNormal) {
      System.out.printf("""
          Date of registration:    %s
          Membership Type:         %s
          Membership Status:       %s
          Email:                   %s
          Telephone:               %s
          Address:                 %s
          """, member.getDateRegistration(), member.getType(), member.getStatus(), member.getEmail(), member.getTelephone(), member.getAddress());
    }
    if (viewCompetitive) {
      System.out.printf("""
          Attending disciplines:   %s
          Results:                 %s
          """, member.getDisciplines(), member.getResults());
    }
    if (viewRestance) {
      System.out.println("NOT IMPLEMENTED YET!");
    }
    System.out.println();
  }
  //TODO Find a member by name
  private Member findMember() {
    System.out.println("Enter a name");
    Scanner in = new Scanner(System.in);
    String name = in.nextLine();
    Member member;
    member = application.findMemberByName(name);
    return member;
  }

  //TODO Needs a member attached
  private void createResult(CompetitiveMember member) {
    System.out.println("Create a result for a member");
    System.out.println("----------------------------");
    System.out.printf("""
        Please select the Discipline in which the result was made.
        1) Butterfly
        2) Crawl
        3) Rygcrawl
        4) Brystsvømning
        """);
    Discipline discipline;
    discipline = null;
    Scanner in = new Scanner(System.in);
    switch (in.nextLine()) {
      case "1" -> discipline = Discipline.BUTTERFLY;
      case "2" -> discipline = Discipline.CRAWL;
      case "3" -> discipline = Discipline.RYGCRAWL;
      case "4" -> discipline = Discipline.BRYSTSVØMNING;
    }

    System.out.printf("""
        Was the result made within a tournament?
        1) Yes
        2) No
        """);

    boolean isTournament = true;
    switch (in.nextLine()) {
      case "1" -> isTournament = true;
      case "2" -> isTournament = false;
    }

    String tournament;
    tournament = null;
    String ranking;
    ranking = null;
    if (isTournament) {
      System.out.println("in which tournament did the contestant participate?");
      tournament = in.nextLine();

      System.out.println("Which place did the contestant make in the tournament?");
      ranking = in.nextLine();
    }

    System.out.println("Insert time [Minutes:Seconds.Milliseconds]");
    System.out.println("Ex. 9:30.08");
    String time;
    time = in.nextLine();

    System.out.println("Insert date [Day/Month/Year]");
    String date;
    date = in.nextLine();

    Result result;
    if (isTournament)
      result = new Result(discipline, tournament, ranking, time, date);
    else
      result = new Result(discipline, time, date);

    member.addResult(result);
  }
  //TODO showResult()
  public void showResult(CompetitiveMember member) {
    System.out.println(member.getName());
    for (int i = 0; i < member.getResults().size(); i++) {
      System.out.println(member.getResults().get(i).getTime());
    }
  }
  //TODO find top5
  public void findTop5() {
    System.out.printf("""
        Choose a discipline for which you want to show the top 5 swimmers
        1) Butterfly
        2) Crawl
        3) Rygcrawl
        4) Brystsvømning
        """);
    Scanner in = new Scanner(System.in);
    Discipline discipline;
    switch (in.nextLine()) {
      case "1" -> discipline = Discipline.BUTTERFLY;
      case "2" -> discipline = Discipline.CRAWL;
      case "3" -> discipline = Discipline.RYGCRAWL;
      case "4" -> discipline = Discipline.BRYSTSVØMNING;
    }

    ArrayList<Member> swimmers = new ArrayList<>();
    ArrayList<Result> bestResult = new ArrayList<>();

    for (int i = 0; i < application.getMembers().size(); i++) {
      CompetitiveMember member = (CompetitiveMember) application.getMembers().get(i);
      swimmers.add(member);
      for (int j = 0; j < member.getResults().size(); j++) {
        Result result = member.getResults().get(j);

      }
    }

  }

  //TODO maybe implement methods
  /*
  public Discipline chooseDiscipline() {
    Scanner in = new Scanner(System.in);
    Discipline discipline;
    switch (in.nextLine()) {
      case "1" -> discipline = Discipline.BUTTERFLY;
      case "2" -> discipline = Discipline.CRAWL;
      case "3" -> discipline = Discipline.RYGCRAWL;
      case "4" -> discipline = Discipline.BRYSTSVØMNING;
    }
    return discipline;
  }
   */
  /*
  public int checkNumberIsWithinRange(int min, int max, int choice) {
    while (choice < min || choice > max) {
      System.out.println("Only values 0-7 allowed"); //setup to fit min + max
      Scanner in = new Scanner(System.in);
      choice = in.nextInt();
    }
    return choice;
  }
   */
}
