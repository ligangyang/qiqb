package net.qiqb.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainEventDao extends JpaRepository<DomainEventDO, String> {
}
