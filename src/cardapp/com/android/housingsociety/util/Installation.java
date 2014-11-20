package cardapp.com.android.housingsociety.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import android.content.Context;

/**
 * 产生唯一标识符
 *
 * @author CardApp@ZuoQing
 *
 */
public class Installation {
    private static String sID = null;
    private static final String INSTALLATION = "HOUSINGSOCIETY_INSTALLATION";

    public synchronized static String id(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);

            try {
                if (!installation.exists()) {
                    writeInstallationFile(installation);
                }

                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return sID;
    }

    private static String readInstallationFile(File installation)
        throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();

        return new String(bytes);
    }

    private static void writeInstallationFile(File installation)
        throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}