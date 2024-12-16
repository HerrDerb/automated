package ch.herrderb.actors;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ch.herrderb.actors.resource.ResourceType;
import ch.herrderb.items.BasicItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Player extends Actor {
    @Getter
    private static Player instance;
    @Getter
    private int autoCollectRange = 40;
    private static final Texture texture;
    private BasicItem itemLeft;
    private BasicItem itemRight;
    private LinkedList<BasicItem> items = new LinkedList<>();
    private Map<ResourceType, Integer> resources = new HashMap<>();

    static {
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    public Player() {
        setBounds(0, 0, 10, 10);
        instance = this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    protected void positionChanged() {
        items.forEach(item -> item.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2));
        if (itemRight != null) {
            itemRight.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
        }
    }

    public void addLeftHandITem(BasicItem item) {
        items.add(item);
        this.itemLeft = item;
        item.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public void setItemRight(BasicItem item) {
        this.itemRight = item;
        item.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    @Override
    public boolean remove() {
        var removed = super.remove();
        if (itemLeft != null) {
            itemLeft.remove();
        }
        return removed;

    }

    public void triggerItem(ItemSlot slot) {
        if (slot == ItemSlot.LEFT_HAND && itemLeft != null) {
            itemLeft.trigger();
        } else if (slot == ItemSlot.RIGHT_HAND && itemRight != null) {
            itemRight.trigger();
        }
    }

    public void activateItemSlot(int itemSlot) {
        if (itemSlot < items.size()) {
            var item = items.get(itemSlot);
            if (itemLeft != null) {
                itemLeft.deactivate();
            }
            itemLeft = item;
            if (itemLeft != null) {
                itemLeft.activate();
                Gdx.app.log("Player", "Activating itemslot " + itemSlot);
            }
        }
    }

    public void addResource(ResourceType wood, int amount) {
        resources.put(wood, resources.getOrDefault(wood, 0) + amount);
        Gdx.app.log("Player", "Added " + amount + " " + wood + " to inventory. New Total: " + resources.get(wood));
    }
}
