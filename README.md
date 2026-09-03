# 书籍人物关系可视化（javaweb-springboot）

基于 Spring Boot 的书籍人物共现关系网络可视化 Web 应用。

## 技术栈

| 层级 | 技术 |
|------|------|
| 框架 | Spring Boot 3.4.0 |
| 语言 | Java 17 |
| 构建 | Maven |
| ORM | MyBatis 3.0.3 |
| 数据库 | MySQL 8.x |
| 模板引擎 | Thymeleaf |
| 前端图表 | Apache ECharts |
| 前端关系图 | vis-network |

## 项目结构

```
src/main/java/com/example/javaweb/
├── JavawebApplication.java          # 启动类
├── config/
│   ├── WebConfig.java               # 静态资源映射
│   └── DataSourceConfig.java        # 数据源配置
├── controller/
│   ├── HelloController.java         # 测试接口
│   ├── LoginController.java         # 登录模块
│   ├── MainController.java          # 首页
│   └── ThreeKingdomsController.java # 三国数据分析
├── entity/                          # 实体类
├── mapper/                          # MyBatis Mapper
├── Service/                         # 业务逻辑层
│   └── impl/                        # Service 实现
├── Script/
│   └── img_create.py               # Python 图生成脚本(旧)
└── util/
    └── RelationGraphGenerator.java  # Java 关系图生成器
```

## 数据库表

| 表名 | 用途 |
|------|------|
| `users` | 用户认证 |
| `character_appearance` | 人物出场章回统计 |
| `character_chapter_count` | 人物章回排名 |
| `force_appearance` | 阵营出场统计 |
| `characters` | 人物生命线(首次/末次出场) |
| `edges` | 人物共现关系权重 |

## API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/hello` | 测试接口 |
| GET | `/login` | 登录页面 |
| POST | `/login` | 登录验证 |
| GET | `/` | 首页 |
| GET | `/threekingdoms` | 三国数据分析面板 |
| GET | `/character/data?names=` | 获取人物章回数据 |
| GET | `/lifeline/data?names=` | 获取人物生命线数据 |
| GET | `/force/data` | 获取阵营出场数据 |
| POST | `/submit` | 生成关系图 |

## 快速开始

### 环境要求

- JDK 17+
- MySQL 8.x
- Maven 3.6+

### 步骤

```bash
# 1. 创建数据库并导入数据
mysql -u root -p mydatabase < src/main/resources/data.sql

# 2. 修改数据库配置(如需要)
# 编辑 src/main/resources/application.properties

# 3. 构建并运行
./mvnw spring-boot:run
```

### 访问地址

- 首页: `http://localhost:8080`
- 登录: `http://localhost:8080/login`
- 三国分析: `http://localhost:8080/threekingdoms`
- 关系图: `http://localhost:8080/relations`

## 功能特性

- **人物章回排名**: 柱状图展示各人物出场次数
- **人物生命线**: 甘特图展示人物首次/末次出场章回
- **阵营出场统计**: 各阵营在不同章回区间的出场对比
- **共现关系网络**: 选择人物生成交互式关系图(基于 vis-network)
