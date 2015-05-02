# QingKee

对青客项目的完全重构，理清思路，总结经验。
>
1. minSdkVersion 14，最低支持 Android 4.0。
2. 添加 [http://jakewharton.github.io/butterknife](http://jakewharton.github.io/butterknife)，简化代码。
3. 添加 [https://github.com/eluleci/FlatUI](https://github.com/eluleci/FlatUI)，确定应用颜色。
4. 添加 appcompat-v7:22.1.1，兼容低版本。
5. Toolbar 的背景色不受主题的影响，需要另外设置。
6. 为 Toolbar 添加菜单：setSupportActionBar(toolbar)。
7. 改变 Toolbar 菜单样式：app:popupTheme="@style/Theme.AppCompat.Light"。
8. 为 Toolbar 添加阴影，使界面更有层次感。
9. 添加 [https://github.com/mcxiaoke/android-volley](https://github.com/mcxiaoke/android-volley)，记得添加访问网络的权限。
10. 添加 [https://github.com/navasmdc/MaterialDesignLibrary](https://github.com/navasmdc/MaterialDesignLibrary)。
11. 添加 [https://github.com/afollestad/material-dialogs](https://github.com/afollestad/material-dialogs)，
因为上一个库的 Dialog 不够棒。
12. 用 AppCompatActivity 替换掉已过时的 ActionBarActivity。
13. 添加 [https://github.com/ksoichiro/Android-ObservableScrollView](https://github.com/ksoichiro/Android-ObservableScrollView)
和 [http://nineoldandroids.com](http://nineoldandroids.com)，实现 toolbar 自动隐藏的 activity。
14. 添加 [https://github.com/traex/RippleEffect](https://github.com/traex/RippleEffect)。
15. 添加 [https://github.com/Lesilva/BetterSpinner](https://github.com/Lesilva/BetterSpinner) 以及
[https://github.com/rengwuxian/MaterialEditText](https://github.com/rengwuxian/MaterialEditText)

