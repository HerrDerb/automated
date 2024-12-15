package ch.herrderb.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ch.herrderb.IntersectionHelper;
import ch.herrderb.MyActions;
import ch.herrderb.abilities.Collidable;

public class Container extends Box2dActor implements Collidable {
    private int hitpoints = 2;
    private static Texture texture;
    static {
        Pixmap pixmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BROWN);
        pixmap.fill();
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    public Container() {
        super(5, 5);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public boolean intersects(Actor actor) {
        boolean intersects = IntersectionHelper.intersects(this, actor);
        if (intersects) {
            if (actor instanceof Weapon) {
                hitpoints -= 1;
                if (hitpoints <= 0) {
                    addAction(Actions.sequence(MyActions.emitParticles(getX(), getY(), "coin_particles.p"),
                            Actions.removeActor()));
                }
                actor.addAction(Actions.removeActor());
            }
        }
        return intersects;
    }
}
