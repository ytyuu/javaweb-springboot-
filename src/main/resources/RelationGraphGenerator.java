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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return (src.equals(edge.src) && dst.equals(edge.dst)) || (src.equals(edge.dst) && dst.equals(edge.src));
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dst);
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

        // 合并相同节点对之间的边（避免重复边）
        List<Edge> mergedEdges = mergeDuplicateEdges(filteredEdges);

        if (mergedEdges.isEmpty()) {
            System.out.println("\n没有找到指定人物之间的关联。");
            System.out.println("请检查CSV文件中是否包含这些人名，以及它们之间是否有关系数据。");
            // 即使没有边，也要生成一个基本的HTML文件
            generateEmptyGraphHtml(selectedPeople, outputPath);
            return;
        }

        System.out.println("\n合并后的数据 (相同节点对的边已合并):");
        for (Edge edge : mergedEdges) {
            System.out.println(edge.src + "," + edge.dst + "," + edge.weight);
        }

        // 生成HTML文件
        generateGraphHtml(selectedPeople, mergedEdges, outputPath);
    }

    /**
     * 合并相同节点对之间的边
     */
    private static List<Edge> mergeDuplicateEdges(List<Edge> edges) {
        // 使用Map来存储(src,dst)对的权重总和
        Map<String, Edge> edgeMap = new HashMap<>();

        for (Edge edge : edges) {
            // 创建一个键，考虑边的方向性（无向图）
            String key = edge.src.compareTo(edge.dst) < 0 ? edge.src + "->" + edge.dst : edge.dst + "->" + edge.src;

            if (edgeMap.containsKey(key)) {
                // 如果已存在相同的(src,dst)对，累加权重
                Edge existingEdge = edgeMap.get(key);
                existingEdge.weight += edge.weight;
            } else {
                // 否则，添加新的边
                edgeMap.put(key, new Edge(edge.src, edge.dst, edge.weight));
            }
        }

        return new ArrayList<>(edgeMap.values());
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