package net.qiqb.domain.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 领域事件容器
 */
public class DomainEventContainer {

    private List<DomainEvent> events = new ArrayList<>();

    public void add(Object... events) {
        if (events != null) {
            for (Object e : events) {
                this.events.add(new DomainEvent("", DomainEventCreator.nameless(), e));
            }
        }
    }

    public void clear() {
        this.events.clear();
    }

    public List<DomainEvent> getEvents() {
        return this.events;
    }
}
