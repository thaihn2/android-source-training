# ConfigureBuildSample

# Overview
* Android Studio sử dụng **Gradle Build Toolkit** để tự động và quản lý quá trình xây dựng, đồng thời giúp cho bạn xác định cấu hình xây dựng tùy chỉnh linh hoạt theo từng môi trường khác nhau.
Mỗi một cấu hình có thể xác resource của riêng nó, đồng thời sử dụng được những phần chung giữa chúng.
* Gradle và Android plugin chạy độc lập với Android Studio. Điều này có nghĩa là bạn có thể xây dựng ứng dụng của bạn có thể sử dụng Android Studio hoặc là không. Miễn sao đầu ra của chúng giống nhau.
* Chúng sử dụng ngôn ngữ **Domain Specific Language(DSL)** để mô tả và thao tác build logic xây dựng bằng Groovy,đây là ngôn ngữ động cho máy ảo Java(JVM)
## Gradle Structure
### Gradle
1. settings.gradle: Đây là file nằm trong thư mục gốc của project, nó khai báo những module nào được sử dụng trong khi xây dựng ứng dụng của bạn. Có thể có 1 hoặc nhiều module được include ở đây:
```
include ':app'
```
2. Properties file: Bao gồm 2 file properties nằm trong thư mục gốc, nơi mà bạn có thể thay đổi cài đặt của gradle toolkit
* [gradle.properties](https://docs.gradle.org/current/userguide/build_environment.html): Nơi bạn có thể cấu hình Gradle cho toàn project, chẳng hạn như kích thước heap tối đa cho Gradle daemon thread. Ví dụ như **org.gradle.jvmargs=-Xmx1536m**
* **local.properties**: Cấu hình các môi trường cho hệ thống, chẳng hạn như path của SDK hoặc NDK. Thông thường môi trường này do Android Studio tự động sửa đổi để phù hợp với từng môi trường nên bạn không nên cố gắng sửa đổi nội dung bên trong nó.
3. gradle-wrapper.properties**: Nơi này quản lý version của Gradle được sử dụng trong project của bạn. Các thay đổi phiên bản ở đây có thể dẫn đến project có thể không chạy tố như trước đối với từng version gradle.
4. top-level build file**: File này nằm trong thư mục gốc của project, định nghĩa các cấu hình mà được sử dụng cho toàn bộ các module của project.
* **buildscript**: Khối này dùng để cấu hình repositories và dependencies dành cho Gralde, không nên include các dependencies của các module trong đây. Chỉ nên thêm các dependencies của các plugin hỗ trợ trong này.
```
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.3.0'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}    
```
* **allprojects**: Là khối cấu hình các repositories và dependencies được sử dụng bởi các module trong toàn project, chẳng hạn như là plugin bên thứ 3 hoặc là các thư viện nào đó. Tuy nhiên bạn nên cấu hình module dependencies bên trong từng module, ở đây chỉ là những repository chung nhất như **google()** hay **jcenter()**. 
* **task**: Tại đây cũng có thể viết thêm các Task của gradle để làm nhiệm vụ nào đó. Ví dụ task clean dưới đây sẽ xóa cây thư mục khi được chạy. Nó được chay khi click sync project hoặc clear project. 
```
task clean(type: Delete) {
    delete rootProject.buildDir
}
```
5. model-level build file: File này nằm trong từ module của project, định nghĩa cấu hình của từng module khác nhau.
* **plugin**: Phần đầu tiên là nơi apply các Android plugin cho gradle. 
* **android**: Phần này là nơi bạn cấu hình tất cả các build option của mình. 
```
android {
    compileSdkVersion 28 // Là mức API nên sử dụng hoặc thấp hơn để compile ứng dụng
    defaultConfig {
        applicationId "android.thaihn.configurebuildsample" // id của ứng dụng khi được publish lên Store
        minSdkVersion 21 // SDK thấp nhất mà ứng dụng hỗ trợ
        targetSdkVersion 28 // Version SDK được chỉ định dùng để test ứng dụng
        versionCode 1 // Định nghĩa số hiệu version của ứng dụng, mỗi lần update ứng dụng đều phải tăng lên
        versionName "1.0" // Định nghĩa tên version của ứng dụng, ví dụ như 1.1.0
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    // Là nơi cấu hình nhiều build type khác nhau. Mặc định, hệ thống sẽ định nghĩa 2 build type là debug và release. 
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
        debug {
            applicationIdSuffix ".debug"
            debuggable true
        }
        staging {
            initWith debug
            manifestPlaceholders = [hostName:"android.thaihn.configurebuildsample"]
            applicationIdSuffix ".debugStaging"
        }
    }
    // Tạo ra nhiều version khác nhau của ứng dụng. Ứng dụng có thể là bản demo hoặc là full
    flavorDimensions "version"
    productFlavors {
        demo {
            dimension "version"
            applicationIdSuffix ".demo"
            versionNameSuffix "-demo"
        }
        full {
            dimension "version"
            applicationIdSuffix ".full"
            versionNameSuffix "-full"
        }
    }
}   
```
* **dependencies**: Là nơi khai báo các dependencies cần thiết cho module.
```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    ...
}
```
### Source sets 
* Android Studio gộp source code và resource cho từng module vào trong source sets. Một module main/source bao gồm mã và tài nguyên được sử dụng bởi tất cả các build variant. Android Studio sẽ tự tạo các source sets khi bạn cấu hình một build variant mới. Tuy nhiên việc tạo source sets, ví dụ như main/ giúp tổ chức các tệp và tài nguyên mà Gradle chỉ nên sử dụng khi xây dựng các phiên bản nhất định của ứng dụng.
* Số lương của source sets sẽ gia tăng nếu như bạn cấu hình nhiều bản build và các productFlavor có chứa trong đó. Ví dụ như:
    * **src/main**: source set bao gồm code và resource cho tất cả các build variant
    * **src/buildType**: source set sinh ra cho các build type khác nhau
    * **src/productFlavor**: source set sinh ra cho các productFlavor
    * **src/productFlavorBuildType**: source set sinh ra dành riêng cho các build variant
* Có thể tạo từng source set cho từng phiên bản productFlavor như sau:
```
sourceSets {
    demo {
        java.srcDirs = ['src/demo/java', 'src/main/java']
        resources.srcDirs = ['src/demo/res']
    }

    full {
        java.srcDirs = ['src/full/java', 'src/main/java']
        resources.srcDirs = ['src/full/res']
    }
}
```
### Task
Những task có sẵn trong Gradle android được chia thành nhiều nhóm, có 4 nhóm chính cơ bản sau:
* **android**: Các task ở đây liên quan đến **dependencies**, **signingReport**(Những thông tin lúc đăng lên store), **sourceSets**(các source set được sinh ra).
* **build**:  Các task liên quan đến build các variant khác nhau với các productFlavor khác nhau.
* **install**: Các task liên quan đến cài đặt ứng dụng
* **verification**: Các task liên quan đến check device, connect, kiến trúc (lint task).

### Build phases
Gradle Build trải qua 3 giai đoạn sau:

* `Initialization`: Gradle hỗ trợ xây dựng một hoặc nhiều project. Trong giai đoạn khởi tạo này, Gradle sẽ xác định dự án nào sẽ tham gia vào quá trình xây dựng và tạo thể hiện riêng cho mỗi project.
* `Configuration`: Trong giai đoạn này, các đối tượng trong project được cấu hình. Các build script của toàn project là một phần của bản build được thực thi.
* `Execution`: Gradle xác định tập hợp con của các tác vụ, được tạo và cấu hình trong giai đoạn `Configuration` sẽ được thực thi. Các task được xác định bởi các đối số sẽ được truyền cho Gradle ở tại thư mục hiện tại. Sau đó Gradle thực thi từng task một.



## Configure build variants
* Việc cài đặt **Build variant** sẽ giúp cho bạn có thể tạo được nhiều phiên bản của ứng dụng bên trong một project cũng như làm thế nào để quản lý dependencies và signing configurations.
* Mỗi một **build variant** là một phiên bản của ứng dụng mà bạn có thể xây dựng. Ví dụ bạn cần xây dựng ứng dụng với các môi trường dev, staging, production, ... trên cùng một project.

1. Configure build types
* Bạn có thể tạo và cấu hình build types trong tệp **build.gradle** của module-app, nằm ở trong khối **android**. Khi bạn tạo mới một module thì Android Studio tự động tạo sẵn cho bạn 2 build type là debug và release. Mặc dù debug build type không xuất hiện trong file build configuration, Android Studio cấu hình nó với **debuggable true**. Điều này cho phép bạn debug ứng dụng trên các thiết bị android bảo mật và cấu hình APK bằng khóa gỡ lỗi chung.
* Do vậy bạn có thể thêm debug build type vào trong file configuration nếu bạn muốn thay đổi cài đặt mặc định. Mẫu sau chỉ định một **applicationIdSuffix** cho debug build type, và "staging" build type thì dựa trên các cấu hình của debug build type.

```
buildTypes {
    release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }

    debug {
        applicationIdSuffix ".debug"
        debuggable true
    }

    staging {
        initWith debug
        manifestPlaceholders = [hostName:"android.thaihn.configurebuildsample"]
        applicationIdSuffix ".debugStaging"
    }

}
```
2. Configure product flavors
* Tạo **product flavors** cũng tương tự như tạo **build types**, sau đó thêm chúng vào khối **productFlavors** trong file cấu hình bao gồm các cài đặt mà bạn muốn.
* Product Flavors hỗ trợ các thuộc tính giống như **defaultConfig** bởi vì nó nằm ở trong lớp ProductFlavor. Điều này có nghĩa là bạn có thể cung cấp cấu hình cơ sở cho tất cả các product flavor cho khối **defaultConfig** và mỗi **product flavor** có thể thay đổi bât kì giá trị mặc định nào, chẳng hạn như applicationId.
* Với **flavorDimensions** cũng thực hiện việc nhóm các productFlavor lại thành các nhóm khác nhau có cùng thuộc tính.
``` 
flavorDimensions "version"
    productFlavors {
        demo {
            dimension "version"
            applicationIdSuffix ".demo"
            versionNameSuffix "-demo"
        }
        full {
            dimension "version"
            applicationIdSuffix ".full"
            versionNameSuffix "-full"
        }
    }
```
* Việc tạo ra rất nhiều version productFlavor sẽ dẫn đến sinh ra những build variant không cần thiết. Vì vậy sẽ có **variantFilter** giúp chúng ta lọc bớt những bản build không cần thiết.
```
variantFilter { variant ->
        def names = variant.flavors*.name
        // To check for a certain build type, use variant.buildType.name == "<buildType>"
        if ( names.contains("demo") && names.contains("staging")) {
            // Gradle ignores any variants that satisfy the conditions above.
            setIgnore(true)
        }
    }
```
3. Dependencies
* Bạn có thể cấu hình các phụ thuộc cho một build variant hoặc là test bằng cách đặt tên của biến thể build hoặc source set trước từ khóa **Implementation** như trong ví dụ sau:
```
dependencies {
    // Adds the local "mylibrary" module as a dependency to the "free" flavor.
    freeImplementation project(":mylibrary")

    // Adds a remote binary dependency only for local tests.
    testImplementation 'junit:junit:4.12'

    // Adds a remote binary dependency only for the instrumented test APK.
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}
```
* Một số các cấu hình của dependency:
    * **implementation**(thay thế cho compile): Sử dụng ở các phiên bản Android Studio Plugin từ 3.0.0. Chỉ chạy trong thời gian runtime. Hầu hết các module của ứng dụng và test đều sử dụng cấu hình này vì nó làm giảm thời gian cần thiết để biên dịch lại khi có thay đổi.
    * **api**(thay thế cho compile): Vì nó cho phép các phụ thuộc chạy trong cả runtime và compile time nên sẽ làm chậm thời gian xây dựng ứng dụng. Vì vậy nên cân nhắc trước khi sử dụng vì nó giống như là compile ở phiên bản cũ.
    * **compileOnly**(thay thế cho provided): Nó chỉ thêm phụ thuộc vào đường dẫn biên dịch chứ không thêm vào đầu ra bản dựng. Điều này sẽ giúp bản APK sinh ra bớt đi những thư viện không cần thiết lúc phát triển.
    * **runtimeOnly**(thay thế cho apk): Hiện không còn sử dụng nữa, vì nó chỉ thêm phụ thuộc cho đầu ra của bản dựng. Có thể dùng với dependency ads của firebase.
    * **annotationProcessor**(thay thế cho compile): Sử dụng để thêm phụ thuộc vào bộ xử lý chú thích, đường dẫn này được tách ra riêng so với các phụ thuộc khác.

* Exclude transitive: Dùng để tránh việc phụ thuộc bắc cầu(transitive) được sinh ra trong ứng dụng. Điều này có nghĩa là 2 phụ thuộc cùng có 1 sự phụ thuộc vào các phiên bản khác nhau của cùng một thư viện.
Ví dụ dưới đây cho thấy 2 phụ thuộc đều có chung phụ thuộc đến thư viện **org.hamcrest:hamcrest-core** đối với các phiên bản khác nhau:
```
dependencies {
    androidTestCompile 'junit:junit:4.12' //(Depends on version 1.3)
    androidTestCompile 'org.mockito:mockito-core:1.10.19' //(Depends on version 1.1)
}
```
Để xử lý việc này chúng ta cần phải sử dụng từ khóa **Exclude** như sau để giải quyết:
```
dependencies {
    implementation ('junit:junit:4.12'){
        exclude group: 'org.hamcrest', module:'hamcrest-core'
    }

    androidTestImplementation ('org.mockito:mockito-core:1.10.19'){
        exclude group: 'org.hamcrest', module:'hamcrest-core'
    }
}
```
## Gradle Task
> Mỗi Gradle Build được xây dựng từ nhiều project khác nhau, còn mỗi project lại được tạo thành từ một hoặc nhiều task.

* Lifecycle task là những task không tự chạy, chúng thường không có bất kỳ action nào. Một số khái niệm được Lifecycle task đại diện sau:
    
    * `work-folow step`:  Đại diện cho một công việc nào đó, ví dụ như chạy tất cả các lệnh check với task `check` hoặc `lint`.
    * `a buildable thing`: Dùng để build 1 bản dựng nào đó, ví dụ tạo một bản product hay là bản debug cho project.
    * `công việc cần phải thực thi nhiều tác vụ login giống nhau`: Ví dụ như chạy tất cả các lệnh biên dịch với compileAll.
    
* Các task phân chia ra nhiều loại, dựa vào nó có được gắn nhãn task hay không. Kết quả của task cũng dựa vào nhãn của task để hiển thị trong bảng điều khiển hay là giao diện người dùng.

    * `(no label) or EXECUTED`: Task thực hiện các hành động của nó, Gradle xác định được chúng và được thực thi như là một phần của bản build. Cũng có thể là các task không có hành động, phụ thuộc. 
    * `UP-TO-DATE`: Các task mà input và output không có sự thay đổi gì.
    * `FROM-CACHE`: Các output của task có thể được tìm thấy ở một thực thi trước đó. 
    * `SKIPPED`: Các task không thực hiện hành động mà đã được loại bỏ khỏi command-line.
    * `NO-SOURCE`: Task không cần thực hiện các hành động của nó. Có input và output nhưng lại không có nguồn. Ví dụ như các tệp .java cho `JavaCompile`.
    
* Định nghĩa một task đơn giản như sau:
```
task helloTask{
    println "Hello task"
}    
```

* Vì task không tự chạy mà tùy theo hành động nào đó từ command-line hoặc giao diện Android Studio nên việc custom task chạy khi nào, trước hay sau khi build, chạy tự động khi build thì ra sao. Chúng ta sử dụng `depenOn` để cấu hình custom task cùng với bản build như sau: 
```
task myTask << {
    println 'do it before build'
}

build.dependsOn myTask
```

## Code Coverage with Jacoco
> `Test coverage report` là một công cụ quan trọng để đo lường các test đối với code của mình. Mặc dù không được đảm bảo phần mềm sẽ không có bug nhưng sẽ có tỷ lệ bao phủ cao để tránh được nhiều vấn đề đau đầu trong dự án của bạn.

* Để tạo `coverage report`, chúng ta sử dụng Jacoco(Java Code Coverage), một trong những công cụ được sử dụng nhiều nhất trong Java cho mục đích này. Nhưng môi trường Android có một kịch bản cụ thể, có 2 bản test là `test`(unit) và `androidTest`(instrumented).
* 

### Setting Jacoco

* Thêm dependecy của jacoco vào trong file build.gradle project-level:
```
dependencies {
    classpath 'com.android.tools.build:gradle:3.0.1'
    classpath 'org.jacoco:org.jacoco.core:0.8.2'
}
```
* Thêm plugin, task, config trong file build.gradle app-level:

```
apply plugin: 'jacoco'

jacoco {
    toolVersion = '0.8.2'
}

tasks.withType(Test) {
    jacoco.includeNoLocationClasses = true
}
```

* Tạo 1 task để run khi chạy tool Jacoco
```
task jacocoTestReport(type: JacocoReport, dependsOn: ['testDebugUnitTest', 'createDebugCoverageReport']) {

    reports {
        xml.enabled = true
        html.enabled = true
    }

    def fileFilter = ['**/R.class', '**/R$*.class', '**/BuildConfig.*', '**/Manifest*.*', '**/*Test*.*', 'android/**/*.*']
    def debugTree = fileTree(dir: "$project.buildDir/intermediates/kotlin-classes/debug", excludes: fileFilter)
    def mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories = files([mainSrc])
    classDirectories = files([debugTree])
    executionData = fileTree(dir: project.buildDir, includes: [
            'jacoco/testDebugUnitTest.exec', 'outputs/code-coverage/connected/*coverage.ec'
    ])
}
```
 
* Thêm testCoverageEnabled true vào build type debug
```
buildTypes {
    debug {
        applicationIdSuffix ".debug"
        debuggable true
        testCoverageEnabled true
    }
}
```

* Thêm test option vào bên trong thẻ `android`: 
```
testOptions {
    execution 'ANDROID_TEST_ORCHESTRATOR'
    animationsDisabled true

    unitTests {
        includeAndroidResources = true
    }
}
```

* Thêm vài dependency dành cho việc test vào module app: 

```
dependencies {
    ... 
    
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    androidTestUtil 'com.android.support.test:orchestrator:1.0.2'
    testImplementation 'junit:junit:4.12'
    testImplementation 'org.robolectric:robolectric:4.0.2'
}
```

### Run task with Jacoco tool
* Bạn có thể run task bằng dòng lệnh hoặc là bằng task trong phần `gradle` của Android Studio. Ở đây mình sử dụng tool trong phần `gradle/app/Run Gradle Task` sau đó tìm đến tên của task cần run. 
* Khi run các task tương ứng sẽ sinh ra file dạng `html` để lưu trữ dữ liệu test. Thông thường ở bên trong thư mục `app/build/reports/androidTests/connected/index.html`. Bạn có thể mở file này với trình duyệt để xem kết quả.
