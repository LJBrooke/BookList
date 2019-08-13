package lucas.personal.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class addBook extends AppCompatActivity {

	Intent intent;

	String oldTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_book);

		intent = getIntent();
		if (intent.hasExtra("lucas.personal.book.BOOKTOEDIT")) {
			setInfo(intent.getStringArrayListExtra("lucas.personal.book.BOOKTOEDIT"));
		}

		Button saveBook = findViewById(R.id.saveBook);
		saveBook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!intent.hasExtra("lucas.personal.book.BOOKTOEDIT")) {
					bookshelf.addBook(getBook());
					finish();
					return;
				}
				bookshelf.editBook(oldTitle, getBook());
				finish();
			}
		});
	}

	/**
	 * Sets the input fields to the values provided by info.
	 *
	 * @param info An Arraylist of the format {Title, Author, Note, startDate, FinishDate, CurrentPage}
	 */
	private void setInfo(ArrayList<String> info) {
		TextView temp = findViewById(R.id.bookTitle);
		temp.setText(info.get(0));
		oldTitle = temp.getText().toString();
		temp = findViewById(R.id.bookAuthor);
		temp.setText(info.get(1));
		temp = findViewById(R.id.bookNotes);
		temp.setText(info.get(2));
		temp = findViewById(R.id.cardDateStarted);
		temp.setText(info.get(3));
		temp = findViewById(R.id.cardDateFinished);
		temp.setText(info.get(4));
		temp = findViewById(R.id.page);
		temp.setText(info.get(5));
	}

	/**
	 * Collects values from all input fields.
	 *
	 * @return TextField values in an ArrayList of format: {Title, Author, Note, startDate, FinishDate, CurrentPage}
	 */
	private ArrayList<String> getBook() {
		ArrayList<String> book = new ArrayList<>();
		TextView temp = findViewById(R.id.bookTitle);
		book.add(temp.getText().toString());
		temp = findViewById(R.id.bookAuthor);
		book.add(temp.getText().toString());
		temp = findViewById(R.id.bookNotes);
		book.add(temp.getText().toString());
		temp = findViewById(R.id.cardDateStarted);
		book.add(temp.getText().toString());
		temp = findViewById(R.id.cardDateFinished);
		book.add(temp.getText().toString());
		temp = findViewById(R.id.page);
		book.add(temp.getText().toString());

		return book;
	}
}
