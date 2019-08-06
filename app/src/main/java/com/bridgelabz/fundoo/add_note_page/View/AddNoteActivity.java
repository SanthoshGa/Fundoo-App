package com.bridgelabz.fundoo.add_note_page.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.Dashboard.DashboardActivity;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.Utility.DatePickerFragment;
import com.bridgelabz.fundoo.Utility.TimePickerFragment;
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

import static com.bridgelabz.fundoo.Utility.AppConstants.ADD_NOTE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.ADD_REMINDER_TO_NOTES_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.CHANGE_COLOR_TO_NOTE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.PIN_UNPIN_TO_NOTE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.SET_ARCHIVE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.UPDATE_NOTE_ACTION;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG = "AddNoteActivity";
    private EditText mTextTitle;
    private EditText mTextDescription;
    private Button mButtonSave;
    private ImageButton imgBtnReminder;
    private ImageButton imgBtnArchive;
    private CheckBox cbPinned;
    private EditText mTextDate;
    private EditText mTextTime;
    private TextView mReminder;
    private Calendar calendar = Calendar.getInstance();
    NoteViewModel noteViewModel;
    ConstraintLayout rootViewGroup;
    private String noteColor = "#ffffff";
    private boolean isEditMode = false;
    private boolean isArchived = false;
    private boolean isPinned = false;
    private boolean isTrashed = false;
    //    private boolean isChangeColor = false;
    private AddNoteModel noteToEdit;
    private RadioGroup radioGroup;
    private StringBuilder reminderStringBuilder = new StringBuilder();
    private NotificationManagerCompat notificationManagerCompat;
    RestApiNoteViewModel apiNoteViewModel;
    private boolean isColourChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteViewModel = new NoteViewModel(this);
        findViews();
        setOnClickSave();
        checkEditMode();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        apiNoteViewModel = new RestApiNoteViewModel(this);
        setClickToPinnedBtn();
        registerLocalBroadcasts();
        getSupportActionBar().hide();
        setButtonListeners();
    }

    private void findViews() {
        mTextTitle = findViewById(R.id.et_title);
        mTextDescription = findViewById(R.id.et_description);
        mButtonSave = findViewById(R.id.btn_save);
        rootViewGroup = findViewById(R.id.root_add_note_activity);
        radioGroup = findViewById(R.id.radio_group);
        imgBtnArchive = findViewById(R.id.img_btn_archive);
        imgBtnReminder = findViewById(R.id.img_btn_reminder);
        cbPinned = findViewById(R.id.cb_pin);
        mTextDate = findViewById(R.id.et_date);
        mTextTime = findViewById(R.id.et_time);
        mReminder = findViewById(R.id.tv_reminder);
    }

    private boolean checkEditMode() {
        Intent intent = getIntent();
        if (intent.hasExtra("noteToEdit")) {
            NoteResponseModel noteResponseModel = (NoteResponseModel) intent.
                    getSerializableExtra("noteToEdit");
            if (noteResponseModel.getReminder().isEmpty())
                noteToEdit = AddNoteModel.getNoteFromResponse(noteResponseModel);
            Log.e(TAG, "note is available");
            System.out.println(noteToEdit.toString());
            isEditMode = true;
            setUpEditFields();
        } else {
            isEditMode = false;
        }
        return true;
    }

    private void setButtonListeners() {
        setPinnedButtonClickListener();
        setReminderButtonClickListener();
        setArchiveButtonClickListener();
    }

    private void setArchiveButtonClickListener() {
        imgBtnArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNoteActivity.this, "Archive Button", Toast.LENGTH_SHORT).show();
                if (isEditMode) {
                    AddNoteModel addNoteModel = new AddNoteModel(noteToEdit.getTitle(), noteToEdit.getDescription(),
                            noteToEdit.getIsPinned(), noteToEdit.getIsArchived(), noteToEdit.getIsDeleted(),
                            noteToEdit.getCreatedDate(), noteToEdit.getModifiedDate(), noteToEdit.getColor(),
                            noteToEdit.getId(), noteToEdit.getUserId(), noteToEdit.getReminder());
                    apiNoteViewModel.setArchiveToNote(addNoteModel);
                    updateNoteToDB(addNoteModel);
                }
            }
        });

    }

    private void setPinnedButtonClickListener() {
        cbPinned.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Toast.makeText(AddNoteActivity.this, "Pinned Button clicked :" + isChecked,
                        Toast.LENGTH_SHORT).show();
                if (isEditMode) {
                    noteToEdit.setIsPinned(isChecked);
                    apiNoteViewModel.pinUnpinToNote(noteToEdit);
                }
            }
        });

    }

    private void setReminderButtonClickListener() {
        imgBtnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNoteActivity.this, "Reminder button clicked",
                        Toast.LENGTH_SHORT).show();

