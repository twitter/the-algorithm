.. _real_t  :

Real-T   aggregate features
============================

 n add  on to comput ng batch aggregate features, t  aggregat on fra work supports real-t   aggregates as  ll. T  fra work concepts used  re are  dent cal to t  batch use case, ho ver, t  underly ng  mple ntat on d ffers and  s prov ded by summ ngb rd-storm jobs.

RTA Runbook
-----------

For operat onal deta ls, please v s  http://go/tqrealt  aggregates.

Prerequ s es
-------------

 n order to start comput ng real-t   aggregate features, t  fra work requ res t  follow ng to be prov ded:

* A back ng  mcac d store that w ll hold t  computed aggregate features. T   s conceptually equ valent to t  output HDFS store  n t  batch compute case.
*  mple ntat on of `StormAggregateS ce <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ml_ut l/aggregat on_fra work/ ron/StormAggregateS ce.scala#n15>`_ that creates `DataRecords` w h t  necessary  nput features. T  serves as t   nput to t  aggregat on operat ons.
* Def n  on of aggregate features by def n ng `AggregateGroup`  n an  mple ntat on of `Onl neAggregat onConf gTra `. T   s  dent cal to t  batch case.
* Job conf g f le def n ng t  back ng  mcac d for feature storage and retr eval, and job-related para ters.

  w ll now go through t  deta ls  n sett ng up each requ red component.

 mcac d store
---------------

Real-t   aggregates use  mcac  as t  back ng cac  to store and update aggregate features keys. Cac s can be prov s oned on `go/cac board <https://cac boardv2--prod--cac .serv ce.atla.tw ter.b z/>`_.

.. admon  on:: Test and prod cac s

  For develop nt,    s suff c ent to setup a test cac  that y  new job can query and wr e to. At t  sa  t  , a product on cac  request should also be subm ted as t se generally have s gn f cant lead t  s for prov s on ng.

StormAggregateS ce
--------------------

To enable aggregat on of y  features,   need to start w h def n ng a `StormAggregateS ce` that bu lds a `Producer[Storm, DataRecord]`. T  summ ngb rd producer generates `DataRecords` that conta n t   nput features and labels that t  real-t   aggregate job w ll compute aggregate features on. Conceptually, t   s equ valent to t   nput data set  n t  offl ne batch use case.

.. admon  on:: Example

   f   are plann ng to aggregate on cl ent engage nts,   would need to subscr be to t  `Cl entEvent` kafka stream and t n convert each event to a `DataRecord` that conta ns t  key and t  engage nt on wh ch to aggregate.

Typ cally,   would setup a julep f lter for t  relevant cl ent events that   would l ke to aggregate on. T  g ves us a `Producer[Storm, LogEvent]` object wh ch   t n convert to `Producer[Storm, DataRecord]` w h adapters that   wrote:

.. code-block:: scala

  lazy val cl entEventProducer: Producer[Storm, LogEvent] =
    Cl entEventS ceScrooge(
      app d = App d(jobConf g.app d),
      top c = "julep_cl ent_event_suggests",
      resu AtLastReadOffset = false
    ).s ce.na ("t  l nes_events")

  lazy val cl entEventW hCac dFeaturesProducer: Producer[Storm, DataRecord] = cl entEventProducer
    .flatMap(mkDataRecords)

Note that t  way of compos ng t  storm graph g ves us flex bl y  n how   can hydrate  nput features.  f   would l ke to jo n more complex features to `DataRecord`,   can do so  re w h add  onal storm components wh ch can  mple nt cac  quer es.

