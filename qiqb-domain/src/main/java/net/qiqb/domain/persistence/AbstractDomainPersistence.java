package net.qiqb.domain.persistence;

import net.qiqb.domain.utils.AggregateRootUtils;
import net.qiqb.execution.example.DeleteDomainHolder;
import net.qiqb.execution.executor.config.DomainPersistence;
import net.qiqb.execution.executor.config.PersistenceWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 抽象的领域持久化对象
 *
 * @param <AR>
 */
@Slf4j
public abstract class AbstractDomainPersistence<AR> implements DomainPersistence<AR> {
    /**
     * 不管聚合根是否跟新，都执行新增操作。
     * 适用于当前聚合根需要更新实时时间（更新人）或者版本号
     */
    private boolean updateWhenNonChange = false;

    @Override
    public void persisted(PersistenceWrapper<AR> ar) {
        AR aggregateRoot = ar.get();
        final Object id = getId(aggregateRoot);
        if (DeleteDomainHolder.isDelete(aggregateRoot)) {
            try {
                doRemove(ar);
            } finally {
                DeleteDomainHolder.deleted(aggregateRoot);
            }
            return;
        }
        if (PersistenceHelper.nonCommit(id)) {
            try {
                doAdd(ar);
            } finally {
                PersistenceHelper.commit(id);
            }
            return;
        }
        //
        if (updateWhenNonChange) {
            doModify(ar);
        } else {
            // 判断是否有改变
            if (ar.hasChange()) {
                doModify(ar);
            }
        }
    }

    protected Object getId(AR ar) {
        // 获取领域对象的id
        final Optional<Object> id = AggregateRootUtils.lookupAggregateRootId(ar);
        if (id.isPresent()) {
            return id.get();
        }
        log.error("找不到聚合根ID:{}", ar.getClass());
        throw new IllegalStateException("找不到聚合根ID");
    }

    protected abstract void doAdd(PersistenceWrapper<AR> ar);

    protected abstract void doModify(PersistenceWrapper<AR> ar);

    protected abstract void doRemove(PersistenceWrapper<AR> ar);

    protected boolean isUpdateWhenNonChange() {
        return updateWhenNonChange;
    }

    public void setUpdateWhenNonChange(boolean updateWhenNonChange) {
        this.updateWhenNonChange = updateWhenNonChange;
    }
}
