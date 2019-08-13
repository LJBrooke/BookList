package lucas.personal.book;

import android.content.Intent;
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

    Intent intent;
    ViewPager pager;
    TabLayout tabLayout;
    TabItem toRead;
    TabItem nowReading;
    TabItem haveRead;

    ArrayList<String> book;
    String oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        tabLayout = findViewById(R.id.tabLayout);
        toRead = findViewById(R.id.tabToRead);
        nowReading = findViewById(R.id.tabNowReading);
        haveRead = findViewById(R.id.tabHaveRead);

	    intent = getIntent();
        if (intent.hasExtra("lucas.personal.book.BOOKTOEDIT")){
        	book = intent.getStringArrayListExtra("lucas.personal.book.BOOKTOEDIT");
        	setInfo(book);
        }

        //ToDo Properly assign tab usage.

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        Button saveBook = findViewById(R.id.saveBook);
        saveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!intent.hasExtra("lucas.personal.book.BOOKTOEDIT")){
                bookshelf.addBook(getBook(), 0);
                finish();
                return;
                }
                bookshelf.editBook(oldTitle, getBook(),0);
                finish();
            }
        });
    }

    /**
     * Sets the nowReading tab to visible.
     * @return the nowReading View.
     */
    private View setNowReading(){
        nowReading.setVisibility(View.VISIBLE);
        toRead.setVisibility(View.INVISIBLE);
        haveRead.setVisibility(View.INVISIBLE);
        return nowReading;
    }

    /**
     * Sets the toReas tab to visible.
     * @return the toRead View.
     */
    private View setToRead(){
        nowReading.setVisibility(View.INVISIBLE);
        toRead.setVisibility(View.VISIBLE);
        haveRead.setVisibility(View.INVISIBLE);
        return toRead;
    }

    /**
     * Sets the haveReading tab to visible.
     * @return the haveRead View.
     */
    private View setHaveRead(){
        nowReading.setVisibility(View.INVISIBLE);
        toRead.setVisibility(View.INVISIBLE);
        haveRead.setVisibility(View.VISIBLE);
        return haveRead;
    }

    /**
     * Sets the input fields to the values provided by info.
     * @param info An Arraylist of the format {Title, Author, Note, startDate, FinishDate, CurrentPage}
     */
    private void setInfo(ArrayList<String> info){
	    TextView temp = findViewById(R.id.bookTitle);
	    temp.setText(info.get(0));
        oldTitle = temp.getText().toString();
        temp = findViewById(R.id.bookAuthor);
	    temp.setText(info.get(1));
	    temp = findViewById(R.id.bookNotes);
	    temp.setText(info.get(2));
	    temp = findViewById(R.id.dateStarted);
	    temp.setText(info.get(3));
	    temp = findViewById(R.id.dateFinished);
	    temp.setText(info.get(4));
	    temp = findViewById(R.id.page);
	    temp.setText(info.get(5));
    }

    /**
     * Collects values from all input fields.
     * @return TextField values in an ArrayList of format: {Title, Author, Note, startDate, FinishDate, CurrentPage}
     */
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
