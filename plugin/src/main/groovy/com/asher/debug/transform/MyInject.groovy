package com.asher.debug.transform

import javassist.ClassPool
import org.gradle.api.Project

public class MyInject {
    public final static ClassPool pool = ClassPool.getDefault();

    public static void injectDir(String path, String packageName, Project project) {
        pool.appendClassPath(path)
        project.logger.err "inject path = " + path + " , packageName = " + packageName + " , project = " + project

        // project.android.bootClassPath 加入android.jar，否则找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse {File file ->
                String filePath = file.absolutePath
                // 确保当前文件是class文件，并且不是系统自动生成的class文件
                if (filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('$') // 代理类
                        && !filePath.contains('R.class') && !filePath.contains('BuildConfig.class')) {
                    // 判断当前目录是否是在我们的应用包里面
                    int index = filePath.indexOf(packageName)
                    boolean isMyPackage = (index != -1)
                }

            }
        }
    }
}