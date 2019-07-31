package com.bridgelabz.fundoo.add_note_page.View;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.BroadcastReceivers.ReminderReceiver;
import com.bridgelabz.fundoo.Dashboard.DashboardActivity;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.Utility.ValidationHelper;
import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.BaseNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.Note;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.ViewModel.NoteViewModel;
import com.bridgelabz.fundoo.add_note_page.ViewModel.RestApiNoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.bridgelabz.fundoo.BroadcastReceivers.LocalBroadcastManager.setArchiveNoteBroadcastReceiver;
import static com.bridgelabz.fundoo.Utility.AppConstants.ADD_NOTE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.SET_ARCHIVE_ACTION;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "AddNoteActivity";
    private EditText mTextTitle;
    private EditText mTextDescription;
    private Button mButtonSave;
    private EditText mTextDate;
    private EditText mTextTime;
    private Button mButtonDate;
    private Button mButtonTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    MenuItem mArchive;
    MenuItem mReminder;
    MenuItem mPinned;
    NoteViewModel noteViewModel;
    ConstraintLayout rootViewGroup;
    private String noteColor = "#ffffff";
    private boolean isEditMode = false;
    private boolean isArchived = false;
    private boolean isPinned = false;
    private boolean isTrashed = false;
    private AddNoteModel noteToEdit;
    private RadioGroup radioGroup;
    private StringBuilder reminderStringBuilder = new StringBuilder();
    private NotificationManagerCompat notificationManagerCompat;
