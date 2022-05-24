package Delfinen;

import Delfinen.Enums.*;
import dnl.utils.text.table.TextTable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class UserInterface {
  private final Controller application;

  //Text color
  private final String TEXT_RESET = "\u001B[0m";
  private final String TEXT_RED = "\u001B[31m";
  private final String TEXT_GREEN = "\u001B[32m";

  public UserInterface(Controller application) {
    this.application = application;
  }

  public void start() {
    System.out.println("Welcome to Dolphinbase 2022");
    printDoubleLine();
    System.out.println("Java edition\n");
    boolean loop = true;

    while (loop) {
      switch (mainMenu()) {
        case 0 -> loop = exit();
        case 1 -> startMemberDatabase();
        case 2 -> financeCalculator();//System.out.println("PLACEHOLDER: VIEW FINANCIAL DATA");
        case 3 -> startCompetetiveDatabase();
        default -> printDoubleLine();
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
    Integer choice = application.tryParseInt(input.nextLine());
    if (choice == null)
      choice = 99;
    while (choice < 0 || choice > 3) {
      System.out.println("Only values 0-3 allowed");
      choice = application.tryParseInt(input.nextLine());
    }

    return choice;
  }

  public void startMemberDatabase() {
    boolean loop = true;
    printDoubleLine();

    while (loop) {
      switch (menuMemberDatabase()) {
        case 0 -> loop = exitDatabase();
        case 1 -> list();
        case 2 -> edit();
        case 3 -> sort();
        case 4 -> create();
        case 5 -> delete();
        case 6 -> load();
        case 7 -> save();
        default -> printDoubleLine();
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
        6) Reload members from file
        7) Save members to file
        0) Exit to main menu
        """);
    Scanner input = new Scanner(System.in);
    Integer choice = application.tryParseInt(input.nextLine());
    if (choice == null)
      choice = 99;
    while (choice < 0 || choice > 7) {
      System.out.println("Only values 0-7 allowed");
      choice = application.tryParseInt(input.nextLine());
    }

    return choice;
  }

  private boolean exitDatabase() {
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    System.out.println("Saving database completed successfully");
    printSingleLineLong();
    return false;
  }

  private boolean exit() {
    System.out.println("Thank you for using Dolphinbase 2022");
    return false;
  }

  private void list() {
    System.out.println("List of all the members");
    printSingleLineShort();
    for (Member member : application.getAllMembers()) {
      displayMemberData(member, true, false);
    }
    System.out.println("There are " + application.getMemberCount() + " members in the list.");
    printSingleLineLong();
  }

  private void edit() {
    System.out.println("Edit member");
    printSingleLineShort();
    System.out.println("Please enter the ID of the member to be edited: ");
    Scanner console = new Scanner(System.in);
    String id = console.nextLine();
    boolean editable = false;

    for (Member member : application.getMembers()) {
      if (member.getMasterData().getId().equals(id)) {
        displayMemberData(member, true, false);
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
            member.getMasterData().setName(console.nextLine());
          }
          case "2", "age" -> {
            System.out.print("Age: ");
            Integer tempAge = application.tryParseInt(console.nextLine());
            if (tempAge != null)
              member.getMasterData().setAge(tempAge);
          }
          case "3", "email" -> {
            System.out.print("Email: ");
            member.getMasterData().setEmail(console.nextLine());
          }
          case "4", "telephone" -> {
            System.out.print("Telephone: ");
            member.getMasterData().setTelephone(console.nextLine());
          }
          case "5", "address" -> {
            System.out.print("Address: ");
            member.getMasterData().setAddress(console.nextLine());
          }
          case "6", "gender" -> {
            System.out.print("Gender (Male/Female): ");
            char genderChar = console.nextLine().toLowerCase().trim().charAt(0);
            if (genderChar == 'f') {
              member.getMasterData().setGender(Gender.FEMALE);
            } else if (genderChar == 'm') {
              member.getMasterData().setGender(Gender.MALE);
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
    printSingleLineLong();
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

    SortDirection direction = sortDirection();

    switch (sortBy) {
      case 'a' -> application.sortBy("age", direction);
      case 'i' -> application.sortBy("id", direction);
      default -> application.sortBy("name", direction);
    }

    // When sorted, show the list again
    list();
  }

  private SortDirection sortDirection() {
    Scanner input = new Scanner(System.in);
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

    return switch (ch) {
      case 'd' -> SortDirection.DESC;
      case 't' -> SortDirection.TOGGLE;
      default -> SortDirection.ASC;
    };
  }

  private void create() {
    String ID = application.createUID();
    String dateRegistration = LocalDateTime.now().toString();
    Gender gender = null;
    MembershipStatus status = null;
    MembershipType type = null;

    System.out.println("Create new member");
    printSingleLineShort();
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
    MasterData masterData = new MasterData(age, name, ID, email, telephone, address, dateRegistration, gender);
    Member member;
    if (type == MembershipType.COMPETITIVE) {
      member = application.createCompetitiveMember(masterData, type, status, new ArrayList<>(), new ArrayList<>());

    } else {
      member = application.createExerciseMember(masterData, type, status);

    }
    application.getMembers().add(member);

    // When created a new member, show the list again
    list();
  }

  private void delete() {
    System.out.println("Delete member");
    printSingleLineShort();
    System.out.println("Please enter the ID of the member to be deleted: ");
    Scanner input = new Scanner(System.in);
    String id = input.nextLine();

    boolean success = application.deleteMember(id);
    if (success) {
      System.out.println("The member with ID '" + id + "' has been deleted");
    } else {
      System.out.println("Member with ID '" + id + "' does not exist, and cannot be deleted");
    }
    printSingleLineLong();
  }

  private void load() {
    System.out.println("Loading the database ...");
    application.setMembers(application.deserializingJson(application.loadFromFile("MemberList.txt")));
    System.out.println("Loading database completed successfully");
    printSingleLineLong();
  }

  private void save() {
    System.out.println("Saving the database ...");
    application.saveToFile(application.serializingJson(), "MemberList.txt");
    System.out.println("Saving database completed successfully");
    printSingleLineLong();
  }

  public void displayMemberData(Member member, boolean viewNormal, boolean viewArrears) {
    System.out.printf("""
        ID:                      %s
        Name:                    %s
        Age:                     %s
        Gender:                  %s
        """, member.getMasterData().getId(), member.getMasterData().getName(), member.getMasterData().getAge(), member.getMasterData().getGender());
    if (viewNormal) {
      System.out.printf("""
          Date of registration:    %s
          Membership Type:         %s
          Membership Status:       %s
          Email:                   %s
          Telephone:               %s
          Address:                 %s
          """, member.getMasterData().getDateRegistration(), member.getType(), member.getStatus(), member.getMasterData().getEmail(), member.getMasterData().getTelephone(), member.getMasterData().getAddress());
    }
    if (viewArrears) {
      System.out.println("NOT IMPLEMENTED YET!");
    }
    printSingleLineShort();
  }

  public void startCompetetiveDatabase() {
    boolean loop = true;
    printDoubleLine();

    while (loop) {
      switch (menuCompetetiveDatabase()) {
        case 0 -> loop = exitDatabase();
        case 1 -> findTop5();
        case 2 -> displayTeams();
        case 3 -> displayResultsByDiscipline((CompetitiveMember) findCompetetiveMember());
        case 4 -> createResult((CompetitiveMember) findCompetetiveMember());
        case 5 -> printNumberedResults((CompetitiveMember) findCompetetiveMember());
        case 6 -> printDisciplines((CompetitiveMember) findCompetetiveMember(), false);
        case 7 -> editCompetitorDisciplines((CompetitiveMember) findCompetetiveMember());
      }
    }
  }

  public int menuCompetetiveDatabase() {
    System.out.println("""
        Menu
        ---------
        1) Show top 5 results specific to a discipline
        2) Show teams roster
        3) Show all results specific to a member
        4) Create a result
        5) Delete a result
        6) Show a competitors Disciplines
        7) Edit a Competitors Disciplines
        0) Exit to main menu
        """);
    Scanner input = new Scanner(System.in);
    Integer choice = application.tryParseInt(input.nextLine());
    if (choice == null)
      choice = 99;
    while (choice < 0 || choice > 7) {
      System.out.println("Only values 0-5 allowed");
      choice = input.nextInt();
    }

    return choice;
  }

  public Member findCompetetiveMember() {
    boolean loop = true;
    Member member = null;
    while (loop) {
      System.out.println("Enter a name");
      Scanner in = new Scanner(System.in);
      String name = in.nextLine();
      member = application.findMemberByName(name);
      if (tryCastToCompetetiveMember(member) != null)
        loop = false;
      else
        System.out.println("The member you specified is not competing\nPlease Try again");
    }

    return member;
  }

  public void createResult(CompetitiveMember member) {
    Scanner in = new Scanner(System.in);
    System.out.println("Create a result for a member");
    printSingleLineShort();
    Discipline discipline = chooseDiscipline("Please select the Discipline in which the result was made.", false);

    if (discipline != null) {
      System.out.println("""
          Was the result made within a tournament?
          1) Yes
          2) No
          """);

    boolean isTournament = true;
    Integer input = null;
    input = validateInput(1,2, input);

    switch (input) {
      case 1 -> isTournament = true;
      case 2 -> isTournament = false;
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
    } else
      System.out.println("No discipline chosen!");
  }

  public void displayResultsByDiscipline(CompetitiveMember member) {
    Discipline discipline = chooseDiscipline("For which discipline do you wish to show results?", true);
    if (discipline == null)
      System.out.println("Show all results.");
    printResults(member, discipline);
  }

  public void printResults(CompetitiveMember member, Discipline discipline) {
    //Sort results from fastest to slowest time
    application.sortResults(member);
    printSingleLineLong();
    System.out.println("Name: " + member.getMasterData().getName());
    System.out.printf("%-20s%-20s%-20s%-20s%-20s\n\n"
        , "Time", "Discipline" ,"Date", "Tournament", "Ranking");

    for (int i = 0; i < member.getResults().size(); i++) {
      Result result = member.getResults().get(i);
      if (discipline == null || discipline == result.getDiscipline()) {
        if (result.getTournament() == null) {
          System.out.printf("%-20s%-20s%-20s\n"
              , result.getTime(), result.getDiscipline(), result.getDate());
        } else {
          System.out.printf("%-20s%-20s%-20s%-20s%-20s\n"
              , result.getTime(), result.getDiscipline(), result.getDate(), result.getTournament(), result.getRanking());
        }
      }
    }
    printSingleLineLong();
  }

  public void printNumberedResults(CompetitiveMember member) {
    //Sort results from fastest to slowest time
    application.sortResults(member);

    System.out.println("Name: " + member.getMasterData().getName());
    System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s\n\n"
        ,"", "Time", "Discipline" ,"Date", "Tournament", "Ranking");

    for (int i = 0; i < member.getResults().size(); i++) {
      Result result = member.getResults().get(i);
      if (result.getTournament() == null) {
        System.out.printf("%-5s%-20s%-20s%-20s\n"
            ,i + 1 + ")" ,result.getTime(), result.getDiscipline(), result.getDate());
      } else {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s\n"
            ,i + 1 + ")" ,result.getTime(), result.getDiscipline(), result.getDate(), result.getTournament(), result.getRanking());
      }
    }
    System.out.println();
    deleteResult(member);
  }

  public void deleteResult(CompetitiveMember member) {
    System.out.println("Enter the number of the result you want to delete");
    Scanner in = new Scanner(System.in);
    Integer input = null;
    input = validateInput(1,member.getResults().size(), input);
    member.getResults().remove(input - 1);
    System.out.println("You removed result number: " + input);
  }

  public void findTop5() {
    Discipline discipline = chooseDiscipline("Choose a discipline for which you want to show the top 5 swimmers", false);
    if (discipline != null) {
      TextTable ttSenior = application.creatTableModel(application.pickBestResults(discipline, true), new String[]{"Name      ", "Time (sec)"});
      TextTable ttJunior = application.creatTableModel(application.pickBestResults(discipline, false), new String[]{"Name      ", "Time (sec)"});
      displayTop5(discipline, ttSenior, ttJunior);
    } else
      System.out.println("No discipline chosen!");
  }

  private void displayTop5(Discipline discipline, TextTable ttSenior, TextTable ttJunior) {
    printDoubleLineTop5();
    System.out.println("Top 5 in the discipline - " + discipline);
    printDoubleLineSenior();
    // this adds the numbering on the left
    ttSenior.setAddRowNumbering(true);
    // sort by the second column
    ttSenior.printTable();
    printDoubleLineJunior();
    ttJunior.setAddRowNumbering(true);
    ttJunior.printTable();
    printDoubleLineTop5();

  }

  private void displayTeams() {
    printDoubleLine();
    printDoubleLineTeam();
    TextTable tt = application.creatTableModel(application.pickTeams(), new String[]{"Senior    ", "Junior    "});
    // this adds the numbering on the left
    //tt.setAddRowNumbering(true);
    // sort by the second column
    tt.printTable();
    printDoubleLine();

  }

  public Discipline chooseDiscipline(String menuHeader, boolean disciplineCanBeNull) {
    System.out.println(menuHeader);
    System.out.println("""
        1) Butterfly
        2) Crawl
        3) Rygcrawl
        4) Brystsvømning""");
    if (disciplineCanBeNull)
      System.out.println("5) Show all results");

    Scanner in = new Scanner(System.in);
    Integer input = null;
    Integer maxInput;
    if (disciplineCanBeNull)
      maxInput = 5;
    else
      maxInput = 4;
    input = validateInput(1,maxInput, input);

    return switch (input) {
      case 1 -> Discipline.BUTTERFLY;
      case 2 -> Discipline.CRAWL;
      case 3 -> Discipline.RYGCRAWL;
      case 4 -> Discipline.BRYSTSVØMNING;
      default -> null;
    };
  }

  public void editCompetitorDisciplines(Member member) {
    boolean loop = true;

    while (loop) {
      printDisciplines(member, true);

      Scanner in = new Scanner(System.in);
      Integer input = null;
      input = validateInput(0, 4, input);

      switch (input) {
        case 1 -> ((CompetitiveMember) member).toggleDiscipline(Discipline.BUTTERFLY);
        case 2 -> ((CompetitiveMember) member).toggleDiscipline(Discipline.CRAWL);
        case 3 -> ((CompetitiveMember) member).toggleDiscipline(Discipline.RYGCRAWL);
        case 4 -> ((CompetitiveMember) member).toggleDiscipline(Discipline.BRYSTSVØMNING);
        case 0 -> loop = false;
      }
    }
    printSingleLineShort();
  }

  public void printDisciplines(Member member, boolean canEdit) {
    boolean hasButterfly = ((CompetitiveMember) member).hasDiscipline(Discipline.BUTTERFLY);
    boolean hasCrawl = ((CompetitiveMember) member).hasDiscipline(Discipline.CRAWL);
    boolean hasRygcrawl = ((CompetitiveMember) member).hasDiscipline(Discipline.RYGCRAWL);
    boolean hasBrystsvømning = ((CompetitiveMember) member).hasDiscipline(Discipline.BRYSTSVØMNING);

    printSingleLineShort();
    if (canEdit) {
      System.out.print("1) Butterfly: ");
      printActiveOrInactive(hasButterfly);
      System.out.print("2) Crawl: ");
      printActiveOrInactive(hasCrawl);
      System.out.print("3) Rygcrawl: ");
      printActiveOrInactive(hasRygcrawl);
      System.out.print("4) Brystsvømning: ");
      printActiveOrInactive(hasBrystsvømning);
      System.out.println("0) Exit");
      System.out.println("Enter a number to toggle corresponding discipline between ACTIVE and INACTIVE");
    } else {
      System.out.print("Butterfly: ");
      printActiveOrInactive(hasButterfly);
      System.out.print("Crawl: ");
      printActiveOrInactive(hasCrawl);
      System.out.print("Rygcrawl: ");
      printActiveOrInactive(hasRygcrawl);
      System.out.print("Brystsvømning: ");
      printActiveOrInactive(hasBrystsvømning);
      printSingleLineShort();
    }
  }

  public void printActiveOrInactive(Boolean isActive) {
    if (isActive)
      System.out.println(TEXT_GREEN + "ACTIVE" + TEXT_RESET);
    else
      System.out.println(TEXT_RED + "INACTIVE" + TEXT_RESET);
  }

  //TODO
  public void financeCalculator() {
    int seniorkredit;

    Scanner sc = new Scanner(System.in);

    System.out.println("Hvor mange senior i klubben?");
    int x = sc.nextInt();
    seniorkredit = x*1200;
    System.out.println("Indkomst for senior " + seniorkredit + " DKK");



    int voksenkredit;
    System.out.println("Hvor mange voksne i klubben?");
    int y = sc.nextInt();
    voksenkredit = y*1600;
    System.out.println("Indkomst for voksne "+voksenkredit+" DKK");


    int juniorkredit;
    System.out.println("Hvor mange junior i klubben?");
    int h = sc.nextInt();
    juniorkredit = h*1000;
    System.out.println("Indkomst for Junior: "+juniorkredit+" DKK");


    int PassivMedlem;
    System.out.println("Hvor mange passive medlemmer i klubben?");
    int t = sc.nextInt();
    PassivMedlem = t*500;
    System.out.println("Indkomst for passiv: " + PassivMedlem + "DKK");




    int timmykredit = seniorkredit + voksenkredit + juniorkredit + PassivMedlem;
    System.out.println("Den totale kredit for klubben: " + timmykredit+ " DKK");
    System.out.println("Baseret på årligt kontigent");
  }

  public Integer validateInput(Integer min, Integer max, Integer input) {
    boolean loop = true;
    while (loop) {
      while (input == null || isIntegerWithinRange(min, max, input) == false) {
        Scanner in = new Scanner(System.in);
        input = application.tryParseInt(in.nextLine());
      }
      if (isIntegerWithinRange(min, max, input))
        loop = false;
      else
        loop = true;
    }
    return input;
  }

  public boolean isIntegerWithinRange(Integer min, Integer max, Integer input) {
    if (input < min || input > max) {
      System.out.printf("Only values %s-%s allowed\n"
          , min, max);
      return false;
    }
    return true;
  }

  public Member tryCastToCompetetiveMember(Member member) {
    try {
      member = (CompetitiveMember) member;
      return member;
    } catch (ClassCastException e) {
      return null;
    }
  }

  //Utility print methods
  private void printDoubleLine() {
    System.out.println("==================================================");
  }

  private void printDoubleLineTop5() {
    System.out.println("=======================TOP5=======================");
  }

  private void printDoubleLineTeam() {
    System.out.println("**********************TEAMS***********************");
  }

  private void printDoubleLineSenior() {
    System.out.println("**********************SENIOR**********************");
  }

  private void printDoubleLineJunior() {
    System.out.println("**********************JUNIOR**********************");
  }

  private void printSingleLineLong() {
    System.out.println("--------------------------------------------------");
  }

  private void printSingleLineShort() {
    System.out.println("----------------------------");
  }
}