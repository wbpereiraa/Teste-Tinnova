package br.com.tinnova.gerenciador_veiculos.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tinnova.gerenciador_veiculos.entities.Veiculo;
import br.com.tinnova.gerenciador_veiculos.exceptions.VeiculoNaoEncontradoException;
import br.com.tinnova.gerenciador_veiculos.services.VeiculoService;

@RestController
@RequestMapping("/veiculos")
class VeiculoController {
    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Veiculo> listarTodos() {
        List<Veiculo> veiculos = veiculoService.listarTodos();
        if (veiculos.isEmpty()) {
            throw new VeiculoNaoEncontradoException("Nenhum veículo encontrado");
        }
        return veiculos;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Veiculo buscarPorId(@PathVariable Long id) {
        return veiculoService.buscarPorId(id).orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado"));
    }

    @GetMapping(params = {"marca", "ano", "cor"})
    @ResponseStatus(HttpStatus.OK)
    public List<Veiculo> buscarPorFiltros(@RequestParam String marca, @RequestParam Integer ano, @RequestParam String cor) {
        List<Veiculo> veiculos = veiculoService.buscarPorFiltros(marca, ano, cor);
        if (marca == null || cor == null || ano == null) {
            throw new IllegalArgumentException("Todos os filtros devem ser fornecidos");
        } else if (veiculos.isEmpty()) {
            throw new VeiculoNaoEncontradoException("Nenhum veículo encontrado");
        }
        return veiculos;
    }

    @GetMapping("/nao-vendidos")
    @ResponseStatus(HttpStatus.OK)
    public long contarNaoVendidos() {
        try {
            return veiculoService.contarNaoVendidos();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar veículos não vendidos", e);
        }
    }

    @GetMapping("/distribuicao-decada")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Long> contarPorDecada() {
        try {
            return veiculoService.contarPorDecada();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar distribuição por década", e);
        }
    }

    @GetMapping("/distribuicao-fabricante")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Long> contarPorFabricante() {
        try {
            return veiculoService.contarPorFabricante();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar distribuição por fabricante", e);
        }
    }

    @GetMapping("/ultima-semana")
    @ResponseStatus(HttpStatus.OK)
    public List<Veiculo> buscarUltimaSemana() {
        try {
            return veiculoService.buscarUltimaSemana();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar veículos da última semana", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo salvar(@RequestBody Veiculo veiculo) {
        if (veiculo == null || veiculo.getMarca() == null || veiculo.getAno() == null || veiculo.getCor() == null || veiculo.getDescricao() == null || veiculo.getVendido() == null) {
            throw new IllegalArgumentException("Dados do veículo inválidos");
        }
        return veiculoService.salvar(veiculo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Veiculo atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        if (!veiculoService.buscarPorId(id).isPresent()) {
            throw new VeiculoNaoEncontradoException("Veículo com ID " + id + " não encontrado");
        }
        return veiculoService.atualizar(id, veiculo);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Veiculo atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        if (!veiculoService.buscarPorId(id).isPresent()) {
            throw new VeiculoNaoEncontradoException("Veículo com ID " + id + " não encontrado");
        }
        return veiculoService.atualizarParcial(id, updates);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        if (!veiculoService.buscarPorId(id).isPresent()) {
            throw new VeiculoNaoEncontradoException("Veículo com ID " + id + " não encontrado");
        }
        veiculoService.deletar(id);
    }
}

