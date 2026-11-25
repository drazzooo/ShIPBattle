import java.util.Scanner;

/**
 * Trabalho 1.ª parte de Introdução à Programação 2026/2027: ShIPBattle.
 *
 * @author Ruan Pimentel 66502
 * @author Qifan Wang 66056
 */
public class ShIPBattle {

    /**
     * Calcula a linha de uma posição no tabuleiro.
     *
     * @param pos        posição no tabuleiro
     * @param numColumns número de colunas do tabuleiro
     * @requires pos >= 0 && numColumns > 0
     * @ensures \result >= 1
     * @return o número da linha correspondente (começa em 1)
     */
    public static int rowOfPosition(int pos, int numColumns) {
        return (pos / numColumns) + 1;
    }

    /**
     * Calcula a coluna de uma posição no tabuleiro.
     *
     * @param pos        posição no tabuleiro
     * @param numColumns número de colunas do tabuleiro
     * @requires pos >= 0 && numColumns > 0
     * @ensures \result >= 1 && \result <= numColumns
     * @return o número da coluna correspondente (começa em 1)
     */
    public static int columnOfPosition(int pos, int numColumns) {
        return (pos % numColumns) + 1;
    }

    /**
     * Calcula o número da posição a partir da linha e da coluna.
     *
     * @param row        número da linha
     * @param column     número da coluna
     * @param numColumns número total de colunas do tabuleiro
     * @requires row >= 1 && column >= 1 && numColumns > 0
     * @ensures \result >= 0
     * @return o índice da posição no tabuleiro (começa em 0)
     */
    public static int positionNumber(int row, int column, int numColumns) {
        return (row - 1) * numColumns + (column - 1);
    }

    /**
     * Calcula o número de linhas de um tabuleiro a partir do número total de
     * posições e do número de colunas.
     *
     * @param size       número total de posições do tabuleiro
     * @param numColumns número de colunas do tabuleiro
     * @requires size > 0 && numColumns > 0 && size % numColumns == 0
     * @ensures \result > 0
     * @return o número de linhas do tabuleiro
     */
    public static int numberRows(int size, int numColumns) {
        return size / numColumns;
    }

    /**
     * Verifica se um carácter representa uma peça de navio.
     *
     * @param c o carácter a verificar
     * @ensures \result == (c == 'C' || c == 'L' || c == 'R' || c == 'B'
     *          || c == 'T' || c == 'M')
     * @return true se c for uma peça de navio ('C', 'L', 'R', 'B', 'T', 'M'),
     *         false caso contrário
     */
    public static boolean isShipPiece(char c) {
        return c == 'C' || c == 'L' || c == 'R' || c == 'B' || c == 'T' || c == 'M';
    }

    /**
     * Verifica se um carácter é de preenchimento (peça de navio ou água).
     *
     * @param c o carácter a verificar
     * @ensures \result == (isShipPiece(c) || c == 'X')
     * @return true se c for uma peça de navio ou água ('X'), false caso contrário
     */
    public static boolean isFillingChar(char c) {
        return isShipPiece(c) || c == 'X';
    }

