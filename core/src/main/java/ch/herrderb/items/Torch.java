package ch.herrderb.items;

import com.badlogic.gdx.graphics.Color;

import box2dLight.PointLight;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Torch extends BasicItem {

    private PointLight pl;

    @Override
    public void act(float delta) {
        if (Math.random() > 0.9) {
            float flicker = (float) (Math.random() * 0.2 + 0.9);
            pl.setDistance(400 * flicker);
        }
    }

    @Override
    public void initAfterStageSet() {
        pl = new PointLight(getRayHandler(), 512);
        pl.setStaticLight(false);
        pl.setSoft(false);
        pl.setDistance(400);
        pl.setColor(new Color(1, 0.5f, 0, .8f));
        pl.attachToBody(getBody());
    }

    @Override
    public void trigger() {
        pl.setActive(!pl.isActive());
    }
}