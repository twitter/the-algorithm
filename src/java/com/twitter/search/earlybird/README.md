# Search  ndex (Earlyb rd) ma n classes

> **TL;DR** Earlyb rd (Search  ndex) f nd t ets from people   follow, rank t m, and serve t m to Ho .

## What  s Earlyb rd (Search  ndex)

[Earlyb rd](http://notes.step nhol day.com/Earlyb rd.pdf)  s a **real-t   search system** based on [Apac  Lucene](https://lucene.apac .org/) to support t  h gh volu  of quer es and content updates. T  major use cases are Relevance Search (spec f cally, Text search) and T  l ne  n-network T et retr eval (or User D based search).    s des gned to enable t  eff c ent  ndex ng and query ng of b ll ons of t ets, and to prov de low-latency search results, even w h  avy query loads.

## H gh-level arch ecture
  spl    ent re t et search  ndex  nto three clusters: a **realt  ** cluster  ndex ng all publ c t ets posted  n about t  last 7 days, a **protected** cluster  ndex ng all protected t ets for t  sa  t  fra ; and an **arch ve** cluster  ndex ng all t ets ever posted, up to about two days ago.

Earlyb rd addresses t  challenges of scal ng real-t   search by spl t ng each cluster across mult ple **part  ons**, each respons ble for a port on of t   ndex. T  arch ecture uses a d str buted * nverted  ndex* that  s sharded and repl cated. T  des gn allows for eff c ent  ndex updates and query process ng.

T  system also employs an  ncre ntal  ndex ng approach, enabl ng   to process and  ndex new t ets  n real-t   as t y arr ve. W h s ngle wr er, mult ple reader structure, Earlyb rd can handle a large number of real-t   updates and quer es concurrently wh le ma nta n ng low query latency. T  system can ach eve h gh query throughput and low query latency wh le ma nta n ng a h gh degree of  ndex freshness.

## Ma n Components 

**Part  on Manager**: Respons ble for manag ng t  conf gurat on of part  ons, as  ll as t  mapp ng bet en users and part  ons.   also handles  ndex load ng and flush ng.

**Real-t    ndexer**: Cont nuously reads from a kafka stream of  ncom ng t ets and updates t   ndex (t et creat on, t et updates, user updates).   also supports t et delet on events.

**Query Eng ne**: Handles t  execut on of search quer es aga nst t  d str buted  ndex.   employs var ous opt m zat on techn ques, such as term-based prun ng and cach ng.

**Docu nt Preprocessor**: Converts raw t ets  nto a docu nt representat on su able for  ndex ng.   handles token zat on, normal zat on, and analys s of t et text and  tadata. See    ngest on p pel ne `src/java/com/tw ter/search/ ngester` for more wr e-path process ng.

** ndex Wr er**: Wr es t et docu nts to t   ndex and ma nta ns t   ndex structure,  nclud ng **post ng l sts** and **term d ct onar es**.

**Seg nt Manager**: Manages  ndex seg nts w h n a part  on.    s respons ble for  rg ng, opt m z ng, and flush ng  ndex seg nts to d sk, or flush to HDFS to snapshot l ve seg nts.

**Searc r**: Executes quer es aga nst t   ndex, us ng techn ques l ke cach ng and parallel query execut on to m n m ze query latency.   also  ncorporates scor ng models and rank ng algor hms to prov de relevant search results.

T  most  mportant two data structures for Earlyb rd (or  nformat on Retr eval  n general)  nclud ng:

* ** nverted  ndex** wh ch stores a mapp ng bet en a Term to a l st of Doc  Ds. Essent ally,   bu ld a hash map: each key  n t  map  s a d st nct Term (e.g., `cat`, `dog`)  n a t et, and each value  s t  l st of t ets (aka., Docu nt)  n wh ch t  word appears.   keep one  nverted  ndex per f eld (text, User D, user na , l nks, etc.)
* **Post ngs L st** wh ch opt m ze t  storage a t  l st of Doc  Ds  nt oned above.

See more at: https://blog.tw ter.com/eng neer ng/en_us/top cs/ nfrastructure/2016/omn search- ndex-formats

## Advanced features

Earlyb rd  ncorporates several advanced features such as facet search, wh ch allows users to ref ne search results based on spec f c attr butes such as user  nt ons, hashtags, and URLs. Furt rmore, t  system supports var ous rank ng models,  nclud ng mach ne learn ng-based scor ng models, to prov de relevant search results.

## D rectory Structure
T  project cons sts of several packages and f les, wh ch can be summar zed as follows:

* At t  root level, t  pr mary focus  s on t  Earlyb rd server  mple ntat on and  s assoc ated classes. T se  nclude classes for search, CPU qual y factors, server manage nt,  ndex conf g, ma n classes, server startup, etc.
* `arch ve/`: D rectory deals w h t  manage nt and conf gurat on of arch ved data, spec f cally for Earlyb rd  ndex Conf gurat ons.   also conta ns a `seg ntbu lder/` subd rectory, wh ch  ncludes classes for bu ld ng and updat ng arch ve  ndex seg nts.
* `common/`: D rectory holds ut l y classes for logg ng, handl ng requests, and Thr ft backend funct onal y.   also has two subd rector es: `conf g/` for Earlyb rd conf gurat on and `userupdates/` for user-related data handl ng.
* `conf g/`: D rectory  s ded cated to manag ng t er conf gurat ons spec f cally for arch ve cluster, wh ch relate to server and search query d str but on.
* `docu nt/`: Handles docu nt creat on and process ng,  nclud ng var ous factor es and token stream wr ers.
* `except on/`: Conta ns custom except ons and except on handl ng classes related to t  system.
* `factory/`: Prov des ut l  es and factor es for conf gurat ons, Kafka consu rs, and server  nstances.
* ` ndex/`: Conta ns  ndex-related classes,  nclud ng  n- mory t   mappers, t et  D mappers, and facets.
* `ml/`: Houses t  `Scor ngModelsManager` for manag ng mach ne learn ng models.
* `part  on/`: Manages part  ons and  ndex seg nts,  nclud ng  ndex loaders, seg nt wr ers, and startup  ndexers.
* `querycac /`:  mple nts cach ng for quer es and query results,  nclud ng cac  conf gurat on and update tasks.
* `queryparser/`: Prov des query pars ng funct onal y,  nclud ng f les that cover query rewr ers and lh gh-frequency term extract on.
* `search/`: Conta ns read path related classes, such as search request process ng, result collectors, and facet collectors.
* `seg nt/`: Prov des classes for manag ng seg nt data prov ders and data reader sets.
* `stats/`: Conta ns classes for track ng and report ng stat st cs related to t  system.
* `tools/`: Houses ut l y classes for deser al z ng thr ft requests.
* `ut l/`:  ncludes ut l y classes for var ous tasks, such as act on logg ng, sc duled tasks, and JSON v e rs.

## Related Serv ces

* T  Earlyb rds s  beh nd Earlyb rd Root servers that fan out quer es to t m. See `src/java/com/tw ter/search/earlyb rd_root/`
* T  Earlyb rds are po red by mult ple  ngest on p pel nes. See `src/java/com/tw ter/search/ ngester/`
* Earlyb rd seg nts for t  Arch ves are bu lt offl ne by seg nt bu lders
* Also, Earlyb rd l ght rank ng  s def ned  n `t  l nes/data_process ng/ad_hoc/earlyb rd_rank ng`
 and `src/python/tw ter/deepb rd/projects/t  l nes/scr pts/models/earlyb rd`.
* Search common l brary/packages

## References

See more: 

* "Earlyb rd: Real-T   Search at Tw ter" (http://notes.step nhol day.com/Earlyb rd.pdf)
* "Reduc ng search  ndex ng latency to one second" (https://blog.tw ter.com/eng neer ng/en_us/top cs/ nfrastructure/2020/reduc ng-search- ndex ng-latency-to-one-second)
* "Omn search  ndex formats" (https://blog.tw ter.com/eng neer ng/en_us/top cs/ nfrastructure/2016/omn search- ndex-formats)




