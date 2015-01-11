package me.ashkan.gpacalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainFragment extends Fragment {
    public int weightFactor = 0;
    ArrayList<String> listItems;
    ArrayAdapter<String> mGPAList;

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

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        final Spinner gpaWeightSpinner = (Spinner) rootView.findViewById(R.id.weight);
        final Spinner gpaPercentageSpinner = (Spinner) rootView.findViewById(R.id.percentage);
        final TextView courseCodeTextView = (TextView) rootView.findViewById(R.id.editText);
        final TextView gpaTextView = (TextView) rootView.findViewById(R.id.GPA_text_view);
        final ListView myCoursesSpinner = (ListView) rootView.findViewById(R.id.inputted_grades);


        setSpinners(rootView);

        Button submitButton = (Button) rootView.findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String courseCode = courseCodeTextView.getText().toString();
                double gpaWeight = Double.parseDouble(gpaWeightSpinner.getSelectedItem().toString());
                String gpaPercentage = gpaPercentageSpinner.getSelectedItem().toString();
                double gpaValue = findGpaValue(gpaPercentage);
                double currentGpa = Double.parseDouble(gpaTextView.getText().toString().substring(5));
                String textToAdd = courseCode + " - " + gpaValue;
                Log.i("INFORMATION", textToAdd + " " + gpaWeight);


                gpaTextView.setText("GPA: " + String.valueOf(updateGpa(gpaValue, currentGpa, gpaWeight)));

                listItems.add(textToAdd);
                mGPAList.notifyDataSetChanged();

                courseCodeTextView.setText("");
            }
        });


        return rootView;
    }

    public void setSpinners(View rootView) {
        Spinner percentageSpinner = (Spinner) rootView.findViewById(R.id.percentage);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.percentage_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        percentageSpinner.setAdapter(adapter);

        Spinner weightSpinner = (Spinner) rootView.findViewById(R.id.weight);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.weight_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        weightSpinner.setAdapter(adapter);


        ListView mainListView = (ListView) rootView.findViewById(R.id.inputted_grades);
        listItems = new ArrayList<String>();
        mGPAList = new ArrayAdapter<String>(
                getActivity(), // The current context (this activity)
                R.layout.list_item_layout, // The name of the layout ID.
                R.id.list_item_layout_gpa, // The ID of the textview to populate.
                listItems);
        mainListView.setAdapter(mGPAList);

    }

    /**
     * @param givenPercentage The mark received given in a percentage range from 0 - 100
     * @return the GPA version of the mark (0 - 4.0)
     */
    public double findGpaValue(String givenPercentage) {
        double[] gpaArray = {4.0, 4.0, 3.7, 3.3, 3.0, 2.7, 2.3, 2.0, 1.7, 1.3, 1.0, 0.7, 0.0};
        String[] percentageArray = {"90–100", "85–89", "80–84", "77–79", "73–76", "70–72", "67–69", "63–66", "60–62", "57–59", "53–56", "50-52", "0–49"};
        for (int index = 0; index < gpaArray.length; index++)
            if (givenPercentage.equals(percentageArray[index]))
                return gpaArray[index];

        return -1;
    }


    public double updateGpa(double gpaValue, double currentGpa, double gpaWeight) {
        double tempGrade = (currentGpa * gpaWeight * 2) + (gpaValue * weightFactor);

        if (gpaWeight == 0.5)
            weightFactor++;
        else if (gpaWeight == 1.0)
            weightFactor += 2;

        return (tempGrade / weightFactor);

    }
}
