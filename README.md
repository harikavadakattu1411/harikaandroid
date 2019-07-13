# Retrofit-Recyclerview

#Step 1: Add the following dependency
```sh
dependencies {
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.okhttp3:okhttp:3.3.1'
    compile 'com.android.support:recyclerview-v7:23.4.0'
}
```

#Step 2: Add internet permission in your manifest
```sh   
 <uses-permission android:name="android.permission.INTERNET"/>

```

#Step 3: Define interface with the url as shown below 
```sh
public interface MyApiEndpointInterface {

    @GET("/search/users")
    Call<User> getUsersNamedTom(@Query("q") String name);

}
```

**Setters and Getters for your json attribute**
```sh
public class User {
// Appropriate POJO's to JSON .
//You can use Schema2POJO And other tools for this.
}

**RecyclerView adapter**

```sh
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User.ItemsEntity> itemsEntities;


    public UserAdapter(List<User.ItemsEntity> itemsEntities) {
        this.itemsEntities = itemsEntities;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, int position) {

        final UserAdapter.MyViewHolder holder1 = holder;

        holder1.title.setText(itemsEntities.get(position).getGists_url());
        holder1.year.setText(itemsEntities.get(position).getLogin());
    }

    @Override
    public int getItemCount() {
        return itemsEntities.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            year = (TextView) view.findViewById(R.id.count);
        }
    }

}

```
**In your Activity you can instantiate the retrofit and okHTTP like as follows**

```sh
OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Accept", "Application/JSON").build();
                                return chain.proceed(request);
                            }
                        }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiEndpointInterface service = retrofit.create(MyApiEndpointInterface.class);
        
         Call<User> call = service.getUsersNamedTom("tom");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                Log.d("MainActivity", "Status Code = " + response.code());

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    Users = new ArrayList<>();
                    User result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));
                    Users = result.getItems();
                    Log.d("MainActivity", "Items = " + Users.size());

                    // This is where data loads
                    mAdapter = new UserAdapter(Users);


                    //attach to recyclerview
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
```

##Screens
![Screen_after_response](https://github.com/ashokslsk/Retrofit-Recyclerview/blob/master/screens/screen1.png)


## License

```
    Copyright 2016 Ashokslsk.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```

