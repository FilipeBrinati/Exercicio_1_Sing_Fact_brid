package singleton;

import java.util.HashMap;
import java.util.Map;

/*
 * Singleton criado para o armazenamento de peças
 * AdicionarPeca: permite adicionar pecas ao estoque
 * RemoverPeca: So permite a remocao caso a peca tenha em estoque
 * 	   e tambem tenha um numero disponivel para a remocao
 * */
public class Estoque {
 private static Estoque instancia;
 Map<String, Integer> pecasDisponiveis; 

 private Estoque() {
     pecasDisponiveis = new HashMap<>();
 }

 public static Estoque getInstancia() {
     if (instancia == null) {
         instancia = new Estoque();
     }
     return instancia;
 }

 public void adicionarPeca(String nome, int quantidade) {
     pecasDisponiveis.put(nome, pecasDisponiveis.getOrDefault(nome, 0) + quantidade);
 }

 public boolean verificarDisponibilidade(String nome, int quantidade) {
     return pecasDisponiveis.getOrDefault(nome, 0) >= quantidade;
 }

 // Método para remover peças do estoque
 public void removerPeca(String nome, int quantidade) throws Exception {
     if (!pecasDisponiveis.containsKey(nome) || pecasDisponiveis.get(nome) < quantidade) {
         throw new Exception("Erro: Peça '" + nome + "' não disponível ou quantidade insuficiente no estoque.");
     }
     pecasDisponiveis.put(nome, pecasDisponiveis.get(nome) - quantidade);
 }
 public void reset() {
     pecasDisponiveis.clear();
 }
 public void mostrarEstado() {
	    System.out.println(pecasDisponiveis); // Supondo que `pecas` seja um mapa que armazena as peças e suas quantidades
	}

}

