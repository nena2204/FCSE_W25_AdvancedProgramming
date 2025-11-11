import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//interface
interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;

    void moveLeft() throws ObjectCanNotBeMovedException;

    void moveRight() throws ObjectCanNotBeMovedException;

    void moveDown() throws ObjectCanNotBeMovedException;

    int getCurrentXPosition();

    int getCurrentYPosition();
}

//exceptions
class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) {
        super(message);
    }
}

class MovablesCollection {
    private static int X_MAX = 0;
    private static int Y_MAX = 0;

    private final List<Movable> movable = new ArrayList<>();

    public MovablesCollection(int x_max, int y_max) {
        X_MAX = x_max;
        Y_MAX = y_max;
    }

    public static int getxMax() {
        return X_MAX;
    }

    public static int getyMax() {
        return Y_MAX;
    }

    public static void setxMax(int xMax) {
        X_MAX = xMax;
    }

    public static void setyMax(int yMax) {
        Y_MAX = yMax;
    }

    void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        if (m instanceof MovableCircle) {
            MovableCircle c = (MovableCircle) m;
            int circle_x = c.getCurrentXPosition();
            int circle_y = c.getCurrentYPosition();
            int radius = c.getRadius();
            boolean fits = (circle_x - radius) >= 0 && (circle_x + radius) <= X_MAX && (circle_y - radius) >= 0 && (circle_y + radius) <= Y_MAX;
            if (!fits) {
                throw new MovableObjectNotFittableException(
                    String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection", circle_x, circle_y, radius)

                );
            }
        } else {
            int x = m.getCurrentXPosition();
            int y = m.getCurrentYPosition();
            boolean fits = x >= 0 && x <= X_MAX && y >= 0 && y <= Y_MAX;
            if (!fits) {
                throw new MovableObjectNotFittableException(
                        String.format("The point with coordinates (%d,%d) cannot fit in the collection.", x, y)
                );
            }
        }
        movable.add(m);
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) {
        for (Movable m : movable) {
            boolean matches = (type == TYPE.POINT && m instanceof MovablePoint) || (type == TYPE.CIRCLE && m instanceof MovableCircle);
            if (!matches)
                continue;

            try {
                if (direction == DIRECTION.UP) {
                    m.moveUp();
                } else if (direction == DIRECTION.DOWN) {
                    m.moveDown();
                } else if (direction == DIRECTION.LEFT) {
                    m.moveLeft();
                } else if (direction == DIRECTION.RIGHT) {
                    m.moveRight();
                }
            } catch (ObjectCanNotBeMovedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Collection of movable objects with size ").append(movable.size()).append(":\n");
        for (int i = 0; i < movable.size(); i++) {
            sb.append(movable.get(i));
            if (i < movable.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

class MovablePoint implements Movable {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }


    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        int newY = y + ySpeed;
        if (newY > MovablesCollection.getyMax()) {
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds [0,%d].", x,newY)
            );
        }
        y = newY;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        int newX = x - xSpeed;
        if (newX < 0) {
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds", newX, y)
            );
        }
        x = newX;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        int newX = x + xSpeed;
        if (newX > MovablesCollection.getxMax()) {
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds", newX, y)
            );
        }
        x = newX;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        int newY = y - ySpeed;
        if (newY < 0) {
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds", x, newY)
            );
        }
        y = newY;
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    @Override
    public String toString() {
        return "Movable point with coordinates (" + x + "," + y + ")";
        //Movable point with coordinates (5,35)
    }
}

class MovableCircle implements Movable {
    private int radius;
    private MovablePoint movingCenter;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.movingCenter = center;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        movingCenter.moveUp();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        movingCenter.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        movingCenter.moveRight();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        movingCenter.moveDown();
    }

    @Override
    public int getCurrentXPosition() {
        return movingCenter.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return movingCenter.getCurrentYPosition();
    }

    @Override
    public String toString() {
        return "Movable circle with center coordinates (" + movingCenter.getCurrentXPosition() + "," + movingCenter.getCurrentYPosition() + ") and radius " + radius;
        //Movable circle with center coordinates (48,21) and radius 3
    }
}


enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

public class CirclesTest {

    public static void main(String[] args) {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) { //point
                try {

                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }
            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                try {

                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                }catch (MovableObjectNotFittableException e){
                    System.out.println(e.getMessage());
                }
            }


        }
        System.out.println(collection.toString());
        System.out.println();

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());
        System.out.println();

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());
        System.out.println();

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());
        System.out.println();

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());
        System.out.println();


    }


}

