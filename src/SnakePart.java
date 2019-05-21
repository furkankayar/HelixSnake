

public class SnakePart{


  private SnakePart next;
  private char nucleotide;
  private int coordX;
  private int coordY;
  private boolean isPointed;

  public SnakePart(char nucleotide){

    this.nucleotide  = nucleotide;
    this.isPointed = false;
  }

  public SnakePart(char nucleotide, int coordX, int coordY){

    this.nucleotide = nucleotide;
    this.coordX = coordX;
    this.coordY = coordY;
  }

  public SnakePart getNext(){
    return this.next;
  }

  public char getNucleotide(){
    return this.nucleotide;
  }

  public int getCoordX(){
    return this.coordX;
  }

  public int getCoordY(){
    return this.coordY;
  }

  public void setNext(SnakePart next){
    this.next = next;
  }

  public void setNucleotide(char nucleotide){
    this.nucleotide = nucleotide;
  }

  public void setCoordX(int coordX){
    this.coordX = coordX;
  }

  public void setCoordY(int coordY){
    this.coordY = coordY;
  }

  public boolean isPointed(){
    return this.isPointed;
  }

  public void setPointed(boolean isPointed){
    this.isPointed = isPointed;
  }
}
