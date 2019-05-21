
public class Aminoacid{

  private String name;
  private String abbreviation;
  private Codon headCodon;
  private Aminoacid next;

  public Aminoacid(String name, String abbreviation){

    this.name = name;
    this.abbreviation = abbreviation;
  }


  public void setNext(Aminoacid next){
    this.next = next;
  }

  public void setHeadCodon(Codon headCodon){
    this.headCodon = headCodon;
  }

  public String getName(){
    return this.name;
  }

  public String getAbbreviation(){
    return this.abbreviation;
  }

  public Codon getHeadCodon(){
    return this.headCodon;
  }

  public Aminoacid getNext(){
    return this.next;
  }
}
