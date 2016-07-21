package luc.edu.neuroscienceapp.entities;

/**
 * Created by diegotavarez on 5/23/16.
 */
public class Example {
    private String name;
    private int thumbnail;
    private int thumbnail_ica;
    private int id;

    private String category;

    /**
     * Instantiates a new Card image.
     */
    public Example() {
    }

    /**
     * Instantiates a new Card image, which is a card that appears as a example of image or sound in the app.
     *
     * @param name          the name of the picture.
     * @param thumbnail     the thumbnail of the picture or the set of pictures that appear in this example.
     * @param thumbnail_ica the image representing the ica filters of that example
     * @param id            the id that is used to identify the thumbnails and information of an example in the Global class
     * @param category      the category of the example, which can be Natural, Non-Natural, or Group
     */
    public Example(String name, int thumbnail, int thumbnail_ica, int id, String category) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.thumbnail_ica = thumbnail_ica;
        this.id = id;
        this.category = category;
    }

    /**
     * Gets the name of the example.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the example.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the image of the example
     *
     * @return the thumbnail
     */
    public int getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets thumbnail.
     *
     * @param thumbnail the thumbnail
     */
    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Gets the id of image with the ica filters of the example, which is obtained in the Global class.
     * @return the thumbnail ica
     */
    public int getThumbnailIca() {
        return thumbnail_ica;
    }

    /**
     * Sets thumbnail ica.
     *
     * @param thumbnail_ica the thumbnail ica
     */
    public void setThumbnailIca(int thumbnail_ica) {
        this.thumbnail_ica = thumbnail_ica;
    }

    /**
     * Gets id of the card, which is basically the index of the information of the card in the arrays of the class Global
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the category of the example, which can be natural, non-natural, or group.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }
}