package ch.herrderb;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.scenes.scene2d.Action;

public class ShakeAction extends Action {
    private int initialX;
    private int initialY;
    private final int intensity = 10;

    private Duration duration = Duration.ofMillis(500);
    private Instant startTime = Instant.MIN;

    @Override
    public boolean act(float delta) {
        if (initialX == 0) {
            initialX = (int) actor.getX();
            initialY = (int) actor.getY();
            startTime = Instant.now();
        }
        var xShake = (int) (Math.random() * intensity - intensity / 2);
        var yShake = (int) (Math.random() * intensity - intensity / 2);
        actor.setPosition(initialX + xShake, initialY + yShake);

        if (startTime.plus(duration).isBefore(Instant.now())) {
            actor.setPosition(initialX, initialY);
            initialX = 0;
            initialY = 0;
            return true;
        }
        return false;
    }

}
