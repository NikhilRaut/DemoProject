package labscat.paytmkaro;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    PaytmPGService servicePay = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        servicePay = PaytmPGService.getStagingService();
//or
        servicePay = PaytmPGService.getProductionService();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                demo();
            }
        });
    }


    private void demo() {

        //Create new order Object having all order information.
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("REQUEST_TYPE", "DEFAULT");
        paramMap.put("ORDER_ID", "ORDER12345");
        paramMap.put("MID", "DIY12386817555501617");
        paramMap.put("CUST_ID", "CUST110");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("WEBSITE", "paytm");
        paramMap.put("TXN_AMOUNT", "100.0");
        paramMap.put("THEME ", "merchant");

        PaytmOrder Order = new PaytmOrder(paramMap);
        //Create new Merchant Object having all merchant configuration.
        PaytmMerchant Merchant = new PaytmMerchant("https://pguat.paytm.com/DIYtesting/", "https://pguat.paytm.com/DIYtesting/");

        //Set PaytmOrder and PaytmMerchant objects. Call this method and set both objects before starting transaction.
        servicePay.initialize(Order, Merchant, null);

//Start the Payment Transaction. Before starting the transaction ensure that initialize method is called.
        servicePay.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionSuccess(Bundle bundle) {
                Log.e("Paytm", "onTransactionSuccess -- " + bundle.toString());
            }

            @Override
            public void onTransactionFailure(String s, Bundle bundle) {
                Log.e("Paytm", "onTransactionFailure -- " + bundle.toString());
            }

            @Override
            public void networkNotAvailable() {
                Log.e("Paytm", "networkNotAvailable -- " );
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e("Paytm", "clientAuthenticationFailed -- " + s.toString());
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e("Paytm", "someUIErrorOccurred -- " + s.toString());
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e("Paytm", "onErrorLoadingWebPage -- " + s.toString());
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e("Paytm", "onBackPressedCancelTransaction -- ");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
