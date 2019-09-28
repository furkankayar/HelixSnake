import enigma.core.Enigma;
import enigma.console.Console;
import enigma.console.TextAttributes;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.Color;
import java.util.Arrays;






public class UserInterface implements KeyListener, TextMouseListener{

  private Console console;
  private long directionChangingTime;
  private Snake snake;
  private GameArea ga;

  private String activeScreen;
  private int cursorPosition;
  private int cursorPosition2;
  private int cursorPosition3;
  private char[][] designArea;
  private boolean designStatus;
  private int selectedSnakeSpeed;


  private String[] header = {"██╗  ██╗███████╗██╗     ██╗██╗  ██╗    ███████╗███╗   ██╗ █████╗ ██╗  ██╗███████╗\n",
                             "██║  ██║██╔════╝██║     ██║╚██╗██╔╝    ██╔════╝████╗  ██║██╔══██╗██║ ██╔╝██╔════╝\n",
                             "███████║█████╗  ██║     ██║ ╚███╔╝     ███████╗██╔██╗ ██║███████║█████╔╝ █████╗  \n",
                             "██╔══██║██╔══╝  ██║     ██║ ██╔██╗     ╚════██║██║╚██╗██║██╔══██║██╔═██╗ ██╔══╝  \n",
                             "██║  ██║███████╗███████╗██║██╔╝ ██╗    ███████║██║ ╚████║██║  ██║██║  ██╗███████╗\n",
                             "╚═╝  ╚═╝╚══════╝╚══════╝╚═╝╚═╝  ╚═╝    ╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝\n"
                           };

  private char[][] gameOver = {" ██████╗  █████╗ ███╗   ███╗███████╗".toCharArray(),
                               "██╔════╝ ██╔══██╗████╗ ████║██╔════╝".toCharArray(),
                               "██║  ███╗███████║██╔████╔██║█████╗  ".toCharArray(),
                               "██║   ██║██╔══██║██║╚██╔╝██║██╔══╝  ".toCharArray(),
                               "╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗".toCharArray(),
                               " ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝".toCharArray(),
                               "  ██████╗ ██╗   ██╗███████╗██████╗  ".toCharArray(),
                               " ██╔═══██╗██║   ██║██╔════╝██╔══██╗ ".toCharArray(),
                               " ██║   ██║██║   ██║█████╗  ██████╔╝ ".toCharArray(),
                               " ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗ ".toCharArray(),
                               " ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║ ".toCharArray(),
                               "  ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝ ".toCharArray()
                             };

  private char[][] leaderBoard = {"██╗     ███████╗ █████╗ ██████╗ ███████╗██████╗ ██████╗  ██████╗  █████╗ ██████╗ ██████╗ ".toCharArray(),
                                  "██║     ██╔════╝██╔══██╗██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██╔══██╗".toCharArray(),
                                  "██║     █████╗  ███████║██║  ██║█████╗  ██████╔╝██████╔╝██║   ██║███████║██████╔╝██║  ██║".toCharArray(),
                                  "██║     ██╔══╝  ██╔══██║██║  ██║██╔══╝  ██╔══██╗██╔══██╗██║   ██║██╔══██║██╔══██╗██║  ██║".toCharArray(),
                                  "███████╗███████╗██║  ██║██████╔╝███████╗██║  ██║██████╔╝╚██████╔╝██║  ██║██║  ██║██████╔╝".toCharArray(),
                                  "╚══════╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ".toCharArray()
                                 };

  private boolean[][] gameOverCheck;


  public UserInterface(Snake snake, GameArea ga){
    this.snake = snake;
    this.ga = ga;
    console = Enigma.getConsole("Helix Snake", 110,30, 25, 0);
    activeScreen = "Login";
    cursorPosition = 0;
    designArea = new char[Config.GAMEAREAHEIGHT][Config.GAMEAREAWIDTH];
    for(int i = 0 ; i < designArea.length ; i++){
      Arrays.fill(designArea[i], ' ');
    }

    console.getTextWindow().addKeyListener(this);
    console.getTextWindow().addTextMouseListener(this);

  }

