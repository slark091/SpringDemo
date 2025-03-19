# Spring Cloud Demo项目

## 项目简介
这是一个基于Spring框架的示例项目，集成了Redis缓存和MongoDB数据库，并使用Springdoc进行API文档管理。本项目展示了如何构建一个现代化的Spring Cloud微服务应用。

## 技术栈
- Spring Boot
- Spring Cloud
- Redis (缓存)
- MongoDB (数据库)
- Springdoc (API文档)

## 环境要求
- JDK 17+
- Maven 3.6+
- Redis 6.0+
- MongoDB 5.0+

## 安装步骤
1. 克隆仓库：
    ```bash
    git clone https://github.com/yourusername/spring-cloud-demo.git
    ```
2. 进入项目目录：
    ```bash
    cd spring-cloud-demo
    ```
3. 安装依赖：
    ```bash
    mvn clean install
    ```
4. 运行项目：
    ```bash
    mvn spring-boot:run
    ```

## 主要功能
- REST API接口
- Redis缓存集成
- MongoDB数据持久化
- Swagger UI API文档 (访问路径: http://localhost:18080/swagger-ui.html)

## 配置说明
项目配置文件位于`src/main/resources/application.yml`，包含：
- Redis配置
- MongoDB连接配置
- Springdoc配置

## 贡献指南
欢迎贡献！请按以下步骤：
1. Fork 仓库
2. 创建新分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some feature'`)
4. 推送分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

## 许可证
MIT License