package lucas.personal.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

public class addBook extends AppCompatActivity {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        tabLayout = findViewById(R.id.tabLayout);

        Button saveBook = findViewById(R.id.saveBook);
        saveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToMain = new Intent(getApplicationContext(), lucas.personal.book.MainActivity.class);
                startActivity(returnToMain);
            }
        });
    }
}
