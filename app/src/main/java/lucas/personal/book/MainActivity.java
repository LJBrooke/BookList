package lucas.personal.book;

import android.app.Activity;
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
    public SharedPreferences.Editor bookList;
    ListView bookCards;

    int[] reading = {}; //{2};
    int[] toRead = {};
    int[] haveRead = {}; //{0, 1, 3, 4, 5, 6, 7};
    int currentCategory = 0;
//    ArrayList<String> titles = {"Words of Radiance", "The Gathering Storm", "Warbreaker", "The Lies of Locke Lamora", "The Night Circus",
//            "Red Seas Under Red Skies", "Mistborn: Secret Histories", "Lord of Chaos"};
//    ArrayList<String> authors = {"Brandon Sanderson", "Robert Jordan and Brandon Sanderson", "Brandon Sanderson", "Scott Lynch",
//            "Erin Morgenstern", "Scott Lynch", "Brandon Sanderson", "Robert Jordan"};
//    ArrayList<String> notes = {"Dalinar touches a Shardblade that screams at him in spite of the in world meta saying it should not",
//            "Egwene scenes are intense", "Hello, would you like to kill someone today?",
//            "The Thorn of Camorr is said to be an unbeatable swordsman, a master thief, a friend to the poor, a ghost that walks through walls. " +
//                    "Slightly built and barely competent with a sword, Locke Lamora is, much to his annoyance the fabled Thorn. It should be said " +
//                    "that this Thorn of Camorr sounds a lot like Robin Hood. Locke however lacks the good grace to actually give to the poor.",
//            "Poppet <3",
//            "", "",
//            "Among the best of Rand's sub arcs"};
//    ArrayList<String> start = {"1st August 2019", "1st August 2019", "1st August 2019", "1st August 2019", "1st August 2019", "", "", "July 2019"};
//    ArrayList<String> finish = {"9th August 2019", "9th August 2019", "", "9th August 2019", "9th August 2019", "", "", "July 2019"};
//    ArrayList<String> currentPage = {"", "", "301", "", "", "", "", ""};
    ArrayList<String> titles;
    ArrayList<String> authors;
    ArrayList<String> notes;
    ArrayList<String> start;
    ArrayList<String> finish;
    ArrayList<String> currentPage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentCategory=0;
                    onStart();
                    return true;
                case R.id.navigation_dashboard:
                    currentCategory=1;
                    onStart();
                    return true;
                case R.id.navigation_notifications:
                    currentCategory=2;
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

        retrieveSharedPrefs();

        // Todo remove testing
        if (currentCategory==0) {showCategoryCards(reading);}
        if (currentCategory==1) {showCategoryCards(toRead);}
        if (currentCategory==2) {showCategoryCards(haveRead);}
//        showAllCards();
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

        // Todo remove testing
        if (currentCategory==0){showCategoryCards(reading);}
        if (currentCategory==1) {showCategoryCards(toRead);}
        if (currentCategory==2) {showCategoryCards(haveRead);}
