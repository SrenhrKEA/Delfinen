package Delfinen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import Delfinen.Comparators.MemberAgeComparator;
import Delfinen.Comparators.MemberIdComparator;
import Delfinen.Comparators.MemberNameComparator;
import Delfinen.Enums.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Controller {
  private ArrayList<Member> members;
  private ArrayList<Staff> staff;
  private int toggleCounter = 0;

  public Controller() {
    members = new ArrayList<>();
  }

  public ArrayList<Member> getMembers() {
    return members;
  }

  public void setMembers(ArrayList<Member> members) {
    this.members = members;
  }

  public static void main(String[] args) {
    new Controller().runProgram();
  }

  private void runProgram() {
    members = deserializingJson(loadFromFile("MemberList.txt"));
    UserInterface ui = new UserInterface(this);
    ui.start();
  }

  public synchronized String createUID() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  public String serializingJson() {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    return gson.toJson(members);
  }

  public ArrayList<Member> deserializingJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, new TypeToken<ArrayList<Member>>() {
    }.getType());
  }

  public void saveToFile(String data, String filePathName) {
    try {
      File fileName = new File(filePathName);
      PrintStream out = new PrintStream(fileName);
      out.println(data);
      out.close();
    } catch (FileNotFoundException fnfe) {
      System.out.println("File not found!, try again");
    }
  }

  public String loadFromFile(String filePathName) {
    try {
      Path filePath = Path.of(filePathName);
      return Files.readString(filePath);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("File not found!, try again");
      return null;
    }
  }

  public Iterable<Member> getAllMembers() {
    return members;
  }

  public int getMemberCount() {
    return members.size();
  }

  public void sortBy(String sortBy, SortDirection sortDirection) {
    System.out.println("TODO: Sort the list of members by: " + sortBy);
    if (Objects.equals(sortBy, "name")) {
      if (sortDirection == SortDirection.ASC || (sortDirection == SortDirection.TOGGLE && toggleCounter % 2 != 0)) {
        members.sort(new MemberNameComparator());
      } else if (sortDirection == SortDirection.DESC || sortDirection == SortDirection.TOGGLE) {
        members.sort(Collections.reverseOrder(new MemberNameComparator()));
      }
      members.stream().map(Member::getName).forEach(System.out::print);
    }
    if (Objects.equals(sortBy, "age")) {
      if (sortDirection == SortDirection.ASC || (sortDirection == SortDirection.TOGGLE && toggleCounter % 2 != 0)) {
        members.sort(new MemberAgeComparator());
      } else if (sortDirection == SortDirection.DESC || sortDirection == SortDirection.TOGGLE) {
        members.sort(Collections.reverseOrder(new MemberAgeComparator()));
      }
      members.stream().map(Member::getAge).forEach(System.out::print);
    }
    if (Objects.equals(sortBy, "id")) {
      if (sortDirection == SortDirection.ASC || (sortDirection == SortDirection.TOGGLE && toggleCounter % 2 != 0)) {
        members.sort(new MemberIdComparator());
      } else if (sortDirection == SortDirection.DESC || sortDirection == SortDirection.TOGGLE) {
        members.sort(Collections.reverseOrder(new MemberIdComparator()));
      }
      members.stream().map(Member::getId).forEach(System.out::print);
    }
    if (sortDirection != SortDirection.ASC && toggleCounter == 0)
      toggleCounter++;
  }

  public void createNewExerciseMember(int age, String name, String address, String email, String telephone, String dateRegistration, String ID, Gender gender, MembershipType type, MembershipStatus status) {
    ExerciseMember member = new ExerciseMember(age, name, address, email, telephone, dateRegistration, ID, gender, type, status);
    members.add(member);
  }

  public void createNewCompetitiveMember(int age, String name, String address, String email, String telephone, String dateRegistration, String ID, Gender gender, MembershipType type, MembershipStatus status, ArrayList<Result> results, ArrayList<Discipline> disciplines) {
    CompetitiveMember member = new CompetitiveMember(age, name, address, email, telephone, dateRegistration, ID, gender, type, status, results, disciplines);
    members.add(member);
  }

  public boolean deleteMember(String name) {
    // find member with this name
    Member member = findMemberById(name);
    if (member == null) {
      return false;
    } else {
      members.remove(member);
      return true;
    }
  }

  private Member findMemberById(String id) {
    for (Member member : members) {
      if (member.getId().equalsIgnoreCase(id)) {
        return member;
      }
    }
    return null;
  }

  //Utilities
  public Integer tryParseInt(String text) {
    try {
      return Integer.parseInt(text);
    } catch (NumberFormatException nfe) {
      System.out.println("Input is not an integer!");
      return null;
    }
  }
}
