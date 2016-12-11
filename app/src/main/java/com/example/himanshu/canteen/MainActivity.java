package com.example.himanshu.canteen;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.example.himanshu.canteen.R.id.card_view;
import static com.example.himanshu.canteen.R.id.card_view1;

public class MainActivity extends AppCompatActivity {
    private List<Order> orderList;
    private List<Shop> shopList;
    private RecyclerView recyclerView, recyclerView1;
    private OrderAdapter orderAdapter;
    private ShopAdapter shopAdapter;
    private CardView cardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardview = (CardView) findViewById(R.id.card_view1);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        shopList = new ArrayList<>();
        shopAdapter = new ShopAdapter(this, shopList);

        RecyclerView.LayoutManager oLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(oLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderAdapter);

        RecyclerView.LayoutManager sLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(sLayoutManager);
        recyclerView1.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(shopAdapter);

        prepareOrder();

        prepareShops();

    }

    private void prepareOrder() {
        Order order = new Order("J","Juice Shop","60");
        orderList.add(order);
        order = new Order("L","Laundry","40");
        orderList.add(order);
        order = new Order("F","Food Barn","220");
        orderList.add(order);

        orderAdapter.notifyDataSetChanged();
    }

    private void prepareShops() {
        int[] covers = new int[] {
                R.drawable.amul_shop,
                R.drawable.food_barn,
                R.drawable.juice_shop,
                R.drawable.laundry,
                R.drawable.pal
        };
        Shop s = new Shop(1, "Amul Shop", covers[0]);
        shopList.add(s);
        s = new Shop(2, "Food Barn", covers[1]);
        shopList.add(s);
        s = new Shop(3, "Juice Shop", covers[2]);
        shopList.add(s);
        s = new Shop(4, "Laundry", covers[3]);
        shopList.add(s);
        s = new Shop(5, "PAL", covers[4]);
        shopList.add(s);

        shopAdapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

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
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}


