package ch.herrderb;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ch.herrderb.actions.CollidableMoveAction;
import ch.herrderb.actions.EmitParticles;
import ch.herrderb.actions.RepeateWhilePressed;
import ch.herrderb.actions.TriggerItem;
import ch.herrderb.actors.ItemSlot;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MyActions {

    public static Action foreverWhileButtonPressed(int button, Action action) {
        return new RepeateWhilePressed(button, action, InputType.BUTTON);
    }

    public static Action foreverWhileKeyPressed(int keyCode, Action action) {
        return new RepeateWhilePressed(keyCode, action, InputType.KEY);
    }

    public static Action moveBy(float x, float y, float duration) {
        return new CollidableMoveAction(Actions.moveBy(x, y, duration));
    }

    public static Action triggerPlayerItem(ItemSlot rightHand) {
        return new TriggerItem(rightHand);
    }

    public static Action emitParticles(float x, float y, String string) {
        return new EmitParticles(x, y, string);
    }

    public static Action switchToItemSlot(int i) {
        return new ActivateItemSlot(i);
    }

    public static Action shake() {
        return new ShakeAction();
    }

}