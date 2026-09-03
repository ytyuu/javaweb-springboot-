package com.example.javaweb.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 关系图生成器 - 用于生成人物关系网络图的HTML文件
 */
public class RelationGraphGenerator {

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
     * 边的内部类表示
     */
    public static class Edge {
        public String src;
        public String dst;
        public int weight;

        public Edge(String src, String dst, int weight) {
            this.src = src;
            this.dst = dst;
            this.weight = weight;
        }
    }

    /**
     * 生成关系图HTML
     * @param selectedPeople 选定的人物列表
     * @param csvFileName CSV文件名（在resources目录下）
     * @param outputPath 输出HTML文件路径
     */
    public static void generateRelationGraph(List<String> selectedPeople, String csvFileName, String outputPath) throws IOException {
        // 从resources目录读取CSV文件
        InputStream inputStream = RelationGraphGenerator.class.getClassLoader().getResourceAsStream(csvFileName);

        if (inputStream == null) {
            // 如果在resources中没找到，尝试从项目根目录读取
            File file = new File(csvFileName);
            if (!file.exists()) {
                throw new IOException("找不到CSV文件：" + csvFileName + "，请确保文件存在于src/main/resources目录或项目根目录下");
            }
            inputStream = Files.newInputStream(file.toPath());
        }

        // 读取CSV数据
        List<Edge> edges = readEdgesFromInputStream(inputStream);

        if (edges.isEmpty()) {
            throw new IOException("无法从CSV文件中读取数据：" + csvFileName);
        }

        System.out.println("原始数据前几行:");
        for (int i = 0; i < Math.min(5, edges.size()); i++) {
            Edge edge = edges.get(i);
            System.out.println(edge.src + "," + edge.dst + "," + edge.weight);
        }

        // 过滤数据，只保留指定人物的数据
        List<Edge> filteredEdges = edges.stream()
                .filter(edge -> selectedPeople.contains(edge.src) && selectedPeople.contains(edge.dst))
                .collect(Collectors.toList());

        System.out.println("\n过滤后的数据 (指定人物: " + String.join(", ", selectedPeople) + "):");
        for (Edge edge : filteredEdges) {
            System.out.println(edge.src + "," + edge.dst + "," + edge.weight);
        }

        if (filteredEdges.isEmpty()) {
            System.out.println("\n没有找到指定人物之间的关联。");
            System.out.println("请检查CSV文件中是否包含这些人名，以及它们之间是否有关系数据。");
            // 即使没有边，也要生成一个基本的HTML文件
            generateEmptyGraphHtml(selectedPeople, outputPath);
            return;
        }

        System.out.println("\n过滤后的数据 (未合并相同节点对的边):");
        for (Edge edge : filteredEdges) {
            System.out.println(edge.src + "," + edge.dst + "," + edge.weight);
        }

        // 生成HTML文件
        generateGraphHtml(selectedPeople, filteredEdges, outputPath);
    }

    /**
     * 从输入流读取边数据
     */
    private static List<Edge> readEdgesFromInputStream(InputStream inputStream) throws IOException {
        List<Edge> edges = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    try {
                        String src = parts[0].trim();
                        String dst = parts[1].trim();
                        int weight = Integer.parseInt(parts[2].trim());
                        edges.add(new Edge(src, dst, weight));
                    } catch (NumberFormatException e) {
                        System.err.println("跳过无效行: " + line);
                    }
                }
            }
        }

        return edges;
    }

    /**
     * 生成包含关系图的HTML文件
     */
    private static void generateGraphHtml(List<String> selectedPeople, List<Edge> edges, String outputPath) throws IOException {
        Set<String> nodes = new HashSet<>();
        for (Edge edge : edges) {
            nodes.add(edge.src);
            nodes.add(edge.dst);
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
            html.append("  {from: '").append(edge.src).append("', to: '").append(edge.dst)
               .append("', value: ").append(edge.weight).append(", label: '").append(edge.weight).append("'},\n");
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
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {
            writer.write(html.toString());
        }

        System.out.println("完成 → " + Paths.get(outputPath).toAbsolutePath());
    }

    /**
     * 生成空图的HTML文件
     */
    private static void generateEmptyGraphHtml(List<String> selectedPeople, String outputPath) throws IOException {
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

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {
            writer.write(html.toString());
        }

        System.out.println("完成 → " + Paths.get(outputPath).toAbsolutePath());
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("用法: java RelationGraphGenerator <人物列表(逗号分隔)> <CSV文件名> [输出文件路径]");
            System.exit(1);
        }

        try {
            String[] peopleArray = args[0].split(",");
            List<String> selectedPeople = Arrays.asList(peopleArray);
            String csvFileName = args[1];
            String outputPath = args.length > 2 ? args[2] : "三國共現網絡.html";

            generateRelationGraph(selectedPeople, csvFileName, outputPath);
        } catch (Exception e) {
            System.err.println("生成关系图时出错: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}