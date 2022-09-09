import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("C:\\Users\\marco\\Downloads\\campeonato-brasileiro-full.csv");

        Path resultado = Path.of("resultado.txt");
        Files.deleteIfExists(resultado);
        Files.createFile(resultado);

        try {
            //1
            Long quantidadeJogos = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .count();
            System.out.println("Quantas partidas aconteceram no total?");
            System.out.println(quantidadeJogos);
            Files.writeString(resultado, "1) " + quantidadeJogos + "\n", StandardOpenOption.APPEND);

            //2
            Integer quantidadeGols = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(tabela -> mapToTabela(tabela))
                    .mapToInt(tabela -> tabela.getTotalPlacar())
                    .sum();
            System.out.println("Quantos gols tiveram no total?");
            System.out.println(quantidadeGols);
            Files.writeString(resultado, "2) " + quantidadeGols + "\n", StandardOpenOption.APPEND);

            //3
            Long partidasPeriodo = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(tabela -> mapToTabela(tabela))
                    .filter(table -> table.getData().getYear() >= 2010 && table.getData().getYear() <= 2015)
                    .count();
            System.out.println("Quantas partidas ocorreram ente 2010 e 2015?");
            System.out.println(partidasPeriodo);
            Files.writeString(resultado, "3) " + partidasPeriodo + "\n", StandardOpenOption.APPEND);

            //4
            Optional <Tabela> maxGols = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(tabela -> mapToTabela(tabela))
                    .max((x, y) -> x.getTotalPlacar().compareTo(y.getTotalPlacar()));
            System.out.println(maxGols.get().getTotalPlacar());
            Files.writeString(resultado, "4) " + maxGols.get().getTotalPlacar() + " " + maxGols.get().getData() + " " + maxGols.get().getMandante() + "x" + maxGols.get().getVisitante() + "\n", StandardOpenOption.APPEND);

            //5
            HashMap<String, Times> timesMap = new HashMap<>();
            Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .forEach(t -> mapToTime(t, timesMap));

            Optional<Times> maisGols = timesMap
                    .values()
                    .stream()
                    .max((t1, t2) -> t1.getGols().compareTo(t2.getGols()));
            System.out.println("5) " + maisGols.get().getNome() + " " + maisGols.get().getGols());
            Files.writeString(resultado, "5) " + maisGols.get().getNome() + " " + maisGols.get().getGols() + "\n", StandardOpenOption.APPEND);

            //6
            Optional<Times> maxGolsSofridos = timesMap
                    .values()
                    .stream()
                    .max((t1, t2) -> t1.getGolsTomados().compareTo(t2.getGolsTomados()));
            System.out.println("6) " + maxGolsSofridos.get().getNome() + " " + maxGolsSofridos.get().getGols());
            Files.writeString(resultado, "6) " + maxGolsSofridos.get().getNome() + " " + maxGolsSofridos.get().getGols() + "\n", StandardOpenOption.APPEND);

            //7
            Optional<Times> maxVitorias = timesMap
                    .values()
                    .stream()
                    .max((t1, t2) -> t1.getVitorias().compareTo(t2.getVitorias()));
            System.out.println("7) " + maxVitorias.get().getNome() + " " + maxVitorias.get().getVitorias());
            Files.writeString(resultado, "7) " + maxVitorias.get().getNome() + " " + maxVitorias.get().getVitorias() + "\n", StandardOpenOption.APPEND);

            //8
            String vitoriaMandante = String.valueOf(Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(table -> mapToTabela(table))
                    .filter((mandante) -> mandante.getMandante().equals(mandante.getVencedor()))
                    .count());
            System.out.println(vitoriaMandante);
            Files.writeString(resultado, "8) " + vitoriaMandante + "\n", StandardOpenOption.APPEND);

            //9
            String vitoriaVisitante = String.valueOf(Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(table -> mapToTabela(table))
                    .filter((visitante) -> visitante.getVisitante().equals(visitante.getVencedor()))
                    .count());
            System.out.println(vitoriaVisitante);
            Files.writeString(resultado, "9) " + vitoriaVisitante + "\n", StandardOpenOption.APPEND);

            //10
            Optional<Times> maxDerrotas = timesMap
                    .values()
                    .stream()
                    .max((t1, t2) -> t1.getDerrotas().compareTo(t2.getDerrotas()));
            System.out.println("10) " + maxDerrotas.get().getNome() + " " + maxDerrotas.get().getDerrotas());
            Files.writeString(resultado, "10) " + maxDerrotas.get().getNome() + " " + maxDerrotas.get().getDerrotas() + "\n", StandardOpenOption.APPEND);

            //11
            Map <Integer, Long> jogosPorAno = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(table -> mapToTabela(table))
                    .collect(Collectors.groupingBy(partidas -> partidas.getData().getYear(),Collectors.counting()));
            Files.writeString(resultado, "11) " + jogosPorAno + "\n", StandardOpenOption.APPEND);

            //12
            timesMap
                    .values()
                    .stream()
                    .forEach(t -> {
                        try {
                            System.out.println(t.getNome() + " " + t.getJogos());
                            Files.writeString(resultado, "12) " + t.getNome() + " " + t.getJogos() + "\n", StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            System.out.println(jogosPorAno);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
    private static void mapToTime(String t, HashMap<String, Times> timesMap) {
        String [] data = t.split(",");

        String mandante = data[5];
        String vencedor = data[11];
        if (timesMap.get(mandante) != null) {
            Times times = timesMap.get(mandante);
            times.setGols(times.getGols() + Integer.parseInt(data[13]));
            times.setGolsTomados(times.getGolsTomados() + Integer.parseInt(data[14]));
            if (mandante.equals(vencedor)) {
                times.setVitorias(times.getVitorias() + 1);
            } else if (!vencedor.equals("-")){
                times.setDerrotas(times.getDerrotas() + 1);
            }
            times.setJogos(times.getJogos() + 1);
        } else {
            Times times = new Times();
            times.setNome(mandante);
            times.setGols(Integer.parseInt(data[13]));
            times.setGolsTomados(Integer.parseInt(data[14]));
            if (mandante.equals(vencedor)) {
                times.setVitorias(1);
            } else if (!vencedor.equals("-")){
                times.setDerrotas(times.getDerrotas() + 1);
            }
            times.setJogos(1);
            timesMap.put(mandante, times);
        }

        String visitante = data[6];
        if (timesMap.get(visitante) != null) {
            Times times = timesMap.get(visitante);
            times.setGols(times.getGols() + Integer.parseInt(data[14]));
            times.setGolsTomados(times.getGolsTomados() + Integer.parseInt(data[13]));
            if (visitante.equals(vencedor)) {
                times.setVitorias(times.getVitorias() + 1);
            } else if (!vencedor.equals("-")){
                times.setDerrotas(times.getDerrotas() + 1);
            }
            times.setJogos(times.getJogos() + 1);
        } else {
            Times times = new Times();
            times.setNome(visitante);
            times.setGols(Integer.parseInt(data[14]));
            times.setGolsTomados(Integer.parseInt(data[13]));
            if (visitante.equals(vencedor)) {
                times.setVitorias(1);
            } else if (!vencedor.equals("-")){
                times.setDerrotas(times.getDerrotas() + 1);
            }
            times.setJogos(1);
            timesMap.put(visitante, times);
        }

    }

    private static Tabela mapToTabela (String t){

        String [] coluna = t.split(",");

        Tabela tabela = new Tabela();
        tabela.setRodada(Integer.parseInt(coluna [1]));
        tabela.setData(LocalDate.parse(coluna[2]));
        tabela.setMandante(coluna[5]);
        tabela.setVisitante(coluna[6]);
        tabela.setVencedor(coluna[11]);
        tabela.setArena(coluna[12]);
        tabela.setMandantePlacar(Integer.parseInt(coluna[13]));
        tabela.setVisitantePlacar(Integer.parseInt(coluna[14]));
        tabela.setMandanteEstado(coluna[15]);
        tabela.setVisitanteEstado(coluna[16]);
        tabela.setVencedorEstado(coluna[17]);
        tabela.setTotalPlacar(tabela.getMandantePlacar()+ tabela.getVisitantePlacar());

        return tabela;
    }
}