//        showAllCards();

        bookCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewBookCard(i);
            }
        });
    }

    @Override
    public void finish() {

//        ToDO Implement Save Functionality
//        titles.add("Red Seas under Red Skies");
//        authors.add("Scott Lynch");
//        notes.add("I'll be keeping an eye on Locke's friendships");
//        currentPage.add("");
//        start.add("");
//        finish.add("");
//
//        toRead = new int[1];
//        toRead[0] = 1;
//        haveRead = new int[0];
//        reading = new int[0];


//        try {saveBookList();}
//        catch (JSONException e){
//            e.printStackTrace();
//        }

        super.finish();
    }

    protected void addBookActivity(){

        Intent addBook = new Intent(this, lucas.personal.book.addBook.class);
        startActivity(addBook);
    }

    /**
     * Adds relevant info to the arrays.
     * @param info Arraylist containing an entry for each array. in the form
     *             {Title, Author, Note, Start, Finish, Page, Category}
     * Todo Implement
     */
    protected void addBookInfo(ArrayList<String> info){
        ;
    }

    protected void viewBookCard(int i){
        Intent viewBook = new Intent(getApplicationContext(), activityBookInfo.class);
        int n = getIndex(i);
        viewBook.putExtra("lucas.personal.book.TITLE", titles.get(n));
        viewBook.putExtra("lucas.personal.book.AUTHOR", authors.get(n));
        viewBook.putExtra("lucas.personal.book.NOTE", notes.get(n));
        viewBook.putExtra("lucas.personal.book.START", start.get(n));
        viewBook.putExtra("lucas.personal.book.FINISH", finish.get(n));
        viewBook.putExtra("lucas.personal.book.PAGE", currentPage.get(n));

        startActivity(viewBook);
    }

    protected void showAllCards(){

        bookCards = findViewById(R.id.bookCards);
        cardAdaptor adapter = new cardAdaptor(this, titles, authors, notes, currentPage);
        bookCards.setAdapter(adapter);
    }

    protected void showCategoryCards(int[] category){

        int size = category.length;
        ArrayList<String> catTitles = new ArrayList<>();
        ArrayList<String> catAuthors = new ArrayList<>();
        ArrayList<String> catNotes = new ArrayList<>();
        ArrayList<String> catCurrentPage = new ArrayList<>();

        int n;
        for (int i=0; i<size; i++){
            n = category[i];
            catTitles.set(i, titles.get(n));
            catAuthors.set(i,authors.get(n));
            catNotes.set(i, notes.get(n));
            catCurrentPage.set(i,currentPage.get(n));
        }

        bookCards = findViewById(R.id.bookCards);
        cardAdaptor adapter = new cardAdaptor(this, catTitles, catAuthors, catNotes, catCurrentPage);
        bookCards.setAdapter(adapter);
    }

    private int getIndex(int i){
        if (currentCategory==0){return reading[i];}
        else if (currentCategory==1) {return toRead[i];}
        else if (currentCategory==2) {return haveRead[i];}
        return i; // Default return
    }

    protected void retrieveSharedPrefs(){

        SharedPreferences bookList = getSharedPreferences("bookListSettings", Activity.MODE_PRIVATE);
        setArrays(bookList);
    }

    protected void setArrays(SharedPreferences prefs){
        String temp;
        temp = prefs.getString("titles", "not found");
        titles = processJson(temp);
        temp = prefs.getString("authors", "not found");
        authors = processJson(temp);
        temp = prefs.getString("notes", "not found");
        notes = processJson(temp);
        temp = prefs.getString("start", "not found");
        start = processJson(temp);
        temp = prefs.getString("finish", "not found");
        finish = processJson(temp);
        temp = prefs.getString("page", "not found");
        currentPage = processJson(temp);
        temp = prefs.getString("toRead", "not found");
        toRead = processJsonInt(temp);
        temp = prefs.getString("haveRead", "not found");
        haveRead = processJsonInt(temp);
        temp = prefs.getString("reading", "not found");
        reading = processJsonInt(temp);

        if (titles==null){
            titles = new ArrayList<>();
            authors = new ArrayList<>();
            notes = new ArrayList<>();
            start = new ArrayList<>();
            finish = new ArrayList<>();
            currentPage = new ArrayList<>();
        }
    }

    private ArrayList<String> processJson(String JSONString) {
        JSONArray jsonBooks = null;
        ArrayList<String> info = new ArrayList<>();

        try {
            jsonBooks = new JSONArray(JSONString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < jsonBooks.length(); i++) {
                try {
                    info.add((String) jsonBooks.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }catch (NullPointerException e){}

        return info;
    }

    private int[] processJsonInt(String JSONString) {
        JSONArray jsonBooks = null;
        ArrayList<Integer> info = new ArrayList<>();

        try {
            jsonBooks = new JSONArray(JSONString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < jsonBooks.length(); i++) {
                try {
                    info.add(Integer.parseInt(jsonBooks.get(i).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {;}

        int size = info.size();
        int[] temp = new int[size];
        for (int i=0 ; i<size; i++){
            temp[i] = info.get(i);
        }

        return temp;
    }

    private void saveBookList() throws org.json.JSONException{

        if (titles!=null) {
            JSONArray jsonReading = new JSONArray(reading);
            JSONArray jsonToRead = new JSONArray(toRead);
            JSONArray jsonHaveRead = new JSONArray(haveRead);
            JSONArray jsonTitles = new JSONArray(titles);
            JSONArray jsonAuthors = new JSONArray(authors);
            JSONArray jsonNotes = new JSONArray(notes);
            JSONArray jsonStart = new JSONArray(start);
            JSONArray jsonFinish = new JSONArray(finish);
            JSONArray jsonPage = new JSONArray(currentPage);

            bookList.putString("toRead", jsonToRead.toString());
            bookList.putString("reading", jsonReading.toString());
            bookList.putString("haveRead", jsonHaveRead.toString());
            bookList.putString("titles", jsonTitles.toString());
            bookList.putString("authors", jsonAuthors.toString());
            bookList.putString("notes", jsonNotes.toString());
            bookList.putString("start", jsonStart.toString());
            bookList.putString("finish", jsonFinish.toString());
            bookList.putString("page", jsonPage.toString());
            bookList.apply();
        }
    }

}
