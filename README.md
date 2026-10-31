# 🧩 Escape-Enigma (Java CLI Text Adventure)

Um jogo de aventura em formato de texto interativo via terminal (CLI), focado na resolução de puzzles e decodificação de senhas cifradas. O projeto foi desenvolvido em Java puro aplicando princípios sólidos de **Orientação a Objetos (SOLID)**, **Clean Architecture** e **Testes Automatizados com JUnit 5**.

---

## 🎯 Sobre o Jogo

O jogador acorda em um escritório trancado e precisa explorar o ambiente, utilizar itens, decifrar códigos e destravar sistemas para obter a chave mestra que abre a porta de saída.

### Exemplo de Comandos
- `examinar [sala / objeto / item]`
- `usar [item] em [objeto]`
- `pegar [item] de [objeto]`
- `digitar [senha] em [terminal]`
- `mochila` (ou `inventario`)
- `sair`

---
## 🏗️ Arquitetura e Design Patterns
O projeto adota uma estrutura desacoplada dividida em 4 pacotes principais:
```
src/main/java/github/ebrauta/
├── engine/      # Motor de jogo e controle de estado (GameEngine)
├── models/       # Entidades de domínio (Room, Inventory, Item, InteractiveObject)
│   └── objects/ # Subclasses polimórficas (Drawer, Terminal, Door)
├── parser/      # Interpretador de comandos via Regex (ParserComando, Comando)
└── util/        # Utilitários de lógica pura (DecodificadorEnigma - Cifra de César)
```

### Principais Destaques de Engenharia
- **Polimorfismo e Extensibilidade:** A classe abstrata `InteractiveObject` define o contrato comum para qualquer interativo da sala, permitindo que `Drawer`, `Terminal` e `Door` especializem suas comportamentos via *Template Method* (`uponUnlocking`).
- **Encapsulamento com `Optional`:** Métodos de busca e coleta de itens (como `takeItem()` e `getItemById()`) utilizam `Optional<T>` nativo do Java para defender o sistema contra `NullPointerException`.
- **Desacoplamento da Engine:** A `GameEngine` processa os comandos de forma agnóstica à entrada padrão (`System.in`), o que permite testar todo o loop de regras de negócio via testes de unidade sem interações de teclado.
- **Parser com Regex:** O `ParserComando` utiliza expressões regulares estruturadas com grupos de captura para extrair comandos complexos de linguagem natural.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java JDK 17** ou superior
- **Maven** ou **Gradle** (ou suporte nativo da sua IDE preferida)

### Passos para Rodar

1. **Clonar o repositório:**
   ```bash
   git clone [https://github.com/ebrauta/escape-enigma.git](https://github.com/ebrauta/escape-enigma.git)
   cd escape-enigma
   
2. **Compilar e Executar via Terminal:**
   ```bash
   # Com Maven
     mvn clean compile exec:java -Dexec.mainClass="br.com.escapeenigma.Main"
   # Ou compilar diretamente com javac
     javac -d bin $(find src/main/java -name "*.java")
     java -cp bin br.com.escapeenigma.Main
   ```

## 🧪 Suíte de Testes Automatizados
O projeto conta com uma cobertura robusta de testes unitários e de integração de regras utilizando JUnit 5.
Executando os Testes
```bash
# Executar a suíte completa pelo Maven
mvn test
```
O que está sendo coberto pelos testes:
- DrawerTest / DoorTest / TerminalTest: Transições de estado (trancado/aberto), uso de itens incorretos e resiliência a senhas.
- DecodificadorEnigmaTest: Matemática de char na Cifra de César, deslocamentos, wrap-around ASCII (A $\to$ Z) e preservação de caracteres especiais/espaços usando @ParameterizedTest.
- GameEngineTest: Simulação da execução de comandos sem necessidade de leitor de Scanner.

## 🛠️ Tecnologias Utilizadas
- Linguagem: Java 21
- Testes: JUnit 5 (Jupiter & Params)
- Paradigma: Object-Oriented Programming (OOP)
  
