# wikift

wikift是一套开源的易用的知识管理系统.

## 编译源码

```bash
mvn clean package -X
```

## 打包docker镜像

```bash
mvn package assembly:assembly docker:build -X
```

## 提交docker镜像到中心仓库

```bash
mvn clean package assembly:assembly docker:push -X
```

## 运行在docker

 - 运行mysql容器

	```bash
	docker run --name wikift-mysql -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=wikift mysql
	```
	
 - 运行wikift server

	```bash
	docker run -p 8080:8080 --name wikift-server --link wikift-mysql:mysql wikift/wikift-server
	```
	
 - 运行wikift server

	```bash
	docker run -p 4200:4200 --name wikift-web --link wikift-server:wikift-server wikift/wikift-web
	```

## wikift系统设计快照

#### 用户未登录首页

[![用户未登录首页](https://raw.githubusercontent.com/qianmoQ/wikift/master/wikift-design/snapshot/%E7%94%A8%E6%88%B7%E6%9C%AA%E7%99%BB%E5%BD%95%E9%A6%96%E9%A1%B5.png "用户未登录首页")](https://raw.githubusercontent.com/qianmoQ/wikift/master/wikift-design/snapshot/%E7%94%A8%E6%88%B7%E6%9C%AA%E7%99%BB%E5%BD%95%E9%A6%96%E9%A1%B5.png "用户未登录首页")

#### 用户已登录首页

[![用户已登录首页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户已登录首页.png?raw=true "用户已登录首页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户已登录首页.png?raw=true "用户已登录首页")

#### 用户未登录文章页

[![用户未登录文章页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户未登录文章页.png?raw=true "用户未登录文章页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户未登录文章页.png?raw=true "用户未登录文章页")

#### 用户已登录文章页

[![用户已登录文章页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户已登录文章页.png?raw=true "用户已登录文章页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户已登录文章页.png?raw=true "用户已登录文章页")

#### 用户登录页

[![用户登录页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户登录页.png?raw=true "用户登录页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户登录页.png?raw=true "用户登录页")

#### 用户注册页

[![用户注册页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户注册页.png?raw=true "用户注册页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户注册页.png?raw=true "用户注册页")

#### 用户写文章

[![用户写文章](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户写文章页.png?raw=true "用户写文章")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户写文章页.png?raw=true "用户写文章")

#### 用户文章发布流程

[![用户文章发布流程](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户文章发布流程.png?raw=true "用户文章发布流程")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户文章发布流程.png?raw=true "用户文章发布流程")

#### 用户个人信息页

[![用户个人信息页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户个人信息页.png?raw=true "用户个人信息页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户个人信息页.png?raw=true "用户个人信息页")

#### 空间页

[![空间页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/空间页.png?raw=true "空间页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/空间页.png?raw=true "空间页")

#### 创建空间页

[![创建空间页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/创建空间页.png?raw=true "创建空间页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/创建空间页.png?raw=true "创建空间页")

#### 空间详情页

[![空间详情页](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/空间详情页.png?raw=true "空间详情页")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/空间详情页.png?raw=true "空间详情页")

#### 用户文章评论趋势

[![用户文章评论趋势](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户文章评论趋势.png?raw=true "用户文章评论趋势")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户文章评论趋势.png?raw=true "用户文章评论趋势")

#### 首页文章标签云

[![首页文章标签云](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/首页文章标签云.png?raw=true "首页文章标签云")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/首页文章标签云.png?raw=true "首页文章标签云")

#### 用户文章浏览趋势

[![用户文章浏览趋势](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户文章浏览趋势.png?raw=true "用户文章浏览趋势")](https://github.com/qianmoQ/wikift/blob/master/wikift-design/snapshot/用户文章浏览趋势.png?raw=true "用户文章浏览趋势")
