package testes_diversos;
public class Votos {
    public int totalEleitores = 1000;
    public int votosValidos = 800;
    public int votosBrancos = 150;
    public int votosNulos = 50;  

    public double percentualVotosValidos(int totalEleitores, int votosValidos) {
        return (votosValidos * 100) / totalEleitores;
    }
    public double percentualVotosBrancos(int totalEleitores, int votosBrancos) {
        return (votosBrancos * 100) / totalEleitores;
    }
    public double percentualVotosNulos(int totalEleitores, int votosNulos) {
        return (votosNulos * 100) / totalEleitores;
    }

    public static void main(String[] args) {
        Votos votos = new Votos();
        System.out.println("Percentual de votos válidos em relação ao total de eleitores: " + votos.percentualVotosValidos(votos.totalEleitores, votos.votosValidos) + "%");
        System.out.println("Percentual de votos brancos em relação ao total de eleitores: " + votos.percentualVotosBrancos(votos.totalEleitores, votos.votosBrancos) + "%");
        System.out.println("Percentual de votos nulos em relação ao total de eleitores: " + votos.percentualVotosNulos(votos.totalEleitores, votos.votosNulos) + "%");
    }
}