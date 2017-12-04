package sk.stuba.fei.uamt.e_shop;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Zendo on 4.12.2017.
 */

public class OrdersToShowTask extends AsyncTask<Void, Void, Boolean> {
    private APIService apiService;
    private List<Orders> orders;
    private Context context;
    private UIChanger uiChanger;
    private LinearLayout ordersLayout;
    private ProgressBar mProgressView;
    private String userEmail;

    public OrdersToShowTask(Context context, UIChanger uiChanger, LinearLayout ordersLayout, ProgressBar mProgressView, String userEmail) {
        this.orders = new ArrayList<>();
        this.context = context;
        this.uiChanger = uiChanger;
        this.ordersLayout = ordersLayout;
        this.mProgressView = mProgressView;
        this.userEmail = userEmail;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        uiChanger.showProgress(true, ordersLayout, mProgressView);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        apiService = RestClient.getClient().create(APIService.class);
        try {
            Credentials credentials = new Credentials("get-my-orders", userEmail);

            orders = getMyOrders(credentials);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        uiChanger.showProgress(false, ordersLayout,mProgressView);
        if (success){
            displayOrders();
        }
        else {
            Toast toast = Toast.makeText(context,R.string.timeout_warning,Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private List<Orders> getMyOrders(Credentials credentials) throws IOException {
        Call<List<Orders>> call = apiService.getMyOrders(credentials);
        return call.execute().body();
    }

    private void displayOrders(){
        for (int i=0; i< orders.size();i++){
            ordersLayout.addView(uiChanger.generateOrder(orders.get(i), context, userEmail));
            ordersLayout.addView(uiChanger.generateLine(context));
        }
    }
}
