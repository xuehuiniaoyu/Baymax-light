# Baymax
Android平台的Spring框架。

一个别名对应一个组件，可以根据需求的变化将别名对应的组件临时替换，非常实用于实际项目的开发。
Controller概念的引入，框架已实现网络层封装，你不再需要关心网络数据的请求实现，只需关心业务如何处理。

The Spring framework of Android platform.
can be used.The introduction of the Controller concept makes the whole framework much easier.

## Gradle

```
1.Add it in your root build.gradle at the end of repositories （添加maven仓库到根目录下的build.gradle）:

allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

2.Add the dependency（添加依赖到app下的build.gradle）

dependencies {
	...
	implementation 'com.github.xuehuiniaoyu:Baymax-light:v1.2'
}

```

## Maven

```
1.
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>

2.
<dependency>
    <groupId>com.github.xuehuiniaoyu</groupId>
    <artifactId>Baymax</artifactId>
    <version>[Latest release]</version>
</dependency>

```

![Screenshot](hello.jpg)


## 引入步骤（Using the step）

```
1.Create Application

public class ExampleApplication extends BaymaxApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Baymax.single().setAnnotationsPackage("com.disney4a.baymax_example.app").play();
    }
}

2. Example Activity

@Tag_Activity(name = "test-activity")
public class TestActivity extends BaymaxActivity {
	...
}

3. Open the Activity

Baymax.single().activity("test-activity").start();

```



```
1.Example Controller

@Tag_Controller(uri = "http://www.baidu.com")
public class TestController {

    public static final String TAG = TestController.class.getSimpleName();

    /**
     * 访问百度
     * @param correspondents
     */
    @Tag_Controller.Path(name = "getBaidyContent", value="/hello")
    @Tag_Http
    public void baidu(Correspondents correspondents, final TextView textView) {
    	Log.i(TAG, "请求地址为："+correspondents.getUri());
        correspondents.setExpectation(new HttpExpectation() {
            @Override
            public void onRepay(HttpRepay repay) {
                try {
                    final String result = System.currentTimeMillis()+" - "+repay.getResponse().body().string();
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(result);
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
        correspondents.go();
    }

    @Tag_Controller.Path(name = "list", value = "/test.xml")
    @Tag_Return(type = Tag_Return.REDIRECT)
    public String jump(Correspondents correspondents, String name, int age, Object obj) {
        Log.i(TAG, "name="+name + " age="+age + " obj="+obj);
        RequestScope.single().put("name", name);
        RequestScope.single().put("age", age);
        RequestScope.single().put("obj", obj);
        return "test-activity-result";
    }
}

2.How to Call Controller Method

Baymax.single().execute("getBaidyContent", baiduResult);
Baymax.single().controller("list").execute("张三", 38, new Timer());

```

correspondents参数为自动创建，只要通过Path注解的方法都必须保证第一个参数为Correspondents或Correspondents的子类型。
你不用管这个对象是怎么来的，你只需要定义它的类型，框架会帮你创建这个对象。

除了correspondents之外的其他参数都要在execute方法执行的时候传递进来。


Please follow this example


新增：

```
Baymax.single().activity("test-activity").start("name=1111", "age=(int)25", "intArr=(int[])45, 66, 101", "stringArr=(string[])  hello,aaa");

Baymax.single().activity("test-activity").start(new Utils.KeyValue("name", "1111"), new Utils.KeyValue("age", 25), new Utils.KeyValue("intArr", new int[]{45, 66, 101})
                        , new Utils.KeyValue("stringArr", new String[]{"  hello","aaa"}));

```