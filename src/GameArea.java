

public class GameArea{

  private char[][] ground;

  public GameArea(){

    ground = new char[Config.GAMEAREAHEIGHT][Config.GAMEAREAWIDTH];
    intializeWalls();
    initializeFoods();
  }

  public void initializeFoods(){

    for(int i = 0 ; i < Config.INITIALFOODNUMBER ; i++) putRandomFood();
  }

  public void intializeWalls(){

    for(int i = 0 ; i < Config.GAMEAREAHEIGHT ; i++){
      for(int j = 0 ; j < Config.GAMEAREAWIDTH ; j++){
        ground[i][j] = ' ';
      }
    }

    for(int i = 0 ; i < Config.GAMEAREAWIDTH ; i++){
      ground[0][i] = Config.WALL;
      ground[Config.GAMEAREAHEIGHT - 1][i] = Config.WALL;
      if(i < Config.GAMEAREAHEIGHT){
        ground[i][0] = Config.WALL;
        ground[i][Config.GAMEAREAWIDTH - 1] = Config.WALL;
      }
    }

    ground[0][30] = ' ';
    ground[0][31] = ' ';
    ground[0][32] = ' ';
    ground[26][30] = ' ';
    ground[26][31] = ' ';
    ground[26][32] = ' ';
  }

  public void putRandomWalls(){

    for(int i = 0 ; i < Config.RANDOMWALLNUMBER ; i++){

      int y = (int)(Math.random() * Config.GAMEAREAWIDTH - 2) + 1;
      int x = (int)(Math.random() * Config.GAMEAREAHEIGHT - 2) + 1;
      while(ground[x][y] != ' '){
        y = (int)(Math.random() * Config.GAMEAREAWIDTH - 2) + 1;
        x = (int)(Math.random() * Config.GAMEAREAHEIGHT - 2) + 1;
      }
      ground[x][y] = Config.WALL;
    }
  }

  public void putWall(int x, int y){

    ground[x][y] = Config.WALL;
  }

  public void putChar(int x, int y, char c){

    ground[x][y] = c;
  }

  public void putRandomFood(){

    int y = (int)(Math.random() * Config.GAMEAREAWIDTH);
    int x = (int)(Math.random() * Config.GAMEAREAHEIGHT);
    while(ground[x][y] != ' '){
      y = (int)(Math.random() * Config.GAMEAREAWIDTH);
      x = (int)(Math.random() * Config.GAMEAREAHEIGHT);
    }
    ground[x][y] = Config.NUCLEOTIDES[(int)(Math.random() * 4)];

  }

  public char[][] getGround(){

    return this.ground;
  }

  public void placeSnake(Snake snake){

    SnakePart head = snake.getHead();


    while(head != null){
      ground[head.getCoordX()][head.getCoordY()] = head.getNucleotide();
      head = head.getNext();
    }
  }

  public char getPosition(int x, int y){

    return ground[x][y];
  }

  public void clearPoint(int[] coordinates){

    ground[coordinates[0]][coordinates[1]] = ' ';
  }

  public void setGround(char[][] ground){
    this.ground = ground;
  }
}
