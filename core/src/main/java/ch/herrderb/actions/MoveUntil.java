package ch.herrderb.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

import ch.herrderb.abilities.Angled;

public class MoveUntil extends Action {

    private final float angle, speed, maxDistance;
    private float distanceTravelled;

    MoveUntil(float angle, float speed, float maxDistance) {
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.angle = angle;
    }

    @Override
    public boolean act(float delta) {
        if (getActor() instanceof Angled angledActor) {
            angledActor.setAngle(Math.toDegrees(angle));
        }
        float x = (float) (getActor().getX() + Math.cos(angle) * speed * delta);
        float y = (float) (getActor().getY() + Math.sin(angle) * speed * delta);
        getActor().setPosition(x, y);
        distanceTravelled += speed * delta;
        if (distanceTravelled >= maxDistance) {
            return true;
        }
        return false;
    }

    public static Action to(float angle, int speed, int maxDistance) {
        return new MoveUntil(angle, speed, maxDistance);
    }

}
