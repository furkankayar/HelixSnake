import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class SoundFX{

  static class CrunchEffect extends Thread{


    @Override
    public void run(){

      try{
        File sound = new File(Config.CRUNCHEFFECTFILE);
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(sound));
        clip.start();
        do{
          Thread.sleep(1);
        }while(clip.isRunning());
        clip.stop();
      }
      catch(Exception e){

      }
    }
  }

  static class PauseEffect extends Thread{


    @Override
    public void run(){

      try{
        File sound = new File(Config.PAUSESOUNDFILE);
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(sound));
        clip.start();
        do{
          Thread.sleep(1);
        }while(clip.isRunning());
        clip.stop();
      }
      catch(Exception e){

      }
    }
  }

  static class UIEffect extends Thread{


    @Override
    public void run(){

      try{
        File sound = new File(Config.UISOUNDFILE);
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(sound));
        clip.start();
        do{
          Thread.sleep(1);
        }while(clip.isRunning());
        clip.stop();

      }
      catch(Exception e){

      }
    }
  }

  static class ConfirmEffect extends Thread{

    @Override
    public void run(){

      try{
        File sound = new File(Config.CONFIRMEFFECTFILE);
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(sound));
        clip.start();
        do{
          Thread.sleep(1);
        }while(clip.isRunning());
        clip.stop();
      }
      catch(Exception e){

      }
    }
  }

  static class GameMusic extends Thread{

    File sound = new File(Config.GAMEMUSICFILE);
    boolean run = true;

    @Override
    public void run(){

      while(true){
        try{
          Clip clip = AudioSystem.getClip();
          clip.open(AudioSystem.getAudioInputStream(sound));
          clip.start();
          do{
            Thread.sleep(1);
          }while(clip.isRunning() && this.run);
          clip.stop();
          if(!this.run) break;
        }
        catch(Exception e){

        }
      }
    }
  }

  static class GameOverEffect extends Thread{

    @Override
    public void run(){

      try{
        File sound = new File(Config.GAMEOVEREFFECTFILE);
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(sound));
        clip.start();
        do{
          Thread.sleep(1);
        }while(clip.isRunning());
        clip.stop();
      }
      catch(Exception e){

      }
    }
  }




}
