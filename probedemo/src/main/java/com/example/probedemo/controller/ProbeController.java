package com.example.probedemo.controller;

import com.example.probedemo.model.Position;
import com.example.probedemo.service.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/probe")
public class ProbeController {
    @Autowired
    private ProbeService probeService;

    @PostMapping("/init")
    public ResponseEntity<Position> initProbe(@RequestBody Map<String, Object> body) {
        Map<String, Object> start = (Map<String, Object>) body.get("start");
        int x = (int) start.get("x");
        int y = (int) start.get("y");
        String direction = (String) start.get("direction");
        Set<String> obstacles = new HashSet<>((List<String>) body.get("obstacles"));

        Position position = probeService.initProbe(x, y, direction, obstacles);
        return ResponseEntity.ok(position);
    }

    @PostMapping("/commands")
    public ResponseEntity<Position> sendCommands(@RequestBody List<String> commands) {

        Position position = probeService.executeCommands(commands);
        return ResponseEntity.ok(position);
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        return Map.of("position", probeService.getPosition(),
                    "visitedCoordinates", probeService.getVisitedCordinates());
    }
}
