package sk.stuba.fei.uamt.e_shop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zendo on 23.11.2017.
 */

public class UIChanger {
    private NavigationView navigationView;

    public UIChanger(NavigationView navigationView){
        this.navigationView = navigationView;
    }

    public void changeUISignIn(Intent data){
        View hView =  navigationView.getHeaderView(0);
        hView.setVisibility(View.VISIBLE);
        TextView name = (TextView)hView.findViewById(R.id.user_name);
        name.setText(data.getExtras().getString("name"));
        TextView email = (TextView)hView.findViewById(R.id.user_email);
        email.setText(data.getExtras().getString("email"));
        Menu hMenu = navigationView.getMenu();
        MenuItem item = (MenuItem) hMenu.findItem(R.id.nav_registration);
        item.setVisible(false);
        item = (MenuItem) hMenu.findItem(R.id.nav_signIn);
        item.setVisible(false);
        item = (MenuItem) hMenu.findItem(R.id.nav_sign_out);
        item.setVisible(true);
        item = (MenuItem) hMenu.findItem(R.id.nav_shopping_cart);
        item.setVisible(true);
        item = (MenuItem) hMenu.findItem(R.id.nav_my_orders);
        item.setVisible(true);
    }

    public void changeUISignout(){
        View hView =  navigationView.getHeaderView(0);
        hView.setVisibility(View.GONE);
        TextView name = (TextView)hView.findViewById(R.id.user_name);
        name.setText("");
        TextView email = (TextView)hView.findViewById(R.id.user_email);
        email.setText("");
        Menu hMenu = navigationView.getMenu();
        MenuItem item = (MenuItem) hMenu.findItem(R.id.nav_registration);
        item.setVisible(true);
        item = (MenuItem) hMenu.findItem(R.id.nav_signIn);
        item.setVisible(true);
        item = (MenuItem) hMenu.findItem(R.id.nav_sign_out);
        item.setVisible(false);
        item = (MenuItem) hMenu.findItem(R.id.nav_shopping_cart);
        item.setVisible(false);
        item = (MenuItem) hMenu.findItem(R.id.nav_my_orders);
        item.setVisible(false);

    }

