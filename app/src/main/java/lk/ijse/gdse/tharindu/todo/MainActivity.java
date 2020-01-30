package lk.ijse.gdse.tharindu.todo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import lk.ijse.gdse.tharindu.todo.db.LiteDBHelper;
import lk.ijse.gdse.tharindu.todo.db.LoginAuthHandleStatements;

import static lk.ijse.gdse.tharindu.todo.db.LoginAuthHandleStatements.LOGIN_USERNAME;

/**
 * the default home activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOutBtnAction();

        Cursor cursor = checkUserInDb();
        checkAuth(cursor);


//        Button frag_btn = findViewById(R.id.frag_btn);
//        frag_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, FragmentActivity.class);
//                startActivity(intent);
//            }
//        });

        ScrollView scrollView = findViewById(R.id.scollView);
        LinearLayout linearLayout = findViewById(R.id.scrollLinear);

        Button[] buttons = new Button[20];

//        RelativeLayout relativeLayout = new RelativeLayout(MainActivity.this);

        int sum = 0;
        for (int i = 1; i < 15; i++) {
            buttons[i] = new Button(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin = sum;
            buttons[i].setText("Button " + i);
            buttons[i].setLayoutParams(params);
            linearLayout.addView(buttons[i]);
            sum = sum + 100;
        }

    }

    private void logOutBtnAction() {
        MaterialButton logoutBtn = findViewById(R.id.log_out_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private Cursor checkUserInDb() {
        LiteDBHelper dbHelper = new LiteDBHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                LoginAuthHandleStatements.FeedEntry.USERNAME,
                LoginAuthHandleStatements.FeedEntry.PASSWORD,
                LoginAuthHandleStatements.FeedEntry.STATUS
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = LoginAuthHandleStatements.FeedEntry.USERNAME + " = ?";
        String[] selectionArgs = {LOGIN_USERNAME};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                LoginAuthHandleStatements.FeedEntry._ID + " DESC";

        return db.query(
                LoginAuthHandleStatements.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
    }

    private void checkAuth(Cursor cursor) {
        List<String> userNames = new ArrayList<>();
        if (cursor.moveToNext()) {
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            int status = cursor.getInt(3);
            userNames.add(username);

            Toast.makeText(this,
                    String.format("%s\n\n%s\n\n%s", username, password, status),
                    Toast.LENGTH_SHORT).show();
        }

        System.out.println(userNames + "\n" + cursor.getCount());
        cursor.close();

        if (userNames.isEmpty() || !userNames.contains(LOGIN_USERNAME)) {
            goToLogin();
        }
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

    private void logout() {
        // DELETE data
        LiteDBHelper dbHelper = new LiteDBHelper(MainActivity.this);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = LoginAuthHandleStatements.FeedEntry.USERNAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {LOGIN_USERNAME};
        // Issue SQL statement.
        int deletedRows = db.delete(LoginAuthHandleStatements.FeedEntry.TABLE_NAME, selection, selectionArgs);
        goToLogin();
    }

    private void goToLogin() {
        Intent intent = new Intent(MainActivity.this, LogNRegActivity.class);
        startActivity(intent);
    }
}
