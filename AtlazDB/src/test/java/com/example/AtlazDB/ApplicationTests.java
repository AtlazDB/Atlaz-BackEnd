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
    @Autowired private UserRepository userRepository;
    @Autowired private ModelRepository modelRepository;
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private CityRepository cityRepository;
    @Autowired private OrderServiceRepository orderServiceRepository;
    @Autowired private FuelRepository fuelRepository;

    private Long idUsuario;
    private Long idViatura;
    private Long idCidade;
    private Long idOs;

    @BeforeEach
    void setup() {
        // limpa na ordem certa por causa das FK
        fuelRepository.deleteAll();
        orderServiceRepository.deleteAll();
        cityRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
        modelRepository.deleteAll();

        User user = new User();
        user.setName("Teste");
        user.setRegistration("000001");
        user.setEmail("teste@email.com");
        user.setPasswordHash("senha");
        user.setProfile(Perfil.ADMIN);
        user.setUserStatus(UserStatus.ATIVO);
        idUsuario = userRepository.save(user).getId();

        Model model = new Model();
        model.setNameModel("Prisma");
        model.setNameBrand("Chevrolet");
        Model modelSalvo = modelRepository.save(model);

        Vehicle vehicle = new Vehicle();
        vehicle.setPrefix("US01");
        vehicle.setType(TipoViatura.UTILITARIO);
        vehicle.setVehicleStatus(VehicleStatus.ATIVO);
        vehicle.setModel(modelSalvo);
        idViatura = vehicleRepository.save(vehicle).getId();

        City city = new City();
        city.setName("São José dos Campos");
        city.setUf("SP");
        idCidade = cityRepository.save(city).getId();

        OrderService os = new OrderService();
        os.setTypeService(TypeOccurrence.ADMINISTRATIVO);
        os.setLocalDestiny("Taubaté");
        os.setRequisition("Teste");
        os.setLeaveKm(new BigDecimal("100"));
        os.setLeaveDate(LocalDateTime.now());
        os.setUser(userRepository.findById(idUsuario).get());
        os.setVehicle(vehicleRepository.findById(idViatura).get());
        idOs = orderServiceRepository.save(os).getId();
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
                .andExpect(jsonPath("$[0].prefix").value("US01"));
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