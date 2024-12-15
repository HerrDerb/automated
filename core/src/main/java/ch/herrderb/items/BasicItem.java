package ch.herrderb.items;

import ch.herrderb.actors.Box2dActor;

public abstract class BasicItem extends Box2dActor {

    public BasicItem() {
        super();
    }

    public abstract void trigger();

    public void deactivate() {

    }

    public void activate() {

    }

}
