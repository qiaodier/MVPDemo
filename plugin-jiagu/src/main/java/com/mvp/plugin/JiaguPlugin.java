package com.mvp.plugin;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

/**
 * @author iqiao
 * 360 jiagu plugin
 */
public class JiaguPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // find gradle file
        final PluginConfigs pluginConfigs = project.getExtensions().create("360jiagu", PluginConfigs.class);
        // listener
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                //get app gradle file
                AppExtension byType = project.getExtensions().getByType(AppExtension.class);
                //get all variants
                byType.getApplicationVariants().all((ApplicationVariant applicationVariant) -> {
                    applicationVariant.getOutputs().all((BaseVariantOutput baseVariantOutput) -> {
                        // get output file
                        File outputFile = baseVariantOutput.getOutputFile();
                        String name = baseVariantOutput.getName();
                        //create jiagu task
                        project.getTasks().create("jiagu" + name, JiaguTask.class, outputFile, pluginConfigs);
                    });
                });
            }
        });
    }
}
