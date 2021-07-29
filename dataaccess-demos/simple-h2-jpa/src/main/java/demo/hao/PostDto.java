package demo.hao;

import java.time.Instant;

class PostDto {
    private final Post post;

    public PostDto(Post post) {
        this.post = post;
    }

    public Long getId() {
        return post.getId();
    }

    public String getName() {
        return post.getName();
    }

    public String getCategory() {
        return post.getCategory();
    }

    public String getDescription() {
        return getCategory() + ":" + getName();
    }

    public Instant getCreatedDate() {
        return post.getCreatedDate();
    }

    public Instant getLastModifiedDate() {
        return post.getLastModifiedDate();
    }
}

