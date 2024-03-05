package edu.java.bot.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import java.util.Objects;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * AddLinkRequest
 */
@Setter @Validated

public class AddLinkRequest {
    @JsonProperty("link")
    private URI link = null;

    public AddLinkRequest link(URI link) {
        this.link = link;
        return this;
    }

    /**
     * Get link
     *
     * @return link
     **/
    @Schema(description = "")
    public URI getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddLinkRequest addLinkRequest = (AddLinkRequest) o;
        return Objects.equals(this.link, addLinkRequest.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AddLinkRequest {\n");

        sb.append("    link: ").append("\n");
        sb.append("}");
        return sb.toString();
    }
}