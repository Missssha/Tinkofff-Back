package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.Objects;

/**
 * RemoveLinkRequest
 */

public class RemoveLinkRequest {
    @JsonProperty("link")
    private URI link = null;

    public RemoveLinkRequest link(URI link) {
        this.link = link;
        return this;
    }

    /**
     * Get link
     *
     * @return link
     **/

    public URI getLink() {
        return link;
    }

    public void setLink(URI link) {
        this.link = link;
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