.. admon  on:: T  l nes Qual y use case

   n T  l nes Qual y,   aggregate cl ent engage nts on `user d` or `t et d` and  mple nt
  `T  l nesStormAggregateS ce <https://cg .tw ter.b z/s ce/tree/src/scala/com/tw ter/t  l nes/pred ct on/common/aggregates/real_t  /T  l nesStormAggregateS ce.scala>`_.   create
  `Producer[Storm,LogEvent]` of T  l nes engage nts to wh ch   apply `Cl entLogEventAdapter <https://cg .tw ter.b z/s ce/tree/src/scala/com/tw ter/t  l nes/pred ct on/adapters/cl ent_log_event/Cl entLogEventAdapter.scala>`_ wh ch converts t  event to `DataRecord` conta n ng `user d`, `t et d`, `t  stampFeature` of t  engage nt and t  engage nt label  self.

.. admon  on:: Mag cRecs use case

  Mag cRecs has a very s m lar setup for real-t   aggregate features.  n add  on, t y also  mple nt a more complex cac  query to fetch t  user's  tory  n t  `StormAggregateS ce` for each observed cl ent engage nt to hydrate a r c r set of  nput `DataRecords`:

  .. code-block:: scala

    val user toryStoreServ ce: Storm#Serv ce[Long,  tory] =
      Storm.serv ce(User toryReadableStore)

    val cl entEventDataRecordProducer: Producer[Storm, DataRecord] =
      mag cRecsCl entEventProducer
        .flatMap { ...
          (user d, logEvent)
        }.leftJo n(user toryStoreServ ce)
        .flatMap {
          case (_, (logEvent,  tory)) =>
            mkDataRecords(LogEvent toryPa r(logEvent,  tory))
        }

.. admon  on:: Ema lRecs use case

  Ema lRecs shares t  sa  cac  as Mag cRecs. T y comb ne not f cat on scr be data w h ema l  tory data to  dent fy t  part cular  em a user engaged w h  n an ema l:

  .. code-block:: scala

    val ema l toryStoreServ ce: Storm#Serv ce[Long,  tory] =
      Storm.serv ce(Ema l toryReadableStore)

    val ema lEventDataRecordProducer: Producer[Storm, DataRecord] =
      ema lEventProducer
        .flatMap { ...
          (user d, logEvent)
        }.leftJo n(ema l toryStoreServ ce)
        .flatMap {
          case (_, (scr be,  tory)) =>
            mkDataRecords(Scr be toryPa r(scr be,  tory))
        }


Aggregat on conf g
------------------

T  real-t   aggregat on conf g  s extended from `Onl neAggregat onConf gTra  <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ml_ut l/aggregat on_fra work/ ron/Onl neAggregat onConf gTra .scala>`_ and def nes t  features to aggregate and t  back ng  mcac d store to wh ch t y w ll be wr ten.

Sett ng up real-t   aggregates follows t  sa  rules as  n t  offl ne batch use case. T  major d fference  re  s that ` nputS ce` should po nt to t  `StormAggregateS ce`  mple ntat on that prov des t  `DataRecord` conta n ng t  engage nts and core features on wh ch to aggregate.  n t  offl ne case, t  would have been an `Offl neAggregateS ce` po nt ng to an offl ne s ce of da ly records.

F nally, `RealT  AggregateStore` def nes t  back ng  mcac  to be used and should be prov ded  re as t  `outputStore`.

.. NOTE::

  Please make sure to prov de an `AggregateGroup` for both stag ng and product on. T  ma n d fference should be t  `outputStore` w re features  n e  r env ron nt are read from and wr ten to.   want to make sure that a staged real-t   aggregates summ ngb rd job  s read ng/wr  ng only to t  test  mcac  store and does not mutate t  product on store.

Job conf g
----------

 n add  on to t  aggregat on conf g that def nes t  features to aggregate, t  f nal p ece   need to prov de  s a `RealT  AggregatesJobConf g` that spec f c es job values such as `app d`, `teamNa ` and counts for t  var ous topology components that def ne t  capac y of t  job (`T  l nes example <https://cg .tw ter.b z/s ce/tree/src/scala/com/tw ter/t  l nes/pred ct on/common/aggregates/real_t  /T  l nesRealT  AggregatesJob.scala#n22>`_).

