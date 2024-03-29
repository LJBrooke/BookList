package lucas.personal.book;

import androidx.annotation.NonNull;

import java.util.ArrayList;

class bookshelf {
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

	/**
	 * Function to add the provided book.
	 *
	 * @param newBook ArrayList of values for the book, in the format:
	 *                *             {Title, Author, Notes, Start, Finish, Page}
	 */
	static void addBook(ArrayList<String> newBook) {
		checkNull();
		int category = detCategory(newBook.get(3), newBook.get(4));
		if (category == 0) {
			reading.add(titles.size());
		} else if (category == 1) {
			toRead.add(titles.size());
		} else if (category == 2) {
			haveRead.add(titles.size());
		}
		try {
			titles.add(newBook.get(0));
			authors.add(newBook.get(1));
			notes.add(newBook.get(2));
			start.add(newBook.get(3));
			finish.add(newBook.get(4));
			currentPage.add(newBook.get(5));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to edit the provided book. Book is located by title.
	 *
	 * @param oldTitle The unedited title of the book.
	 * @param book     ArrayList of new values for the book, in the format:
	 *                 {Title, Author, Notes, Start, Finish, Page}
	 */
	static void editBook(String oldTitle, ArrayList<String> book) {
		int index = titles.indexOf(oldTitle);
		int category = detCategory(book.get(3), book.get(4));
		removeFromCategory(index);
		if (category == 0) {
			reading.add(index);
		} else if (category == 1) {
			toRead.add(index);
		} else if (category == 2) {
			haveRead.add(index);
		}

		titles.set(index, book.get(0));
		authors.set(index, book.get(1));
		notes.set(index, book.get(2));
		start.set(index, book.get(3));
		finish.set(index, book.get(4));
		currentPage.set(index, book.get(5));
	}

	/**
	 * Deletes the book at the given index.
	 *
	 * @param categoryIndex The index of the selected book in the given category.
	 */
	static void deleteBook(int categoryIndex) {
		if (titles == null) {
			return;
		}

		int universalIndex = getIndex(categoryIndex);

		removeFromCategory(universalIndex);
		correctIndex(universalIndex);

		titles.remove(universalIndex);
		authors.remove(universalIndex);
		notes.remove(universalIndex);
		start.remove(universalIndex);
		finish.remove(universalIndex);
		currentPage.remove(universalIndex);
	}

	/**
	 * Sets the new category.
	 *
	 * @param newCategory The new Category.
	 */
	static void setCurrentCategory(int newCategory) {
		currentCategory = newCategory;
	}

	/**
	 * Gives the books in the specified Category.
	 *
	 * @param category Chooses the category. 0 is reading, 1 is toRead and 2 is haveRead.
	 */
	static void setCatBooks(int category, ArrayList<Integer> newCategory) {
		if (category == 0) {
			reading = newCategory;
		} else if (category == 1) {
			toRead = newCategory;
		} else if (category == 2) {
			haveRead = newCategory;
		}
	}

	private static int getIndex(String title) {
		return titles.lastIndexOf(title);
	}

	/**
	 * Takes the index from a category and returns the overall index.
	 *
	 * @param categoryIndex the index of the book in the current category.
	 * @return the overall index of the book.
	 */
	static int getIndex(int categoryIndex) {
		if (currentCategory == 0) {
			return reading.get(categoryIndex);
		} else if (currentCategory == 1) {
			return toRead.get(categoryIndex);
		}
		return haveRead.get(categoryIndex);
	}

	/**
	 * Gives the book title at the specified index.
	 *
	 * @param categoryIndex The index of the book within it's current category not overall category.
	 * @return Book Title of the book at categoryIndex.
	 */
	static String getBookTitle(int categoryIndex) {
		return titles.get(getIndex(categoryIndex));
	}

	/**
	 * Gives the books in the currently selected Category.
	 *
	 * @return ArrayList of Indexes for books in the current category.
	 */
	static ArrayList<Integer> getCatBooks() {
		if (currentCategory == 0) {
			if (reading == null) {
				return new ArrayList<>();
			}
			return reading;
		} else if (currentCategory == 1) {
			if (toRead == null) {
				return new ArrayList<>();
			}
			return toRead;
		} else if (currentCategory == 2) {
			if (haveRead == null) {
				return new ArrayList<>();
			}
			return haveRead;
		}
		return null;
	}

	/**
	 * Gives the books in the specified Category.
	 *
	 * @param category Chooses the category. 0 is reading, 1 is toRead and 2 is haveRead.
	 * @return ArrayList of Indexes for books in the specified category.
	 */
	@NonNull
	static ArrayList<Integer> getCatBooks(int category) {
		if (category == 0) {
			if (reading == null) {
				return new ArrayList<>();
			}
			return reading;
		} else if (category == 1) {
			if (toRead == null) {
				return new ArrayList<>();
			}
			return toRead;
		} else if (category == 2) {
			if (haveRead == null) {
				return new ArrayList<>();
			}
			return haveRead;
		}
		return new ArrayList<>();
	}

	/**
	 * @return ArrayList of Current Titles
	 */
	static ArrayList<String> getTitles() {return titles;}

	static void setTitles(ArrayList<String> newTitles) {
		titles = newTitles;
	}

	/**
	 * @return ArrayList of Current Authors
	 */
	static ArrayList<String> getAuthors() {
		return authors;
	}

	static void setAuthors(ArrayList<String> newAuthors) {
		authors = newAuthors;
	}

	/**
	 * @return ArrayList of Current Notes
	 */
	static ArrayList<String> getNotes() {
		return notes;
	}

	static void setNotes(ArrayList<String> newNotes) {
		notes = newNotes;
	}

	/**
	 * @return ArrayList of Current start dates.
	 */
	static ArrayList<String> getStart() {
		return start;
	}

	static void setStart(ArrayList<String> newStart) {
		start = newStart;
	}

	/**
	 * @return ArrayList of Current finish dates.
	 */
	static ArrayList<String> getFinish() {
		return finish;
	}

	static void setFinish(ArrayList<String> newFinish) {
		finish = newFinish;
	}

	/**
	 * @return ArrayList of Current Pages for books on the bookshelf.
	 */
	static ArrayList<String> getCurrentPage() {
		return currentPage;
	}

	static void setCurrentPage(ArrayList<String> newCurrentPage) {currentPage = newCurrentPage;}

	/**
	 * Function to retrieve info of the book stored at the provided index.
	 *
	 * @param index The index of the desired book.
	 * @return an ArrayList containing all relevant info on the desired book.
	 * in the format {Title, Author, Notes, Start, Finish, Page}
	 */
	static ArrayList<String> getBook(int index) {
		ArrayList<String> book = new ArrayList<>(6);
		if (titles.size() > 0) {
			book.add(titles.get(index));
			book.add(authors.get(index));
			book.add(notes.get(index));
			book.add(start.get(index));
			book.add(finish.get(index));
			book.add(currentPage.get(index));
		}
		return book;
	}

	/**
	 * Ensures ArrayLists are never null. Check is only validated against title
	 * as All other info would be useless without the title.
	 */
	static void checkNull() {
		try {
			titles.size();
		} catch (NullPointerException e) {
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

	/**
	 * Removes index from it's current category.
	 *
	 * @param index to be removed.
	 */
	private static void removeFromCategory(int index) {
		int d;
		if (reading.contains(index)) {
			d = reading.indexOf(index);
			reading.remove(d);
		} else if (toRead.contains(index)) {
			d = toRead.indexOf(index);
			toRead.remove(d);
		} else if (haveRead.contains(index)) {
			d = haveRead.indexOf(index);
			haveRead.remove(d);
		}
	}

	/**
	 * De-increments all indexes bigger than the given index.
	 * Intended for use when an index has been deleted and the
	 * remaining indexes need corrections.
	 *
	 * @param index The index to correct from.
	 */
	private static void correctIndex(int index) {
		int value;
		for (int n = 0; n < reading.size(); n++) {
			value = reading.get(n);
			if (value > index) {
				reading.set(n, value - 1);
			}
		}
		for (int n = 0; n < toRead.size(); n++) {
			value = toRead.get(n);
			if (value > index) {
				toRead.set(n, value - 1);
			}
		}
		for (int n = 0; n < haveRead.size(); n++) {
			value = haveRead.get(n);
			if (value > index) {
				haveRead.set(n, value - 1);
			}
		}
	}

	/**
	 * Determines the category to place the book under.
	 *
	 * @param startDate Date the book was started on.
	 * @param endDate   Date the book was finished on.
	 * @return 0 for now reading, 1 for toRead and 2 for haveRead.
	 */
	private static int detCategory(String startDate, String endDate) {
		if (endDate.length() > 0) {
			return 2;
		}
		if (startDate.length() > 0) {
			return 0;
		}
		return 1;
	}
}
