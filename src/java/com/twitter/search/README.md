# T et Search System (Earlyb rd)
> **TL;DR** T et Search System (Earlyb rd) f nd t ets from people   follow, rank t m, and serve t  t ets to Ho .

## What  s T et Search System (Earlyb rd)? 
[Earlyb rd](http://notes.step nhol day.com/Earlyb rd.pdf)  s a **real-t   search system** based on [Apac  Lucene](https://lucene.apac .org/) to support t  h gh volu  of quer es and content updates. T  major use cases are Relevance Search (spec f cally, Text search) and T  l ne  n-network T et retr eval (or User D based search).    s des gned to enable t  eff c ent  ndex ng and query ng of b ll ons of t ets, and to prov de low-latency search results, even w h  avy query loads.

## How    s related to t  Ho  T  l ne Recom ndat on Algor hm

![ n-network]( mg/ n-network.png)

At Tw ter,   use T et Search System (Earlyb rd) to do Ho  T  l ne  n-network T et retr eval: g ven a l st of follow ng users, f nd t  r recently posted t ets. Earlyb rd (Search  ndex)  s t  major cand date s ce for  n-network t ets across Follow ng tab and For   tab.


## H gh-level arch ecture
  spl    ent re t et search  ndex  nto three clusters: a **realt  ** cluster  ndex ng all publ c t ets posted  n about t  last 7 days, a **protected** cluster  ndex ng all protected t ets for t  sa  t  fra ; and an **arch ve** cluster  ndex ng all t ets ever posted, up to about two days ago. 

Earlyb rd addresses t  challenges of scal ng real-t   search by spl t ng each cluster across mult ple **part  ons**, each respons ble for a port on of t   ndex. T  arch ecture uses a d str buted * nverted  ndex* that  s sharded and repl cated. T  des gn allows for eff c ent  ndex updates and query process ng. 

T  system also employs an  ncre ntal  ndex ng approach, enabl ng   to process and  ndex new t ets  n real-t   as t y arr ve. W h s ngle wr er, mult ple reader structure, Earlyb rd can handle a large number of real-t   updates and quer es concurrently wh le ma nta n ng low query latency. T  system can ach eve h gh query throughput and low query latency wh le ma nta n ng a h gh degree of  ndex freshness. 


###  ndex ng 
*  ngesters read t ets and user mod f cat ons from kafka top cs, extract f elds and features from t m and wr e t  extracted data to  nter d ate kafka top cs for Earlyb rds to consu ,  ndex and serve.
* Feature Update Serv ce feeds feature updates such as up-to-date engage nt (l ke, ret ets, repl es) counts to Earlyb rd.
![ ndex ng]( mg/ ndex ng.png)

### Serv ng
Earlyb rd roots fanout requests to d fferent Earlyb rd clusters or part  ons. Upon rece v ng responses from t  clusters or part  ons, roots  rge t  responses before f nally return ng t   rged response to t  cl ent. 
![serv ng]( mg/serv ng.png)

## Use cases

1. T et Search
  * Top search
  * Latest search

![top]( mg/top-search.png)

2. Cand date generat on
  * T  l ne (For   Tab, Follow ng Tab)
  * Not f cat ons

![ho ]( mg/for .png)

## References
* "Earlyb rd: Real-T   Search at Tw ter" (http://notes.step nhol day.com/Earlyb rd.pdf)
* "Reduc ng search  ndex ng latency to one second" (https://blog.tw ter.com/eng neer ng/en_us/top cs/ nfrastructure/2020/reduc ng-search- ndex ng-latency-to-one-second)
* "Omn search  ndex formats" (https://blog.tw ter.com/eng neer ng/en_us/top cs/ nfrastructure/2016/omn search- ndex-formats)


