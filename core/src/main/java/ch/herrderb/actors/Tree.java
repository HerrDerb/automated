package ch.herrderb.actors;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.herrderb.IntersectionHelper;
import ch.herrderb.MyActions;
import ch.herrderb.abilities.Collidable;
import ch.herrderb.actors.resource.Wood;

public class Tree extends Box2dActor implements Collidable, Choppable {
    private int hitpoints = 3;

    private static Texture grown;
    private static Texture chopped;
    private boolean choppedDown = false;
    private Duration reGrowDuration = Duration.ofSeconds(10);
    private Instant choppedAt = Instant.MIN;

    static {
        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.FOREST);
        pixmap.fillCircle(10, 10, 10);
        pixmap.setColor(Color.GREEN);
        pixmap.drawCircle(10, 10, 8);
        pixmap.drawCircle(10, 10, 4);
        pixmap.drawCircle(10, 10, 1);
        grown = new Texture(pixmap);
        pixmap.dispose();

        Pixmap pixmapChopped = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmapChopped.setColor(Color.BROWN);
        pixmapChopped.drawRectangle(6, 6, 8, 8);
        pixmapChopped.setColor(Color.TAN);
        pixmapChopped.drawRectangle(8, 8, 4, 4);
        chopped = new Texture(pixmapChopped);
        pixmapChopped.dispose();
    }

    public Tree() {
        super(20, 20);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (choppedDown && Duration.between(choppedAt, Instant.now()).compareTo(reGrowDuration) > 0) {
            reset();
        }
    }

    private void reset() {
        hitpoints = 3;
        choppedDown = false;
        setSize(20, 20);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(choppedDown ? chopped : grown, getX(), getY());
    }

    @Override
    public boolean intersects(Actor actor) {
        return IntersectionHelper.intersects(this, actor);
    }

    @Override
    public void chop() {
        if (choppedDown) {
            return;
        }
        Gdx.app.log("Tree", "chop");
        hitpoints--;
        if (hitpoints <= 0) {
            choppedDown = true;
            choppedAt = Instant.now();
            setSize(12, 12);
            var wood = new Wood();
            wood.setPosition(getX(), getY());
            getStage().addActor(wood);
            addAction(MyActions.replaceActor(TreeStump.class, 6, 6));
        } else {
            // lets shake the tree
            addAction(MyActions.shake());
        }
    }

}
