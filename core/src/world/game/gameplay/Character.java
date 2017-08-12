package world.game.gameplay;

public interface Character {
    int getXField();
    int getYField();
    String getName();
    int getSpeed();
    int getStrength();
    int getHealth();
    void setHealth(int health);
    String toString();
    boolean moved = false;
    void checkIfAlive();
}
