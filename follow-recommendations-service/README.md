# Follow Recom ndat ons Serv ce

##  ntroduct on to t  Follow Recom ndat ons Serv ce (FRS)
T  Follow Recom ndat ons Serv ce (FRS)  s a robust recom ndat on eng ne des gned to prov de users w h personal zed suggest ons for accounts to follow. At present, FRS supports Who-To-Follow (WTF) module recom ndat ons across a var ety of Tw ter product  nterfaces. Add  onally, by suggest ng t et authors, FRS also del vers FutureGraph t et recom ndat ons, wh ch cons st of t ets from accounts that users may be  nterested  n follow ng  n t  future.

## Des gn
T  system  s ta lored to accommodate d verse use cases, such as Post New-User-Exper ence (NUX), advert se nts, FutureGraph t ets, and more. Each use case features a un que d splay locat on  dent f er. To v ew all d splay locat ons, refer to t  follow ng path: `follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/models/D splayLocat on.scala`.

Recom ndat on steps are custom zed accord ng to each d splay locat on. Common and h gh-level steps are encapsulated w h n t  "Recom ndat onFlow," wh ch  ncludes operat ons l ke cand date generat on, ranker select on, f lter ng, transformat on, and beyond. To explore all flows, refer to t  path: `follow-recom ndat ons-serv ce/server/src/ma n/scala/com/tw ter/follow_recom ndat ons/flows`.

For each product (correspond ng to a d splay locat on), one or mult ple flows can be selected to generate cand dates based on code and conf gurat ons. To v ew all products, refer to t  follow ng path: `follow-recom ndat ons-serv ce/server/src/ma n/scala/com/tw ter/follow_recom ndat ons/products/ho _t  l ne_t et_recs`.

T  FRS overv ew d agram  s dep cted below:

![FRS_arch ecture.png](FRS_arch ecture.png)


### Cand date Generat on
Dur ng t  step, FRS ut l zes var ous user s gnals and algor hms to  dent fy cand dates from all Tw ter accounts. T  cand date s ce folder  s located at `follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/cand date_s ces/`, w h a README f le prov ded w h n each cand date s ce folder.

### F lter ng
 n t  phase, FRS appl es d fferent f lter ng log c after generat ng account cand dates to  mprove qual y and  alth. F lter ng may occur before and/or after t  rank ng step, w h  av er f lter ng log c (e.g., h g r latency) typ cally appl ed after t  rank ng step. T  f lters' folder  s located at `follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/pred cates`.

### Rank ng
Dur ng t  step, FRS employs both Mach ne Learn ng (ML) and  ur st c rule-based cand date rank ng. For t  ML ranker, ML features are fetc d beforehand ( .e., feature hydrat on),
and a DataRecord (t  Tw ter-standard Mach ne Learn ng data format used to represent feature data, labels, and pred ct ons w n tra n ng or serv ng)  s constructed for each <user, cand date> pa r. 
T se pa rs are t n sent to a separate ML pred ct on serv ce, wh ch houses t  ML model tra ned offl ne.
T  ML pred ct on serv ce returns a pred ct on score, represent ng t  probab l y that a user w ll follow and engage w h t  cand date.
T  score  s a   ghted sum of p(follow|recom ndat on) and p(pos  ve engage nt|follow), and FRS uses t  score to rank t  cand dates.

T  rankers' folder  s located at `follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/rankers`.

### Transform
 n t  phase, t  sequence of cand dates undergoes necessary transformat ons, such as dedupl cat on, attach ng soc al proof ( .e., "follo d by XX user"), add ng track ng tokens, and more.
T  transfor rs' folder can be found at `follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/transforms`.

### Truncat on
Dur ng t  f nal step, FRS tr ms t  cand date pool to a spec f ed s ze. T  process ensures that only t  most relevant and engag ng cand dates are presented to users wh le ma nta n ng an opt mal user exper ence.

By  mple nt ng t se compre ns ve steps and adapt ng to var ous use cases, t  Follow Recom ndat ons Serv ce (FRS) effect vely curates ta lored suggest ons for Tw ter users, enhanc ng t  r overall exper ence and promot ng  an ngful connect ons w h n t  platform.
