package lucas.personal.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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

        setBookInfo();

        View v = cardAdaptor.displayBookCard(this, title,author,note, start, finish, page);

        setContentView(v);

        FloatingActionButton fab = findViewById(R.id.editEntryFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editBook = new Intent(getApplicationContext(), lucas.personal.book.addBook.class);
                editBook.putStringArrayListExtra("lucas.personal.book.BOOKTOEDIT", getBookInfo());
                startActivity(editBook);
                finish();
            }
        });
    }

    /**
     * Gathers intent information and assigns it to instance variables.
     */
    private void setBookInfo(){
        title = getIntent().getStringExtra("lucas.personal.book.TITLE");
        author = getIntent().getStringExtra("lucas.personal.book.AUTHOR");
        note = getIntent().getStringExtra("lucas.personal.book.NOTE");
        start = getIntent().getStringExtra("lucas.personal.book.START");
        finish = getIntent().getStringExtra("lucas.personal.book.FINISH");
        page = getIntent().getStringExtra("lucas.personal.book.PAGE");
    }

    /**
     * Gathers all book info stored in instance variables and puts it into an ArrayList.
     * @return Book info in the format {Title, Author, Note, Start, FInish, Page}
     */
    private ArrayList<String> getBookInfo(){
        ArrayList<String> book = new ArrayList<>(6);
        book.add(title);
        book.add(author);
        book.add(note);
        book.add(start);
        book.add(finish);
        book.add(page);
        return book;
    }
}
