# Wikift Center

Wikift 服务注册中心, 该中心管理所有的 Wikift 应用服务

# 编译部署

 - 编译源码
 
    ```bash
    mvn clean package -Dmaven.test.skip=true -X
    ```
 - 部署服务
 
    ```bash
    java -jar wikift-center/target/wikift-center-1.0.0.jar --server.port=8001
    ```