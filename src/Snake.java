

public class Snake{

  private SnakePart head;
  private char direction;
  private long lastMovingTime;
  private int size;
  private boolean status;
  private int speed;

  public Snake(){

    head = null;
    this.direction = 'N';
    for(int i = 0, x = 13, y = 31; i < 3 ; i++, x++){
      addInitialPart(Config.NUCLEOTIDES[(int)(Math.random() * 4)], x, y);
    }
    speed = Config.SNAKESPEEDBEGINNER;
  }

  public void addInitialPart(char nucleotide, int coordX, int coordY){

    SnakePart newPart = new SnakePart(nucleotide, coordX, coordY);

    if(head == null){

      head = newPart;
    }
    else{

      SnakePart temp = head;
      while(temp.getNext() != null){
        temp = temp.getNext();
      }
      temp.setNext(newPart);
    }

    this.size += 1;
  }

  public void reverseSnake(){

    SnakePart prev = null;
    SnakePart current = head;
    SnakePart next = null;
    while(current != null){
      next = current.getNext();
      current.setNext(prev);
      prev = current;
      current = next;
    }
    head = prev;
  }



  public void addPart(char nucleotide, int coordX, int coordY){

    SnakePart newPart = new SnakePart(nucleotide, coordX, coordY);

    if(head == null){
      head = newPart;
    }
    else{

      newPart.setNext(head);
      head = newPart;
    }

    this.size += 1;
  }


  public int[] move(){

    int tempX = head.getCoordX();
    int tempY = head.getCoordY();
    boolean move = false;

    if(head.getCoordY() != head.getNext().getCoordY() || head.getCoordX() != head.getNext().getCoordX()) move = true;

    if(this.direction == 'N'){
      if(tempX - 1 < 0)
        head.setCoordX(Config.GAMEAREAHEIGHT - 1);
      else
        head.setCoordX(tempX - 1);

    }
    else if(this.direction == 'S'){
      if(tempX + 1 >= Config.GAMEAREAHEIGHT)
        head.setCoordX(0);
      else
        head.setCoordX(tempX + 1);
    }
    else if(this.direction  == 'W'){
      if(tempY - 1 < 0)
        head.setCoordY(Config.GAMEAREAWIDTH - 1);
      else
        head.setCoordY(tempY - 1);
    }
    else if(this.direction == 'E'){
      if(tempY + 1 >= Config.GAMEAREAWIDTH)
        head.setCoordY(0);
      else
        head.setCoordY(tempY + 1);
    }

    SnakePart temp = head.getNext();

    if(move){
      while(temp != null){

        int temp2X = temp.getCoordX();
        int temp2Y = temp.getCoordY();
        temp.setCoordX(tempX);
        temp.setCoordY(tempY);
        tempX = temp2X;
        tempY = temp2Y;
        temp = temp.getNext();
      }
    }

    return new int[]{tempX, tempY};
  }

  public String[] searchProteins(Proteins proteins, Player player){

    reverseSnake();
    String buffer = "";
    SnakePart temp = head;
    int score = 0;
    String[] pointedProteins = new String[100];
    int counter = 0;

    for(int i = 0 ; i < this.size - 2; i++){

      if(!temp.isPointed()){
        SnakePart temp2 = temp;
        for(int j = 0 ; j < 3 ; j++){
          buffer += temp2.getNucleotide();
          temp2.setPointed(true);
          temp2 = temp2.getNext();
        }
        int point = proteins.searchCodon(buffer);
        if(point != -1){
          score += point;
          pointedProteins[counter++] = buffer + " - " + point;
        }
        else if(point == -1){
          temp2 = temp;
          temp2.setPointed(false);
          temp2.getNext().setPointed(false);
          temp2.getNext().getNext().setPointed(false);
        }
        buffer = "";
      }
      temp = temp.getNext();
    }

    score += (size - 3) * 5;
    player.setScore(score);

    temp = head;
    while(temp != null){
      temp.setPointed(false);
      temp = temp.getNext();
    }

    reverseSnake();
    return pointedProteins;
  }

  public SnakePart getHead(){
    return this.head;
  }

  public char getDirection(){
    return this.direction;
  }

  public void setDirection(char direction){
    this.direction = direction;
  }

  public long getLastMovingTime(){
    return this.lastMovingTime;
  }

  public void setLastMovingTime(long lastMovingTime){
    this.lastMovingTime = lastMovingTime;
  }

  public boolean getStatus(){
    return this.status;
  }

  public void setStatus(boolean status){
    this.status = status;
  }

  public void setSpeed(int speed){
    this.speed = speed;
  }

  public int getSpeed(){
    return this.speed;
  }
}
