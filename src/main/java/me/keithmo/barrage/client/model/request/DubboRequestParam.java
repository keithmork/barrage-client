package me.keithmo.barrage.client.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author keithmo
 */
@Data
public class DubboRequestParam implements Serializable {
    /**
     * Fully-qualified class name(FQCN)
     */
    @NotBlank
    private String parameterType;

    @NotNull
    private Object arg;
}
