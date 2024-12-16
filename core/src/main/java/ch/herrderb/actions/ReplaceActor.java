package ch.herrderb.actions;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReplaceActor extends Action {

    private final Class<? extends Actor> actorClass;
    private final int xOffset;
    private final int yOffset;

    @Override
    public boolean act(float delta) {
        var newX = getActor().getX() + xOffset;
        var newY = getActor().getY() + yOffset;
        Actor newActor;
        try {
            newActor = actorClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
        newActor.setPosition(newX, newY);
        getActor().getStage().addActor(newActor);
        getActor().addAction(Actions.removeActor());
        return true;
    }

}
