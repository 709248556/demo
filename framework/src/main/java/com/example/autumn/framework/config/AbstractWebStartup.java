package com.example.autumn.framework.config;

import com.autumn.mybatis.mapper.PropertyMapperUtils;
import com.autumn.web.AbstractAutumnApplication;
import com.autumn.zero.authorization.configure.AuthorizationEntityCustomKeyGenerationTypeMapperConfigure;
import com.autumn.zero.common.library.configure.CommonLibraryEntityCustomKeyGenerationTypeMapperConfigure;
import com.autumn.zero.file.storage.configure.StorageEntityCustomKeyGenerationTypeMapperConfigure;

import javax.persistence.GenerationType;
import java.util.Locale;

/**
 * 启动抽象
 * <p>
 * </p>
 *
 * @description
 * @author: yanlianglong
 * @create: 2020/6/9 10:07
 **/
public abstract class AbstractWebStartup extends AbstractAutumnApplication {

    static {
        //默认采用中文
        Locale.setDefault(Locale.CHINA);

        //初始化引用库对应的表采用非自动id(默认都是自动id)，采由注解 EnableAutumnCloudUidGenerator 生成的分布式自动id
        PropertyMapperUtils.registerEntityKeyGenerationType(new AuthorizationEntityCustomKeyGenerationTypeMapperConfigure(GenerationType.TABLE)
                .bindGenerationTypeMapper());
        PropertyMapperUtils.registerEntityKeyGenerationType(new StorageEntityCustomKeyGenerationTypeMapperConfigure(GenerationType.TABLE)
                .bindGenerationTypeMapper());
        PropertyMapperUtils.registerEntityKeyGenerationType(new CommonLibraryEntityCustomKeyGenerationTypeMapperConfigure(GenerationType.TABLE)
                .bindGenerationTypeMapper());
    }

    /**
     * 将所有 Long 类型自动转换为 String 类型，因大数的Long值在web端时，js读取为number时造成精度损失，客户端提交的 String 类型在服务器会自动转换为 long
     *
     * @return
     */
    @Override
    protected boolean isJsonCastLongToString() {
        return true;
    }

}
