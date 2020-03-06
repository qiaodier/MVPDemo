package com.mvp.plugin;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.File;

/**
 * @author iqiao
 * 360 jiagu plugin
 */
public class JiaguPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // find gradle file
        final PluginConfigs pluginConfigs = project.getExtensions().create("jiagu", PluginConfigs.class);
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
                        project.getTasks().create(name + "-jiagu", JiaguTask.class, outputFile, pluginConfigs).doLast(new Action<Task>() {
                            @Override
                            public void execute(Task task) {


                            }
                        });

//                        Set<File> files = outputs.getPreviousOutputFiles();
//                        files.forEach(new Consumer<File>() {
//                            @Override
//                            public void accept(File file) {
//                                if (file.getName().endsWith("sign.apk")) {
//                                    project.exec(new Action<ExecSpec>() {
//                                        @Override
//                                        public void execute(ExecSpec execSpec) {
//                                            /*def uploadCommand = "curl " +
//                                        "-F \"file=@${outputFile.absolutePath}\"" +
//                                        " -F \"_api_key=$pgyerApiKey\" " +
//                                        "-F \"buildUpdateDescription=$desc\" " +
//                                        "-F \"buildInstallType=2\" " +
//                                        "-F \"buildPassword=123\" " +
//                                        "https://www.pgyer.com/apiv2/app/upload"*/
//                                            ExecSpec execSpec1 = execSpec.commandLine("curl", "-k", "http://www.pgyer.com/apiv1/app/upload", "-F", "file=@" + file.getAbsolutePath(), "-F", "_api_key", pluginConfigs.apiKey, "-F", "buildUpdateDescription=" + pluginConfigs.desc, "-F", "buildInstallType=2", "-F", "buildPassword=" + pluginConfigs.pwd);
//                                            List<String> args = execSpec1.getArgs();
//                                            args.forEach(new Consumer<String>() {
//                                                @Override
//                                                public void accept(String s) {
//                                                    project.getLogger().log(LogLevel.DEBUG, s);
//                                                }
//                                            });
//
//                                        }
//                                    });
//                                }
//                            }
//                        });

                    });
                });
            }
        });
    }
}
