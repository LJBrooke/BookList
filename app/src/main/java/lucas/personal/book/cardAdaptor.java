package lucas.personal.book;

import android.annotation.SuppressLint;
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
	private ArrayList<String> startDate;
	private ArrayList<String> finishDate;
	private ArrayList<String> currentPage;

	cardAdaptor(Context c, ArrayList<String> titles, ArrayList<String> authors, ArrayList<String> notes, ArrayList<String> startDate, ArrayList<String> finishDate, ArrayList<String> currentPage) {
		this.titles = titles;
		this.authors = authors;
		this.notes = notes;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.currentPage = currentPage;

		mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Creates an expanded bookcard view that displays the given information.
	 *
	 * @param c      Application Context.
	 * @param title  The book title
	 * @param author The book Author.
	 * @param note   User notes on the book.
	 * @param start  Date the book was started on.
	 * @param finish date the book was finished on.
	 * @param page   The current page of the book.
	 * @return A view containing the given information.
	 */
	static View displayBookCard(Context c, String title, String author, String note, String start, String finish, String page) {
		LayoutInflater mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		assert mInflater != null;
		@SuppressLint("InflateParams") View v = mInflater.inflate(R.layout.book_info_expanded, null);
		TextView titleView = v.findViewById(R.id.cardBookTitle);
		TextView authorView = v.findViewById(R.id.cardBookAuthor);
		TextView noteView = v.findViewById(R.id.cardBookNotes);
		TextView startView = v.findViewById(R.id.cardDateStarted);
		TextView endView = v.findViewById(R.id.cardDateFinished);

		titleView.setText(title);
		authorView.setText(author);
		noteView.setText(note);
		startView.setText(start);
		if (start.equals("")) {
			startView = v.findViewById(R.id.DateStartedText);
			startView.setText("");
		}
		if (finish.equals("")) {
			endView = v.findViewById(R.id.dateFinishedText);
			String curPage = "";
			if (!start.equals("")) {
				curPage = "Page: " + page;
			}
			endView.setText(curPage);
		} else {
			endView.setText(finish);
		}

		return v;
	}

	@Override
	public int getCount() {
		return titles.size();
	}

	@Override
	public ArrayList<String> getItem(int index) {
		ArrayList<String> book = new ArrayList<>();

		if (index < titles.size()) {
			book.add(titles.get(index));
			book.add(authors.get(index));
			book.add(notes.get(index));
			book.add(startDate.get(index));
			book.add(finishDate.get(index));
			book.add(currentPage.get(index));
			return book;
		}
		return book;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		@SuppressLint({"ViewHolder", "InflateParams"})
		View v = mInflater.inflate(R.layout.book_info_card, null);
		TextView titleView = v.findViewById(R.id.cardBookTitle);
		TextView authorView = v.findViewById(R.id.cardBookAuthor);
		TextView noteView = v.findViewById(R.id.cardBookNotes);

		if (i < titles.size()) {
			titleView.setText(titles.get(i));
			authorView.setText(authors.get(i));
			noteView.setText(notes.get(i));
			if (!currentPage.get(i).equals("")) {
				noteView = v.findViewById(R.id.page);
				String curPage = "Page " + currentPage.get(i);
				noteView.setText(curPage);
			}
		}

		return v;
	}
}
