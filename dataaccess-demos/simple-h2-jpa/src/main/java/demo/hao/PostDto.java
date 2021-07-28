package demo.hao;

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
}