//    private DatePickerDialog.OnDateSetListener mDateSetListener;
//    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteViewModel = new NoteViewModel(this);
        findViews();
        setOnClickSave();
        checkEditMode();
        mButtonDate.setOnClickListener(this);
        mButtonTime.setOnClickListener(this);
        notificationManagerCompat = NotificationManagerCompat.from(this);

        LocalBroadcastManager.getInstance(this).registerReceiver
                (addedNoteBroadcastReceiver,
                        new IntentFilter(ADD_NOTE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(setArchiveNoteBroadcastReceiver,
                new IntentFilter(SET_ARCHIVE_ACTION));
    }

    private void checkEditMode() {
        Intent intent = getIntent();
        if (intent.hasExtra("noteToEdit")) {
            NoteResponseModel noteResponseModel = (NoteResponseModel) intent.
                    getSerializableExtra("noteToEdit");
            if(noteResponseModel.getReminder().isEmpty())
            noteToEdit = AddNoteModel.getNoteFromResponse(noteResponseModel);
            Log.e(TAG, "note is available");
            System.out.println(noteToEdit.toString());
            isEditMode = true;
            setUpEditFields();
        } else {
            isEditMode = false;
        }
    }

    private void setUpEditFields() {
        mTextTitle.setText(noteToEdit.getTitle());
        mTextDescription.setText(noteToEdit.getDescription());
        setColorRdButton(noteToEdit);
    }

    private void setColorRdButton(AddNoteModel noteToEdit) {
        String noteColor = noteToEdit.getColor();
        String defaultColor = getString(R.string.none_hexCode);
        String whiteColor = getString(R.string.white_hexCode);
        String greenColor = getString(R.string.green_hexCode);
        String yellowColor = getString(R.string.yellow_hexCode);
        String pinkColor = getString(R.string.pink_hexCode);
        String silverColor = getString(R.string.silver_hexCode);
        String oliveColor = getString(R.string.olive_hexCode);
        String skyBlueColor = getString(R.string.skyBlue_hexCode);
        String purpleColor = getString(R.string.purple_hexCode);

        checkColorRadioBtn(defaultColor, noteColor, R.id.radio_none, R.color.colorDefault);
        checkColorRadioBtn(whiteColor, noteColor, R.id.radio_white, R.color.white);
        checkColorRadioBtn(greenColor, noteColor, R.id.radio_green, R.color.yellowGreen);
        checkColorRadioBtn(yellowColor, noteColor, R.id.radio_yellow, R.color.yellow);
        checkColorRadioBtn(pinkColor, noteColor, R.id.radio_pink, R.color.pink);
        checkColorRadioBtn(silverColor, noteColor, R.id.radio_silver, R.color.silver);
        checkColorRadioBtn(oliveColor, noteColor, R.id.radio_olive, R.color.olive);
        checkColorRadioBtn(skyBlueColor, noteColor, R.id.radio_skyBlue, R.color.skyBlue);
        checkColorRadioBtn(purpleColor, noteColor, R.id.radio_purple, R.color.purple);
    }

    private void checkColorRadioBtn(String defaultColor, final String noteColor, int viewResId,
                                    final int colorResId) {
        if (defaultColor.equals(noteColor)) {

            radioGroup.check(viewResId);
            RadioButton rdBtn = findViewById(viewResId);
            onRadioButtonClicked(rdBtn);
        }
    }

    private void findViews() {
        mTextTitle = findViewById(R.id.et_title);
        mTextDescription = findViewById(R.id.et_description);
        mButtonSave = findViewById(R.id.btn_save);
        rootViewGroup = findViewById(R.id.root_add_note_activity);
        radioGroup = findViewById(R.id.radio_group);
        mArchive = findViewById(R.id.action_archive);
        mPinned = findViewById(R.id.action_pinned);
        mReminder = findViewById(R.id.action_reminder);
        mTextDate = findViewById(R.id.et_date);
        mTextTime = findViewById(R.id.et_time);
        mButtonDate = findViewById(R.id.btn_date);
        mButtonTime = findViewById(R.id.btn_time);
    }

    private void setOnClickSave() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               Note noteDetails =  getNoteDetailsFromFields();

                String title = mTextTitle.getText().toString().trim();
                String description = mTextDescription.getText().toString().trim();

                if (ValidationHelper.validateTitle(title)) {
                    if (ValidationHelper.validateDescription(description)) {
                        if (isEditMode) {
                            noteToEdit.setTitle(title);
                            noteToEdit.setDescription(description);
                            noteToEdit.setColor(noteColor);
                            updateNoteToDB(noteToEdit);
                        } else {
                            Note note = new Note(title, description, noteColor, isArchived,
                                    reminderStringBuilder.toString(), isPinned, isTrashed);

                            Log.e(TAG, note.toString());
                            addNoteToDb(note);
                        }

                    } else {
                        Toast.makeText(AddNoteActivity.this, "Enter Description",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddNoteActivity.this, "Enter Title",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // test
    private void updateNoteToDB(AddNoteModel noteToEdit) {
        // update note to database
        boolean isNoteEdit = noteViewModel.updateNote(noteToEdit);
        if (isNoteEdit) {
            Toast.makeText(AddNoteActivity.this, "Note is Updated", Toast.LENGTH_SHORT).show();
            Intent editData = new Intent(AddNoteActivity.this, DashboardActivity.class);
            startActivity(editData);
        } else {
            Toast.makeText(AddNoteActivity.this, "Unable to update", Toast.LENGTH_SHORT).show();
        }
    }


    public BroadcastReceiver addedNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: Local broadcasts are working ");
            if (intent.hasExtra("isNoteAdded")) {
                boolean isNoteAdded = intent.
                        getBooleanExtra("isNoteAdded", false);
                Log.e(TAG, "onReceive: Yup we got the data " + isNoteAdded);


                if (isNoteAdded) {
                    Toast.makeText(AddNoteActivity.this, " Note is Successfully Saved",
                            Toast.LENGTH_SHORT).show();
//                      Log.e(TAG, note.toString());
//                      if(reminderStringBuilder.toString().isEmpty()){
//                        Toast.makeText(this, "not added the reminder", Toast.LENGTH_SHORT).show();
//                      }
//                      else{
//                        addReminder(new Date());
//                       }

                    Intent data = new Intent(AddNoteActivity.this, DashboardActivity.class);
                    startActivity(data);
                    finish();
                } else {
                    Toast.makeText(AddNoteActivity.this, "Something Went Wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    protected void addNoteToDb(Note note) {
        //TODO :

        BaseNoteModel noteModel = new AddNoteModel(note.getTitle(), note.getDescription(),
                note.isPinned(), note.isArchived(), note.isTrashed(),
                "", "",
                note.getColor(), "", "", note.getIfReminder());

        RestApiNoteViewModel noteViewModel = new RestApiNoteViewModel(this);
        noteViewModel.addNotes((AddNoteModel) noteModel);
//        boolean isNoteAdd = noteViewModel.addNote(note);

    }

    public void onRadioButtonClicked(View radioButtonView) {
        boolean checked = ((RadioButton) radioButtonView).isChecked();
        switch (radioButtonView.getId()) {
            case R.id.radio_none:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.white);
                    setNoteColor("#FFFFFF");
                }
                break;
            case R.id.radio_white:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.white);
                    setNoteColor("#FFFFFF");
                }
                break;
            case R.id.radio_green:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.yellowGreen);
                    setNoteColor("#9ACD32");
                }
                break;
            case R.id.radio_olive:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.olive);
                    setNoteColor("#808000");
                }
                break;
            case R.id.radio_pink:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.pink);
                    setNoteColor("#FFC0CB");
                }
                break;
            case R.id.radio_purple:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.purple);
                    setNoteColor("#800080");
                }
                break;
            case R.id.radio_skyBlue:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.skyBlue);
                    setNoteColor("#87CEEB");
                }
                break;
            case R.id.radio_silver:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.silver);
                    setNoteColor("#C0C0C0");
                }
                break;
            case R.id.radio_yellow:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.yellow);
                    setNoteColor("#FFFF00");
                    Log.e(TAG, "YELLOW ADDED");
                }
                break;
            default:
                setNoteColor("#FFFFFF");
        }
    }

    private void setNoteColor(String noteColor) {
        this.noteColor = noteColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addnoteactivity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_archive:
                String title = mTextTitle.getText().toString().trim();
                String description = mTextDescription.getText().toString().trim();
                Note note = new Note(title, description, noteColor, true,
                        reminderStringBuilder.toString(), false, isTrashed);
                addNoteToDb(note);
                return true;

            case R.id.action_reminder:
//                DialogFragment datePicker = new DatePickerFragment();
//                datePicker.show(getSupportFragmentManager(), "date picker");
//
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(), "time picker");
//                return true;

