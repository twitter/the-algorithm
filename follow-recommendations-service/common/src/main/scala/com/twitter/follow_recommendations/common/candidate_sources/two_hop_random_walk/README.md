# Two-hop Random Walk
The TwoHopRandomWalk algorithm re-ranks a user's second degree connections based on recent engagement strength. The algorithm works as follows:

* Given a user `src`, find their top K first degree connections `fd(1)`, `fd(2)`, `fd(3)`,...,`fd(K)`. The ranking is based on real graph weights, which measure the recent engagement strength on the edges.
* For each of the first degree connections `fd(i)`, expand to their top L connections via real graph, `sd(i,1)`, `sd(i,2)`,...,`sd(i,L)`. Note that sd nodes can also be `src`'s first degree nodes.
* Aggregate all the nodes in step 2, filter out the first degree nodes, and calculate the weighted sum for the second degree.
* Re-rank the second degree nodes and select the top M results as the algorithm output.
