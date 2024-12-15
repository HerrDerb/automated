package ch.herrderb.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;

public class EmitParticles extends Action {
    ParticleEffect effect = new ParticleEffect();
    private final float x;
    private final float y;
    private final ParticleEffectActor actor;


    public EmitParticles(float x, float y, String effectFile) {
        this.x = x;
        this.y = y;
        effect.load(Gdx.files.internal(effectFile), Gdx.files.internal(""));
        actor = new ParticleEffectActor(effect, false);
        actor.start();
        actor.setPosition(x, y);
    }

    @Override
    public boolean act(float delta) {
        actor.setPosition(x, y);
        getActor().getStage().addActor(actor);
        return true;
    }

}
