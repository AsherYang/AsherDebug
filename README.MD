# An android performance test library

## 提供`android` 测试方法执行时间的库
参考仿照`JakeWharton/hugo` 库，主要是学习如何在`android`中使用`Aspectj`进行`AOP`切面编程

## AsherDebug 工程分为两个阶段
1. 第一个阶段，完成`AOP`编程，并将宿主与切面联系起来可以使用。
2. 第二个阶段，完成`gradle plugin`的`maven`私服支持。解放所有的宿主的引用编译方式。

### 说明
`AsherDebug` 的第二阶段的`maven`私服支持所使用的是公司内部地址。若需要外部支持，可进行相应替换。

可以替换成第三方的`maven`库，或者`jCenter`等。请自行替换。

若无第二阶段，可以使用第一阶段方式:

1. 先将第二阶段使用的相关`maven`信息删除，
2. 在宿主模块下的`build.gradle`中加入`ajc` 编译器支持，
3. 同时去除宿主模块下的引入申明方式，
4. 修改其他项目的引用方式，其中包括`runtime`引用的`annotations`的引用方式，改为`compile project` 方式等


## 程序运行

1. 在宿主`build.gradle`中添加依赖支持
  ```JAVA
  buildscript {
      repositories {
          mavenCentral()
          maven { url "http://172.28.10.222:8081/nexus/content/groups/android_public/" }
      }
      dependencies {
          classpath 'com.xtc.ouyangfan.aop:aop-time-plugin:0.0.7-SNAPSHOT'
      }
  }
  
  apply plugin: 'asher'
  ```
  
2. 运行效果

![pic01] (https://raw.githubusercontent.com/AsherYang/AsherDebug/master/screenshot/plugin_run_demo.png)