## Opção 1: Execução Simples (Recomendada)

### Pré-requisitos
- Java 8 ou superior instalado
- Download do SQLite JDBC Driver

### Passos:
1. **Baixar dependências:**
   - Acesse: https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
   - Baixe a versão 3.42.0.0
   - Coloque o arquivo .jar na pasta `lib/`

2. **Estrutura de pastas:**
   ```
   SistemaEscalacoes/
   ├── src/
   │   ├── classes/
   │   ├── dao/
   │   ├── controller/
   │   └── view/
   ├── lib/
   │   └── sqlite-jdbc-3.42.0.0.jar
   ├── bin/ (será criado automaticamente)
   ├── executar.sh (Linux/Mac)
   └── executar.bat (Windows)
   ```

3. **Executar:**
   - **Linux/Mac:** `chmod +x executar.sh && ./executar.sh`
   - **Windows:** Duplo-click em `executar.bat`

## Opção 2: IDE (Eclipse, IntelliJ, NetBeans)

### Eclipse:
1. File → New → Java Project
2. Criar projeto "SistemaEscalacoes"
3. Copiar arquivos fonte para src/
4. Adicionar sqlite-jdbc jar ao Build Path:
   - Right-click no projeto → Properties
   - Java Build Path → Libraries → Add External JARs
   - Selecionar sqlite-jdbc-3.42.0.0.jar
5. Run → Run As → Java Application → TelaPrincipal

### IntelliJ IDEA:
1. File → New → Project → Java
2. Copiar arquivos fonte
3. File → Project Structure → Libraries → + → Java
4. Adicionar sqlite-jdbc-3.42.