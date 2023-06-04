package net.qiqb.domain.event;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
@Slf4j
public class DefaultDomainEventStorage implements DomainEventStorage {

    private final DomainEventDao domainEventDao;


    public DefaultDomainEventStorage(DomainEventDao domainEventDao) {
        this.domainEventDao = domainEventDao;
    }


    @Override
    public boolean save(Collection<DomainEvent> events) {
        List<DomainEventDO> eventDOList = new ArrayList<>();
        for (DomainEvent event : events) {
            DomainEventDO eventDO = DomainEventDO.builder()
                    .id(event.getId())
                    .createTime(event.getTimestamp())
                    .content(JSONUtil.toJsonStr(event.getContent()))
                    .build();
            eventDOList.add(eventDO);
        }
        final List<DomainEventDO> domainEventDOS = domainEventDao.saveAll(eventDOList);
        return true;
    }
}
