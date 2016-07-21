package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.adapters.ImagesAdapter;
import luc.edu.neuroscienceapp.entities.CardImage;
import luc.edu.neuroscienceapp.entities.Global;

public class ImageExamplesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImagesAdapter adapter;
    private List<CardImage> cards;
    private Button btNatural, btArtificial, btGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        btNatural = (Button) findViewById(R.id.bt_natural);
        btArtificial = (Button) findViewById(R.id.bt_artificial);
        btGroups = (Button) findViewById(R.id.bt_groups);


        btNatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btNatural.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                btArtificial.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cardview_light_background));
                btGroups.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cardview_light_background));

                loadNatural();

            }
        });

        btArtificial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btNatural.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cardview_light_background));
                btArtificial.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                btGroups.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cardview_light_background));

                loadArtificial();

            }
        });

        btGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btNatural.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cardview_light_background));
                btArtificial.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cardview_light_background));
                btGroups.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

                loadGroups();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        initCollapsingToolbar();


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cards = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loadAll();

        try {
            Glide.with(this).load(R.drawable.real_receptive_fields).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbar.setTitle(getString(R.string.app_name));
//                    isShow = true;
//                } else if (isShow) {
//                    collapsingToolbar.setTitle(" ");
//                    isShow = false;
//                }
//            }
//        });
    }

    /**
     * Adding few albums for testing
     */
    private void loadAll() {
        cards.clear();

        int[] covers = Global.thumbnails;
        int[] covers_ica = Global.image_ica_thumbnails;
        String[] titles = Global.image_names;
        boolean[] labels = Global.natural_images;

        CardImage a = null;
        String lab = "";
        for (int i = 0; i < covers.length; i++) {
            if (labels[i]) {
                lab = "Natural Image";
            } else {
                lab = "Non-natural Image";
            }
            a = new CardImage(titles[i], covers[i], covers_ica[i], i, lab);
            cards.add(a);
        }

        adapter = new ImagesAdapter(getApplicationContext(), cards);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadGroups() {
        cards.clear();

        int[] covers = Global.image_groups_thumbnails;
        int[] covers_ica = Global.image_groups_ica_thumbnails;
        String[] titles = Global.image_groups_names;
        boolean[] labels = Global.natural_images;

        CardImage a = null;
        String lab = "";
        for (int i = 0; i < covers.length; i++) {
            lab = Global.IMAGE_GROUP;
            a = new CardImage(titles[i], covers[i], covers_ica[i], i, lab);
            cards.add(a);
        }

        adapter = new ImagesAdapter(getApplicationContext(), cards);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadNatural() {
        cards.clear();
        int[] covers = Global.thumbnails;
        int[] covers_ica = Global.image_ica_thumbnails;
        String[] titles = Global.image_names;
        boolean[] labels = Global.natural_images;

        CardImage a = null;
        String lab = "";
        for (int i = 0; i < covers.length; i++) {
            if (labels[i]) {
                lab = "Natural Image";
                a = new CardImage(titles[i], covers[i], covers_ica[i], i, lab);
                cards.add(a);
            }
        }
        adapter = new ImagesAdapter(getApplicationContext(), cards);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadArtificial() {
        cards.clear();
        int[] covers = Global.thumbnails;
        int[] covers_ica = Global.image_ica_thumbnails;
        String[] titles = Global.image_names;
        boolean[] labels = Global.natural_images;

        CardImage a = null;
        String lab = "";
        for (int i = 0; i < covers.length; i++) {
            if (!labels[i]) {
                lab = "Artificial Image";
                a = new CardImage(titles[i], covers[i], covers_ica[i], i, lab);
                cards.add(a);
            }
        }
        adapter = new ImagesAdapter(getApplicationContext(), cards);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.action_about) {
            Intent intentAbout = new Intent(ImageExamplesActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(ImageExamplesActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return true;
        }
        if (id == R.id.action_what_is) {
            Intent intent = new Intent(ImageExamplesActivity.this, WelcomeActivity.class);
            intent.putExtra("menu","menu");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
