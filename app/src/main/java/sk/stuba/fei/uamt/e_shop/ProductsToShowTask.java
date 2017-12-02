package sk.stuba.fei.uamt.e_shop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Zendo on 2.12.2017.
 */

public class ProductsToShowTask extends AsyncTask<Void, Void, Boolean> {

    private APIService apiService;
    private List<Product> products;
    private Context context;
    private UIChanger uiChanger;
    private LinearLayout productsLayout;
    private ProgressBar mProgressView;

    ProductsToShowTask(Context context, UIChanger uiChanger, LinearLayout productsLayout, ProgressBar mProgressView){
        this.context = context;
        products = new ArrayList<>();
        this.uiChanger = uiChanger;
        this.productsLayout = productsLayout;
        this.mProgressView = mProgressView;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        uiChanger.showProgress(true, productsLayout, mProgressView);
        apiService = RestClient.getClient().create(APIService.class);
        try {
            products = getProducts("get-products");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        uiChanger.showProgress(false, productsLayout,mProgressView);
        if (success){
            displayProducts();
        }
        else {
            Toast toast = Toast.makeText(context,R.string.timeout_warning,Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private List<Product> getProducts(String action) throws IOException {
        Call<List<Product>> call = apiService.getProducts(action);
        return call.execute().body();
    }

    private void displayProducts(){
        for (int i=0; i<products.size();i++){
            productsLayout.addView(uiChanger.generateProduct(products.get(i), context));
            productsLayout.addView(uiChanger.generateLine(context));
        }
    }

}
