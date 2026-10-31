package github.ebrauta.models;

import github.ebrauta.models.objects.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalTest {

    private Terminal terminal;
    private Item chaveCartao;
    private Item chaveSaida;
    private final Inventory mochila = new Inventory();

    @BeforeEach
    void setUp() {
        chaveSaida = new Item("chave-saida", "Chave Mestra", "Abre a porta principal.");
        terminal = new Terminal("terminal-01", "Terminal de Segurança", "Um terminal com tela verde.", "cartao-acesso", "HELLO WORLD", chaveSaida);
        chaveCartao = new Item("cartao-acesso", "Cartão Magnético", "Um cartão de acesso.");
    }

    @Test
    @DisplayName("Deve iniciar trancado por padrão")
    void deveIniciarTrancado() {
        assertTrue(terminal.objectIsClosed());
    }

    @Test
    @DisplayName("Deve negar acesso ao digitar senha incorreta")
    void deveNegarAcessoComSenhaIncorreta() {
        String resposta = terminal.tryPassword("SENHA ERRADA", mochila);

        assertTrue(terminal.objectIsClosed());
        assertTrue(resposta.contains("ACESSO NEGADO"));
    }

    @Test
    @DisplayName("Deve conceder acesso e destrancar ao digitar a senha correta (case insensitive)")
    void deveConcederAcessoComSenhaCorreta() {
        // Testando com letras minúsculas e espaços extras para garantir resiliência
        String resposta = terminal.tryPassword("  hello world  ", mochila);

        assertFalse(terminal.objectIsClosed());
        assertTrue(resposta.contains("ACESSO CONCEDIDO"));
    }
    @Test
    @DisplayName("Deve inserir chave na mochila ao digitar a senha correta (case insensitive)")
    void deveConcederChaveComSenhaCorreta() {
        // Testando com letras minúsculas e espaços extras para garantir resiliência
        terminal.tryPassword("  hello world  ", mochila);

        assertFalse(terminal.objectIsClosed());
        assertTrue(mochila.getItems().contains(chaveSaida));
    }

    @Test
    @DisplayName("Não deve permitir aceitar itens físicos via useItem")
    void naoDeveAceitarItemFisico() {
        String resposta = terminal.useItem(chaveCartao);

        assertTrue(terminal.objectIsClosed());
        assertTrue(resposta.contains("não tem fechadura física"));
    }

    @Test
    @DisplayName("Deve informar se o terminal já estiver desbloqueado")
    void deveAvisarSeJaEstiverDesbloqueado() {
        terminal.tryPassword("HELLO WORLD", mochila);

        String respostaSegundaTentativa = terminal.tryPassword("HELLO WORLD", mochila);
        assertTrue(respostaSegundaTentativa.contains("já está desbloqueado"));
    }
}