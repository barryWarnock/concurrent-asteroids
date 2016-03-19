package entity;

public class Asteroid extends Entity {

    private int size;

    public Asteroid(int size) {
        this.size = size;
    }


    @Override
    public void runCollisionChecking() {
        //TODO
    }

    @Override
    public void updateLocationAndMomentum() {
        //TODO
    }


    private void split() {
        //TODO add new asteroids to a global list of entities.
        //TODO conserve momentum not add it
        int newMomentum = momentum()/2;
        Asteroid asteroid1 = new Asteroid(size/2);
        this.size = size/2;
    }

    /**
     *
     * @return Asteroid momentum
     */
    @Override
    protected int momentum() {
        return (xSpeed + ySpeed) * size;
    }
}

