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

  public static int[] covers = new int[]{
          R.drawable.carpet,
          R.drawable.tv_static,
          R.drawable.vaso,
          R.drawable.knob,
          R.drawable.bedroom,
          R.drawable.flowers,
          R.drawable.grass,
          R.drawable.grasshopper,
          R.drawable.cat,
          R.drawable.starry_night,
          R.drawable.newspaper,
  };

  public static int[] sound_covers = new int[]{
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

  public static int[] covers_ica = new int[]{
          R.drawable.carpet_ica,
          R.drawable.tv_static_ica,
          R.drawable.vaso_ica,
          R.drawable.knob_ica,
          R.drawable.bedroom_ica,
          R.drawable.flowers_ica,
          R.drawable.grass_ica,
          R.drawable.grasshopper_ica,
          R.drawable.cat_ica,
          R.drawable.starry_night_ica,
          R.drawable.newspaper_ica,
  };

    public static int[] sound_covers_ica = new int[]{
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

  public static int[] covers_grayscale = new int[]{
          R.drawable.carpet_grayscale,
          R.drawable.tv_static_grayscale,
          R.drawable.vaso_grayscale,
          R.drawable.knob_grayscale,
          R.drawable.bedroom_grayscale,
          R.drawable.flowers_grayscale,
          R.drawable.grass_grayscale,
          R.drawable.grasshopper_grayscale,
          R.drawable.cat_grayscale,
          R.drawable.starry_night_grayscale,
          R.drawable.newspaper_grayscale,
  };

    public static String[] sound_titles = new String[]{
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

  public static String[] titles = new String[]{
          "Carpet",
          "TV Static",
          "Vase",
          "Doorknob",
          "Bedroom",
          "Flowers",
          "Grass",
          "Grasshopper",
          "Cat",
          "Starry Night",
          "Newspaper"
  };

  public static boolean[] labels = new boolean[]{
          false,
          false,
          false,
          false,
          false,
          true,
          true,
          true,
          true,
          false,
          false
  };


}

