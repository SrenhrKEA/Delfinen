package Delfinen;

    import java.util.Scanner;

public class KASSERER {

  int juniorprices;
  int adultprices;
  int seniorprices;
  int competiveadult;
  int competivejunior;




  public int kasserer() {

    Scanner babe = new Scanner(System.in);
    System.out.println("Hvad er prisen for senior i klubben?");
    int vladdydady = babe.nextInt();
    System.out.println("Årlig pris for senior " + vladdydady + " DKK");


    System.out.println("Hvad er prisen for voksne i klubben?");
    int Adildo = babe.nextInt();
    System.out.println("Årlig pris for voksen " + Adildo + " DKK");


    System.out.println("Hvad er prisen for junior i klubben?");
    int MaoZedick = babe.nextInt();
    System.out.println("Årlig pris for junior " + MaoZedick + " DKK");


    System.out.println("Hvad er prisen for passive i klubben?");
    int tissetrold = babe.nextInt();
    System.out.println("Årlig pris for passiv " + tissetrold + "DKK");



    return kasserer();
  }



  public int totalkredit()
  {
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

    return totalkredit();


  }






  public static void main(String[] args) {
    Delfinen.KASSERER k = new Delfinen.KASSERER();

    k.kasserer();
  }


}

