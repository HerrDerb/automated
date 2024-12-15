package ch.herrderb.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

import ch.herrderb.actors.ItemSlot;
import ch.herrderb.actors.Player;

public class TriggerItem extends Action {

    private ItemSlot itemSlot;

    public TriggerItem(ItemSlot itemSlot) {
        this.itemSlot = itemSlot;
    }

    @Override
    public boolean act(float delta) {
        if (getActor() instanceof Player player) {
            player.triggerItem(itemSlot);
        }
        return true;
    }

}
