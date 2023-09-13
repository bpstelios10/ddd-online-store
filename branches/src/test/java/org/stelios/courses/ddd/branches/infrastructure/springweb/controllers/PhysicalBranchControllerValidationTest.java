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
import org.stelios.courses.ddd.branches.repositories.PhysicalBranchEntity;

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
public class PhysicalBranchControllerValidationTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BranchService branchService;

    @Test
    void getAll_succeeds_whenBranchExists() throws Exception {
        List<PhysicalBranchEntity> expectedResponseBranches = List.of(new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "address1"));
        when(branchService.getAllPhysicalBranches()).thenReturn(expectedResponseBranches);

        ResultActions responseActions = mvc.perform(get("/branches/physical").accept(MediaType.APPLICATION_JSON));

        responseActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponseBranches)));
    }

    @Test
    void create_succeeds_whenValidRequest() throws Exception {
        PhysicalBranchEntity branch = new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "address1");
        doNothing().when(branchService).save(branch);

        ResultActions responseActions = mvc.perform(post("/branches/physical")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(branch))
        );

        responseActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @ParameterizedTest
    @MethodSource("invalidBranchValues")
    void create_fails_whenCityFieldOfBranchInvalid(PhysicalBranchEntity branch) throws Exception {
        doNothing().when(branchService).save(branch);

        ResultActions responseActions = mvc.perform(post("/branches/physical")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(branch))
        );

        responseActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    private static Stream<Arguments> invalidBranchValues() {
        return Stream.of(
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", "city1", " ")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "\t")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "\n")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", "city1", null)),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", "", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", " ", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", "\t", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", "\n", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "owner1", null, "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", " ", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "\t", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", "\n", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "description1", null, "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "", "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", " ", "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "\t", "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", "\n", "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("id1", null, "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("", "description1", "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity(" ", "description1", "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("\t", "description1", "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity("\n", "description1", "owner1", "city1", "address1")),
                Arguments.of(new PhysicalBranchEntity(null, "description1", "owner1", "city1", "address1"))
        );
    }
}
