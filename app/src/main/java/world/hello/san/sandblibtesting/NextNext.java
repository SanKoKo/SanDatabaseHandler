package world.hello.san.sandblibtesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NextNext extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_next);
        Book bb = new Book("Luck","$1900");
        bb.insert();
    }
}