//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(AddNoteActivity.this,
//                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth, mDateSetListener, year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//
//                int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                int minute = calendar.get(Calendar.MINUTE);
//
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNoteActivity.this,
//                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth, mTimeSetListener, hour, minute,false);
//                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                timePickerDialog.show();
//
//                return true;


            case R.id.action_pinned:
                String title_1 = mTextTitle.getText().toString().trim();
                String description_1 = mTextDescription.getText().toString().trim();
                Note note1 = new Note(title_1, description_1, noteColor, false,
                        reminderStringBuilder.toString(), true, false);
                addNoteToDb(note1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonDate) {

            // Get Current Date
            Calendar calender = Calendar.getInstance();
            mYear = calender.get(Calendar.YEAR);
            mMonth = calender.get(Calendar.MONTH);
            mDay = calender.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


            setDateTimeString("EEE, MMM d ", calender.getTime());
        }
        if (v == mButtonTime) {

            // Get Current Time
            final Calendar calender = Calendar.getInstance();
            mHour = calender.get(Calendar.HOUR_OF_DAY);
            mMinute = calender.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            mTextTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
            setDateTimeString(" hh:mm:ss", calender.getTime());
        }

    }

    private void addReminder(Date date) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        Intent notificationIntent = new Intent(this, ReminderReceiver.class);
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        int requestCode = 100;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, requestCode,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), broadcast);
    }

    //
//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//        setDateTimeString("EEE, MMM d ", calendar.getTime());
//        Log.e(TAG, "THE DATE IS : " + calendar.getTime().toString());
//    }
//
//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.MINUTE, minute);
//
//        setDateTimeString(" hh : mm : ss", calendar.getTime());
//        Log.e(TAG, "THE TIME IS : " + calendar.getTime().toString());
//    }
//
    private void setDateTimeString(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        reminderStringBuilder.append(simpleDateFormat.format(date)).append(" ");
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(addedNoteBroadcastReceiver);
        super.onDestroy();
    }
}