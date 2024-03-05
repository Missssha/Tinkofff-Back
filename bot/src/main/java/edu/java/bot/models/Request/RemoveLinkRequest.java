package edu.java.bot.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * RemoveLinkRequest
 */
@Setter
@Validated
@Getter
public class RemoveLinkRequest {
    @JsonProperty("link")
    private URI link = null;

    public RemoveLinkRequest link(URI link) {
        this.link = link;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemoveLinkRequest removeLinkRequest = (RemoveLinkRequest) o;
        return Objects.equals(this.link, removeLinkRequest.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RemoveLinkRequest {\n");

        sb.append("    link: ").append("\n");
        sb.append("}");
        return sb.toString();
    }
}
