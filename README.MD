# An android performance test library

## 提供`android` 测试方法执行时间的库
参考仿照`JakeWharton/hugo` 库，主要是学习如何在`android`中使用`Aspectj`进行`AOP`切面编程

### 说明

`AsherDebug` 是一款基于`AOP`的性能检测库。

**主要包含一下功能：**

1. 提供函数运行时间检测


## 使用方法
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
  
2. 使用`@Time`来进行检测时间
 ```JAVA
 @Time
 private void testTime(String name, int age) {
    try {
        Thread.sleep(5000);
        Log.i(TAG, "--- name = " + name + " , age = " + age);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
 } 
 ```

## 程序运行

![pic01] (https://raw.githubusercontent.com/AsherYang/AsherDebug/master/screenshot/plugin_run_demo.png)

