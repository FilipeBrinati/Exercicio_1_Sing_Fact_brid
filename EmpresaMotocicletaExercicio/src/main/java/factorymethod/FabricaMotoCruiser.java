package factorymethod;

import singleton.Estoque;
import bridge.Peca;
import bridge.Motor4Cilindros;


class FabricaMotoCruiser implements IFabricarMoto {
 private Peca peca;
 private Estoque estoque;
 private boolean fabricado = false;

 public FabricaMotoCruiser(Peca peca, Estoque estoque) {
     this.peca = new Motor4Cilindros();
     this.estoque = estoque;
 }

 @Override
 public String executar() throws Exception {
     String nomePeca = peca.getDescricao();
     if (estoque.verificarDisponibilidade(nomePeca, 1)) {
         estoque.removerPeca(nomePeca, 1);
         fabricado = true;
         return "Moto Cruiser montada com " + nomePeca;
     } else {
         throw new Exception("Erro: Peça '" + nomePeca + "' não disponível no estoque.");
     }
 }

 @Override
 public String cancelar() {
     if (!fabricado) {
         return "Erro: Não há fabricação de Moto Cruiser para cancelar.";
     }
     String nomePeca = peca.getDescricao();
     estoque.adicionarPeca(nomePeca, 1);
     fabricado = false;
     return "Fabricação da Moto Cruiser cancelada. Peça '" + nomePeca + "' retornada ao estoque.";
 }
}