Once   have t  job conf g,  mple nt ng t  storm job  self  s easy and almost as conc se as  n t  batch use case:

.. code-block:: scala

  object T  l nesRealT  AggregatesJob extends RealT  AggregatesJobBase {
    overr de lazy val statsRece ver = DefaultStatsRece ver.scope("t  l nes_real_t  _aggregates")
    overr de lazy val jobConf gs = T  l nesRealT  AggregatesJobConf gs
    overr de lazy val aggregatesToCompute = T  l nesOnl neAggregat onConf g.AggregatesToCompute
  }

.. NOTE::
  T re are so  topology sett ngs that are currently hard-coded.  n part cular,   enable `Conf g.TOPOLOGY_DROPTUPLES_UPON_BACKPRESSURE` to be true for added robustness. T  may be made user-def nable  n t  future.

Steps to hydrate RTAs
--------------------
1. Make t  changes to RTAs and follow t  steps for `Runn ng t  topology`.
2. Reg ster t  new RTAs to feature store. Sample phab: https://phabr cator.tw ter.b z/D718120
3. W re t  features from feature store to TLX. T   s usually done w h t  feature sw ch set to False. So  's just a code change and w ll not yet start hydrat ng t  features yet.  rge t  phab. Sample phab: https://phabr cator.tw ter.b z/D718424
4. Now   hydrate t  features to TLX gradually by do ng   shard w se. For t , f rst create a PCM and t n enable t  hydrat on. Sample PCM: https://j ra.tw ter.b z/browse/PCM-147814

Runn ng t  topology
--------------------
0. For phab that makes change to t  topology (such as add ng new ML features), before land ng t  phab, please create a PCM (`example <https://j ra.tw ter.b z/browse/PCM-131614>`_) and deploy t  change to devel topology f rst and t n prod (atla and pdxa). Once    s conf r d that t  prod topology can handle t  change, t  phab can be landed. 
1. Go to https://c .tw ter.b z/job/tq-c /bu ld
2.  n `commands`  nput

.. code-block:: bash

  . src/scala/com/tw ter/t  l nes/pred ct on/common/aggregates/real_t  /deploy_local.sh [devel|atla|pdxa]

One can only deploy e  r `devel`, `atla` (prod atla), `pdxa` (prod pdxa) at a t  .
For example, to deploy both pdxa and atla prod topolog es, one needs to bu ld/run t  above steps tw ce, one w h `pdxa` and t  ot r w h `atla`.

T  status and performance stats of t  topology are found at `go/ ron-u  <http:// ron-u -new--prod-- ron.serv ce.pdxa.tw ter.b z/topolog es>`_.  re   can v ew w t r t  job  s process ng tuples, w t r    s under any  mory or backpressure and prov des general observab l y.

F nally, s nce   enable `Conf g.TOPOLOGY_DROPTUPLES_UPON_BACKPRESSURE` by default  n t  topology,   also need to mon or and alert on t  number of dropped tuples. S nce t   s a job generat ng features a small fract on of dropped tuples  s tolerable  f that enables us to avo d backpressure that would hold up global computat on  n t  ent re graph.

Hydrat ng Real-T   Aggregate Features
--------------------------------------

Once t  job  s up and runn ng, t  aggregate features w ll be access ble  n t  back ng  mcac d store. To access t se features and hydrate to y  onl ne p pel ne,   need to bu ld a  mcac  cl ent w h t  r ght query key.

.. admon  on:: Example

  So  care needs to be taken to def ne t  key  nject on and codec correctly for t   mcac d store. T se types do not change and   can use t  T  l nes ` mcac  cl ent bu lder <https://cg .tw ter.b z/s ce/tree/t  l nem xer/common/src/ma n/scala/com/tw ter/t  l nem xer/cl ents/real_t  _aggregates_cac /RealT  Aggregates mcac Bu lder.scala>`_ as an example.

