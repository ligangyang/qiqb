package net.qiqb.dao.sql;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 单表
 *
 * @param <T>
 */
public class SingleTableChanges<T> {

    @Getter
    private final String tableName;

    @Getter
    private final List<ChangeColumn<T>> changeValues;

    public SingleTableChanges(String tableName) {
        this.tableName = tableName;
        this.changeValues = new ArrayList<>();
    }

    public ChangeColumn<T> add() {
        final ChangeColumn<T> changeColumn = new ChangeColumn<>(this);
        changeValues.add(changeColumn);
        return changeColumn;
    }


    public boolean hasChange() {
        return changeValues.isEmpty();
    }


}
