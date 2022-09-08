import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args)  {

        Path path = Paths.get("C:\\Users\\marco\\Downloads\\campeonato-brasileiro-full.csv");

        try {
            //1
            long quantidadeJogos = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .count();
            System.out.println("Quantas partidas aconteceram no total?");
            System.out.println(quantidadeJogos);

            //2
            Integer quantidadeGols = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(tabela -> mapToTabela(tabela))
                    .mapToInt(tabela -> tabela.getMandantePlacar() + tabela.getVisitantePlacar())
                    .sum();
            System.out.println("Quantos gols tiveram no total?");
            System.out.println(quantidadeGols);

            //3
            Long partidasPeriodo = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(tabela -> mapToTabela(tabela))
                    .filter(table -> table.getData().getYear() >= 2010 && table.getData().getYear() <= 2015)
                    .count();
            System.out.println("Quantas partidas ocorreram ente 2010 e 2015?");
            System.out.println(partidasPeriodo);

            //4
            long maxGols = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(tabela -> mapToTabela(tabela))
                    .mapToInt(gols -> gols.getMandantePlacar() + gols.getVisitantePlacar())
                    .max().getAsInt();
            System.out.println(maxGols);

            //7
            Optional<Tabela> maxVitorias = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(table -> mapToTabela(table))
                    .max(Comparator.comparing(Tabela::getVencedor));
            System.out.println(maxVitorias.get().getVencedor());

            //8
            String vitoriaMandante = String.valueOf(Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(table -> mapToTabela(table))
                    .filter((mandante) -> mandante.getMandante().equals(mandante.getVencedor()))
                    .count());
            System.out.println(vitoriaMandante);

            //9
            String vitoriaVisitante = String.valueOf(Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(table -> mapToTabela(table))
                    .filter((visitante) -> visitante.getVisitante().equals(visitante.getVencedor()))
                    .count());
            System.out.println(vitoriaVisitante);

            //11
            Map <Integer, Long> jogosPorAno = Files.lines(path.toAbsolutePath())
                    .skip(1)
                    .map(table -> mapToTabela(table))
                    .collect(Collectors.groupingBy(partidas -> partidas.getData().getYear(),Collectors.counting()));

            System.out.println(jogosPorAno);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
