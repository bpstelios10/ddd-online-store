package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.stelios.courses.ddd.branches.application.BranchService;
import org.stelios.courses.ddd.branches.repositories.BranchEntity;
import org.stelios.courses.ddd.branches.repositories.OnlineBranchEntity;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OnlineBranchControllerValidationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BranchService branchService;

    @Test
    void getAll_succeeds_whenBranchExists() throws Exception {
        List<OnlineBranchEntity> expectedResponseBranches = List.of(new OnlineBranchEntity("id1", "description1", "owner1", "city1"));
        when(branchService.getAllOnlineBranches()).thenReturn(expectedResponseBranches);

        ResultActions responseActions = mvc.perform(get("/branches/online").accept(MediaType.APPLICATION_JSON));

        responseActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponseBranches)));
    }

    @Test
    void create_succeeds_whenValidRequest() throws Exception {
        OnlineBranchEntity branch = new OnlineBranchEntity("id1", "description1", "owner1", "city1");
        doNothing().when(branchService).save(branch);

        ResultActions responseActions = mvc.perform(post("/branches/online")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(branch))
        );

        responseActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @ParameterizedTest
    @MethodSource("invalidBranchValues")
    void create_fails_whenCityFieldOfBranchInvalid(OnlineBranchEntity branch) throws Exception {
        doNothing().when(branchService).save(branch);

        ResultActions responseActions = mvc.perform(post("/branches/online")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(branch))
        );

        responseActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    private static Stream<Arguments> invalidBranchValues() {
        return Stream.of(
                Arguments.of(new OnlineBranchEntity("id1", "description1", "owner1", "")),
                Arguments.of(new OnlineBranchEntity("id1", "description1", "owner1", " ")),
                Arguments.of(new OnlineBranchEntity("id1", "description1", "owner1", "\t")),
                Arguments.of(new OnlineBranchEntity("id1", "description1", "owner1", "\n")),
                Arguments.of(new OnlineBranchEntity("id1", "description1", "owner1", null)),
                Arguments.of(new OnlineBranchEntity("id1", "description1", "", "city1")),
                Arguments.of(new OnlineBranchEntity("id1", "description1", " ", "city1")),
                Arguments.of(new OnlineBranchEntity("id1", "description1", "\t", "city1")),
                Arguments.of(new OnlineBranchEntity("id1", "description1", "\n", "city1")),
                Arguments.of(new OnlineBranchEntity("id1", "description1", null, "city1")),
                Arguments.of(new OnlineBranchEntity("id1", "", "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity("id1", " ", "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity("id1", "\t", "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity("id1", "\n", "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity("id1", null, "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity("", "description1", "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity(" ", "description1", "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity("\t", "description1", "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity("\n", "description1", "owner1", "city1")),
                Arguments.of(new OnlineBranchEntity(null, "description1", "owner1", "city1"))
        );
    }
}
