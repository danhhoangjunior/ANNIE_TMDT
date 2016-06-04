package com.dhyuka.tmdt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhyuka.tmdt.json.DownLoadDataJSON;
import com.dhyuka.tmdt.models.DesProduct;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {

    String url = "http://annieandroid.somee.com/api/getDesProduc?_maSP=";
    String name, maSP;
    TextView txtName, txtDetail, txtAddToCart;
    ProgressBar progress_loading;
    Button btnOpenDetail, btnCloseDetail, btnOpenAddToCart, btnCloseAddToCart;
    LinearLayout lnDetail, lnAddToCart;
    RelativeLayout lnImage;
    DesProduct desProduct;
    Animation animSide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        maSP = bundle.getString("maSP");
        name = bundle.getString("name");

        this.setTitle(name);
        progress_loading = (ProgressBar) findViewById(R.id.progress_loading);
        progress_loading.setVisibility(View.VISIBLE);

        txtDetail = (TextView) findViewById(R.id.txtDetail);
        txtName = (TextView) findViewById(R.id.txtName);
        txtAddToCart = (TextView) findViewById(R.id.txtAddToCart);

        btnOpenDetail = (Button) findViewById(R.id.btnOpenDetail);
        btnOpenAddToCart = (Button) findViewById(R.id.btnOpenAddToCart);
        btnCloseDetail = (Button) findViewById(R.id.btnCloseDetail);
        btnCloseAddToCart = (Button) findViewById(R.id.btnCloseAddToCart);
        btnCloseAddToCart.setOnClickListener(this);
        btnCloseDetail.setOnClickListener(this);
        btnOpenDetail.setOnClickListener(this);
        btnOpenAddToCart.setOnClickListener(this);

        lnImage = (RelativeLayout) findViewById(R.id.lnImage);
        lnDetail = (LinearLayout) findViewById(R.id.lnDetail);
        lnAddToCart = (LinearLayout) findViewById(R.id.lnAddToCart);

        DownLoadDataJSON dataJSON = new DownLoadDataJSON(new DownLoadDataJSON.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                loadDesProduct(output);
            }
        });
        dataJSON.execute(url + maSP);
    }

    private void loadDesProduct(String output) {
        ParseDataJSON parseDataJSON = new ParseDataJSON();
        desProduct = parseDataJSON.getDesProduct(output);

        if (desProduct.getMaSP() != null) {
            progress_loading.setVisibility(View.GONE);
            txtName.setText(desProduct.getMaSP() + "\n"
                    + desProduct.getDsHinhAnh());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnOpenDetail) {
            animSide = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_in);
            lnDetail.setVisibility(View.VISIBLE);
            lnDetail.startAnimation(animSide);
            lnImage.setVisibility(View.GONE);

            txtDetail.setText(desProduct.getThongTinChiTiet());

        } else if (v == btnOpenAddToCart) {
            animSide = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_in);
            lnAddToCart.setVisibility(View.VISIBLE);
            lnAddToCart.startAnimation(animSide);
            lnImage.setVisibility(View.GONE);

            txtAddToCart.setText(desProduct.getFullSize());

        } else if (v == btnCloseDetail) {
            animSide = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_out);
            lnDetail.startAnimation(animSide);
            lnDetail.setVisibility(View.GONE);
            lnImage.setVisibility(View.VISIBLE);

        } else if (v == btnCloseAddToCart) {
            animSide = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up_out);
            lnAddToCart.startAnimation(animSide);
            lnAddToCart.setVisibility(View.GONE);
            lnImage.setVisibility(View.VISIBLE);
        }
    }
}
