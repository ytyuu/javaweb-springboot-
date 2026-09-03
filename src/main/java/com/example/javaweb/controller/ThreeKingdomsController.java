package com.example.javaweb.controller;

import com.example.javaweb.entity.CharacterAppearance;
import com.example.javaweb.entity.ForceAppearance;
import com.example.javaweb.entity.CharacterChapterCount;
import com.example.javaweb.entity.CharacterLifeLine;
import com.example.javaweb.service.CharacterAppearanceService;
import com.example.javaweb.service.ForceAppearanceService;
import com.example.javaweb.Service.CharacterChapterCountService;
import com.example.javaweb.Service.CharacterLifeLineService;
import com.example.javaweb.service.RelationGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ThreeKingdomsController {

    @Autowired
    private CharacterAppearanceService characterAppearanceService;

    @Autowired
    private ForceAppearanceService forceAppearanceService;

    @Autowired
    private RelationGraphService relationGraphService;

    @Autowired
    private CharacterChapterCountService characterChapterCountService;
    
    @Autowired
    private CharacterLifeLineService characterLifeLineService;

    // 跳转主页面
    @GetMapping("/threekingdoms")
    public String toIndex() {
        return "threekingdoms/index";
    }

    // 获取人物出场数据 - 根据选择的人物
    @GetMapping("/character/data")
    @ResponseBody
    public List<CharacterChapterCount> getCharacterData(@RequestParam(required = false) List<String> names) {
        if (names == null || names.isEmpty()) {
            // 如果没有指定人物，则返回所有数据
            return characterChapterCountService.getAllCharacterChapterCounts();
        } else {
            // 根据指定人物查询数据
            return characterChapterCountService.getAllCharacterChapterCounts()
                    .stream()
                    .filter(item -> names.contains(item.getCharacterName()))
                    .collect(Collectors.toList());
        }
    }

    // 获取人物生命线数据 - 根据选择的人物
    @GetMapping("/lifeline/data")
    @ResponseBody
    public List<CharacterLifeLine> getCharacterLifeLineData(@RequestParam(required = false) List<String> names) {
        if (names == null || names.isEmpty()) {
            // 如果没有指定人物，则返回所有数据
            return characterLifeLineService.getAllCharacterLifeLines();
        } else {
            // 根据指定人物查询数据
            return characterLifeLineService.getCharacterLifeLinesByNames(names);
        }
    }

    // 获取势力出场数据
    @GetMapping("/force/data")
    @ResponseBody
    public List<ForceAppearance> getForceData() {
        return forceAppearanceService.getAllForceAppearance();
    }
    
    // 处理人物关系图生成请求
    @PostMapping("/submit")
    @ResponseBody
    public Map<String, Object> submit(@RequestParam("selectedButtons") String selectedButtons) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 解析选择的人物
            String[] peopleArray = selectedButtons.split(",");
            List<String> selectedPeople = Arrays.asList(peopleArray);
            
            // 使用数据库生成关系图
            String outputPath = "三國共現網絡.html";
            relationGraphService.generateRelationGraph(selectedPeople, outputPath);
            
            response.put("success", true);
            response.put("message", "网络图生成成功！");
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "生成失败: " + e.getMessage());
            e.printStackTrace(); // 打印堆栈跟踪以进行调试
        }
        
        return response;
    }
    
    // 提供生成的HTML文件
    @GetMapping("/三國共現網絡.html")
    public ResponseEntity<Resource> getGeneratedHtml() {
        File htmlFile = new File("三國共現網絡.html");
        if (!htmlFile.exists()) {
            return ResponseEntity.notFound().build();
        }
        
        Resource resource = new FileSystemResource(htmlFile);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"三國共現網絡.html\"")
                .body(resource);
    }
}