    public LinearLayout generateProduct(final Product product, final Context context, final String userEmail){

        LinearLayout productLayout = new LinearLayout(context);
        productLayout.setOrientation(LinearLayout.HORIZONTAL);
        productLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout productChild1 = new LinearLayout(context);
        productChild1.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lpChild1_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpChild1_2.setMargins(dpToPx(context,10),dpToPx(context,10),dpToPx(context,10),dpToPx(context,10));
        productChild1.setLayoutParams(lpChild1_2);

        final TextView productTitle = new TextView(context);
        LinearLayout.LayoutParams lpProductTitle = new LinearLayout.LayoutParams(dpToPx(context,240), LinearLayout.LayoutParams.WRAP_CONTENT);
        productTitle.setText(product.getTitle());
        productTitle.setLayoutParams(lpProductTitle);

        TextView productDescription = new TextView(context);
        LinearLayout.LayoutParams lpProductDescription = new LinearLayout.LayoutParams(dpToPx(context,240), LinearLayout.LayoutParams.WRAP_CONTENT);
        productDescription.setText("Popis: " + product.getDescription());
        productDescription.setLayoutParams(lpProductDescription);

        final TextView productPrice = new TextView(context);
        LinearLayout.LayoutParams lpProductPrice = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        productPrice.setText("Cena: " + product.getPrice() + " \u20ac");
        productPrice.setLayoutParams(lpProductPrice);


        //add productTitle, product description, productPrice
        productChild1.addView(productTitle);
        productChild1.addView(productDescription);
        productChild1.addView(productPrice);


        LinearLayout productChild2 = new LinearLayout(context);
        productChild2.setOrientation(LinearLayout.VERTICAL);
        productChild2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


        final TextView count = new TextView(context);
        LinearLayout.LayoutParams lpCount = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpCount.gravity = Gravity.END;
        lpCount.setMargins(0,dpToPx(context,10),dpToPx(context,10),0);
        count.setLayoutParams(lpCount);
        count.setText("Na sklade: " + product.getCount());

        ImageButton selector = new ImageButton(context);
        LinearLayout.LayoutParams lpSelector = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpSelector.gravity = Gravity.END;
        lpSelector.setMargins(0,dpToPx(context,5),0,0);
        selector.setLayoutParams(lpSelector);
        selector.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_more_vert));
        selector.setId(Integer.parseInt(product.getID()));
        if (userEmail != null && Integer.parseInt(product.getCount()) != 0) {
            selector.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("selector", product.getTitle());
                            generateOptionsForProduct(context, product.getTitle(), count, product.getID(), userEmail).show();
                        }
                    });
        }



        //add selector, count
        productChild2.addView(selector);
        productChild2.addView(count);

        //add prodcutChild1, productChild2
        productLayout.addView(productChild1);
        productLayout.addView(productChild2);

        return productLayout;

    }

    public LinearLayout generateOrder(final Orders order, final Context context, final String userEmail){

        LinearLayout orderLayout = new LinearLayout(context);
        orderLayout.setOrientation(LinearLayout.HORIZONTAL);
        orderLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout orderChild1 = new LinearLayout(context);
        orderChild1.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lpChild1_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpChild1_2.setMargins(dpToPx(context,10),dpToPx(context,10),dpToPx(context,10),dpToPx(context,10));
        orderChild1.setLayoutParams(lpChild1_2);

        final TextView orderTitle = new TextView(context);
        LinearLayout.LayoutParams lpProductTitle = new LinearLayout.LayoutParams(dpToPx(context,240), LinearLayout.LayoutParams.WRAP_CONTENT);
        orderTitle.setText(order.getTitle());
        orderTitle.setLayoutParams(lpProductTitle);

        TextView orderDescription = new TextView(context);
        LinearLayout.LayoutParams lpProductDescription = new LinearLayout.LayoutParams(dpToPx(context,240), LinearLayout.LayoutParams.WRAP_CONTENT);
        orderDescription.setText("Popis: " + order.getDescription());
        orderDescription.setLayoutParams(lpProductDescription);

        /*final TextView productPrice = new TextView(context);
        LinearLayout.LayoutParams lpProductPrice = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        productPrice.setText("Cena: " + order.getPrice() + " \u20ac");
        productPrice.setLayoutParams(lpProductPrice);*/


        //add productTitle, product description, productPrice
        orderChild1.addView(orderTitle);
        orderChild1.addView(orderDescription);
       // productChild1.addView(productPrice);


        LinearLayout orderChild2 = new LinearLayout(context);
        orderChild2.setOrientation(LinearLayout.VERTICAL);
        orderChild2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


        final TextView count = new TextView(context);
        LinearLayout.LayoutParams lpCount = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpCount.gravity = Gravity.END;
        lpCount.setMargins(0,dpToPx(context,10),dpToPx(context,10),0);
        count.setLayoutParams(lpCount);
        count.setText("Počet: " + order.getItem_count());

        /*ImageButton selector = new ImageButton(context);
        LinearLayout.LayoutParams lpSelector = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpSelector.gravity = Gravity.END;
        lpSelector.setMargins(0,dpToPx(context,5),0,0);
        selector.setLayoutParams(lpSelector);
        selector.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_more_vert));
        selector.setId(Integer.parseInt(order.getID()));
        if (userEmail != null && Integer.parseInt(order.getCount()) != 0) {
            selector.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("selector", order.getTitle());
                            generateOptionsForProduct(context, order.getTitle(), count, order.getID(), userEmail).show();
                        }
                    });
        }*/



        //add selector, count
        //productChild2.addView(selector);
        orderChild2.addView(count);

        //add prodcutChild1, productChild2
        orderLayout.addView(orderChild1);
        orderLayout.addView(orderChild2);

        return orderLayout;

    }

    public TextView generateLine(Context context){
        TextView line = new TextView(context);
        line.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        line.setHeight(dpToPx(context,1));
        line.setBackground(ContextCompat.getDrawable(context,R.color.grey));
        return line;
    }

    private int dpToPx(Context context ,int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show, final View productsLayout, final ProgressBar mProgressView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;

            productsLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            productsLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    productsLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            productsLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private Dialog generateOptionsForProduct(final Context context, String productTitle, final TextView productCount, final String productId, final String userEmail){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(productTitle)
                .setItems(R.array.options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1){
                            createOrder(productCount,productId,userEmail,context);
                        }
                    }
                });
      return  builder.create();
    }

    private void createOrder(TextView productCount, String productId, String userEmail, Context context){
        Order order = new Order("create-order",userEmail,productId);
        MakeOrderTask mOderTask = new MakeOrderTask(context,productCount,order);
        mOderTask.execute((Void)null);
    }
}
