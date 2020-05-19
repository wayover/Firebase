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

public class MainActivity extends AppCompatActivity {

EditText etWynik;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button bCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
      //  addAdaLovelace();
      //  addAlanTuring();
        etWynik =findViewById(R.id.etWynik);
      //  getAllUsers();
    addCustomClass();
    customObjects();
    bCity=findViewById(R.id.bCity);
    bCity.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),CityActivity.class);
            startActivity(intent);
        }
    });
    }

    public void customObjects() {
        // [START custom_objects]
        DocumentReference docRef = db.collection("cities").document("BJ");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                City city = documentSnapshot.toObject(City.class);
            }
        });
        // [END custom_objects]
    }


    public void addCustomClass() {
        // [START add_custom_class]
        City city = new City("Los Angeles", "CA", "USA",
                false, 5000000L, Arrays.asList("west_coast", "sorcal"));
        db.collection("cities").document("LA").set(city);
        City lodz= new City("Lodz","Lodzkie","Pl",false,700000L,Arrays.asList("Baluty","Góra","Śródmieśie","Patolesie"));
        db.collection("cities").document("EL").set(lodz);
        // [END add_custom_class]
    }


    public void addAdaLovelace(){

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
                       // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        pokazToast("DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Log.w(TAG, "Error adding document", e);
                        pokazToast("Error "+e.getMessage());
                    }
                });
    }

    public void addAlanTuring(){
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Alan");
        user.put("middle", "Mathison");
        user.put("last", "Turing");
        user.put("born", 1912);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        pokazToast("DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Log.w(TAG, "Error adding document", e);
                        pokazToast("Error "+e.getMessage());
                    }
                });


    }



    public void getAllUsers(){



        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            etWynik.setText("Dane z kolekcjii user \n");
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                etWynik.append(document.getId()+ " => "+document.getData()+ "\n");
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                          //  Log.w(TAG, "Error getting documents.", task.getException());
                            pokazToast("Error");
                        }
                    }
                });
    }


    public void pokazToast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG);
        toast.show();

    }
}





