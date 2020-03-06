

# 功能概要
   * 使用`Artifactory`创建私有库，加快项目构建速度

### app
   * ~~增加日志打印类（包括将log输出到文件中功能）~~
   * 应用实现自动安装（模拟用户点击效果来实现自动安装，会有系统安装应用过程的展示页面，并非静默安装）
       + `InstallService` 模拟用户点击的服务类
       + `accessibility_service_config.xml` 配置Android系统的无障碍服务，具体参数文件里有说明
   * 新增打包自动上传蒲公英插件upload2Pgyer
       + 使用命令`gradlew u2p`

### mvp_master
   * MVP架构代码简单实现(目前还未使用`Dagger2`框架)
   * 增加`jetpack`组件`Lifecycle对Activity`生命周期监听
   * 集成微信MMKV框架，`LoginActivity`做了`MMKV`和`Sharedpreference`的写入1000次数据的耗时对比(MMKV进阶使用请参考[MMKV进阶使用指南](https://github.com/Tencent/MMKV/wiki/android_advance_cn)
   * 集成微信xLog框架，并输出到文件(源码中已经编译出arm64-v8a、armeabi-v7a、x86、x86-64架构的so库可直接使用)
       + 查看日志文件mars\mars\log\crypt执行 python decode_mars_nocrypt_log_file.py C:\Users\i\Desktop\mvpDemo_20200228.xlog命令,更多请参考[xlog进阶使用指南](https://github.com/Tencent/mars/wiki/Mars-Android-%E6%8E%A5%E5%85%A5%E6%8C%87%E5%8D%97)
   * 自定义OKHTTPDns解析类
   * 应用保活简单实现：APP保活(拉活)代码都在com.mvp.master.keep包下
   * 使用自定义注解和反射的方式实现对switch语句中多个case的优化
       + 调用示例 `RunTaskFactory.buildRunTask(type,content);`

###router-compiler
   * 使用自定义注解处理器完成对模板代码的自动生成（目前模板代码还未使用javapoet,javapoet正在学习中...）

###plugins
   * 实现360加固、签名、上传蒲公英（上传功能还待优化，不影响上传）。








