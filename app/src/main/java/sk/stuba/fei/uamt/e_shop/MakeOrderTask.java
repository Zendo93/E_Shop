package sk.stuba.fei.uamt.e_shop;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Zendo on 3.12.2017.
 */

public class MakeOrderTask extends AsyncTask<Void,Void,Boolean> {
    private Order order;
    private TextView view;
    private APIService apiService;
    private Context context;

    public MakeOrderTask(Context context, TextView view, Order order){
        this.context = context;
        this.order = order;
        this.view = view;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        Log.e("order",order.getAction());
        Log.e("order",order.getEmail());
        Log.e("order",order.getProduct_id());
        apiService = RestClient.getClient().create(APIService.class);
        try {
            createOrder(order);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean sucess) {
        super.onPostExecute(sucess);
        if (sucess){
            lowerPrice(view);
            Toast toast = Toast.makeText(context,R.string.order_sucess,Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(context,R.string.timeout_warning,Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void createOrder(Order order) throws IOException {
        Call<Void> call = apiService.createOrder(order);
        call.execute().body();
    }

    private void lowerPrice(TextView view){
        String pieces[] = view.getText().toString().split(" ");
        int count = Integer.parseInt(pieces[2]);
        --count;
        view.setText("Na sklade: " + Integer.toString(count));
    }
}
