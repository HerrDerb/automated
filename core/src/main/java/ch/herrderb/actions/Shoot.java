package ch.herrderb.actions;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ch.herrderb.abilities.Angled;
import ch.herrderb.actors.BasicShot;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Shoot extends Action {
    
    private Duration coolDown = Duration.ofMillis(100);
    private Instant lastShot = Instant.MIN;

    @Override
    public boolean act(float delta) {
        if (lastShot.plus(coolDown).isAfter(Instant.now())) {
            return false;
        }
        lastShot = Instant.now();
        float xTarget = Gdx.input.getX();
        float yTarget = Gdx.graphics.getHeight() - Gdx.input.getY();
        float deltaX = xTarget - getActor().getX();
        float deltaY = yTarget - getActor().getY();
        var angleRadiant = (float) Math.atan2(deltaY, deltaX);
        if (getActor() instanceof Angled angledActor) {
            angledActor.setAngle(Math.toDegrees(angleRadiant));
        }
        var shot = new BasicShot();
        shot.setPosition(getActor().getX() + getActor().getWidth() / 2, getActor().getY() + getActor().getHeight() / 2);
        shot.addAction(Actions.sequence(MoveUntil.to(angleRadiant, 150, 300), Actions.removeActor()));
        actor.getStage().addActor(shot);
        Gdx.app.log("Shoot", "Shooting from " + shot.getX() + " " + shot.getY() + " to " + xTarget + " " + yTarget);
        return true;
    }

    @Override
    public void restart() {
        super.restart();
    }

    public static Action shoot() {
        return new Shoot();
    }

}
