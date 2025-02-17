package br.com.tinnova.gerenciador_veiculos.repositories;
import br.com.tinnova.gerenciador_veiculos.entities.Veiculo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByMarcaAndAnoAndCor(String marca, Integer ano, String cor);
    long countByVendidoFalse();
    List<Veiculo> findAll();
    List<Veiculo> findByCreatedAfter(LocalDateTime date);
}
