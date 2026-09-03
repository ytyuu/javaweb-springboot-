import sys
import os

try:
    import pandas as pd
    import networkx as nx
    import pyvis.network as pv
    import webbrowser
except ImportError as e:
    print(f"缺少必要的Python库: {e}")
    print("请安装所需库: pip install pandas networkx pyvis")
    sys.exit(1)

# 检查是否存在edge1.csv文件
if not os.path.exists('edge1.csv'):
    print("错误: 找不到edge1.csv文件")
    sys.exit(1)

# 从命令行参数获取指定的人物列表，如果没有则使用默认值
if len(sys.argv) > 1:
    specified_people = sys.argv[1].split(',')
else:
    # 默认人物列表
    specified_people = ['刘备', '司马懿', '孙权', '曹操', '周瑜','诸葛亮','吕布']

# 人物所属阵营
factions = {
    '刘备': '蜀汉', '关羽': '蜀汉', '张飞': '蜀汉', '赵云': '蜀汉','诸葛亮':'蜀汉',
    '曹操': '曹魏', '司马懿': '曹魏', '夏侯惇': '曹魏', '夏侯渊': '曹魏','徐晃':'曹魏','张辽': '曹魏',
    '孙权': '东吴', '周瑜': '东吴','孙策':'东吴',
    '董卓': '群雄','吕布':'群雄','袁绍':'群雄','袁术':'群雄'
}

# 读边（csv 三列：src, dst, w）
df = pd.read_csv('edge1.csv', sep=',', names=['src', 'dst', 'w'])

# 打印原始数据的前几行以检查
print("原始数据前几行:")
print(df.head())

# 过滤数据，只保留指定人物的数据
filtered_df = df[(df['src'].isin(specified_people)) & (df['dst'].isin(specified_people))]

# 打印过滤后的数据以检查
print("\n过滤后的数据:")
print(filtered_df)

# 如果过滤后的数据为空，则不执行后续操作
if filtered_df.empty:
    print("\n没有找到指定人物之间的关联。")
else:
    # 2. 建带权图
    G = nx.from_pandas_edgelist(filtered_df, 'src', 'dst', edge_attr='w')

    # 3. 初始化 pyvis 网络（非 notebook 模式）
    nt = pv.Network(height='750px', width='100%', notebook=False)

    # 4. 加节点（以人名做 id，也做标签）
    color_map = {}  # 节点颜色映射
    for node in G.nodes:
        faction = factions.get(node, '未知阵营')  # 获取人物阵营
        color_map[node] = {
            '蜀汉': '#9b6359',
            '曹魏': '#52558d',
            '东吴': '#55713b',
            '群雄': '#8d7e03'
        }.get(faction, 'purple')  # 默认颜色

        nt.add_node(str(node), label=str(node), title=str(node), color=color_map[node])

    # 5. 加边（权重映射到 edge 粗细 & 悬停提示，颜色统一为黑色）
    for u, v, d in G.edges(data=True):
        w = d['w']
        nt.add_edge(str(u), str(v), value=w, title=f'{w}', color='#cdcdcd')  # 权重映射到边粗细和悬停提示

    # 6. 关闭物理引擎，布局更稳定
    nt.options.physics.enabled = False

    # 7. 生成 HTML
    html_file = '三國共現網絡.html'
    nt.write_html(html_file, notebook=False, open_browser=False)

    print(f'完成 → {os.path.abspath(html_file)}')
    # 注意：注释掉自动打开浏览器的功能，因为这将在服务器环境中运行
    # webbrowser.open(f'file://{os.path.abspath(html_file)}')