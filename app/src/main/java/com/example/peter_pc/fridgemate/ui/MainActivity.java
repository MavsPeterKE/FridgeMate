package com.example.peter_pc.fridgemate.ui;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter_pc.fridgemate.R;
import com.example.peter_pc.fridgemate.db.ProductModel;
import com.example.peter_pc.fridgemate.viewmodels.ProductViewModel;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener {
    protected  @BindView(R.id.scanBarcode)      Button    btnScanBarcode;
    protected  @BindView(R.id.scandate)         Button    btnScanDate;
    protected  @BindView(R.id.itemName)         EditText  edtItemName;
    protected  @BindView(R.id.tvitemHeader)     TextView  tvItemHeader;
    protected  @BindView(R.id.tvSavedItems)     TextView  tvSavedProducts;
    protected  @BindView(R.id.tvBcodeHeader)    TextView  tvBcodeHeader;
    protected  @BindView(R.id.dateView)         TextView   tvExpDate;
    protected  @BindView(R.id.fab)              FloatingActionButton fab;
    protected  @BindView(R.id.datePicker)       DatePicker dtpicker;
    private Date date;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private static String TAG="BarcodeResult";
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final int RC_OCR_CAPTURE = 9003;
    private ProductViewModel productViewModel;
    private String barCode,expiryDate;
    private  String expiryTime;
    private  static final String CHANNEL_ID="Ex01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        datePickerDialog=new DatePickerDialog(this,MainActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        productViewModel= ViewModelProviders.of(this).get(ProductViewModel.class);

    }

    public void notifyUser(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.badge_icon)
                .setContentTitle("Product Expiry")
                .setContentText("Several Fruits due. Act First")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }

    @OnClick(R.id.tvSavedItems)
    public void onTvSaveClick(){
        startActivity(new Intent(this,RecyclerListActivity.class));
    }

    @OnClick(R.id.fab)
           public void onFloatButtonClick(){
                String item=edtItemName.getText().toString().trim();
                if (!item.equals("")||!item.isEmpty()) {
                    productViewModel.saveProduct(new ProductModel(item,barCode,expiryTime, "486 Remaining"));
                }else {
                    edtItemName.setError("Product Name Requirred");
                }
    }

    @OnClick({R.id.scandate,R.id.scanBarcode})
           public void onButtonClick(View v){
              switch (v.getId()){
                  case R.id.scanBarcode:
                     startBarcodeScanner();
                      break;
                  case R.id.scandate:
                      startOcrScanner();
                      //startActivity(new Intent(this,RecyclerListActivity.class));
                      break;

              }
    }

    private void startBarcodeScanner(){
               Intent intent=new Intent(this,BarcodeCaptureActivity.class);
               startActivityForResult(intent,RC_BARCODE_CAPTURE);
    }

    private void startOcrScanner(){
        Intent intent=new Intent(this,OcrCaptureActivity.class);
        startActivityForResult(intent,RC_OCR_CAPTURE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {

            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Toast.makeText(this, "Scanned Successfully", Toast.LENGTH_SHORT).show();
                    tvBcodeHeader.setVisibility(View.VISIBLE);
                    tvBcodeHeader.setText("Barcode: "+barcode.displayValue);
                    tvItemHeader.setVisibility(View.VISIBLE);
                    tvItemHeader.setText("ItemName: "+"Kasuku Book 200pgs");
                    barCode=barcode.displayValue;
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                } else {
                    tvBcodeHeader.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Bracode. Failed To Scan", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Toast.makeText(this, "Error Scanning", Toast.LENGTH_SHORT).show();
            }
        }else  if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    Toast.makeText(this, "Scanned Successfully"+text, Toast.LENGTH_SHORT).show();
                    //tvItemHeader.setVisibility(View.VISIBLE);
                    //tvItemHeader.setText("Ocr: "+text);
                    expiryDate=text;
                    Log.d(TAG, "Text read: " + text);
                } else {
                    tvItemHeader.setVisibility(View.GONE);
                    Toast.makeText(this, "Scanned Failed", Toast.LENGTH_SHORT).show();
                    datePickerDialog.show();
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                Toast.makeText(this, "Error Scanning", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();
        expiryTime= Objects.toString(date.getTime(),"0");
    }
}

