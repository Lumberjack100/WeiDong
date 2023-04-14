# Spring Boot 后端部署
----
## 基于腾讯云 Ubuntu Server 22.04 LTS 64bit 踩坑,借助 ChatGPT 辅助

#### 安装 mysql 
- 安装后，设置 root 密码，并新建账户密码
- 允许远程连接

#### 安装 redis
- 安装后，打开 sudo nano /etc/redis/redis.conf  设置允许来自任何 IP 地址的连接


#### 本机 IDEA 上运行服务
- 编译失败，可以选择 File->Invalidate Caches... 后重新编译运行


#### 通过 IP 访问 Mac 上的本地图片,
- 在端口 9997 上启动一个 web 服务器: python3 -m http.server 9997
- 获取您的 Mac 的本地 IP 地址: ifconfig | grep "inet " | grep -Fv 127.0.0.1 | awk '{print $2}'