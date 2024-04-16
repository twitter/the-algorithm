# Two-hop Random Walk
T  TwoHopRandomWalk algor hm re-ranks a user's second degree connect ons based on recent engage nt strength. T  algor hm works as follows:

* G ven a user `src`, f nd t  r top K f rst degree connect ons `fd(1)`, `fd(2)`, `fd(3)`,...,`fd(K)`. T  rank ng  s based on real graph   ghts, wh ch  asure t  recent engage nt strength on t  edges.
* For each of t  f rst degree connect ons `fd( )`, expand to t  r top L connect ons v a real graph, `sd( ,1)`, `sd( ,2)`,...,`sd( ,L)`. Note that sd nodes can also be `src`'s f rst degree nodes.
* Aggregate all t  nodes  n step 2, f lter out t  f rst degree nodes, and calculate t    ghted sum for t  second degree.
* Re-rank t  second degree nodes and select t  top M results as t  algor hm output.
