# Search  ndex (Earlyb rd) core classes 

> **TL;DR** Earlyb rd (Search  ndex) f nd t ets from people   follow, rank t m, and serve t ets to Ho .

## What  s Earlyb rd (Search  ndex)

[Earlyb rd](http://notes.step nhol day.com/Earlyb rd.pdf)  s a **real-t   search system** based on [Apac  Lucene](https://lucene.apac .org/) to support t  h gh volu  of quer es and content updates. T  major use cases are Relevance Search (spec f cally, Text search) and T  l ne  n-network T et retr eval (or User D based search).    s des gned to enable t  eff c ent  ndex ng and query ng of b ll ons of t ets, and to prov de low-latency search results, even w h  avy query loads. 

## D rectory Structure
T  project cons sts of several packages and f les, wh ch can be summar zed as follows:


* `facets/`: T  subd rectory conta ns classes respons ble for facet count ng and process ng. So  key classes  nclude Earlyb rdFacets, Earlyb rdFacetsFactory, FacetAccumulator, and FacetCountAggregator. T  classes handle facet count ng, facet  erators, facet label prov ders, and facet response rewr  ng.
* ` ndex/`: T  d rectory conta ns t   ndex ng and search  nfra f les, w h several subd rector es for spec f c components.
  * `column/`: T  subd rectory conta ns classes related to column-str de f eld  ndexes,  nclud ng ColumnStr deByte ndex, ColumnStr de nt ndex, ColumnStr deLong ndex, and var ous opt m zed vers ons of t se  ndexes. T se classes deal w h manag ng and updat ng doc values.
  * `extens ons/`: T  subd rectory conta ns classes for  ndex extens ons,  nclud ng Earlyb rd ndexExtens onsData, Earlyb rd ndexExtens onsFactory, and Earlyb rdRealt   ndexExtens onsData.
  * ` nverted/`: T  subd rectory focuses on t   nverted  ndex and  s components, such as  n moryF elds,  ndexOpt m zer,  nverted ndex, and  nvertedRealt   ndex.   also conta ns classes for manag ng and process ng post ng l sts and term d ct onar es, l ke Earlyb rdPost ngsEnum, FSTTermD ct onary, and MPHTermD ct onary.
  * `ut l/`: T  subd rectory conta ns ut l y classes for manag ng search  erators and f lters, such as AllDocs erator, RangeD S , RangeF lterD S , and SearchSortUt ls. T  system appears to be des gned to handle search  ndex ng and facet count ng eff c ently. Key components  nclude an  nverted  ndex, var ous types of post ng l sts, and term d ct onar es. Facet count ng and process ng  s handled by spec al zed classes w h n t  facets subd rectory. T  overall structure  nd cates a  ll-organ zed and modular search  ndex ng system that can be ma nta ned and extended as needed.

## Related Serv ces
* T  Earlyb rds ma n classes. See `src/java/com/tw ter/search/earlyb rd/`
