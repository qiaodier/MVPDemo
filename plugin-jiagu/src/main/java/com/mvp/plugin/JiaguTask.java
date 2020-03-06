package com.mvp.plugin;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

import java.io.File;

import javax.inject.Inject;

/**
 * @author iqiao
 * @date 2020-03-06 16:14
 * @desc 360加固Task
 */
public class JiaguTask extends DefaultTask {

    private final File apk;
    private final PluginConfigs pluginConfigs;

    @Inject
    public JiaguTask(File apk, PluginConfigs pluginConfigs) {
        setGroup("360jiagu");
        this.apk = apk;
        this.pluginConfigs = pluginConfigs;
    }

    /**
     * 双击执行
     */
    @TaskAction
    public void doTask() {
        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                //执行命令
                execSpec.commandLine("java", "-jar", pluginConfigs.qihuPath, "-importsign", pluginConfigs.keyStorePath, pluginConfigs.keyStorePass, pluginConfigs.keyStoreKeyAlias, pluginConfigs.keyStoreKeyAliasPass);
            }
        });

        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                //执行命令
                execSpec.commandLine("java", "-jar", pluginConfigs.qihuPath, "-jiagu", apk.getAbsolutePath(), apk.getParent(), "-autosign");
            }
        });

    }
}
