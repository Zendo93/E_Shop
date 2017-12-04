package sk.stuba.fei.uamt.e_shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MyOrdersActivity extends AppCompatActivity {

    private UIChanger uiChanger;
    private String userEmail;
    private OrdersToShowTask mOrdersToShowTask;
    private LinearLayout orders;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        userEmail = getIntent().getStringExtra("userEmail");
        uiChanger = new UIChanger(null);
        orders = (LinearLayout) findViewById(R.id.orders);
        progressBar = (ProgressBar) findViewById(R.id.orders_progress);
        mOrdersToShowTask = new OrdersToShowTask(this,uiChanger,orders, progressBar, userEmail);
        mOrdersToShowTask.execute((Void)null);
    }
}
