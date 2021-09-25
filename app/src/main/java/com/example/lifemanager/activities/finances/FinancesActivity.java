package com.example.lifemanager.activities.finances;


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
import com.example.lifemanager.async_tasks.FinancesAsyncTask;
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
        super.onCreate(savedInstanceState);
        roomFinanceDAO = LifeManagerDatabase.getInstance(this).getRoomFinanceDAO();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        new FinancesAsyncTask(new FinancesAsyncTask.FinancesAsyncTaskInterface() {
            @Override
            public List<Finance> doInBackground() {
                List<Finance> finances = new ArrayList<>();
                finances.add(roomFinanceDAO.getFinanceById(chosenId));
                return finances;
            }

            @Override
            public void onPostExecute(List<Finance> finances) {
                Finance finance = finances.get(0);
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
                    intent.putExtra("finance",finance);
                    startActivity(intent);
                }
            }
        }).execute();
        return super.onContextItemSelected(item);
    }

    private void deleteItemFromRecyclerView(Finance finance) {
        new FinancesAsyncTask(new FinancesAsyncTask.FinancesAsyncTaskInterface() {
            @Override
            public List<Finance> doInBackground() {
                roomFinanceDAO.delete(finance);
                return null;
            }

            @Override
            public void onPostExecute(List<Finance> finances) {
                showToast(getApplicationContext(), getResources().getString(R.string.delete_finance_toast_message));
                configureAdapter();
            }
        }).execute();
    }

    private void configureAdapter() {
        new FinancesAsyncTask(new FinancesAsyncTask.FinancesAsyncTaskInterface() {
            @Override
            public List<Finance> doInBackground() {
                return roomFinanceDAO.getAllFinances();
            }

            @Override
            public void onPostExecute(List<Finance> finances) {
                adapter = new ListFinancesAdapter(context, finances, new ListFinancesAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(Finance finance) {
                        Intent intent = new Intent(getApplicationContext(), DetailedFinanceActivity.class);
                        intent.putExtra("finance",finance);
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
        titleIconAppbar = getResources().getString(R.string.title_appbar_finance_form);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        formAddClass = AddFinanceActivity.class;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureAdapter();
    }
}