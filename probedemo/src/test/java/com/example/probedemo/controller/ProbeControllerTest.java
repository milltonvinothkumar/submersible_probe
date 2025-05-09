package com.example.probedemo.controller;

import com.example.probedemo.model.Direction;
import com.example.probedemo.model.Position;
import com.example.probedemo.service.ProbeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProbeController.class)
public class ProbeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProbeService probeService;

    @Test
    public void testInitProbe() throws Exception {

        Position position = new Position(3, 5, Direction.UP, new HashSet<>());
        when(probeService.initProbe(3, 5, "UP", new HashSet<>())).thenReturn(position);

        String inputJson = "{\n" +
                "    \"start\": { \"x\": 2, \"y\": 3, \"direction\": \"UP\" },\n" +
                "    \"obstacles\": [\"4,3\", \"2,5\"]\n" +
                "}";
        mockMvc.perform(post("/api/v1/probe/init").contentType(MediaType.APPLICATION_JSON).content(inputJson))
                .andExpect(status().isOk());
        verify(probeService, times(1)).initProbe(2,3,"UP", Set.of("4,3", "2,5"));
    }

    @Test
    public void testExecuteCommands() throws Exception {

        List<String> commands = List.of("MOVE_FORWARD");
        Position position = new Position(3, 5, Direction.UP, new HashSet<>());
        when(probeService.executeCommands(commands)).thenReturn(position);

        String inputJson = "[\"MOVE_FORWARD\"]";
        mockMvc.perform(post("/api/v1/probe/commands").contentType(MediaType.APPLICATION_JSON).content(inputJson))
                .andExpect(status().isOk());
        verify(probeService, times(1)).executeCommands(commands);
    }

    @Test
    public void testStatus() throws Exception {

        Set<String> visitedCordinates = Set.of("3,5", "3,6");
        Position position = new Position(3, 5, Direction.UP, new HashSet<>());
        when(probeService.getPosition()).thenReturn(position);
        when(probeService.getVisitedCordinates()).thenReturn(visitedCordinates);

        mockMvc.perform(get("/api/v1/probe/status"))
                .andExpect(status().isOk());
        verify(probeService, times(1)).getPosition();
        verify(probeService, times(1)).getVisitedCordinates();
    }
}
