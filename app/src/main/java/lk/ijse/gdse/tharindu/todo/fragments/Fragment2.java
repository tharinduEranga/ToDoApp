package lk.ijse.gdse.tharindu.todo.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import lk.ijse.gdse.tharindu.todo.R;

public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, viewGroup, false);
        TextView output= view.findViewById(R.id.msg2);
        output.setText(getString(R.string.frag_two));
        return view;
    }

}