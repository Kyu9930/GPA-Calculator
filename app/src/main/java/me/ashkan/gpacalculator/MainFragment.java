package me.ashkan.gpacalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    private int weightFactor = 0;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> mGPAListAdapter;
    private final String TAG = "MainFragment";


    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

        // Do not allow the keyboard to popup until the user clicks on the EditText
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        final Spinner gpaWeightSpinner = (Spinner) rootView.findViewById(R.id.weight);
        final Spinner gpaPercentageSpinner = (Spinner) rootView.findViewById(R.id.percentage);
        final TextView courseCodeTextView = (TextView) rootView.findViewById(R.id.editText);
        final TextView gpaTextView = (TextView) rootView.findViewById(R.id.GPA_text_view);
        final ListView myCoursesListView = (ListView) rootView.findViewById(R.id.inputted_grades);


        // Set the values for the spinners
        setSpinners(rootView);


        listItems = new ArrayList<String>();
        mGPAListAdapter = new ArrayAdapter<String>(
                getActivity(), // The current context (this activity)
                R.layout.list_item_layout, // The name of the layout ID.
                R.id.list_item_layout_gpa, // The ID of the textview to populate.
                listItems);
        myCoursesListView.setAdapter(mGPAListAdapter);


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


                gpaTextView.setText("GPA: " + String.valueOf(updateGpa(gpaValue, currentGpa, gpaWeight)));

                // Add the new course to the list and update the Spinner
                listItems.add(textToAdd);
                mGPAListAdapter.notifyDataSetChanged();

                // Clear the EditText text
                courseCodeTextView.setText("");

                for (String temp : listItems)
                    Log.i(TAG, "INDEX: " + listItems.indexOf(temp) + " ITEM: " + temp);

            }
        });

        //SwipeDismissListViewTouchListener code by Roman Nurik:
        // https://github.com/romannurik/Android-SwipeToDismiss
        // Create a ListView-specific touch listener. ListViews are given special treatment because
        // by default they handle touches for their list items... i.e. they're in charge of drawing
        // the pressed state (the list selector), handling list item clicks, etc.
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        myCoursesListView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    try {
                                        Log.i(TAG, "ListView Item Position: " + position);
                                        Log.i(TAG, "ListView Item: " + mGPAListAdapter.getItem(position));
                                        mGPAListAdapter.remove(mGPAListAdapter.getItem(position));
                                    } catch (Exception e) {
                                        //Temporary fix to SwipeDismissListViewTouchListener
                                        // index of out bounds exception error.
                                        Log.i(TAG, "HAHA, Avoided the crash!");
                                        Log.i(TAG, e.toString());
                                    }
                                }
                                mGPAListAdapter.notifyDataSetChanged();
                            }
                        });
        myCoursesListView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        myCoursesListView.setOnScrollListener(touchListener.makeScrollListener());


        return rootView;
    }

    /**
     * Set the values for both the percentage and weight Spinners
     *
     * @param rootView The fragment rootView
     */
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


    /**
     * Update the GPA given a new GPA value and course weight
     *
     * @param gpaValue   The GPA value of the given course
     * @param currentGpa The current GPA
     * @param gpaWeight  The weight of the course (0.5 or 1.0)
     * @return the current GPA with the next GPA value added (rounded to 2 decimal places)
     */
    public double updateGpa(double gpaValue, double currentGpa, double gpaWeight) {
        double tempGrade = (gpaValue * gpaWeight * 2) + (currentGpa * weightFactor);

        if (gpaWeight == 0.5)
            weightFactor++;
        else if (gpaWeight == 1.0)
            weightFactor += 2;
        return (Math.round((tempGrade / weightFactor) * 100.0) / 100.0);

    }
}
