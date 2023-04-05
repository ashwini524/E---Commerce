package com.apps.ecommerceapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.ecommerceapp.R;
import com.apps.ecommerceapp.adapters.CategoryAdapter;
import com.apps.ecommerceapp.adapters.ProductAdapter;
import com.apps.ecommerceapp.databinding.ActivityMainBinding;
import com.apps.ecommerceapp.model.Category;
import com.apps.ecommerceapp.model.Product;
import com.apps.ecommerceapp.utils.Constants;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;


    ProductAdapter productAdapter;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCategories();
        initProducts();
        initSlider();


    }
    private void initSlider() {
//       binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/1655500326614.png","some caption"));
//       binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/1654101525743.png","some cap"));
//       binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/Available%20Best%20Interior%20Stuff%20Browse%20and%20Discover%20Now%20for%20Your%20Room.jpg","no cap"));
        getRecentOffers();
    }
    void  getRecentOffers(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")){
                    JSONArray offerArray = object.getJSONArray("news_infos");
                    for(int i =0; i < offerArray.length(); i++){
                        JSONObject childObj =  offerArray.getJSONObject(i);
                        binding.carousel.addData(
                                new CarouselItem(
                                        Constants.NEWS_IMAGE_URL + childObj.getString("image"),
                                        childObj.getString("title")
                                )
                        );
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },error -> {

        });
        queue.add(request);
    }

    private void initProducts() {
        products = new ArrayList<>();
//        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1654241353982.jpg","",11,12,1,1,2));
//        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1654241353982.jpg","",11,12,1,1,2));
//        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1654241353982.jpg","",11,12,1,1,2));
//        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1654241353982.jpg","",11,12,1,1,2));
//        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1654241353982.jpg","",11,12,1,1,2));
//        products.add(new Product("Korean Loose Short Cowboy Outerwear","https://tutorials.mianasad.com/ecommerce/uploads/product/1654241353982.jpg","",11,12,1,1,2));

        productAdapter = new ProductAdapter(this, products);

        getRecentProducts();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }
    void getRecentProducts() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = Constants.GET_PRODUCTS_URL + "?count=8";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")){
                    JSONArray productsArray = object.getJSONArray("products");
                    for(int i =0; i< productsArray.length(); i++) {
                        JSONObject childObj = productsArray.getJSONObject(i);
                        Product product = new Product(
                                childObj.getString("name"),
                                Constants.PRODUCTS_IMAGE_URL + childObj.getString("image"),
                                childObj.getString("status"),
                                childObj.getDouble("price"),
                                childObj.getDouble("price_discount"),
                                childObj.getInt("stock"),
                                childObj.getInt("id")

                        );
                        products.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> { });

        queue.add(request);
    }

    private void initCategories() {
        categories=new ArrayList<>();
        categoryAdapter =new CategoryAdapter(this, categories);
//        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#18ab4e","description",1));
//        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#18ab4e","description",1));
//        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#18ab4e","description",1));
//        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#18ab4e","description",1));
//        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#18ab4e","description",1));
//        categories.add(new Category("Sports & Outdoor","https://tutorials.mianasad.com/ecommerce/uploads/category/1651894605161.png","#18ab4e","description",1));

        getCategories();

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }
    void  getCategories(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request= new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e("err",response);
                try {
                    JSONObject mainObj = new JSONObject(response);
                    if (mainObj.getString("status").equals("success")){
                        JSONArray categoriesArray= mainObj.getJSONArray("categories");
                        for (int i=0; i <categoriesArray.length();i++){
                            JSONObject object= categoriesArray.getJSONObject(i);
                            Category category =new Category(
                                    object.getString("name"),
                                    Constants.CATEGORIES_IMAGE_URL + object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief"),
                                    object.getInt("id")
                            );
                            categories.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }else {
                        //nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}