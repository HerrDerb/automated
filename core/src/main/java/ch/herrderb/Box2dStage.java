package ch.herrderb;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import box2dLight.RayHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Box2dStage extends Stage {

    @Getter
    private final World world;

    @Getter
    private final RayHandler rayHandler;

}
