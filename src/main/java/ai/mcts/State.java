//package ai.mcts;
//
//import game.GameBoard;
//import game.Move;
//import utility.RandomNumbers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class State {
//    private GameBoard board;
//    private int playerIndex;
//    private int visitCount;
//    private double score;
//
//    public State() {
//        this.board = new GameBoard();
//    }
//
//    // copy constructor
//    public State(State state) {
//        this.board = new GameBoard(state.board);
//        this.playerIndex = state.playerIndex;
//        this.visitCount = state.visitCount;
//        this.score = state.score;
//    }
//
//    public State(GameBoard board) {
//        this.board = new GameBoard(board);
//    }
//
//    public GameBoard getBoard() {
//        return board;
//    }
//
//    public void setBoard(GameBoard board) {
//        this.board = board;
//    }
//
//    public int getPlayerIndex() {
//        return playerIndex;
//    }
//
//    public void setPlayerIndex(int playerIndex) {
//        this.playerIndex = playerIndex;
//    }
//
//    public int getVisitCount() {
//        return visitCount;
//    }
//
//    public void setVisitCount(int visitCount) {
//        this.visitCount = visitCount;
//    }
//
//    public double getScore() {
//        return score;
//    }
//
//    public void setScore(double score) {
//        this.score = score;
//    }
//
//    // TODO only works for second phase (movement)
//    public List<State> getAllPossibleStates() {
//        List<State> possibleStates = new ArrayList<>();
//
//        for (Move move : board.getLegalMovesForPlayerByIndex(this.playerIndex)) {
//            if (move.getPenguin().isOnGameBoard() && board.hasPenguinLegalMoves(move.getPenguin())) {
//                State newState = new State(this);
//                // TODO adjust opponent index?
//                newState.setPlayerIndex(getOpponentIndex());
//                // TODO adjust moving player first?
//                newState.getBoard().movePenguin(move.getPenguin(), move.getRowIndex(), move.getColIndex());
//                possibleStates.add(newState);
//            }
//        }
//
//        return possibleStates;
//    }
//
//    // TODO only works for second phase (movement)
//    public void randomPlay() {
//        List<Move> allPossibleMoves = this.board.getLegalMovesForPlayerByIndex(this.playerIndex);
//        Move randomMove = allPossibleMoves.get(RandomNumbers.getRandomIndex(allPossibleMoves.size()));
//        this.board.movePenguin(randomMove.getPenguin(), randomMove.getRowIndex(), randomMove.getColIndex());
//    }
//
//    public void increaseVisitCount() {
//        this.visitCount++;
//    }
//
//    public void increaseScore(double score) {
//        this.score += score;
//    }
//
//    public int getOpponentIndex() {
//        return 1 - this.playerIndex;
//    }
//}
