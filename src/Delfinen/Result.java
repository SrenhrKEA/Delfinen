package Delfinen;

public class Result {
  private Discipline discipline;
  private String result;
  private String date;
  private String time;

  private String convention;
  private String tournament;

  private String placement;
  private String ranking;

  public Discipline getDiscipline() {
    return discipline;
  }

  public void setDiscipline(Discipline discipline) {
    this.discipline = discipline;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTournament() {
    return tournament;
  }

  public void setTournament(String tournament) {
    this.tournament = tournament;
  }

  public String getRanking() {
    return ranking;
  }

  public void setRanking(String ranking) {
    this.ranking = ranking;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
  /*
  public Result(Discipline discipline, String result, String date) {
    this.discipline = discipline;
    this.result = result;
    this.date = date;
  }
   */

  public Result(Discipline discipline, String tournament, String ranking, String time) {
    this.discipline = discipline;
    this.tournament = tournament;
    this.ranking = ranking;
    this.time = time;
  }

  //new constructors
  //constructor for conventions
  public Result(Discipline discipline, String convention, String placement, String time, String date) {
    this.discipline = discipline;
    this.convention = convention;
    this.placement = placement;
    this.time = time;
    this.date = date;
  }
  //constructor for training
  public Result(Discipline discipline, String time, String date) {
    this.discipline = discipline;
    this.time = time;
    this.date = date;
  }

}
