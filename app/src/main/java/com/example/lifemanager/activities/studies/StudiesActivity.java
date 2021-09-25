package com.example.lifemanager.activities.studies;

import static com.example.lifemanager.tools.Util.deletionDialog;
import static com.example.lifemanager.tools.Util.showToast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.async_tasks.StudiesAsyncTask;
import com.example.lifemanager.dao.RoomStudiesDAO;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.recycler_view.ListStudiesAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudiesActivity extends CategoryActivity {

    private RoomStudiesDAO roomStudiesDAO;
    private ListStudiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[1];
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        super.onCreate(savedInstanceState);
        roomStudiesDAO = LifeManagerDatabase.getInstance(this).getRoomStudiesDAO();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        new StudiesAsyncTask(new StudiesAsyncTask.StudiesAsyncTaskInterface() {
            @Override
            public List<Studies> doInBackground() {
                List<Studies> studies = new ArrayList<>();
                studies.add(roomStudiesDAO.getStudyById(chosenId));
                return studies;
            }

            @Override
            public void onPostExecute(List<Studies> studies) {
                if (item.getTitle().equals(getResources().getString(R.string.context_menu_delete_option))){
                    AlertDialog deletionDialog = deletionDialog(context, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItemFromRecyclerView(studies.get(0));
                        }
                    });
                    deletionDialog.show();
                }else {
                    Intent intent = new Intent(context, AddStudyActivity.class);
                    intent.putExtra("study",studies.get(0));
                    startActivity(intent);
                }
            }
        }).execute();
        return super.onContextItemSelected(item);
    }

    private void deleteItemFromRecyclerView(Studies study) {
        new StudiesAsyncTask(new StudiesAsyncTask.StudiesAsyncTaskInterface() {
            @Override
            public List<Studies> doInBackground() {
                roomStudiesDAO.delete(study);
                return null;
            }

            @Override
            public void onPostExecute(List<Studies> studies) {
                showToast(getApplicationContext(),getResources().getString(R.string.delete_study_toast_message));
                configureAdapter();
            }
        }).execute();
    }

    private void configureAdapter() {
        new StudiesAsyncTask(new StudiesAsyncTask.StudiesAsyncTaskInterface() {
            @Override
            public List<Studies> doInBackground() {
                return roomStudiesDAO.getAllStudies();
            }

            @Override
            public void onPostExecute(List<Studies> studies) {
                adapter = new ListStudiesAdapter(context, studies, new ListStudiesAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(Studies study) {
                        Intent intent = new Intent(getApplicationContext(), DetailedStudyActivity.class);
                        intent.putExtra("study",study);
                        startActivity(intent);
                    }
                });
                recyclerViewResources.setAdapter(adapter);
                registerForContextMenu(recyclerViewResources);
            }
        }).execute();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        titleIconAppbar = getResources().getString(R.string.title_appbar_studies_form);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        formAddClass = AddStudyActivity.class;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureAdapter();
    }

}