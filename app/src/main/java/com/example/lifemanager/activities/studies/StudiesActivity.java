package com.example.lifemanager.activities.studies;

import static com.example.lifemanager.tools.Util.areToastsEnabled;
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
import com.example.lifemanager.dao.RoomStudiesDAO;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.recycler_view.ListStudiesAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class StudiesActivity extends CategoryActivity {

    private RoomStudiesDAO roomStudiesDAO;
    private List<Studies> studies;
    private ListStudiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[1];
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        super.onCreate(savedInstanceState);
        roomStudiesDAO = LifeManagerDatabase.getInstance(this).getRoomStudiesDAO();
        configureAdapter();
        registerForContextMenu(recyclerViewResources);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        Studies study = roomStudiesDAO.getStudyById(chosenId);
        if (item.getTitle().equals(getResources().getString(R.string.context_menu_delete_option))){
            AlertDialog deletionDialog = deletionDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    roomStudiesDAO.delete(study);
                    showToast(getApplicationContext(),getResources().getString(R.string.delete_study_toast_message),
                            areToastsEnabled(getApplicationContext()));
                    configureAdapter();
                }
            });
            deletionDialog.show();
        }else {
            Intent intent = new Intent(this, AddStudyActivity.class);
            intent.putExtra("study",study);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    private void configureAdapter() {
        studies = roomStudiesDAO.getAllStudies();
        adapter = new ListStudiesAdapter(this, studies, new ListStudiesAdapter.OnClickListener() {
            @Override
            public void onItemClickListener(Studies study) {
                Intent intent = new Intent(getApplicationContext(), DetailedStudyActivity.class);
                intent.putExtra("study",study);
                startActivity(intent);
            }
        });
        recyclerViewResources.setAdapter(adapter);
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