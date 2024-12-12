package factorymethod;

import singleton.Estoque;
import bridge.Motor2Cilindros;
import bridge.Peca;


class FabricaMotoEsportiva implements IFabricarMoto {
    private Peca peca;
    private Estoque estoque;
    private boolean fabricado = false;

    public FabricaMotoEsportiva(Peca peca, Estoque estoque) {
        this.peca = new Motor2Cilindros();
        this.estoque = estoque;
    }

    @Override
    public String executar() throws Exception {
        String nomePeca = peca.getDescricao();
        if (estoque.verificarDisponibilidade(nomePeca, 1)) {
            estoque.removerPeca(nomePeca, 1);
            fabricado = true;
            return "Moto Esportiva montada com " + nomePeca;
        } else {
            throw new Exception("Erro: Peça '" + nomePeca + "' não disponível no estoque.");
        }
    }

    @Override
    public String cancelar() {
        if (!fabricado) {
            return "Erro: Não há fabricação de Moto Esportiva para cancelar.";
        }
        String nomePeca = peca.getDescricao();
        estoque.adicionarPeca(nomePeca, 1);
        fabricado = false;
        return "Fabricação da Moto Esportiva cancelada. Peça '" + nomePeca + "' retornada ao estoque.";
    }
}