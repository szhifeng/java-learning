package com.szf.example;


import org.gradle.api.Plugin;
import org.gradle.api.Project;

class CustomBuildPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("customBuild", task -> {
            task.doLast(t -> {
                System.out.println("Running custom build tasks");
                // 添加构建逻辑，例如执行 clean、test、compile、package、install 等任务

            });
        });
    }
}