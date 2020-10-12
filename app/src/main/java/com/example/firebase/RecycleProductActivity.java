package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebase.model.Product;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RecycleProductActivity extends AppCompatActivity {

    FirestoreRecyclerAdapter adapter;
    RecyclerView mfirestoreRecycleViewList;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_product);
        mfirestoreRecycleViewList = findViewById(R.id.recycleView);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firebaseFirestore.collection("products").orderBy("price");

        //Recycle Options
        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>().setQuery(query,Product.class).build();



        adapter = new FirestoreRecyclerAdapter<Product,ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
                holder.listPrice.setText(String.valueOf(model.getPrice())+"PLN");
                holder.listName.setText(model.getName());
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_1,parent,false);
                return new ProductViewHolder(view);
            }
        };

        mfirestoreRecycleViewList.setLayoutManager(new LinearLayoutManager(this));
        mfirestoreRecycleViewList.setHasFixedSize(true);
        mfirestoreRecycleViewList.setAdapter(adapter);
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView listName;
        TextView listPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.listName);
            listPrice = itemView.findViewById(R.id.listPrice);
        }



    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
