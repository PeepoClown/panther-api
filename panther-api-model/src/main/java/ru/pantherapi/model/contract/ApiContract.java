package ru.pantherapi.model.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.pantherapi.model.contract.element.ApiContractElement;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class ApiContract
        implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -2330797719926051251L;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("version")
    private String version;

    @JsonProperty("description")
    private String description;

    @Valid
    @NotNull
    @JsonProperty("elements")
    private List<ApiContractElement> elements;
}
