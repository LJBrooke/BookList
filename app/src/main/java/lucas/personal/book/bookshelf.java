package lucas.personal.book;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class bookshelf {
    // Shelf Info:
    private static ArrayList<Integer> reading;
    private static ArrayList<Integer> toRead;
    private static ArrayList<Integer> haveRead;
    private static int currentCategory;

    // Book Info:
    private static ArrayList<String> titles;
    private static ArrayList<String> authors;
    private static ArrayList<String> notes;
    private static ArrayList<String> start;
    private static ArrayList<String> finish;
    private static ArrayList<String> currentPage;

    bookshelf(){
        checkNull();
        //ToDO Move to New Thread
        loadBookList();
    }

    public static void addBook(String title, String author, String note, String startDate, String finishDate, String page, int category){
        checkNull();

        switch (category){
            case 0: {reading.add(titles.size());}
            case 1: {toRead.add(titles.size());}
            case 2: {haveRead.add(titles.size());}
        }

        titles.add(title);
        authors.add(author);
        notes.add(note);
        start.add(startDate);
        finish.add(finishDate);
        currentPage.add(page);
    }

    public static void addBook(ArrayList<String> newBook, int category){
        checkNull();

        if (category==0){reading.add(titles.size());}
        else if (category==1){toRead.add(titles.size());}
        else if (category==2){haveRead.add(titles.size());}
        try {
            titles.add(newBook.get(0));
            authors.add(newBook.get(1));
            notes.add(newBook.get(2));
            start.add(newBook.get(3));
            finish.add(newBook.get(4));
            currentPage.add(newBook.get(5));
        } catch (NullPointerException e){}
    }

    public static void editBook(String oldTitle, String title, String author, String note, String startDate, String finishDate, String page, int category){
        checkNull();

        int index = titles.lastIndexOf(oldTitle);
        removeFromCategory(index);
        switch (category){
            case 0: {
                reading.add(titles.size()-1);
                finishDate = "";
            }
            case 1: {
                toRead.add(titles.size()-1);
                startDate = "";
                finishDate = "";
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
        start.set(index, startDate);
        finish.set(index, finishDate);
        currentPage.set(index, page);
    }

    /**
     * Function to edit the provided book. Book is located by title.
     * @param oldTitle The unedited title of the book.
     * @param book ArrayList of new values for the book, in the format:
     *             {Title, Author, Notes, Start, Finish, Page}
     * @param category The new category for the book.
     */
    public static void editBook(String oldTitle, ArrayList<String> book, int category){
        checkNull();

        int index = titles.indexOf(oldTitle);
        removeFromCategory(index);
        if (category==0){
            reading.add(titles.size()-1);
            book.set(4, "");
        }
        else if (category==1){
            toRead.add(titles.size()-1);
            book.set(3,"");
            book.set(4, "");
            book.set(5, "");
        }
        else if (category==2){
            haveRead.add(titles.size()-1);
            book.set(5, "");
        }

        titles.set(index, book.get(0));
        authors.set(index, book.get(1));
        notes.set(index, book.get(2));
        start.set(index, book.get(3));
        finish.set(index, book.get(4));
        currentPage.set(index, book.get(5));
    }

    /**
     * Function to retrieve info of the book stored at the provided index.
     * @param index The index of the desired book.
     * @return an ArrayList containing all relevant info on the desired book.
     * in the format {Title, Author, Notes, Start, Finish, Page}
     */
    public static ArrayList<String> getBook(int index){
        ArrayList<String> book = new ArrayList<>(6);
        if (titles.size()>0) {
            book.add(titles.get(index));
            book.add(authors.get(index));
            book.add(notes.get(index));
            book.add(start.get(index));
            book.add(finish.get(index));
            book.add(currentPage.get(index));
        }
        return book;
    }

    public static void deleteBook(int i){
        // ToDo add prompt.
        if (titles== null){return;}

        int n = getIndex(i);

        removeFromCategory(n);
        correctIndex(n);

        titles.remove(n);
        authors.remove(n);
        notes.remove(n);
        start.remove(n);
        finish.remove(n);
        currentPage.remove(n);
    }

    public static void deleteBook(ArrayList<String> book){
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

    public static boolean onBookshelf(String title){return  titles.contains(title);}

    public static int getIndex(String title){return titles.lastIndexOf(title);}

    /**
     * Takes the index from a category and returns the overall index.
     * @param i the index of the book in the current category.
     * @return the ovrall index of the book.
     */
    public static int getIndex(int i){
        if (currentCategory==0){return reading.get(i);}
        else if (currentCategory==1) {return toRead.get(i);}
        return haveRead.get(i);
    }

    public static ArrayList<Integer> getCatBooks(){
        if (currentCategory==0) {
            if (reading==null){return new ArrayList<Integer>();}
            return reading;
        }
        else if (currentCategory==1) {
            if (toRead==null){return new ArrayList<Integer>();}
            return toRead;
        }
        else if (currentCategory==2) {
            if (haveRead == null) {return new ArrayList<Integer>();}
            return haveRead;
        }
        return null;
    }

    public static void setCurrentCategory(int newCategory){currentCategory=newCategory;}

    public static ArrayList<String> getTitles(){return titles;}

    public static ArrayList<String> getAuthors(){return authors;}

    public static ArrayList<String> getNotes(){return notes;}

    public static ArrayList<String> getCurrentPage(){return currentPage;}

    protected static void saveBookshelf(){

        return; // ToDo Implement Shared Prefs properly.

//        checkNull();
//
//        if (titles!=null) {
//            SharedPreferences bookListCurrent = getSharedPreferences("bookList", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor bookList = bookListCurrent.edit();
//
//            JSONArray jsonReading = new JSONArray(reading);
//            JSONArray jsonToRead = new JSONArray(toRead);
//            JSONArray jsonHaveRead = new JSONArray(haveRead);
//            JSONArray jsonTitles = new JSONArray(titles);
//            JSONArray jsonAuthors = new JSONArray(authors);
//            JSONArray jsonNotes = new JSONArray(notes);
//            JSONArray jsonStart = new JSONArray(start);
//            JSONArray jsonFinish = new JSONArray(finish);
//            JSONArray jsonPage = new JSONArray(currentPage);
//
//            bookList.putString("toRead", jsonToRead.toString());
//            bookList.putString("reading", jsonReading.toString());
//            bookList.putString("haveRead", jsonHaveRead.toString());
//            bookList.putString("titles", jsonTitles.toString());
//            bookList.putString("authors", jsonAuthors.toString());
//            bookList.putString("notes", jsonNotes.toString());
//            bookList.putString("start", jsonStart.toString());
//            bookList.putString("finish", jsonFinish.toString());
//            bookList.putString("page", jsonPage.toString());
//            bookList.apply();
//        }
    }

    private static void loadBookList(){

        return; // ToDo Implement Shared Prefs properly.

//        SharedPreferences bookList = getSharedPreferences("bookList", Activity.MODE_PRIVATE);
//        String temp;
//        temp = bookList.getString("titles", "not found");
//        titles = processJson(temp);
//        temp = bookList.getString("authors", "not found");
//        authors = processJson(temp);
//        temp = bookList.getString("notes", "not found");
//        notes = processJson(temp);
//        temp = bookList.getString("start", "not found");
//        start = processJson(temp);
//        temp = bookList.getString("finish", "not found");
//        finish = processJson(temp);
//        temp = bookList.getString("page", "not found");
//        currentPage = processJson(temp);
//        temp = bookList.getString("toRead", "not found");
//        toRead = processJsonInt(temp);
//        temp = bookList.getString("haveRead", "not found");
//        haveRead = processJsonInt(temp);
//        temp = bookList.getString("reading", "not found");
//        reading = processJsonInt(temp);
    }

    private static void removeFromCategory(int i){
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

    private static void correctIndex(int i){
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

    private static void checkNull(){
        try{
            titles.size();
        }catch (NullPointerException e){
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
