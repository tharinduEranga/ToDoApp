package lk.ijse.gdse.tharindu.todo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import lk.ijse.gdse.tharindu.todo.db.LiteDBHelper;
import lk.ijse.gdse.tharindu.todo.db.LoginAuthHandleStatements;

import static lk.ijse.gdse.tharindu.todo.db.LoginAuthHandleStatements.LOGIN_PASSWORD;
import static lk.ijse.gdse.tharindu.todo.db.LoginAuthHandleStatements.LOGIN_USERNAME;

/**
 * The activity represent login and registration functions
 */
public class LogNRegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_nreg);
        Objects.requireNonNull(getSupportActionBar()).hide();
        loginBtnAction();
    }

    /**
     * This function is override for handling back button exit event for current activity.
     *
     * @param keyCode triggered key code from key event
     * @param event   the key event
     * @return the boolean status by super
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishAffinity();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void loginBtnAction() {
        MaterialButton loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        TextInputEditText usernameTxt = findViewById(R.id.usernameTxt);
        TextInputEditText passwordTxt = findViewById(R.id.passwordTxt);

        String username = String.valueOf(usernameTxt.getText());
        String password = String.valueOf(passwordTxt.getText());

        if (username.equalsIgnoreCase("") || !username.equals(LOGIN_USERNAME)) {
            setEditTextLineColorAndFocus(usernameTxt, Color.RED);
            return;
        }
        if (password.equalsIgnoreCase("") || !password.equals(LOGIN_PASSWORD)) {
            setEditTextLineColorAndFocus(passwordTxt, Color.RED);
            return;
        }

        // navigating into the given activity
        Intent intent = new Intent(LogNRegActivity.this, MainActivity.class);

        // save data
        LiteDBHelper dbHelper = new LiteDBHelper(LogNRegActivity.this);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LoginAuthHandleStatements.FeedEntry.USERNAME, username);
        values.put(LoginAuthHandleStatements.FeedEntry.PASSWORD, password);
        values.put(LoginAuthHandleStatements.FeedEntry.STATUS, true);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(LoginAuthHandleStatements.FeedEntry.TABLE_NAME, null, values);

        startActivity(intent);
    }

    private void setEditTextLineColorAndFocus(EditText editText, int color) {
//        clickable = true;
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        ViewCompat.setBackgroundTintList(editText, colorStateList);
        editText.requestFocus();
//        showKeyBoard(editText);
    }

    private void showKeyBoard(EditText editText) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }
}
