package ch.herrderb.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.herrderb.abilities.Collidable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Wall extends Box2dActor implements Collidable {
    private final Texture texture;

    public Wall(int width, int height) {
        super(width, height);
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public boolean intersects(Actor otherActor) {
        var actorX = otherActor.getX();
        var actorY = otherActor.getY();
        var actorWidth = otherActor.getWidth();
        var actorHeight = otherActor.getHeight();
        var otherActorHitbox = new Rectangle(actorX, actorY, actorWidth, actorHeight);
        var actorHitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
        var collides = otherActorHitbox.overlaps(actorHitbox);
        return collides;
    }

}
