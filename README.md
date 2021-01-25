# spring-mqtt
spring下mqtt的订阅和发布示例，其中，订阅话题可灵活配置主题名及其处理方法

# 运行
  - 运行emqx
    - 官网下载emqx，下载地址https://www.emqx.cn/downloads#broker
    - 命令行进入emqx解压路径，启动emqx，启动命令：./bin/emqx start
    - emq访问地址：http://localhost:18083/ ，登录账号admin 密码public
  - 运行程序
    - 无需修改配置文件，启动程序

# 发布订阅
  - 使用程序发布主题
    - 在emqx界面中http://localhost:18083/#/websocket ，订阅主题/service/send
	- post方式请求接口http://localhost:8080/sendTest，即可通过程序发布主题
	- 在emqx界面可查看已订阅的消息列表
  - 使用程序订阅主题
    - 在程序里新建订阅类，继承自SubcribeProcessor接口，类中需完善订阅主题和主题payload处理方法。
	  如：我已在程序中新建了TestOneSubcribeProcessor类，继承自SubcribeProcessor接口，订阅主题名为test1
	- 接上，在emqx界面中http://localhost:18083/#/websocket，发布主题 /test1， 发布内容 test1 content
	- 可在console界面看到主题名为test1的订阅内容：“process topic1,receive payload content:hello test1”
