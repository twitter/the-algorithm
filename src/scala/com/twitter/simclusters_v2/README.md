# S mClusters: Commun y-based Representat ons for  terogeneous Recom ndat ons at Tw ter

## Overv ew
S mClusters  s as a general-purpose representat on layer based on overlapp ng commun  es  nto wh ch users as  ll as  terogeneous content can be captured as sparse,  nterpretable vectors to support a mult ude of recom ndat on tasks.

  bu ld   user and t et S mClusters embedd ngs based on t   nferred commun  es, and t  representat ons po r   personal zed t et recom ndat on v a   onl ne serv ng serv ce S mClusters ANN.


For more deta ls, please read   paper that was publ s d  n KDD'2020 Appl ed Data Sc ence Track: https://www.kdd.org/kdd2020/accepted-papers/v ew/s mclusters-commun y-based-representat ons-for- terogeneous-recom ndat o

## Br ef  ntroduct on to S mclusters Algor hm

### Follow relat onsh ps as a b part e graph
Follow relat onsh ps on Tw ter are perhaps most naturally thought of as d rected graph, w re each node  s a user and each edge represents a Follow. Edges are d rected  n that User 1 can follow User 2, User 2 can follow User 1 or both User 1 and User 2 can follow each ot r.

T  d rected graph can be also v e d as a b part e graph, w re nodes are grouped  nto two sets, Producers and Consu rs.  n t  b part e graph, Producers are t  users who are Follo d and Consu rs are t  Follo es. Below  s a toy example of a follow graph for f  users:

< mg src=" mages/b part e_graph.png" w dth = "400px">

> F gure 1 - Left panel: A d rected follow graph; R ght panel: A b part e graph representat on of t  d rected graph

### Commun y Detect on - Known For 
T  b part e follow graph can be used to  dent fy groups of Producers who have s m lar follo rs, or who are "Known For" a top c. Spec f cally, t  b part e follow graph can also be represented as an *m x n* matr x (*A*), w re consu rs are presented as *u* and producers are represented as *v*.

Producer-producer s m lar y  s computed as t  cos ne s m lar y bet en users who follow each producer. T  result ng cos ne s m lar y values can be used to construct a producer-producer s m lar y graph, w re t  nodes are producers and edges are   ghted by t  correspond ng cos ne s m lar y value. No se removal  s perfor d, such that edges w h   ghts below a spec f ed threshold are deleted from t  graph.

After no se removal has been completed,  tropol s-Hast ngs sampl ng-based commun y detect on  s t n run on t  Producer-Producer s m lar y graph to  dent fy a commun y aff l at on for each producer. T  algor hm takes  n a para ter *k* for t  number of commun  es to be detected.

< mg src=" mages/producer_producer_s m lar y.png">

> F gure 2 -  Left panel: Matr x representat on of t  follow graph dep cted  n F gure 1; M ddle panel: Producer-Producer s m lar y  s est mated by calculat ng t  cos ne s m lar y bet en t  users who follow each producer; R ght panel: Cos ne s m lar y scores are used to create t  Producer-Producer s m lar y graph. A cluster ng algor hm  s run on t  graph to  dent fy groups of Producers w h s m lar follo rs.

Commun y aff l at on scores are t n used to construct an *n x k* "Known For" matr x (*V*). T  matr x  s max mally sparse, and each Producer  s aff l ated w h at most one commun y.  n product on, t  Known For dataset covers t  top 20M producers and k ~= 145000.  n ot r words,   d scover around 145k commun  es based on Tw ter's user follow graph.

< mg src=" mages/knownfor.png">

> F gure 3 -  T  cluster ng algor hm returns commun y aff l at on scores for each producer. T se scores are represented  n matr x V.

 n t  example above, Producer 1  s "Known For" commun y 2, Producer 2  s "Known For" commun y 1, and so forth.

### Consu r Embedd ngs - User  nterested n
An  nterested  n matr x (*U*) can be computed by mult ply ng t  matr x representat on of t  follow graph (*A*) by t  Known For matr x (*V*): 

< mg src=" mages/ nterested n.png">

 n t  toy example, consu r 1  s  nterested  n commun y 1 only, w reas consu r 3  s  nterested  n all three commun  es. T re  s also a no se removal step appl ed to t   nterested  n matr x.

  use t   nterested n embedd ngs to capture consu r's long-term  nterest. T   nterested n embedd ngs  s one of   major s ce for consu r-based t et recom ndat ons.

### Producer Embedd ngs
W n comput ng t  Known For matr x, each producer can only be Known For a s ngle commun y. Although t  max mally sparse matr x  s useful from a computat onal perspect ve,   know that   users t et about many d fferent top cs and may be "Known"  n many d fferent commun  es. Producer embedd ngs ( *Ṽ* )  are used to capture t  r c r structure of t  graph.

To calculate producer embedd ngs, t  cos ne s m lar y  s calculated bet en each Producer’s follow graph and t   nterested  n vector for each commun y.

< mg src=" mages/producer_embedd ngs.png">

Producer embedd ngs are used for producer-based t et recom ndat ons. For example,   can recom nd s m lar t ets based on an account   just follo d.

### Ent y Embedd ngs
S mClusters can also be used to generate embedd ngs for d fferent k nd of contents, such as
- T ets (used for T et recom ndat ons)
- Top cs (used for Top cFollow)

