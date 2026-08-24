package negocio;

import dados.Cidade;
import dados.Cliente;
import dados.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaPassagem {

    private final List<Cidade> listaDeCidades = new ArrayList<>();
    private final List<Cliente> listaDeCliente = new ArrayList<>();

    public void cadastrarCidade(Cidade cidade) {
        listaDeCidades.add(cidade);
    }

    public boolean existeCpf(String cpf) {
        return buscarCliente(cpf) != null;
    }

    public void cadastrarCliente(Cliente cliente) {
        listaDeCliente.add(cliente);
    }

    public  void reservarIda(Cliente cliente, Reserva reserva) {
        cliente.reservarIda(reserva);
    }

    public void reservarVolta(Cliente  cliente, Reserva ida, Reserva volta) {
        cliente.reservarVolta(ida, volta);
    }

    public  Reserva[] mostrarReservas(String cpfCliente) {
        Cliente cliente = buscarCliente(cpfCliente);
        if (cliente == null) {
            return new Reserva[0];
        }
        return cliente.getReservas();
    }

    public Cliente[] mostrarClientes() {
        return listaDeCliente.toArray(new Cliente[0]);
    }

    public Cidade[] mostrarCidades() {
        return listaDeCidades.toArray(new Cidade[0]);
    }

    private Cliente buscarCliente(String cpfCliente) {
        for (Cliente cliente : listaDeCliente) {
            if (cliente.getCpf().equals(cpfCliente)) {
                return cliente;
            }
        }
        return null;
    }
}
