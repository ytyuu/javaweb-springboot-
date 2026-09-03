# Python 环境设置说明

为了使用人物关系图生成功能，您需要安装以下Python库：

## 安装依赖

```bash
pip install pandas networkx pyvis
```

或者使用requirements.txt文件：

```bash
pip install -r requirements.txt
```

## 数据文件

确保项目根目录下有 `edge1.csv` 文件，格式应为三列：src, dst, w
- src: 起始人物
- dst: 目标人物
- w: 关系权重

## 测试Python环境

在项目根目录运行以下命令测试Python环境：

```bash
python -c "import pandas, networkx, pyvis; print('所有依赖库均已安装')"
```

## 如何使用

1. 启动Spring Boot应用
2. 访问 http://localhost:8080/threekingdoms
3. 在左侧选择要分析的人物
4. 点击"生成网络图"按钮
5. 查看右侧生成的关系图

注意：生成的HTML文件名为"三國共現網絡.html"，会自动显示在界面上。