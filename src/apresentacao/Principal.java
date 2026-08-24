package apresentacao;

import negocio.ReservaPassagem;
import dados.Cidade;
import dados.Cliente;
import dados.Reserva;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Principal {

    private final ReservaPassagem reservaPassagem = new ReservaPassagem();
    private final Scanner sc = new Scanner(System.in);
    private int contadorReservas = 0;
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        new Principal().executar();
    }

    public void executar() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opcao: ");
            switch (opcao) {
                case 1:
                    cadastrarCidade();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    fazerReserva();
                    break;
                case 4:
                    mostrarReservas();
                    break;
                case 5:
                    mostrarClientes();
                    break;
                case 6:
                    mostrarCidades();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1 - Cadastrar cidade");
        System.out.println("2 - Cadastrar cliente");
        System.out.println("3 - Fazer reserva");
        System.out.println("4 - Mostrar reservas de um cliente");
        System.out.println("5 - Mostrar clientes");
        System.out.println("6 - Mostrar cidades");
        System.out.println("0 - Sair");
    }

    public void cadastrarCidade() {
        System.out.print("Nome da cidade: ");
        String nome = sc.nextLine();
        System.out.print("Estado: ");
        String estado = sc.nextLine();

        reservaPassagem.cadastrarCidade(new Cidade(nome, estado));
        System.out.println("Cidade cadastrada com sucesso!");
    }

    public void cadastrarCliente() {
        System.out.print("CPF (somente numeros, 11 digitos): ");
        String cpf = sc.nextLine();

        if (!cpf.matches("\\d{11}")) {
            System.out.println("CPF invalido! Deve conter 11 digitos numericos.");
            return;
        }

        if (reservaPassagem.existeCpf(cpf)) {
            System.out.println("Ja existe um cliente cadastrado com esse CPF.");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        if (nome.isBlank()) {
            System.out.println("Nome nao pode ser vazio.");
            return;
        }

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Endereco: ");
        String endereco = sc.nextLine();

        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);

        reservaPassagem.cadastrarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void fazerReserva() {
        System.out.print("CPF do cliente: ");
        String cpf = sc.nextLine();

        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente nao encontrado!");
            return;
        }

        Cidade origem = escolherCidade("Escolha a cidade de ORIGEM:");
        if (origem == null) return;

        Cidade destino = escolherCidade("Escolha a cidade de DESTINO:");
        if (destino == null) return;

        if (origem.equals(destino)) {
            System.out.println("Origem e destino nao podem ser a mesma cidade.");
            return;
        }

        Reserva ida = criarReserva(origem, destino, false, null);
        if (ida == null) return;

        System.out.print("Deseja reservar ida e volta? (S/N): ");
        String resposta = sc.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            Reserva volta = criarReserva(destino, origem, true, null);
            if (volta == null) return;
            reservaPassagem.reservarVolta(cliente, ida, volta);
        } else {
            reservaPassagem.reservarIda(cliente, ida);
        }

        System.out.println("Reserva realizada com sucesso!");
    }

    private Reserva criarReserva(Cidade origem, Cidade destino, boolean idaEvolta, Reserva volta) {
        System.out.print("Data do voo (dd/mm/aaaa): ");
        String data = sc.nextLine();

        if (!dataValida(data)) {
            System.out.println("Data invalida ou no passado. Use o formato dd/mm/aaaa.");
            return null;
        }

        System.out.print("Hora do voo (hh:mm): ");
        String hora = sc.nextLine();

        System.out.print("Preco: ");
        String precoTexto = sc.nextLine();
        float preco;
        try {
            preco = Float.parseFloat(precoTexto);
        } catch (NumberFormatException e) {
            System.out.println("Preco invalido!");
            return null;
        }
        if (preco <= 0) {
            System.out.println("Preco deve ser maior que zero.");
            return null;
        }

        System.out.print("Classe (Economica/Executiva): ");
        String classe = sc.nextLine();

        contadorReservas++;
        return new Reserva(contadorReservas, data, hora, preco, classe, idaEvolta, origem, destino, volta);
    }

    private boolean dataValida(String data) {
        try {
            LocalDate dataVoo = LocalDate.parse(data, FORMATO_DATA);
            return !dataVoo.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void mostrarReservas() {
        System.out.print("CPF do cliente: ");
        String cpf = sc.nextLine();

        Reserva[] reservas = reservaPassagem.mostrarReservas(cpf);
        if (reservas.length == 0) {
            System.out.println("Nenhuma reserva encontrada.");
            return;
        }

        System.out.println("\n===== RESERVAS =====");
        for (int i = 0; i < reservas.length; i++) {
            System.out.println(reservas[i]);
            System.out.println("--------------------------------");
        }
    }

    public void mostrarClientes() {
        Cliente[] clientes = reservaPassagem.mostrarClientes();
        if (clientes.length == 0) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n===== CLIENTES CADASTRADOS =====");
        for (int i = 0; i < clientes.length; i++) {
            System.out.println(clientes[i]);
            System.out.println("--------------------------------");
        }
    }

    public void mostrarCidades() {
        Cidade[] cidades = reservaPassagem.mostrarCidades();
        if (cidades.length == 0) {
            System.out.println("Nenhuma cidade cadastrada.");
            return;
        }

        System.out.println("\n===== CIDADES CADASTRADAS =====");
        for (int i = 0; i < cidades.length; i++) {
            System.out.println((i + 1) + " - " + cidades[i]);
        }
        System.out.println("--------------------------------");
    }

    private Cidade escolherCidade(String mensagem) {
        Cidade[] cidades = reservaPassagem.mostrarCidades();
        if (cidades.length == 0) {
            System.out.println("Nenhuma cidade cadastrada ainda!");
            return null;
        }

        System.out.println(mensagem);
        for (int i = 0; i < cidades.length; i++) {
            System.out.println((i + 1) + " - " + cidades[i].getNome() + "/" + cidades[i].getEstado());
        }

        int indice = lerInt("Numero da cidade: ") - 1;
        if (indice < 0 || indice >= cidades.length) {
            System.out.println("Opcao invalida!");
            return null;
        }
        return cidades[indice];
    }

    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : reservaPassagem.mostrarClientes()) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    private int lerInt(String mensagem) {
        System.out.print(mensagem);
        while (!sc.hasNextInt()) {
            System.out.println("Digite um numero valido!");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }
}