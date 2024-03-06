package com.xupt3g.ordersview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

<<<<<<< HEAD
=======
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "ordersView/OrdersActivity")
>>>>>>> 85312de7e7f0ad8772144c6a7452b387e7032e8e
public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
    }
}