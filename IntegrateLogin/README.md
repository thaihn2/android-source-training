# Integrate Login
Integrate Login with Facebook, Twitter, Google

# OAuth2

* OAuth2 là một framework ủy quyền cho các ứng dụng có quyền truy cập hạn chế vào tài khoản trên dịch vụ HTTP, chẳng hạn như Facebook, Github, DigitalOcean. Nó hoạt dodonjog bằng cách ủy quyền xác thực người dùng cho bên thứ 3. Nó cung cấp được cho các ứng dụng web, desktop, mobile.

* OAuth định nghĩa 4 role: 

    * **Resouce Owner**: Là người dùng cho phép ứng dụng truy cập vào tài khoản của họ. Quyền truy cập này của ứng dụng vào tài khoản người dùng bị giới hạn chỉ trong phạm vi ủy quyền.
    * **Client**: Là ứng dụng muốn truy cập vào tài khoản của người dùng. Trước khi làm như vậy phải được người dùng ủy quyền và API xác thực trước.
    * **Resource Server**: Là nơi lưu trữ các tài khoản người dùng được bảo vệ.
    * **Authorization Server**: Phục vụ việc xác minh danh tính người dùng sau đó cấp mã thông báo truy cập cho các ứng dụng.

* Luồng giao thức dưới dạng trừu tượng dưới đây sẽ mô tả cách OAuth hoạt động:

