package Delfinen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Controller {
  private final ArrayList<Member> memberList = new ArrayList<>();

  public ArrayList<Member> getMemberList() {
    return memberList;
  }

  public static void main(String[] args) {
    new Controller().runProgram();
  }

  private void runProgram() {
    //load member list from file upon startup
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
