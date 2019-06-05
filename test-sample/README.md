# TestSample
Learn about Test in Android

# Overview

* Android Studio được thiết kế để tạo ra testing một cách dễ dàng. Với một vài click, bạn đã có thể cài đặt JUnit và chạy trên JVM hoặc trên một thiết bị cụ thể.
* Tất nhiên bạn cũng có thể mở rộng khả năng testing như tích hợp [Mockito](https://github.com/mockito/mockito) để test API, [Espresso](https://developer.android.com/training/testing/#Espresso) hoặc [UI Automator](https://developer.android.com/training/testing/#UIAutomator) để test tương tác người dùng. Bạn có thể tạo ra Espresso một cách tự động sử dụng [Espresso Test Recorder](https://developer.android.com/studio/test/espresso-test-recorder.html).

## Test types and location

Vị trí của code test phụ thuộc vào loại test mà bạn đang viết. Android Studio cung cấp các thư mục source code cho 2 loại test:

* `Local unit tests`

    * Nằm trong thư mục **module-name/src/test/java/**
    * Là các test chạy trên JVM(Java Vitual Machine), sử dụng các test này để giảm thiểu thời gian thực hiện khi các test của bạn không phụ thuộc vào Android framework hoặc khi bạn có thể giả định phụ thuộc của Android Framework.
    * Trong khi chạy, các test này được thực hiện với phiên bản sử đổi của **android.jar**. Điều này cho phép bạn sử dụng các thư viện mocking, giống như **Mockito**.
    
* `Instrumented tests`
    
    * Nằm trong thư mục **module-name/src/androidTest/java/**.
    * Những bài test này chạy trên thiết bị thật hoặc là máy ảo. Các bài test này có quyền truy cập vào API của thiết bị, cung cấp cho bạn quyền truy cập các thông tin như Context đang kiểm tra và kiểm soát ứng dụng được test từ code test.
    * Thực hiện các bài test này khi viết tích hợp và kiểm tra giao diện để tự động hóa tương tác của người dùng hoặc các bài test khác phụ thuộc Android mà các đối tượng giả không thể tạo ra.
    * Bởi vì các bài test được tích hợp vào APK(tách biệt với APK ứng dụng của bạn), nên chúng ta phải có tệp **AndroidMenifest.xml** của riêng nó. Tuy nhiên Gradle tự động tạo tệp này trong quá trình xây dựng để nó không hiển thị trong source code của bạn. Nếu cần bạn có thể thêm tệp manifest vào thư mục test.

## Create Local Test

* Thêm dependency của **junit** và **mockito** vào trong project để sử dụng trong khi viết test.

```
testImplementation 'junit:junit:4.12'
testImplementation 'org.mockito:mockito-core:1.10.19'
```

## JUnit Test

* Ở đây có hàm kiểm tra xem chuỗi nhập vào có phải là định dạng Email hay không, sẽ viết Unit Test cho hàm này. 

```
class EmailValidator {

    companion object {
        private val EMAIL_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        )

        fun isValid(email: CharSequence?): Boolean {
            return email != null && EMAIL_PATTERN.matcher(email).matches()
        }
    }
}
```

* Trước khi viết test, phải xác định xem có những trường hợp nào của việc test trên, ở đây là check xem chuỗi nhập vào có phải là định dạng email không, sẽ có một số các trường hợp sau:

    * Nhập vào đúng sẽ là: test@gmailcom
    * Email có dạng subdomain: test@gmail.co.uk
    * Không có .com: test@gmail
    * Có nhiều ký tự: test@gmail..com
    * Không có tên người dùng: @gmail.com
    * Không có trường nhập vào
    * Giá trị bị null

* Tạo file EmailValidatorTest trong thư mục app/test/java/..., đây là thư mục chứa các test local unit test.

```
class EmailValidatorTest {

    @Test
    fun emailValidator_CorrectInput_ReturnsTrue() {
        Assert.assertTrue(EmailValidator.isValid("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        Assert.assertTrue(EmailValidator.isValid("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValid("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValid("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValid("@email.com"))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValid(null))
    }
}
```

* Một số chú ý có thể bạn chưa biết về các annotation trên:

    * **@Test**: Được cung cấp bởi JUnit Framework để đánh dấu 1 phương thức là một trường hợp test.
    * **assertTrue()**: Là một phương thức để khẳng định giá trị bên trong nó là TRUE. Nếu bên trong là sai thì đây là một test case sai.
    * **assertFalse()**: Cũng tương tự như assertTrue, nếu trường hợp sai thì test đúng và ngược lại.

## Mockito Test

* Mockito là một thư viện của JAVA dùng để test các ứng dụng code bằng Java. Nó được sử dụng để có thể tạo và sử dụng các đối tượng giả để cung cấp các đối tượng giả cung cấp phụ thuộc cho class đang được test.
* Sau khi xây dựng việc test cho class EmailValidator, tiếp theo hãy giả lập để test được class SharedPreferenceHelper sau:

```
class SharedPreferencesHelper(private val mPreference: SharedPreferences) {

    companion object {
        const val KEY_NAME = "key_name"
        const val KEY_DOB = "key_dob"
        const val KEY_EMAIL = "key_email"
    }

    fun savePersonalInfo(userInfo: UserInfo): Boolean {
        mPreference.edit()?.let { editor ->
            editor.putString(KEY_NAME, userInfo.name)
            editor.putLong(KEY_DOB, userInfo.dateOfBirth)
            editor.putString(KEY_EMAIL, userInfo.email)

            return editor.commit()
        }
        return false
    }

    fun getPersonalInfo(): UserInfo {
        val name = mPreference.getString(KEY_NAME, "")
        val dobMillisecond = mPreference.getLong(KEY_DOB, Calendar.getInstance().timeInMillis)
        val email = mPreference.getString(KEY_EMAIL, "")

        return UserInfo(name, dobMillisecond, email)
    }
}
```

* Như các bạn thấy, việc thực hiện test cho class trên sẽ trải qua bước khởi tạo đối tượng, sau đó sử dụng dữ liệu ghi vào SharedPreference và sau đó lại đọc dữ liệu ra để xem có giống nhau hay không.

* Đầu tiên, phải tạo được đối tượng SharedPreference để thao tác trên đó. Ở đây sẽ sử dụng `when` có trong thư viện Mockito để phân biệt ra các trường hợp cụ thể trong đó.

```
private fun createMockSharedPreference(): SharedPreferencesHelper? {
    // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written correctly.
    `when`(mMockSharedPreferences?.getString(eq(SharedPreferencesHelper.KEY_NAME), anyString())).thenReturn(mUserInfo?.name)
    `when`(mMockSharedPreferences?.getLong(eq(SharedPreferencesHelper.KEY_DOB), anyLong())).thenReturn(mUserInfo?.dateOfBirth)
    `when`(mMockSharedPreferences?.getString(eq(SharedPreferencesHelper.KEY_EMAIL), anyString())).thenReturn(mUserInfo?.email)

    // Mocking a successful commit.
    `when`(mMockEditor?.commit()).thenReturn(true)

    // Return the MockEditor when requesting it
    `when`(mMockSharedPreferences?.edit()).thenReturn(mMockEditor)
    return mMockSharedPreferencesHelper
}

private fun createMockBrokenSharedPreference(): SharedPreferencesHelper? {
    `when`(mMockBrokenEditor?.commit()).thenReturn(false)

    // Return the broken MockEditor when requesting it
    `when`(mMockBrokenPreference?.edit()).thenReturn(mMockBrokenEditor)

    return mMockBrokenPreference?.let { SharedPreferencesHelper(it) }
}
```
> ở đây hàm **SharedPreferenceHelper** cần đối số là một đối tượng của **SharedPreference**, vì vậy chúng ta cần tạo ra đối số này với annotation **@Mock**. Lưu ý rằng chúng ta đang tạo ra 2 phiên bản của **SharedPrefenrece**, một trường hợp cho việc ghi thành công và một cho việc ghi thất bại. Việc ghi lại sai có thể là do cung cấp khóa sai, context sai, ...

* Để khởi tạo được đối tượng trước khi xử lý các hàm test, phải lưu ý khởi tạo nó ở bước trên. Ở đây ta sử dụng annotation **@Before** để chỉ việc trước khi test sẽ thực hiện nó trước.

```
@Before
fun initMocks() {
    // create entity
    mUserInfo = UserInfo(TEST_NAME, TEST_DATE_OF_BIRTH.timeInMillis, TEST_EMAIL)

    // create a mocked SharedPreferences
    mMockSharedPreferencesHelper = createMockSharedPreference()

    // Create a mocked SharedPreferences that fails at saving data.
    mMockBrokenSharedPreferencesHelper = createMockBrokenSharedPreference()
}
```

* Khi đã chuẩn bị các đối tượng cần thiết một cách đầy đủ, chúng ta sẽ đến việc test bằng các hàm cụ thể như sau:

```
@RunWith(MockitoJUnitRunner::class)
class SharedPreferencesHelperTest {

    ...

    @Before
    fun initMocks() {
        ...
    }

    @Test
    fun sharedPreferencesHelper_SaveAndReadPersonalInfo() {
        val success = mUserInfo?.let { mMockSharedPreferencesHelper?.savePersonalInfo(it) }

        if (success != null) {
            assertThat("Checking that SharedPreferenceEntry.save... returns true", success, `is`(true))
        } else {
            assertThat("Checking that SharedPreferenceEntry.save... returns true", success, equalTo(null))
        }


        if (mMockSharedPreferencesHelper != null) {
            // Read personal information from SharedPreferences
            val userInfo = mMockSharedPreferencesHelper?.getPersonalInfo()

            // Make sure both written and retrieved personal information are equal.
            assertThat("Checking that UserInfo.name has been persisted and read correctly", mUserInfo?.name, `is`(equalTo(userInfo?.name)))
            assertThat("Checking that UserInfo.dateOfBirth has been persisted and read correctly", mUserInfo?.dateOfBirth, `is`(equalTo(userInfo?.dateOfBirth)))
            assertThat("Checking that UserInfo.email has been persisted and read correctly", mUserInfo?.email, `is`(equalTo(userInfo?.email)))
        }

    }

    @Test
    fun sharedPreferencesHelper_SavePersonalInfoFailed_ReturnsFalse() {
        // Read personal information from a broken

        val success = mUserInfo?.let { mMockBrokenSharedPreferencesHelper?.savePersonalInfo(it) }

        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success, `is`(false))
    }


    private fun createMockSharedPreference(): SharedPreferencesHelper? {
        ...
    }

    private fun createMockBrokenSharedPreference(): SharedPreferencesHelper? {
    ...
    }
}
```

* Ở trên đầu tiên sử dụng annotation **@RunWith** để hướng IDE khởi tạo thư viện Mockito, cũng có thể sử dụng thay thế là **MockitoAnnotations.initMocks( testClass )** trong phương thức có annotation **@Before**.

* Sau đó các hàm dùng để test đều có annotation **@Test**, sau đó sử dụng các phương thức của JUnit:

    * **assertThat()**: Cho phép bạn tạo ra các xác nhận tùy chỉnh, không chỉ là giá trị true hoặc false. Nó gồm 3 tham số là lý do miêu tả, giá trị ban đầu cần kiểm tra, giá trị thực tế.
    * **Is()**: Trả về đối tượng Matcher sau cho khớp đối tượng nguồn với đối tượng được cung cấp vào phương thức.
    * **equalTo()**: Kiểm tra sự bằng nhau của các đối tượng.
    * **when()**: Đây là phương thức rất mạnh, gọi một phương thức làm tham số. Nó nhận bên trong phương thức được khai thác hoặc sao chép. Khi phương thức được thực thi, phương thức **then()** sẽ được gọi.
    * **thenReturn()**: Nó được gọi khi đã chạy xong phương thức của **when()**. Nó được sử dụng để trả về kết quả của phương thức nếu nó không có giá trị. 
