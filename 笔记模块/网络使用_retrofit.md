### date

```sh
2022 0529编辑
```

### 待请求网络

```sh
https://api.unsplash.com/photos/random?client_id=3lypSQe2gg6xmYuUgdvJTjMIcGRg___DHpfc4A4_yuA&count=5

#baseurl
https://api.unsplash.com/

#get
photos/random

#query1
client_id=3lypSQe2gg6xmYuUgdvJTjMIcGRg___DHpfc4A4_yuA

#query2
count=5
```

### 库的使用

```groovy
    //build.gradle:Module

	implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.google.code.gson:gson:2.9.0'
```

### 允许使用网络

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 文件结构

```sh
# Model 是 网络请求返回后转化的数据格式 根据返回的json决定
- ImageModel
- ImageUrls #是ImageModel的一个属性

# 填入get/post和query等url参数
- UnsplashRetrofitApi
#填入baseurl 以及 做json转化
- UnsplashService
```

### 源文件路径

```sh
https://github.com/xinhuo2001/Andro_studio_study_project/tree/main/media_lesson
```

### 具体文件内容演示

#### 请求网络获取的json

```json
请求的url如下
https://api.unsplash.com/photos/random?client_id=3lypSQe2gg6xmYuUgdvJTjMIcGRg___DHpfc4A4_yuA&count=1

下面文件我们取出urls->regular urls->small 即可

[
    //删除了...
    {
        "urls": {
            "raw": "https://images.unsplash.com/photo-1653587106660-4908e9a7bae7?ixid=MnwzMzMwNzB8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTM4MjA0OTk&ixlib=rb-1.2.1",
            "full": "https://images.unsplash.com/photo-1653587106660-4908e9a7bae7?crop=entropy&cs=tinysrgb&fm=jpg&ixid=MnwzMzMwNzB8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTM4MjA0OTk&ixlib=rb-1.2.1&q=80",
            "regular": "https://images.unsplash.com/photo-1653587106660-4908e9a7bae7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMzMwNzB8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTM4MjA0OTk&ixlib=rb-1.2.1&q=80&w=1080",
            "small": "https://images.unsplash.com/photo-1653587106660-4908e9a7bae7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMzMwNzB8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTM4MjA0OTk&ixlib=rb-1.2.1&q=80&w=400",
            "thumb": "https://images.unsplash.com/photo-1653587106660-4908e9a7bae7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMzMwNzB8MHwxfHJhbmRvbXx8fHx8fHx8fDE2NTM4MjA0OTk&ixlib=rb-1.2.1&q=80&w=200",
            "small_s3": "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1653587106660-4908e9a7bae7"
        },
    //删除了...
]
```

#### ImageModel.java

```java
public class ImageModel {
    @SerializedName("urls")
    ImageUrls imageUrls;

    public String getRegular() {
        if(imageUrls == null) {
            return null;
        }
        return imageUrls.regularUrl;
    }
}
```

#### ImageUrls.java

```java
public class ImageUrls {
    @SerializedName("regular")
    String regularUrl;

    @SerializedName("small")
    String smallUrl;
}
```

#### UnsplashRetrofitApi.java

```java
public interface UnsplashRetrofitApi {
    @GET("photos/random")
    public Call<List<ImageModel>> randomPics(@Query("client_id") String clientId, @Query("count") int count);
}
```

#### UnsplashService.java

```java
public class UnsplashService {
    private static Retrofit retrofit;
    public static Retrofit get() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.unsplash.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
```

#### 调用

```java
	//发起网络请求 获取图片url 并把url加载到MyAdapter
    public void addImgUrlToAdapter(int count) {
        //网络请求设置图片
        UnsplashService.get().create(UnsplashRetrofitApi.class)
                .randomPics("aTDd1xsdCGMDF8Ew6rdqXnRczGvFmiWhpI-ZyQBbSgQ", count)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                        List<ImageModel> list = response.body();
					  //这里已经获取到了所有数据
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        Log.d("yzf", "net error");
                        Toast.makeText(MainActivity.this, "client_id被拉黑", Toast.LENGTH_SHORT);
                    }
                });
    }
```







