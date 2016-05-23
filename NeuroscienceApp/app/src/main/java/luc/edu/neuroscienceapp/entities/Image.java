package luc.edu.neuroscienceapp.entities;

/**
 * Created by diegotavarez on 5/23/16.
 */
public class Image {
    private String name;
    private int thumbnail;

    public Image() {
    }

    public Image(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
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
}