package github.ebrauta.models;

import github.ebrauta.models.objects.Drawer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DrawerTest {
    private Item chaveCorreta;
    private Item chaveErrada;
    private Item bilhete;
    private Drawer gaveta;

    @BeforeEach
    void setUp() {
        chaveCorreta = new Item("chave-gaveta", "Chave de Latão", "Uma chave pequena.");
        chaveErrada = new Item("chave-falsa", "Chave Velha", "Não serve para nada.");
        bilhete = new Item("bilhete", "Bilhete Cifrado", "Contém um texto estranho.");

        gaveta = new Drawer("gaveta", "Gaveta da Mesa", "Gaveta de madeira", "chave-gaveta", bilhete);
    }
    @Test
    @DisplayName("Não deve abrir a gaveta com a chave incorreta")
    void naoDeveAbrirComChaveIncorreta() {
        String resultado = gaveta.useItem(chaveErrada);
        assertTrue(gaveta.objectIsClosed());
        assertTrue(resultado.contains("Isso não parece funcionar"));
        assertEquals(Optional.empty(), gaveta.takeItem());
    }

    @Test
    @DisplayName("Deve abrir a gaveta com a chave certa")
    void deveAbrirComChaveCerta() {
        gaveta.useItem(chaveCorreta);
        assertFalse(gaveta.objectIsClosed());
    }

    @Test
    @DisplayName("Deve coletar o item")
    void deveEntregarItemComSucesso() {
        gaveta.useItem(chaveCorreta);
        Optional<Item> itemColetado = gaveta.takeItem();
        assertTrue(itemColetado.isPresent());
        assertEquals(bilhete, itemColetado.get());
    }

    @Test
    @DisplayName("Não deve coletar o item mais de uma vez")
    void deveVirVazioSeTentarPegarItemMaisDeUmaVez() {
        gaveta.useItem(chaveCorreta);
        gaveta.takeItem();
        assertEquals(Optional.empty(), gaveta.takeItem());
    }
}
