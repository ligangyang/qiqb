package net.qiqb.dao.mybatis.plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.qiqb.auth.AuthUserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

@Slf4j
public class BasicInfoFillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class); // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "createBy", () -> AuthUserHolder.get().getCurrent().toString(), String.class); // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "lastModifyTime", LocalDateTime::now, LocalDateTime.class); // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "lastModifyBy", () -> AuthUserHolder.get().getCurrent().toString(), String.class); // 起始版本 3.3.3(推荐)

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject, "lastModifyTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "lastModifyBy", () -> AuthUserHolder.get().getCurrent().toString(), String.class); // 起始版本 3.3.3(推荐)

    }
}