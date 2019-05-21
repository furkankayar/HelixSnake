import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScoreTable{
  private Record head;
  private Record tail;

  public ScoreTable(){
    try{
      createFile();
      readRecords();
    }catch(IOException e){

    }
  }

  public void addRecord(Player player){
    Record newRecord = new Record(player);

    if(head == null){
      head = newRecord;
      tail = head;
    }else{
      Record temp = head;

      if(head.getPlayer().getScore() < player.getScore()){
        head.setPrev(newRecord);
        newRecord.setNext(head);
        head = newRecord;
      }
      else if(tail.getPlayer().getScore() >= player.getScore()){

        tail.setNext(newRecord);
        newRecord.setPrev(tail);
        tail = newRecord;
      }
      else{

        while(temp.getPlayer().getScore() > player.getScore())
          temp = temp.getNext();

        temp.getPrev().setNext(newRecord);
        newRecord.setPrev(temp.getPrev());
        newRecord.setNext(temp);
        temp.setPrev(newRecord);

      }
    }
  }


  public void createFile()throws IOException{
    try{
      FileReader file = new FileReader(new File(Config.SCOREFILE));
      file.close();
    }catch(FileNotFoundException ex){
      new File(Config.SCOREFILE).createNewFile();
    }
  }

  public void display(){

    Record temp = head;

    while(temp != null){
      System.out.println(temp.getPlayer().getName() + " " + temp.getPlayer().getScore());
      temp = temp.getNext();
    }
  }

  public void saveRecords(){

    Record temp = head;


    try{
      BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Config.SCOREFILE)));
      while(temp != null){
        bw.write(temp.getPlayer().toString());
        temp = temp.getNext();
      }
      bw.close();
    }
    catch(FileNotFoundException ex){

    }
    catch(IOException ex2){

    }
  }

  public void readRecords(){

    try{
      BufferedReader br = new BufferedReader(new FileReader(new File(Config.SCOREFILE)));
      String row = null;
      while((row = br.readLine()) != null){
        String[] rowArr = row.split(Config.SEPERATOR);
        Player player = new Player(rowArr[0], Integer.valueOf(rowArr[1]));
        addRecord(player);
      }
      br.close();
    }
    catch(FileNotFoundException ex){

    }
    catch(IOException ex2){

    }
    catch(NumberFormatException ex){

    }
  }


  public Record getHead(){
    return this.head;
  }
}
