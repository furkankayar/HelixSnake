

public class Player{

  private String name;
  private int score;


  public Player(String name, int score){
    this.name = name;
    this.score = score;
  }

  public Player(){

  }

  public String getName(){
    return this.name;
  }

  public void setName(String name){
    this.name = name;
  }

  public void increaseScore(int amount){
    this.score += amount;
  }

  public int getScore(){
    return this.score;
  }

  public void setScore(int score){
    this.score = score;
  }



  @Override
  public String toString(){

    return this.name + ";" + this.score + "\n";
  }

}
