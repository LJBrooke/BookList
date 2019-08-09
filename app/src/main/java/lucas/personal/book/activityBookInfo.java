package lucas.personal.book;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class activityBookInfo extends AppCompatActivity {

    String title;
    String author;
    String start;
    String finish;
    String note;
    String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        title = getIntent().getStringExtra("lucas.personal.book.TITLE");
        author = getIntent().getStringExtra("lucas.personal.book.AUTHOR");
        note = getIntent().getStringExtra("lucas.personal.book.NOTE");
        start = getIntent().getStringExtra("lucas.personal.book.START");
        finish = getIntent().getStringExtra("lucas.personal.book.FINISH");
        page = getIntent().getStringExtra("lucas.personal.book.PAGE");

        View v = cardAdaptor.displayBookCard(this, title,author,note, start, finish, page);

        setContentView(v);

        FloatingActionButton fab = findViewById(R.id.editEntryFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Takes user to an edit screen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
