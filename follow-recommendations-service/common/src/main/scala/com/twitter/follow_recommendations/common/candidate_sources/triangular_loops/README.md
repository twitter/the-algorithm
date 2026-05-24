# Triangular Loops Candidate Source
Provides account candidates based on the graph structures of the form u -> v -> w -> u,
where the arrow indicates a follow edge. In other words, it looks for triangular loops in the user-user graph.

If the edge v -> u does not exist in the triangular loop, the Triangular Loops Candidate Source recommends u as a potential outbound mutual follow candidate for v.
