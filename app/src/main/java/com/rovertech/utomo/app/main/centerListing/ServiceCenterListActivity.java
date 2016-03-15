package com.rovertech.utomo.app.main.centerListing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

public class ServiceCenterListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtNoData;
    private MaterialSearchView searchView;
    private EditText edtArea;
    private Button btnSearch;
    private ImageView imgFilter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center_list);

        init();

    }

    private void init() {

        initSearch();

        initToolbar();

        edtArea = (EditText) findViewById(R.id.edtArea);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        txtNoData = (TextView) findViewById(R.id.txtNoData);
        imgFilter = (ImageView) findViewById(R.id.imgFilter);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTypeface();

    }

    private void setTypeface() {
        edtArea.setTypeface(Functions.getNormalFont(this));
        txtNoData.setTypeface(Functions.getBoldFont(this));
        btnSearch.setTypeface(Functions.getBoldFont(this));
    }

    private void initSearch() {
        searchView = (MaterialSearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("TAG_SUBMIT", query + " submitted");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("TAG_change", newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                txtCustomTitle.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                txtCustomTitle.setVisibility(View.VISIBLE);
            }
        });
        searchView.setHint("Search Service Centre");

        EditText editText = (EditText) searchView.findViewById(R.id.searchTextView);
        editText.setTypeface(Functions.getNormalFont(this));
        searchView.setAnimationDuration(100);
    }

    private void initToolbar() {

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Nearby Centres");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.center_listing_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(menuItem);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
