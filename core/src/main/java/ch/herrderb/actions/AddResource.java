package ch.herrderb.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

import ch.herrderb.actors.Player;
import ch.herrderb.actors.resource.ResourceType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddResource extends Action {

    private final ResourceType wood;
    private final int amount;

    @Override
    public boolean act(float delta) {
        Player.getInstance().addResource(wood, amount);
        return true;
    }
}
