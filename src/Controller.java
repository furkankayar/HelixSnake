
import java.util.Scanner;

public class Controller{

  private Player player;
  private UserInterface ui;
  private GameArea gameArea;
  private Snake snake;
  private ScoreTable scoreTable;
  private SoundFX.GameMusic gameMusic;
  private Proteins proteins;
  private int level;
  private boolean isGameEnd;
  private int turn;

  public Controller(){

    scoreTable = new ScoreTable();
    snake = new Snake();
    gameArea = new GameArea();
    ui = new UserInterface(this.snake, this.gameArea);
    proteins = new Proteins();
    gameMusic = new SoundFX.GameMusic();
    player = new Player();
    level = 1;

    gameMusic.start();
    startGame();
  }

  public void restartGame(){
    scoreTable = new ScoreTable();
    snake = new Snake();
    snake.setStatus(true);
    proteins = new Proteins();
    gameMusic = new SoundFX.GameMusic();
    gameMusic.start();
    ui.setActiveScreen("Game");
    if(!ui.getDesignStatus()){
      gameArea = new GameArea();

    }
    player = new Player();
    level = 1;
    isGameEnd = false;
    ui.setGameArea(gameArea);
    ui.setSnake(snake);
  }

  public void startGame(){


    String lastActiveScreen = "Login";
    while(true){

      if(ui.getActiveScreen().equals("Login")){
        ui.clear();
        ui.printLoginScreen();
        lastActiveScreen = "Login";
      }
      else if(ui.getActiveScreen().equals("Game") && !lastActiveScreen.equals("Game")){
        new SoundFX.ConfirmEffect().start();
        ui.clear();
        lastActiveScreen = "Game";
        snake.setStatus(true);
        runGame();
      }
      else if(ui.getActiveScreen().equals("Highscores") && !lastActiveScreen.equals("Highscores")){
        ui.clear();
        ui.printScoreTable(scoreTable);
        lastActiveScreen = "Highscores";
      }
      else if(ui.getActiveScreen().equals("Credits")){

        ui.printCredits(gameArea, snake);
        lastActiveScreen = "Credits";
      }
      else if(ui.getActiveScreen().equals("GameEnd") && !lastActiveScreen.equals("GameEnd")){

        ui.printGameEnd(player, scoreTable);
        lastActiveScreen = "GameEnd";
        turn++;
      }
      else if(ui.getActiveScreen().equals("MapDesign") && !lastActiveScreen.equals("MapDesign")){
        ui.clear();
        ui.printMapDesign();
        lastActiveScreen = "MapDesign";
      }
      else if(ui.getActiveScreen().equals("LevelSelect") && !lastActiveScreen.equals("LevelSelect")){
        ui.clear();
        ui.printLevelSelect();
        lastActiveScreen = "LevelSelect";
      }

      try{Thread.sleep(50);}catch(Exception e){}

    }
  }

  public void runGame(){


    if(turn > 0)
      restartGame();
    ui.printStatics();
    snake.setSpeed(ui.getSelectedSnakeSpeed());
    ui.drawGameArea(gameArea.getGround(), snake);
    gameArea.placeSnake(snake);
    snake.searchProteins(proteins, player);
    ui.drawGameArea(gameArea.getGround(), snake);
    int time = 0;
    String[] pointedProteins = snake.searchProteins(proteins, player);
    ui.printProteins(pointedProteins);
    long lastTimeUpdate = System.currentTimeMillis();

    long lastLevelUpTime = System.currentTimeMillis();
    while(!isGameEnd){

      while(snake.getStatus()){
        if(System.currentTimeMillis() - lastLevelUpTime >= Config.LEVELINCREASINGDELAY){
          level++;
          gameArea.putRandomWalls();
          ui.drawGameArea(gameArea.getGround(), snake);
          lastLevelUpTime = System.currentTimeMillis();
        }


        if(System.currentTimeMillis() >= snake.getLastMovingTime() + snake.getSpeed()){
          int[] lastCoordinates = snake.move();
          collisionDetection(lastCoordinates);
          gameArea.clearPoint(lastCoordinates);
          ui.printLevelTime(level, time, player.getScore() );
          gameArea.placeSnake(snake);
          snake.setLastMovingTime(System.currentTimeMillis());

        }

        if(System.currentTimeMillis() - lastTimeUpdate >= 1000){
          time++;
          lastTimeUpdate = System.currentTimeMillis();
        }

        if(!isGameEnd){
          ui.drawGameArea(gameArea.getGround(), snake);
        }
      }
      if(!isGameEnd)
        ui.printPauseMenu();

      try{Thread.sleep(1);}catch(Exception e){}
    }
  }

  public void collisionDetection(int[] lastCoordinates){

    int x = snake.getHead().getCoordX();
    int y = snake.getHead().getCoordY();
    char point = gameArea.getPosition(x,y);
    if(point == Config.WALL){
      gameMusic.run = false;
      new SoundFX.GameOverEffect().start();
      ui.deathAnimation(gameArea, snake);
      isGameEnd = true;
    }

    SnakePart temp = snake.getHead().getNext();
    while(temp != null){

      if(x == temp.getCoordX() && y == temp.getCoordY()){
        isGameEnd = true;
        gameMusic.run = false;
        new SoundFX.GameOverEffect().start();
        ui.deathAnimation(gameArea, snake);
      }
      temp = temp.getNext();
    }

    for(int i = 0 ; i < Config.NUCLEOTIDES.length && !isGameEnd ; i++){
      if(Config.NUCLEOTIDES[i] == point && (x != lastCoordinates[0] || y != lastCoordinates[1])){
        snake.addPart(point, x, y);
        new SoundFX.CrunchEffect().start();
        String[] pointedProteins = snake.searchProteins(proteins, player);
        ui.printProteins(pointedProteins);
        gameArea.putRandomFood();
      }
    }
  }



  public static void main(String[] args){

    new Controller();
  }
}
