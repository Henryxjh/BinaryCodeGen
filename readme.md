<h1>生成各种条码的工具</h1>
现在只支持二维码，计划加入Zxing库支持的所有条码类型。

<h2>使用</h2>
命令行或无构建工具时用Standalone版。
其他时候用标准版，需要args4j.args4j:2.37和com.google.zxing.javase:3.5.3作为依赖。

<h2>编译</h2>
使用jdk17编写，可以通过更改pom.xml中的maven.compiler.source和maven.compiler.target更改产物版本。

<h2>下载</h2>
<a href="https://github.com/Henryxjh/BinaryCodeGen/releases/latest">Github Release</a>下载最新版。