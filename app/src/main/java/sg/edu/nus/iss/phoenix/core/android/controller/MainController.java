package sg.edu.nus.iss.phoenix.core.android.controller;

import android.app.Application;
import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;

public class MainController {
    private static Application app = null;
    private String username;
    private MainScreen mainScreen;

    public static Application getApp() {
        return app;
    }

    public static void setApp(Application app) {
        MainController.app = app;
    }

    public static void displayScreen(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(intent);
    }

    public void startUseCase(String username) {
        this.username = username;

        Intent intent = new Intent(MainController.getApp(), MainScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        mainScreen.showUsername(username);
    }

    public void selectMaintainProgram() {
        ControlFactory.getProgramController().startUseCase();
    }

    public void maintainedProgram() {
        startUseCase(username);
    }

    public void selectLogout() {
        username = "<not logged in>";
        ControlFactory.getLoginController().logout();
    }

    public void selectMaintainSchedule() {
        ControlFactory.getScheduleController().startUseCase();
    }

    // This is a dummy operation to test the invocation of Review Select Radio Program use case.
    public void selectedProgram(RadioProgram rpSelected) {
        startUseCase(username);
    }

    public void selectedProgram(ProgramSlot rpSelected) {
        startUseCase(username);
    }
    public void selectMaintainUser(){
        ControlFactory.getUserController().startUseCase();
    }
    public void maintainedUser() {
        startUseCase(username);
    }

    public void maintainSchedule() {
        startUseCase(username);
    }
}
