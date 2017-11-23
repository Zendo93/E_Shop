package sk.stuba.fei.uamt.e_shop;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Zendo on 23.11.2017.
 */

public class UIChanger {
    private NavigationView navigationView;
    private boolean revertChanges;

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

    }

}
