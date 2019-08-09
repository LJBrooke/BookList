package lucas.personal.book;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class addBook extends AppCompatActivity {

    TabLayout tabLayout;
    ArrayList<String> newBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        tabLayout = findViewById(R.id.tabLayout);

        Button saveBook = findViewById(R.id.saveBook);
        saveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newBook = getBook();
                finish();
            }
        });
    }

    private ArrayList<String> getBook() {
        ArrayList<String> book = new ArrayList<>();
        TextView temp = findViewById(R.id.bookTitle);
        book.set(0, temp.toString());
        temp = findViewById(R.id.bookAuthor);
        book.set(1, temp.toString());
        temp = findViewById(R.id.dateStarted);
        book.set(3, temp.toString());
        temp = findViewById(R.id.dateFinished);
        book.set(4, temp.toString());
        temp = findViewById(R.id.bookNotes);
        book.set(2, temp.toString());
        temp = findViewById(R.id.page);
        book.set(5, temp.toString());

        return book;
    }
}