Aggregate features are wr ten to store w h a `(Aggregat onKey, Batch D)` key.

`Aggregat onKey <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ml_ut l/aggregat on_fra work/Aggregat onKey.scala#n31>`_  s an  nstant of t  keys that   prev ously def ned  n `AggregateGroup`.  f y  aggregat on key  s `USER_ D`,   would need to  nstant ate `Aggregat onKey` w h t  `USER_ D` feature d and t  user d value.

.. admon  on:: Returned features

  T  `DataRecord` that  s returned by t  cac  now conta ns all real-t   aggregate features for t  query `Aggregat onKey` (s m lar to t  batch use case).  f y  onl ne hydrat on flow produces data records, t  real-t   aggregate features can be jo ned w h y  ex st ng records  n a stra ghtforward way.

Add ng features from Feature Store to RTA
--------------------------------------------
To add features from Feature Store to RTA and create real t   aggregated features based on t m, one needs to follow t se steps:

**Step 1**

Copy Strato column for features that one wants to explore and add a cac   f needed. See deta ls at `Custom ze any Columns for y  Team as Needed <https://docb rd.tw ter.b z/ml_feature_store/product on sat on-c ckl st.html?h ghl ght=manhattan#custom ze-any-columns-for-y -team-as-needed>`_. As an `example <https://phabr cator.tw ter.b z/D441050>`_,   copy Strato column of recom ndat onsUserFeaturesProd.User.strato and add a cac  for t  l nes team's usage. 

**Step 2**

Create a new ReadableStore wh ch uses Feature Store Cl ent to request features from Feature Store.  mple nt FeaturesAdapter wh ch extends T  l nesAdapterBase and der ve new features based on raw features from Feature Store. As an `example <https://phabr cator.tw ter.b z/D458168>`_,   create UserFeaturesReadableStore wh ch reads d screte feature user state, and convert   to a l st of boolean user state features. 

**Step 3**

Jo n t se der ved features from Feature Store to t  l nes storm aggregate s ce. Depends on t  character st c of t se der ved features, jo ned key could be t et  d, user  d or ot rs. As an `example <https://phabr cator.tw ter.b z/D454408>`_, because user state  s per user, t  jo ned key  s user  d. 

**Step 4**

Def ne `AggregateGroup` based on der ved features  n RTA

Add ng New Aggregate Features from an Ex st ng Dataset
--------------------------------
To add a new aggregate feature group from an ex st ng dataset for use  n ho  models, use t  follow ng steps:

1.  dent fy t  hypot s s be ng tested by t  add  on of t  features,  n accordance w h `go/tpfeaturegu de <http://go/tpfeaturegu de>`_. 
2. Mod fy or add a new AggregateGroup to `T  l nesOnl neAggregat onConf gBase.scala <https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/src/scala/com/tw ter/t  l nes/pred ct on/common/aggregates/real_t  /T  l nesOnl neAggregat onConf gBase.scala>`_ to def ne t  aggregat on key, set of features, labels and  tr cs. An example phab to add more halfl ves can be found at `D204415 <https://phabr cator.tw ter.b z/D204415>`_.
3.  f t  change  s expected to be very large,   may be recom nded to perform capac y est mat on. See :ref:`Capac y Est mat on` for more deta ls.
4. Create feature catalog  ems for t  new RTAs. An example phab  s `D706348 <https://phabr cator.tw ter.b z/D706438>`_. For approval from a featurestore owner p ng # lp-ml-features on slack.
5. Add new features to t  featurestore. An example phab  s `D706112 <https://phabr cator.tw ter.b z/D706112>`_. T  change can be rolled out w h feature sw c s or by canary ng TLX, depend ng on t  r sk. An example PCM for feature sw c s  s: `PCM-148654 <https://j ra.tw ter.b z/browse/PCM-148654>`_. An example PCM for canary ng  s: `PCM-145753 <https://j ra.tw ter.b z/browse/PCM-145753>`_.
6. Wa  for redeploy and conf rm t  new features are ava lable. One way  s query ng  n B gQuery from a table l ke `tw ter-bq-t  l nes-prod.cont nuous_tra n ng_recap_fav`. Anot r way  s to  nspect  nd v dual records us ng pcat. T  command to be used  s l ke: 

