package com.example.javaweb.service;

import com.example.javaweb.Service.EdgeService;
import com.example.javaweb.entity.Edge;
import com.example.javaweb.util.RelationGraphGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class RelationGraphService {

    @Autowired
    private EdgeService edgeService;

    // 人物所属阵营
    private static final Map<String, String> FACTIONS = new HashMap<>();

    static {
        FACTIONS.put("刘备", "蜀汉");
        FACTIONS.put("关羽", "蜀汉");
        FACTIONS.put("张飞", "蜀汉");
        FACTIONS.put("赵云", "蜀汉");
        FACTIONS.put("诸葛亮", "蜀汉");
        FACTIONS.put("曹操", "曹魏");
        FACTIONS.put("司马懿", "曹魏");
        FACTIONS.put("夏侯惇", "曹魏");
        FACTIONS.put("夏侯渊", "曹魏");
        FACTIONS.put("徐晃", "曹魏");
        FACTIONS.put("张辽", "曹魏");
        FACTIONS.put("孙权", "东吴");
        FACTIONS.put("周瑜", "东吴");
        FACTIONS.put("孙策", "东吴");
        FACTIONS.put("董卓", "群雄");
        FACTIONS.put("吕布", "群雄");
        FACTIONS.put("袁绍", "群雄");
        FACTIONS.put("袁术", "群雄");
    }

    // 阵营颜色映射
    private static final Map<String, String> FACTION_COLORS = new HashMap<>();

    static {
        FACTION_COLORS.put("蜀汉", "#9b6359");
        FACTION_COLORS.put("曹魏", "#52558d");
        FACTION_COLORS.put("东吴", "#55713b");
        FACTION_COLORS.put("群雄", "#8d7e03");
    }

    /**
     * 生成关系图HTML
     * @param selectedPeople 选定的人物列表
     * @param outputPath 输出HTML文件路径
     */
    public void generateRelationGraph(List<String> selectedPeople, String outputPath) throws IOException {
        System.out.println("=== 调试信息 ===");
        System.out.println("输入的选定人物: " + selectedPeople);
        System.out.println("选定人物数量: " + selectedPeople.size());
        
        // 从数据库读取边数据
        System.out.println("开始查询数据库中的所有边数据...");
        List<Edge> edges = edgeService.findAll();
        System.out.println("从数据库查询到的边总数: " + edges.size());

        if (edges.isEmpty()) {
            throw new IOException("无法从数据库中读取边数据");
        }

        System.out.println("数据库中的所有数据详情:");
        int count = 0;
        Set<String> allNodes = new HashSet<>();
        for (Edge edge : edges) {
            System.out.println("  [" + (++count) + "] " + edge.getSrc() + " -> " + edge.getDst() + " (weight: " + edge.getWeight() + ")");
            allNodes.add(edge.getSrc());
            allNodes.add(edge.getDst());
        }
        
        System.out.println("数据库中所有出现的人物: " + allNodes);
        System.out.println("数据库中人物总数: " + allNodes.size());

        System.out.println("指定的人物: " + selectedPeople);
        Set<String> selectedSet = new HashSet<>(selectedPeople);
        System.out.println("指定人物集合: " + selectedSet);

        // 检查哪些指定的人物在数据库中存在
        Set<String> matchedNodes = new HashSet<>();
        for (String person : selectedSet) {
            if (allNodes.contains(person)) {
                matchedNodes.add(person);
            }
        }
        System.out.println("在数据库中存在的指定人物: " + matchedNodes);
        System.out.println("在数据库中不存在的指定人物: " + 
                          selectedSet.stream()
                                   .filter(p -> !allNodes.contains(p))
                                   .toArray(String[]::new));

        // 过滤数据，只保留指定人物之间的数据（双向关系）
        List<Edge> filteredEdges = new ArrayList<>();
        for (Edge edge : edges) {
            // 检查边的两个端点是否都在选中的人物列表中
            if (selectedSet.contains(edge.getSrc()) && selectedSet.contains(edge.getDst())) {
                System.out.println("  匹配的边: " + edge.getSrc() + " -> " + edge.getDst() + " (weight: " + edge.getWeight() + ")");
                filteredEdges.add(edge);
            }
        }

        System.out.println("\n过滤后的数据 (总共 " + filteredEdges.size() + " 条边):");
        for (Edge edge : filteredEdges) {
            System.out.println("  " + edge.getSrc() + "," + edge.getDst() + "," + edge.getWeight());
        }

        if (filteredEdges.isEmpty()) {
            System.out.println("\n没有找到指定人物之间的关联。");
            System.out.println("请检查数据库中是否包含这些人名，以及它们之间是否有关系数据。");
            // 即使没有边，也要生成一个基本的HTML文件
            generateEmptyGraphHtml(selectedPeople, outputPath);
            return;
        }

        System.out.println("\n最终用于生成图的边数据 (未合并相同节点对的边):");
        for (Edge edge : filteredEdges) {
            System.out.println("  " + edge.getSrc() + "," + edge.getDst() + "," + edge.getWeight());
        }

        // 生成HTML文件
        generateGraphHtml(selectedPeople, filteredEdges, outputPath);
    }

    /**
     * 生成包含关系图的HTML文件
     */
    private void generateGraphHtml(List<String> selectedPeople, List<Edge> edges, String outputPath) throws IOException {
        Set<String> nodes = new HashSet<>();
        for (Edge edge : edges) {
            nodes.add(edge.getSrc());
            nodes.add(edge.getDst());
        }

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n<head>\n");
        html.append("<meta charset=\"utf-8\">\n");
        html.append("<title>三国人物关系网络图</title>\n");
        html.append("<script type=\"text/javascript\" src=\"https://unpkg.com/vis-network/standalone/umd/vis-network.min.js\"></script>\n");
        html.append("<link href=\"https://unpkg.com/vis-network/styles/vis-network.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
        html.append("<style type=\"text/css\">\n");
        html.append("#mynetwork { width: 100%; height: 750px; }\n");
        html.append("</style>\n");
        html.append("</head>\n<body>\n");
        html.append("<div id=\"mynetwork\"></div>\n");
        html.append("<script type=\"text/javascript\">\n");

        // 创建节点数据
        html.append("var nodes = [\n");
        for (String node : nodes) {
            String faction = FACTIONS.getOrDefault(node, "未知阵营");
            String color = FACTION_COLORS.getOrDefault(faction, "purple");
            html.append("  {id: '").append(node).append("', label: '").append(node)
               .append("', color: {background: '").append(color).append("'}},\n");
        }
        html.append("];\n\n");

        // 创建边数据 - 包含权重标签
        html.append("var edges = [\n");
        for (Edge edge : edges) {
            html.append("  {from: '").append(edge.getSrc()).append("', to: '").append(edge.getDst())
               .append("', value: ").append(edge.getWeight()).append(", label: '").append(edge.getWeight()).append("'},\n");
        }
        html.append("];\n\n");

        // 初始化网络
        html.append("var container = document.getElementById('mynetwork');\n");
        html.append("var data = {\n  nodes: new vis.DataSet(nodes),\n  edges: new vis.DataSet(edges)\n};\n");
        html.append("var options = {\n");
        html.append("  physics: { enabled: false },\n");
        html.append("  edges: { smooth: { enabled: true } },\n");
        html.append("  nodes: { size: 20, font: { size: 14, color: '#000000' } },\n");
        html.append("  interaction: { hover: true }\n");
        html.append("};\n");
        html.append("var network = new vis.Network(container, data, options);\n");
        html.append("</script>\n</body>\n</html>");

        // 写入文件
        try (java.io.BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {
            writer.write(html.toString());
        }

        System.out.println("完成 → " + Paths.get(outputPath).toAbsolutePath());
    }

    /**
     * 生成空图的HTML文件
     */
    private void generateEmptyGraphHtml(List<String> selectedPeople, String outputPath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n<head>\n");
        html.append("<meta charset=\"utf-8\">\n");
        html.append("<title>三国人物关系网络图</title>\n");
        html.append("<style type=\"text/css\">\n");
        html.append("body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }\n");
        html.append(".message { text-align: center; color: #666; }\n");
        html.append("</style>\n");
        html.append("</head>\n<body>\n");
        html.append("<div class=\"message\">\n");
        html.append("<h2>人物关系网络图</h2>\n");
        html.append("<p>没有找到指定人物之间的关联</p>\n");
        html.append("<p>指定人物: ").append(String.join(", ", selectedPeople)).append("</p>\n");
        html.append("<p>请检查数据文件中是否包含这些人名，以及它们之间是否有关系数据。</p>\n");
        html.append("</div>\n");
        html.append("</body>\n</html>");

        try (java.io.BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {
            writer.write(html.toString());
        }

        System.out.println("完成 → " + Paths.get(outputPath).toAbsolutePath());
    }
}