//                apiNoteViewModel.addReminderToNotes(addNoteModel);
                showReminderDialogBox();
            }
        });

    }

    private void showReminderDialogBox() {
        final Dialog dialog = new Dialog(this);
        View dialogView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.dialog_box_date_time_picker, null);
        dialog.setContentView(dialogView);
        dialog.setTitle("Date and Time Picker");
        dialog.show();
        setReminder(dialogView, dialog);
    }

    private void setReminder(View dialogView, Dialog dialog) {
        setDateTextViewClickListener(dialogView);
        setTimeTextViewClickListener(dialogView);
        setCancelReminderButtonClickListener(dialog);
        setSaveReminderButtonClickedListener(dialog);
    }

    private void setCancelReminderButtonClickListener(final Dialog dialog) {

        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNoteActivity.this, "Cancel Button Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void setSaveReminderButtonClickedListener(final Dialog dialog) {
        Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reminderDate = mTextDate.getText().toString();
                String reminderTime = mTextTime.getText().toString();
                String reminder = reminderDate + " " + reminderTime;
                mReminder.setText(reminder);
                apiNoteViewModel.addReminderToNotes(noteToEdit);
                Toast.makeText(AddNoteActivity.this, "Save Button Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void setDateTextViewClickListener(View dialogView) {
        mTextDate = dialogView.findViewById(R.id.et_date);
        mTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment(
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month,
                                                  int day) {
                                String reminderDate = day + "/" + (month + 1) + "/" + year;
                                mTextDate.setText(reminderDate);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.YEAR, year);
                            }
                        });
                datePickerFragment.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void setTimeTextViewClickListener(View dialogView) {
        mTextTime = dialogView.findViewById(R.id.et_time);
        mTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment(
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                                String time = hour + " : " + min;
                                mTextTime.setText(time);
//                              to notify alarm
                                calendar.set(Calendar.HOUR_OF_DAY, hour);
                                calendar.set(Calendar.MINUTE, min);
                                calendar.set(Calendar.SECOND, 0);

                            }
                        });
                timePickerFragment.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    private void setClickToPinnedBtn() {
        View pinnedView = LayoutInflater.from(this).inflate(R.layout.switch_item, null,
                false);
        CheckBox cbPinned = pinnedView.findViewById(R.id.cb_pinned);
        cbPinned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNoteActivity.this, "Pinned Clicked", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void registerLocalBroadcasts() {

        LocalBroadcastManager.getInstance(this).registerReceiver(addedNoteBroadcastReceiver,
                new IntentFilter(ADD_NOTE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(setArchiveNoteBroadcastReceiver,
                new IntentFilter(SET_ARCHIVE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(updateNotesBroadcastReceiver,
                new IntentFilter(UPDATE_NOTE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(changeColorToNoteBroadcastReceiver,
                new IntentFilter(CHANGE_COLOR_TO_NOTE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(pinUnpinToNoteBroadcastReceiver,
                new IntentFilter(PIN_UNPIN_TO_NOTE_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(addReminderToNotesBroadcastReceiver,
                new IntentFilter(ADD_REMINDER_TO_NOTES_ACTION));
    }

    public BroadcastReceiver addReminderToNotesBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("addReminder")) {
                boolean addReminder = intent.getBooleanExtra("addReminder", false);
                Log.e(TAG, "onReceive: we got the data of Reminder " + addReminder);
                if (addReminder) {
                }
            }

        }
    };

    public static BroadcastReceiver setArchiveNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("isNoteArchived")) {
                boolean isNoteArchived = intent.getBooleanExtra("isNoteArchived", false);
                Log.e(TAG, "onReceive:  we got the data of Archive " + isNoteArchived);
                if (isNoteArchived) {


                }
            }

        }
    };

    public BroadcastReceiver pinUnpinToNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("isPinned")) {
                boolean isPinned = intent.getBooleanExtra("isPinned", false);
                Log.e(TAG, "onReceive: Yes we got the data!!!!!!!!!!! " + isPinned);
                if (isPinned) {

                }
            }
        }
    };

    public BroadcastReceiver changeColorToNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("isColorEdit")) {
                boolean isColorEdit = intent.getBooleanExtra("isColorEdit", false);
                Log.e(TAG, "onReceive: Yes we got the data!!!! " + isColorEdit);
                if (isColorEdit) {

                }

            }

        }
    };


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

    private BroadcastReceiver updateNotesBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: Local Broadcasts are working in dashboard");
            if (intent.hasExtra("isNoteEdit")) {
                boolean isNoteEdit = intent.getBooleanExtra("isNoteEdit", false);
                Log.e(TAG, "onReceive: Yes we got the data " + isNoteEdit);
                if (isNoteEdit) {
                    Toast.makeText(AddNoteActivity.this, "Note updated", Toast.LENGTH_SHORT).show();

                    Intent editNoteIntent = new Intent(AddNoteActivity.this, DashboardActivity.class);
                    startActivity(editNoteIntent);
                } else {
                    Toast.makeText(AddNoteActivity.this, "Something went wrong",
                            Toast.LENGTH_SHORT).show();
                }

            } else if (intent.hasExtra("error")) {
                Toast.makeText(AddNoteActivity.this, "Failed to connect to server",
                        Toast.LENGTH_SHORT).show();
            }

        }
    };

    // test
    private void updateNoteToDB(AddNoteModel noteToEdit) {
        // update note to database
//        boolean isNoteEdit = noteViewModel.updateNote(noteToEdit);
//        if (isNoteEdit) {
//            Toast.makeText(AddNoteActivity.this, "Note is Updated", Toast.LENGTH_SHORT).show();
//            Intent editData = new Intent(AddNoteActivity.this, DashboardActivity.class);
//            startActivity(editData);
//        } else {
//            Toast.makeText(AddNoteActivity.this, "Unable to update", Toast.LENGTH_SHORT).show();
//        }
//        RestApiNoteViewModel apiNoteViewModel = new RestApiNoteViewModel(this);
        if (isColourChanged) {
            apiNoteViewModel.changeColorToNote(noteToEdit);
        }

        apiNoteViewModel.updateNotes(noteToEdit);
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


        apiNoteViewModel.addNotes((AddNoteModel) noteModel);
//        boolean isNoteAdd = noteViewModel.addNote(note);

    }

    public void onRadioButtonClicked(View radioButtonView) {
        boolean checked = ((RadioButton) radioButtonView).isChecked();
        switch (radioButtonView.getId()) {
            case R.id.radio_none:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.white);
                    setNoteColor("#FFFFFF");
                    isColourChanged = true;
                }
                break;
            case R.id.radio_white:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.white);
                    setNoteColor("#FFFFFF");
                    isColourChanged = true;
                }
                break;
            case R.id.radio_green:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.yellowGreen);
                    setNoteColor("#9ACD32");
                    isColourChanged = true;
                }
                break;
            case R.id.radio_olive:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.olive);
                    setNoteColor("#808000");
                    isColourChanged = true;
                }
                break;
            case R.id.radio_pink:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.pink);
                    setNoteColor("#FFC0CB");
                    isColourChanged = true;
                }
                break;
            case R.id.radio_purple:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.purple);
                    setNoteColor("#800080");
                    isColourChanged = true;
                }
                break;
            case R.id.radio_skyBlue:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.skyBlue);
                    setNoteColor("#87CEEB");
                    isColourChanged = true;
                }
                break;
            case R.id.radio_silver:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.silver);
                    setNoteColor("#C0C0C0");
                    isColourChanged = true;
                }
                break;
            case R.id.radio_yellow:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.yellow);
                    setNoteColor("#FFFF00");
                    Log.e(TAG, "YELLOW ADDED");
                    isColourChanged = true;
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.action_archive:
//                if (isEditMode) {
//                    AddNoteModel addNoteModel = new AddNoteModel(noteToEdit.getTitle(), noteToEdit.getDescription(),
//                            noteToEdit.getIsPinned(), noteToEdit.getIsArchived(), noteToEdit.getIsDeleted(),
//                            noteToEdit.getCreatedDate(), noteToEdit.getModifiedDate(), noteToEdit.getColor(),
//                            noteToEdit.getId(), noteToEdit.getUserId(), noteToEdit.getReminder());
//                    apiNoteViewModel.setArchiveToNote(addNoteModel);
//                    updateNoteToDB(addNoteModel);
//                } else {