#### T et embedd ngs
W n a t et  s created,  s t et embedd ng  s  n  al zed as an empty vector.
T et embedd ngs are updated each t   t  t et  s favor ed. Spec f cally, t   nterested n vector of each user who Fav-ed t  t et  s added to t  t et vector.
S nce t et embedd ngs are updated each t   a t et  s favor ed, t y change over t  .

T et embedd ngs are cr  cal for   t et recom ndat on tasks.   can calculate t et s m lar y and recom nd s m lar t ets to users based on t  r t et engage nt  tory.

  have a onl ne  ron job that updates t  t et embedd ngs  n realt  , c ck out [ re](summ ngb rd/README.md) for more. 

#### Top c embedd ngs
Top c embedd ngs (**R**) are determ ned by tak ng t  cos ne s m lar y bet en consu rs who are  nterested  n a commun y and t  number of aggregated favor es each consu r has taken on a t et that has a top c annotat on (w h so  t   decay).

< mg src=" mages/top c_embedd ngs.png">


## Project D rectory Overv ew
T  whole S mClusters project can be understood as 2 ma n components
- S mClusters Offl ne Jobs (Scald ng / GCP)
- S mClusters Real-t   Stream ng Jobs 

### S mClusters Offl ne Jobs

**S mClusters Scald ng Jobs**

| Jobs   | Code  | Descr pt on  |
|---|---|---|
| KnownFor  |  [s mclusters_v2/scald ng/update_known_for/UpdateKnownFor20M145K2020.scala](scald ng/update_known_for/UpdateKnownFor20M145K2020.scala) | T  job outputs t  KnownFor dataset wh ch stores t  relat onsh ps bet en  cluster d and producerUser d. </n> KnownFor dataset covers t  top 20M follo d producers.   use t  KnownFor dataset (or so-called clusters) to bu ld all ot r ent y embedd ngs. |
|  nterested n Embedd ngs|  [s mclusters_v2/scald ng/ nterested nFromKnownFor.scala](scald ng/ nterested nFromKnownFor.scala) |  T  code  mple nts t  job for comput ng users'  nterested n embedd ng from t   KnownFor dataset. </n>   use t  dataset for consu r-based t et recom ndat ons.|
| Producer Embedd ngs  | [s mclusters_v2/scald ng/embedd ng/ProducerEmbedd ngsFrom nterested n.scala](scald ng/embedd ng/ProducerEmbedd ngsFrom nterested n.scala)  |  T  code  mple nts t  job for computer producer embedd ngs, wh ch represents t  content user produces. </n>   use t  dataset for producer-based t et recom ndat ons.|
| Semant c Core Ent y Embedd ngs  | [s mclusters_v2/scald ng/embedd ng/Ent yToS mClustersEmbedd ngsJob.scala](scald ng/embedd ng/Ent yToS mClustersEmbedd ngsJob.scala)   | T  job computes t  semant c core ent y embedd ngs.   outputs datasets that stores t   "Semant cCore ent y d -> L st(cluster d)" and "cluster d -> L st(Semant cCore ent y d))" relat onsh ps.|
| Top c Embedd ngs | [s mclusters_v2/scald ng/embedd ng/tfg/FavTfgBasedTop cEmbedd ngs.scala](scald ng/embedd ng/tfg/FavTfgBasedTop cEmbedd ngs.scala)  | Jobs to generate Fav-based Top c-Follow-Graph (TFG) top c embedd ngs </n> A top c's fav-based TFG embedd ng  s t  sum of  s follo rs' fav-based  nterested n.   use t  embedd ng for top c related recom ndat ons.|

**S mClusters GCP Jobs**

  have a GCP p pel ne w re   bu ld   S mClusters ANN  ndex v a B gQuery. T  allows us to do fast  erat ons and bu ld new embedd ngs more eff c ently compared to Scald ng.

All S mClusters related GCP jobs are under [src/scala/com/tw ter/s mclusters_v2/sc o/bq_generat on](sc o/bq_generat on).

| Jobs   | Code  | Descr pt on  |
|---|---|---|
| PushOpenBased S mClusters ANN  ndex  |  [Engage ntEventBasedClusterToT et ndexGenerat onJob.scala](sc o/bq_generat on/s mclusters_ ndex_generat on/Engage ntEventBasedClusterToT et ndexGenerat onJob.scala) | T  job bu lds a cluster d -> TopT et  ndex based on user-open engage nt  tory. </n> T  SANN s ce  s used for cand date generat on for Not f cat ons. |
| V deoV ewBased S mClusters  ndex|  [Engage ntEventBasedClusterToT et ndexGenerat onJob.scala](sc o/bq_generat on/s mclusters_ ndex_generat on/Engage ntEventBasedClusterToT et ndexGenerat onJob.scala) |  T  job bu lds a cluster d -> TopT et  ndex based on t  user's v deo v ew  tory. </n> T  SANN s ce  s used for v deo recom ndat on on Ho .|

### S mClusters Real-T   Stream ng T ets Jobs

| Jobs   | Code  | Descr pt on  |
|---|---|---|
| T et Embedd ng Job |  [s mclusters_v2/summ ngb rd/storm/T etJob.scala](summ ngb rd/storm/T etJob.scala) | Generate t  T et embedd ng and  ndex of t ets for t  S mClusters |
| Pers stent T et Embedd ng Job|  [s mclusters_v2/summ ngb rd/storm/Pers stentT etJob.scala](summ ngb rd/storm/Pers stentT etJob.scala) |  Pers stent t  t et embedd ngs from  mCac   nto Manhattan.|