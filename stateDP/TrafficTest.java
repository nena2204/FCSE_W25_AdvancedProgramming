package stateDP;

//treba da razlikuvame akcii od states
//akcii ode vo interface State
//opcii za state odat vo glavna klasa
//kreiras i eden state
//metod createstates() + startna
//abstract state + konstruktor
//DEF STATES sega gledas spored metodite so se slucuva sledno + azuriras state

//vo glavna klasa od main dodadi metodi
//dodadi geteri i seteri za toa so cuvas vo glavna klasa

interface State3 {
    void nextSignal();

    void emergencyStop();
}

class TrafficLight {
    int secondsInState;

    public TrafficLight() {
        this.secondsInState = 0;
        createStates();
    }

    State3 state;
    State3 red;
    State3 green;
    State3 yellow;

    final void createStates() {
        red = new RedState(this);
        green = new GreenState(this);
        yellow = new YellowState(this);
        state = red;
    }

    public void nextSignal(){
        state.nextSignal();
    }
    public void emergencyStop(){
        state.emergencyStop();
    }

}

abstract class AbstractState3 implements State3 {
    TrafficLight light;

    public AbstractState3(TrafficLight light) {
        this.light = light;
    }
}

class RedState extends AbstractState3 {
    public RedState(TrafficLight light) {
        super(light);
    }

    @Override
    public void nextSignal() {
        System.out.println("Light turning to green");
        light.state = light.green;
    }

    @Override
    public void emergencyStop() {
        System.out.println("Already stopped, light is Red");
    }
}

class GreenState extends AbstractState3 {
    public GreenState(TrafficLight light) {
        super(light);
    }

    @Override
    public void nextSignal() {
        System.out.println("Light turning to yellow");
        light.state = light.yellow;
    }

    @Override
    public void emergencyStop() {
        System.out.println("Emergency stop activated, light is Red");
        light.state= light.red;
    }
}
class YellowState extends AbstractState3{
    public YellowState(TrafficLight light) {
        super(light);
    }

    @Override
    public void nextSignal() {
        System.out.println("Light turning to red");
        light.state= light.red;
    }

    @Override
    public void emergencyStop() {
        System.out.println("Emergency stop activated, light is Red");
        light.state= light.red;
    }
}
public class TrafficTest {
    public static void main(String[] args) {
        TrafficLight light = new TrafficLight();

        light.nextSignal();        // "Light turning to Green"
        light.nextSignal();        // "Light turning to Yellow"
        light.emergencyStop();     // "Emergency stop activated, light is Red"
        light.emergencyStop();     // "Already stopped, light is Red"
        light.nextSignal();        // "Light turning to Green"
        light.nextSignal();        // "Light turning to Yellow"
        light.nextSignal();        // "Light turning to Red"
    }
}
