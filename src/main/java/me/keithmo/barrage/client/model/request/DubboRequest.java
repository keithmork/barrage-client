package me.keithmo.barrage.client.model.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author keithmo
 */
@Data
public class DubboRequest implements Serializable {
    @NotBlank
    private String url;

    /**
     * Fully-qualified class name(FQCN)
     */
    @NotBlank
    private String interfaceClass;

    @NotBlank
    private String method;

    @Valid
    private DubboRequestParam[] parameters;

    private String group;

    private String version;

    @Min(10)
    private Integer timeOut = 10000;

    private String protocol = "dubbo://";

    private Boolean async = false;

    private Integer connections = 10;
}
