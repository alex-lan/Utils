# Utils

> A Utilities-Lib edited by AlexL, better to use in kotlin!

[![](https://jitpack.io/v/alex-lan/Utils.svg)](https://jitpack.io/#alex-lan/Utils)
[![](https://img.shields.io/badge/license-Apache2-blue)](https://opensource.org/licenses/Apache-2.0)

***

### Usege
1. Add the JitPack repository to your build file

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
2. Add the dependency

```
dependencies {
    implementation 'com.github.alex-lan:Utils:$version'
}
```
3. Init & Use
```
AUtils.init {
    context = ...
    ... ...
}
```
```
1. Toast: "xxxx".toast() / "xxxx".toast(0 or 1)

2. Logcat: "xxxx".i() / "xxxx".e() / "xxxx".d() / "xxxx".v() / ...

3. 转换：(int or float).px2dp() / (int or float).dp2px() / ...

4. 弱引用：WeakReference: var any by Weak<Any>() ...

5. 全局屏幕宽高：_screenWidth / _screenHeight

6. _network 是 NetWork 的一个全局实例，可以获得网络状态和类别：如 _network.isConnected、_network.isWifi、_network.netType 等...

7. 加密："xxx".md5() / "xxx".sha1() / "xxx".sha256()

8. 提供一些零星方法：比如 ExecutorService.shutdown() 、View.getAllChildViews()

9. RecyclerViewItemClickSupport：使用在绑定监听 RecyclerView - item 的点击事件，防双击，这是可选方法之一（MVVM 架构下几乎用不到）
设置方法：RecyclerViewItemClickSupport.addTo(recyclerView).setOnItemClickListener(listener)
注销监听：RecyclerViewItemClickSupport.removeFrom(recyclerView)

10. SoftKeyBroadStateObserve 软键盘状态监听，使用方法: SoftKeyBroadStateObserve.with(decorView).addStateListener(object :listener)

... ...
```

***

### LICENSE
````
Copyright 2020 AlexL

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````
