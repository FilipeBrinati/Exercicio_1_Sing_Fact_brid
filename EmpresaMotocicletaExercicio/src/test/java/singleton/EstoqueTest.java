package singleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstoqueTest {

    private Estoque estoque;

    @BeforeEach
    void setUp() {
        estoque = Estoque.getInstancia();
        estoque.adicionarPeca("reset", -estoque.pecasDisponiveis.getOrDefault("reset", 0));
    }

    @Test
    void testAdicionarPeca() {
        estoque.adicionarPeca("Motor 2 Cilindros", 5);
        assertTrue(estoque.verificarDisponibilidade("Motor 2 Cilindros", 5), "Peça deveria estar disponível após adicionar.");
    }

    @Test
    void testVerificarDisponibilidadeTrue() {
        estoque.adicionarPeca("Motor 4 Cilindros", 3);
        assertTrue(estoque.verificarDisponibilidade("Motor 4 Cilindros", 2), "Deveria haver peças suficientes no estoque.");
    }

    @Test
    void testVerificarDisponibilidadeFalse() {
        estoque.adicionarPeca("Freio", 1);
        assertFalse(estoque.verificarDisponibilidade("Freio", 2), "Não deveria haver peças suficientes no estoque.");
    }

    @Test
    void testRemoverPecaComSucesso() throws Exception {
        estoque.adicionarPeca("Guidon", 5);
        estoque.removerPeca("Guidon", 3);
        assertTrue(estoque.verificarDisponibilidade("Guidon", 2), "Quantidade restante deveria ser suficiente.");
        assertFalse(estoque.verificarDisponibilidade("Guidon", 3), "Quantidade restante não deveria ser suficiente.");
    }

    @Test
    void testRemoverPecaInsuficiente() {
        estoque.adicionarPeca("Pneu", 2);
        Exception exception = assertThrows(Exception.class, () -> 
            estoque.removerPeca("Pneu", 3)
        );
        assertEquals("Erro: Peça 'Pneu' não disponível ou quantidade insuficiente no estoque.", exception.getMessage());
    }

    @Test
    void testRemoverPecaNaoExistente() {
        Exception exception = assertThrows(Exception.class, () -> 
            estoque.removerPeca("Bateria", 1)
        );
        assertEquals("Erro: Peça 'Bateria' não disponível ou quantidade insuficiente no estoque.", exception.getMessage());
    }

    @Test
    void testSingletonBehavior() {
        Estoque outraInstancia = Estoque.getInstancia();
        assertSame(estoque, outraInstancia, "As instâncias do singleton deveriam ser as mesmas.");
    }
}