package lucas.personal.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class cardAdaptor extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<String> titles;
    private ArrayList<String> authors;
    private ArrayList<String> notes;
    private ArrayList<String> currentPage;

    protected cardAdaptor(Context c, ArrayList<String> titles, ArrayList<String> authors, ArrayList<String> notes, ArrayList<String> currentPage){
        this.titles = titles;
        this.authors = authors;
        this.notes = notes;
        this.currentPage = currentPage;


        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View viewBookCard(int i){
        View v = mInflater.inflate(R.layout.book_info_expanded ,null);
        TextView titleView = v.findViewById(R.id.cardBookTitle);
        TextView authorView = v.findViewById(R.id.cardBookAuthor);
        TextView noteView = v.findViewById(R.id.cardBookNotes);

        titleView.setText(titles.get(i));
        authorView.setText(authors.get(i));
        noteView.setText(notes.get(i));

        if (!currentPage.get(i).equals("")){
            noteView = v.findViewById(R.id.page);
            noteView.setText(currentPage.get(i));
        }

        return v;
    }

    public static View displayBookCard(Context c, String title, String author, String note, String start, String finish, String page){
        LayoutInflater mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.book_info_expanded ,null);
        TextView titleView = v.findViewById(R.id.cardBookTitle);
        TextView authorView = v.findViewById(R.id.cardBookAuthor);
        TextView noteView = v.findViewById(R.id.cardBookNotes);
        TextView startView = v.findViewById(R.id.dateStarted);
        TextView endView = v.findViewById(R.id.dateFinished);

        titleView.setText(title);
        authorView.setText(author);
        noteView.setText(note);
        startView.setText(start);
        if (finish.equals("")){
            endView = v.findViewById(R.id.dateFinished);
            String curPage = "";
            if (!start.equals("")){
                curPage = "Page: " + page;
            }
            endView.setText(curPage);
        }
        else {endView.setText(finish);}

        return v;
    }

    @Override
    public int getCount() {
        return  titles.size();
    }

    @Override
    public ArrayList<String> getItem(int index) {
        ArrayList<String> book = new ArrayList<>();

        if (index < titles.size()){
            book.add(titles.get(index));
            book.add(authors.get(index));
            book.add(notes.get(index));
            return book;
        }

        book.add("Book Not Found");
        book.add(" ");
        book.add(" ");

        return book;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.book_info_card ,null);
        TextView titleView = (TextView) v.findViewById(R.id.cardBookTitle);
        TextView authorView = (TextView) v.findViewById(R.id.cardBookAuthor);
        TextView noteView = (TextView) v.findViewById(R.id.cardBookNotes);

        if (i < titles.size()) {
            titleView.setText(titles.get(i));
            authorView.setText(authors.get(i));
            noteView.setText(notes.get(i));
            if (!currentPage.get(i).equals("")){
                noteView = v.findViewById(R.id.page);
                String curPage = "Page " + currentPage.get(i); // Todo Extract to strings.xml
                noteView.setText(curPage);
            }
        }

        return v;
    }
}
