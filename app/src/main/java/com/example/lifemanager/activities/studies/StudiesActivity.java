package com.example.lifemanager.activities.studies;

import static com.example.lifemanager.tools.Util.deletionDialog;
import static com.example.lifemanager.tools.Util.showToastIfEnabled;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
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
        titleIconAppbar = getResources().getString(R.string.title_appbar_studies_form);
        resourceType = getResources().getStringArray(R.array.categories)[1];
        formAddClass = AddStudyActivity.class;
        super.onCreate(savedInstanceState);
        roomStudiesDAO = LifeManagerDatabase.getInstance(this).getRoomStudiesDAO();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomStudiesDAO.getStudyById(chosenId));
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Studies study = (Studies) objects.get(0);
                if (item.getTitle().equals(getResources().getString(R.string.context_menu_delete_option))){
                    AlertDialog deletionDialog = deletionDialog(context, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItemFromRecyclerView(study);
                        }
                    });
                    deletionDialog.show();
                }else {
                    Intent intent = new Intent(context, AddStudyActivity.class);
                    intent.putExtra(resourceType,study);
                    startActivity(intent);
                }
            }
        }).execute();
        return super.onContextItemSelected(item);
    }

    private void deleteItemFromRecyclerView(Studies study) {
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                roomStudiesDAO.delete(study);
                return null;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                showToastIfEnabled(getApplicationContext(),getResources().getString(R.string.delete_study_toast_message));
                configureAdapter();
            }
        }).execute();
    }

    protected void configureAdapter() {
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.addAll(roomStudiesDAO.getAllStudies());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                List<Studies> studies = new ArrayList<>();
                for (Object object : objects){
                    studies.add((Studies) object);
                }
                adapter = new ListStudiesAdapter(context, studies, new ListStudiesAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(Studies study) {
                        Intent intent = new Intent(getApplicationContext(), DetailedStudyActivity.class);
                        intent.putExtra(resourceType,study);
                        startActivity(intent);
                    }
                });
                recyclerViewResources.setAdapter(adapter);
                registerForContextMenu(recyclerViewResources);
            }
        }).execute();
    }

}