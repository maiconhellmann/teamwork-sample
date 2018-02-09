# README

Libraries and tools included:

- Support libraries
- RecyclerViews, ConstraintLayout and CardViews
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
- [Retrofit 2](http://square.github.io/retrofit/)
- [Dagger 2](http://google.github.io/dagger/)
- [Timber](https://github.com/JakeWharton/timber)
- [Glide](https://github.com/bumptech/glide)
- Functional tests with [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/index.html)
- [Robolectric](http://robolectric.org/)
- [Mockito](http://mockito.org/)
- [Logging interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)

## Requirements

- [Android SDK](http://developer.android.com/sdk/index.html).
- Kotlin 1.2.21.
- Latest Android SDK Tools and build tools.

## Architecture

This project follows ribot's Android architecture guidelines that are based on [MVP (Model View Presenter)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter). Read more about them [here](https://github.com/ribot/android-guidelines/blob/master/architecture_guidelines/android_architecture.md). 

![](https://github.com/ribot/android-guidelines/raw/master/architecture_guidelines/architecture_diagram.png)

### How to implement a new screen following MVP

Imagine you have to implement a sign in screen. 

1. Create a new package under `ui` called `signin`
2. Create a new class called `SignInContract`. Inside `SignInContract`, create an interface called 
`View` extending `MvPView` and an abstract class called `Presenter` extending `BasePresenter<View>`.
```
class SignInContract{
    interface View: MvPView{
    }
    abstract class Presenter: BasePresenter < View>(){
    }
}
```
3. Create a new class called `SignInPresenter` extending `SignInContract.Presenter`.
```
class SignInPresenter(): SignInContract.Presenter(){
}
```
4. Provide dependency injection to Presenter at `PresenterModule`.  
```
@Provides
@ConfigPersistent
fun provideSignInPresenter(): SignInContract.Presenter{
    return SignInPresenter()
}
```
4. Create a new Activity called `SignInActivity`. You could also use a Fragment. You should extend
`BaseActivity()` or `BaseFragment()` and your view contract definied.
```
class SignInActivity(): BaseActivity(), SignInContract.View {
    @Inject lateinit var presenter: SignInContract.Presenter
    
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        activityComponent.inject(this)
        presenter.attachView(this)
    }
}
```
5. Provide activity injector method at `ActivityComponent` or `ConfigPersistentComponent` for fragments.
6. Create a `SignInPresenterTest` and write unit tests. Remember to mock the view and also the `DataManager`.

### Tests

To run **unit** tests on your machine:

``` 
gradlew test
``` 

### Release  
Change you version number and version name in your app build.gradle:
``` 
versionCode 1
versionName "1.0"
``` 
To generate a new version, use the following command:
``` 
gradlew clean test assembleRelease
``` 
The apk file will be in:
```
trunk\app\build\outputs\apk\release\app-release.apk
```
