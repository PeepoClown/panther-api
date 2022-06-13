package ru.pantherapi.model.contract.element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
@JsonTypeInfo(use = NAME, property = "elementType")
@JsonSubTypes({
        @JsonSubTypes.Type(ApiContractField.class),
        @JsonSubTypes.Type(ApiContractMethod.class)
})
public abstract class ApiContractElement
        implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 5680150001122480759L;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
