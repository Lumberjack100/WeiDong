# Spring Boot 后端部署
----
## 基于腾讯云 Ubuntu Server 22.04 LTS 64bit 踩坑

#### 安装 mysql 
- 安装后，设置 root 密码，并新建账户密码
- 允许远程连接

#### 安装 redis
- 安装后，打开 sudo nano /etc/redis/redis.conf  设置允许来自任何 IP 地址的连接


#### 本机 IDEA 上运行服务
- 编译失败，可以选择 File->Invalidate Caches... 后重新编译运行
