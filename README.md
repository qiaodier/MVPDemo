# 2020-02-24 更新内容：自定义注解处理器
   * 使用自定义注解处理器完成对模板代码的自动生成（目前模板代码还未使用javapoet）
# 2020-02-22 更新内容：优化mvp架构代码
   * 去掉之前一些无关代码；
   * mvp架构代码都在com.mvp.cn.mvp包下
   * 增加jetpack组件Lifecycle对Activity生命周期监听
# 2020-02-18 更新内容:应用实现自动安装
  * 增加日志打印类（包括将log输出到文件中功能）
  + 应用实现自动安装（模拟用户点击效果来实现自动安装，会有系统安装应用过程的展示页面，并非静默安装）
     - InstallService 模拟用户点击的服务类
     - accessibility_service_config.xml 配置Android系统的无障碍服务，具体参数文件里有说明
# 2020-01-09 更新内容:对switch语句中多个case的优化
  * 使用自定义注解和反射的方式实现对switch语句中多个case的优化
  + 调用示例
     - RunTaskFactory.buildRunTask(type,content);
# MVPDemo
mvpDemo试写；
