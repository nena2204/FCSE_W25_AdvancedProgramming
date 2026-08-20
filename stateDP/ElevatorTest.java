package stateDP;

interface State2 {
    void callUp();

    void callDown();

    void openDoor();

    void closeDoor();
}
//treba da razlikuvame akcii od states
//akcii ode vo interface State
//opcii za state odat vo glavna klasa
//kreiras i eden state
//metod createstates() + startna
//abstract state + konstruktor

//DEF STATES sega gledas spored metodite so se slucuva sledno + azuriras state

//vo glavna klasa od main dodadi metodi
//dodadi geteri i seteri za toa so cuvas vo glavna klasa

class ElevatorController {
    int currentFloor;
    int maxFloor;

    State2 doorsClosed;
    State2 doorsOpened;

    State2 state;

    final void createStates() {
        doorsClosed = new DoorClosed(this);
        doorsOpened = new DoorsOpened(this);
        state = doorsClosed;
    }

    public ElevatorController(int maxFloor) {
        this.currentFloor = 0;
        this.maxFloor = maxFloor;
        createStates();
    }

    public State2 getDoorsClosed() {
        return doorsClosed;
    }

    public State2 getDoorsOpened() {
        return doorsOpened;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) {
        this.currentFloor = floor;
    }

    public void callUp() {
        state.callUp();
    }

    public void callDown() {
        state.callDown();
    }

    public void openDoor() {
        state.openDoor();
    }

    public void closeDoor() {
        state.closeDoor();
    }
}

class DoorClosed extends AbstractState {
    public DoorClosed(ElevatorController elevator) {
        super(elevator);
    }

    @Override
    public void callUp() {
        if (elevator.getCurrentFloor()< elevator.maxFloor){
            elevator.setCurrentFloor(elevator.getCurrentFloor()+1);
            System.out.println("Moving up to floor "+elevator.getCurrentFloor());
        }else if (elevator.currentFloor== elevator.maxFloor){
            System.out.println("already at the top floor");
        }
    }

    @Override
    public void callDown() {
        if (elevator.currentFloor>0){
            elevator.setCurrentFloor(elevator.getCurrentFloor()-1);
            System.out.println("moving down to floor "+elevator.getCurrentFloor());
        }
        else if (elevator.currentFloor==0){
            System.out.println("already at the ground floor");
        }
    }

    @Override
    public void openDoor() {
        System.out.println("doors open at floor: "+elevator.getCurrentFloor());
        elevator.state= elevator.getDoorsOpened();
    }

    @Override
    public void closeDoor() {
        System.out.println("doors already closed");
    }
}

class DoorsOpened extends AbstractState {
    public DoorsOpened(ElevatorController elevator) {
        super(elevator);
    }

    @Override
    public void callUp() {
        System.out.println("close doors first");
    }

    @Override
    public void callDown() {
        System.out.println("close door first");
    }

    @Override
    public void openDoor() {
        System.out.println("doors already opended");
    }

    @Override
    public void closeDoor() {
        System.out.println("doors closing");
        elevator.state= elevator.getDoorsClosed();
    }
}

abstract class AbstractState implements State2 {
    ElevatorController elevator;

    public AbstractState(ElevatorController elevator) {
        this.elevator = elevator;
    }
}

public class ElevatorTest {
    public static void main(String[] args) {
        ElevatorController elevator = new ElevatorController(5);

        elevator.callDown();       // "Already at ground floor"
        elevator.callUp();         // "Moving up to floor 1"
        elevator.callUp();         // "Moving up to floor 2"
        elevator.openDoor();       // "Doors opening at floor 2"
        elevator.callUp();         // "Close doors first"
        elevator.openDoor();       // "Doors already open"
        elevator.closeDoor();      // "Doors closing"
        elevator.closeDoor();      // "Doors already closed"
        elevator.callDown();       // "Moving down to floor 1"
    }
}
