package application;

import javafx.event.EventHandler;

//The Handler
class MyEventHandler implements EventHandler<MyEvent> {
   
 @Override
 public void handle(MyEvent event) {
     
     if (event.getEventType().equals(MyEvent.CICLO_TERMINADO)) {
         System.out.println("MyEvent 2");
     }
 }
}
