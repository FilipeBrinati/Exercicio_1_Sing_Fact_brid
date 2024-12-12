package factorymethod;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import singleton.Estoque;
import bridge.Motor2Cilindros;
import bridge.Motor4Cilindros;

@TestMethodOrder(OrderAnnotation.class)
class FabricaMotoTest {
    private Estoque estoque;

    @BeforeEach
    void setUp() {
        estoque = Estoque.getInstancia();
        estoque.adicionarPeca("Motor 2 Cilindros", 5);
        estoque.adicionarPeca("Motor 4 Cilindros", 5);
    }
    
    @AfterEach
    void tearDown() {
    	estoque.reset();
    	estoque = null;
    }

    @Test
    @Order(1)
    void testExecutarMotoEsportivaComSucesso() throws Exception {
        FabricaMotoEsportiva fabricaEsportiva = new FabricaMotoEsportiva(new Motor2Cilindros(), estoque);
        String resultado = fabricaEsportiva.executar();
        assertEquals("Moto Esportiva montada com Motor 2 Cilindros", resultado);
        assertFalse(estoque.verificarDisponibilidade("Motor 2 Cilindros", 5));
    }

    @Test
    @Order(2)
    void testExecutarMotoCruiserComSucesso() throws Exception {
        FabricaMotoCruiser fabricaCruiser = new FabricaMotoCruiser(new Motor4Cilindros(), estoque);
        String resultado = fabricaCruiser.executar();
        assertEquals("Moto Cruiser montada com Motor 4 Cilindros", resultado);
        assertFalse(estoque.verificarDisponibilidade("Motor 4 Cilindros", 5));
    }


    @Test
    @Order(3)
    void testCancelarMotoEsportiva() throws Exception {
        FabricaMotoEsportiva fabricaEsportiva = new FabricaMotoEsportiva(new Motor2Cilindros(), estoque);
        fabricaEsportiva.executar();
        String resultadoCancelamento = fabricaEsportiva.cancelar();
        assertEquals("Fabricação da Moto Esportiva cancelada. Peça 'Motor 2 Cilindros' retornada ao estoque.", resultadoCancelamento);
        assertTrue(estoque.verificarDisponibilidade("Motor 2 Cilindros", 5));
    }

    @Test
    @Order(4)
    void testCancelarMotoCruiser() throws Exception {
        FabricaMotoCruiser fabricaCruiser = new FabricaMotoCruiser(new Motor4Cilindros(), estoque);
        fabricaCruiser.executar();
        String resultadoCancelamento = fabricaCruiser.cancelar();
        assertEquals("Fabricação da Moto Cruiser cancelada. Peça 'Motor 4 Cilindros' retornada ao estoque.", resultadoCancelamento);
        assertTrue(estoque.verificarDisponibilidade("Motor 4 Cilindros", 5));
    }

    @Test
    @Order(5)
    void testCancelarSemFabricacaoMotoEsportiva() {
        FabricaMotoEsportiva fabricaEsportiva = new FabricaMotoEsportiva(new Motor2Cilindros(), estoque);
        String resultadoCancelamento = fabricaEsportiva.cancelar();
        assertEquals("Erro: Não há fabricação de Moto Esportiva para cancelar.", resultadoCancelamento);
    }

    @Test
    @Order(6)
    void testCancelarSemFabricacaoMotoCruiser() {
        FabricaMotoCruiser fabricaCruiser = new FabricaMotoCruiser(new Motor4Cilindros(), estoque);
        String resultadoCancelamento = fabricaCruiser.cancelar();
        assertEquals("Erro: Não há fabricação de Moto Cruiser para cancelar.", resultadoCancelamento);
    }

    @Test
    @Order(7)
    void testExecutarMotoEsportivaSemEstoque() {
    	estoque.reset();
        FabricaMotoEsportiva fabricaEsportiva = new FabricaMotoEsportiva(new Motor2Cilindros(), estoque);
        estoque.adicionarPeca("Motor 2 Cilindros", 0);
        Exception exception = assertThrows(Exception.class, fabricaEsportiva::executar);
        assertEquals("Erro: Peça 'Motor 2 Cilindros' não disponível no estoque.", exception.getMessage());
    }

    @Test
    @Order(8)
    void testExecutarMotoCruiserSemEstoque() {
    	estoque.reset();
        FabricaMotoCruiser fabricaCruiser = new FabricaMotoCruiser(new Motor4Cilindros(), estoque);
        estoque.adicionarPeca("Motor 4 Cilindros", 0);
        Exception exception = assertThrows(Exception.class, fabricaCruiser::executar);
        assertEquals("Erro: Peça 'Motor 4 Cilindros' não disponível no estoque.", exception.getMessage());
    }
}
