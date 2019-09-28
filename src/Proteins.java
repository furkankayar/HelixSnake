
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;



public class Proteins{

  private Aminoacid head;
  private BufferedReader br;

  public Proteins(){

    try{
      br = new BufferedReader(new FileReader(new File(Config.PROTEINFILE)));
    }
    catch(FileNotFoundException ex){
      System.out.print("aminoacids.txt has not been found. Terminating...");
      for(int i = 5 ; i > 0 ; i--){
        System.out.print(" " +i);
        try{Thread.sleep(1000);}catch(Exception e){}
      }

      System.exit(0);
    }

    createProteins();
  }



  public void createProteins(){

    String row = null;

    while((row = readRow()) != null){
      String[] fields = row.split(",");
      addAminoacid(fields[0], fields[1]);
      for(int i = 2 ; i < fields.length ; i++){
        String[] parts = fields[i].split("-");
        addCodon(fields[0], parts[0], Integer.valueOf(parts[1]));
      }
    }
  }

  public void addAminoacid(String name, String abbreviation){

    if(head == null){

      Aminoacid newNode = new Aminoacid(name, abbreviation);
      head = newNode;
    }
    else{
      Aminoacid temp = head;
      while(temp.getNext() != null)
        temp = temp.getNext();
      Aminoacid newNode = new Aminoacid(name, abbreviation);
      temp.setNext(newNode);
    }
  }

  public void addCodon(String aminoacidName, String name, int point){

    if(head == null){
      //print error
    }
    else{
      Aminoacid temp = head;

      while(temp != null){

        if(aminoacidName.equals(temp.getName())){

          Codon temp2 = temp.getHeadCodon();
          if( temp2 == null){
            Codon newNode = new Codon(name, point);
            temp.setHeadCodon(newNode);
          }
          else{
            while(temp2.getNext() != null)
              temp2 = temp2.getNext();
            Codon newNode = new Codon(name, point);
            temp2.setNext(newNode);
          }
        }
        temp = temp.getNext();
      }
    }

  }


  public int searchCodon(String codon){

    Aminoacid tempAmino = head;

    while(tempAmino != null){

      Codon temp = tempAmino.getHeadCodon();
      while(temp != null){
        if(codon.equals(temp.getCode())){
          return temp.getPoint();
        }
        temp = temp.getNext();
      }
      tempAmino = tempAmino.getNext();
    }

    return -1;
  }





  public String readRow(){

    try{
      return br.readLine();
    }
    catch(IOException ex){

    }

    return null;
  }
}
