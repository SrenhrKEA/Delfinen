package Delfinen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Controller {
  private ArrayList<Member> members;
  private ArrayList<Team> teams;

  public Controller (){
    members = new ArrayList<>();
    teams = new ArrayList<>();
  }


  public ArrayList<Member> getMembers() {
    return members;
  }

  public static void main(String[] args) {
    new Controller().runProgram();
  }

  private void runProgram() {
    members = deserializingJson(loadFromFile());
    UserInterface ui = new UserInterface(this);
    ui.start();
    saveToFile(serializingJson());
  }

  private String serializingJson() {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    return gson.toJson(members);
  }

  private ArrayList<Member> deserializingJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, new TypeToken<ArrayList<Member>>() {
    }.getType());
  }

  private void saveToFile(String data) {
    try {
      File fileName = new File("newfile.txt");
      PrintStream out = new PrintStream(fileName);
      out.println(data);
      out.close();
    } catch (FileNotFoundException fnfe) {
      System.out.println("File not found!, try again");
    }
  }

  private String loadFromFile() {
    try {
      Path filePath = Path.of("newfile.txt");
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
    // TODO: Implement sorting!
    System.out.println("TODO: Sort the list of members by: " + sortBy);
    Collections.sort(members,new MemberSortingName());
    members.stream().map(Member::getName).forEach(System.out::print);
  }

  public void createNewMember (int age, String name, String dateRegistration, boolean genderMale, boolean membershipActive, boolean membershipJunior, boolean membershipCompetitive) {
    Member member = new Member(age, name, dateRegistration, genderMale, membershipActive, membershipJunior, membershipCompetitive);
    members.add(member);
  }

  public boolean deleteMember(String name) {
    // find member with this name
    Member member = findMemberByName(name);
    if (member == null) {
      return false;
    } else {
      members.remove(member);
      return true;
    }
  }

  private Member findMemberByName(String name) {
    for (Member member : members) {
      if (member.getName().equalsIgnoreCase(name)) {
        return member;
      }
    }
    return null;
  }
}
