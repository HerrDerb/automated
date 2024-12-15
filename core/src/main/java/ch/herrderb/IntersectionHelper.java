package ch.herrderb;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class IntersectionHelper {

    public static boolean intersects(Actor actor, Actor otherActor) {
        var actorX = actor.getX();
        var actorY = actor.getY();
        var actorWidth = actor.getWidth();
        var actorHeight = actor.getHeight();
        var otherActorHitbox = new Rectangle(actorX, actorY, actorWidth, actorHeight);
        var actorHitbox = new Rectangle(otherActor.getX(), otherActor.getY(), otherActor.getWidth(),
                otherActor.getHeight());
        var collides = otherActorHitbox.overlaps(actorHitbox);
        return collides;
    }
}
