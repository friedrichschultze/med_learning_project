package med.xuxao.api.controller;

import lombok.SneakyThrows;
import med.xuxao.api.domain.consultas.AgendamentoConsultas;
import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import med.xuxao.api.domain.consultas.DadosDetalhamentoConsulta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendamentoConsultas agendamentoConsultas;

    @SneakyThrows
    @Test
    @DisplayName("Testa se a chamada volta 400 ao enviar uma chamada sem body")
    void agendarConsultaCaso1(){
        var results = mvc.perform(post("/consultas"))
                        .andReturn().getResponse();
        assertThat(results.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @SneakyThrows
    @Test
    @DisplayName("Testa se a chamada volta 200 ao enviar uma chamada com o body corretamente configurado")
    void agendarConsultaCaso2(){
        var proximaTercaAs14 = LocalDate.now()
                .plusWeeks(1)
                .with(DayOfWeek.TUESDAY)
                .atTime(14,00);

        when(agendamentoConsultas.agendar(any()))
        .thenReturn(new DadosDetalhamentoConsulta(null, 2L, 4L, proximaTercaAs14));

        var results = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJson.write(
                                new DadosAgendamentoConsulta(2L, 4L, proximaTercaAs14, null)
                        ).getJson()))
                        .andReturn().getResponse();
        assertThat(results.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(
                new DadosDetalhamentoConsulta(null, 2L, 4L, proximaTercaAs14)).getJson();

        assertThat(results.getContentAsString()).isEqualTo(jsonEsperado);
    }

}