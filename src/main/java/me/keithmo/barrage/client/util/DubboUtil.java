package me.keithmo.barrage.client.util;

import lombok.experimental.UtilityClass;
import me.keithmo.barrage.client.constants.CommonConstants;
import me.keithmo.barrage.client.model.request.DubboRequest;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @author keithmo
 */
@UtilityClass
public class DubboUtil {
    public static GenericService getGenericReference(DubboRequest request) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setGeneric("true");
        reference.setReconnect("false");
        reference.setCheck(false);
        reference.setUrl(request.getProtocol() + request.getUrl());
        reference.setInterface(request.getInterfaceClass());
        reference.setVersion(request.getVersion());
        reference.setGroup(request.getGroup());
        reference.setTimeout(request.getTimeOut());
        reference.setAsync(request.getAsync());
        reference.setConnections(request.getConnections());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig(CommonConstants.APPLICATION_NAME))
                .reference(reference)
                .start();

        ReferenceConfigCache cache = ReferenceConfigCache.getCache("default", KEY_GENERATOR);
        GenericService genericService = cache.get(reference);
        if (genericService == null) {
            cache.destroy(reference);
            throw new IllegalStateException("Service Unavailable");
        }

        return genericService;
    }

    /**
     * 默认 key 为 group/interface:version，如果泛化调用第一次请求错了，会一直缓存错的 ReferenceConfig
     * 因此加上额外的字段
     */
    public static final ReferenceConfigCache.KeyGenerator KEY_GENERATOR = referenceConfig -> {
        // Dubbo default
        String iName = referenceConfig.getInterface();
        if (StringUtils.isBlank(iName)) {
            Class<?> clazz = referenceConfig.getInterfaceClass();
            iName = clazz.getName();
        }
        if (StringUtils.isBlank(iName)) {
            throw new IllegalArgumentException("No interface info in ReferenceConfig" + referenceConfig);
        }

        StringBuilder ret = new StringBuilder();
        if (!StringUtils.isBlank(referenceConfig.getGroup())) {
            ret.append(referenceConfig.getGroup()).append("/");
        }
        ret.append(iName);
        if (!StringUtils.isBlank(referenceConfig.getVersion())) {
            ret.append(":").append(referenceConfig.getVersion());
        }

        // New
        RegistryConfig registry = referenceConfig.getRegistry();
        if (registry != null) {
            ret.append(":").append(registry.getAddress());
        }
        if (!StringUtils.isBlank(referenceConfig.getUrl())) {
            ret.append(":").append(referenceConfig.getUrl());
        }
        if (referenceConfig.getTimeout() != null) {
            ret.append(":").append(referenceConfig.getTimeout());
        }

        return ret.toString();
    };
}
