package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.enums.StatusPedidos;
import com.deliverytech.delivery_api.model.Cliente;
import com.deliverytech.delivery_api.model.ItemPedido;
import com.deliverytech.delivery_api.model.Pedido;
import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.ItemPedidoRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
            RestauranteRepository restauranteRepository, ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido criarPedido(Long clienteId, Long restauranteId){
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(()-> new IllegalArgumentException("Cliente não encontrado."));

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
            .orElseThrow(()-> new IllegalArgumentException("Restaurante não encontrado."));

        Pedido entradaPedido = new Pedido();
        entradaPedido.setCliente(cliente);
        entradaPedido.setRestaurante(restaurante);
        /* entradaPedido.setNumeroPedido(); */
        entradaPedido.setStatus(StatusPedidos.PENDENTE);
        entradaPedido.setDataPedido(LocalDateTime.now());
        entradaPedido.setValorTotal(BigDecimal.ZERO);
        return pedidoRepository.save(entradaPedido);
    }

    public Pedido atualizarStatus(Long pedidoId, StatusPedidos status){
        Pedido pedido = pedidoRepository.findById(pedidoId)
        .orElseThrow(()-> new IllegalArgumentException("Pedido não encontrado."));

        pedido.setStatus(status);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPorCliente(Long clienteId){
        return pedidoRepository.findByClienteId(clienteId);
    }

    public ItemPedido adicionarItem(Long pedidoId, Long produtoId, Integer quantidade){
        Pedido pedido = pedidoRepository.findById(pedidoId)
        .orElseThrow(()-> new IllegalArgumentException("Pedido não encontrado."));

        Produto produto = produtoRepository.findById(produtoId)
        .orElseThrow(()-> new IllegalArgumentException("Produto não encontrado."));

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());

        BigDecimal subtotal = produto.getPreco()
        .multiply(BigDecimal.valueOf(quantidade));
        item.setSubtotal(subtotal);
        itemPedidoRepository.save(item);

        pedido.setValorTotal(pedido.getValorTotal().add(subtotal));
        pedidoRepository.save(pedido);

        return item;
    }
}
