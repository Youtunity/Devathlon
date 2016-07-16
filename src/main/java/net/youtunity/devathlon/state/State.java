package net.youtunity.devathlon.state;

/**
 * Created by thecrealm on 16.07.16.
 */
public abstract class State {

    abstract void onEnter();

    abstract void onQuit();
}
