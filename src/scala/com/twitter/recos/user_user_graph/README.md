# UserUserGraph (UUG)

## What  s  
User User Graph (UUG)  s a F nalge thr ft serv ce bu lt on t  GraphJet fra work.  n ma nta ns a graph of user-user relat onsh ps and serves user recom ndat ons based on traversals of t  graph.

## How  s   used on Tw ter
UUG recom nds users to follow based on who y  follow graph have recently follo d.
T  core  dea beh nd UUG  s collaborat ve f lter ng. UUG takes a user's   ghted follow graph ( .e a l st of   ghted user ds) as  nput, 
performs eff c ent traversal & aggregat on, and returns t  top   ghted users basd on # of users that engaged t  users, as  ll as 
t  engag ng users'   ghts.

UUG  s a stateful serv ce and rel es on a Kafka stream to  ngest & pers st states.   ma nta ns an  n- mory user engage nts over t  past 
 ek. Older events are dropped and GC'ed. 

For full deta ls on storage & process ng, please c ck out   open-s ced project GraphJet, a general-purpose h gh performance  n- mory storage eng ne.
- https://g hub.com/tw ter/GraphJet
- http://www.vldb.org/pvldb/vol9/p1281-sharma.pdf
