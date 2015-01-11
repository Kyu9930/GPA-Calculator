package me.ashkan.gpacalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;


public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        setSpinners(rootView);

        return rootView;
    }

    public void setSpinners(View rootView) {
        Spinner percentageSpinner = (Spinner) rootView.findViewById(R.id.weight);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.percentage_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        percentageSpinner.setAdapter(adapter);

        Spinner weightSpinner = (Spinner) rootView.findViewById(R.id.percentage);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.weight_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        weightSpinner.setAdapter(adapter);


        String[] data = {
                "POL344 - 4.0",
                "JAL355 - 4.0",
                "UNI268 - 4.0",
                "WGS360 - 3.7",
                "WGS350 - 2.7",
                "POL322 - 4.0",
                "POL344 - 4.0",
                "POL450 - 4.0",
                "MAT257 - 3.0",
                "CSC165 - 4.0"
        };
        ListView mainListView = (ListView) rootView.findViewById(R.id.inputted_grades);
        ArrayList<String> gpaData = new ArrayList<String>(Arrays.asList(data));
        ArrayAdapter<String> mGPAList = new ArrayAdapter<String>(
                getActivity(), // The current context (this activity)
                R.layout.list_item_layout, // The name of the layout ID.
                R.id.list_item_layout_gpa, // The ID of the textview to populate.
                gpaData);
        mainListView.setAdapter(mGPAList);

    }
}
