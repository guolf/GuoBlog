package cn.guolf.guoblog.lib.database.sqlite;

import java.util.List;

import cn.guolf.guoblog.lib.database.exception.DbException;
import cn.guolf.guoblog.lib.database.table.ColumnUtils;
import cn.guolf.guoblog.lib.database.table.Finder;
import cn.guolf.guoblog.lib.database.table.Table;

/**
 * Author: wyouflf
 * Date: 13-9-10
 * Time: 下午10:50
 */
public class FinderLazyLoader<T> {
    private final Finder finderColumn;
    private final Object finderValue;

    public FinderLazyLoader(Finder finderColumn, Object value) {
        this.finderColumn = finderColumn;
        this.finderValue = ColumnUtils.convert2DbColumnValueIfNeeded(value);
    }

    public List<T> getAllFromDb() throws DbException {
        List<T> entities = null;
        Table table = finderColumn.getTable();
        if (table != null) {
            entities = table.db.findAll(
                    Selector.from(finderColumn.getTargetEntityType()).
                            where(finderColumn.getTargetColumnName(), "=", finderValue)
            );
        }
        return entities;
    }

    public T getFirstFromDb() throws DbException {
        T entity = null;
        Table table = finderColumn.getTable();
        if (table != null) {
            entity = table.db.findFirst(
                    Selector.from(finderColumn.getTargetEntityType()).
                            where(finderColumn.getTargetColumnName(), "=", finderValue)
            );
        }
        return entity;
    }
}
