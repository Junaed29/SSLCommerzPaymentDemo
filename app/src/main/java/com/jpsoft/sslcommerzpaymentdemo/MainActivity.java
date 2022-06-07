package com.jpsoft.sslcommerzpaymentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jpsoft.sslcommerzpaymentdemo.databinding.ActivityMainBinding;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCShipmentInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

public class MainActivity extends AppCompatActivity implements SSLCTransactionResponseListener {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization
                ("beyon629e1ff536418","beyon629e1ff536418@ssl", 100,
                        SSLCCurrencyType.BDT,"123456", "yourProductType", SSLCSdkType.TESTBOX);

        final SSLCCustomerInfoInitializer customerInfoInitializer = new
                SSLCCustomerInfoInitializer("customer name","customer email",
                "address","dhaka", "1214", "Bangladesh","phoneNumber");

        final SSLCProductInitializer productInitializer = new SSLCProductInitializer ("food", "food",
                new SSLCProductInitializer.ProductProfile.TravelVertical("Travel", "10",
                        "A", "12", "Dhk-Syl"));
        final SSLCShipmentInfoInitializer shipmentInfoInitializer = new SSLCShipmentInfoInitializer
                ("Courier", 2, new SSLCShipmentInfoInitializer.ShipmentDetails("AA","Address 1","Dhaka","1000","BD"));

        binding.paymentButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntegrateSSLCommerz
                        .getInstance(MainActivity.this)
                        .addSSLCommerzInitialization(sslCommerzInitialization)
                        .addCustomerInfoInitializer(customerInfoInitializer)
                        .addProductInitializer(productInitializer)
                        .buildApiCall(MainActivity.this);
            }
        });
    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {

        binding.resultTextView.setText("connection: "+sslcTransactionInfoModel.getAPIConnect()+"Status :"+sslcTransactionInfoModel.getStatus() );
    }

    @Override
    public void transactionFail(String s) {
        s = "Transaction Failed : "+s;
        binding.resultTextView.setText(s);
    }

    @Override
    public void merchantValidationError(String s) {
        s = "Validation Error : "+s;
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }
}