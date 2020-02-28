

# 功能概要
   * MVP架构代码简单实现(代码都在com.mvp.cn.keep包下)
   * 增加jetpack组件Lifecycle对Activity生命周期监听
   * 集成微信MMKV框架，LoginActivity做了MMKV和Sharedpreference的写入1000次数据的耗时对比(MMKV进阶使用请参考[MMKV进阶使用指南](https://github.com/Tencent/MMKV/wiki/android_advance_cn)
   * 集成微信xLog框架，并输出到文件(源码中已经编译出arm64-v8a、armeabi-v7a、x86、x86—64架构的so库可直接使用)
   * 自定义OKHTTPDns解析类
   * 应用保活简单实现：APP保活(拉活)代码都在com.mvp.cn.keep包下
   * 使用自定义注解处理器完成对模板代码的自动生成（目前模板代码还未使用javapoet）
   * 使用自定义注解和反射的方式实现对switch语句中多个case的优化
        + 调用示例 RunTaskFactory.buildRunTask(type,content);
   * ~~增加日志打印类（包括将log输出到文件中功能）~~
   * 应用实现自动安装（模拟用户点击效果来实现自动安装，会有系统安装应用过程的展示页面，并非静默安装）
     + InstallService 模拟用户点击的服务类
     + accessibility_service_config.xml 配置Android系统的无障碍服务，具体参数文件里有说明



