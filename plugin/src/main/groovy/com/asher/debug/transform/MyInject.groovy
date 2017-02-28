package com.asher.debug.transform

import com.asher.debug.util.Utils
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

import java.lang.annotation.Annotation

public class MyInject {
    public final static ClassPool pool = ClassPool.getDefault();

    public static void injectDir(String path, String packageName, Project project) {
        pool.appendClassPath(path)

        // project.android.bootClassPath 加入android.jar，否则找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        Utils.importBaseClass(pool);
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
                    if (isMyPackage) {
                        String className = Utils.getClassName(index, filePath)
                        CtClass c = pool.getCtClass(className)
                        c.stopPruning(true)
                        if (c.isFrozen()) {
                            c.defrost()
                        }
                        for (CtMethod ctMethod : c.getDeclaredMethods()) {
                            String methodName = Utils.getSimpleName(ctMethod, project);
                            for (Annotation mAnnotation : ctMethod.getAnnotations()) {
                                String annoName = mAnnotation.annotationType().canonicalName
                                project.logger.error "----> annoName = $annoName"
                                if (mAnnotation.annotationType().canonicalName.equals(Utils.TimeAnnotation)) {
                                    project.logger.error "==== @Time 方法正在修改 ===="
                                    String insertStr = "{Log.i(\"-- TAG --\", \"Time插入代码成功\");}\n"
                                    ctMethod.insertBefore(insertStr)
                                }
                            }
                            if (Utils.ON_CREATE.contains(methodName)) {
                                project.logger.error "==== onCreate 方法正在修改 ===="
                                ctMethod.insertBefore("{Log.i(\"-- TAG --\", \"onCreate插入代码成功\");}\n")
                            }
                        }
//                        c.writeFile()
                        project.logger.error "path -> $path"
                        c.writeFile(path)
                        //用完一定记得要卸载，否则pool里的永远是旧的代码
                        c.detach()
                    }
                }

            }
        }
    }
}