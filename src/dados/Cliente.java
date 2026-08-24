package dados;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String cpf;
    private String nome;
    private String telefone;
    private String endereco;

    private final List<Reserva> reservas = new ArrayList<>();

    public void reservarIda(Reserva reserva){
        reserva.setIdaEvolta(false);
        reserva.setVolta(null);
        reservas.add(reserva);
    }

    public void reservarVolta(Reserva ida, Reserva volta){
        ida.setIdaEvolta(true);
        volta.setIdaEvolta(true);
        ida.setVolta(volta);
        reservas.add(ida);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Reserva[] getReservas() {
        return reservas.toArray(new Reserva[0]);
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nCPF: " + cpf +
                "\nTelefone: " + telefone +
                "\nEndereco: " + endereco +
                "\nReservas: " + reservas.size();
    }
}
