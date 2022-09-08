import java.time.LocalDate;

public class Tabela {

    private Integer rodada;
    private LocalDate data;
    private String mandante;
    private String visitante;

    private String vencedor;
    private String arena;
    private Integer mandantePlacar;
    private Integer visitantePlacar;
    private String mandanteEstado;
    private String visitanteEstado;
    private String vencedorEstado;

    public Tabela() {
    }

    public Integer getRodada() {
        return rodada;
    }

    public void setRodada(Integer rodada) {
        this.rodada = rodada;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMandante() {
        return mandante;
    }

    public void setMandante(String mandante) {
        this.mandante = mandante;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
    }

    public Integer getMandantePlacar() {
        return mandantePlacar;
    }

    public void setMandantePlacar(Integer mandantePlacar) {
        this.mandantePlacar = mandantePlacar;
    }

    public Integer getVisitantePlacar() {
        return visitantePlacar;
    }

    public void setVisitantePlacar(Integer visitantePlacar) {
        this.visitantePlacar = visitantePlacar;
    }

    public String getMandanteEstado() {
        return mandanteEstado;
    }

    public void setMandanteEstado(String mandanteEstado) {
        this.mandanteEstado = mandanteEstado;
    }

    public String getVisitanteEstado() {
        return visitanteEstado;
    }

    public void setVisitanteEstado(String visitanteEstado) {
        this.visitanteEstado = visitanteEstado;
    }

    public String getVencedorEstado() {
        return vencedorEstado;
    }

    public void setVencedorEstado(String vencedorEstado) {
        this.vencedorEstado = vencedorEstado;
    }
}
