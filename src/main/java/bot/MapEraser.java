package bot;

import java.util.TimerTask;

public class MapEraser extends TimerTask {
    @Override
    public void run(){
        JokeBot.chatMap.clear();
        System.out.println("erased");
    }
}
