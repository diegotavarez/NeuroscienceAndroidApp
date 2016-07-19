package luc.edu.neuroscienceapp.entities;

import android.graphics.Bitmap;

import luc.edu.neuroscienceapp.R;

//This class was created to retrieve large Bitmap Objects in order to pass through Android activities.
//The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all
// transactions in progress for the process. Consequently, a Failed Binder Trasaction, or TransactionTooLargeException
// can be thrown when there are many transactions in progress even when most of the individual
// transactions are of moderate size.

public class Global {
  public static Bitmap img;
  public static Bitmap imgGrayscale;
  public static byte[] bytesBitmap;

  public static final String IMAGE_GROUP = "IMAGE_GROUP";
  public static final String NORMAL = "normal";
  public static final String SOUND_GROUP = "SOUND_GROUP";

  /**
   * The thumbnails of the examples of images of the app.
   */
  public static int[] thumbnails = new int[]{
          R.drawable.carpet,
          R.drawable.tv_static,
          R.drawable.vaso,
          R.drawable.knob,
          R.drawable.bedroom,
          R.drawable.starry_night,
          R.drawable.newspaper,
          R.drawable.flowers,
          R.drawable.grass,
          R.drawable.grasshopper,
          R.drawable.cat

  };

  /**
   * The thumbnails of the examples of sounds of the app.
   */
  public static int[] sound_thumbnails = new int[]{
          R.drawable.bear,
          R.drawable.keyboard,
          R.drawable.laser,
          R.drawable.wolf,
          R.drawable.bird,
          R.drawable.cow,
          R.drawable.cricket,
          R.drawable.footstep,
          R.drawable.wind,
          R.drawable.cat,
          R.drawable.human_no
  };

  /**
   * The thumbnails of the groups of sound of the app.
   */
  public static int[] sound_group_thumbnails = new int[]{
          R.drawable.puncutated,
          R.drawable.human_no
  };

  /**
   * The thumbnails of the groups of images of the app.
   */
  public static int[] image_groups_thumbnails = new int[]{
          R.drawable.animals,
          R.drawable.landscapes
  };

  /**
   * The sound files for the sound examples of the app.
   */
  public static int[] sounds = new int[]{
          R.raw.bear,
          R.raw.typing,
          R.raw.laser,
          R.raw.wolf,
          R.raw.bird,
          R.raw.cow,
          R.raw.cricket,
          R.raw.footsteps_concrete,
          R.raw.wind,
          R.raw.cat,
          R.raw.human_no
  };

  /**
   * The sound files for the sound group examples of the app.
   */
  public static int[] sounds_group = new int[]{
          R.raw.punctuated,
          R.raw.speech
  };

  /**
   * The thumbnails of the ica filters for the examples of image of the app.
   */
  public static int[] image_ica_thumbnails = new int[]{
          R.drawable.carpet_ica,
          R.drawable.tv_static_ica,
          R.drawable.vaso_ica,
          R.drawable.knob_ica,
          R.drawable.bedroom_ica,
          R.drawable.starry_night_ica,
          R.drawable.newspaper_ica,
          R.drawable.flowers_ica,
          R.drawable.grass_ica,
          R.drawable.grasshopper_ica,
          R.drawable.cat_ica

  };

  /**
   * The thumbnails of the ica filters for the examples of groups of images of the app.
   */
  public static int[] image_groups_ica_thumbnails = new int[]{
          R.drawable.animals_ica,
          R.drawable.landscapes_ica

  };

  /**
   * The thumbnails of the ica filters for the examples of sound of the app.
   */
    public static int[] sound_ica_thumbnails = new int[]{
            R.drawable.bear_filters,
            R.drawable.typing_filters,
            R.drawable.laser_filters,
            R.drawable.wolf_filters,
            R.drawable.birdfilters,
            R.drawable.cow_filters,
            R.drawable.cricket_filters,
            R.drawable.footsteps_filters,
            R.drawable.wind_filters,
            R.drawable.cat_filters,
            R.drawable.human_no_filters,
    };

  /**
   * The thumbnails of the ica filters for the examples of groups of sounds of the app.
   */
  public static int[] sound_groups_ica_thumbnails = new int[]{
          R.drawable.punctuated_ica,
          R.drawable.speech_ica

  };

  /**
   * The thumbnails of the examples of images of the app, converted to grayscale.
   */
  public static int[] image_grayscale_thumbnails = new int[]{
          R.drawable.carpet_grayscale,
          R.drawable.tv_static_grayscale,
          R.drawable.vaso_grayscale,
          R.drawable.knob_grayscale,
          R.drawable.bedroom_grayscale,
          R.drawable.starry_night_grayscale,
          R.drawable.newspaper_grayscale,
          R.drawable.flowers_grayscale,
          R.drawable.grass_grayscale,
          R.drawable.grasshopper_grayscale,
          R.drawable.cat_grayscale

  };

  /**
   * The thumbnails of the examples of groups of images of the app, converted to grayscale.
   */
  public static int[] image_groups_covers_grayscale = new int[]{
          R.drawable.animals,
          R.drawable.landscapes,

  };

  /**
   * The names of the examples of sounds.
   */
    public static String[] sound_names = new String[]{
            "Bear",
            "Keyboard",
            "Laser",
            "Wolf",
            "Bird",
            "Cow",
            "Cricket",
            "Footsteps",
            "Wind",
            "Cat",
            "Human Speech"
    };

  /**
   * An array of booleans that determines which sounds are harmonic and which are not of the examples.
   */
  public static boolean[] harmonic_sounds = new boolean[]{
          true,
          false,
          true,
          true,
          true,
          true,
          true,
          false,
          false,
          true,
          false
  };

  /**
   * The names of the examples of images.
   */
  public static String[] image_names = new String[]{
          "Carpet",
          "TV Static",
          "Vase",
          "Doorknob",
          "Bedroom",
          "Starry Night",
          "Newspaper",
          "Flowers",
          "Grass",
          "Grasshopper",
          "Cat"

  };

  /**
   * The names of the examples of groups of images.
   */
  public static String[] image_groups_names = new String[]{
          "Animals",
          "Landscapes"

  };

  /**
   * The names of the examples of groups of sounds.
   */
  public static String[] sound_groups_names = new String[]{
          "Puntuated Sounds",
          "Speech"

  };

  /**
   * An array of booleans that determines which images are natural and which are not of the examples.
   */
  public static boolean[] natural_images = new boolean[]{
          false,
          false,
          false,
          false,
          false,
          false,
          false,
          true,
          true,
          true,
          true

  };


}

