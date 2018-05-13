package world.hello.san.sandblibtesting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import san.db.handler.Column;
import san.db.handler.Database;
import san.db.handler.SanDBHandler;
import san.db.handler.Table;


public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    EditText ed_name,ed_fee,ed_course,ed_fathername;
    List<Student> mList;
    SanDBHandler sanDBHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sanDBHandler = new SanDBHandler(this);
        spinner =  findViewById(R.id.spinner);
        mList = new ArrayList<>();
        ed_fathername = findViewById(R.id.ed_fname);
        ed_fee = findViewById(R.id.ed_age);
        ed_course =  findViewById(R.id.ed_dob);
        ed_name =  findViewById(R.id.ed_name);


    }


    public void btInsertClick(View view) {

    }

    public void btnUpdate(View view) {
    }

    public void btnDelete(View view) {
    }

    public void btnSearch(View view) {
    }

    public void btnNext(View view) {

    }

    public void btnPrevious(View view) {
    }
}