  public void uiReset(){

    activeScreen = "Login";
    cursorPosition = 0;
    designArea = new char[Config.GAMEAREAHEIGHT][Config.GAMEAREAWIDTH];
    for(int i = 0 ; i < designArea.length ; i++){
      Arrays.fill(designArea[i], ' ');
    }
    console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
    designStatus = false;
  }

  public void printLoginScreen(){

    long lastChangeTime = System.currentTimeMillis() - 1000;

    while("Login".equals(activeScreen)){

      if(System.currentTimeMillis() - lastChangeTime >= 1000){

        for(int i = 0 ; i < header.length ; i++){
          console.setTextAttributes(new TextAttributes(new Color((int)(Math.random() * 0xFF),(int)(Math.random() * 0xFF),(int)(Math.random() * 0xFF))));
          console.getTextWindow().setCursorPosition(15, 5+i);
          console.getWriter().print(header[i]);
        }
        lastChangeTime = System.currentTimeMillis();
      }
      console.getTextWindow().setCursorPosition(53, 17);
      if(cursorPosition == 0){
        console.setTextAttributes(new TextAttributes(new Color(0x7C,0x10,0xE9), new Color(0xBF,0xFF,0xF3)));
      }
      console.getWriter().print("• Play •");
      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
      console.getTextWindow().setCursorPosition(50, 19);
      if(cursorPosition == 1){
        console.setTextAttributes(new TextAttributes(new Color(0xBF,0xFF,0xF3), new Color(0x7C,0x10,0xE9)));
      }
      console.getWriter().print("• Highscores •");
      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
      console.getTextWindow().setCursorPosition(49, 21);
      if(cursorPosition == 2){
        console.setTextAttributes(new TextAttributes(new Color(0x00,0x00,0x00),new Color(0xCC,0xA1,0x16)));
      }
      console.getWriter().print("• Map Designer •");

      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));

    }
  }

  public String getInput(){

    return console.readLine();
  }

  public void drawGameArea(char[][] area, Snake snake){

    for(int j = 0 ; j < Config.GAMEAREAHEIGHT ; j++){
      for(int i = 0 ; i < Config.GAMEAREAWIDTH ; i++){
        console.getTextWindow().setCursorPosition(i+1, j+1);
        console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF), new Color(0x00,0x00,0x10)));
        if(area[j][i] == 'A' || area[j][i] == 'T' || area[j][i] == 'G' || area[j][i] == 'C'){
          console.setTextAttributes(new TextAttributes(new Color(0x0,0x0,0x0), new Color(0xFF,0xA5,0x1E)));
          SnakePart temp = snake.getHead().getNext();
          while(temp != null){
            if(i == temp.getCoordY() && j == temp.getCoordX()){
              console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF), new Color(0xDC,0x04,0xE0)));
              break;
            }
            temp = temp.getNext();
          }
          if(i == snake.getHead().getCoordY() && j == snake.getHead().getCoordX()){
            console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF), new Color(0,0,0xFF)));
          }
        }
        else if("GameEnd".equals(activeScreen) && gameOverCheck[j][i]){
          console.setTextAttributes(new TextAttributes(new Color(0x07,0x28,0xBA), new Color(0x42,0xBC,0xF4)));
        }
        else if(area[j][i] == Config.WALL){
          console.setTextAttributes(new TextAttributes(new Color(0x42,0xBC,0xF4)));
        }


        console.getWriter().print(area[j][i]);
        console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
      }
    }
  }

  public void printLevelTime(int level, int time, int score){

    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 33, Config.GAMEAREAHEIGHT - 3);
    console.getWriter().print(level);
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 21, Config.GAMEAREAHEIGHT - 3);
    console.getWriter().print(time);
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 25, 4);
    console.getWriter().print(score);

  }

  public void printStatics(){
    console.setTextAttributes(new TextAttributes(new Color(0xDC,0x04,0xE0)));
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 3, 1);
    console.getWriter().print(" __| |______________________________| |__ \n");
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 3, 2);
    console.getWriter().print("(__   ______________________________   __)\n");
    for(int i = 0 ; i < 22 ; i++){
      console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 3, 3+i);
      console.getWriter().print("   | |                              | |   \n");
    }
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 3, 25);
    console.getWriter().print(" __| |______________________________| |__ \n");
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 3, 26);
    console.getWriter().print("(__   ______________________________   __)\n");
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 3, 27);
    console.getWriter().print("   | |                              | |   \n");

    console.setTextAttributes(new TextAttributes(new Color(0x42,0xBC,0xF4)));
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 19, 4);
    console.getWriter().print("Score ");
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 15, Config.GAMEAREAHEIGHT - 3);
    console.getWriter().print("Time: ");
    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 26, Config.GAMEAREAHEIGHT - 3);
    console.getWriter().print("Level: ");
    console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
  }

  public void printProteins(String[] proteins){

    for(int i = 0 ; i < proteins.length && proteins[i] != null; i++){

      if(i >= 15 && i < 30)
        console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 20, Config.GAMEAREAHEIGHT - 20 + i % 15);
      else if(i >= 30 && i < 45)
        console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 30, Config.GAMEAREAHEIGHT - 20 + i % 15);
      else
        console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH + 10, Config.GAMEAREAHEIGHT - 20 + i);

      console.getWriter().print(proteins[i]);
    }
  }

  public void printPauseMenu(){

    console.getTextWindow().setCursorPosition(Config.GAMEAREAWIDTH/2 , Config.GAMEAREAHEIGHT/2);
    System.out.println("PAUSE");
  }


  public void clear(){

    for(int i = 0; i < 45; i++)
      System.out.print("\n");
    console.getTextWindow().setCursorPosition(0, 0);

  }

  public void printLeaderboardText(){

    for(int i = 0 ; i < leaderBoard.length ; i++){
      for(int j = 0 ; j < leaderBoard[0].length; j++){
        console.setTextAttributes(new TextAttributes(new Color((int)(Math.random() * 0xFF),(int)(Math.random() * 0xFF),(int)(Math.random() * 0xFF))));
        console.getTextWindow().setCursorPosition(j + 11, i + 2);
        console.getWriter().print(leaderBoard[i][j]);
      }
      console.getWriter().println();
    }

  }

  public void printScoreTable(ScoreTable st){

    Record temp = st.getHead();
    char[][] records = new char[10][];

    long leaderBoardLastChange = System.currentTimeMillis();
    printLeaderboardText();

    console.setTextAttributes(new TextAttributes(new Color(0x61,0x25,0xED)));
    console.getTextWindow().setCursorPosition(37,10);
    console.getWriter().println(String.format("%-8s%10s%16s\n","Rank", "Name", "Score"));



    for(int i = 0 ; i < 10 ; i++){

      if(temp != null){
        Player player = temp.getPlayer();
        if(i == 0)
          records[i] = String.format("%s\u265b\u265b\u265b %-12d%-17s%-5d \u265b\u265b\u265b","    ", i + 1, player.getName(), player.getScore()).toCharArray();
        else if(i == 1)
          records[i] = String.format("%s \u265b\u265b %-12d%-17s%-5d \u265b\u265b ","    ", i + 1, player.getName(), player.getScore()).toCharArray();
        else if(i == 2)
          records[i] = String.format("%s  \u265b %-12d%-17s%-5d \u265b  ","    ", i + 1, player.getName(), player.getScore()).toCharArray();
        else
          records[i] = String.format("%s    %-12d%-17s%-5d    ","    ", i + 1, player.getName(), player.getScore()).toCharArray();
        temp = temp.getNext();
      }
      else{
        records[i] = String.format("%s    %-12s%-17s%-5s    ","    ", "", "", "").toCharArray();
      }
    }

    char[][] check = new char[10][46];
    for(int i = 0 ; i < 460 ; ){
      int x = (int)(Math.random() * 46);
      int y = (int)(Math.random() * 10);

      if(check[y][x] == '\0'){
        i++;
        check[y][x] = 'F';
      }
      if(y == 0)
        console.setTextAttributes(new TextAttributes(new Color(0xF4,0xD4,0x42)));
      else if(y == 1)
        console.setTextAttributes(new TextAttributes(new Color(0x52,0xE5,0x22)));
      else if(y == 2)
        console.setTextAttributes(new TextAttributes(new Color(0xE0,0x0D,0x0D)));
      console.getTextWindow().setCursorPosition(x + 30,y + 12);
      console.getWriter().print(records[y][x]);
      if(i < 400)
        try{Thread.sleep(1);}catch(Exception ex){}
      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));

      if(System.currentTimeMillis() - leaderBoardLastChange >= 500){
        printLeaderboardText();
        leaderBoardLastChange = System.currentTimeMillis();
      }
    }

    while("Highscores".equals(activeScreen)){

      if(System.currentTimeMillis() - leaderBoardLastChange >= 500){
        printLeaderboardText();
        leaderBoardLastChange = System.currentTimeMillis();
      }
    }
  }

  public void deathAnimation(GameArea ga, Snake snake){

    gameOverCheck = new boolean[ga.getGround().length][ga.getGround()[0].length];
    this.activeScreen = "GameEnd";
    snake.setStatus(false);
    int dir = 0;
    int leftPaddingX = 0;
    int upPaddingY = 0;
    int rightPaddingX = ga.getGround()[0].length - 1;
    int downPaddingY = ga.getGround().length - 1;
    for(int i = 0 ; i < 53; i++){

      if(dir == 0){
        for(int k = leftPaddingX ; k <= rightPaddingX ; k++){
          if(k > 12 && k <= 12 + gameOver[0].length && upPaddingY > 1 && upPaddingY <= 1 + gameOver.length){
            ga.putChar(upPaddingY, k, gameOver[upPaddingY - 2][k - 13]);
            gameOverCheck[upPaddingY][k] = true;
          }
          else
            ga.putWall(upPaddingY, k);
          drawGameArea(ga.getGround(), snake);
          try{Thread.sleep(1);}catch(Exception ex){}
        }
        upPaddingY++;
        dir = 1;
      }
      else if(dir == 1){
        for(int k = upPaddingY ; k <= downPaddingY ; k++){

          ga.putWall(k,rightPaddingX);
          drawGameArea(ga.getGround(), snake);
          try{Thread.sleep(1);}catch(Exception ex){}
        }
        rightPaddingX--;
        dir = 2;
      }
      else if(dir == 2){
        for(int k = rightPaddingX ; k >= leftPaddingX ; k--){
          ga.putWall(downPaddingY, k);
          drawGameArea(ga.getGround(), snake);
          try{Thread.sleep(1);}catch(Exception ex){}
        }
        downPaddingY--;
        dir = 3;

      }
      else if(dir == 3){
        for(int k = downPaddingY ; k >= upPaddingY ; k--){
          ga.putWall(k, leftPaddingX);
          drawGameArea(ga.getGround(), snake);
          try{Thread.sleep(1);}catch(Exception ex){}
        }
        leftPaddingX++;
        dir = 0;

      }

    }
  }

  public void printGameEnd(Player player, ScoreTable scoreTable){

    console.getTextWindow().setCursorPosition(20, 20);
    console.setTextAttributes(new TextAttributes(new Color(0x07,0x28,0xBA), new Color(0x42,0xBC,0xF4)));
    console.getWriter().print("Enter your name: ");
    player.setName(console.readLine());
    scoreTable.addRecord(player);
    scoreTable.saveRecords();
    activeScreen = "Credits";

  }

  public String getActiveScreen(){
    return this.activeScreen;
  }

  public void setActiveScreen(String activeScreen){
    this.activeScreen = activeScreen;
  }

  public void printCredits(GameArea ga, Snake snake){

    for(int i = 0; i < ga.getGround().length ; i++){
      for(int j = 0 ; j < ga.getGround()[0].length ; j++){
        ga.putWall(i,j);
      }
    }

    drawGameArea(ga.getGround(), snake);
    if(cursorPosition2 == 0)
      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF), new Color(0x07,0x28,0xBA)));
    else
      console.setTextAttributes(new TextAttributes(new Color(0x07,0x28,0xBA), new Color(0x42,0xBC,0xF4)));
    console.getTextWindow().setCursorPosition(25, 9);
    console.getWriter().print("• Main Menu •");
    console.getTextWindow().setCursorPosition(24, 14);
    if(cursorPosition2 == 1)
      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF), new Color(0x07,0x28,0xBA)));
    else
      console.setTextAttributes(new TextAttributes(new Color(0x07,0x28,0xBA), new Color(0x42,0xBC,0xF4)));
    console.getWriter().print("• Highscores •");
    console.getTextWindow().setCursorPosition(27, 19);
    if(cursorPosition2 == 2)
      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF), new Color(0x07,0x28,0xBA)));
    else
      console.setTextAttributes(new TextAttributes(new Color(0x07,0x28,0xBA), new Color(0x42,0xBC,0xF4)));
    console.getWriter().print("• Exit •");

    console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));

  }

  public void printLevelSelect(){

    while("LevelSelect".equals(activeScreen)){
      console.getTextWindow().setCursorPosition(50, 10);
      if(cursorPosition3 == 0){
        console.setTextAttributes(new TextAttributes(new Color(0x00,0x00,0x00), new Color(0x5F,0xF4,0x42)));
      }
      console.getWriter().print("☠ Beginner ☠");
      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
      console.getTextWindow().setCursorPosition(47, 15);
      if(cursorPosition3 == 1){
        console.setTextAttributes(new TextAttributes(new Color(0x00,0x00,0x00), new Color(0xCC,0xA1,0x16)));
      }
      console.getWriter().print("☠☠ Professional ☠☠");
      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
      console.getTextWindow().setCursorPosition(48, 20);
      if(cursorPosition3 == 2){
        console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF),new Color(0xEF,0x2D,0x2D)));
      }
      console.getWriter().print("☠☠☠ Superstar ☠☠☠");

      console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
    }
  }

  public void printDesignArea(){

    console.getTextWindow().setCursorPosition(30, 0);
    console.getWriter().print("Create Your Map! Press 's' to save and 'Esc' to exit.");

    for(int i = 0 ; i < designArea.length ; i++){
      for(int j = 0 ; j < designArea[0].length ; j++){

        console.getTextWindow().setCursorPosition(j + 25, i + 2);
        if(designArea[i][j] == ' '){
          console.setTextAttributes(new TextAttributes(new Color(0x00,0x00,0x10), new Color(0xFF,0xFF,0xFF)));
          console.getWriter().print(" ");
        }
        else if(designArea[i][j] == Config.WALL){
          console.setTextAttributes(new TextAttributes(new Color(0x00,0x00,0xFF)));
          console.getWriter().print(Config.WALL);
        }
      }
      console.getWriter().println();
    }
    console.setTextAttributes(new TextAttributes(new Color(0xFF,0xFF,0xFF)));
  }

  public void printMapDesign(){

    printDesignArea();

  }

  public void saveDesign(){

    ga.setGround(this.designArea);
    ga.initializeFoods();
    this.designStatus = true;
    activeScreen = "Login";
  }

  @Override
  public void keyPressed(KeyEvent event){

    int key = event.getKeyCode();

    if(snake.getLastMovingTime() >= directionChangingTime && "Game".equals(activeScreen)){
      switch(key){
        case KeyEvent.VK_LEFT: if(snake.getDirection() != 'E') snake.setDirection('W'); directionChangingTime = System.currentTimeMillis();break;
        case KeyEvent.VK_UP: if(snake.getDirection() != 'S') snake.setDirection('N'); directionChangingTime = System.currentTimeMillis();break;
        case KeyEvent.VK_RIGHT: if(snake.getDirection() != 'W') snake.setDirection('E'); directionChangingTime = System.currentTimeMillis();break;
        case KeyEvent.VK_DOWN: if(snake.getDirection() != 'N') snake.setDirection('S'); directionChangingTime = System.currentTimeMillis();break;
        case KeyEvent.VK_P: snake.setStatus(!snake.getStatus()); activeScreen = "PauseMenu"; new SoundFX.PauseEffect().start();break;
      }
    }
    else if("Login".equals(activeScreen)){
      switch(key){
        case KeyEvent.VK_UP: if(cursorPosition == 0) cursorPosition = 2; else cursorPosition--;new SoundFX.UIEffect().start();break;
        case KeyEvent.VK_DOWN: if(cursorPosition == 2) cursorPosition = 0; else cursorPosition++;new SoundFX.UIEffect().start();break;
        case KeyEvent.VK_ENTER: if(cursorPosition == 0) activeScreen = "LevelSelect";
                                else if(cursorPosition == 1) activeScreen = "Highscores";
                                else if(cursorPosition == 2) activeScreen = "MapDesign";break;
        case KeyEvent.VK_ESCAPE: System.exit(0);break;
      }
    }
    else if("PauseMenu".equals(activeScreen)){
      switch(key){
        case KeyEvent.VK_P: snake.setStatus(!snake.getStatus()); activeScreen = "Game"; new SoundFX.PauseEffect().start();break;
      }
    }
    else if("Highscores".equals(activeScreen)){
      switch(key){
        case KeyEvent.VK_ESCAPE: activeScreen = "Login";break;
      }
    }
    else if("Credits".equals(activeScreen)){

      switch(key){
        case KeyEvent.VK_UP: if(cursorPosition2 == 0) cursorPosition2 = 2; else cursorPosition2--;new SoundFX.UIEffect().start();break;
        case KeyEvent.VK_DOWN: if(cursorPosition2 == 2) cursorPosition2 = 0; else cursorPosition2++;new SoundFX.UIEffect().start();break;
        case KeyEvent.VK_ENTER: switch(cursorPosition2){
                                  case 0: uiReset();activeScreen = "Login";break;
                                  case 1: uiReset();activeScreen = "Highscores";break;
                                  case 2: System.exit(0);break;
                                }break;
      }
    }
    else if("MapDesign".equals(activeScreen)){

      switch(key){
        case KeyEvent.VK_ESCAPE: activeScreen = "Login";break;
        case KeyEvent.VK_S: saveDesign();break;
      }
    }
    else if("LevelSelect".equals(activeScreen)){

      switch(key){
        case KeyEvent.VK_ESCAPE: activeScreen = "Login";break;
        case KeyEvent.VK_UP: if(cursorPosition3 == 0) cursorPosition3 = 2; else cursorPosition3--;new SoundFX.UIEffect().start();break;
        case KeyEvent.VK_DOWN: if(cursorPosition3 == 2) cursorPosition3 = 0; else cursorPosition3++;new SoundFX.UIEffect().start();break;
        case KeyEvent.VK_ENTER: switch(cursorPosition3){
                                  case 0: this.selectedSnakeSpeed = Config.SNAKESPEEDBEGINNER;activeScreen = "Game";break;
                                  case 1: this.selectedSnakeSpeed = Config.SNAKESPEEDPROFESSIONAL;activeScreen = "Game";break;
                                  case 2: this.selectedSnakeSpeed = Config.SNAKESPEEDSUPERSTAR;activeScreen = "Game";break;
                                }break;
      }
    }
  }

  public void setSnake(Snake snake){
    this.snake = snake;
  }

  public void setGameArea(GameArea ga){
    this.ga = ga;
  }

  public boolean getDesignStatus(){
    return this.designStatus;
  }

  public void setDesignStatus(boolean designStatus){
    this.designStatus = designStatus;
  }

  public int getSelectedSnakeSpeed() {
  	return this.selectedSnakeSpeed;
  }

  @Override
  public void keyReleased(KeyEvent event){

  }

  @Override
  public void keyTyped(KeyEvent event){

  }



  @Override
  public void mouseReleased(TextMouseEvent event){

  }

  @Override
  public void mousePressed(TextMouseEvent event){


    if("MapDesign".equals(activeScreen)){
      int x = 0;
      int y = 0;
      if((x = event.getX()) < designArea[0].length + 25 && x >= 25 && (y = event.getY()) < designArea.length + 2 && y >= 2){
        designArea[y- 2][x - 25] = Config.WALL;
        printDesignArea();
      }
    }
  }

  @Override
  public void mouseClicked(TextMouseEvent event){


  }
}
