package com.asher.debug.plugin

import com.asher.debug.transform.JavassistTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavassistPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def log = project.logger;
        log.error "===================="
        log.error " 正在修改class !"
        log.error "===================="
        project.dependencies {
            debugCompile 'com.xtc.ouyangfan.aop:aop-time-runtime:0.0.1-SNAPSHOT'
            debugCompile 'org.aspectj:aspectjrt:1.8.6'
            compile  'com.xtc.ouyangfan.aop:aop-time-annotations:0.0.1-SNAPSHOT'
        }
        project.android.registerTransform(new JavassistTransform(project))
    }
}