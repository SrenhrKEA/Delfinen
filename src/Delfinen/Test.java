package Delfinen;

public class Test {
  public static void main(String[] args) {
    new Test().runProgram();
  }

  private void runProgram() {
    Controller con = new Controller();
    System.out.println(con.createUID());
  }
}
