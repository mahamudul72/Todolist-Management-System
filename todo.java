/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todolist;

import java.time.LocalDate;

/**
 *
 * @author Asus
 */
public class todo {
    String eventName;
    LocalDate date;

    public todo(String eventName, LocalDate date) {
        this.eventName = eventName;
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "todo{" + "eventName=" + eventName + ", date=" + date + '}';
    }
    
    
}
