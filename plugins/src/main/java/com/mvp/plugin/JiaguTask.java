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
        setGroup("jiagu");
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
                //导入签名
                execSpec.commandLine("java", "-jar", pluginConfigs.qihuPath, "-importsign", pluginConfigs.keyStorePath, pluginConfigs.keyStorePass, pluginConfigs.keyStoreKeyAlias, pluginConfigs.keyStoreKeyAliasPass);
            }
        });

        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                //加固+签名
                execSpec.commandLine("java", "-jar", pluginConfigs.qihuPath, "-jiagu", apk.getAbsolutePath(), apk.getParent(), "-autosign");
            }
        });
        File file = new File(apk.getParent());
        if (!file.exists()) {
            getProject().getLogger().error("file path not find");
            return;
        }
        if (!file.isDirectory()) {
            getProject().getLogger().error("file path not dir");
            return;
        }
        File[] files = file.listFiles((File pathname) -> {
            return pathname.getAbsolutePath().endsWith("sign.apk");
        });
        if (files.length > 0) {
            getProject().exec((ExecSpec execSpec) -> {
                getProject().getLogger().error("=========>>>>>upload starting ...\n");
                ExecSpec curl = execSpec.commandLine("curl", "-F", "file=@" + files[0].getAbsolutePath(), "-F", "_api_key=" + pluginConfigs.apiKey, "-F", "uKey=" + pluginConfigs.uKey, "-F", "buildUpdateDescription=" + pluginConfigs.desc, "-F", "buildInstallType=2", "-F", "buildPassword=" + pluginConfigs.pwd, "http://www.pgyer.com/apiv1/app/upload");
//                OutputStream fos = curl.getStandardOutput();
//                OutputStreamWriter writer = new OutputStreamWriter(fos);
//                BufferedReader br = new BufferedReader(writer);
//                String line;
//
//                try {
//                    getProject().getLogger().error("=========>>>>>upload response ...\n");
//                    while ((line = br.readLine()) != null) {
//                        String startStr = "\"buildShortcutUrl\":\"";
//                        int start = line.indexOf(startStr) + startStr.length();
//                        int end = line.indexOf("\"", start);
//                        if (start > 0 && end > 0) {
//                            String sUrl = line.substring(start, end);
////                            execSpec.commandLine("cmd   /c   start https://www.pgyer.com/" + sUrl);
////                                        runtime.exec("cmd   /c   start  https://www.pgyer.com/$sUrl")
//                            getProject().getLogger().error("> Task: upload2Pgyer======> apk upload finish !!!>>>" + "<<< click here please：https://www.pgyer.com/" + sUrl);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            });
        } else {
            getProject().getLogger().error("files is null");
        }
//        getProject().exec(new Action<ExecSpec>() {
//            @Override
//            public void execute(ExecSpec execSpec) {
//                String fileName = apk.getName();
//                String newFileName = fileName.substring(0, fileName.length() - 4) + "_10_jiagu_sign.apk";
//                ExecSpec curl = execSpec.commandLine("curl", "-F", "file=@" + apk.getParent() + "\\" + newFileName, "-F", "_api_key=" + pluginConfigs.apiKey, "-F", "uKey=" + pluginConfigs.uKey, "-F", "buildUpdateDescription=" + pluginConfigs.desc, "-F", "buildInstallType=2", "-F", "buildPassword=" + pluginConfigs.pwd, "http://www.pgyer.com/apiv1/app/upload");
//                //TODO youhuabufen
//                InputStream fis = curl.getStandardInput();
//                InputStreamReader isr = new InputStreamReader(fis);
//                BufferedReader br = new BufferedReader(isr);
//                String line = null;
//                try {
//                    logging.debug("> Task: upload2Pgyer======>upload result :");
//                    while ((line = br.readLine()) != null) {
//                        logging.debug("> Task: upload2Pgyer======>" + line);
//                        String startStr = "\"buildShortcutUrl\":\"";
//                        int start = line.indexOf(startStr) + startStr.length();
//                        int end = line.indexOf("\"", start);
//                        if (start > 0 && end > 0) {
//                            String sUrl = line.substring(start, end);
//                            execSpec.commandLine("cmd   /c   start https://www.pgyer.com/" + sUrl);
////                                        runtime.exec("cmd   /c   start  https://www.pgyer.com/$sUrl")
//                            logging.debug("> Task: upload2Pgyer======> apk upload finish !!!>>>" + "<<< click here please：https://www.pgyer.com/" + sUrl);
//                        }
//                    }
//                    br.close();
//                    isr.close();
//                    fis.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });


    }
}
