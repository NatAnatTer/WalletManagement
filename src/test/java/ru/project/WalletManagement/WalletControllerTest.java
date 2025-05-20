package ru.project.WalletManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import ru.project.controller.WalletController;
import ru.project.dto.WalletDto;
import ru.project.dto.WalletOperationDto;
import ru.project.entity.OperationType;
import ru.project.exception.NotEnoughAmountException;
import ru.project.exception.ResourceNotFoundException;
import ru.project.exception.UUIDFormatException;
import ru.project.service.WalletService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WalletController walletController;

    @MockitoBean
    private WalletService walletService;

    public WalletControllerTest() {
    }

    @Test
    public void getBalanceTest() throws Exception {
        String uuid = "38205798-2b30-4720-aeb9-38a5de00a357";
        WalletDto responseDto = new WalletDto(uuid, 2071L);
        when(walletService.getWallet(uuid)).thenReturn(responseDto);
        mvc.perform(get("/api/v1/wallets/{walletUuid}", uuid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(uuid))
                .andExpect(jsonPath("$.amount").value(2071));
    }

    @Test
    public void createTransactionTest() throws Exception {

        WalletOperationDto request = new WalletOperationDto("38205798-2b30-4720-aeb9-38a5de00a357",
                OperationType.DEPOSIT, 1000L);
        String jsonRequest = objectMapper.writeValueAsString(request);
        mvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isCreated());
    }

    @Test
    public void walletNotFoundTest() throws Exception {
        String uuid = "invalid-uuid";
        ResourceNotFoundException exception = new ResourceNotFoundException(uuid);
        when(walletService.getWallet(uuid)).thenThrow(exception);

        mvc.perform(get("/api/v1/wallets/{uuid}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Кошелек не найден " + uuid));
    }

    @Test
    public void UUIDFormatExceptionTest() throws Exception {
        String uuid = "invalid-uuid";
        UUIDFormatException exception = new UUIDFormatException(uuid);
        when(walletService.getWallet(uuid)).thenThrow(exception);

        mvc.perform(get("/api/v1/wallets/{uuid}", uuid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Ошибка формата " + uuid));
    }

    @Test
    public void NotEnoughAmountExceptionTest() throws Exception {

        Long amount = 1000L;
        WalletOperationDto request = new WalletOperationDto("38205798-2b30-4720-aeb9-38a5de00a357",
                OperationType.WITHDRAW, amount);
        NotEnoughAmountException exception = new NotEnoughAmountException(amount.toString());
        String jsonRequest = objectMapper.writeValueAsString(request);

        when(walletService.changeBalance(request)).thenThrow(exception);

        mvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isPaymentRequired())
                .andExpect(jsonPath("$.message")
                        .value("Недостаточно средств для выполнения операции. Текущий баланс: " + amount));

    }

}
