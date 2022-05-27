# Jtestapi

#### 介绍
{**以下是 JTest1.0 API说明**
JTest1.0 是 单元测试用API，模仿的是JUnit

#### 软件架构

ExecutableJTest是接口，主要用于assertThrows()
JAssertions是所有的断言方法
JTest是@JTest注解
JTestResult封装了2个String
JTestRunnerz是主程序


#### 导包教程

import static JTest.JAssertions.*;
import JTest.JTest;
import JTest.JTestRunner;

#### 使用说明

1.  创建一个包
2.  包里面写好测试类
3.  在随便一个类里用main方法调用JTestRunner.getpackage()参数是你的包名

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
