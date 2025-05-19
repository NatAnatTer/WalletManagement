package ru.project.WalletManagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.project.controller.WalletController;
import ru.project.dto.WalletDto;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testcontainers.shaded.org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private WalletController walletController;

    @Test
    public void getBalanceTest() throws Exception{
        WalletDto walletDto = new WalletDto("35846da0-349f-4a38-b6aa-359dd3d82e5e", 111L);

//        given(walletController.getBalance("35846da0-349f-4a38-b6aa-359dd3d82e5e")).willReturn(walletDto);
//        mvc.perform(get("/v1/wallets/{walletUuid}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(request.getName()));


      //  mvc.perform(get(VERSION + ARRIVAL + "all")
//                        .with(user("blaze").password("Q1w2e3r4"))
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].city", is(arrival.getCity())));

        testEndpoint("/v1/wallets/35846da0-349f-4a38-b6aa-359dd3d82e5e", "{\n" +
                "    \"walletId\": \"35846da0-349f-4a38-b6aa-359dd3d82e5e\",\n" +
                "    \"amount\": 111\n" +
                "}");
    }
    @Test
    public void createTransactionTest() throws Exception{

    }

    private void testEndpoint(String path, String response) throws Exception {
        mvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andReturn();
    }
}
