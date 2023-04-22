package fr.amurotakahashi.cefimtestcda2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.annotation.Validated;

@SpringBootTest
@AutoConfigureMockMvc
@Validated
@Transactional
public class ControllerTests {
    private final static ResultMatcher RESULT_MATCHER_OK = MockMvcResultMatchers.status().isOk();
    private final static ResultMatcher RESULT_MATCHER_CREATED = MockMvcResultMatchers.status().isCreated();
    private final static ResultMatcher RESULT_MATCHER_NOT_FOUND = MockMvcResultMatchers.status().isNotFound();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String request(HttpMethod httpMethod, String url, Object contentValue, HttpStatus httpStatus) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.request(httpMethod, url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentValue));
        ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(httpStatus.value());

        return mockMvc.perform(requestBuilder)
                .andExpect(resultMatcher)
                .andReturn().getResponse().getContentAsString();
    }

    protected String request(HttpMethod httpMethod, String url, HttpStatus httpStatus) throws Exception {
        return request(httpMethod, url, null, httpStatus);
    }

    protected String request(HttpMethod httpMethod, String url, Object contentValue) throws Exception {
        return request(httpMethod, url, contentValue, HttpStatus.OK);
    }

    protected String request(HttpMethod httpMethod, String url) throws Exception {
        return request(httpMethod, url, null);
    }

    protected String get(String url) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);

        return mockMvc.perform(requestBuilder)
                .andExpect(RESULT_MATCHER_OK)
                .andReturn().getResponse().getContentAsString();
    }

    protected String post(String url, Object value) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(value));

        return mockMvc.perform(requestBuilder)
                .andExpect(RESULT_MATCHER_CREATED)
                .andReturn().getResponse().getContentAsString();
    }

    protected String patch(String url, Object value) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(value));

        return mockMvc.perform(requestBuilder)
                .andExpect(RESULT_MATCHER_OK)
                .andReturn().getResponse().getContentAsString();
    }

    protected String put(String url, Object value) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(value));

        return mockMvc.perform(requestBuilder)
                .andExpect(RESULT_MATCHER_OK)
                .andReturn().getResponse().getContentAsString();
    }

    protected String delete(String url) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(url);

        return mockMvc.perform(requestBuilder)
                .andExpect(RESULT_MATCHER_OK)
                .andReturn().getResponse().getContentAsString();
    }

}
