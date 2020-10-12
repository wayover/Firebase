package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.firebase.model.City;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class CityActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore db;

    Button bDodaj, bEdytuj;
    EditText etName, etId, etState, etCountry, etRegions, etPopulation;
    Switch switchCapital;
    String idCity, name, state, country, txtPopulation, txtRegions;
    long population;
    boolean capital;
    public static final String CITIES_COLLECTION = "Cities";
    List<String> regions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        db = FirebaseFirestore.getInstance();

        etId = findViewById(R.id.etDocId);
        etName = findViewById(R.id.etName);
        etState = findViewById(R.id.etState);
        etCountry = findViewById(R.id.etCountry);
        etPopulation = findViewById(R.id.etPopulation);
        etRegions = findViewById(R.id.mtRegions);

        switchCapital = findViewById(R.id.swCapital);

        bDodaj = findViewById(R.id.bAdd);
        bEdytuj = findViewById(R.id.bEdit);

        bEdytuj.setOnClickListener(this);
        bDodaj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bEdit:
                edytujCity();
                break;
            case R.id.bAdd:
                idCity = etId.getText().toString().trim();
                name = etName.getText().toString().trim();
                state = etState.getText().toString().trim();
                country = etCountry.getText().toString().trim();
                txtPopulation = etPopulation.getText().toString().trim();
                population = new Long(txtPopulation);
                capital = switchCapital.isChecked();
                txtRegions = etRegions.getText().toString().trim();
                String[] tabRegions = txtRegions.split(",");
                for (int i = 0; i < tabRegions.length; i++) {
                    tabRegions[i] = tabRegions[i].trim();
                }
                regions = Arrays.asList(tabRegions);
                City cityDodaj = new City(name, state, country, capital, population, regions);
                db.collection(CITIES_COLLECTION).document(idCity).set(cityDodaj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pokazToast("UDALO SIE");
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pokazToast("FAIL LESZCZU");

                    }
                });
        }
    }

    public void edytujCity() {
        idCity = etId.getText().toString().trim();
        DocumentReference drCity = db.collection(CITIES_COLLECTION).document(idCity);

        drCity.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                City city = documentSnapshot.toObject(City.class);
                etName.setText(city.getName());
                etState.setText(city.getState());
                etCountry.setText(city.getCountry());
                etPopulation.setText(String.valueOf(city.getPopulation()));
                switchCapital.setChecked(city.isCapital());
                regions = city.getRegions();
                etRegions.setText(regions.toString());
            }
        });
    }

    void pokazToast(String txt) {
        Toast toast = Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG);
        toast.show();
    }
}