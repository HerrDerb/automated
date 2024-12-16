package ch.herrderb.actions;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.scenes.scene2d.Action;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShakeAction extends Action {
    private final int intensity;
    private final Duration duration ;
    private int initialX;
    private int initialY;
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
