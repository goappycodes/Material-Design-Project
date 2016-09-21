package com.example.xyzreader.ui;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import android.util.Log;


import com.example.xyzreader.R;

import com.example.xyzreader.data.ItemsContract;


/**
 * An activity representing a list of Articles. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link ArticleDetailActivity} representing item details. On tablets, the
 * activity presents a grid of items as cards.
 */
public class ArticleListActivity extends AppCompatActivity implements
        ArticleListFragment.onItemSelectedListener {

    private Toolbar mToolbar;
    private boolean isTwoPane = false;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    final String LOG_TAG = ArticleListActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        if (findViewById(R.id.detail_container) != null) {
            isTwoPane = true;
        }

    }


    @Override
    public void onItemSelected(long itemId) {
        Uri uri = ItemsContract.Items.buildItemUri(itemId);
        DynamicHeightNetworkImageView view = (DynamicHeightNetworkImageView) findViewById(R.id.thumbnail);

        if (isTwoPane) {
            Log.v(LOG_TAG, "Inside two pane " + itemId);
            ArticleDetailFragment articleDetailFragment = ArticleDetailFragment.newInstance(itemId);
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ft.addSharedElement(view, "transition_photo");

            }


            ft.replace(R.id.detail_container, articleDetailFragment);
            ft.commit();
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }
}
