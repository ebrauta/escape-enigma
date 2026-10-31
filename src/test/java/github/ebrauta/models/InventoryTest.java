package github.ebrauta.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory mochila;
    private Item chave;
    private Item bilhete;

    @BeforeEach
    void setUp() {
        mochila = new Inventory();
        chave = new Item("chave-gaveta", "Chave de Latão", "Uma chave pequena.");
        bilhete = new Item("bilhete", "Bilhete Cifrado", "Contém um texto codificado.");
    }

    @Test
    @DisplayName("Deve adicionar o item na mochila")
    void DeveAdicionarItem() {
        mochila.addItem(chave);
        Optional<Item> itemEncontrado = mochila.getItemById("chave-gaveta");
        assertTrue(itemEncontrado.isPresent());
        assertEquals(chave, itemEncontrado.get());
    }

    @Test
    @DisplayName("Deve buscar item por ID de forma insensível a maiúsculas/minúsculas")
    void deveBuscarItemCaseInsensitive() {
        mochila.addItem(chave);

        Optional<Item> itemEncontrado = mochila.getItemById("CHAVE-GAVETA");
        assertTrue(itemEncontrado.isPresent());
        assertEquals("Chave de Latão", itemEncontrado.get().name());
    }

    @Test
    @DisplayName("Deve retornar Optional.empty ao buscar um ID inexistente")
    void deveRetornarVazioParaItemInexistente() {
        mochila.addItem(chave);

        Optional<Item> itemEncontrado = mochila.getItemById("item-fantasma");
        assertTrue(itemEncontrado.isEmpty());
    }

    @Test
    @DisplayName("Deve remover item do inventário corretamente")
    void deveRemoverItem() {
        mochila.addItem(chave);
        mochila.addItem(bilhete);

        mochila.removeItem(chave);

        assertTrue(mochila.getItemById("chave-gaveta").isEmpty());
        assertTrue(mochila.getItemById("bilhete").isPresent());
    }
}