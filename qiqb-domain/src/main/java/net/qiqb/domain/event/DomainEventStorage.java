package net.qiqb.domain.event;

import java.util.Collection;

public interface DomainEventStorage {
    /**
     * 保存领域业务处理的过程中产生的事件
     *
     * @param events
     * @return
     */
    boolean save(Collection<DomainEvent> events);

}
