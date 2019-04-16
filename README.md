# Wiki Search
- Application to display images into gridlayout from wiki pages
- Shows the detail image of search content

**How To Add Dependency Used In Project**

In your project go to **app/build.gradle** file and
add the below line, once added synch your gradle file

```
dependencies {
    ...
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.11.0'
    
    implementation 'com.github.bumptech.glide:glide:4.7.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.7.1'
}
```

- Used Architecture Pattern in app **MVVM**
- Consist of **Models**, **ViewModels** & **View** structure
- Wiki API's are used for fetching images from server using **RETROFIT** and display them in grid
using **Glide** library
- Used data binding for avoiding the boiler plate dependency 
