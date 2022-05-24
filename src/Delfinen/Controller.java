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
import Delfinen.Comparators.ResultTimeComparator;
import Delfinen.Enums.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dnl.utils.text.table.TextTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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


  public void TotalIncome()
  {




double MaxIncome = 0;


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
    ArrayList<DTO> temp = new ArrayList<>();
    for (Member member : members) {
      temp.add(member.convertToDTO());
    }
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    return gson.toJson(temp);
  }

  public ArrayList<Member> deserializingJson(String json) {
    Gson gson = new Gson();
    return convertFromDTO(gson.fromJson(json, new TypeToken<ArrayList<DTO>>() {
    }.getType()));
  }

  public ArrayList<Member> convertFromDTO(ArrayList<DTO> dtos) {
    ArrayList<Member> temp = new ArrayList<>();
    for (DTO dto : dtos) {
      MembershipType className = dto.getType();
      if (className == null) {
        continue;
      }
      Member member;
      if (className == (MembershipType.COMPETITIVE)) {
        member = createCompetitiveMember(dto.getMasterData(), dto.getType(),
            dto.getStatus(), dto.getResults(), dto.getDisciplines());
      } else {
        member = createExerciseMember(dto.getMasterData(), dto.getType(),
            dto.getStatus());
      }
      temp.add(member);
    }
    return temp;
  }

  public void saveToFile(String data, String filePathName) {
    try {
      File fileName = new File(filePathName);
      PrintStream out = new PrintStream(fileName);
      out.println(data);
      out.close();
    } catch (FileNotFoundException fnfe) {
      System.out.println("File not found!");
    }
  }

  public String loadFromFile(String filePathName) {
    try {
      Path filePath = Path.of(filePathName);
      return Files.readString(filePath);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("File not found!");
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
    if (Objects.equals(sortBy, "name")) {
      if (sortDirection == SortDirection.ASC || (sortDirection == SortDirection.TOGGLE && toggleCounter % 2 != 0)) {
        members.sort(new MemberNameComparator());
      } else if (sortDirection == SortDirection.DESC || sortDirection == SortDirection.TOGGLE) {
        members.sort(Collections.reverseOrder(new MemberNameComparator()));
      }
    }
    if (Objects.equals(sortBy, "age")) {
      if (sortDirection == SortDirection.ASC || (sortDirection == SortDirection.TOGGLE && toggleCounter % 2 != 0)) {
        members.sort(new MemberAgeComparator());
      } else if (sortDirection == SortDirection.DESC || sortDirection == SortDirection.TOGGLE) {
        members.sort(Collections.reverseOrder(new MemberAgeComparator()));
      }
    }
    if (Objects.equals(sortBy, "id")) {
      if (sortDirection == SortDirection.ASC || (sortDirection == SortDirection.TOGGLE && toggleCounter % 2 != 0)) {
        members.sort(new MemberIdComparator());
      } else if (sortDirection == SortDirection.DESC || sortDirection == SortDirection.TOGGLE) {
        members.sort(Collections.reverseOrder(new MemberIdComparator()));
      }
    }
    if (sortDirection != SortDirection.ASC && toggleCounter == 0)
      toggleCounter++;
  }

  public Object[][] pickBestResults(Discipline discipline, boolean senior) {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Double> times = new ArrayList<>();

    for (Member member : members) {
      if (member instanceof CompetitiveMember) {
        ArrayList<Result> Results = ((CompetitiveMember) member).getResults();
        for (Result result : Results) {
          if (result.getDiscipline() == discipline && senior && member.getMasterData().getAge() >= 18) {
            names.add(member.getMasterData().getName());
            times.add(result.getTimeInSeconds());
          } else if (result.getDiscipline() == discipline && !senior && member.getMasterData().getAge() < 18) {
            names.add(member.getMasterData().getName());
            times.add(result.getTimeInSeconds());
          }
        }
      }
    }
    Object[][] data = new Object[names.size()][2];
    for (int j = 0; j < names.size(); j++) {
      data[j][0] = names.get(j);
      data[j][1] = times.get(j);
    }

    Arrays.sort(data, Comparator.comparingDouble(o -> (Double) o[1]));
    Object[][] copy = new Object[5][2];
    System.arraycopy(data, 0, copy, 0, Math.min(data.length, 5));

    return copy;
  }

  public Object[][] pickTeams() {
    ArrayList<String> senior = new ArrayList<>();
    ArrayList<String> junior = new ArrayList<>();

    for (Member member : members) {
      if (member instanceof CompetitiveMember) {
        if (member.getMasterData().getAge() >= 18)
          senior.add(member.getMasterData().getName());
        else
          junior.add(member.getMasterData().getName());
      }
    }

    int biggest = Math.max(senior.size(), junior.size());

    Object[][] data = new Object[biggest][2];
    for (int j = 0; j < biggest; j++) {
      try {
        data[j][0] = senior.get(j);
      } catch (Exception e) {
        data[j][0] = "";
      }
      try {
        data[j][1] = junior.get(j);
      } catch (Exception e) {
        data[j][1] = "";
      }
    }

    return data;
  }

  public TextTable creatTableModel(Object[][] data, String[] columnNames) {

    TableModel model = new DefaultTableModel(data, columnNames) {
      @Override
      public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex != 0) return Integer.class;
        return super.getColumnClass(columnIndex);
      }
    };

    return new TextTable(model);
  }

  public void sortResults(CompetitiveMember member) {
    member.getResults().sort(new ResultTimeComparator());
  }

  public ExerciseMember createExerciseMember(MasterData masterData, MembershipType type, MembershipStatus status) {
    return new ExerciseMember(masterData, type, status);
  }

  public CompetitiveMember createCompetitiveMember(MasterData masterData, MembershipType type, MembershipStatus status, ArrayList<Result> results, ArrayList<Discipline> disciplines) {
    return new CompetitiveMember(masterData, type, status, results, disciplines);
  }

  public boolean deleteMember(String id) {
    // find member with this id
    Member member = findMemberById(id);
    if (member == null) {
      return false;
    } else {
      members.remove(member);
      return true;
    }
  }

  private Member findMemberById(String id) {
    for (Member member : members) {
      if (member.getMasterData().getId().equalsIgnoreCase(id)) {
        return member;
      }
    }
    return null;
  }

  public Member findMemberByName(String name) {
    for (Member member : members) {
      if (member.getMasterData().getName().equalsIgnoreCase(name)) {
        return member;
      }
    }
    return null;
  }

  //Utilities
  public Integer tryParseInt(String text) {
    try {
      return Integer.parseInt(text);
    } catch (Exception e) {
      System.out.println("Input is not an integer!");
      return null;
    }
  }

}
