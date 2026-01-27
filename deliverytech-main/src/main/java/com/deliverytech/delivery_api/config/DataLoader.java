package com.deliverytech.delivery_api.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner initData(
        ClienteRepository clienteRepository,
        RestauranteRepository restauranteRepository,
        ProdutoRepository produtoRepository,
        PedidoRepository pedidoRepository,
        ItemPedidoRepository itemPedidoRepository
    ){
        return args ->{
            System.out.println("Iniciando carregamento de dados...");

            Cliente cliente1 = new Cliente();
            cliente1.setNome("Raiel Landre");
            cliente1.setEmail("raiel@gmail.com");
            cliente1.setTelefone("11987654321");
            cliente1.setEndereco("Rua das Flores, 123, São Paulo, SP");
            cliente1.setAtivo(true);

            Cliente cliente2 = new Cliente();
            cliente2.setNome("Giovanni de Carvalho");
            cliente2.setEmail("giovanni@gmail.com");
            cliente2.setTelefone("11912345678");
            cliente2.setEndereco("Avenida Brasil, 456, Rio de Janeiro, RJ");
            cliente2.setAtivo(true);

            List<Cliente> clientes = new ArrayList<>();
            clientes.add(cliente1);
            clientes.add(cliente2);
            clienteRepository.saveAll(clientes);

            Restaurante r1 = new Restaurante();
            r1.setNome("Pizza");
            r1.setCategoria("Pizzaria");
            r1.setEndereco("Rua das Pizzas, 100, São Paulo, SP");
            r1.setTelefone("11999998888");
            r1.setAvaliacao(new BigDecimal("4.5"));
            r1.setTaxaEntrega(new BigDecimal("5.00"));
            r1.setAtivo(true);

            Restaurante r2 = new Restaurante();
            r2.setNome("Burger House");
            r2.setCategoria("Hamburgueria");
            r2.setEndereco("Rua Augusta, 500 - São Paulo/SP");
            r2.setTelefone("11999998888");
            r2.setAvaliacao(new BigDecimal("4.0"));
            r2.setTaxaEntrega(new BigDecimal("8.00"));
            r2.setAtivo(true);

            List<Restaurante> restaurantes = new ArrayList<>();
            restaurantes.add(r1);
            restaurantes.add(r2);
            restauranteRepository.saveAll(restaurantes);

            Produto p1 = new Produto();
            p1.setNome("Pizza de Calabresa");
            p1.setDescricao("Deliciosa pizza de calabresa com borda recheada");
            p1.setPreco(new BigDecimal("35.00"));
            p1.setCategoria("Pizza");
            p1.setDisponivel(true);
            p1.setRestaurante(r1);

            Produto p2 = new Produto();
            p2.setNome("Hambúrguer Clássico");
            p2.setDescricao("Hambúrguer com queijo, alface, tomate e molho especial");
            p2.setPreco(new BigDecimal("25.00"));
            p2.setCategoria("Hambúrguer");
            p2.setDisponivel(true);
            p2.setRestaurante(r2);

            List<Produto> produtos = new ArrayList<>();
            produtos.add(p1);
            produtos.add(p2);
            produtoRepository.saveAll(produtos);

            Pedido pedido1 = new Pedido();
            pedido1.setCliente(cliente1);
            pedido1.setRestaurante(r1);
            pedido1.setStatus(StatusPedidos.PENDENTE);
            pedido1.setEnderecoEntrega(cliente1.getEndereco());
            pedido1.setValorTotal(BigDecimal.ZERO);

            Pedido pedido2 = new Pedido();
            pedido2.setCliente(cliente2);
            pedido2.setRestaurante(r2);
            pedido2.setStatus(StatusPedidos.PENDENTE);
            pedido2.setEnderecoEntrega(cliente2.getEndereco());
            pedido2.setValorTotal(BigDecimal.ZERO);

            pedidoRepository.save(pedido1);
            pedidoRepository.save(pedido2);

            ItemPedido item1 = new ItemPedido();
            item1.setProduto(p2);
            item1.setPedido(pedido2);
            item1.setQuantidade(2);
            item1.setPrecoUnitario(p2.getPreco());
            item1.setSubtotal(p2.getPreco().multiply(BigDecimal.valueOf(item1.getQuantidade())));
            
            ItemPedido item2 = new ItemPedido();
            item2.setProduto(p1);
            item2.setPedido(pedido1);
            item2.setPrecoUnitario(p1.getPreco());
            item2.setQuantidade(5);
            item2.setSubtotal(p1.getPreco().multiply(BigDecimal.valueOf(item2.getQuantidade())));
            
            itemPedidoRepository.save(item1);
            itemPedidoRepository.save(item2);

            System.out.println("Dados carregados com sucesso!");

        };
    }
    
}
