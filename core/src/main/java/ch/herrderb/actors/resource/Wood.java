package ch.herrderb.actors.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ch.herrderb.MyActions;
import ch.herrderb.actors.Player;

public class Wood extends Actor {
    private static Texture texture;

    static {
        Pixmap pixmap = new Pixmap(12, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BROWN);
        pixmap.fillRectangle(0, 0, 10, 3);
        pixmap.fillRectangle(2, 3, 10, 4);
        pixmap.fillRectangle(0, 7, 10, 3);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.drawLine(0, 0, 9, 0);
        pixmap.drawLine(0, 3, 9, 3);
        pixmap.drawLine(0, 6, 9, 6);
        pixmap.drawLine(0, 9, 9, 9);
        pixmap.drawLine(0, 0, 0, 9);
        pixmap.drawLine(9, 0, 9, 9);
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    private boolean collected;

    public Wood() {
        setBounds(0, 0, 12, 10);
    }

    @Override
    public void act(float delta) {
        if (!collected) {
            var player = Player.getInstance();
            var collectRange = player.getAutoCollectRange();
            var distanceToPlayer = Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));
            if (distanceToPlayer < collectRange) {
                Gdx.app.log("Wood", "collected");
                collected = true;
                this.addAction(Actions.sequence(Actions.moveTo(player.getX(),
                        player.getY(), 0.1f), MyActions.addResource(ResourceType.WOOD, 5), Actions.removeActor()));
            }
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}
