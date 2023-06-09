package net.qiqb.execution.executor.config;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;

public class PersistenceWrapper<D> {
    /**
     * 初始化后得到的数据
     */
    private final D initObject;

    /**
     * 业务执行后得到的领域对象
     */
    private final D completedBusinessObject;


    public PersistenceWrapper(D initObject, D completedBusinessObject) {
        this.initObject = initObject;
        this.completedBusinessObject = completedBusinessObject;
    }

    public D get() {
        return completedBusinessObject;
    }



    /**
     * 判断是否有改变
     *
     * @param function
     * @param <P>
     * @param <R>
     * @return
     */
    public <P, R> boolean hasChange(Func1<P, R> function) {
        final String fieldName = LambdaUtil.getFieldName(function);
        //final List<PropertyChange> propertyChanges = diff.getChanges().getPropertyChanges(fieldName);
        //return !propertyChanges.isEmpty();
        return false;
    }

    public boolean hasChange() {
        //return !diff.getChanges().isEmpty();
        return true;
    }
}
