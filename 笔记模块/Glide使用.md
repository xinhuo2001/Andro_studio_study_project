### date

```sh
2022 0529
```



### 导包

```gr
implementation 'com.github.bumptech.glide:glide:4.13.0'
```

### 使用

```java
//基本用法
Glide.with(holder.imageView.getContext()) //with 填入组件上下文
    .load(url)							//load填入图片url
    .centerCrop()						//centerCrop 表示填满显示屏
    .into(holder.imageView);			 //into 表示图片插入到哪个地方

//清空ImageView
Glide.with(holder.imageView.getContext()).clear(holder.imageView);
```

