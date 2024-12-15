package ch.herrderb.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

import ch.herrderb.InputType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepeateWhilePressed extends Action {

    private final int keyCode;
    private final Action action;
    private final InputType inputType;

    public RepeateWhilePressed(int keyCode, Action action, InputType inputType) {
        this.keyCode = keyCode;
        this.action = action;
        this.inputType = inputType;
    }

    @Override
    public boolean act(float delta) {
        var actionComplete = action.act(delta);
        boolean isPressed = false;
        switch (inputType) {
            case BUTTON:
                isPressed = Gdx.input.isButtonPressed(keyCode);
                break;
            case KEY:
                isPressed = Gdx.input.isKeyPressed(keyCode);
                break;
        }
        if (actionComplete && isPressed) {
            action.restart();
            return false;
        }
        return actionComplete;
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);
        action.setActor(actor);
    }

    @Override
    public void setPool(Pool pool) {
        super.setPool(pool);
        action.setPool(pool);
    }

    @Override
    public void setTarget(Actor target) {
        super.setTarget(target);
    }

}
