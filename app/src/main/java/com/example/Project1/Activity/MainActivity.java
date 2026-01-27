package com.example.Project1.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.Project1.Model.Location;
import com.example.Project1.R;
import com.example.Project1.databinding.ActivityMainBinding;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private int adultPassenger = 1, childPassenger = 1;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
    private Calendar calendar = Calendar.getInstance();
    private FirebaseAuth auth;
    private DatabaseReference database;
    private ImageView logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        checkFirstTimeUser();
        checkUserAuthentication();

        displayUserName();
        setupLogoutButton();

        initLocations();
        initPassengers();
        initClassSeat();
        initDatePickup();
        setVariable();
    }

    private void checkFirstTimeUser() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean("isFirstTime", true);

        if (isFirstTime) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            startActivity(new Intent(MainActivity.this, IntroActivity.class));
            finish();
        }
    }

    private void checkUserAuthentication() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            finish();
        }
    }

    private void displayUserName() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userName = user.getDisplayName();
            if (userName != null && !userName.isEmpty()) {
                binding.textView4.setText("Welcome, " + userName + "!");
            } else {
                binding.textView4.setText("Welcome, Guest!");
            }
        } else {
            binding.textView4.setText("Welcome, Guest!");
        }
    }

    private void setupLogoutButton() {
        logoutButton = findViewById(R.id.imageView4);
        logoutButton.setOnClickListener(view -> logoutUser());
    }
    private void initLocations() {
        binding.progressBarFrom.setVisibility(View.VISIBLE);
        binding.progressBarTo.setVisibility(View.VISIBLE);
        DatabaseReference myRef = database.child("Locations");
        ArrayList<Location> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.fromSp.setAdapter(adapter);
                    binding.toSp.setAdapter(adapter);
                    binding.fromSp.setSelection(1);
                    binding.progressBarFrom.setVisibility(View.GONE);
                    binding.progressBarTo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error loading locations", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPassengers() {
        binding.plusAdultBtn.setOnClickListener(v -> {
            adultPassenger++;
            binding.AdultTxt.setText(adultPassenger + " Adult");
        });

        binding.minusAdultBtn.setOnClickListener(v -> {
            if (adultPassenger > 1) {
                adultPassenger--;
                binding.AdultTxt.setText(adultPassenger + " Adult");
            }
        });

        binding.plusChildBtn.setOnClickListener(v -> {
            childPassenger++;
            binding.childTxt.setText(childPassenger + " Child");
        });

        binding.minusChildBtn.setOnClickListener(v -> {
            if (childPassenger > 0) {
                childPassenger--;
                binding.childTxt.setText(childPassenger + " Child");
            }
        });
    }

    private void initClassSeat() {
        binding.progressBarClass.setVisibility(View.VISIBLE);
        ArrayList<String> list = new ArrayList<>();
        list.add("Business Class");
        list.add("First Class");
        list.add("Economy Class");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.classSp.setAdapter(adapter);
        binding.progressBarClass.setVisibility(View.GONE);
    }


    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, SignUpActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    private void setVariable() {
        binding.searchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("from", ((Location) binding.fromSp.getSelectedItem()).getName());
            intent.putExtra("to", ((Location) binding.toSp.getSelectedItem()).getName());
            intent.putExtra("date", binding.departureDateTxt.getText().toString());
            intent.putExtra("numPassenger", adultPassenger + childPassenger);
            startActivity(intent);
        });
    }

    private void initDatePickup() {
        Calendar calendarToday = Calendar.getInstance();
        String currentDate = dateFormat.format(calendarToday.getTime());
        binding.departureDateTxt.setText(currentDate);

        Calendar calendarTomorrow = Calendar.getInstance();
        calendarTomorrow.add(Calendar.DAY_OF_YEAR, 1);
        String tomorrowDate = dateFormat.format(calendarTomorrow.getTime());
        binding.returnDateTxt.setText(tomorrowDate);

        binding.departureDateTxt.setOnClickListener(v -> showDatePickerDialog(binding.departureDateTxt));
        binding.returnDateTxt.setOnClickListener(v -> showDatePickerDialog(binding.returnDateTxt));
    }

    private void showDatePickerDialog(TextView textView) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            calendar.set(selectedYear, selectedMonth, selectedDay);
            String formattedDate = dateFormat.format(calendar.getTime());
            textView.setText(formattedDate);
        }, year, month, day);
        datePickerDialog.show();
    }
}
