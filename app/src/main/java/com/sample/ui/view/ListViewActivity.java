package com.sample.ui.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sample.ui.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    List<Product> m_sampleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        //建立資料
        setSampleData();
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new ProductAdapter(m_sampleList, R.layout.view_product_circle));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //取得資料的方式1  須先在 Adapter 的 view 上設定tag
                Product product = (Product) view.getTag();
                //取得資料的方式2 須先在 Adapter 複寫 getItem()
//                Product product =(Product) listView.getAdapter().getItem(position);
                if (null != product) {
                    //外開連結
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(product.productUrl));
                    startActivity(intent);
                }
            }
        });
    }

    class Product {
        String productName;
        String oriPrice;
        String discPrice;
        String imageUrl;
        String productUrl;
    }

    //view 與 data間的橋樑
    class ProductAdapter extends BaseAdapter {

        List<Product> productList = new ArrayList<>();
        int resId = -1;

        public ProductAdapter(List<Product> productList, int resId) {
            this.productList = productList;
            this.resId = resId;
        }

        //宣告會使用到的元件
        class ViewHolder {
            TextView name;
            TextView oriPrice;
            TextView discPrice;
            ImageView image;
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Product getItem(int position) {
            return productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater _LayoutInflater = LayoutInflater.from(ListViewActivity.this);
            convertView = _LayoutInflater.inflate(resId, null);

            if (convertView != null) {
                Product item = productList.get(position);
                //setTag 可設定各種類型資料
                convertView.setTag(item);
                ViewHolder holder = new ViewHolder();
                holder.name = convertView.findViewById(R.id.name);
                holder.oriPrice = convertView.findViewById(R.id.oriPrice);
                holder.discPrice = convertView.findViewById(R.id.discPrice);
                holder.image = convertView.findViewById(R.id.image);

                holder.name.setText(item.productName);
                holder.oriPrice.setText("原價＄" + item.oriPrice);
                holder.discPrice.setText("售價＄" + item.discPrice);

                Glide.with(ListViewActivity.this)
                        .load(item.imageUrl)
                        .placeholder(R.drawable.sample)
                        .into(holder.image);

            }
            return convertView;
        }
    }

    private void setSampleData() {
        try {
            JSONArray jsonArray = new JSONArray(sampleData);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.optJSONObject(i);
                Product product = new Product();
                product.productName = obj.optString("productName");
                product.oriPrice = obj.optString("oriPrice");
                product.discPrice = obj.optString("discPrice");
                product.imageUrl = obj.optString("imageUrl");
                product.productUrl = obj.optString("productUrl");
                m_sampleList.add(product);
            }
        } catch (Exception e) {
        }
    }

    private String sampleData = "[\n" +
            "    {\n" +
            "      \"productName\": \"Galaxy S22 Ultra(12G/256G)\",\n" +
            "      \"oriPrice\": \"38900\",\n" +
            "      \"discPrice\": \"38900\",\n" +
            "      \"imageUrl\": \"https://tspimg.tstartel.com/upload/material/21/43669/mie_202202131754370.png\",\n" +
            "      \"productUrl\": \"https://emall.tstartel.com/eMall/product/66614\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productName\": \"Galaxy S22+(8G/256G)\",\n" +
            "      \"oriPrice\": \"31900\",\n" +
            "      \"discPrice\": \"31900\",\n" +
            "      \"imageUrl\": \"https://tspimg.tstartel.com/upload/material/26/43674/mie_202202131725150.png\",\n" +
            "      \"productUrl\": \"https://emall.tstartel.com/eMall/product/66615\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productName\": \"Galaxy S22(8G/256G)\",\n" +
            "      \"oriPrice\": \"26900\",\n" +
            "      \"discPrice\": \"26900\",\n" +
            "      \"imageUrl\": \"https://tspimg.tstartel.com/upload/material/34/43682/mie_202202131635300.png\",\n" +
            "      \"productUrl\": \"https://emall.tstartel.com/eMall/product/66617\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productName\": \"V23 5G (8GB/128GB)\",\n" +
            "      \"oriPrice\": \"15990\",\n" +
            "      \"discPrice\": \"14990\",\n" +
            "      \"imageUrl\": \"https://tspimg.tstartel.com/upload/material/109/43245/mie_202201111333220.png\",\n" +
            "      \"productUrl\": \"https://emall.tstartel.com/eMall/product/66280\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"productName\": \"Y76 5G\",\n" +
            "      \"oriPrice\": \"9990\",\n" +
            "      \"discPrice\": \"8490\",\n" +
            "      \"imageUrl\": \"https://tspimg.tstartel.com/upload/material/62/42942/mie_202112151408100.png\",\n" +
            "      \"productUrl\": \"https://emall.tstartel.com/eMall/product/66051\"\n" +
            "    }\n" +
            "  ]\n";
}