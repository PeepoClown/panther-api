package ru.pantherapi.model.contract.element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("METHOD")
public class ApiContractMethod
        extends ApiContractElement {
    @JsonIgnore
    private static final long serialVersionUID = -7993053535922648291L;

    @NotBlank
    @JsonProperty("returnDataType")
    private String returnDataType;

    @NotNull
    @JsonProperty("argsDataType")
    private List<String> argsDataType;
}
