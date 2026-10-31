package github.ebrauta.models;

import github.ebrauta.models.objects.Door;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {
    private Door porta;
    private Item chaveMestra;
    private Item chaveErrada;

    @BeforeEach
    void setUp() {
        porta = new Door("porta-saida", "Porta Principal", "Uma porta pesada de ferro.", "chave-saida");
        chaveMestra = new Item("chave-saida", "Chave Mestra", "Abre a porta principal.");
        chaveErrada = new Item("chave-gaveta", "Chave Pequena", "Chave de gaveta.");
    }

    @Test
    @DisplayName("Não deve abrir a porta com a chave incorreta")
    void naoDeveAbrirComChaveErrada() {
        String mensagem = porta.useItem(chaveErrada);

        assertTrue(porta.objectIsClosed());
        assertFalse(porta.isEscaped());
        assertTrue(mensagem.contains("Isso não parece funcionar"));
    }

    @Test
    @DisplayName("Deve abrir a porta e sinalizar vitória com a chave correta")
    void deveAbrirESinalizarVitoria() {
        String mensagem = porta.useItem(chaveMestra);

        assertFalse(porta.objectIsClosed());
        assertTrue(porta.isEscaped());
        assertTrue(mensagem.contains("VOCÊ ESCAPOU COM SUCESSO"));
    }
}