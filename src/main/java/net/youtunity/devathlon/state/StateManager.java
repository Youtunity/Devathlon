package net.youtunity.devathlon.state;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by thecrealm on 30.07.16.
 */
public class StateManager {

    private List<State> registeredStates = new LinkedList<>();
    private ListIterator<State> stateIterator;
    private State current;

    public void prepareAndStart() {
        this.stateIterator = registeredStates.listIterator();
        this.current = stateIterator.next();
        this.current.onEnter();
    }

    public void registerState(State state) {
        if(stateIterator != null) {
            throw new IllegalStateException("State already started!");
        }

        registeredStates.add(state);
    }

    public State getCurrent() {
        return current;
    }

    public void doNextState() {

        if(stateIterator == null) {
            throw new IllegalStateException("States are not initialized, cannot go to next state");
        }

        if(stateIterator.hasNext()) {
            State next = stateIterator.next();
            this.current.onLeave();
            this.current = next;
            this.current.onEnter();
        } else {
            //TODO: Game ends
        }
    }
}
