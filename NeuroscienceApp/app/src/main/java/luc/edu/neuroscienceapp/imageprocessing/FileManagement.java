package luc.edu.neuroscienceapp.imageprocessing;

import android.os.Environment;

import java.io.File;

/**
 * Created by diegotavarez on 5/20/16.
 */
public class FileManagement {
    public File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }
}
