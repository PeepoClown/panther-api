package ru.pantherapi.model.artefact.verify;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.pantherapi.model.artefact.ApiArtefact;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class VerifyArtefactsRq
        implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -8392973009044644448L;

    @Valid
    @NotNull
    @JsonProperty("artefacts")
    private List<ApiArtefact> artefacts;
}
