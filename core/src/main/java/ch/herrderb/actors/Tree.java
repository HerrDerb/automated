package ch.herrderb.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.herrderb.IntersectionHelper;
import ch.herrderb.MyActions;
import ch.herrderb.abilities.Collidable;

public class Tree extends Box2dActor implements Collidable, Choppable {
    private int hitpoints = 3;

    private static Texture texture;

    static {
        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fillCircle(10, 10, 10);
        pixmap.setColor(Color.OLIVE);
        pixmap.drawCircle(10, 10, 7);
        pixmap.drawCircle(10, 10, 3);
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    public Tree() {
        super(20, 20);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public boolean intersects(Actor actor) {
        return IntersectionHelper.intersects(this, actor);
    }

    @Override
    public void chop() {
        Gdx.app.log("Tree", "chop");
        hitpoints--;
        if (hitpoints <= 0) {
            remove();
        } else {
            // lets shake the tree
            addAction(MyActions.shake());
        }
    }

}