![](https://assets.digitalocean.com/articles/oauth/abstract_flow.png)

* Trước khi sử dụng OAuth cho ứng dụng, bạn phải đăng ký ứng dụng của mình với nhà cung cấp dịch vụ như Facebook, Google, Twitter, ... bằng cách sử dụng SDK hoặc là API trên trang web của dịch vụ. 

* Khi đăng nhập thành công, dịch vụ sẽ cung cấp cho bạn **client credentials** dưới dạng định danh của một **client identifier** và một **client secret**. Trong đó **client indentifier** được hiển thị công khai, được sử dụng bởi API để xác định ứng dụng và xây dựng các URL ủy quyền. Còn **client secret** phải được giữ bí mật giữa app và API.

* Trong giao thức ở trên, 4 bước đầu tiên đã dành cho việc cấp và nhận ủy quyền. OAuth2 định nghĩa 4 loại cấp ủy quyền trong các trường hợp khác nhau:

    * **Authorization Code**: Sử dụng với các ứng dụng phía server
    * **Implicit**: Được sử dụng với mobile hoặc web app.
    * **Resource Owner Password Credentials**: Được sử dụng cho các ứng dụng đáng tin cậy, chẳng hạn như các ứng dụng do chính dịch vụ sở hữu.
    * **Client Credentials**: Sử dụng để truy cập các API của ứng dụng.
    
*  Cấp quyền theo dạng **Authorization Code** được sử dụng nhiều nhất vì nó tối ưu cho các ứng dụng phía server, nơi source code không được công khai và bảo mật có thể được duy trì. Đây là luồng dựa trên chuyển hướng, nên ứng dụng phải có khả năng tương tác với người dùng. 

    * Step 1: Người dùng được cung cấp một liên kết **authorization code** như sau:
    
    ```
    https://cloud.digitalocean.com/v1/oauth/authorize?response_type=code&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read
    ```
    * Khi người dùng click vào link, trước tiên phải đăng nhập vào dịch vụ để xác minh được danh tính của họ. Sau đó họ sẽ được nhắc nhở xem có cho phép hay từ chối quyền của ứng dụng.
    * Nếu người dùng cho phép, dịch vụ sẽ chuyển hướng ứng dụng đến một callback nào đó và trả về ứng dụng.
    * Ứng dụng tiếp tục request token từ API, bằng các chuyển **authorization code** cùng với các xác thực liên quan đến API.
    * Tiếp theo ứng dụng nhận về **Access Token** từ server nếu việc xác thực thành công.

* Cấp quyền theo dạng **Implicit** được sử dụng cho mobile và web vì nơi bảo mật client không được đảm bảo.:

    * Loại cấp quyền ngầm này cùng là một kiểu chuyển hướng nhưng **access token** được cung cấp cho người dùng để chuyển tiếp vì vậy sẽ được hiển thị cho người dùng thấy.
    * Luồng này không xác thực danh tính của ứng dụng và dựa vào URI chuyển hướng để sử dụng.
    * Loai này không hỗ trợ **refresh token**.
    * Luồng này sẽ hoạt động như sau: Người dùng được yêu cầu ủy quyền cho ứng dụng, sau đó máy chủ ủy quyền chuyển access token truy cập trở lại người dùng.

* Cấp quyền dạng **Resource Owner Password Credentials** là người dùng sẽ cung cấp thông tin đăng nhập của họ trực tiếp cho ứng dụng để nhận về mã accessToken.

    * Loại này chỉ nên được bật khi các luồng khác không khả thi, Ngoài ra nó nên được sử dụng trong trường hợp tin cậy của người dùng.
    * Luồng này sẽ xảy ra sau khi người dùng cung cấp thông tin đăng nhập của họ, ứng dụng sẽ yêu cầu access token từ máy chủ.

* Cấp quyền dạng **Client Credentials** cung cấp cho ứng dụng một cách để truy cập vào tài khoản dịch vụ của chính nó.

    * Ví dụ như khi đăng nhập xong muốn truy cập vào một số các chức năng khác hoặc lấy ra các thông tin trong cùng một dịch vụ.
    * Luồng này sẽ hoạt động như sau: Người dùng yêu cầu access token bằng cách gửi đi **credential**, **client id**, **client secret** của client đến máy chủ ủy quyền. Nếu xác thực thành công thì dịch vụ sẽ trả lại mã thông báo và người dùng có quyền sử dụng tài khoản của dịch vụ. 

* Luồng của **refresh token**: Sau khi access token hết hạn, sử dụng nó để gửi request sẽ trả về lỗi **Invalid Token Error**. Tại thời điểm này, nếu có refresh token khi access token ban đầu được phát hành, có thể sử dụng để yêu cầu một access token khác.

# Login app with Facebook

* Hiện nay rất nhiều các ứng dụng đã tích hợp việc đăng ký tài khoản người dùng thông qua tài khoản Facebook. Bởi bây giờ đa số ai cũng sẽ có cho mình một tài khoản Facebook.
* Việc đăng nhập này dựa trên SDK của Facebook cung cấp cho chúng ta và tích hợp vào ứng dụng của mình.
* Đăng nhập sử dụng Facebook được hướng dẫn chi tiết tại trang [facebook developer](https://developers.facebook.com/docs/facebook-login/android?locale=en)

## Setting

1. Đầu tiên là bạn phải có một ứng dụng có trên trang **developer facebook**, nếu chưa có thì hãy tạo mới một ứng dụng để tiến hành liên kết. Xem các ứng dụng đã có hoặc là tạo mới ứng dụng tại [đây](https://developers.facebook.com/apps/)
2. Trọn bộ SDK của Facebook cung cấp cho developer ở [Facebook SDK for Android](https://developers.facebook.com/docs/android/componentsdks). Ở đây chúng ta sử dụng **Facebook Login SDK**, để sử dụng cần thêm dependency vào thư mục build.gradle.

* Thêm repository `jcenter()` vào file build.gradle project-level.
* Thêm dependency của facebook login `implementation 'com.facebook.android:facebook-login:[4,5)'` vào file build.gradle app-level.

3. Chỉnh sửa Resource và Manifest

* Thêm **app_id** và **fb_login_protocol_schema** vào file string để sử dụng cho **FacebookActivity**.


```
<string name="facebook_app_id">1274481512658205</string>
<string name="fb_login_protocol_scheme">fb1274481512658205</string>
```

* Thêm quyền kết nối **INTERNET** và thiết đặt của **FacebookActivity** vào trong thư mục **AndroidManifest.xml**.

```
<meta-data android:name="com.facebook.sdk.ApplicationId" 
        android:value="@string/facebook_app_id"/>

<activity android:name="com.facebook.FacebookActivity"
    android:configChanges=
                "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
    android:label="@string/app_name" />

<activity
    android:name="com.facebook.CustomTabActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="@string/fb_login_protocol_scheme" />
    </intent-filter>
</activity>
```

* Thêm package name và default class của ứng dụng của bạn vào phần 5 tại [đây](https://developers.facebook.com/docs/facebook-login/android?locale=en) để Facebook SDK nhận ra ứng dụng của bạn. Default class sẽ phải chỉ đến chính xác package name mà Activity đó thực hiện login.

* Sau đó thêm **Release Key Hashes** của ứng dụng lên trên ứng dụng mới tạo trên Facebook develop. Để lấy được keystore dev, sử dụng lệnh hoặc hàm để lấy ra:

    * Đối với Linux, sử dụng lệnh dưới đây để lấy ra key dev, những nền tảng khác xem tại [đây](https://medium.com/mindorks/generate-hash-key-for-facebook-and-sha-1-key-for-google-maps-in-android-studio-48d92e4f3c05)
    
    ```
    keytool -exportcert -alias androiddebugkey -keystore debug.keystore | openssl sha1 -binary | openssl base64
    ```
    
    * Có thể sử dụng function để lấy ra được key store debug khi chạy ứng dụng:
    
    ```
    fun getKeyStoreDebug(context: Context) {
        try {
            val info =
                context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")

                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e(TAG, "key hash: $something")
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e(TAG, "name not found: $e1")
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "no such an algorithm: $e")
        } catch (e: Exception) {
            Log.e(TAG, "exception: $e")
        }

    }
    ```

* Bật chức năng **Single Sign On** cho ứng dụng của bạn trong phần FacebookLogin/QuickStart của Facebook developer.

## Add Login with Facebook

* Bạn có thể sử dụng App event để log lại các event của ứng dụng thông qua Facebook Android SDK. Event này có thể là 1 trong 14 event được xác định trước hoặc có thể tạo thêm những event mới trong ứng dụng của mình để theo dõi.
* Để đăng lý **Logging App Activations** cho ứng dụng của bạn, nó có thể giúp cho bạn thống kê được tần xuất người click, người dùng, ... thông qua Facebook Analytics. Thêm việc đăng ký logging vào  bên trong **onCreate()** của class Application.

```
override fun onCreate() {
        super.onCreate()
        
        // Enable AppEventLogger
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
    
// For logging
logger.logPurchase(BigDecimal.valueOf(4.32), Currency.getInstance("USD"))
```

* Thêm button **Login with Facebook** bên trong ứng dụng từ Facebook SDK. Nó là một thành phần nằm trong class **LoginManager**.

```
<com.facebook.login.widget.LoginButton
            android:id="@+id/login_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:layout_marginTop="30dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
```

* Tạo đối tượng của **CallbackManager** để thực hiện việc nhận callback về đăng nhập.

```
mCallbackManager = CallbackManager.Factory.create()
```

* Thêm các quyền cần thiết khi bạn tiến hành login với tài khoản facebook vào button login có sẵn, ví dụ như **email**, **public-profile**. Xem thêm tại [đây](https://developers.facebook.com/docs/facebook-login/android/permissions).

```
facebookLoginBinding.loginButton.setReadPermissions(createPermission())

private fun createPermission(): List<String> {
    return arrayListOf(PERMISSION_EMAIL, PERMISSION_PUBLIC_PROFILE)
}
```

* Đăng kí callback để nhận về sự kiện đăng nhập thành công hoặc thất bại, sử dụng phương thức **registerCallback()**.

```
facebookLoginBinding.loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
    override fun onCancel() {
        Log.d(TAG, "onCancel()")
    }

    override fun onError(error: FacebookException?) {
        error?.printStackTrace()
        Log.d(TAG, "Error code: ${error.hashCode()}")
    }

    override fun onSuccess(result: LoginResult?) {
        Log.d(TAG, "onSuccess: ${result.toString()}")
    }
})
```

* Nếu sử dụng custom button thì sẽ sử dụng **LoginManager** để thực hiện nhận callback như sau.

```
LoginManager.getInstance().registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
    override fun onCancel() {
        Log.d(TAG, "onCancel")
    }

    override fun onSuccess(result: LoginResult?) {
        Log.d(TAG, "Login result: $result")
    }

    override fun onError(error: FacebookException?) {
        error?.printStackTrace()
        Log.d(TAG, "Error code: ${error.hashCode()}")
    }
})

// Call when press button login
LoginManager.getInstance().logInWithReadPermissions(this, createPermission())

// Call when logout
LoginManager.getInstance().logOut()
```

* Để nhận được kết quả đăng nhập, hãy lắng nghe ở phương thức **onActivityResult()** như sau:

```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    mCallbackManager.onActivityResult(requestCode, resultCode, data)
    super.onActivityResult(requestCode, resultCode, data)
}
```

* Để check trạng thái của người dùng có đăng nhập hay không, sử dụng **AccessToken.getCurrentAccessToken()** để kiểm tra.

```
private fun checkLoginStatus(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }
```

* Cuối cùng lấy thông tin của người dùng qua đối tượng profile.

```
val user = Profile.getCurrentProfile()
```

## [Tracking Access Tokens and Profiles](https://developers.facebook.com/docs/facebook-login/android/accesstokens)

* Nếu muốn ứng dụng của bạn giữ cập nhật với accessToken và profile, bạn có thể triển khai các class **AccessTokenTracker** và **ProfileTracker**.
* Các phương thức này sẽ lắng nghe và gọi đến code của bạn khi có sự thay đổi, vì vậy cần phải gọi **stopTracking()** trong hàm **onDestroy()**.

* Để theo dõi accessToken, thực hiện như sau:

```
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FacebookSdk.sdkInitialize(this.getApplicationContext());
    callbackManager = CallbackManager.Factory.create();

    accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(
            AccessToken oldAccessToken,
            AccessToken currentAccessToken) {
                // Set the access token using 
                // currentAccessToken when it's loaded or set.
        }
    };
    // If the access token is available already assign it.
    accessToken = AccessToken.getCurrentAccessToken();
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
}

@Override
public void onDestroy() {
    super.onDestroy();
    accessTokenTracker.stopTracking();
}
```

* Để thực hiện theo dõi profile nếu có thay đổi như sau:

```
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FacebookSdk.sdkInitialize(this.getApplicationContext());
    callbackManager = CallbackManager.Factory.create();

    profileTracker = new ProfileTracker() {
        @Override
        protected void onCurrentProfileChanged(
                Profile oldProfile,
                Profile currentProfile) {
            // App code
        }
    };
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
}

@Override
public void onDestroy() {
    super.onDestroy();
    profileTracker.stopTracking();
}
```

## Connect to FireBase Authentication

* Nếu muốn sử dụng token của Facebook để đăng nhập FireBase Authentication, hãy sử dụng **FacebookAuthProvider** để lấy ra đối tượng **AuthCredential**. Sau đó sử dụng nó để đăng ký user trên FireBase Auth.

```
private void handleFacebookAccessToken(AccessToken token) {
    Log.d(TAG, "handleFacebookAccessToken:" + token);

    AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
    mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // ...
                }
            });
}
```

# Login app with Twitter

## Setup and Login Basic

* Trước hết đăng nhập tài khoản Twitter ở [đây](https://developer.twitter.com/en/apps) và tạo thêm 1 app cho tài khoản.
* Điền đầy đủ các trường trong **Application Details**, riêng trường **Callback URLs** thì nên điền **twittersdk://**. Điều này là bắt buộc nếu muốn xác thực qua Twitter.
* Sau khi tạo được app trên Twitter, quay trở về project và thêm vào dependency cần thiết.

```
implementation 'com.twitter.sdk.android:twitter:3.1.0'
implementation 'com.twitter.sdk.android:twitter-core:3.1.1'
implementation 'com.twitter.sdk.android:twitter-mopub:3.1.0'
```

* Cài đặt config của Twitter để sử dụng trong ứng dụng, cài đặt trước khi sử dụng(thường là trong Application).

```
val config = TwitterConfig.Builder(this)
    .logger(DefaultLogger(Log.DEBUG))
    .twitterAuthConfig(TwitterAuthConfig(
        resources.getString(R.string.consumer_api_key),
        resources.getString(R.string.consumner_api_key_secret)))
    .debug(true)
    .build()

Twitter.initialize(config)
```

* Thêm button login của Twitter vào giao diện như sau:

```
<com.twitter.sdk.android.core.identity.TwitterLoginButton
    android:id="@+id/login_button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent" />
```

* Tạo callback để thực hiện login, sử dụng **Callback** và nhận về giá trị **TwitterSession** của phiên login này.

```
login_button.callback = object : Callback<TwitterSession>() {

    override fun failure(exception: TwitterException?) {
        exception?.printStackTrace()
        Toast.makeText(applicationContext, exception?.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun success(result: Result<TwitterSession>?) {
        Log.d(TAG, "success:result:$result")
        result?.let {
            handleResult(it)
        }
    }
}
```

* Nhận dữ liệu login trả về bên trong hàm **onActivityResult()** sử dụng login_button có sẵn.

```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    login_button.onActivityResult(requestCode, resultCode, data)
}
```

* Nếu nhận dữ liệu ở Fragment để xử lý, có thể sử dụng cách sau bên trong hàm **onActivityResult**

```
    // Pass the activity result to the fragment, which will then pass the result to the login
    // button.
    Fragment fragment = getFragmentManager().findFragmentById(R.id.your_fragment_id);
    if (fragment != null) {
        fragment.onActivityResult(requestCode, resultCode, data);
    }
```

* Lấy ra dữ liệu khi đã login thành công, sử dụng đối tượng của TwitterSession.

```
result.data?.let {
    TwitterCore.getInstance().getApiClient(it).accountService
        .verifyCredentials(true, true, false)
        .enqueue(object : Callback<User>() {
            override fun success(result: Result<User>) {
                val name = result.data.name
                val userName = result.data.screenName
                val profileImageUrl = result.data.profileImageUrl.replace("_normal", "")

                tv_notification.text = "name:$name \n userName:$userName \n profileImageUrl:$profileImageUrl"
            }

            override fun failure(exception: TwitterException) {
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    })
}
```

## Login Custom

* Chúng ta có thể thêm một button bất kì và thực hiện login với twitter sử dụng **TwitterAuthClient**.

```
mTwitterAuthClient.authorize(this, object : Callback<TwitterSession>() {
    override fun failure(exception: TwitterException?) {
    }

    override fun success(result: Result<TwitterSession>?) {
    }
}
```

* Sử dụng TwitterAuthClient để đăng kí nhận phản hồi trong hàm **onActivityResult()**.

```
mTwitterAuthClient.onActivityResult(requestCode, resultCode, data)
```

* Để check xem user đã login vào Twitter chưa, sử dụng **TwitterCore** để check xem có **activeSession** hay không và có token trả về hay không.

```
val session = TwitterCore.getInstance().sessionManager.activeSession
val authorToken = session?.authToken
val token = authorToken?.token
val secret = authorToken?.secret
```

* Để logout user khỏi Twitter, sử dụng **TwitterCore** để clear session của user login hiện tại. 

```
TwitterCore.getInstance().sessionManager.clearActiveSession()
```

## Connect to FireBase Authentication

* Có thể sau khi sign-in với Twitter, nếu có nhu cầu sử dụng FireBase Authentication để đăng ký luôn tài khoản trên FireBase sử dụng cho ứng dụng.

```
private void handleTwitterSession(TwitterSession session) {

    AuthCredential credential = TwitterAuthProvider.getCredential(
            session.getAuthToken().token,
            session.getAuthToken().secret);

    mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(TwitterLoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // ...
                }
            });
}
```

# Login app with Google

* Hiện tại hầu hết các ứng dụng đều cho phép đăng nhập bằng Google hoặc Facebook, vì rất tiện lợi và không cần người dùng phải nhập thêm bất cứ thông tin gì vì đa số giờ đều có tài khoản Google hoặc Facebook.
* Login theo hướng dẫn của Google đầy đủ ở [đây](https://developers.google.com/identity/sign-in/android/start-integrating)

## Start Integrating
* Đầu tiên, thêm Google's Maven **google()** vào trong repositories và dependency vào trong module app:

```
allprojects {
    repositories {
        google()
        // If you're using a version of Gradle lower than 4.1, you must instead use:
        // maven {
        //     url 'https://maven.google.com'
        // }
    }
}
// App module

dependencies {
    compile 'com.google.android.gms:play-services-auth:16.0.1'
}
```

* Tiếp theo là config Google API Console project để thiết lập các thông tin cần thiết khi liên kết với project trên Google API. Việc này cần cung cấp mã **SHA-1** để định danh ứng dụng của bạn phục vụ cho việc đăng lên chợ ứng dụng sau này.
* Nếu ứng dụng của bạn sử dụng trình xác thực với **backend server**, hoặc truy cập Google API từ **backend server** của bạn, hãy lấy **OAuth 2.0 client ID** ở trong [google console](https://console.developers.google.com/apis/credentials?authuser=4) và tìm type là **Web application**.
> Thêm client id này vào phương thức **requestIdToken**, **requestServerAuthCode** khi bạn tạo đối tượng **GoogleSignInOptions**.

## Add Sign-In

* Tạo đối tượng của **GoogleSignInOptions** để chỉ định cấu hình khi sign-in với Google. Ở đây sử dụng scope **DEFAULT_SIGN_IN**, nếu muốn yêu cầu bổ sung phạm vi để truy cập Google API, hãy chỉ định với [requestScopes()](https://developers.google.com/identity/sign-in/android/additional-scopes?authuser=4).   

```
val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build()
```
* Bạn có thể lấy được các thông tin khác nhau khi sử dụng các phương thức **request...** ví dụ như **requestEmail()**, **requestProfile()**.

* Tạo một đối tượng **GoogleSignInClient** ở activity mà bạn dùng để request sign-in với google account.

```
mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
```

* Kiểm tra xem đã tồn tại user đăng nhập chưa, sử dụng **GoogleSignIn** để lấy ra account cuối cùng sign-in bằng phương thức **getLastSignedInAccount()**.

```
val account = GoogleSignIn.getLastSignedInAccount(this)
```
> Nếu như bạn cần phát hiện ra các thay đổi về trạng thái xác thực của người dùng xảy ra bên ngoài ứng dụng của bạn chẳng hạn như thu hồi mã thông báo truy cập, hãy gọi **GoogleSignInClient.silentSignIn** khi ứng dụng khởi động.

* Sau đó, tạo button sign-in google trong file xml. Có thể thay đổi size của button bằng hàm **setSize()** với tham số tùy chọn, ví dụ như **SignInButton.SIZE_STANDARD**.

```
<com.google.android.gms.common.SignInButton
    android:id="@+id/sign_in_button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

* Lấy ra intent để có thể gọi đến giao diện chọn các tài khoản có trong máy bằng **SignInIntent**.

```
private fun signIn(){
    val intent = mGoogleSignInClient.signInIntent
    startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
}
```

* Kết quả sẽ trả về bên trong phương thức **onActivityResult()**, có thể lấy được account tại đây hoặc là nhận được lỗi khi đăng nhập với google account.

```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_CODE_SIGN_IN) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleResult(task)
    }
}

private fun handleResult(completeTask: Task<GoogleSignInAccount>) {
    try {
        val account = completeTask.getResult(ApiException::class.java)
        updateUi(account)
    } catch (ex: ApiException) {
        ex.printStackTrace()
        updateUi(null)
        Log.d(TAG, "Sign-In failed with code: ${ex.statusCode}")
    }
}
```

* Để Sign-Out khỏi tài khoản đang đăng nhập, sử dụng phương thức **signOut()** của GoogleSignInClient.

```
private fun signOut() {
    mGoogleSignInClient.signOut()
        .addOnCompleteListener(this) {
        Log.d(TAG, "SignOut: ${it.result}")
        if (it.isSuccessful) {
            Toast.makeText(applicationContext, "Sign out success", Toast.LENGTH_SHORT).show()
        }
    }
}
```

* Bạn cũng nên cung cấp cho người dùng đã đăng nhập bằng Google account cách ngắt kết nối tài khoản của họ. Nếu người dùng xóa tài khoản khỏi ứng dụng của bạn, bạn phải xóa các thông tin liên quan đến người dùng. Sử dụng phương thức **revokeAccess()**.

```
private fun disconnectAccount() {
    mGoogleSignInClient.revokeAccess()
        .addOnCompleteListener(this) {
        Log.d(TAG, "DisconnectAccount: ${it.result}")
        if (it.isSuccessful) {
            Toast.makeText(applicationContext, "Disconnect account success", Toast.LENGTH_SHORT).show()
        }
    }
}
```

## Sign-In with Id token to FireBase Authentication

* Để khi đăng nhập bằng google sign-in trả về được token của người dùng, sử dụng webIdToken khi tạo project trên **Google API Console** rồi sử dụng phương thức **requestIdToken()**.

```
val webClientId = "779671502565-q27gv7hs2boc6l1vtfecicn7al8su8e5.apps.googleusercontent.com"

 val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestIdToken(webClientId)
                .build()
```

* Tiếp theo, muốn sử dụng FirebaseAuth để lưu trữ người dùng đăng nhập vào cơ sở dữ liệu của Firebase, hãy tạo một project firebase tương ứng với project của Google API.
* Thêm phương thức đăng nhập vào trong FireBase Authentication, ở đây cần enable sign-in qua Google. 
* Sau khi nhận được kết quả khi sign-in bằng Google account, sử dụng **GoogleSignInAccount** để lấy ra **idToken** để thực hiện việc lưu account lên FireBase Authentication.

```
private fun signInWithCredential(account: GoogleSignInAccount) {
    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "signInWithCredential:success")
                val user = mAuth.currentUser
                Log.d(TAG, "Firebase: CurrentUser:$user")
            } else {
                Log.d(TAG, "signInWithCredential:failure", it.exception)
                Toast.makeText(applicationContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
```

> Để thực hiện login qua FireBase, thêm file cấu hình **google-service.json** vào trong thư mục app. Trước đó việc tạo ra project Google API cũng sinh ra file cấu hình **credentials.json** cũng được để ở đây.

## [Authenticate with Backend Server](https://developers.google.com/identity/sign-in/android/backend-auth?authuser=4)

> Thông thường chúng ta sẽ có một server backend riêng để quản lý việc người dùng nào đang đăng nhập trên server. Để làm như vậy sau khi đăng nhập xong, hãy gửi **idToken** của người dùng đến server để quản lý.

* Sử dụng HTTPS với phương thức POST để gửi idToken của người dùng lên server, ví dụ như:
* Để xác minh tính toàn vẹn của **idToken**, có thể sử dụng mã khóa công khai của Google để xác định tính toàn vẹn, đọc thêm tại [đây](https://developers.google.com/identity/sign-in/android/backend-auth?authuser=4)
* Sử dụng thư viện Google API Client để có thể validate **idToken**, sử dụng đối tượng **GoogleIdTokenVerifier** để verifier token nhận từ server ở lần sau.

```
GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
    // Specify the CLIENT_ID of the app that accesses the backend:
    .setAudience(Collections.singletonList(CLIENT_ID))
    // Or, if multiple clients access the backend:
    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
    .build();

// (Receive idTokenString by HTTPS POST)

GoogleIdToken idToken = verifier.verify(idTokenString);
```

* Để xác thực **idToken** cũng có thể sử dụng request đến một api dạng như sau:

```
https://oauth2.googleapis.com/tokeninfo?id_token=XYZ123
```

## [Enabling Server-Side Access](https://developers.google.com/identity/sign-in/android/offline-access?authuser=4)

> Việc thêm Sign-In của Google ở trên chỉ thực hiện bên trên client, nếu server muốn thay mặt client gọi ra một API Google thì sẽ cần server phải request access.

* Khi thực hiện tạo object của **GoogleSignInOptions**, sử dụng phương thức **requestServerAuthCode** khi thực hiện **requestScopes()**.

```
GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        ...
        .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
        .build()
```

* Sau khi login thành công, sẽ nhận về được **serverAuthCode()** và gửi code này lên trên server backend để quản lý.

```
val authCode = account.getServerAuthCode()
```

* Trên server sử dụng authCode của client gửi lên để thực hiện **refreshToken()**, từ đó lấy được token mới và thực hiện thay client các dịch vụ mà Google API cung cấp.

```
// Exchange auth code for access token
GoogleClientSecrets clientSecrets =
    GoogleClientSecrets.load(
        JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));
GoogleTokenResponse tokenResponse =
          new GoogleAuthorizationCodeTokenRequest(
              new NetHttpTransport(),
              JacksonFactory.getDefaultInstance(),
              "https://www.googleapis.com/oauth2/v4/token",
              clientSecrets.getDetails().getClientId(),
              clientSecrets.getDetails().getClientSecret(),
              authCode,
              REDIRECT_URI)  // Specify the same redirect URI that you use with your web
                             // app. If you don't have a web version of your app, you can
                             // specify an empty string.
              .execute();

String accessToken = tokenResponse.getAccessToken();
```
