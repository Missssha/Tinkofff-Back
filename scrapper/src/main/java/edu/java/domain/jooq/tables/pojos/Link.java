package edu.java.domain.jooq.tables.pojos;

import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private OffsetDateTime lastCheckTime;
    private OffsetDateTime createdAt;

    public Link() {
    }

    public Link(Link value) {
        this.id = value.id;
        this.url = value.url;
        this.lastCheckTime = value.lastCheckTime;
        this.createdAt = value.createdAt;
    }

    @ConstructorProperties({"id", "url", "lastCheckTime", "createdAt"})
    public Link(
        @NotNull Long id,
        @NotNull String url,
        @NotNull OffsetDateTime lastCheckTime,
        @NotNull OffsetDateTime createdAt
    ) {
        this.id = id;
        this.url = url;
        this.lastCheckTime = lastCheckTime;
        this.createdAt = createdAt;
    }

    /**
     * Getter for <code>LINK.ID</code>.
     */
    @NotNull
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINK.ID</code>.
     */
    public void setId(@NotNull Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINK.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 128)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINK.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINK.LAST_CHECK_TIME</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getLastCheckTime() {
        return this.lastCheckTime;
    }

    /**
     * Setter for <code>LINK.LAST_CHECK_TIME</code>.
     */
    public void setLastCheckTime(@NotNull OffsetDateTime lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    /**
     * Getter for <code>LINK.CREATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Setter for <code>LINK.CREATED_AT</code>.
     */
    public void setCreatedAt(@NotNull OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!this.url.equals(other.url)) {
            return false;
        }
        if (this.lastCheckTime == null) {
            if (other.lastCheckTime != null) {
                return false;
            }
        } else if (!this.lastCheckTime.equals(other.lastCheckTime)) {
            return false;
        }
        if (this.createdAt == null) {
            if (other.createdAt != null) {
                return false;
            }
        } else if (!this.createdAt.equals(other.createdAt)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.lastCheckTime == null) ? 0 : this.lastCheckTime.hashCode());
        result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Link (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(lastCheckTime);
        sb.append(", ").append(createdAt);

        sb.append(")");
        return sb.toString();
    }
}
