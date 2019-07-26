package com.bridgelabz.fundoo.Dashboard.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.bridgelabz.fundoo.ObserverPattern.Observable;
import com.bridgelabz.fundoo.ObserverPattern.ObservableNotes;
import com.bridgelabz.fundoo.ObserverPattern.Observer;
import com.bridgelabz.fundoo.label_page.view.AddLabelFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.fundoo.Dashboard.Model.Note;
import com.bridgelabz.fundoo.Dashboard.View.Adapter.NotesAdapter;
import com.bridgelabz.fundoo.Dashboard.ViewModel.NoteViewModel;
import com.bridgelabz.fundoo.LoginSignup.View.LoginActivity;
import com.bridgelabz.fundoo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, Observer {

    private static final String TAG = "DashboardActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    TextView mTextTakeNote;
    RecyclerView mRecyclerView;
    private List<Note> noteList = new ArrayList<>();
    NoteViewModel noteViewModel;
    NotesAdapter notesAdapter;
    NavigationView navigationView;
    Observable<List<Note>> observableNotes = new ObservableNotes(noteList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noteViewModel = new NoteViewModel(this);
        prepareRecyclerView();
        registerObserverForNotes();
        setNavigationViewListener();
        setUpDrawer();
        setUpNavigationView();
        setOnClickTakeNote();
    }

    private void registerObserverForNotes() {
        observableNotes.registerObserver(this);
    }

    private void setUpDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout_dashboard);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    private boolean deleteNote(Note noteAt) {
        return noteViewModel.deleteNote(noteAt);
    }


    private void prepareRecyclerView() {
        observableNotes = noteViewModel.fetchAllNotes();
        noteList = ((ObservableNotes) observableNotes).getListOfNotes();
//        noteList = noteViewModel.getAllNoteData();
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);

        // for Staggered Grid View
        mRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        // for Recycler view
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notesAdapter = new NotesAdapter(noteList, new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int notePosition) {
                Intent intent = new Intent(DashboardActivity.this, AddNoteActivity.class);
                intent.putExtra("noteToEdit", noteList.get(notePosition));
                startActivity(intent);
                Log.e(TAG, "the position of note is " + notePosition);
            }
        });
        mRecyclerView.setAdapter(notesAdapter);
        noteItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private ItemTouchHelper noteItemTouchHelper = new ItemTouchHelper
            (new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
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
                    int adapterPosition = swipedViewHolder.getAdapterPosition();
                    Log.e(TAG, "The adapter position is " + adapterPosition);
                    Note noteToDelete = notesAdapter.getNoteAt(adapterPosition);
                    boolean isDeleted = deleteNote(noteToDelete);
                    if (isDeleted) {
                        noteList.remove(noteToDelete);
                        notesAdapter.notifyItemRemoved(adapterPosition);
                        Toast.makeText(DashboardActivity.this, "Item Deleted",
                                Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(DashboardActivity.this, "Item could not be deleted",
                                Toast.LENGTH_SHORT).show();
                    }
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
                finish();
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
            TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
            ImageView imageProfile = navigationView.getHeaderView(0).findViewById(R.id.profile_pic);
            tvEmail.setText(emailId);
            Glide.with(this).load(imageUrl).circleCrop().into(imageProfile);
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
                noteList = noteViewModel.getAllNoteData();
                notesAdapter.setNoteModelArrayList(noteList);
                notesAdapter.notifyDataSetChanged();
                break;
            case R.id.drawer_menu_reminders:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Reminders drawer menu clicked!", Toast.LENGTH_SHORT).show();
                noteList = noteViewModel.getReminderNotes();
                notesAdapter.setNoteModelArrayList(noteList);
                notesAdapter.notifyDataSetChanged();
                break;
            case R.id.drawer_menu_archives:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Archive drawer menu clicked!", Toast.LENGTH_SHORT).show();
                noteList = noteViewModel.getArchivedNotes();
                notesAdapter.setNoteModelArrayList(noteList);
                notesAdapter.notifyDataSetChanged();
                break;

            case R.id.drawer_menu_pinned:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Pinned drawer menu clicked!", Toast.LENGTH_SHORT).show();
                noteList = noteViewModel.getPinnedNotes();
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
                noteList = noteViewModel.getTrashedNotes();
                notesAdapter.setNoteModelArrayList(noteList);
                notesAdapter.notifyDataSetChanged();
                break;
            case R.id.drawer_menu_signOut:
                Toast.makeText(this, "Sign Out drawer menu clicked!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void update(Observable observable) {
        this.noteList = ((ObservableNotes) observable).getListOfNotes();
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observableNotes.unregisterObserver(this);
    }
}
