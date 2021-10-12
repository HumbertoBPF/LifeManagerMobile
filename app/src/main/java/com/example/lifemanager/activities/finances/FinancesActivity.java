package com.example.lifemanager.activities.finances;

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
import com.example.lifemanager.dao.RoomFinanceDAO;
import com.example.lifemanager.model.Finance;
import com.example.lifemanager.recycler_view.ListFinancesAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.ArrayList;
import java.util.List;

public class FinancesActivity extends CategoryActivity {

    private RoomFinanceDAO roomFinanceDAO;
    private ListFinancesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[0];
        colorAppbar = getResources().getColor(R.color.color_finances_item);
        titleIconAppbar = getResources().getString(R.string.title_appbar_finance_form);
        resourceType = getResources().getStringArray(R.array.categories)[0];
        formAddClass = AddFinanceActivity.class;
        super.onCreate(savedInstanceState);
        roomFinanceDAO = LifeManagerDatabase.getInstance(this).getRoomFinanceDAO();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        loadingDialog.show();
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomFinanceDAO.getFinanceById(chosenId));
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Finance finance = (Finance) objects.get(0);
                loadingDialog.dismiss();
                if (item.getTitle().equals(getResources().getString(R.string.context_menu_delete_option))) {
                    AlertDialog deletionDialog = deletionDialog(context, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItemFromRecyclerView(finance);
                        }
                    });
                    deletionDialog.show();
                }else {
                    Intent intent = new Intent(context,AddFinanceActivity.class);
                    intent.putExtra(resourceType,finance);
                    startActivity(intent);
                }
            }
        }).execute();
        return super.onContextItemSelected(item);
    }

    private void deleteItemFromRecyclerView(Finance finance) {
        loadingDialog.show();
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                roomFinanceDAO.delete(finance);
                return null;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                showToastIfEnabled(getApplicationContext(), getResources().getString(R.string.delete_finance_toast_message));
                configureAdapter();
                loadingDialog.dismiss();
            }
        }).execute();
    }

    protected void configureAdapter() {
        loadingDialog.show();
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.addAll(roomFinanceDAO.getAllFinances());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                List<Finance> finances = new ArrayList<>();
                for (Object object : objects){
                    finances.add((Finance) object);
                }
                adapter = new ListFinancesAdapter(context, finances, new ListFinancesAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(Finance finance) {
                        Intent intent = new Intent(getApplicationContext(), DetailedFinanceActivity.class);
                        intent.putExtra(resourceType,finance);
                        startActivity(intent);
                    }
                });
                recyclerViewResources.setAdapter(adapter);
                registerForContextMenu(recyclerViewResources);
                loadingDialog.dismiss();
            }
        }).execute();
    }

}