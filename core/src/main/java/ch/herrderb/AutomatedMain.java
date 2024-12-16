package ch.herrderb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import box2dLight.RayHandler;
import ch.herrderb.actions.Shoot;
import ch.herrderb.actors.Container;
import ch.herrderb.actors.ItemSlot;
import ch.herrderb.actors.Player;
import ch.herrderb.actors.Tree;
import ch.herrderb.actors.Wall;
import ch.herrderb.items.Axe;
import ch.herrderb.items.Gun;
import ch.herrderb.items.Torch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class AutomatedMain extends ApplicationAdapter {
    private Stage stage;
    private Player player;
    private World world;
    private RayHandler rayHandler;

    @Override
    public void create() {
        world = new World(new Vector2(0, 0), true);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 0.8f);
        rayHandler.setCulling(false);
        stage = new Box2dStage(world, rayHandler);
        stage.setViewport(new ScreenViewport());
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (button) {
                    case Input.Buttons.LEFT:
                        player.addAction(
                                MyActions.triggerPlayerItem(ItemSlot.LEFT_HAND));
                        break;
                    case Input.Buttons.RIGHT:
                        player.addAction(MyActions.triggerPlayerItem(ItemSlot.RIGHT_HAND));
                        break;
                }
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.NUM_1:
                        player.addAction(MyActions.switchToItemSlot(0));
                        break;
                    case Input.Keys.NUM_2:
                        player.addAction(MyActions.switchToItemSlot(1));
                        break;
                    case Input.Keys.W:
                    case Input.Keys.UP:
                        player.addAction(MyActions.foreverWhileKeyPressed(keycode, MyActions.moveBy(0, 2, 0.02f)));
                        break;
                    case Input.Keys.S:
                    case Input.Keys.DOWN:
                        player.addAction(MyActions.foreverWhileKeyPressed(keycode, MyActions.moveBy(0, -2, 0.02f)));
                        break;
                    case Input.Keys.A:
                    case Input.Keys.LEFT:
                        player.addAction(MyActions.foreverWhileKeyPressed(keycode, MyActions.moveBy(-2, 0, 0.02f)));
                        break;
                    case Input.Keys.D:
                    case Input.Keys.RIGHT:
                        player.addAction(MyActions.foreverWhileKeyPressed(keycode, MyActions.moveBy(2, 0, 0.02f)));
                        break;
                }
                return true;
            }

        });
        Gdx.input.setInputProcessor(stage);

        // Add player
        player = new Player();
        stage.addActor(player);
        player.setPosition(Gdx.graphics.getWidth() / 2 - player.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - player.getHeight() / 2);
        // Add Wall
        var wall1 = new Wall(20, 100);
        stage.addActor(wall1);
        wall1.setPosition(player.getX() + player.getWidth() + 50, player.getY());
        var wall2 = new Wall(200, 20);
        stage.addActor(wall2);
        wall2.setPosition(player.getX() - 200, player.getY() + 50);

        // add Container
        var container = new Container();
        stage.addActor(container);
        container.setPosition(player.getX() + 50, player.getY() - 50);
        var container2 = new Container();
        stage.addActor(container2);
        container2.setPosition(player.getX() + 100, player.getY() - 50);
        var container3 = new Container();
        stage.addActor(container3);
        container3.setPosition(player.getX() + 150, player.getY() - 50);

        // Add Torch
        var torch = new Torch();
        stage.addActor(torch);
        player.setItemRight(torch);

        var gun = new Gun();
        stage.addActor(gun);
        player.addLeftHandITem(gun);
        var axe = new Axe();
        stage.addActor(axe);
        player.addLeftHandITem(axe);

        // Add Tree
        var tree = new Tree();
        tree.setPosition(player.getX() - 200, player.getY());
        stage.addActor(tree);
        var tree2 = new Tree();
        tree2.setPosition(player.getX() - 190, player.getY() - 20);
        stage.addActor(tree2);
        var tree3 = new Tree();
        tree3.setPosition(player.getX() - 200, player.getY() - 50);
        stage.addActor(tree3);

    }

    public void resize(int width, int height) {
        // See below for what true means.
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        stage.draw();
        rayHandler.setCombinedMatrix(stage.getCamera().combined, 0, 0, 1, 1);
        rayHandler.updateAndRender();
    }

    @Override
    public void dispose() {
        stage.dispose();
        rayHandler.dispose();
    }
}
