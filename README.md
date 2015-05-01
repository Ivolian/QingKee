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
8. 为 Toolbar 添加阴影，使界面更有层次感。
9. 添加 [https://github.com/mcxiaoke/android-volley](https://github.com/mcxiaoke/android-volley)，记得添加访问网络的权限。
10. 添加 [https://github.com/navasmdc/MaterialDesignLibrary](https://github.com/navasmdc/MaterialDesignLibrary)。
11. 添加 [https://github.com/afollestad/material-dialogs](https://github.com/afollestad/material-dialogs)，
因为上一个库的 Dialog 不符合需求。
12. 用 AppCompatActivity 替换掉已过时的 ActionBarActivity，好像解决了图标对齐问题。

