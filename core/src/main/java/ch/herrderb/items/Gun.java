package ch.herrderb.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import ch.herrderb.MyActions;
import ch.herrderb.actions.Shoot;

public class Gun extends BasicItem {

    @Override
    public void trigger() {
        Gdx.app.log("Gun", "triggered");
        addAction(MyActions.foreverWhileButtonPressed(Input.Buttons.LEFT, Shoot.shoot()));
    }

}
