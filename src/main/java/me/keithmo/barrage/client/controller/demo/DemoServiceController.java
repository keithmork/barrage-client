package me.keithmo.barrage.client.controller.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author keithmo
 */
@RestController
@RequestMapping("/rpc/demo")
@RequiredArgsConstructor
public class DemoServiceController {
    @GetMapping("/say-hello")
    public String sayHello(String name) {
        return "";
    }

    @PostMapping("/get-time")
    public Long getTime(@RequestBody @Valid Object request) {
        return 1L;
    }
}