.. code-block:: bash

  java -cp pcat-deploy.jar:$(hadoop classpath) com.tw ter.ml.tool.pcat.Pred ct onCatTool 
  -path /atla/proc2/user/t  l nes/processed/suggests/recap/cont nuous_tra n ng_data_records/fav/data/YYYY/MM/DD/01/part-00000.lzo 
  -fc /atla/proc2/user/t  l nes/processed/suggests/recap/cont nuous_tra n ng_data_records/fav/data_spec.json 
  -dates YYYY-MM-DDT01 -record_l m  100 | grep [feature_group]


7. Create a phab w h t  new features and test t  performance of a model w h t m compared to a control model w hout t m. Test offl ne us ng `Deepb rd for tra n ng <https://docb rd.tw ter.b z/tq_gcp_gu de/deepb rd.html to tra n>`_ and `RCE Hypot s s Test ng <https://docb rd.tw ter.b z/T  l nes_Deepb rd_v2/tra n ng.html#model-evaluat on-rce-hypot s s-test ng>`_ to test. Test onl ne us ng a DDG. So   lpful  nstruct ons are ava lable  n `Serv ng T  l nes Models <https://docb rd.tw ter.b z/t  l nes_deepb rd_v2/serv ng.html>`_ and t  `Exper  nt Cookbook <https://docs.google.com/docu nt/d/1FTaqd_XOzdTppzePe pLhAgYA9 rcN5a_SyQXbuGws/ed #>`_

Capac y Est mat on
--------------------------------
T  sect on descr bes how to approx mate t  capac y requ red for a new aggregate group.    s not expected to be exact, but should g ve a rough est mate.

T re are two ma n components that must be stored for each aggregate group.

Key space: Each Aggregat onKey struct cons sts of two maps, one of wh ch  s populated w h tuples [Long, Long] represent ng <feature d, value> of d screte features. T  takes up 4 x 8 bytes or 32 bytes. T  cac  team est mates an add  onal 40 bytes of over ad.

Features: An aggregate feature  s represented as a <Long, Double> pa r (16 bytes) and  s produced for each feature x label x  tr c x halfl fe comb nat on.

1. Use b gquery to est mate how many un que values ex st for t  selected key (key_count). Also collect t  number of features, labels,  tr cs, and half-l ves be ng used.
2. Compute t  number of entr es to be created, wh ch  s num_ent res = feature_count * label_count *  tr c_count * halfl fe_count
3. Compute t  number of bytes per entry, wh ch  s num_entry_bytes = 16*num_entr es + 32 bytes (key storage) + 40 bytes (over ad)
4. Compute total space requ red = num_entry_bytes * key_count

Debugg ng New Aggregate Features
--------------------------------

To debug problems  n t  setup of y  job, t re are several steps   can take.

F rst, ensure that data  s be ng rece ved from t   nput stream and passed through to create data records. T  can be ach eved by logg ng results at var ous places  n y  code, and espec ally at t  po nt of data record creat on.

For example, suppose   want to ensure that a data record  s be ng created w h
t  features   expect. W h push and ema l features,   f nd that data records
are created  n t  adaptor, us ng log c l ke t  follow ng:

.. code-block:: scala

  val record = new SR chDataRecord(new DataRecord)
  ...
  record.setFeatureValue(feature, value)

To see what t se feature values look l ke,   can have   adaptor class extend
Tw ter's `Logg ng` tra , and wr e each created record to a log f le.

