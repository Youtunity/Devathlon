package net.youtunity.devathlon.state;

import com.quartercode.quarterbukkit.api.MathUtil;
import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;
import net.youtunity.devathlon.DevathlonPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thecrealm on 30.07.16.
 */
public abstract class CountedState extends State {

    protected DevathlonPlugin plugin;
    private final int end;
    private AtomicInteger counter;
    private final Direction direction;

    private ScheduleTask task;

    public CountedState(DevathlonPlugin plugin, int initialCount, int endCount, Direction direction) {
        this.plugin = plugin;
        this.counter = new AtomicInteger(initialCount);
        this.end = endCount;
        this.direction = direction;
    }

    @Override
    public void onEnter() {

        this.task = new ScheduleTask(plugin) {

            @Override
            public void run() {

                switch (direction) {
                    case UP:
                        if(getCurrentCount() >= end) {
                            cancel();
                        }
                        onCount(counter.incrementAndGet());
                    case DOWN:
                        if(getCurrentCount() <= end) {
                            cancel();
                        }
                        onCount(counter.decrementAndGet());
                }
            }
        };

        this.task.run(true, 0, MathUtil.getTicks(1000));
        enter();
    }

    @Override
    public void onLeave() {

        if(task != null) {
            task.cancel();
        }
        leave();
    }

    public int getCurrentCount() {
        return counter.get();
    }

    public int getEndCount() {
        return end;
    }

    protected abstract void enter();

    protected abstract void leave();

    protected abstract void onCount(int count);

    public static enum Direction {

        UP,
        DOWN;

    }
}
