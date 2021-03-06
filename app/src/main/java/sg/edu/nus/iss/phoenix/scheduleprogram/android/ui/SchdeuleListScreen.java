package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;


/**
 * Created by thushara on 9/26/2017.
 */

public class SchdeuleListScreen extends AppCompatActivity  {
    //tag for logging
    private static final String TAG = SchdeuleListScreen.class.getName();
    private ListView mListView;
    private ScheduleAdapter mScheduleAdapter;
    private ProgramSlot selectedRP = null;
    private boolean scheduleList = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        // mRPNameEditText = (EditText) findViewById(R.id.maintain_program_name_text_view);
        // mRPDescEditText = (EditText) findViewById(R.id.maintain_program_desc_text_view);
        // mDurationEditText = (EditText) findViewById(R.id.maintain_program_duration_text_view);

        scheduleList = getIntent().getExtras().getBoolean("copy");
        ArrayList<ProgramSlot> slotsPrograms = new ArrayList<ProgramSlot>();
        mScheduleAdapter = new ScheduleAdapter(this, slotsPrograms);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreateSchedule);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getScheduleController().selectCreateSchedule();
            }
        });

        mListView = (ListView) findViewById(R.id.schedule_list);
        mListView.setAdapter(mScheduleAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Log.v(TAG, "Radio program at position " + position + " selected.");
                ProgramSlot programSlot = (ProgramSlot) adapterView.getItemAtPosition(position);
                // Log.v(TAG, "Radio program name is " + rp.getRadioProgram());
                selectedRP = programSlot;

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // yettowrite
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);
        ControlFactory.getScheduleController().onDisplayProgramList(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (scheduleList == true) {
            MenuItem menuItem = menu.findItem(R.id.action_view);
            menuItem.setVisible(false);
        }else {
            MenuItem menuItemCopy = menu.findItem(R.id.action_copy_button);
            menuItemCopy.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_view:
                if (selectedRP == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a  schedule first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected  schedule.");
                }
                else {
                    Log.v(TAG, "Viewing schedule: " + selectedRP.getRadioProgram() + "...");
                    ControlFactory.getScheduleController().selectEditSchedule(selectedRP);
                }
            case R.id.action_copy_button:
                if (selectedRP == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a  schedule first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected  schedule.");
                }
                else {
                    Log.v(TAG, "Viewing schedule: " + selectedRP.getRadioProgram() + "...");
                    ControlFactory.getScheduleController().selectEditSchedule(selectedRP);
                }
        }

        return true;
    }

    @Override
    public void onBackPressed() {ControlFactory.getScheduleController().maintainedSchedule();}

    public void showSchedule(List<ProgramSlot> programSlots) {
        mScheduleAdapter.clear();
        for (int i = 0; i < programSlots.size(); i++) {
            mScheduleAdapter.add(programSlots.get(i));
        }
    }

}
