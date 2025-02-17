package br.com.tinnova.gerenciador_veiculos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.tinnova.gerenciador_veiculos.entities.Veiculo;
import br.com.tinnova.gerenciador_veiculos.repositories.VeiculoRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GerenciadorVeiculosApplicationTests {

	@Autowired
    private MockMvc mockMvc;

	 @Autowired
    private VeiculoRepository veiculoRepository;
    
    private Veiculo veiculo;

	@BeforeEach
    void setUp() {
        veiculoRepository.deleteAll();
        veiculo = new Veiculo();
        veiculo.setVeiculo("Carro Teste");
        veiculo.setMarca("Honda");
        veiculo.setAno(2022);
        veiculo.setDescricao("Novo");
        veiculo.setCor("Azul");
        veiculo.setVendido(false);
        veiculo.setCreated(LocalDateTime.now());
        veiculo.setUpdated(LocalDateTime.now());
        veiculo = veiculoRepository.save(veiculo);
    }

    @Test
    void deveRetornarListaDeVeiculos() throws Exception {
        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

	@Test
    void deveBuscarVeiculoPorId() throws Exception {
        mockMvc.perform(get("/veiculos/" + veiculo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(veiculo.getId()));
    }

    @Test
    void deveFiltrarVeiculos() throws Exception {
        mockMvc.perform(get("/veiculos?marca=Toyota&ano=2020&cor=Preto"))
                .andExpect(status().isOk());
    }

    @Test
    void deveCriarNovoVeiculo() throws Exception {
        String json = "{\"veiculo\":\"Carro Teste\",\"marca\":\"Honda\",\"ano\":2022,\"descricao\":\"Novo\",\"cor\":\"Azul\",\"vendido\":false}";
        mockMvc.perform(post("/veiculos").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

	@Test
    void deveAtualizarVeiculo() throws Exception {
        String json = "{\"veiculo\":\"Carro Atualizado\",\"marca\":\"Ford\",\"ano\":2023,\"descricao\":\"Usado\",\"cor\":\"Vermelho\",\"vendido\":true}";
        mockMvc.perform(put("/veiculos/" + veiculo.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

	@Test
    void deveAtualizarParcialmenteVeiculo() throws Exception {
        String json = "{\"cor\":\"Branco\"}";
        mockMvc.perform(patch("/veiculos/" + veiculo.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deveDeletarVeiculo() throws Exception {
        mockMvc.perform(delete("/veiculos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deveContarVeiculosNaoVendidos() throws Exception {
        mockMvc.perform(get("/veiculos/nao-vendidos"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void deveContarVeiculosPorDecada() throws Exception {
        mockMvc.perform(get("/veiculos/distribuicao-decada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['2020s']", is(1)));
    }

    @Test
    void deveContarVeiculosPorFabricante() throws Exception {
        mockMvc.perform(get("/veiculos/distribuicao-fabricante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Honda", is(1)));
    }

    @Test
    void deveBuscarVeiculosUltimaSemana() throws Exception {
        mockMvc.perform(get("/veiculos/ultima-semana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
