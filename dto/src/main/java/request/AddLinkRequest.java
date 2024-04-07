package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.Objects;

/**
 * AddLinkRequest
 */
public class AddLinkRequest   {
    @JsonProperty("link")
    private URI link = null;

    public AddLinkRequest link(URI link) {
        this.link = link;
        return this;
    }

    /**
     * Get link
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