//                    String title = mTextTitle.getText().toString().trim();
//                    String description = mTextDescription.getText().toString().trim();
//                    Note note = new Note(title, description, noteColor, true,
//                            reminderStringBuilder.toString(), false, isTrashed);
//                    addNoteToDb(note);
//                    return true;
//                }


//            case R.id.action_reminder:
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

//            case R.id.action_pinned:
//                Log.e(TAG, "onOptionsItemSelected: action pinned clicked");
//                isPinned = !isPinned;
//                Toast.makeText(this, "Pinned : " + isPinned, Toast.LENGTH_SHORT).show();

//                if (isEditMode) {
//                    Log.e(TAG, "IF CLAUSE");
//                    AddNoteModel addNoteModel = new AddNoteModel(noteToEdit.getTitle(), noteToEdit.getDescription(),
//                            noteToEdit.getIsPinned(), noteToEdit.getIsArchived(), noteToEdit.getIsDeleted(),
//                            noteToEdit.getCreatedDate(), noteToEdit.getModifiedDate(), noteToEdit.getColor(),
//                            noteToEdit.getId(), noteToEdit.getUserId(), noteToEdit.getReminder());
//                    apiNoteViewModel.pinUnpinToNote(addNoteModel);
//                    updateNoteToDB(addNoteModel);
//                } else {
//                    Log.e(TAG, "");
//                    String title_1 = mTextTitle.getText().toString().trim();
//                    String description_1 = mTextDescription.getText().toString().trim();
//                    Note note1 = new Note(title_1, description_1, noteColor, false,
//                            reminderStringBuilder.toString(), true, false);
//                    addNoteToDb(note1);
//                }
//                return true;

