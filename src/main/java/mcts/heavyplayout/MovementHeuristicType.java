package mcts.heavyplayout;

public enum MovementHeuristicType {
  NONE,
  MAX_TOTAL_FISH,
  MAX_NEW_FISH_PER_TILE,
  MAX_NEW_TOTAL_FISH,
  ISOLATE_OPPONENT,
  SECURE_AREA
}
