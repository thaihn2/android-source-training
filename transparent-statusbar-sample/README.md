# TransparentStatusBarSample

https://medium.com/@manishgiri/android-transparent-status-bar-part-1-989e16b11785

# Overview

# Transparent Status Bar with Navigation Drawer and Coordinator Layout

## With Navigation Drawer

* Khi làm việc với giao diện của Navigation Drawer, thông thường sẽ cần phải làm trong suốt thanh status bar để nhìn thấy giao diện được đồng bộ hơn.

* Sử dụng attribute **android:fitsSystemWindows=”true”** đặt trong xml để làm cho thanh status bar được trong suốt với phần nội dung hiển thị của view được chen vào đó.

```
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

    <include
        layout="@layout/app_bar_nav"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <android.support.design.widget.NavigationView
        android:id="@+id/navigationView"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/nav_header"
        app:menu="@menu/nav_menu" />

</android.support.v4.widget.DrawerLayout>
```

* Sử dụng thêm style để setup cho Activity một thuộc tính **android:windowDrawsSystemBarBackgrounds** thành true. Thuộc tính này cho biết liệu cửa sổ này có chịu trách nhiệm vẽ background cho thanh system bar hay không. Nếu giá trị là true và của sổ không đổi, các thanh hệ thống sẽ được vẽ với nền trong suốt và cá khu vực tương ứng sẽ được lấp đầy bằng các màu được chỉ định trong **android.R.attr#statusBarColor** và **android.R.attr#navigationBarColor**. 

```
<style name="AppTheme.TransparentTheme">
        <item name="android:windowDrawsSystemBarBackgrounds">true</item>
        <item name="android:statusBarColor">@android:color/transparent</item>
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
    </style>

    <style name="AppTheme.PopupOverlay" parent="ThemeOverlay.AppCompat.Light" />

    <style name="AppTheme.AppBarOverlay" parent="ThemeOverlay.AppCompat.Dark.ActionBar" />
```

* Thông thường khi sử dụng transparent, phần status bar sẽ có phần làm mờ và không được hoàn toàn hiển thị background bên dưới.

