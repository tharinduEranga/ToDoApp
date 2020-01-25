package lk.ijse.gdse.tharindu.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import lk.ijse.gdse.tharindu.todo.fragments.Fragment1;
import lk.ijse.gdse.tharindu.todo.fragments.Fragment2;

public class FragmentActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.view_div, new Fragment1()).commit();

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {

                Fragment fragment = null;
                if(view == findViewById(R.id.button1)){
                    fragment = new Fragment1();
                } else {
                    fragment = new Fragment2();
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.view_div, fragment);

                transaction.commit();
            }
        };
        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(listener);
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(listener);

    }
}
