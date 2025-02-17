package br.com.tinnova.gerenciador_veiculos.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tinnova.gerenciador_veiculos.entities.Veiculo;
import br.com.tinnova.gerenciador_veiculos.repositories.VeiculoRepository;

@Service
public class VeiculoService {
    
    @Autowired
    private final VeiculoRepository veiculoRepository;
    private static final Set<String> MARCAS_VALIDAS = Set.of("Ford", "Chevrolet", "Honda", "Volkswagen", "Toyota", "Fiat", "Renault", "Nissan", "Jeep", "Hyundai");

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public List<Veiculo> buscarPorFiltros(String marca, Integer ano, String cor) {
        return veiculoRepository.findByMarcaAndAnoAndCor(marca, ano, cor);
    }

    public Optional<Veiculo> buscarPorId(Long id) {
        return veiculoRepository.findById(id);
    }

    public Veiculo salvar(Veiculo veiculo) {
        if (!MARCAS_VALIDAS.contains(veiculo.getMarca())) {
            throw new IllegalArgumentException("Marca inválida: " + veiculo.getMarca());
        }
        veiculo.setCreated(LocalDateTime.now());
        veiculo.setUpdated(LocalDateTime.now());
        return veiculoRepository.save(veiculo);
    }

    public Veiculo atualizar(Long id, Veiculo veiculoAtualizado) {
        return veiculoRepository.findById(id).map(veiculo -> {
            veiculo.setVeiculo(veiculoAtualizado.getVeiculo());
            veiculo.setMarca(veiculoAtualizado.getMarca());
            veiculo.setAno(veiculoAtualizado.getAno());
            veiculo.setDescricao(veiculoAtualizado.getDescricao());
            
            veiculo.setVendido(veiculoAtualizado.getVendido());
            veiculo.setUpdated(LocalDateTime.now());
            return veiculoRepository.save(veiculo);
        }).orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
    }

    public Veiculo atualizarParcial(Long id, Map<String, Object> updates) {
        return veiculoRepository.findById(id).map(veiculo -> {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "veiculo" -> veiculo.setVeiculo((String) value);
                    case "marca" -> veiculo.setMarca((String) value);
                    case "ano" -> veiculo.setAno((Integer) value);
                    case "descricao" -> veiculo.setDescricao((String) value);
                    case "cor" -> veiculo.setCor((String) value);
                    case "vendido" -> veiculo.setVendido((Boolean) value);
                }
            });
            veiculo.setUpdated(LocalDateTime.now());
            return veiculoRepository.save(veiculo);
        }).orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
    }

    public long contarNaoVendidos() {
        return veiculoRepository.countByVendidoFalse();
    }

    public Map<String, Long> contarPorDecada() {
        return veiculoRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        v -> (v.getAno() / 10 * 10) + "s",
                        Collectors.counting()
                ));
    }

    public Map<String, Long> contarPorFabricante() {
        return veiculoRepository.findAll().stream()
                .collect(Collectors.groupingBy(Veiculo::getMarca, Collectors.counting()));
    }

    public List<Veiculo> buscarUltimaSemana() {
        LocalDateTime umaSemanaAtras = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        return veiculoRepository.findByCreatedAfter(umaSemanaAtras);
    }

    public void deletar(Long id) {
        veiculoRepository.deleteById(id);
    }
}
