# T etyp e

## Overv ew

T etyp e  s t  core T et serv ce that handles t  read ng and wr  ng of T et data.    s called by t  Tw ter cl ents (through GraphQL), as  ll as var ous  nternal Tw ter serv ces, to fetch, create, delete, and ed  T ets. T etyp e calls several backends to hydrate T et related data to return to callers.

## How   Works

T  next sect ons descr be t  layers  nvolved  n t  read and create paths for T ets.

### Read Path

 n t  read path, T etyp e fetc s t  T et data from [Manhattan](https://blog.tw ter.com/eng neer ng/en_us/a/2014/manhattan- -real-t  -mult -tenant-d str buted-database-for-tw ter-scale) or [T mcac ](https://blog.tw ter.com/eng neer ng/en_us/a/2012/cach ng-w h-t mcac ), and hydrates data about t  T et from var ous ot r backend serv ces.

#### Relevant Packages

- [backends](src/ma n/scala/com/tw ter/t etyp e/backends/): A "backend"  s a wrapper around a thr ft serv ce that T etyp e calls. For example [Talon.scala](src/ma n/scala/com/tw ter/t etyp e/backends/Talon.scala)  s t  backend for Talon, t  URL shortener.
- [repos ory](src/ma n/scala/com/tw ter/t etyp e/repos ory/): A "repos ory" wraps a backend and prov des a structured  nterface for retr ev ng data from t  backend. [UrlRepos ory.scala](src/ma n/scala/com/tw ter/t etyp e/repos ory/UrlRepos ory.scala)  s t  repos ory for t  Talon backend.
- [hydrator](src/ma n/scala/com/tw ter/t etyp e/hydrator/): T etyp e doesn't store all t  data assoc ated w h T ets. For example,   doesn't store User objects, but   stores screenna s  n t  T et text (as  nt ons).   stores  d a  Ds, but   doesn't store t   d a  tadata. Hydrators take t  raw T et data from Manhattan or Cac  and return   w h so  add  onal  nformat on, along w h hydrat on  tadata that says w t r t  hydrat on took place. T   nformat on  s usually fetc d us ng a repos ory. For example, dur ng t  hydrat on process, t  [UrlEnt yHydrator](src/ma n/scala/com/tw ter/t etyp e/hydrator/UrlEnt yHydrator.scala) calls Talon us ng t  [UrlRepos ory](src/ma n/scala/com/tw ter/t etyp e/repos ory/UrlRepos ory.scala) and fetc s t  expanded URLs for t  t.co l nks  n t  T et.
- [handler](src/ma n/scala/com/tw ter/t etyp e/handler/): A handler  s a funct on that handles requests to one of t  T etyp e endpo nts. T  [GetT etsHandler](src/ma n/scala/com/tw ter/t etyp e/handler/GetT etsHandler.scala) handles requests to `get_t ets`, one of t  endpo nts used to fetch T ets.

#### Through t  Read Path

At a h gh level, t  path a `get_t ets` request takes  s as follows.

- T  request  s handled by [GetT etsHandler](src/ma n/scala/com/tw ter/t etyp e/handler/GetT etsHandler.scala).
- GetT etsHandler uses t  T etResultRepos ory (def ned  n [Log calRepos or es.scala](src/ma n/scala/com/tw ter/t etyp e/conf g/Log calRepos or es#L301)). T  T etResultRepos ory has at  s core a [ManhattanT etRespos ory](src/ma n/scala/com/tw ter/t etyp e/repos ory/ManhattanT etRepos ory.scala) (that fetc s t  T et data from Manhattan), wrapped  n a [Cach ngT etRepos ory](src/ma n/scala/com/tw ter/t etyp e/repos ory/ManhattanT etRepos ory.scala) (that appl es cach ng us ng T mcac ). F nally, t  cach ng repos ory  s wrapped  n a hydrat on layer (prov ded by [T etHydrat on.hydrateRepo](src/ma n/scala/com/tw ter/t etyp e/hydrator/T etHydrat on.scala#L789)). Essent ally, t  T etResultRepos ory fetc s t  T et data from cac  or Manhattan, and passes   through t  hydrat on p pel ne.
- T  hydrat on p pel ne  s descr bed  n [T etHydrat on.scala](src/ma n/scala/com/tw ter/t etyp e/hydrator/T etHydrat on.scala), w re all t  hydrators are comb ned toget r.

### Wr e Path

T  wr e path follows d fferent patterns to t  read path, but reuses so  of t  code.

#### Relevant Packages

- [store](src/ma n/scala/com/tw ter/t etyp e/store/): T  store package  ncludes t  code for updat ng backends on wr e, and t  coord nat on code for descr b ng wh ch backends need to be updated for wh ch endpo nts. T re are two types of f le  n t  package: stores and store modules. F les that end  n Store are stores and def ne t  log c for updat ng a backend, for example [ManhattanT etStore](src/ma n/scala/com/tw ter/t etyp e/store/ManhattanT etStore.scala) wr es T ets to Manhattan. Most of t  f les that don't end  n Store are store modules and def ne t  log c for handl ng a wr e endpo nt, and descr be wh ch stores are called, for example [ nsertT et](src/ma n/scala/com/tw ter/t etyp e/store/ nsertT et.scala) wh ch handles t  `post_t et` endpo nt. Modules def ne wh ch stores t y call, and stores def ne wh ch modules t y handle.

#### Through t  Wr e Path

T  path a `post_t et` request takes  s as follows.

- T  request  s handled  n [PostT et.scala](src/ma n/scala/com/tw ter/t etyp e/handler/PostT et.scala#L338).
- [T etBu lder](src/ma n/scala/com/tw ter/t etyp e/handler/T etBu lder.scala) creates a T et from t  request, after perform ng text process ng, val dat on, URL shorten ng,  d a process ng, c ck ng for dupl cates etc.
- [Wr ePathHydrat on.hydrate nsertT et](src/ma n/scala/com/tw ter/t etyp e/conf g/Wr ePathHydrat on.scala#L54) passes t  T et through t  hydrat on p pel ne to return t  caller.
- T  T et data  s wr ten to var ous stores as descr bed  n [ nsertT et.scala](src/ma n/scala/com/tw ter/t etyp e/store/ nsertT et.scala#L84).