![](https://i.stack.imgur.com/Nz7G2.jpg)

* Để xử lý vấn đề này, sử dụng FLAG **FLAG_LAYOUT_NO_LIMITS** ở window của activity.

```
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    activity.window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}
```

* Cũng có thể thay đổi màu của các icon ở trên status bar bằng cách sử dụng **View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR**.

```
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

// Or add in to style
<item name="android:windowLightStatusBar">true</item>
```

* Thay đổi background của status bar sử dụng phương thức **setStatusBarColor()**.

```
activity.window.statusBarColor = Color.YELLOW
```


## With CollapsingToolbar

* Collapsing Toolbar là một giao diện mới được miêu tả như hình dưới đây:

![](http://arnaud-camus.fr/assets/media/collapsing_header_animated.gif)

* Cũng như việc sử dụng với Navigation Drawer, Collapsing Toolbar cũng phải sử dụng thuộc tính **android:fitsSystemWindows="true"** cho AppBarLayout.

```
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    ...
    android:fitsSystemWindows="true">

    <android.support.design.widget.AppBarLayout
        ...
        android:fitsSystemWindows="true">

        <android.support.design.widget.CollapsingToolbarLayout
            ...
            android:background="@android:color/transparent"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:expandedTitleMarginEnd="64dp"
            app:expandedTitleMarginStart="48dp"
            app:layout_scrollFlags="scroll|exitUntilCollapsed"
            app:statusBarScrim="@android:color/transparent">

            <android.support.constraint.ConstraintLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:fitsSystemWindows="true">

                ...
            </android.support.constraint.ConstraintLayout>

            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_collapseMode="pin" />
        </android.support.design.widget.CollapsingToolbarLayout>
    </android.support.design.widget.AppBarLayout>

    <android.support.v4.widget.NestedScrollView
        ...
        app:layout_behavior="@string/appbar_scrolling_view_behavior">
        
        ...
        </android.support.constraint.ConstraintLayout>
    </android.support.v4.widget.NestedScrollView>
</android.support.design.widget.CoordinatorLayout>
```

* Như trên ví dụ, chúng ta phải set đồng thời thuộc tính **android:fitsSystemWindows="true"** cho các container và appbar layout, collapsing toolbar để có thể sử dụng vùng hiển thị của status bar.

## With another version Android

> Xử lý việc transparent status bar sẽ khác nhau đối với từng version của Android. Vì vậy phải xử lý các version cũ khi không có sự cải thiện từ thư viện support ở các API cao hơn.

### Từ Android KITKAT (API 19) trở lên

* Chỉ sử dụng cho API từ 19 trở lên, thêm flag **FLAG_TRANSLUCENT_STATUS** để thực hiện:

```
protected void setStatusBarTranslucent(boolean makeTranslucent) {
    if (makeTranslucent) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    } else {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
```

* Kết quả sẽ như sau, khi thanh status bar transparent thì layout sẽ sử dụng chiều cao của nó làm layout, vì vậy sẽ bị vỡ layout đang thiết kế.

![](https://i.stack.imgur.com/IqgD5.png)

* Để xử lý việc này cách đầu tiên có thể xử lý nhanh nhất là thêm thuộc tính **android:fitsSystemWindows="true"** vào layout container.

* Cách thứ 2 có thể xử lý là lấy ra layout và set padding cho layout để nội dung không bị tràn lên.

```
protected void setStatusBarTranslucent(boolean makeTranslucent) {
        View v = findViewById(R.id.bellow_actionbar);
        if (v != null) {
            int paddingTop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? MyScreenUtils.getStatusBarHeight(this) : 0;
            TypedValue tv = new TypedValue();
            getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true);
            paddingTop += TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            v.setPadding(0, makeTranslucent ? paddingTop : 0, 0, 0);
        }

        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
```

### Dưới Android KITKAT

* Theo như phía chính thức của Android thì việc này là không thể đối với các phiên bản android dưới KITKAT

* Nhưng một số hãng điện thoại như Samsung hay Sony đã thêm tùy chọn này trong phần mềm của họ. Có một triển khai riêng của class View với các flag **SYSTEM_UI_**.

* Để kiểm tra xem có tồn tại các cờ đó không, hãy xem ví dụ dưới đây:

```
fun resolveTransparentStatusBarFlag(activity: Activity): Int {
    activity.packageManager?.systemSharedLibraryNames?.let { libs ->
        var reflect: String? = null
        libs.forEach { lib ->
            if (lib.equals("touchwiz")) {
                reflect = "SYSTEM_UI_FLAG_TRANSPARENT_BACKGROUND"
            } else if (lib.startsWith("com.sonyericsson.navigationbar")) {
                reflect = "SYSTEM_UI_FLAG_TRANSPARENT"
            }
        }

        reflect?.let {
            try {
                val field = View::class.java.getField(it)
                if (field.type == Integer.TYPE) {
                    return field.getInt(null)
                }
                return 0
            } catch (ex: Exception) {
                ex.printStackTrace()
                return 0
            }
        }

        return 0
    }

    return 0
}
```

* Sau đó mới sử dụng flag đã lấy rồi apply vào decorView.

```
activity.window?.decorView?.systemUiVisibility = resolveTransparentStatusBarFlag(activity)
``` 

# Theme and Style

* Muốn tạo một style nào đó cho một view hoặc là activity, chúng ta có thể tự tạo 1 style mới hoàn toàn hoặc là extend các style có sẵn sau đó thay đổi lại một vài thuộc tính có trong đó.

* Một style tự tạo sẽ phải điều chỉnh rất nhiều thông số để có thể được như ý người tạo. ví dụ như:

```
<style name="CustomTextView">
    <item name="android:textColor">#00FF00</item>
    <item name="android:typeface">monospace</item>
    <item name="android:textSize">30sp</item>
    <item name="android:background">#ffffff</item>
</style>
```

* Nhưng nếu mà sử dụng việc custom style, thông qua thuộc tính **parent** thì sẽ đơn giản hơn rất nhieefuf, chỉ phải điều chỉnh sự cần thiết của một vài thuộc tính.

```
<style name="ExtendTextView" parent="@android:style/TextAppearance">
    <item name="android:textSize">10dp</item>
</style>
```

* Việc xử lý style không tương thích với android version, có thể giải quyết được bằng cách tạo nhiều thư mục values và thêm các style có chứa thuộc tính theo từng API Android. Ví dụ có một thuộc tính chỉ trong API 23 trở lên mới có, mà minSDK lại là 21, vậy nếu không tạo ra một thư mục values mới sẽ dẫn đến không hoạt động hoặc là chết ứng dụng.

* Những style thông thường nên extend từ thư viện **Android Support Library** để có thể hỗ trợ cho Android 4.0 trở lên. Các thư viện trong Support Library thường có tên giống trong các phiên bản nhưng có thêm **AppCompat** và được bắt đầu bằng **@android/**.

* **Theme** là sử dụng các style để quy định chung cho các activity hoặc là toàn bộ ứng dụng. Để áp dụng cho toàn bộ ứng dụng, thay theme cho thẻ **application** còn nếu muốn áp dụng cho activity thì thêm vào từng thẻ tương ứng. 

```
<application
        ...
        android:theme="@style/AppTheme">
        
<activity
    ...
    android:theme="@style/AppTheme.NoActionBar" />
```
