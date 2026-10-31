package github.ebrauta;

import github.ebrauta.engine.GameEngine;
import github.ebrauta.models.Inventory;
import github.ebrauta.models.Item;
import github.ebrauta.models.Room;
import github.ebrauta.models.objects.Door;
import github.ebrauta.models.objects.Drawer;
import github.ebrauta.models.objects.Terminal;
import github.ebrauta.parser.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    private GameEngine engine;
    private Room sala;
    private Inventory mochila;
    private Drawer gaveta;
    private Terminal terminal;
    private Door portaSaida;

    @BeforeEach
    void setUp() {
        Item chaveGaveta = new Item("chave-gaveta", "Chave Pequena", "Abre a gaveta.");
        Item bilhete = new Item("bilhete", "Bilhete Cifrado", "KHOOR ZRUOG");
        Item chaveSaida = new Item("chave-saida", "Chave Mestra", "Abre a porta principal.");

        gaveta = new Drawer("gaveta", "Gaveta da Mesa", "Gaveta de madeira antiga.", "chave-gaveta", bilhete);
        terminal = new Terminal("terminal", "Terminal de Acesso", "Terminal verde.", "bilhete","HELLO WORLD", chaveSaida);
        portaSaida = new Door("porta", "Porta Principal", "Porta pesada de ferro.", "chave-saida");

        sala = new Room("Escritório", "Um escritório escuro.");
        sala.addObject(gaveta);
        sala.addObject(terminal);
        sala.addObject(portaSaida);

        mochila = new Inventory();
        mochila.addItem(chaveGaveta);

        engine = new GameEngine(sala, mochila);
    }

    @Test
    @DisplayName("Deve iniciar a engine com o jogo em execução")
    void deveIniciarExecutando() {
        assertTrue(engine.isRunning());
    }

    @Test
    @DisplayName("Deve permitir o jogador abrir a gaveta e coletar o item para a mochila")
    void deveAbrirGavetaEPeagarItem() {
        // 1. Usar a chave na gaveta
        engine.processCommand(new Command("USAR", "chave-gaveta", "gaveta"));
        assertFalse(gaveta.objectIsClosed());

        // 2. Pegar o bilhete de dentro da gaveta
        engine.processCommand(new Command("PEGAR", "bilhete", "gaveta"));
        assertTrue(mochila.getItemById("bilhete").isPresent());
    }

    @Test
    @DisplayName("Deve aceitar a senha correta no terminal e desbloqueá-lo")
    void deveDesbloquearTerminalComSenha() {
        engine.processCommand(new Command("DIGITAR", "HELLO WORLD", "terminal"));
        assertFalse(terminal.objectIsClosed());
    }

    @Test
    @DisplayName("Deve encerrar a execução do jogo ao acionar a ação SAIR")
    void deveEncerrarAoSair() {
        engine.processCommand(new Command("SAIR", null, null));
        assertFalse(engine.isRunning());
    }

    @Test
    @DisplayName("Deve encerrar o jogo com vitória ao usar a chave-saida na porta")
    void deveVencerOJogoAoUsarChaveNaPorta() {
        // Adiciona a chave mestra diretamente na mochila para testar o fim do jogo
        Item chaveSaida = new Item("chave-saida", "Chave Mestra", "Abre a porta.");
        mochila.addItem(chaveSaida);

        // Usar a chave mestra na porta
        engine.processCommand(new Command("USAR", "chave-saida", "porta"));

        assertTrue(portaSaida.isEscaped());
        assertFalse(engine.isRunning(), "A engine deve parar de rodar após a vitória.");
    }
}