.. code-block:: scala

  class  EventAdaptor extends T  l nesAdapterBase[ Object] w h Logg ng {
    ...
    ...
      def mkDataRecord( Features:  Features): DataRecord = {
        val record = new SR chDataRecord(new DataRecord)
        ...
        record.setFeatureValue(feature, value)
        logger. nfo("data record xyz: " + record.getRecord.toStr ng)
      }

T  way, every t   a data record  s sent to t  aggregator,   w ll also be
logged. To  nspect t se logs,   can push t se changes to a stag ng  nstance,
ssh  nto that aurora  nstance, and grep t  `log-f les` d rectory for `xyz`. T 
data record objects   f nd should resemble a map from feature  ds to t  r
values.

To c ck that steps  n t  aggregat on are be ng perfor d,   can also  nspect t  job's topology on go/ ronu .

Lastly, to ver fy that values are be ng wr ten to y  cac    can c ck t  `set` chart  n y  cac 's v z.

To c ck part cular feature values for a g ven key,   can sp n up a Scala REPL l ke so:

.. code-block:: bash

  $ ssh -fN -L*:2181:sdzookeeper-read.atla.tw ter.com:2181 -D *:50001 nest.atlc.tw ter.com

  $ ./pants repl --jvm-repl-scala-opt ons='-DsocksProxyHost=localhost -DsocksProxyPort=50001 -Dcom.tw ter.server.resolverZkHosts=localhost:2181' t  l nem xer/common/src/ma n/scala/com/tw ter/t  l nem xer/cl ents/real_t  _aggregates_cac 

  w ll t n need to create a connect on to t  cac , and a key w h wh ch to query  .

.. code-block:: scala

   mport com.tw ter.convers ons.Durat onOps._
   mport com.tw ter.f nagle.stats.{DefaultStatsRece ver, StatsRece ver}
   mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.Aggregat onKey
   mport com.tw ter.summ ngb rd.batch.Batc r
   mport com.tw ter.t  l nem xer.cl ents.real_t  _aggregates_cac .RealT  Aggregates mcac Bu lder
   mport com.tw ter.t  l nes.cl ents. mcac _common.Storehaus mcac Conf g

  val userFeature = -1887718638306251279L // feature  d correspond ng to User feature
  val user d = 12L // replace w h a user  d logged w n creat ng y  data record
  val key = (Aggregat onKey(Map(userFeature -> user d), Map.empty), Batc r.un .currentBatch)

  val dataset = "t mcac _mag crecs_real_t  _aggregates_cac _stag ng" // replace w h t  appropr ate cac  na 
  val dest = s"/srv#/test/local/cac /t mcac _/$dataset"

  val statsRece ver: StatsRece ver = DefaultStatsRece ver
  val cac  = new RealT  Aggregates mcac Bu lder(
        conf g = Storehaus mcac Conf g(
          destNa  = dest,
          keyPref x = "",
          requestT  out = 10.seconds,
          numTr es = 1,
          globalT  out = 10.seconds,
          tcpConnectT  out = 10.seconds,
          connect onAcqu s  onT  out = 10.seconds,
          numPend ngRequests = 250,
           sReadOnly = true
        ),
        statsRece ver.scope(dataset)
      ).bu ld

  val result = cac .get(key)

Anot r opt on  s to create a debugger wh ch po nts to t  stag ng cac  and creates a cac  connect on and key s m lar to t  log c above.

Run CQL query to f nd  tr cs/counters
--------------------------------
  can also v sual ze t  counters from   job to ver fy new features. Run CQL query on term nal to f nd t  r ght path of  tr cs/counters. For example,  n order to c ck counter  rgeNumFeatures, run:

cql -z atla keys  ron/summ ngb rd_t  l nes_real_t  _aggregates Ta l-FlatMap | grep  rgeNumFeatures
   
   
T n use t  r ght path to create t  v z, example: https://mon or ng.tw ter.b z/t ny/2552105   
