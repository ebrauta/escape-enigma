package github.ebrauta.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EnigmaDecoderTest {
    @Test
    @DisplayName("Deve decifrar uma palavra simples com deslocamento de +3")
    void deveDecifrarPalavraSimples() {
        String cifrado = "KHOOR";
        int deslocamento = 3;
        String resultado = EnigmaDecoder.decode(cifrado, deslocamento);
        assertEquals("HELLO", resultado);
    }

    @Test
    @DisplayName("Deve manter os espaços inalterados ao decifrar uma frase")
    void deveManterEspacosInalterados() {
        String cifrado = "KHOOR ZRUOG";
        int deslocamento = 3;
        String resultado = EnigmaDecoder.decode(cifrado, deslocamento);
        assertEquals("HELLO WORLD", resultado);
    }

    @Test
    @DisplayName("Deve fazer o wrap-around corretamente do início do alfabeto (ex: A voltando para Z)")
    void deveFazerWrapAroundDoAlfabeto() {
        // Com deslocamento 3: D -> A, C -> Z, B -> Y, A -> X
        String cifrado = "DCBA";
        int deslocamento = 3;

        String resultado = EnigmaDecoder.decode(cifrado, deslocamento);

        assertEquals("AZYX", resultado);
    }

    @ParameterizedTest(name = "Cifrado: \"{0}\" com shift {1} deve resultar em: \"{2}\"")
    @CsvSource({
            "KHOOR ZRUOG, 3, HELLO WORLD",
            "EUG, 2, CSE",
            "ABC, 1, ZAB",
            "TESTE, 0, TESTE"
    })
    @DisplayName("Deve validar múltiplos cenários de decodificação via testes parametrizados")
    void deveValidarMultiplosCenarios(String cifrado, int shift, String esperado) {
        assertEquals(esperado, EnigmaDecoder.decode(cifrado, shift));
    }

    @Test
    @DisplayName("Deve manter caracteres especiais e números intocados")
    void deveManterCaracteresEspeciaisENumeros() {
        String cifrado = "KHOOR! 123";
        int deslocamento = 3;

        String resultado = EnigmaDecoder.decode(cifrado, deslocamento);

        assertEquals("HELLO! 123", resultado);
    }
}