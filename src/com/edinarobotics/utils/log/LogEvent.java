package com.edinarobotics.utils.log;

/**
 * Represents an event being logged by the logging system.
 * 
 * This class tracks all aspects of a loggable event such as its severity
 * level, the message, the original logger to which it was submitted,
 * and an optional Throwable associated with the event.
 */
public final class LogEvent {
    private Level level;
    private String message;
    private Throwable thrown;
    private String originalLoggerName;
    
    /**
     * Constructs a new LogEvent with the given severity, and message that
     * was originally submitted to the given logger.
     * @param level The severity level of this LogEvent.
     * @param message The message associated with this LogEvent.
     * @param originalLoggerName The name of the logger to which this LogEvent
     * was originally submitted.
     */
    protected LogEvent(Level level, String message, String originalLoggerName){
        this(level, message, null, originalLoggerName);
    }
    
    /**
     * Constructs a new LogEvent with the given severity, message 
     * @param level The severity level of this LogEvent.
     * @param message The message associated with this LogEvent.
     * @param thrown The optional Throwable associated with this LogEvent.
     * @param originalLoggerName The name of the logger to which this LogEvent
     * was originally submitted.
     */
    protected LogEvent(Level level, String message, Throwable thrown, String originalLoggerName){
        if(level == null){
            throw new IllegalArgumentException("Provided log Level must not be null");
        }
        if(message == null){
            throw new IllegalArgumentException("Provided String message must not be null");
        }
        if(originalLoggerName == null){
            throw new IllegalArgumentException("Provided original logger name must not be null");
        }
        this.level = level;
        this.message = message;
        this.thrown = thrown;
        this.originalLoggerName = originalLoggerName;
    }
    
    /**
     * Indicates whether this LogEvent has an associated Throwable.
     * 
     * If this method returns {@code true}, {@link #getThrowable()} will
     * return {@code null}.
     * @return {@code true} if this LogEvent has an associated Throwable,
     * {@code false} otherwise.
     */
    public boolean hasThrowable(){
        return thrown != null;
    }
    
    /**
     * Returns the severity level of this LogEvent.
     * @return The Level representing the severity of this LogEvent.
     */
    public Level getLevel(){
        return level;
    }
    
    /**
     * Returns the message associated with this LogEvent.
     * @return The String message associated with this LogEvent.
     */
    public String getMessage(){
        return message;
    }
    
    /**
     * Returns the optional Throwable associated with this LogEvent, it it
     * exists.
     * @return Returns the Throwable instance associated with this LogEvent
     * if it exists, {@code null} otherwise.
     * @see #hasThrowable()
     */
    public Throwable getThrowable(){
        return thrown;
    }
    
    /**
     * Returns the name of the logger to which this LogEvent was originally
     * submitted.
     * 
     * The name returned by this method is the full-qualified name
     * of the Logger to which this LogEvent was submitted, including
     * the name of the implicit root Logger.
     * @return The fully-qualified String name of the logger to which this
     * LogEvent was submitted.
     */
    public String getOriginalLoggerName(){
        return originalLoggerName;
    }
    
    /**
     * Computes a hash code value for this LogEvent as required by
     * {@link Object#hashCode()}.
     * @return An {@code int} hash code value for this LogEvent.
     */
    public int hashCode(){
        int hash = 5;
        hash = 31*hash + level.hashCode();
        hash = 31*hash + message.hashCode();
        hash = 31*hash + (thrown == null ? 0 : thrown.hashCode());
        hash = 31*hash + originalLoggerName.hashCode();
        return hash;
    }
    
    /**
     * Determines whether some object is equal to this LogEvent.
     * 
     * Another object is equal to this LogEvent if it is also a LogEvent
     * instance and if its {@link #getLevel()}, {@link #getMessage()},
     * {@link #getOriginalLoggerName()}, and {@link #getThrowable()} methods
     * return equal values.
     * @param other The object to be tested for equality against this
     * LogEvent.
     * @return {@code true} if the objects are equal as defined above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof LogEvent){
            LogEvent otherEvent = (LogEvent)other;
            return otherEvent.getLevel().equals(getLevel()) &&
                    otherEvent.getMessage().equals(getMessage()) &&
                    otherEvent.getOriginalLoggerName().equals(getOriginalLoggerName()) &&
                    (otherEvent.getThrowable() != null ? otherEvent.getThrowable().equals(getThrowable()) : getThrowable() == null);
        }
        return false;
    }
    
    /**
     * Returns a String representation of this LogEvent.
     * 
     * The String representation can span either one or two lines depending on
     * whether or not this LogEvent has an associated Throwable.
     * The String has the form:
     * [-level name-] -original logger name-: -message-
     *     -Throwable-
     * If no Throwable is associated with this LogEvent, the second line is
     * omitted.
     * @return A String representation of this LogEvent.
     */
    public String toString() {
        String stringRepresentation = "["+getLevel()+"] "+getOriginalLoggerName()+": \""+message+"\"";
        if(hasThrowable()){
            stringRepresentation += "\n    "+getThrowable().toString();
        }
        return stringRepresentation;
    }
}
