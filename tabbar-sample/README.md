# TabBarSample

## Overview

* Khi bạn muốn chuyển đổi các chế độ xem khác nhau trong ứng dụng của bạn, hãy sử dụng TabLayout có trong thư viện **android.support.design** mà google cung cấp cho các lập trình viên.

* TabLayout được thiết kế để thêm các tab được scroll theo chiều ngang nếu như có nhiều tab trong 1 màn hình.

* Thông thường TabLayout hay được sử dụng với ViewPager để người dùng dễ dàng chuyển tab bằng cách vuốt chứ không phải mặc định là nhấn vào từng tab.

## Implement

### Basic TabLayout

* Thông thường TabLayout được kết hợp với ViewPager để tạo ra các tab khác nhau có thể thao tác vuốt hoặc là click.

* Thêm thư viện Support Design vào dependencies

```
implementation 'com.android.support:design:28.0.0'
```

* Thêm thẻ TabLayout vào bên trong giao diện chứa các tabs

```
<android.support.design.widget.TabLayout
        android:id="@+id/tabMain"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

* Tạo thêm các tab bằng cách sử dụng phương thức **addTab()** và **newTab()**, có thể thêm các thuộc tính như Text, Icon cho tab khi tạo

```
tabMain.addTab(tabMain.newTab().setText("Home").setIcon(R.drawable.ic_home_24dp))
```

* Khi đã hiển thị được các tab, chúng ta cần xử lý các nội dung thay đổi khi chuyển qua lại các tab bằng sự kiện **OnTabSelectedListener**.

```
tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
```
> Thông thường những nội dung cần thay đổi sẽ được hiển thị qua các Fragment được hiển thị luôn bên trong Activity và việc add hay replace fragment dựa vào các sự kiện chuyển tab.

* Bạn có thể điều chỉnh được tab nào sẽ được select đầu tiên thông qua phương thức **select()**.

```
tabMain.getTabAt(1)?.select()
```

### TabLayout with ViewPager
> Thông thường khi làm việc với tab, chúng ta sẽ có nhu cầu di chuyển giữa các tab bằng cách vuốt ngang màn hình qua lại, lúc này TabLayout sẽ kết hợp với ViewPager để dễ dàng thao tác hơn.

* Thêm thẻ ViewPager vào trong activity chứa TabLayout, có thể để chúng tách riêng hoặc là TabLayout lồng bên trong của ViewPager.

```
<android.support.v4.view.ViewPager
        android:layout_width="match_parent"
        android:layout_height="match_parent" >

        <android.support.design.widget.TabLayout
            android:id="@+id/tabMain"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    </android.support.v4.view.ViewPager>
```

* Tiếp đó, để implement ViewPager, chúng ta cần tạo 1 Adapter chứa danh sách các Fragment tương ứng với các tab. Adapter này được implement từ **FragmentPagerAdapter** hoặc **FragmentStatePagerAdapter** để quản lý việc lưu trữ các fragment và giữ trạng thái các fragment. Xem thêm tại [đây](https://github.com/oHoangNgocThai/ViewPagerState)

```
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val mFragments = arrayListOf<Fragment>()
    private val mTitles = arrayListOf<String>()

    override fun getCount(): Int = mFragments.size

    override fun getItem(position: Int): Fragment = mFragments[position]

    fun addFragment(fragment: Fragment, title: String) {
        mFragments.add(fragment)
        mTitles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}
```

* Thực hiện thêm các tab vào bên trong adapter của ViewPager là các instance của Fragment tương ứng. 

```
private fun setupViewPager(viewPager: ViewPager) {
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(HomeFragment.getInstance("Home Content"), "Home")
        pagerAdapter.addFragment(SearchFragment.getInstance("Search Content"), "Search")
        pagerAdapter.addFragment(UserFragment.getInstance("User Content"), "User")
        viewPager.adapter = pagerAdapter
    }
```

* Cài đặt ViewPager cho TabLayout bằng phương thức **setupWithViewPager**.

```
tabMain.setupWithViewPager(viewPager)
```

* Khi di chuyển qua lại giữa các tab, nếu có nhu cầu muốn giữ lại trạng thái của người dùng thì ở bên trong Fragment nên sử dụng phương thức **onSaveInstanceState()** để lưu trữ trạng thái người dùng, bên cạnh đó adapter của ViewPager phải implement từ **FragmentStatePagerAdapter**. Xem thêm tại [đây](https://github.com/oHoangNgocThai/ViewPagerState)

* Sử dụng listener OnPageChangeListener của ViewPager để lắng nghe được sự kiện chuyển pager và xử lý thích hợp khi pager được chọn.

```
viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
            }
        })
```

### Custom TabLayout

* Bạn có thể điều chỉnh chế độ bố trí của tab bằng thuộc tính **tabMode** với các thông số như: 

    * **fixed**: Trong trường hợp cố định, các tab sẽ chia đều và chiếm hết chiều rộng của màn hình, nó chỉ thích hợp với số lượng tab ít và title của tab không quá dài.
    * **scrollable**: Trường hợp này thì có thể scroll sang ngang nếu như số lượng tab nhiều và title của tab dài.

* Bạn có thể điều chỉnh vị trí của tab như nằm ở giữa hoặc là chiếm hết chiều rộng màn hình bằng thuộc tính **tabGravity**. Thuộc tính này họat động khi thuộc tính **tabMode** là fixed.

    * **center**: Nếu có ít tab thì vị trí của tab sẽ nằm ở giữa màn hình theo chiều rộng
    * **fill** trải dài cả màn hình theo chiều rộng.
    
* Để thêm icon vào trong các tab khi sử dụng với ViewPager, ta lấy ra từng tab thông qua phương thức **getTabAt()** và truyền vào đó position muốn lấy, sau đó có thể set lại các thuộc tính như title hoặc icon tương ứng.

```
tabMain.getTabAt(TabType.HOME.position)?.setIcon(tabIcons[TabType.HOME.position])
```

* Trường hợp bạn muốn tab chỉ bao gồm các icon, override phương thức **getPagerTitle()** và trả về giá trị null

```
override fun getPageTitle(position: Int): CharSequence? {
        return null
    }
```

* Trường hợp muốn custom lại view từng tab của TabLayout, bạn sẽ phải tạo ra một layout bên ngoài rồi sử dụng phương thức **setCustomView** của TabLayout để set lại cho TabLayout.

```
val viewHome = LayoutInflater.from(this).inflate(R.layout.item_tabs, null).apply {
    textNotify.visibility = View.VISIBLE
    textTitle.text = "Home"
    imageIcon.setImageResource(R.drawable.ic_home_24dp)
}
tabMain.getTabAt(TabType.HOME.position)?.customView = viewHome
```

* Ở dưới các tab được select, mặc định sẽ có 1 thanh indicator với màu sắc mặc định của ứng dụng, ta có thể thay đổi màu sắc của nó hoặc là ẩn đi bằng các thuộc tính **tabIndicatorColor** hoặc **tabIndicatorHeight** được setup bên trong xml hoặc code.

```
app:tabIndicatorHeight="0dp"
app:tabIndicatorColor="@color/colorPrimary"
```
