# Tw ter's Recom ndat on Algor hm

Tw ter's Recom ndat on Algor hm  s a set of serv ces and jobs that are respons ble for serv ng feeds of T ets and ot r content across all Tw ter product surfaces (e.g. For   T  l ne, Search, Explore, Not f cat ons). For an  ntroduct on to how t  algor hm works, please refer to   [eng neer ng blog](https://blog.tw ter.com/eng neer ng/en_us/top cs/open-s ce/2023/tw ter-recom ndat on-algor hm).

## Arch ecture

Product surfaces at Tw ter are bu lt on a shared set of data, models, and software fra works. T  shared components  ncluded  n t  repos ory are l sted below:

| Type | Component | Descr pt on |
|------------|------------|------------|
| Data | [t etyp e](t etyp e/server/README.md) | Core T et serv ce that handles t  read ng and wr  ng of T et data. |
|      | [un f ed-user-act ons](un f ed_user_act ons/README.md) | Real-t   stream of user act ons on Tw ter. |
|      | [user-s gnal-serv ce](user-s gnal-serv ce/README.md) | Central zed platform to retr eve expl c  (e.g. l kes, repl es) and  mpl c  (e.g. prof le v s s, t et cl cks) user s gnals. |
| Model | [S mClusters](src/scala/com/tw ter/s mclusters_v2/README.md) | Commun y detect on and sparse embedd ngs  nto those commun  es. |
|       | [TwH N](https://g hub.com/tw ter/t -algor hm-ml/blob/ma n/projects/twh n/README.md) | Dense knowledge graph embedd ngs for Users and T ets. |
|       | [trust-and-safety-models](trust_and_safety_models/README.md) | Models for detect ng NSFW or abus ve content. |
|       | [real-graph](src/scala/com/tw ter/ nteract on_graph/README.md) | Model to pred ct t  l kel hood of a Tw ter User  nteract ng w h anot r User. |
|       | [t epcred](src/scala/com/tw ter/graph/batch/job/t epcred/README) | Page-Rank algor hm for calculat ng Tw ter User reputat on. |
|       | [recos- njector](recos- njector/README.md) | Stream ng event processor for bu ld ng  nput streams for [GraphJet](https://g hub.com/tw ter/GraphJet) based serv ces. |
|       | [graph-feature-serv ce](graph-feature-serv ce/README.md) | Serves graph features for a d rected pa r of Users (e.g. how many of User A's follow ng l ked T ets from User B). |
|       | [top c-soc al-proof](top c-soc al-proof/README.md) |  dent f es top cs related to  nd v dual T ets. |
|       | [representat on-scorer](representat on-scorer/README.md) | Compute scores bet en pa rs of ent  es (Users, T ets, etc.) us ng embedd ng s m lar y. |
| Software fra work | [nav ](nav /README.md) | H gh performance, mach ne learn ng model serv ng wr ten  n Rust. |
|                    | [product-m xer](product-m xer/README.md) | Software fra work for bu ld ng feeds of content. |
|                    | [t  l nes-aggregat on-fra work](t  l nes/data_process ng/ml_ut l/aggregat on_fra work/README.md) | Fra work for generat ng aggregate features  n batch or real t  . |
|                    | [representat on-manager](representat on-manager/README.md) | Serv ce to retr eve embedd ngs ( .e. S mClusers and TwH N). |
|                    | [twml](twml/README.md) | Legacy mach ne learn ng fra work bu lt on TensorFlow v1. |

T  product surfaces currently  ncluded  n t  repos ory are t  For   T  l ne and Recom nded Not f cat ons.

### For   T  l ne

T  d agram below  llustrates how major serv ces and jobs  nterconnect to construct a For   T  l ne.

![](docs/system-d agram.png)

T  core components of t  For   T  l ne  ncluded  n t  repos ory are l sted below:

| Type | Component | Descr pt on |
|------------|------------|------------|
| Cand date S ce | [search- ndex](src/java/com/tw ter/search/README.md) | F nd and rank  n-Network T ets. ~50% of T ets co  from t  cand date s ce. |
|                  | [cr-m xer](cr-m xer/README.md) | Coord nat on layer for fetch ng Out-of-Network t et cand dates from underly ng compute serv ces. |
|                  | [user-t et-ent y-graph](src/scala/com/tw ter/recos/user_t et_ent y_graph/README.md) (UTEG)| Ma nta ns an  n  mory User to T et  nteract on graph, and f nds cand dates based on traversals of t  graph. T   s bu lt on t  [GraphJet](https://g hub.com/tw ter/GraphJet) fra work. Several ot r GraphJet based features and cand date s ces are located [ re](src/scala/com/tw ter/recos). |
|                  | [follow-recom ndat on-serv ce](follow-recom ndat ons-serv ce/README.md) (FRS)| Prov des Users w h recom ndat ons for accounts to follow, and T ets from those accounts. |
| Rank ng | [l ght-ranker](src/python/tw ter/deepb rd/projects/t  l nes/scr pts/models/earlyb rd/README.md) | L ght Ranker model used by search  ndex (Earlyb rd) to rank T ets. |
|         | [ avy-ranker](https://g hub.com/tw ter/t -algor hm-ml/blob/ma n/projects/ho /recap/README.md) | Neural network for rank ng cand date t ets. One of t  ma n s gnals used to select t  l ne T ets post cand date s c ng. |
| T et m x ng & f lter ng | [ho -m xer](ho -m xer/README.md) | Ma n serv ce used to construct and serve t  Ho  T  l ne. Bu lt on [product-m xer](product-m xer/README.md). |
|                          | [v s b l y-f lters](v s b l yl b/README.md) | Respons ble for f lter ng Tw ter content to support legal compl ance,  mprove product qual y,  ncrease user trust, protect revenue through t  use of hard-f lter ng, v s ble product treat nts, and coarse-gra ned downrank ng. |
|                          | [t  l neranker](t  l neranker/README.md) | Legacy serv ce wh ch prov des relevance-scored t ets from t  Earlyb rd Search  ndex and UTEG serv ce. |

### Recom nded Not f cat ons

T  core components of Recom nded Not f cat ons  ncluded  n t  repos ory are l sted below:

| Type | Component | Descr pt on |
|------------|------------|------------|
| Serv ce | [pushserv ce](pushserv ce/README.md) | Ma n recom ndat on serv ce at Tw ter used to surface recom ndat ons to   users v a not f cat ons.
| Rank ng | [pushserv ce-l ght-ranker](pushserv ce/src/ma n/python/models/l ght_rank ng/README.md) | L ght Ranker model used by pushserv ce to rank T ets. Br dges cand date generat on and  avy rank ng by pre-select ng h ghly-relevant cand dates from t   n  al huge cand date pool. |
|         | [pushserv ce- avy-ranker](pushserv ce/src/ma n/python/models/ avy_rank ng/README.md) | Mult -task learn ng model to pred ct t  probab l  es that t  target users w ll open and engage w h t  sent not f cat ons. |

## Bu ld and test code

   nclude Bazel BU LD f les for most components, but not a top-level BU LD or WORKSPACE f le.   plan to add a more complete bu ld and test system  n t  future.

## Contr but ng

   nv e t  commun y to subm  G Hub  ssues and pull requests for suggest ons on  mprov ng t  recom ndat on algor hm.   are work ng on tools to manage t se suggest ons and sync changes to    nternal repos ory. Any secur y concerns or  ssues should be routed to   off c al [bug bounty program](https://hackerone.com/tw ter) through HackerOne.   hope to benef  from t  collect ve  ntell gence and expert se of t  global commun y  n  lp ng us  dent fy  ssues and suggest  mprove nts, ult mately lead ng to a better Tw ter.

Read   blog on t  open s ce  n  at ve [ re](https://blog.tw ter.com/en_us/top cs/company/2023/a-new-era-of-transparency-for-tw ter).
