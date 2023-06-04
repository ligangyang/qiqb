package net.qiqb.domain.event;

import net.qiqb.domain.config.support.EntityIdGeneratorHolder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.EventObject;

/**
 * 域事件
 * 领域事件
 *
 * @author WangYun
 * @date 2023/02/20
 */
public class DomainEvent extends EventObject {

    /**
     * 事件id
     */
    @Getter
    private String id;

    /**
     * 可以通过此值来进行追踪事件的。
     */
    @Getter
    private String traceId;

    /**
     * 产生事件的时间
     */
    @Getter
    private LocalDateTime timestamp = LocalDateTime.now();

    @Getter
    private final DomainEventCreator creator;
    @Getter
    private DomainEventStatus status;
    @Getter
    private final Object content;

    public DomainEvent(Object resource, DomainEventCreator creator, Object content) {
        super(resource);
        // 默认采用雪花id
        this.id = EntityIdGeneratorHolder.getEntityIdGenerator(Long.class).generate().toString();
        this.creator = creator;
        this.status = DomainEventStatus.PRE_SEND;
        this.content = content;
    }

}
