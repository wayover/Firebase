package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.firebase.model.City;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CityActivity extends AppCompatActivity implements View.OnClickListener {


    Button bDodaj,bEdytuj;
    EditText etName,etState,etCountry,etRegions,etPopulation,etId;
    Switch switchCapital;
    FirebaseFirestore db;
    String idCity;
    public static final String CITIES_COLLECTION = "Cities";
    List<String> regions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        db = FirebaseFirestore.getInstance();
        etId = findViewById(R.id.etID);
        etName = findViewById(R.id.etName);
        etState = findViewById(R.id.etState);
        etCountry = findViewById(R.id.etCountry);
        etPopulation = findViewById(R.id.etPopulation);
        etRegions = findViewById(R.id.etRegions);

        switchCapital = findViewById(R.id.sCapital);

        bDodaj = findViewById(R.id.bDodaj);
        bEdytuj = findViewById(R.id.bEdytuj);



        bEdytuj.setOnClickListener(this);
        bDodaj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.bEdytuj:
                edytujCity();

                break;
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
}


