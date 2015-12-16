package sample;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by Nick on 12/16/2015.
 */
public class FloatingCardEvent extends Event {

    public FloatingCardEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }


    public static final EventType<FloatingCardEvent> CLEAR =
            new EventType<FloatingCardEvent> (Event.ANY, "CARD_CLEAR");

    public static final EventType<FloatingCardEvent> ERROR =
            new EventType<FloatingCardEvent> (Event.ANY, "CARD_ERROR");

    public static final EventType<FloatingCardEvent> ACCEPT =
            new EventType<FloatingCardEvent> (Event.ANY, "CARD_ACCEPT");
}
