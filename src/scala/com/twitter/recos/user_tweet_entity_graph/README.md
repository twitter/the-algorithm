# UserT etEnt yGraph (UTEG)

## What  s  
User T et Ent y Graph (UTEG)  s a F nalge thr ft serv ce bu lt on t  GraphJet fra work.   ma nta ns a graph of user-t et relat onsh ps and serves user recom ndat ons based on traversals  n t  graph.

## How  s   used on Tw ter
UTEG generates t  "XXX L ked" out-of-network t ets seen on Tw ter's Ho  T  l ne.
T  core  dea beh nd UTEG  s collaborat ve f lter ng. UTEG takes a user's   ghted follow graph ( .e a l st of   ghted user ds) as  nput,
performs eff c ent traversal & aggregat on, and returns t  top-  ghted t ets engaged based on # of users that engaged t  t et, as  ll as
t  engaged users'   ghts.

UTEG  s a stateful serv ce and rel es on a Kafka stream to  ngest & pers st states.   ma nta ns  n- mory user engage nts over t  past
24-48 h s. Older events are dropped and GC'ed.

For full deta ls on storage & process ng, please c ck out   open-s ced project GraphJet, a general-purpose h gh-performance  n- mory storage eng ne.
- https://g hub.com/tw ter/GraphJet
- http://www.vldb.org/pvldb/vol9/p1281-sharma.pdf
