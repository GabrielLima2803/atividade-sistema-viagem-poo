package dados;

public class Reserva {

    private Integer numReservas;
    private String dataVoo;
    private String horaVoo;
    private float preco;
    private String classeVoo;
    private boolean idaEvolta;

    private Cidade origem;
    private Cidade destino;
    private Reserva volta;

    public Reserva(Integer numReservas, String dataVoo, String horaVoo, float preco, String classeVoo, boolean idaEvolta, Cidade origem, Cidade destino, Reserva volta) {
        this.numReservas = numReservas;
        this.dataVoo = dataVoo;
        this.horaVoo = horaVoo;
        this.preco = preco;
        this.classeVoo = classeVoo;
        this.idaEvolta = idaEvolta;
        this.origem = origem;
        this.destino = destino;
        this.volta = volta;
    }

    public Integer getNumReservas() {
        return numReservas;
    }

    public void setNumReservas(Integer numReservas) {
        this.numReservas = numReservas;
    }

    public String getDataVoo() {
        return dataVoo;
    }

    public void setDataVoo(String dataVoo) {
        this.dataVoo = dataVoo;
    }

    public String getHoraVoo() {
        return horaVoo;
    }

    public void setHoraVoo(String horaVoo) {
        this.horaVoo = horaVoo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getClasseVoo() {
        return classeVoo;
    }

    public void setClasseVoo(String classeVoo) {
        this.classeVoo = classeVoo;
    }

    public boolean isIdaEvolta() {
        return idaEvolta;
    }

    public void setIdaEvolta(boolean idaEvolta) {
        this.idaEvolta = idaEvolta;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public void setOrigem(Cidade origem) {
        this.origem = origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public void setDestino(Cidade destino) {
        this.destino = destino;
    }

    public Reserva getVolta() {
        return volta;
    }

    public void setVolta(Reserva volta) {
        this.volta = volta;
    }

    @Override
    public String toString() {
        String texto = "Reserva #" + numReservas +
                "\nTrecho: " + origem + " -> " + destino +
                "\nData/Hora: " + dataVoo + " as " + horaVoo +
                "\nClasse: " + classeVoo +
                "\nPreco: R$ " + String.format("%.2f", preco) +
                "\nIda e volta: " + (idaEvolta ? "Sim" : "Nao");

        if (volta != null) {
            texto += "\nVolta: " + volta.getOrigem() + " -> " + volta.getDestino() +
                    " em " + volta.getDataVoo() + " as " + volta.getHoraVoo();
        }

        return texto;
    }
}
