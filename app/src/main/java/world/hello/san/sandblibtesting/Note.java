package world.hello.san.sandblibtesting;


import android.graphics.Bitmap;

import san.db.handler.SanDbResult;
import san.db.handler.SetColumnAttr;

/**
 * Created by sanyatihan on 05-Sep-17.
 */

public class Note extends SanDbResult<Note> {

    @SetColumnAttr(setPrimary = true,setAutoIncrement = true)
    int id;
    String abc;
    String clean;


    public Note() {
    }

    public Note(int id, String abc, String clean) {
        this.id = id;
        this.abc = abc;
        this.clean = clean;
    }
    public Note( String abc, String clean) {
        this.abc = abc;
        this.clean = clean;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public String getClean() {
        return clean;
    }

    public void setClean(String clean) {
        this.clean = clean;
    }
}
