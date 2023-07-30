# SALSA Candidate Source
Provides an account expansion based on the SALSA PYMK (People You May Know) algorithm for a given account. The algorithm focuses on the mutual follow and address book graph, making it highly effective at providing good mutual follow recommendations.

The SALSA algorithm constructs a local graph and performs personalized random walks to identify the best recommendations for the user. The local graph represents the community of users that are most similar to or most relevant to the user, while the personalized random walk identifies the most popular interests among them.

For each target user, the local graph is a bipartite graph with a left-hand side (LHS) and a right-hand side (RHS). The LHS is built from several sources, including the target user, forward and reverse address books, mutual follows, recent followings, and recent followers. We choose a specified number of top candidates from these sources for each target user with different weights assigned to each source to favor the corresponding source, and build the LHS using the target user and those top candidates. The RHS consists of two parts: the top candidates from the sources mentioned above for the target user and the mutual follows of the other entries in the LHS.

The random walk starts from the target user in the LHS and adopts a restarting strategy to realize personalization.

In summary, the SALSA Candidate Source provides an account expansion based on the SALSA PYMK algorithm, utilizing a bipartite graph with personalized random walks to identify the most relevant and interesting recommendations for the user.
