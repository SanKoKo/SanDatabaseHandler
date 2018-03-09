package world.hello.san.sandblibtesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Next extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        Book book = new Book("General","$1000");
        book.insert();


        startActivity(new Intent(this,NextNext.class));

    }
}
