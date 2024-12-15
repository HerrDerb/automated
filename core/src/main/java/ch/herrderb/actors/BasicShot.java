package ch.herrderb.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.herrderb.abilities.Angled;
import ch.herrderb.abilities.Collidable;

public class BasicShot extends Actor implements Angled, Weapon {
    private static Texture texture;
    double angleDegrees;

    static {
        Pixmap pixmap = new Pixmap(2, 6, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.YELLOW);
        pixmap.fill();
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    public BasicShot() {
        setWidth(2);
        setHeight(6);
        setVisible(false);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), 1, 3, 2, 6, 1, 1, (float) angleDegrees, 0, 0, 2, 6, false, false);
    }

    @Override
    public void act(float delta) {
        setVisible(true);
        super.act(delta);
        if (getStage() == null) {
            return;
        }
        getStage().getActors().forEach(actor -> {
            if (actor instanceof Collidable collidable) {
                if (collidable.intersects(this)) {
                    remove();
                }
            }
        });
    }

    @Override
    public void setAngle(double angleDegrees) {
        angleDegrees += 90;
        this.angleDegrees = angleDegrees;
    }

    @Override
    public double getAngle() {
        return angleDegrees;
    }

}
