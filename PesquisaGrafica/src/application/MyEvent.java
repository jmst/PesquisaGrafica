package application;

import javafx.event.Event;
import javafx.event.EventType;

//The Event
class MyEvent extends Event {
 
 public static final EventType<MyEvent> CICLO_TERMINADO = new EventType(ANY, "CICLO_TERMINADO");
 
 public MyEvent() {
     this( CICLO_TERMINADO);
 }
 public MyEvent(EventType<? extends Event> arg0) {
     super(arg0);
 }

}