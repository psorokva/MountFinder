package model;

import java.util.Calendar;
import java.util.Date;


/**
 * Represents a MountFinder system event.
 * Adapted from <a href="https://github.students.cs.ubc.ca/CPSC210/AlarmSystem">...</a>
 */
public class Event {
    private static final int HASH_CONSTANT = 13;
    private final Date dateLogged;
    private final String description;

    /**
     * EFFECTS: creates an event with the given description
     * and the current date/time stamp.
     *
     * @param description a description of the event
     */
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /**
     * EFFECTS: Gets the date of this event (includes time).
     *
     * @return the date of the event
     */
    public Date getDate() {
        return dateLogged;
    }

    /**
     * EFFECTS: Gets the description of this event.
     *
     * @return the description of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * MODIFIES: this
     * EFFECTS: Overwrites implementation for equals methods
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                &&
                this.description.equals(otherEvent.description));
    }

    /**
     * EFFECTS: Overwrites implementation for hashCode
     */
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    /**
     * EFFECTS: Overwrites implementation for toString
     */
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}