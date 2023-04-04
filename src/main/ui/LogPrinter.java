package ui;

/**
 * Defines behaviours that event log printers must support.
 * Adapted from <a href="https://github.students.cs.ubc.ca/CPSC210/AlarmSystem">...</a>
 */
public interface LogPrinter {
    /**
     * Prints current log
     */
    void printLog();
}