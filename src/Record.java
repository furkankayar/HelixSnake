



public class Record{
  private Player player;
  private Record next;
  private Record prev;

  public Record(Player player){
    this.player = player;
    this.next = null;
    this.prev = null;
  }

  public Record getNext(){
    return this.next;
  }
  public Record getPrev(){
    return this.prev;
  }
  public Player getPlayer(){
    return this.player;
  }

  public void setNext(Record next){
    this.next = next;
  }
  public void setPrev(Record prev){
    this.prev = prev;
  }
}
