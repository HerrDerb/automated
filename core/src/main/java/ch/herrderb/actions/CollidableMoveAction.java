package ch.herrderb.actions;

import java.util.Objects;
import java.util.stream.Stream;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelegateAction;

import ch.herrderb.abilities.Collidable;

public class CollidableMoveAction extends DelegateAction {

    public CollidableMoveAction(Action action) {
        setAction(action);
    }

    @Override
    public boolean delegate(float delta) {
        var xPrevious = getActor().getX();
        var yPrevious = getActor().getY();
        var actionCompleted = action.act(delta);
        var collides = intersectsWithCollidable();
        if (collides) {
            getActor().setPosition(xPrevious, yPrevious);
            return true;
        }
        return actionCompleted;
    }

    private boolean intersectsWithCollidable() {
        return Stream.of(getActor().getStage().getActors().toArray()).filter(Objects::nonNull)
                .filter(actor -> !Objects.equals(actor, getActor()))
                .filter(actor -> actor instanceof Collidable)
                .map(actor -> (Collidable) actor)
                .anyMatch(collidable -> {
                    return collidable.intersects(getActor());
                });
    }

    @Override
    public void setTarget(Actor target) {
        action.setTarget(target);
    }

}
