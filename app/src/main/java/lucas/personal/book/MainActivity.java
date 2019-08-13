package lucas.personal.book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView bookCards;
    private SharedPreferences bookshelfSharedPrefs;
    private SharedPreferences.Editor bookshelfEditor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    bookshelf.setCurrentCategory(0);
                    onStart();
                    return true;
                case R.id.navigation_dashboard:
                    bookshelf.setCurrentCategory(1);
                    onStart();
                    return true;
                case R.id.navigation_notifications:
                    bookshelf.setCurrentCategory(2);
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

        bookshelfSharedPrefs = getSharedPreferences("bookList", AppCompatActivity.MODE_PRIVATE);
        loadBookshelf();

        showCategoryCards(bookshelf.getCatBooks());
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

        showCategoryCards(bookshelf.getCatBooks());

        bookCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewBookCard(i);
            }
        });

        bookCards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                bookshelf.deleteBook(i);
                onStart();
                return true;
            }
        });
    }

    @Override
    public void finish() {

        super.finish();
    }

    @Override
    protected void onDestroy() {

        //ToDo Move to new thread.
        saveBookshelf();

        super.onDestroy();
    }

    /**
     * Launches the addBook Activity.
     */
    protected void addBookActivity(){

        Intent addBook = new Intent(this, lucas.personal.book.addBook.class);
        startActivity(addBook);
        onStart();
    }

    /**
     * Launches the viewBook activity, for a full overview of the selected book.
     * @param i The index of the selected book in the current category.
     */
    protected void viewBookCard(int i){
        Intent viewBook = new Intent(getApplicationContext(), activityBookInfo.class);
        int n = bookshelf.getIndex(i);
        ArrayList<String> book = bookshelf.getBook(n);
        viewBook.putExtra("lucas.personal.book.TITLE", book.get(0));
        viewBook.putExtra("lucas.personal.book.AUTHOR", book.get(1));
        viewBook.putExtra("lucas.personal.book.NOTE", book.get(2));
        viewBook.putExtra("lucas.personal.book.START", book.get(3));
        viewBook.putExtra("lucas.personal.book.FINISH", book.get(4));
        viewBook.putExtra("lucas.personal.book.PAGE", book.get(5));

        startActivity(viewBook);
    }

    /**
     * Shows all cards. Irrespective of the books category.
     */
    protected void showAllCards(){

        bookCards = findViewById(R.id.bookCards);
        //ToDo Create new method for this implementation.
        cardAdaptor adapter = new cardAdaptor(this, bookshelf.getTitles(), bookshelf.getAuthors(), bookshelf.getNotes(), bookshelf.getCurrentPage());
        bookCards.setAdapter(adapter);
    }

    /**
     * Uses cardAdaptor to display all cards from the given category.
     * @param category An ArrayList of the indexes of all books in the desired category.
     */
    protected void showCategoryCards(ArrayList<Integer> category){

        int size = category.size();
        ArrayList<String> catTitles = new ArrayList<>(size);
        ArrayList<String> catAuthors = new ArrayList<>(size);
        ArrayList<String> catNotes = new ArrayList<>(size);
        ArrayList<String> catCurrentPage = new ArrayList<>(size);

        int n;
        if (size>0) {
            ArrayList<String> book;
            for (int i = 0; i < size; i++) {
                n = category.get(i);

                book = bookshelf.getBook(n);

                catTitles.add(i, book.get(0));
                catAuthors.add(i, book.get(1));
                catNotes.add(i, book.get(2));
                catCurrentPage.add(i, book.get(5));
            }
        }

        bookCards = findViewById(R.id.bookCards);
        cardAdaptor adapter = new cardAdaptor(this, catTitles, catAuthors, catNotes, catCurrentPage);
        bookCards.setAdapter(adapter);
    }

    /**
     * Save application information to sharedprefs
     */
    private void saveBookshelf(){
        bookshelf.checkNull();

        bookshelfEditor = bookshelfSharedPrefs.edit();

        JSONArray jsonReading = new JSONArray(bookshelf.getCatBooks(0));
        JSONArray jsonToRead = new JSONArray(bookshelf.getCatBooks(1));
        JSONArray jsonHaveRead = new JSONArray(bookshelf.getCatBooks(2));
        JSONArray jsonTitles = new JSONArray(bookshelf.getTitles());
        JSONArray jsonAuthors = new JSONArray(bookshelf.getAuthors());
        JSONArray jsonNotes = new JSONArray(bookshelf.getNotes());
        JSONArray jsonStart = new JSONArray(bookshelf.getStart());
        JSONArray jsonFinish = new JSONArray(bookshelf.getFinish());
        JSONArray jsonPage = new JSONArray(bookshelf.getCurrentPage());

        bookshelfEditor.putString("toRead", jsonToRead.toString());
        bookshelfEditor.putString("reading", jsonReading.toString());
        bookshelfEditor.putString("haveRead", jsonHaveRead.toString());
        bookshelfEditor.putString("titles", jsonTitles.toString());
        bookshelfEditor.putString("authors", jsonAuthors.toString());
        bookshelfEditor.putString("notes", jsonNotes.toString());
        bookshelfEditor.putString("start", jsonStart.toString());
        bookshelfEditor.putString("finish", jsonFinish.toString());
        bookshelfEditor.putString("page", jsonPage.toString());
        bookshelfEditor.apply();
        System.out.println("FooBar save titles: " + jsonTitles.toString());
    }

    /**
     * Load Application information form shared prefs.
     */
    private void loadBookshelf(){
        String temp;
        temp = bookshelfSharedPrefs.getString("titles", "Titles not found");
        System.out.println("FooBar load temp: " + temp);
        bookshelf.setTitles(processJson(temp));
        temp = bookshelfSharedPrefs.getString("authors", null);
        bookshelf.setAuthors(processJson(temp));
        temp = bookshelfSharedPrefs.getString("notes", null);
        bookshelf.setNotes(processJson(temp));
        temp = bookshelfSharedPrefs.getString("start", null);
        bookshelf.setStart(processJson(temp));
        temp = bookshelfSharedPrefs.getString("finish", null);
        bookshelf.setFinish(processJson(temp));
        temp = bookshelfSharedPrefs.getString("page", null);
        bookshelf.setCurrentPage(processJson(temp));
        temp = bookshelfSharedPrefs.getString("toRead", null);
        bookshelf.setCatBooks(1, processJsonInt(temp));
        temp = bookshelfSharedPrefs.getString("haveRead", null);
        bookshelf.setCatBooks(2, processJsonInt(temp));
        temp = bookshelfSharedPrefs.getString("reading", null);
        bookshelf.setCatBooks(0, processJsonInt(temp));
    }

    /**
     * Converts JSONString to an ArrayList<String>.
     * @param JSONString JSONString to be converted.
     * @return ArrayList equivalent to the provided JSONString.
     */
    private static ArrayList<String> processJson(String JSONString) {
        System.out.println("FooBar processJson: "+ JSONString);
        JSONArray jsonBooks = null;
        ArrayList<String> info = new ArrayList<>();

        try {
            try {
                jsonBooks = new JSONArray(JSONString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jsonBooks.length(); i++) {
                try {
                    info.add(jsonBooks.getString(i));
                    System.out.println("FooBar processJson jsonBooks.getString(i): " + jsonBooks.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }catch (NullPointerException e){}

        return info;
    }

    /**
     * Converts JSONString to an ArrayList<Integer>.
     * @param JSONString JSONString to be converted.
     * @return ArrayList equivalent to the provided JSONString.
     */
    private static ArrayList<Integer> processJsonInt(String JSONString) {
        JSONArray jsonBooks = null;
        ArrayList<Integer> info = new ArrayList<>();

        try {
            try {
                jsonBooks = new JSONArray(JSONString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jsonBooks.length(); i++) {
                try {
                    info.add(i, Integer.parseInt(jsonBooks.get(i).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {e.printStackTrace();}

        return info;
    }
}
