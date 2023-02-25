# PhotoFilterApp
This Android app allows users to easily add filters to photos. Users can select their photos through the app and choose from a variety of filters. You can set the filters via the api. Users can also adjust the filters of their choice and increase or decrease the filter effect. The app allows users to save and share their filtered photos. This app is ideal for those who are interested in photo editing.

## API Information

#### Fetch All Overlays

```http
  GET https://run.mocky.io/v3/7d366396-1980-4551-a6f5-5b1cf9af1216
```

## Development Environment
- Android Studio: Dolphin or Higher
- Language: Kotlin
- Build System: Gradle

## Features
- Clean Architecture + Model View Model Model Pattern + Repository Pattern.
- Jetpack Libraries and Architecture Component
- Refrofit2, OKHTTP3 and Gson
- RxJava2
- Offline Persistence (Room Database)
- Coil
- CustomView, Recyclerview
- Lifecycle
- Navigation Component
- File operations (Save image)
- Rotation Support
- Github for CI

## Libraries
   * Data Binding
   * Live Data
   * Navigation
   * Dagger2
   * Rxjava2
   * Room
   * Coil
   * Refrofit2
   * OKHTTP3
   * Lifecycle

## Other Features
* Custom view that draws given bitmap and selected overlay bitmap.
* Custom view extend View class.   
* Drag overlay bitmap with one finger touch. Used GestureDetector.SimpleOnGestureListener
* Implement pinch zoom to overlay bitmap. Used ScaleGestureDetector.SimpleOnScaleGestureListener
* Save Image. When click to the save icon, Save drawn bitmap as JPG to the external.

## Demo
<table>
  <tr>
     <td>User Interface</td>
     <td>CustomView/Recyclerview</td>
     <td>Drag Overlay</td>
  </tr>
  <tr>
    <td style="width:270px;height:480px"><img src="images/1.png" width=270 height=480></td>
    <td style="width:270px;height:480px"><img src="images/2.png" width=270 height=480></td>
    <td style="width:270px;height:480px"><img src="images/3.png" width=270 height=480></td>
  </tr>
   <tr>
     <td>Pinch Zoom</td>
     <td>Save Image</td>
     <td></td>
  </tr>
   <tr>
    <td style="width:270px;height:480px"><img src="images/4.png" width=270 height=480></td>
    <td style="width:270px;height:480px"><img src="images/5.png" width=270 height=480></td>
    <td style="width:270px;height:480px"></td>
  </tr>
 </table>