//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public void onClick(View v) {
//        if (v == mButtonDate) {
//
//            // Get Current Date
//            Calendar calender = Calendar.getInstance();
//            int mYear = calender.get(Calendar.YEAR);
//            int mMonth = calender.get(Calendar.MONTH);
//            int mDay = calender.get(Calendar.DAY_OF_MONTH);
//
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                    new DatePickerDialog.OnDateSetListener() {
//
//                        @Override
//                        public void onDateSet(DatePicker view, int year,
//                                              int monthOfYear, int dayOfMonth) {
//
//                            mTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//
//                        }
//                    }, mYear, mMonth, mDay);
//            datePickerDialog.show();
//
//
//            setDateTimeString("EEE, MMM d ", calender.getTime());
//        }
//        if (v == mButtonTime) {
//
//            // Get Current Time
//            final Calendar calender = Calendar.getInstance();
//            int mHour = calender.get(Calendar.HOUR_OF_DAY);
//            int mMinute = calender.get(Calendar.MINUTE);
//
//            // Launch Time Picker Dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                    new TimePickerDialog.OnTimeSetListener() {
//
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay,
//                                              int minute) {
//
//                            mTextTime.setText(hourOfDay + ":" + minute);
//                        }
//                    }, mHour, mMinute, false);
//            timePickerDialog.show();
//            setDateTimeString(" hh:mm:ss", calender.getTime());
//        }
//
//    }
//
//    private void addReminder(Date date) {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//
//        Intent notificationIntent = new Intent(this, ReminderReceiver.class);
//        notificationIntent.addCategory("android.intent.category.DEFAULT");
//
//        int requestCode = 100;
//        PendingIntent broadcast = PendingIntent.getBroadcast(this, requestCode,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), broadcast);
//    }

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
        unregisterBroadcastReceivers();
        super.onDestroy();
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(addedNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(setArchiveNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(changeColorToNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(pinUnpinToNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateNotesBroadcastReceiver);
    }
}