package sg.edu.rp.c346.id21021436.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ModifyMovie extends AppCompatActivity {

    //define variables

    EditText movieId, movieTitle, genre, year, rating;
    Button btnUpdate, btnDelete, btnDeleteAll, btnCancel;
    ArrayList<Movie> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_movie);

        movieId = findViewById(R.id.etMovieID);
        movieTitle = findViewById(R.id.etMovieTitle);
        genre = findViewById(R.id.etGenre);
        year = findViewById(R.id.etYear);
        rating = findViewById(R.id.etRating);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnCancel = findViewById(R.id.btnCancel);

        al = new ArrayList<Movie>();


        Intent intentReceivedFromSecond = getIntent();
        Movie moviePicked = (Movie) intentReceivedFromSecond.getSerializableExtra("moviePicked");

        movieId.setText(moviePicked.get_id() + "");
        //to prevent editing of the movieId, set focusable and clickable to false
        movieId.setFocusable(false);
        movieId.setClickable(false);
        movieTitle.setText(moviePicked.getTitle() + "");
        genre.setText(moviePicked.getGenre() + "");
        year.setText(moviePicked.getYear() + "");
        rating.setText(moviePicked.getRating() + "");


        DBHelper dbh = new DBHelper(ModifyMovie.this);
        btnUpdate.setOnClickListener(v -> {

            int resultUpdate = dbh.updateMovie(moviePicked, movieTitle.getText().toString(), genre.getText().toString(), Integer.parseInt(year.getText().toString()), rating.getText().toString());
            if (resultUpdate != -1) Toast.makeText(ModifyMovie.this, "Update successful", Toast.LENGTH_SHORT).show();

            al.clear();
            al.addAll(dbh.getAllMovies());
            Intent intentGoingBackToShowMovies = new Intent(ModifyMovie.this, ShowMovies.class);
            intentGoingBackToShowMovies.putExtra("movieList", al);
            startActivity(intentGoingBackToShowMovies);
            finish();

        });

        btnDelete.setOnClickListener(v -> {
            int resultDelete = dbh.deleteMovie(moviePicked);
            if (resultDelete != -1) Toast.makeText(ModifyMovie.this, "Delete successful", Toast.LENGTH_SHORT).show();

            al.clear();
            al.addAll(dbh.getAllMovies());
            Intent intentGoingBackToShowMovies = new Intent(ModifyMovie.this, ShowMovies.class);
            intentGoingBackToShowMovies.putExtra("movieList", al);
            startActivity(intentGoingBackToShowMovies);
            finish();
        });

        btnDeleteAll.setOnClickListener(v -> {
            dbh.deleteAllMovies();
            Toast.makeText(ModifyMovie.this, "All Movies Deleted!", Toast.LENGTH_SHORT).show();

            al.clear();
            al.addAll(dbh.getAllMovies());
            Intent intentGoingBackToShowMovies = new Intent(ModifyMovie.this, ShowMovies.class);
            intentGoingBackToShowMovies.putExtra("movieList", al);
            startActivity(intentGoingBackToShowMovies);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            al.clear();
            al.addAll(dbh.getAllMovies());
            Intent intentGoingBackToShowMovies = new Intent(ModifyMovie.this, ShowMovies.class);
            intentGoingBackToShowMovies.putExtra("movieList", al);
            startActivity(intentGoingBackToShowMovies);
            finish();

        });

    }
}