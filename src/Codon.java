

public class Codon{

  private String code;
  private int point;
  private Codon next;


  public Codon(String code, int point){
    this.code = code;
    this.point = point;
  }


  public Codon getNext(){
    return this.next;
  }

  public void setNext(Codon next){
    this.next = next;
  }

  public int getPoint(){
    return this.point;
  }

  public String getCode(){
    return this.code;
  }
}
