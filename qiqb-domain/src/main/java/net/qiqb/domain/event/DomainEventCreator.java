package net.qiqb.domain.event;

import lombok.Getter;

import java.io.Serializable;

public class DomainEventCreator implements Serializable {

    @Getter
    private String id;

    @Getter
    private String name;


    public DomainEventCreator(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DomainEventCreator nameless(){
        return new DomainEventCreator("nameless","佚名");
    }
}
