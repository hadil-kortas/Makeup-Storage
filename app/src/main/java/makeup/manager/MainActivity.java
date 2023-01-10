package makeup.manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import makeup.manager.data.InventoryDbHelper;
import makeup.manager.data.StockItem;
import makeup.manager.StockCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getCanonicalName();
    InventoryDbHelper dbHelper;
    StockCursorAdapter adapter;
    int lastVisibleItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("");
        dbHelper = new InventoryDbHelper(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        final ListView listView = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        Cursor cursor = dbHelper.readStock();

        adapter = new StockCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0) return;
                    final int currentFirstVisibleItem = view.getFirstVisiblePosition();
                    if (currentFirstVisibleItem > lastVisibleItem) {
                        fab.show();
                    } else if (currentFirstVisibleItem < lastVisibleItem) {
                        fab.hide();
                    }
                lastVisibleItem = currentFirstVisibleItem;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(dbHelper.readStock());
    }

    public void clickOnViewItem(long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("itemId", id);
        startActivity(intent);
    }

    public void clickOnSale(long id, int quantity) {
        dbHelper.sellOneItem(id, quantity);
        adapter.swapCursor(dbHelper.readStock());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                // add dummy data for testing
                addDummyData();
                adapter.swapCursor(dbHelper.readStock());
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Add data for demo purposes
     */
    private void addDummyData() {
        StockItem mascara = new StockItem(
                "Long Lasting & Panoramic Volume Mascara",
                "12,99 €",
                45,
                "KIKO",
                "+49 544 455 455",
                "kiko@gmail.com",
                "android.resource://makeup.manager/drawable/mascara_kiko");
        dbHelper.insertItem(mascara);

        StockItem lipstick = new StockItem(
                "Power Bullet Matte Lipstick",
                "11 €",
                74,
                "Huda Beauty",
                "+49 854 543 784",
                "HudaBeauty@gmail.com",
                "android.resource://makeup.manager/drawable/lipstick_huda_beauty");
        dbHelper.insertItem(lipstick);

        StockItem pallette = new StockItem(
                "Rose Quartz Eyeshadow Palette",
                "13 €",
                44,
                "Huda Beauty",
                "+49 854 543 784",
                "HudaBeauty@gmail.com",
                "android.resource://makeup.manager/drawable/eyeshadow_huda_beauty");
        dbHelper.insertItem(pallette);

        StockItem eyeliner = new StockItem(
                "EYELINER PEN BLUE EYED BABE",
                "20 €",
                34,
                "Kylie Cosmetices",
                "+49 548 216 658",
                "KylieCosmitics@gmail.com",
                "android.resource://makeup.manager/drawable/stassxkylie");
        dbHelper.insertItem(eyeliner);

        StockItem gloss = new StockItem(
                "BESITOS GLOSS DRIP",
                "12 €",
                26,
                "Kylie Cosmetics",
                "+49 548 216 658",
                "KylieCosmitics@gmail.com",
                "android.resource://makeup.manager/drawable/gloss_kylie");
        dbHelper.insertItem(gloss);

//        StockItem fresquito = new StockItem(
//                "Fresquito",
//                "9 €",
//                54,
//                "Fiesta S.A.",
//                "+34 000 000 0000",
//                "fiesta@dulce.com",
//                "android.resource://eu.admin.inventorymanager/drawable/fresquito");
//        dbHelper.insertItem(fresquito);
//
//        StockItem hotChillies = new StockItem(
//                "Hot chillies",
//                "13 €",
//                12,
//                "Fiesta S.A.",
//                "+34 000 000 0000",
//                "fiesta@dulce.com",
//                "android.resource://eu.admin.inventorymanager/drawable/hot_chillies");
//        dbHelper.insertItem(hotChillies);
//
//        StockItem lolipopStrawberry = new StockItem(
//                "Lolipop strawberry",
//                "12 €",
//                62,
//                "Fiesta S.A.",
//                "+34 000 000 0000",
//                "fiesta@dulce.com",
//                "android.resource://eu.admin.inventorymanager/drawable/lolipop");
//        dbHelper.insertItem(lolipopStrawberry);
//
//        StockItem heartGummy = new StockItem(
//                "Heart gummy jellies",
//                "13 €",
//                22,
//                "Fiesta S.A.",
//                "+34 000 000 0000",
//                "fiesta@dulce.com",
//                "android.resource://eu.admin.inventorymanager/drawable/heart_gummy");
//        dbHelper.insertItem(heartGummy);
    }
}
