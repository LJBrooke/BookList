package lucas.personal.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView bookCards;
    bookshelf books;
    private ArrayList<String> book;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    books.setCurrentCategory(0);
                    onStart();
                    return true;
                case R.id.navigation_dashboard:
                    books.setCurrentCategory(1);
                    onStart();
                    return true;
                case R.id.navigation_notifications:
                    books.setCurrentCategory(2);
                    onStart();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        books = new bookshelf();

        showCategoryCards(books.getCatBooks());
        FloatingActionButton addBook = findViewById(R.id.addBookFAB);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        showCategoryCards(books.getCatBooks());

        bookCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewBookCard(i);
            }
        });
    }

    @Override
    public void finish() {

        //ToDo Move to new thread.
        books.saveBookshelf();

        super.finish();
    }

    protected void addBookActivity(){

        Intent addBook = new Intent(this, lucas.personal.book.addBook.class);
        startActivity(addBook);
        ArrayList<String> newBook = addBook.getStringArrayListExtra("newBook");
        int category;
        category = 0; //ToDo implement method to get Category.
        books.addBook(newBook, category);
    }

    protected void viewBookCard(int i){
        Intent viewBook = new Intent(getApplicationContext(), activityBookInfo.class);
        int n = books.getIndex(i);
        book = books.getBook(n);
        viewBook.putExtra("lucas.personal.book.TITLE", book.get(0));
        viewBook.putExtra("lucas.personal.book.AUTHOR", book.get(1));
        viewBook.putExtra("lucas.personal.book.NOTE", book.get(2));
        viewBook.putExtra("lucas.personal.book.START", book.get(3));
        viewBook.putExtra("lucas.personal.book.FINISH", book.get(4));
        viewBook.putExtra("lucas.personal.book.PAGE", book.get(5));

        startActivity(viewBook);
    }

    protected void showAllCards(){

        bookCards = findViewById(R.id.bookCards);
        //ToDo Create new method for this implementation.
        cardAdaptor adapter = new cardAdaptor(this, books.getTitles(), books.getAuthors(), books.getNotes(), books.getCurrentPage());
        bookCards.setAdapter(adapter);
    }

    protected void showCategoryCards(ArrayList<Integer> category){

        int size = category.size();
        ArrayList<String> catTitles = new ArrayList<>();
        ArrayList<String> catAuthors = new ArrayList<>();
        ArrayList<String> catNotes = new ArrayList<>();
        ArrayList<String> catCurrentPage = new ArrayList<>();

        int n;
        for (int i=0; i<size; i++){
            n = category.get(i);
            book = books.getBook(n);

            catTitles.set(i, book.get(0));
            catAuthors.set(i, book.get(1));
            catNotes.set(i, book.get(2));
            catCurrentPage.set(i, book.get(5));
        }

        bookCards = findViewById(R.id.bookCards);
        cardAdaptor adapter = new cardAdaptor(this, catTitles, catAuthors, catNotes, catCurrentPage);
        bookCards.setAdapter(adapter);
    }

}
