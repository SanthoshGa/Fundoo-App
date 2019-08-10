package com.bridgelabz.fundoo.Dashboard;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.Note;
import com.bridgelabz.fundoo.add_note_page.View.AddNoteActivity;
import com.bridgelabz.fundoo.add_note_page.Model.BaseNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.View.Adapter.NotesAdapter;
import com.bridgelabz.fundoo.add_note_page.ViewModel.NoteViewModel;
import com.bridgelabz.fundoo.add_note_page.ViewModel.RestApiNoteViewModel;
import com.bridgelabz.fundoo.LoginSignup.View.LoginActivity;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.add_label_page.view.AddLabelFragment;
import com.bridgelabz.fundoo.common.SharedPreferencesManager;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.internal.Util;

import static com.bridgelabz.fundoo.Utility.AppConstants.FETCH_NOTE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.GET_ARCHIVE_NOTES_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.GET_REMINDER_NOTES_LIST_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.GET_TRASH_NOTES_LIST_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.TRASH_NOTE_ACTION;
import static com.bridgelabz.fundoo.Utility.AppConstants.UPDATE_NOTE_ACTION;

public class DashboardActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DashboardActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView mTextTakeNote;
    private ImageView imageProfile;
    private TextView tvEmail;
    public static final int PICK_REQUEST_CODE = 10;
    RecyclerView mRecyclerView;
    private List<NoteResponseModel> noteList = new ArrayList<>();
    NoteViewModel noteViewModel;
    RestApiNoteViewModel restApiNoteViewModel;
    NotesAdapter notesAdapter;
    NavigationView navigationView;
    NoteResponseModel noteToDelete;
    private SharedPreferencesManager sharedPreferencesManager;
    Animation animZoomOut;
//    Observable<List<BaseNoteModel>> observableNotes = new ObservableNotes(noteList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noteViewModel = new NoteViewModel(this);
        restApiNoteViewModel = new RestApiNoteViewModel(this);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        prepareRecyclerView();
