package com.edinarobotics.utils.log.formatters;

import com.edinarobotics.utils.log.Formatter;
import com.edinarobotics.utils.log.LogEvent;

/**
 * This class implements a Formatter that formats log events into a
 * human-readable String suitable for printing.
 */
public class PrintFormatter implements Formatter {
    
    /**
     * Creates a new PrintFormatter instance.
     */
    public PrintFormatter(){
        
    }
    
    /**
     * Formats a given LogEvent object into a format suitable for printing.
     * 
     * A possible output is:
     * {@code [SEVERE] _root_.full.logger.name: An error occurred.}
     * {@code     Exception: If there is an exception. It goes here.}
     * @param event The LogEvent object to be formatted by this Formatter.
     * @return A human-readable String representing this LogEvent, that
     * is suitable for printing.
     */
    public String format(LogEvent event){
        String formattedEvent = "["+event.getLevel().toString().toUpperCase()+"] "
                +event.getOriginalLoggerName()+": "+event.getMessage();
        if(event.hasThrowable()){
            formattedEvent += "\n    "+event.getThrowable().toString();
        }
        return formattedEvent;
    }
    
    /**
     * Returns a String representation of this PrintFormatter.
     * 
     * This String representation is intended to be human-readable.
     * @return A human-readable String representation of this PrintFormatter.
     */
    public String toString(){
        return "<PrintFormatter>";
    }
}