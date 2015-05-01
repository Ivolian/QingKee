# QingKee

对青客项目的完全重构，为了理清思路，总结经验。
>
1. minSdkVersion 14，最低支持 Android 4.0。
2. 添加 [http://jakewharton.github.io/butterknife](http://jakewharton.github.io/butterknife)，简化代码。
3. 添加 [https://github.com/eluleci/FlatUI](https://github.com/eluleci/FlatUI)，美化 UI。
4. 添加 appcompat-v7:21.0.3，使低版本系统兼容 Material 主题。
5. Toolbar 的背景色不受主题的影响，需要另外设置。
6. 为 Toolbar 添加菜单：setSupportActionBar(toolbar)。
7. 改变 Toolbar 菜单主题：app:popupTheme="@style/Theme.AppCompat.Light"。  

