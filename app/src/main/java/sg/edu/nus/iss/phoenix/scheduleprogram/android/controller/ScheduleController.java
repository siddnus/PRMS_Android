package sg.edu.nus.iss.phoenix.scheduleprogram.android.controller;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.SchdeuleListScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.ModifyScheduleDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.ScheduleProgramScreen;

/**
 * Created by thushara on 9/26/2017.
 */

public class ScheduleController {
    private static final String TAG = ScheduleController.class.getName();
    private SchdeuleListScreen scheduleListScreen;
    private ScheduleProgramScreen scheduleProgramScreen;
    private ProgramSlot sp2edit = null;

    public void startUseCase() {
        sp2edit = null;
        Intent intent = new Intent(MainController.getApp(), SchdeuleListScreen.class);
        MainController.displayScreen(intent);
    }
    public void onDisplayProgramList(SchdeuleListScreen programListScreen) {
        this.scheduleListScreen = programListScreen;
        new RetrieveScheduleDelegate(this).execute("all");
    }
    public void scheduleRetrieved(List<ProgramSlot> programSlotList) {
        scheduleListScreen.showSchedule(programSlotList);
    }
    public void selectCreateSchedule() {
        sp2edit = null;
        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
        MainController.displayScreen(intent);
    }
    public void selectEditSchedule(ProgramSlot programSlot) {
        sp2edit = programSlot;
        Log.v(TAG, "Editing program Slot : " + programSlot.getRadioProgramName() + "...");

        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
/*        Bundle b = new Bundle();
        b.putString("Name", radioProgram.getRadioProgramName());
        b.putString("Description", radioProgram.getRadioProgramDescription());
        b.putString("Duration", radioProgram.getRadioProgramDuration());
        intent.putExtras(b);
*/
        MainController.displayScreen(intent);
    }

    public void onDisplayScheduleProgram(ScheduleProgramScreen scheduleProgramScreen) {
        this.scheduleProgramScreen = scheduleProgramScreen;
        if (sp2edit == null)
            scheduleProgramScreen.createSchedule();
        else
            scheduleProgramScreen.editSchedule(sp2edit);
    }


    public void selectUpdateScheduleProgram(ProgramSlot programSlot) {
        new ModifyScheduleDelegate(this).execute(programSlot);
    }





    public void scheduleUpdated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void selectCreateScheudle(ProgramSlot programSlot) {
        new CreateScheduleDelegate(this).execute(programSlot);
    }

    public void scheduleCreated(boolean success) {
        // Go back to List screen with refreshed programs.
        startUseCase();
    }

    public void selectCancelCreateEditSchedule() {
        // Go back to List screen with refreshed programs.
        startUseCase();
    }

    public void ScheduleProgram() {
        ControlFactory.getMainController().maintainedProgram();
    }

    public void selectCreateSchedule(ProgramSlot programSlot) {
        new CreateScheduleDelegate(this).execute(programSlot);
    }

    public void scheduleUpdated(ProgramSlot sp2edit) {
        new ModifyScheduleDelegate(this).execute(sp2edit);
    }

    public void onDisplayProgram(ScheduleProgramScreen scheduleProgramScreen) {
        this.scheduleProgramScreen = scheduleProgramScreen;
        if (sp2edit == null) {
            scheduleProgramScreen.createSchedule();
        }
        else{scheduleProgramScreen.editSchedule(sp2edit);}

    }
    public void maintainedSchedule() {
        ControlFactory.getMainController().maintainSchedule();
    }
}