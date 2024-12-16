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

public class TreeStump extends Box2dActor implements Collidable {

    private final Instant choppedAt;
    private static Texture chopped;
    private Duration reGrowDuration = Duration.ofSeconds(10);

    static {
        Pixmap pixmapChopped = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        pixmapChopped.setColor(Color.BROWN);
        pixmapChopped.fill();
        pixmapChopped.setColor(Color.TAN);
        pixmapChopped.drawRectangle(2, 2, 4, 4);
        chopped = new Texture(pixmapChopped);
        pixmapChopped.dispose();
    }

    public TreeStump() {
        super(8, 8);
        choppedAt = Instant.now();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (Duration.between(choppedAt, Instant.now()).compareTo(reGrowDuration) > 0) {
            addAction(MyActions.replaceActor(Tree.class, -6, -6));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(chopped, getX(), getY());
    }

    @Override
    public boolean intersects(Actor actor) {
        return IntersectionHelper.intersects(this, actor);
    }

}
