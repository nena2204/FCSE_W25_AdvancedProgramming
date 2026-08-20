package stateDP;

import java.util.ArrayList;
import java.util.List;
class Song {
    String title;
    String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return " Song {" + " title =" + title + ", artist =" + artist + "}";
    }
}
interface State1 {

    void pressPlay();
    void pressStop();
    void pressFwd();
    void pressRew();
    void forward();
    void reward();
}

class MP3Player {
    List<Song> songs;
    int currentSong;

    State1 play;
    State1 pause;
    State1 stop;
    State1 fwd;
    State1 rew;

    final void createStates() {
        play = new PlayState(this);
        pause = new PauseState(this);
        stop = new StopState(this);
        fwd = new FWDState(this);
        rew = new REWState(this);
        state = stop;
    }

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        currentSong = 0;
        createStates();
    }

    public State1 getPlay() {
        return play;
    }

    public void setPlay(State1 play) {
        this.play = play;
    }

    public State1 getPause() {
        return pause;
    }

    public void setPause(State1 pause) {
        this.pause = pause;
    }

    public State1 getStop() {
        return stop;
    }

    public State1 getFwd() {
        return fwd;
    }

    public State1 getRew() {
        return rew;
    }

    public void setStop(State1 stop) {
        this.stop = stop;
    }

    public void setFwd(State1 fwd) {
        this.fwd = fwd;
    }

    public void setRew(State1 rew) {
        this.rew = rew;
    }

    State1 state;

    public State1 getState() {
        return state;
    }

    public void setState(State1 state) {
        this.state = state;
    }

    public Song getCurrentSong() {
        return songs.get(currentSong);
    }

    public void setSongIndex(int currentsong) {
        this.currentSong = currentsong % songs.size();
    }

    public int getSongIndex() {
        return currentSong;
    }

    public void songFWD() {
        currentSong = (currentSong + 1) % songs.size();
    }

    public void songREW() {
        currentSong = (currentSong + songs.size() - 1) % songs.size();
    }

    public void pressPlay() {
        state.pressPlay();
    }

    public void pressStop() {
        state.pressStop();
    }

    public void pressFWD() {
        state.pressFwd();
        state.forward();
    }

    public void pressREW() {
        state.pressRew();
        state.reward();
    }

    void printCurrentSong() {
        System.out.println(getCurrentSong());
    }

    @Override
    public String toString() {
        return "MP3Player {currentSong = " + currentSong + ", songList = " + songs + " }";
    }
}

abstract class AbstractState1 implements State1 {
    MP3Player mp3;
    public AbstractState1(MP3Player pl) {
        this.mp3 = pl;
    }
}

//momentalno e kliknato play i pee
class PlayState extends AbstractState1 {
    public PlayState(MP3Player player) {
        super(player);
    }

    @Override
    public void pressPlay() {
        System.out.println(" Song is already playing ");
    }

    @Override
    public void pressStop() {
        System.out.println("Song " + mp3.getSongIndex() + " is paused");
        mp3.setState(mp3.getPause());
    }

    @Override
    public void pressFwd() {
        System.out.println("Forward...");
        mp3.setState(mp3.getFwd());
    }

    @Override
    public void pressRew() {
        System.out.println("Reward ...");
        mp3.setState(mp3.getRew());
    }

    @Override
    public void forward() {
        System.out.println("Illegal action");
    }

    @Override
    public void reward() {
        System.out.println("Illegal action");
    }
}

class FWDState extends AbstractState1 {

    public FWDState(MP3Player mp3) {
        super(mp3);
    }

    @Override
    public void pressPlay() {
        System.out.println("Illegal action");
    }

    @Override
    public void pressStop() {
        System.out.println("Illegal action");
    }

    @Override
    public void pressFwd() {
        System.out.println("Illegal action");

    }

    @Override
    public void pressRew() {
        System.out.println("Illegal action");

    }

    @Override
    public void forward() {
        mp3.songFWD();
        mp3.setState(mp3.getPause());
    }

    @Override
    public void reward() {
        System.out.println("Illegal action");
    }
}

class REWState extends AbstractState1 {

    public REWState(MP3Player mp3) {
        super(mp3);
    }

    @Override
    public void pressPlay() {
        System.out.println("Illegal action");
    }

    @Override
    public void pressStop() {
        System.out.println("Illegal action");

    }

    @Override
    public void pressFwd() {
        System.out.println("Illegal action");

    }

    @Override
    public void pressRew() {
        System.out.println("Illegal action");

    }

    @Override
    public void forward() {
        System.out.println("Illegal action");

    }

    @Override
    public void reward() {
        mp3.songREW();
        mp3.setState(mp3.getPause());
    }
}

class StopState extends AbstractState1 {

    public StopState(MP3Player pl) {
        super(pl);
    }

    @Override
    public void pressPlay() {
        System.out.println("Song " + mp3.getSongIndex() + " is playing");
        mp3.setState(mp3.getPlay());
    }

    @Override
    public void pressStop() {
        System.out.println("Song is already stopped");
    }

    @Override
    public void pressFwd() {
        System.out.println("Forward...");
        mp3.setState(mp3.getFwd());
    }

    @Override
    public void pressRew() {

        System.out.println("Reward...");
        mp3.setState(mp3.getRew());
    }

    @Override
    public void forward() {
        System.out.println("Illegal action");
    }

    @Override
    public void reward() {
        System.out.println("Illegal action");
    }
}

class PauseState extends AbstractState1 {
    public PauseState(MP3Player pl) {
        super(pl);
    }

    @Override
    public void pressPlay() {
        System.out.println("Song " + mp3.getSongIndex() + " is playing");
        mp3.setState(mp3.getPlay());
    }

    @Override
    public void pressStop() {
        System.out.println("Songs are stopped");
        mp3.setSongIndex(0);
        mp3.setState(mp3.getStop());
    }

    @Override
    public void pressFwd() {
        System.out.println("Forward...");
        mp3.setState(mp3.getFwd());
    }

    @Override
    public void pressRew() {
        System.out.println("Reward...");
        mp3.setState(mp3.getRew());
    }

    @Override
    public void forward() {
        System.out.println("Illegal action");

    }
    @Override
    public void reward() {
        System.out.println("Illegal action");
    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);

        System.out.println(player.toString());
        System.out.println("First test");

        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");

        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");

        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}


