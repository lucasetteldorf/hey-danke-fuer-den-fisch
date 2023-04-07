package mcts.heavyplayout;

public enum PlacementHeuristicType {
  NONE,
  MAX_FISH_PER_TILE,
  MAX_FISH_PER_NEIGHBOR_TILE,
  MAX_TOTAL_FISH,
  MAX_TOTAL_NEIGHBOR_FISH,
  MAX_OWN_POSSIBILITIES,
  MIN_OPPONENT_POSSIBILITIES
}