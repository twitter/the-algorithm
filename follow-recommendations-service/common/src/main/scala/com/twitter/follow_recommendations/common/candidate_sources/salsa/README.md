# SALSA Cand date S ce
Prov des an account expans on based on t  SALSA PYMK (People   May Know) algor hm for a g ven account. T  algor hm focuses on t  mutual follow and address book graph, mak ng   h ghly effect ve at prov d ng good mutual follow recom ndat ons.

T  SALSA algor hm constructs a local graph and performs personal zed random walks to  dent fy t  best recom ndat ons for t  user. T  local graph represents t  commun y of users that are most s m lar to or most relevant to t  user, wh le t  personal zed random walk  dent f es t  most popular  nterests among t m.

For each target user, t  local graph  s a b part e graph w h a left-hand s de (LHS) and a r ght-hand s de (RHS). T  LHS  s bu lt from several s ces,  nclud ng t  target user, forward and reverse address books, mutual follows, recent follow ngs, and recent follo rs.   choose a spec f ed number of top cand dates from t se s ces for each target user w h d fferent   ghts ass gned to each s ce to favor t  correspond ng s ce, and bu ld t  LHS us ng t  target user and those top cand dates. T  RHS cons sts of two parts: t  top cand dates from t  s ces  nt oned above for t  target user and t  mutual follows of t  ot r entr es  n t  LHS.

T  random walk starts from t  target user  n t  LHS and adopts a restart ng strategy to real ze personal zat on.

 n summary, t  SALSA Cand date S ce prov des an account expans on based on t  SALSA PYMK algor hm, ut l z ng a b part e graph w h personal zed random walks to  dent fy t  most relevant and  nterest ng recom ndat ons for t  user.
