package lucas.personal.book;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class addBook extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabLayout;
    TabItem toRead;
    TabItem nowReading;
    TabItem haveRead;
    ArrayList<String> newBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        tabLayout = findViewById(R.id.tabLayout);
        toRead = findViewById(R.id.tabToRead);
        nowReading = findViewById(R.id.tabNowReading);
        haveRead = findViewById(R.id.tabHaveRead);

        //ToDo Properly assign tab usage.

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        Button saveBook = findViewById(R.id.saveBook);
        saveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookshelf.addBook(getBook(), 0);
                finish();
            }
        });
    }

    private View setNowReading(){
        nowReading.setVisibility(View.VISIBLE);
        toRead.setVisibility(View.INVISIBLE);
        haveRead.setVisibility(View.INVISIBLE);
        return nowReading;
    }

    private View setToRead(){
        nowReading.setVisibility(View.INVISIBLE);
        toRead.setVisibility(View.VISIBLE);
        haveRead.setVisibility(View.INVISIBLE);
        return toRead;
    }

    private View setHaveRead(){
        nowReading.setVisibility(View.INVISIBLE);
        toRead.setVisibility(View.INVISIBLE);
        haveRead.setVisibility(View.VISIBLE);
        return haveRead;
    }

    private ArrayList<String> getBook() {
        ArrayList<String> book = new ArrayList<>(6);
        TextView temp = findViewById(R.id.bookTitle);
        book.add(temp.getText().toString());
        temp = findViewById(R.id.bookAuthor);
        book.add(temp.getText().toString());
        temp = findViewById(R.id.bookNotes);
        book.add(temp.getText().toString());
        temp = findViewById(R.id.dateStarted);
        book.add(temp.getText().toString());
        temp = findViewById(R.id.dateFinished);
        book.add(temp.getText().toString());
        temp = findViewById(R.id.page);
        book.add(temp.getText().toString());

        return book;
    }
}
