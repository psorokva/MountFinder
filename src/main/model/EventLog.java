package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of MountainFinder system events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 * Adapted from <a href="https://github.students.cs.ubc.ca/CPSC210/AlarmSystem">...</a>
 */
public class EventLog implements Iterable<Event> {
    /**
     * the only EventLog in the system (Singleton Design Pattern)
     */
    private static EventLog theLog;
    private final Collection<Event> events;

    /**
     * EFFECTS: constructs an EventLog, prevents external construction.
     * (Singleton Design Pattern).
     */
    // EFFECTS: constructs an EventLog
    private EventLog() {
        events = new ArrayList<>();
    }

    /**
     * MODIFIES: this
     * EFFECTS: Gets instance of EventLog - creates it if it doesn't already exist.
     * (Singleton Design Pattern)
     *
     * @return instance of EventLog
     */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    /**
     * MODIFIES: this
     * EFFECTS: Adds an event to the event log.
     *
     * @param e the event to be added
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    /**
     * MODIFIES: this
     * EFFECTS: Clears the event log and logs the event.
     */
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    /**
     * EFFECTS: Creates iterator for event
     */
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}