package ch.herrderb.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import ch.herrderb.actors.Tree;

public class Axe extends BasicItem {
    private static final int CHOP_RADIUS = 30;
    private final Circle actionCircle = new Circle(0, 0, CHOP_RADIUS);
    private boolean displayChopRadius = false;
    private static Texture texture;

    static {
        Pixmap pixmap = new Pixmap(CHOP_RADIUS * 2, CHOP_RADIUS * 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.SALMON);
        pixmap.fillCircle(CHOP_RADIUS, CHOP_RADIUS, CHOP_RADIUS);
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void trigger() {
        Gdx.app.log("Axe", "chop");
        displayChopRadius = true;
        // center the action circle on the player
        Vector2 playerCenterVecor = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        actionCircle.setPosition(playerCenterVecor);
        getStage().getActors().forEach(actor -> {
            if (actor instanceof Tree) {
                var tree = (Tree) actor;
                var treeCenterVector = new Vector2(tree.getX() + tree.getWidth() / 2,
                        tree.getY() + tree.getHeight() / 2);
                if (actionCircle.contains(treeCenterVector)) {
                    tree.chop();
                }
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (displayChopRadius) {
            batch.draw(texture, actionCircle.x - actionCircle.radius, actionCircle.y - actionCircle.radius,
                    actionCircle.radius * 2, actionCircle.radius * 2);
            displayChopRadius = false;
        }
    }

}
