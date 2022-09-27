package me.keithmo.barrage.client.controller;

import lombok.AllArgsConstructor;
import me.keithmo.barrage.client.model.request.DubboRequest;
import me.keithmo.barrage.client.service.DubboGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author keithmo
 */
@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/rpc")
public class HttpToRpcController {
    private DubboGenericService dubboGenericService;

    @PostMapping("/dubbo")
    public Object toDubbo(@Valid @RequestBody DubboRequest request) {
        return dubboGenericService.invoke(request);
    }
}
