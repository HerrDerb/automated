package ch.herrderb.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

import ch.herrderb.actors.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActivateItemSlot extends Action {

    private final int itemSlot;

    @Override
    public boolean act(float delta) {
        if (getActor() instanceof Player player) {
            player.activateItemSlot(itemSlot);
        }
        return true;
    }

}
