package luc.edu.neuroscienceapp.entities;

import android.graphics.Bitmap;

//This class was created to retrieve large Bitmap Objects in order to pass through Android activities.
//The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all
// transactions in progress for the process. Consequently, a Failed Binder Trasaction, or TransactionTooLargeException
// can be thrown when there are many transactions in progress even when most of the individual
// transactions are of moderate size.


public class Global {
  public static Bitmap img;
  public static Bitmap imgGrayscale;
  public static byte[] bytesBitmap;
}

