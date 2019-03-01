# TodoList_b
todolist########################
西二在线四轮考核寒假作业
黎家泽提交


本次作业我选的是TodoList，在这里说明一下用到的东西以及存在的问题

该程序通过用户注册账号使用，用户注册时，程序会给用户分配一个唯一性的账号，该账号是通过我的linux服务器上面的MySQL数据库分配的。

用户可以通过账号、密码登录，也可以通过QQ登录

在程序中总共有以下3个数据库

1、linux服务器上面的MySQL数据库，该数据库仅用于给用户分配账号

2、用SQLite实现的数据库，该数据库用于记录在本地所有的申请的账号和密码

3、用LitePal实现的数据库，该数据库用于记录用户所添加的每一条todolist（以最新的编辑时间作为todolist的唯一身份）

####################

程序中还用到很多第三方库，具体可以看源代码

#####################

程序存在的重大问题：

在真机测试中，我用到了OPPO R9（Android 6.0.1）、360（Android 6.0.0）、努比亚 Z17（Android 8.0.1）、以及联想的ZUK Z2 Pro（Android 8.0.0）

测试时，OPPO和360的手机仅能打开第一个页面，打开第二个页面时，出现了闪退，我初步猜测是含有第三方控件不支持Android 6.0.0等版本

努比亚手机能成功运行，但是UI出现偏差

ZUK手机，该手机一直作为程序开发的测试真机，所有的功能均达到预期的效果

上述的问题尚未解决

##################


