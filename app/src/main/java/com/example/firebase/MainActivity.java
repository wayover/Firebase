package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.model.City;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG;
    EditText editText;
    Button bCity;
    Button bProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
//        addAda();
        addCustonCity();
//        getAllUsers();
        customObjects();

        bCity = findViewById(R.id.bCity);
        bCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CityActivity.class);
                startActivity(intent);
            }
        });

        bProduct= findViewById(R .id.bProduct);
        bProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecycleProductActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addAda() {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        pokazToast("DocumentSnapshot added with ID: " + documentReference.getId());
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pokazToast("Error adding document" + e.getMessage());
                    }
                });
    }

    void pokazToast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG);
        toast.show();
    }

    public void getAllUsers(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                editText.setText("Dane z kolekcji \n");
                                editText.append(document.getId() + " " + document.getData() + "\n");
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                            editText.append("Error getting documents."+ task.getException());
                        }
                    }
                });
    }

    public void addCustonCity(){
        City city = new City("Los Angeles", "CA", "USA", false, 5000000, Arrays.asList("West Coast", "Sorcal"));
        db.collection("Cities").document("LA").set(city);
        City city2 = new City("Lodz", "Lodzkie", "PL", false, 700000, Arrays.asList("Baluty", "Widzew", "Polesie"));
        db.collection("Cities").document("EL").set(city2);
    }

    public void customObjects() {
        // [START custom_objects]
        DocumentReference docRef = db.collection("Cities").document("EL");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                City city = documentSnapshot.toObject(City.class);
                editText.setText("Miasto " + documentSnapshot.getData());
            }
        });
        // [END custom_objects]
    }

}




