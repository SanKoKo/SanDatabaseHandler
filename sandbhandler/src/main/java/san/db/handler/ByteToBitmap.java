package san.db.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;


class ByteToBitmap {

    protected static   byte [] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,outputStream);
        return outputStream.toByteArray();
    }


    protected  static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }

}
