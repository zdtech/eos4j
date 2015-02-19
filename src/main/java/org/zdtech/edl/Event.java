package org.zdtech.edl;

/**
 * @author Schekalev
 * @since 17.02.15
 */
public class Event {
    private int id;
    private String name;
    private int priority;
    private String expression;

    public Event(int id, String name, int priority, String expression) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.expression = expression;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public String getExpression() {
        return expression;
    }
}
