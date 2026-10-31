package github.ebrauta.models;

import github.ebrauta.models.objects.Drawer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room sala;
    private Drawer gaveta;
    private Item bilhete;

    @BeforeEach
    void setUp() {
        sala = new Room("Escritório", "Um escritório escuro e empoeirado.");
        bilhete = new Item("bilhete", "Bilhete", "Texto secreto.");
        gaveta = new Drawer("gaveta", "Gaveta da Mesa", "Gaveta de madeira antiga.", "chave-gaveta", bilhete);
    }

    @Test
    @DisplayName("Deve adicionar e buscar objeto na sala pelo ID com sucesso")
    void deveAdicionarEBuscarObjeto() {
        sala.addObject(gaveta);

        Optional<InteractiveObject> objetoEncontrado = sala.getObject("gaveta");
        assertTrue(objetoEncontrado.isPresent());
        assertEquals("Gaveta da Mesa", objetoEncontrado.get().getName());
    }

    @Test
    @DisplayName("Deve buscar objeto com ID em maiúsculas sem falhar")
    void deveBuscarObjetoCaseInsensitive() {
        sala.addObject(gaveta);

        Optional<InteractiveObject> objetoEncontrado = sala.getObject("GAVETA");
        assertTrue(objetoEncontrado.isPresent());
    }

    @Test
    @DisplayName("Deve retornar Optional.empty para objeto inexistente ou busca nula")
    void deveRetornarVazioParaObjetoInexistente() {
        sala.addObject(gaveta);

        assertTrue(sala.getObject("cofre").isEmpty());
        assertTrue(sala.getObject(null).isEmpty());
    }

    @Test
    @DisplayName("O método examine deve incluir a descrição da sala e listar os objetos")
    void deveExaminarSalaComDescricaoEObjetos() {
        sala.addObject(gaveta);

        String relatorio = sala.examine();

        assertTrue(relatorio.contains("Um escritório escuro e empoeirado."));
        assertTrue(relatorio.contains("Gaveta de madeira antiga."));
    }
}