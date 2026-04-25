package com.example.AtlazDB;

import com.example.AtlazDB.enums.*;
import com.example.AtlazDB.model.*;
import com.example.AtlazDB.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FuelControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ModeloRepository modeloRepository;
    @Autowired private ViaturaRepository viaturaRepository;
    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private OrdemServicoRepository ordemServicoRepository;
    @Autowired private FuelRepository fuelRepository;

    private Long idUsuario;
    private Long idViatura;
    private Long idCidade;
    private Long idOs;

    @BeforeEach
    void setup() {
        // limpa na ordem certa por causa das FK
        fuelRepository.deleteAll();
        ordemServicoRepository.deleteAll();
        cidadeRepository.deleteAll();
        viaturaRepository.deleteAll();
        usuarioRepository.deleteAll();
        modeloRepository.deleteAll();

        User user = new User();
        user.setNome("Teste");
        user.setMatricula("000001");
        user.setEmail("teste@email.com");
        user.setSenhaHash("senha");
        user.setPerfil(Perfil.ADMIN);
        user.setUsuarioStatus(UsuarioStatus.ATIVO);
        idUsuario = usuarioRepository.save(user).getId();

        Model model = new Model();
        model.setNomeModelo("Prisma");
        model.setNomeMarca("Chevrolet");
        Model modelSalvo = modeloRepository.save(model);

        Vehicle vehicle = new Vehicle();
        vehicle.setPrefixo("US01");
        vehicle.setTipo(TipoViatura.UTILITARIO);
        vehicle.setViaturaStatus(ViaturaStatus.ATIVO);
        vehicle.setModel(modelSalvo);
        idViatura = viaturaRepository.save(vehicle).getId();

        City city = new City();
        city.setNome("São José dos Campos");
        city.setUf("SP");
        idCidade = cidadeRepository.save(city).getId();

        OrderService os = new OrderService();
        os.setTipoServico(TypeOccurrence.ADMINISTRATIVO);
        os.setLocalDestino("Taubaté");
        os.setRequisitante("Teste");
        os.setKmSaida(new BigDecimal("100"));
        os.setDataSaida(LocalDateTime.now());
        os.setUser(usuarioRepository.findById(idUsuario).get());
        os.setVehicle(viaturaRepository.findById(idViatura).get());
        idOs = ordemServicoRepository.save(os).getId();
    }

    @Test
    void deveCreateAbastecimento() throws Exception {
        mockMvc.perform(post("/abastecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "dataHora": "2026-04-02T17:36:17",
                            "litros": 1,
                            "valorTotal": 1,
                            "numeroNotaFiscal": "teste",
                            "idUsuario": %d,
                            "idViatura": %d,
                            "idCidade": %d,
                            "idOs": %d
                        }
                        """.formatted(idUsuario, idViatura, idCidade, idOs)))
                .andExpect(status().isOk());
    }
    @Test
    void deveBuscarAbastecimentosPorMes() throws Exception {
        mockMvc.perform(post("/abastecimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
            {
                "dataHora": "2026-04-02T17:36:17",
                "litros": 1,
                "valorTotal": 1,
                "numeroNotaFiscal": "teste",
                "idUsuario": %d,
                "idViatura": %d,
                "idCidade": %d,
                "idOs": %d
            }
            """.formatted(idUsuario, idViatura, idCidade, idOs)));

        mockMvc.perform(get("/abastecimentos/por-mes")
                        .param("mes", "4")
                        .param("ano", "2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void deveRetornarListaVaziaParaMesSemAbastecimento() throws Exception {
        mockMvc.perform(get("/abastecimentos/por-mes")
                        .param("mes", "1")
                        .param("ano", "2000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void deveListarTiposOcorrencia() throws Exception {
        mockMvc.perform(get("/ordens-servico/tipos-ocorrencia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("ADMINISTRATIVO"));
    }

    @Test
    void deveListarViaturas() throws Exception {
        mockMvc.perform(get("/viaturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].prefixo").value("US01"));
    }

    @Test
    void deveGerarCsvCompleto() throws Exception {
        mockMvc.perform(get("/ordens-servico/csv"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/csv; charset=UTF-8"))
                .andExpect(header().exists("Content-Disposition"));
    }

    @Test
    void deveGerarCsvFiltradoPorMes() throws Exception {
        mockMvc.perform(get("/ordens-servico/csv")
                        .param("mes", "4")
                        .param("ano", "2026"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/csv; charset=UTF-8"));
    }
}