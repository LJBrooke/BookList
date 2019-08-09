package lucas.personal.book;

import android.app.Activity;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class bookshelf {
    // Shelf Info:
    private ArrayList<Integer> reading;
    private ArrayList<Integer> toRead;
    private ArrayList<Integer> haveRead;
    private int currentCategory;

    // Book Info:
    private ArrayList<String> titles;
    private ArrayList<String> authors;
    private ArrayList<String> notes;
    private ArrayList<String> start;
    private ArrayList<String> finish;
    private ArrayList<String> currentPage;

    private bookshelf(){
        checkNull();
        getBookList();
    }

    public void addBook(String title, String author, String note, String start, String finish, String page, int category){
        checkNull();

        switch (category){
            case 0: {reading.add(titles.size());}
            case 1: {toRead.add(titles.size());}
            case 2: {haveRead.add(titles.size());}
        }

        titles.add(title);
        authors.add(author);
        notes.add(note);
        this.start.add(start);
        this.finish.add(finish);
        currentPage.add(page);
    }

    public void addBook(ArrayList<String> book, int category){
        checkNull();

        switch (category){
            case 0: {reading.add(titles.size());}
            case 1: {toRead.add(titles.size());}
            case 2: {haveRead.add(titles.size());}
        }

        titles.add(book.get(0));
        authors.add(book.get(1));
        notes.add(book.get(2));
        this.start.add(book.get(3));
        this.finish.add(book.get(4));
        currentPage.add(book.get(5));
    }

    public void editBook(String oldTitle, String title, String author, String note, String start, String finish, String page, int category){
        checkNull();

        int index = titles.lastIndexOf(oldTitle);
        removeFromCategory(index);
        switch (category){
            case 0: {
                reading.add(titles.size()-1);
                finish = "";
            }
            case 1: {
                toRead.add(titles.size()-1);
                start = "";
                finish = "";
                page = "";
            }
            case 2: {
                haveRead.add(titles.size()-1);
                page = "";
            }
        }

        titles.set(index, title);
        authors.set(index, author);
        notes.set(index, note);
        this.start.set(index, start);
        this.finish.set(index, finish);
        currentPage.set(index, page);
    }

    /**
     * Function to edit the provided book. Book is located by title.
     * @param oldTitle The unedited title of the book.
     * @param book ArrayList of new values for the book, in the format:
     *             {Title, Author, Notes, Start, Finish, Page}
     * @param category The new category for the book.
     */
    public void editBook(String oldTitle, ArrayList<String> book, int category){
        checkNull();

        int index = titles.lastIndexOf(oldTitle);
        removeFromCategory(index);
        switch (category){
            case 0: {
                reading.add(titles.size()-1);
                book.set(4, "");
            }
            case 1: {
                toRead.add(titles.size()-1);
                book.set(3,"");
                book.set(4, "");
                book.set(5, "");
            }
            case 2: {
                haveRead.add(titles.size()-1);
                book.set(5, "");
            }
        }

        titles.set(index, book.get(0));
        authors.set(index, book.get(1));
        notes.set(index, book.get(2));
        this.start.set(index, book.get(3));
        this.finish.set(index, book.get(4));
        currentPage.set(index, book.get(5));
    }

    /**
     * Function to retrieve info of the book stored at the provided index.
     * @param index The index of the desired book.
     * @return an ArrayList containing all relevant info on the desired book.
     * in the format {Title, Author, Notes, Start, Finish, Page}
     */
    public ArrayList<String> getBook(int index){
        ArrayList<String> book = new ArrayList<>();
        book.add(titles.get(index));
        book.add(authors.get(index));
        book.add(notes.get(index));
        book.add(start.get(index));
        book.add(finish.get(index));
        book.add(currentPage.get(index));

        return book;
    }

    public void deleteBook(int i){
        if (titles== null){return;}

        removeFromCategory(i);
        correctIndex(i);

        titles.remove(i);
        authors.remove(i);
        notes.remove(i);
        start.remove(i);
        finish.remove(i);
        currentPage.remove(i);
    }

    public void deleteBook(ArrayList<String> book){
        if (titles== null){return;}

        int i = getIndex(book.get(0));

        removeFromCategory(i);
        correctIndex(i);

        titles.remove(i);
        authors.remove(i);
        notes.remove(i);
        start.remove(i);
        finish.remove(i);
        currentPage.remove(i);
    }

    public boolean onBookshelf(String title){return  titles.contains(title);}

    public int getIndex(String title){return titles.lastIndexOf(title);}

    public void saveBookshelf(){

        //ToDo Move to new thread.
        checkNull();

        if (titles!=null) {
            SharedPreferences bookListCurrent = getSharedPreferences("bookList", Activity.MODE_PRIVATE);
            SharedPreferences.Editor bookList = bookListCurrent.edit();

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

    private void getBookList(){

        //ToDo Move to new thread.
        SharedPreferences bookList = getSharedPreferences("bookList", Activity.MODE_PRIVATE);
        String temp;
        temp = bookList.getString("titles", "not found");
        titles = processJson(temp);
        temp = bookList.getString("authors", "not found");
        authors = processJson(temp);
        temp = bookList.getString("notes", "not found");
        notes = processJson(temp);
        temp = bookList.getString("start", "not found");
        start = processJson(temp);
        temp = bookList.getString("finish", "not found");
        finish = processJson(temp);
        temp = bookList.getString("page", "not found");
        currentPage = processJson(temp);
        temp = bookList.getString("toRead", "not found");
        toRead = processJsonInt(temp);
        temp = bookList.getString("haveRead", "not found");
        haveRead = processJsonInt(temp);
        temp = bookList.getString("reading", "not found");
        reading = processJsonInt(temp);
    }

    public int getCurrentCategory(){return  currentCategory;}

    private void removeFromCategory(int i){
        int d;
        if (reading.contains(i)){
            d = reading.indexOf(i);
            reading.remove(d);
        }
        else if (toRead.contains(i)){
            d = toRead.indexOf(i);
            toRead.remove(d);
        }
        else if (haveRead.contains(i)){
            d = haveRead.indexOf(i);
            haveRead.remove(d);
        }
    }

    private void correctIndex(int i){
        int value;
        for (int n = 0; n<reading.size(); n++){
            value = reading.get(n);
            if (value>i){reading.set(n, value-1);}
        }
        for (int n = 0; n<toRead.size(); n++){
            value = toRead.get(n);
            if (value>i){toRead.set(n, value-1);}
        }
        for (int n = 0; n<haveRead.size(); n++){
            value = haveRead.get(n);
            if (value>i){haveRead.set(n, value-1);}
        }
    }

    private void checkNull(){
        if (titles == null){
            titles = new ArrayList<>();
            authors = new ArrayList<>();
            notes = new ArrayList<>();
            start = new ArrayList<>();
            finish = new ArrayList<>();
            currentPage = new ArrayList<>();

            toRead = new ArrayList<>();
            reading = new ArrayList<>();
            haveRead = new ArrayList<>();
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

    private ArrayList<Integer> processJsonInt(String JSONString) {
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
                    info.add(i, Integer.parseInt(jsonBooks.get(i).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {e.printStackTrace();}

        return info;
    }
}
