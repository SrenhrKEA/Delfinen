package Delfinen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Test {
  private ArrayList<Member> memberList = new ArrayList<>();

  public ArrayList<Member> getMemberList() {
    return memberList;
  }

  public static void main(String[] args) {
    new Test().runTest();
  }

  private void runTest() {
    Member person1 = new Member(31,"Søren", LocalDateTime.now().toString(),true,true,false,true,"john",new ArrayList<>(),new ArrayList<>());
    person1.getDisciplines().add(Discipline.BRYSTSVØMNING);
    person1.getDisciplines().add(Discipline.CRAWL);
    Member person2 = new Member(37,"Martin", LocalDateTime.now().toString(),true,true,false,true,"john",new ArrayList<>(),new ArrayList<>());
    memberList.add(person1);
    memberList.add(person2);
    saveToFile(serializingJson());
    memberList.clear();
    memberList = deserializingJson(loadFromFile());
    System.out.println("//TEST1");
    System.out.println(memberList);

    System.out.println("//TEST2");
    System.out.println(person1.getDisciplines());
    System.out.println(person1.getResults());
  }

  private String serializingJson() {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    return gson.toJson(memberList);
  }

  private ArrayList<Member> deserializingJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, new TypeToken<ArrayList<Member>>(){}.getType());
  }

  private void saveToFile( String data) {
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
    }
     catch (IOException e) {
      e.printStackTrace();
       System.out.println("File not found!, try again");
       return null;
    }
  }

}
