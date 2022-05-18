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
        case 2 -> filter();
        case 3 -> sort();
        case 4 -> create();
        case 5 -> delete();
        case 6 -> load();
        case 7 -> save();
        case 8 -> {
          Member member = findMember();
          if (member instanceof CompetitiveMember) {
            createResult((CompetitiveMember) member);
            System.out.println(member.getClass());
          }
        }
        case 9 -> showResult((CompetitiveMember) findMember());
      }
    }
  }

  public int menuMemberDatabase() {
    System.out.println("""
        Menu
        ---------
        1) List all members
        2) Filter list of members
        3) Sort list of members
        4) Create new member
        5) Delete member
        6) Load members from file
        7) Save members to file
        0) Exit to main menu
        """);
    Scanner input = new Scanner(System.in);
    int choice = input.nextInt();
    //Change number below to fit final amount of options
    while (choice < 0 || choice > 9) {
      System.out.println("Only values 0-7 allowed");
      choice = input.nextInt();
    }

    return choice;
  }

  private boolean exitMemberDatabase() {
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    application.saveToFile(String.valueOf(application.getIdCounter()), "IdCounter.txt");
    System.out.println("Saving database completed successfully");
    return false;
  }

  private void exit() {
    /*
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    application.saveToFile(String.valueOf(application.getIdCounter()), "IdCounter.txt");
    System.out.println("Saving database completed successfully");

     */
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
    String ID = application.createID();
    String dateRegistration = LocalDateTime.now().toString();
    System.out.println("Create new member\n-----------------");
    Scanner input = new Scanner(System.in);
    System.out.print("Name: ");
    String name = input.nextLine();
    System.out.print("Age: ");
    int age = input.nextInt();
    input.nextLine(); // ScannerBug fix
    System.out.print("Address: ");
    String address = input.nextLine();
    System.out.print("Email: ");
    String email = input.nextLine();
    System.out.print("Telephone number: ");
    String telephone = input.nextLine();
    System.out.print("Male Gender (true/false): ");
    boolean genderMale = Boolean.parseBoolean(input.nextLine());
    System.out.print("Active membership (true/false): ");
    boolean membershipActive = Boolean.parseBoolean(input.nextLine());
    System.out.print("Competitive membership (true/false): ");
    boolean membershipCompetitive = Boolean.parseBoolean(input.nextLine());

    if (membershipCompetitive && membershipActive) {
      application.createNewCompetitiveMember(age, name, address, email, telephone, dateRegistration, ID, genderMale, new ArrayList<>(), new ArrayList<>());
    } else if (membershipActive) {
      application.createNewActiveMember(age, name, address, email, telephone, dateRegistration, ID, genderMale);
    } else
      application.createNewMember(age, name, address, email, telephone, dateRegistration, ID, genderMale);

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
        Was the result made within a convention?
        1) Yes
        2) No
        """);

    boolean isConvention = true;
    switch (in.nextLine()) {
      case "1" -> isConvention = true;
      case "2" -> isConvention = false;
    }

    String convention;
    convention = null;
    String placement;
    placement = null;
    if (isConvention) {
      System.out.println("in which convention did the contestant participate?");
      convention = in.nextLine();

      System.out.println("Which place did the contestant make in the convention?");
      placement = in.nextLine();
    }

    System.out.println("Insert time [Minutes:Seconds.Milliseconds]");
    System.out.println("Ex. 9:30.08");
    String time;
    time = in.nextLine();

    System.out.println("Insert date [Day/Month/Year]");
    String date;
    date = in.nextLine();

    Result result;
    if (isConvention)
      result = new Result(discipline, convention, placement, time, date);
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
