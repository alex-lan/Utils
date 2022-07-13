# Utils

> A utilities-lib made by AlexL, better to use in kotlin!

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
"xxxx".toast() / "xxxx".toast(0 or 1)

"xxxx".i() / "xxxx".e() / "xxxx".d() / "xxxx".v() / ...

(int or float).px2dp() / (int or float).dp2px() / ...

WeakReference: var any by Weak<Any>() ...

_screenWidth / _screenHeight

"xxx".md5() / "xxx".sha1() / "xxx".sha256()

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
