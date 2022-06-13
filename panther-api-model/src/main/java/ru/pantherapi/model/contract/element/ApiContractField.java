package ru.pantherapi.model.contract.element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("FIELD")
public class ApiContractField
        extends ApiContractElement {
    @JsonIgnore
    private static final long serialVersionUID = 1864794315350219061L;

    @NotBlank
    @JsonProperty("dataType")
    private String dataType;
}
