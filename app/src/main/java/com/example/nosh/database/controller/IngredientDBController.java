package com.example.nosh.database.controller;

import android.util.Log;

import com.example.nosh.entity.Hashable;
import com.example.nosh.entity.Ingredient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;


public class IngredientDBController extends DBController {

    static final String REF_NAME = "ingredient_storage";

    IngredientDBController(CollectionReference ref) {
        super(ref);
    }

    @Override
    public void create(Hashable o) {
        assertType(o);

        ref.document(o.getHashcode())
                .set(o)
                .addOnSuccessListener(unused ->
                        Log.i("CREATE", "DocumentSnapshot written with ID: " +
                                o.getHashcode()))
                .addOnFailureListener(e ->
                        Log.w("CREATE", "Error adding document", e));
    }

    @Override
    public Object retrieve(Hashable o) {
        assertType(o);

        return null;
    }

    @Override
    public void retrieve() {
        ref
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Ingredient[] ingredients =
                                new Ingredient[task.getResult().size()];

                        int i = 0;
                        for (DocumentSnapshot doc :
                                task.getResult()) {
                            ingredients[i] = doc.toObject(Ingredient.class);
                            ingredients[i++].setHashcode(doc.getId());
                        }

                        setChanged();
                        notifyObservers(ingredients);
                    } else {
                        Log.w("RETRIEVE", "Cached get failed: ",
                                task.getException());
                    }
                });
    }

    @Override
    public void update(Hashable o) {
        assertType(o);

        Ingredient ingredient = (Ingredient) o;

        DocumentReference doc = ref.document(ingredient.getHashcode());

        doc
                .update(
                "inStorage", ingredient.isInStorage(),
                        "bestBeforeDate", ingredient.getBestBeforeDate(),
                        "unit", ingredient.getUnit(),
                        "amount", ingredient.getAmount(),
                        "category", ingredient.getCategory(),
                        "description", ingredient.getDescription(),
                        "location", ingredient.getLocation(),
                        "name", ingredient.getName()
                )
                .addOnSuccessListener(unused ->
                        Log.i("UPDATE", "DocumentSnapshot " +
                                ingredient.getHashcode() + "successfully updated"))
                .addOnFailureListener(e
                        -> Log.w("UPDATE", "Error updating document", e));
    }

    @Override
    public void delete(Hashable o) {
        assertType(o);

        ref.document(o.getHashcode())
                .delete()
                .addOnSuccessListener(unused ->
                        Log.i("REMOVE", "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e ->
                        Log.w("REMOVE", "Error deleting document", e));
    }

    private void assertType(Object o) {
        assert o instanceof Ingredient;
    }
}
