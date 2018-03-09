package world.hello.san.sandblibtesting;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import san.db.handler.SanDBHandler;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SanDBHandler sanDBHandler = new SanDBHandler(this);

        //sanDBHandler.createDatabaseByClass(new Class[]{Book.class},"HERO");
        //sanDBHandler.createDatabaseFromAsset(this,"HERO",Note.class);
        //  sanDBHandler.setSecure(true);


        //sanDBHandler.createDatabaseByClass(new Class[]{Book.class,Next.class},"db");
    }


}
