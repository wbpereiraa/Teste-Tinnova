package testes_diversos;

public class SomaMultiplos {
    public static void main(String[] args) {
        int numero = 10;
        int soma = 0;
        for (int i = 0; i < numero; i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                soma += i;
            }
        }
        System.out.println("Soma dos múltiplos de 3 ou 5: " + soma);
    }
    
}