    /**
     * Verifica se uma String representa uma solução-tabuleiro-em-construção.
     *
     * @param stc a String a verificar
     * @ensures \result == (stc != null && (para todo o c em stc:
     *          isFillingChar(c) || c == '.'))
     * @return true se stc não for null e contiver apenas peças de navio, água
     *         ('X') ou '.', false caso contrário
     */
    public static boolean isValidBoardInConstruction(String stc) {
        if (stc == null) {
            return false;
        }
        for (int i = 0; i < stc.length(); i++) {
            char c = stc.charAt(i);
            if (!isFillingChar(c) && c != '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se uma solução-tabuleiro-em-construção está totalmente preenchida
     * (sem pontos).
     *
     * @param stc a String do tabuleiro a verificar
     * @ensures \result == (stc != null && (para todo o c em stc: c != '.'))
     * @return true se stc não for null e não contiver o carácter '.', false caso
     *         contrário
     */
    public static boolean isFilled(String stc) {
        if (stc == null) {
            return false;
        }
        for (int i = 0; i < stc.length(); i++) {
            if (stc.charAt(i) == '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * Conta as peças de navio numa linha do tabuleiro.
     *
     * @param row        número da linha (começa em 1)
     * @param stc        a String do tabuleiro
     * @param numColumns número total de colunas do tabuleiro
     * @requires isValidBoardInConstruction(stc) && numColumns > 0 && row >= 1
     *           && row <= numberRows(stc.length(), numColumns)
     * @ensures \result >= 0 && \result <= numColumns
     * @return o número de peças de navio presentes na linha
     */
    public static int countShipPiecesInRow(int row, String stc, int numColumns) {
        int count = 0;
        int startPos = (row - 1) * numColumns;
        int endPos = row * numColumns;

        for (int i = startPos; i < endPos; i++) {
            char c = stc.charAt(i);
            if (isShipPiece(c)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Conta as peças de navio numa coluna do tabuleiro.
     *
     * @param col        número da coluna (começa em 1)
     * @param stc        a String do tabuleiro
     * @param numColumns número total de colunas do tabuleiro
     * @requires isValidBoardInConstruction(stc) && numColumns > 0 && col >= 1
     *           && col <= numColumns
     * @ensures \result >= 0
     * @return o número de peças de navio presentes na coluna
     */
    public static int countShipPiecesInCol(int col, String stc, int numColumns) {
        int count = 0;
        int startPos = col - 1;

        for (int i = startPos; i < stc.length(); i += numColumns) {
            char c = stc.charAt(i);
            if (isShipPiece(c)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Conta as peças de água ('X') numa linha do tabuleiro.
     *
     * @param row        número da linha (começa em 1)
     * @param stc        a String do tabuleiro em construção
     * @param numColumns número total de colunas do tabuleiro
     * @requires isValidBoardInConstruction(stc) && numColumns > 0 && row >= 1
     *           && row <= numberRows(stc.length(), numColumns)
     * @ensures \result >= 0 && \result <= numColumns
     * @return o número de peças de água na linha
     */
    public static int countWaterPiecesInRow(int row, String stc, int numColumns) {
        int count = 0;
        int startPos = positionNumber(row, 1, numColumns);

        for (int col = 1; col <= numColumns; col++) {
            int pos = startPos + (col - 1);
            if (stc.charAt(pos) == 'X') {
                count++;
            }
        }
        return count;
    }

    /**
     * Conta as peças de água ('X') numa coluna do tabuleiro.
     *
     * @param col        número da coluna (começa em 1)
     * @param stc        a String do tabuleiro em construção
     * @param numColumns número total de colunas do tabuleiro
     * @requires isValidBoardInConstruction(stc) && numColumns > 0 && col >= 1
     *           && col <= numColumns
     * @ensures \result >= 0
     * @return o número de peças de água na coluna
     */
    public static int countWaterPiecesInCol(int col, String stc, int numColumns) {
        int count = 0;
        int numRows = numberRows(stc.length(), numColumns);

        for (int row = 1; row <= numRows; row++) {
            int pos = positionNumber(row, col, numColumns);
            if (stc.charAt(pos) == 'X') {
                count++;
            }
        }
        return count;
    }

    /**
     * Verifica várias condições necessárias para stc ser um tabuleiro inicial
     * válido: tamanho de stc e das pistas, pistas formadas só por dígitos, e
     * compatibilidade das peças já preenchidas com cada pista.
     *
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param stc      solução-tabuleiro-em-construção
     * @param rowClues pistas de linha
     * @param colClues pistas de coluna
     * @requires rows > 0 && cols > 0 && isValidBoardInConstruction(stc)
     * @ensures \result só é true se stc tem tamanho rows * cols, as pistas têm o
     *          tamanho certo e são dígitos, e para cada linha e coluna as peças
     *          de navio preenchidas não excedem a pista e há posições (não
     *          água) suficientes para a atingir
     * @return true se todas as condições se verificarem, false caso contrário
     */
    public static boolean isBoard(int rows, int cols, String stc, String rowClues,
            String colClues) {
        if (stc == null || rowClues == null || colClues == null) {
            return false;
        }
        if (stc.length() != rows * cols) {
            return false;
        }
        if (rowClues.length() != rows || colClues.length() != cols) {
            return false;
        }
        for (int i = 0; i < rows; i++) {
            if (!Character.isDigit(rowClues.charAt(i))) {
                return false;
            }
        }
        for (int j = 0; j < cols; j++) {
            if (!Character.isDigit(colClues.charAt(j))) {
                return false;
            }
        }
        for (int row = 1; row <= rows; row++) {
            int clue = Character.getNumericValue(rowClues.charAt(row - 1));
            int ships = countShipPiecesInRow(row, stc, cols);
            int water = countWaterPiecesInRow(row, stc, cols);

            if (ships > clue || (cols - water) < clue) {
                return false;
            }
        }
        for (int col = 1; col <= cols; col++) {
            int clue = Character.getNumericValue(colClues.charAt(col - 1));
            int ships = countShipPiecesInCol(col, stc, cols);
            int water = countWaterPiecesInCol(col, stc, cols);

            if (ships > clue || (rows - water) < clue) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se stc é uma solução do tabuleiro board, considerando as pistas.
     *
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param board    tabuleiro inicial
     * @param rowClues pistas de linha
     * @param colClues pistas de coluna
     * @param stc      proposta de solução
     * @requires rows > 0 && cols > 0
     *           && isBoard(rows, cols, board, rowClues, colClues)
     *           && isValidBoardInConstruction(stc)
     * @ensures \result só é true se stc tem o tamanho de board e está toda
     *          preenchida, mantém as peças iniciais, respeita as pistas, tem um
     *          navio de cada tamanho e todos estão rodeados de água
     * @return true se stc for uma solução de board, false caso contrário
     */
    public static boolean isSolution(int rows, int cols, String board,
            String rowClues, String colClues, String stc) {
        return stc.length() == board.length() && isFilled(stc)
                && keepsInitialPieces(board, stc)
                && matchesClues(rows, cols, rowClues, colClues, stc)
                && hasValidFleet(rows, cols, stc);
    }

    /**
     * Verifica se a proposta conserva todas as peças iniciais.
     *
     * @param board tabuleiro inicial
     * @param stc   proposta de solução
     * @requires board.length() == stc.length()
     * @ensures \result == (para toda a posição pos de board: board[pos] == '.'
     *          || board[pos] == stc[pos])
     * @return true se todas as posições fixas forem respeitadas
     */
    private static boolean keepsInitialPieces(String board, String stc) {
        for (int pos = 0; pos < board.length(); pos++) {
            char initialPiece = board.charAt(pos);
            if (initialPiece != '.' && initialPiece != stc.charAt(pos)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se o número de peças de navio coincide com cada pista.
     *
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param rowClues pistas de linha
     * @param colClues pistas de coluna
     * @param stc      tabuleiro preenchido
     * @requires as pistas só têm dígitos e têm o tamanho certo
     * @ensures \result == (cada linha e cada coluna tem tantas peças de navio
     *          como a respetiva pista)
     * @return true se todas as pistas forem respeitadas
     */
    private static boolean matchesClues(int rows, int cols, String rowClues,
            String colClues, String stc) {
        for (int row = 1; row <= rows; row++) {
            int expected = Character.getNumericValue(rowClues.charAt(row - 1));
            if (countShipPiecesInRow(row, stc, cols) != expected) {
                return false;
            }
        }

        for (int col = 1; col <= cols; col++) {
            int expected = Character.getNumericValue(colClues.charAt(col - 1));
            if (countShipPiecesInCol(col, stc, cols) != expected) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se a frota contém exatamente um navio de cada tamanho (1, 2, 3 e
     * 4), bem formados e rodeados de água, e que todas as peças de navio
     * pertencem a esses navios.
     *
     * @param rows número de linhas do tabuleiro
     * @param cols número de colunas do tabuleiro
     * @param stc  tabuleiro preenchido
     * @requires stc.length() == rows * cols && isFilled(stc)
     * @ensures \result só é true se existe um navio de cada tamanho, todos
     *          rodeados de água, e não há peças de navio soltas
     * @return true se todas as peças pertencerem a uma frota válida
     */
    private static boolean hasValidFleet(int rows, int cols, String stc) {
        String seenLength = "";
        int shipPieces = 0;
        int coveredPieces = 0;

        for (int pos = 0; pos < stc.length(); pos++) {
            char piece = stc.charAt(pos);
            if (isShipPiece(piece)) {
                shipPieces++;
            }
            if (piece == 'C' || piece == 'L' || piece == 'T') {
                int row = rowOfPosition(pos, cols);
                int col = columnOfPosition(pos, cols);

                int rowStep = 0;
                int colStep = 0;
                if (piece == 'T') {
                    rowStep = 1;
                } else if (piece == 'L') {
                    colStep = 1;
                }

                int length = 1;
                if (piece != 'C') {
                    char end = 'R';
                    if (piece == 'T') {
                        end = 'B';
                    }
                    length = readShipLength(rows, cols, stc, row, col, rowStep,
                            colStep, end);
                }

                String lengthText = "" + length;
                if (length == 0 || seenLength.indexOf(lengthText) >= 0) {
                    return false;
                }

                int lastRow = row + (length - 1) * rowStep;
                int lastCol = col + (length - 1) * colStep;
                if (!hasWaterAround(rows, cols, stc, row, col, lastRow, lastCol)) {
                    return false;
                }

                seenLength = lengthText + seenLength;
                coveredPieces = coveredPieces + length;
            }
        }
        return seenLength.length() == 4 && coveredPieces == shipPieces;
    }

    /**
     * Calcula o comprimento de um navio a partir da sua extremidade inicial,
     * avançando numa direção até encontrar a extremidade final.
     *
     * @param rows    número de linhas do tabuleiro
     * @param cols    número de colunas do tabuleiro
     * @param stc     tabuleiro preenchido
     * @param row     linha da extremidade inicial
     * @param col     coluna da extremidade inicial
     * @param rowStep passo na linha (1 para navios verticais, 0 caso contrário)
     * @param colStep passo na coluna (1 para navios horizontais, 0 caso
     *                contrário)
     * @param end     peça que termina o navio ('R' na horizontal, 'B' na vertical)
     * @requires stc.length() == rows * cols && (rowStep == 1 && colStep == 0
     *           || rowStep == 0 && colStep == 1)
     * @ensures \result == 0 || \result >= 2 && \result <= 4
     * @return o comprimento do navio, ou 0 se o navio estiver mal formado
     */
    private static int readShipLength(int rows, int cols, String stc, int row,
            int col, int rowStep, int colStep, char end) {

        for (int offset = 1; offset <= 3; offset++) {
            int nextRow = row + offset * rowStep;
            int nextCol = col + offset * colStep;

            if (nextRow > rows || nextCol > cols) {
                return 0;
            }

            char piece = stc.charAt(positionNumber(nextRow, nextCol, cols));

            if (piece == end) {
                return offset + 1;
            }
            if (piece != 'M') {
                return 0;
            }
        }

        return 0;
    }

    /**
     * Verifica se as posições à volta de um navio contêm apenas água.
     *
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param stc      tabuleiro preenchido
     * @param firstRow linha da primeira peça do navio
     * @param firstCol coluna da primeira peça do navio
     * @param lastRow  linha da última peça do navio
     * @param lastCol  coluna da última peça do navio
     * @requires stc.length() == rows * cols
     * @ensures \result == (todas as posições vizinhas ao navio, dentro do
     *          tabuleiro, têm 'X')
     * @return true se todas as posições vizinhas dentro do tabuleiro forem água
     */
    private static boolean hasWaterAround(int rows, int cols, String stc,
            int firstRow, int firstCol, int lastRow, int lastCol) {
        for (int row = firstRow - 1; row <= lastRow + 1; row++) {
            for (int col = firstCol - 1; col <= lastCol + 1; col++) {
                boolean onBoard = row >= 1 && row <= rows && col >= 1 && col <= cols;

                boolean onShip = row >= firstRow && row <= lastRow
                        && col >= firstCol && col <= lastCol;

                if (onBoard && !onShip) {
                    int pos = positionNumber(row, col, cols);
                    if (stc.charAt(pos) != 'X') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Imprime o tabuleiro em quadrícula, com a pista de cada linha à direita
     * (separada por um espaço) e as pistas de coluna numa linha por baixo.
     *
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param stc      solução-tabuleiro-em-construção
     * @param rowClues pistas de linha
     * @param colClues pistas de coluna
     * @requires rows > 0 && cols > 0 && stc.length() == rows * cols
     *           && rowClues.length() == rows && colClues.length() == cols
     * @ensures o tabuleiro e as pistas são escritos no standard output
     */
    public static void printBoard(int rows, int cols, String stc, String rowClues,
            String colClues) {
        for (int row = 1; row <= rows; row++) {
            int start = positionNumber(row, 1, cols);
            int end = start + cols;

            String rowStr = stc.substring(start, end);

            System.out.println(rowStr + " " + rowClues.charAt(row - 1));
        }
        System.out.println(colClues);
    }

    /**
     * Verifica se uma jogada é válida.
     *
     * @param stc   solução-tabuleiro-em-construção
     * @param board tabuleiro inicial
     * @param pos   posição da jogada
     * @param c     carácter da jogada
     * @ensures \result == (pos é uma posição do tabuleiro, vazia em board, e c é
     *          uma peça de navio, 'X' ou '.')
     * @return true se a jogada for válida, false caso contrário
     */
    public static boolean isValidMove(String stc, String board, int pos, char c) {
        if (stc == null || board == null || stc.length() != board.length()) {
            return false;
        }
        if (pos < 0 || pos >= board.length() || board.charAt(pos) != '.') {
            return false;
        }
        if (!isFillingChar(c) && c != '.') {
            return false;
        }
        return true;
    }

    /**
     * Efetua uma jogada, devolvendo a nova solução-tabuleiro-em-construção.
     *
     * @param stc solução-tabuleiro-em-construção
     * @param pos posição da jogada
     * @param c   carácter da jogada
     * @requires pos >= 0 && pos < stc.length() && (isFillingChar(c) || c == '.')
     * @ensures \result.length() == stc.length() && \result.charAt(pos) == c, e as
     *          restantes posições ficam iguais às de stc
     * @return a String que resulta de colocar c na posição pos de stc
     */
    public static String playMove(String stc, int pos, char c) {
        return stc.substring(0, pos) + c + stc.substring(pos + 1);
    }

    /**
     * Ciclo de jogo: lê jogadas do utilizador até o tabuleiro estar resolvido ou
     * o utilizador indicar -1 como posição para terminar.
     *
     * @param sc       canal de leitura do standard input
     * @param rows     número de linhas do tabuleiro
     * @param cols     número de colunas do tabuleiro
     * @param board    tabuleiro inicial
     * @param rowClues pistas de linha
     * @param colClues pistas de coluna
     * @requires sc aberto && rows > 0 && cols > 0
     *           && isBoard(rows, cols, board, rowClues, colClues)
     * @ensures o jogo decorre no standard input/output e termina quando o
     *          tabuleiro está resolvido ou o utilizador indica -1
     */
    public static void play(Scanner sc, int rows, int cols, String board,
            String rowClues, String colClues) {
        String stc = board;
        printBoard(rows, cols, stc, rowClues, colClues);

        boolean quit = false;

        while (!quit && !isSolution(rows, cols, board, rowClues, colClues, stc)) {
            System.out.println("Introduza uma jogada (ou -1 para sair).");
            System.out.print("Posicao: ");
            int pos = sc.nextInt();
            if (pos == -1) {
                quit = true;
            } else {
                sc.nextLine(); // consome o resto da linha da posição
                System.out.print("Jogada: ");
                String move = sc.nextLine();
                if (move.length() == 1 && isValidMove(stc, board, pos, move.charAt(0))) {
                    stc = playMove(stc, pos, move.charAt(0));
                } else {
                    System.out.println("Jogada inválida! Por favor tente novamente, "
                            + "ou indique -1 como posição de jogada para terminar.");
                }
                printBoard(rows, cols, stc, rowClues, colClues);
            }
        }
        if (isSolution(rows, cols, board, rowClues, colClues, stc)) {
            System.out.println("Parabéns!!! Encontrou todos os navios em ShIPBattle!");
        } else {
            System.out.println("Jogo terminado.");
        }
    }

    /**
     * Converte um texto num inteiro, usando um Scanner.
     *
     * @param text texto a converter
     * @requires text != null
     * @ensures \result == o inteiro representado por text, ou 0 se text não
     *          representar um inteiro
     * @return o inteiro representado por text, ou 0 se não for um inteiro
     */
    private static int readIntArg(String text) {
        Scanner scanner = new Scanner(text);
        int value = 0;
        if (scanner.hasNextInt()) {
            value = scanner.nextInt();
        }
        return value;
    }
    /**
     * Método principal que inicia a aplicação ShIPBattle.
     * 
     * @param args Argumentos da linha de comandos (opcionalmente: rows, cols, board, rowClues, colClues)
     * @requires args != null
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int rows = 0;
        int cols = 0;
        String board = "";
        String rowClues = "";
        String colClues = "";

        if (args.length == 5) {
            rows = readIntArg(args[0]);
            cols = readIntArg(args[1]);
            board = args[2];
            rowClues = args[3];
            colClues = args[4];
        } else if (args.length == 0) {
            System.out.print("Número de linhas: ");
            rows = sc.nextInt();
            System.out.print("Número de colunas: ");
            cols = sc.nextInt();
            System.out.print("Tabuleiro inicial (string de tamanho " + (rows * cols) + "): ");
            board = sc.next();
            System.out.print("Pistas de linha: ");
            rowClues = sc.next();
            System.out.print("Pistas de coluna: ");
            colClues = sc.next();
        }
        if (rows >= 1 && rows <= 10 && cols >= 1 && cols <= 10
                && isValidBoardInConstruction(board)
                && isBoard(rows, cols, board, rowClues, colClues)) {
            play(sc, rows, cols, board, rowClues, colClues);
        } else {
            System.out.println("Tabuleiro inicial inválido! Verifique o tamanho, "
                    + "as pistas fornecidas, ou as peças no tabuleiro inicial.");
        }
        sc.close();
    }
}
