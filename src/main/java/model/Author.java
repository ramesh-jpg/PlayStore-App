package model;

import java.util.Objects;

/**
 * Represents an application author or developer in the PlayStore.
 * <p>
 * This model holds the author's unique identifier and their display name.
 * It is used to link apps to their creators.
 */
public class Author {
    private final int authorId;
    private String authorName;

    /**
     * Constructs a new Author instance.
     *
     * @param authorId The unique identifier for the author (0 if new).
     * @param authorName     The display name of the author or company.
     */
    public Author(final int authorId, final String authorName){
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public int getAuthorId(){
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                '}';
    }

    @Override
    public int hashCode(){
       return Objects.hashCode(authorId);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Author author)) return false;
        return authorId == author.authorId;
    }
}
