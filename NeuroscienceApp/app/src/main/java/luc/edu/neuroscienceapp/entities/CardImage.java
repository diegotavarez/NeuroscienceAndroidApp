package luc.edu.neuroscienceapp.entities;

/**
 * Created by diegotavarez on 5/23/16.
 */
public class CardImage {
    private String name;
    private int thumbnail;
    private int thumbnail_ica;
    private int id;

    private String category;

    public CardImage() {
    }

    public CardImage(String name, int thumbnail, int thumbnail_ica, int id, String category) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.thumbnail_ica = thumbnail_ica;
        this.id = id;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnailIca() {
        return thumbnail_ica;
    }

    public void setThumbnailIca(int thumbnail_ica) {
        this.thumbnail_ica = thumbnail_ica;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}