//        registerObserverForNotes();
        setNavigationViewListener();
        setUpDrawer();
        setUpNavigationView();
        setOnClickTakeNote();
        registerLocalBroadcasts();
        setOnClickListenerToProfile();

    }

    private void setOnClickListenerToProfile() {
        navigationView = findViewById(R.id.nav_view);
        imageProfile = navigationView.getHeaderView(0).findViewById(R.id.profile_pic);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this, "profile clicked", Toast.LENGTH_SHORT).show();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PICK_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_REQUEST_CODE) {
            Uri imageUri = data.getData();
            Log.e(TAG, "onActivityResult: " + imageUri.toString());
            Glide.with(this).load(imageUri).circleCrop().into(imageProfile);
        }
    }

    private void registerLocalBroadcasts() {
        LocalBroadcastManager.getInstance(this).registerReceiver(getNotesBroadcastReceiver,
                new IntentFilter(FETCH_NOTE_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(getArchiveNotesListBroadcastReceiver,
                new IntentFilter(GET_ARCHIVE_NOTES_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(trashNotesBroadcastReceiver,
                new IntentFilter(TRASH_NOTE_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(getTrashNotesListBroadcastReceiver,
                new IntentFilter(GET_TRASH_NOTES_LIST_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(getReminderNotesListBroadcastReceiver,
                new IntentFilter(GET_REMINDER_NOTES_LIST_ACTION));


    }

    private void setUpDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout_dashboard);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    private boolean deleteNote(BaseNoteModel noteAt) {
//        return noteViewModel.deleteNote(noteAt);
        return true;          // TODO: change
    }


    private void prepareRecyclerView() {
//        observableNotes = noteViewModel.fetchAllNotes();
//        noteList = ((ObservableNotes) observableNotes).getListOfNotes();
//        noteList = noteViewModel.getAllNoteData();
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        showList();
//        restApiNoteViewModel.fetchNoteList();


        // for Staggered Grid View
        mRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        // for Recycler view
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notesAdapter = new NotesAdapter(noteList, new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int notePosition) {

//                animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
//                animZoomOut.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        Toast.makeText(DashboardActivity.this, "animation start",
//                                Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
                Intent intent = new Intent(DashboardActivity.this, AddNoteActivity.class);
                intent.putExtra("noteToEdit", noteList.get(notePosition));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation
                                (DashboardActivity.this).toBundle());
                    }
                }
                Log.e(TAG, "the position of note is " + notePosition);
            }
        });
        mRecyclerView.setAdapter(notesAdapter);
        noteItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void showList() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            restApiNoteViewModel.fetchNoteList();
            noteViewModel.deleteAllNotes();
            Log.e(TAG, "showList: " + noteList);
        } else {
            Log.e(TAG, "showList: Not connected!!!!!");
        }
    }

    private BroadcastReceiver trashNotesBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("isTrashed")) {
                boolean trashNotes = intent.getBooleanExtra("isTrashed", false);
                Log.e(TAG, "onReceive: we got the data of trashNotes " + trashNotes);
                if (trashNotes) {
//                                    notesAdapter.notifyItemRemoved(adapterPosition);
                    Toast.makeText(DashboardActivity.this, "Item Deleted",
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    };


    private ItemTouchHelper noteItemTouchHelper = new ItemTouchHelper
            (new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                        draggedViewHolder, @NonNull RecyclerView.ViewHolder targetViewHolder) {
                    int draggedPosition = draggedViewHolder.getAdapterPosition();
                    int targetPosition = targetViewHolder.getAdapterPosition();

                    notesAdapter.onItemMove(draggedPosition, targetPosition);
                    Collections.swap(noteList, draggedPosition, targetPosition);
                    Log.e(TAG, "dragged and moved");
                    return false;
                }


                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder swipedViewHolder, int direction) {
                    final int adapterPosition = swipedViewHolder.getAdapterPosition();
                    Log.e(TAG, "The adapter position is " + adapterPosition);
                    noteToDelete = notesAdapter.getNoteAt(adapterPosition);
                    restApiNoteViewModel.trashNotes(noteToDelete);

//                    new TrashBroadcastReceiver(adapterPosition, noteToDelete);
//                    noteList.remove(noteToDelete);
                    notesAdapter.onItemSwiped(adapterPosition);
                    notesAdapter.notifyItemRemoved(adapterPosition);
                    Toast.makeText(DashboardActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
//
//                    notesAdapter.notifyItemRemoved(adapterPosition);
//                    boolean isDeleted = deleteNote(noteToDelete);
//                    if (isDeleted) {
//                        noteList.remove(noteToDelete);
//                        notesAdapter.notifyItemRemoved(adapterPosition);
//                        Toast.makeText(DashboardActivity.this, "Item Deleted",
//                                Toast.LENGTH_SHORT).show();
//
//
//                    } else {
//                        Toast.makeText(DashboardActivity.this, "Item could not be deleted",
//                                Toast.LENGTH_SHORT).show();
//                    }
                }
            });

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setOnClickTakeNote() {
        mTextTakeNote = findViewById(R.id.tv_takeNote);
        mTextTakeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(DashboardActivity.this, AddNoteActivity.class);
                startActivity(noteIntent);
            }
        });
    }

    private void setUpNavigationView() {
        Intent intent = getIntent();
        if (intent.hasExtra("email")) {
            String emailId = intent.getStringExtra("email");
            String imageUrl = intent.getStringExtra("imageUrl");
            Log.e("Dashboard", emailId);

            navigationView = findViewById(R.id.nav_view);
            tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
            imageProfile = navigationView.getHeaderView(0).findViewById(R.id.profile_pic);
            tvEmail.setText(emailId);
            if (imageUrl == null) {
                Glide.with(this).load(R.drawable.icon1).into(imageProfile);
            } else {
                Glide.with(this).load(imageUrl).circleCrop().into(imageProfile);
            }
        } else {
            Log.e(TAG, "intent does not have extra string \"email id\" ");
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                noteList.clear();
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(this, " All Notes Deleted ", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "items in arrayList is " + noteList.size());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notesAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_menu_notes:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Notes drawer menu clicked!", Toast.LENGTH_SHORT).show();
//                noteList = noteViewModel.getAllNoteData();            // TODO: change
                restApiNoteViewModel.fetchNoteList();
                notesAdapter.setNoteModelArrayList(noteList);
                notesAdapter.notifyDataSetChanged();
                break;
            case R.id.drawer_menu_reminders:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Reminders drawer menu clicked!", Toast.LENGTH_SHORT).show();
//                noteList = noteViewModel.getReminderNotes();          // TODO: change
                restApiNoteViewModel.getReminderNotesList();
//                notesAdapter.setNoteModelArrayList(noteList);
//                notesAdapter.notifyDataSetChanged();
                break;
            case R.id.drawer_menu_archives:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Archive drawer menu clicked!", Toast.LENGTH_SHORT).show();
//                noteList = noteViewModel.getArchivedNotes();          // TODO: change
                restApiNoteViewModel.getArchiveNotesList();
                Log.e(TAG, "Inside archive list method");
                break;

            case R.id.drawer_menu_pinned:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Pinned drawer menu clicked!", Toast.LENGTH_SHORT).show();
//                noteList = noteViewModel.getPinnedNotes();          // TODO: change
                notesAdapter.notifyDataSetChanged();
                break;
            case R.id.drawer_menu_create_label:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Create Label drawer menu clicked!", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddLabelFragment addLabelFragment = new AddLabelFragment();
                fragmentTransaction.replace(R.id.container_fragment, addLabelFragment, "Add Label Fragment");
                fragmentTransaction.addToBackStack("Add Label Fragment").commit();
                break;
            case R.id.drawer_menu_trashed_notes:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Trashed drawer menu clicked!", Toast.LENGTH_SHORT).show();
//                noteList = noteViewModel.getTrashedNotes();          // TODO: change
                restApiNoteViewModel.getTrashNotesList();
//                notesAdapter.setNoteModelArrayList(noteList);
//                notesAdapter.notifyDataSetChanged();
                break;
            case R.id.drawer_menu_signOut:
                Toast.makeText(this, "Sign Out drawer menu clicked!", Toast.LENGTH_SHORT).show();
                sharedPreferencesManager.removeLoginDetails();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.drawer_menu_settings:
                Toast.makeText(this, "Settings drawer menu clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void closeFragmentIfShowing() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Add Label Fragment");
        if (fragment != null) {
            fragment.getFragmentManager().popBackStack();
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

//    @Override
//    public void update(Observable observable) {
//        this.noteList = ((ObservableNotes) observable).getListOfNotes();
//        notesAdapter.notifyDataSetChanged();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getNotesBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getArchiveNotesListBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(trashNotesBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getReminderNotesListBroadcastReceiver);
    }


    // Broadcast receivers
    public BroadcastReceiver getArchiveNotesListBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: local broadcast working fot archive list");

            if (intent.hasExtra("archiveNoteList")) {
                noteList = (List<NoteResponseModel>)
                        intent.getSerializableExtra("archiveNoteList");
                notesAdapter.setNoteModelArrayList(noteList);
                notesAdapter.notifyDataSetChanged();
            }

        }
    };

    public BroadcastReceiver getTrashNotesListBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: local broadcast working for trash notes list");
            if (intent.hasExtra("trashNotesList")) {
                noteList = (List<NoteResponseModel>) intent.getSerializableExtra("trashNotesList");
                notesAdapter.setNoteModelArrayList(noteList);
                notesAdapter.notifyDataSetChanged();
            }

        }
    };


    private BroadcastReceiver getNotesBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Local Broadcast is working in dashboard");
            if (intent.hasExtra("noteList")) {
                noteList = (List<NoteResponseModel>)
                        intent.getSerializableExtra("noteList");
                noteViewModel.addListOfNote(noteList);
                notesAdapter.setNoteModelArrayList(noteViewModel.getAllNoteData());
                Log.e(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: " + noteList);
                notesAdapter.notifyDataSetChanged();
            }
        }
    };

    private BroadcastReceiver getReminderNotesListBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Local Broadcast is working for Reminders List");
            if (intent.hasExtra("reminderNotesList")) {
                noteList = (List<NoteResponseModel>)
                        intent.getSerializableExtra("reminderNotesList");
                notesAdapter.setNoteModelArrayList(noteList);
                notesAdapter.notifyDataSetChanged();
            }

        }
    };
}
