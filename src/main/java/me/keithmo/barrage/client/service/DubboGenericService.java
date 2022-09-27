package me.keithmo.barrage.client.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.keithmo.barrage.client.constants.CommonConstants;
import me.keithmo.barrage.client.model.request.DubboRequest;
import me.keithmo.barrage.client.model.request.DubboRequestParam;
import me.keithmo.barrage.client.util.DubboUtil;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keithmo
 */
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class DubboGenericService {
    public Object invoke(DubboRequest request) {
        GenericService service = DubboUtil.getGenericReference(request);

        DubboRequestParam[] params = request.getParameters();
        String method = request.getMethod();
        if (params == null || params.length <= 0) {
            return service.$invoke(method, CommonConstants.EMPTY_STRING_ARRAY, CommonConstants.EMPTY_OBJECT_ARRAY);
        }

        List<String> paramTypes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (DubboRequestParam param : params) {
            paramTypes.add(param.getParameterType());
            values.add(param.getArg());
        }

        return service.$invoke(method,
                paramTypes.toArray(CommonConstants.EMPTY_STRING_ARRAY),
                values.toArray());
    }
}
