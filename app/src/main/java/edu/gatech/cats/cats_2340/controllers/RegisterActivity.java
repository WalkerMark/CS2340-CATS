package edu.gatech.cats.cats_2340.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.gatech.cats.cats_2340.R;

import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.cats.cats_2340.model.Model;
import edu.gatech.cats.cats_2340.model.User;

import android.view.*;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText nameText;
    private EditText userText;
    private EditText passText;
    private Spinner userTypeLabel;
    private boolean nameEdit;
    private boolean userEdit;
    private boolean passEdit;
    // Keeps track of spinner changes
    private String _type = "Unselected";
    boolean isAdmin = false;

    /**
     * Overriden on-create sets the view to the application view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameText = (EditText) findViewById(R.id.nameField);
        userText = (EditText) findViewById(R.id.userID);
        passText = (EditText) findViewById(R.id.password);
        userTypeLabel = (Spinner) findViewById(R.id.userTypeField);

        nameEdit = false;
        userEdit = false;
        passEdit = false;

        TextView failedRegisterText = (TextView) findViewById(R.id.invalidSubmit);
        failedRegisterText.setVisibility(View.INVISIBLE);

        // Set up adapter to display the allowable types inside the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, User.type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeLabel.setAdapter(adapter);

        // Clears name entry when first clicked on
        nameText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!nameEdit) {
                    nameText.setText("");
                    nameEdit = true;
                } else if (!nameText.getText().toString().equals("")) {
                    userText.requestFocus();
                    userText.setText("");
                    userEdit = true;
                }
            }
        });
        // Clears user entry when first clicked on
        userText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!userEdit) {
                    userText.setText("");
                    userEdit = true;
                } else if (!userText.getText().toString().equals("")) {
                    passText.requestFocus();
                    passText.setText("");
                    passEdit = true;

                }
            }
        });
        // Clears pass entry when first clicked on
        passText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!passEdit) {
                    passText.setText("");
                    passEdit = true;
                }
                else if (!userText.getText().toString().equals("")) {
                    userTypeLabel.requestFocus();
                }
            }
        });
    }

    /**
     * Adds a new user with submitted data to the list of users. Will most likely update with
     * database functionality soon
     * @param view
     */
    public void onRegisterPressed(View view) {
        Model model = Model.getInstance();

        String name = nameText.getText().toString();
        String user = userText.getText().toString();
        String pass = passText.getText().toString();

        boolean isAdmin = false;
        if (userTypeLabel.getSelectedItem().equals("Admin")) {
            isAdmin = true;
        }

        if (name.equals("") || user.equals("") || pass.equals("")) {
            TextView failedRegisterText = (TextView) findViewById(R.id.invalidSubmit);
            failedRegisterText.setVisibility(View.VISIBLE);

            return;
        }

        User newUser = new User(name, user, pass, isAdmin);

        model.register(newUser);
        startActivity(new Intent(getBaseContext(),OpeningActivity.class));
        finish();
    }
    /**
     * Goes back to splash screen when the user presses cancel
     * @param view
     */
    public void onCancelPressed(View view) {
        startActivity(new Intent(getBaseContext(),OpeningActivity.class));
        finish();
    }

    /**
     * Informs the application that the item has been selected or not
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        _type = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
