package ch.herrderb.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import box2dLight.RayHandler;
import ch.herrderb.Box2dStage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Box2dActor extends Actor {
    @Getter
    private Body body;
    private final FixtureDef fd;

    public Box2dActor() {
        this(0, 0);
    }

    public Box2dActor(int width, int height) {
        setSize(width, height);
        var polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / 2f, height / 2f);
        fd = new FixtureDef();
        fd.density = 10;
        fd.shape = polygonShape;
    }

    public World getWorld() {
        return ((Box2dStage) getStage()).getWorld();
    }

    public RayHandler getRayHandler() {
        return ((Box2dStage) getStage()).getRayHandler();
    }

    @Override
    protected void setStage(Stage stage) {
        if (stage == null) {

            return;
        }
        if (stage instanceof Box2dStage box2dStage) {
            super.setStage(stage);
            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            body = box2dStage.getWorld().createBody(bd);
            body.setTransform(getX() + getWidth() / 2, getY() + getHeight() / 2, 0);
            body.createFixture(fd);
            initAfterStageSet();
        } else {
            throw new IllegalStateException("Box2dActor can only be used in a Box2dStage");
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (body != null) {
            body.setTransform(getX() + getWidth() / 2, getY() + getHeight() / 2, 0);
        }
    }

    @Override
    public boolean remove() {
        getWorld().destroyBody(body);
        return super.remove();
    }

    public void initAfterStageSet() {
    }

}
