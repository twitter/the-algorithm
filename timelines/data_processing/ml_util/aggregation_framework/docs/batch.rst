.. _batch:

Batch aggregate feature jobs
============================

 n t  prev ous sect on,    nt over t  core concepts of t  aggregat on fra work and d scussed how   can set up   own `AggregateGroups` to compute aggregate features.

G ven t se groups, t  sect on w ll d scuss how   can setup offl ne batch jobs to produce t  correspond ng aggregate features, updated da ly. To accompl sh t ,   need to setup a summ ngb rd-scald ng job that  s po nted to t   nput data records conta n ng features and labels to be aggregated.

 nput Data
----------

 n order to generate aggregate features, t  relevant  nput features need to be ava lable offl ne as a da ly scald ng s ce  n `DataRecord` format (typ cally `Da lySuff xFeatureS ce <https://cg .tw ter.b z/s ce/tree/src/scala/com/tw ter/ml/ap /FeatureS ce.scala>`_, though `H lySuff xFeatureS ce` could also be usable but   have not tested t ).

.. admon  on:: Note

  T   nput data s ce should conta n t  keys, features and labels   want to use  n y  `AggregateGroups`.

Aggregat on Conf g
------------------

Now that   have a da ly data s ce w h  nput features and labels,   need to setup t  `AggregateGroup` conf g  self. T  conta ns all aggregat on groups that   would l ke to compute and   w ll go through t   mple ntat on step-by-step.

.. admon  on:: Example: T  l nes Qual y conf g

  `T  l nesAggregat onConf g <https://cg .tw ter.b z/s ce/tree/src/scala/com/tw ter/t  l nes/pred ct on/common/aggregates/T  l nesAggregat onConf g.scala>`_  mports t  conf gured `Aggregat onGroups` from `T  l nesAggregat onConf gDeta ls <https://cg .tw ter.b z/s ce/tree/src/scala/com/tw ter/t  l nes/pred ct on/common/aggregates/T  l nesAggregat onConf gDeta ls.scala>`_. T  conf g  s t n referenced by t   mple nt ng summ ngb rd-scald ng job wh ch   w ll setup below.

Offl neAggregateS ce
----------------------

Each `AggregateGroup` w ll need to def ne a (da ly) s ce of  nput features.   use `Offl neAggregateS ce` for t  to tell t  aggregat on fra work w re t   nput data set  s and t  requ red t  stamp feature that t  fra work uses to decay aggregate feature values:

.. code-block:: scala

 val t  l nesDa lyRecapS ce = Offl neAggregateS ce(
    na  = "t  l nes_da ly_recap",
    t  stampFeature = T MESTAMP,
    scald ngHdfsPath = So ("/user/t  l nes/processed/suggests/recap/data_records"),
    scald ngSuff xType = So ("da ly"),
    w hVal dat on = true
  )

.. admon  on:: Note

  .. cssclass:: shortl st

  #. T  na   s not  mportant as long as    s un que.

  #. `t  stampFeature` must be a d screte feature of type `com.tw ter.ml.ap .Feature[Long]` and represents t  “t  ” of a g ven tra n ng record  n m ll seconds - for example, t  t   at wh ch an engage nt, push open event, or abuse event took place that   are try ng to tra n on.  f   do not already have such a feature  n y  da ly tra n ng data,   need to add one.

  #. `scald ngSuff xType` can be “h ly” or “da ly” depend ng on t  type of s ce (`H lySuff xFeatureS ce` vs `Da lySuff xFeatureS ce`).
  
  #. Set `w hVal dat on` to true to val date t  presence of _SUCCESS f le. Context: https://j ra.tw ter.b z/browse/TQ-10618

Output HDFS store
-----------------

T  output HDFS store  s w re t  computed aggregate features are stored. T  store conta ns all computed aggregate feature values and  s  ncre ntally updated by t  aggregates job every day.

.. code-block:: scala

 val outputHdfsPath = "/user/t  l nes/processed/aggregates_v2"
  val t  l nesOffl neAggregateS nk = new Offl neStoreCommonConf g {
    overr de def apply(startDate: Str ng) = new Offl neAggregateStoreCommonConf g(
      outputHdfsPathPref x = outputHdfsPath,
      dum App d = "t  l nes_aggregates_v2_ro", // unused - can be arb rary
      dum DatasetPref x = "t  l nes_aggregates_v2_ro", // unused - can be arb rary
      startDate = startDate
    )
  }

Note: `dum App d` and `dum DatasetPref x` are unused so can be set to any arb rary value. T y should be removed on t  fra work s de.

T  `outputHdfsPathPref x`  s t  only f eld that matters, and should be set to t  HDFS path w re   want to store t  aggregate features. Make sure   have a lot of quota ava lable at that path.

Sett ng Up Aggregates Job
-------------------------

Once   have def ned a conf g f le w h t  aggregates   would l ke to compute, t  next step  s to create t  aggregates scald ng job us ng t  conf g (`example <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ad_hoc/aggregate_ nteract ons/v2/offl ne_aggregat on/T  l nesAggregat onScald ngJob.scala>`_). T   s very conc se and requ res only a few l nes of code:

.. code-block:: scala

  object T  l nesAggregat onScald ngJob extends AggregatesV2Scald ngJob {
    overr de val aggregatesToCompute = T  l nesAggregat onConf g.aggregatesToCompute
  }

Now that t  scald ng job  s  mple nted w h t  aggregat on conf g,   need to setup a capesos conf g s m lar to https://cg .tw ter.b z/s ce/tree/sc ence/scald ng/ sos/t  l nes/prod.yml:

.. code-block:: scala

  # Common conf gurat on shared by all aggregates v2 jobs
  __aggregates_v2_common__: &__aggregates_v2_common__
    class: HadoopSumm ngb rdProducer
    bundle: offl ne_aggregat on-deploy.tar.gz
    ma njar: offl ne_aggregat on-deploy.jar
    pants_target: "bundle t  l nes/data_process ng/ad_hoc/aggregate_ nteract ons/v2/offl ne_aggregat on:b n"
    cron_coll s on_pol cy: CANCEL_NEW
    use_l bjar_w ld_card: true

.. code-block:: scala

  # Spec f c job comput ng user aggregates
  user_aggregates_v2:
    <<: *__aggregates_v2_common__
    cron_sc dule: "25 * * * *"
    argu nts: --batc s 1 --output_stores user_aggregates --job_na  t  l nes_user_aggregates_v2

.. admon  on::  mportant

  Each AggregateGroup  n y  conf g should have  s own assoc ated offl ne job wh ch spec f es `output_stores` po nt ng to t  output store na    def ned  n y  conf g.

Runn ng T  Job
---------------

W n   run t  batch job for t  f rst t  ,   need to add a temporary entry to y  capesos yml f le that looks l ke t :

.. code-block:: scala

  user_aggregates_v2_ n  al_run:
    <<: *__aggregates_v2_common__
    cron_sc dule: "25 * * * *"
    argu nts: --batc s 1 --start-t   “2017-03-03 00:00:00” --output_stores user_aggregates --job_na  t  l nes_user_aggregates_v2

.. admon  on:: Start T  

  T  add  onal `--start-t  ` argu nt should match t  `startDate`  n y  conf g for that AggregateGroup, but  n t  format `yyyy-mm-dd hh:mm:ss`. 

To  nvoke t   n  al run v a capesos,   would do t  follow ng ( n T  l nes case):

.. code-block:: scala

  CAPESOSPY_ENV=prod capesospy-v2 update --bu ld_locally --start_cron user_aggregates_v2_ n  al_run sc ence/scald ng/ sos/t  l nes/prod.yml

Once    s runn ng smoothly,   can desc dule t   n  al run job and delete t  temporary entry from y  product on yml conf g. 

.. code-block:: scala

  aurora cron desc dule atla/t  l nes/prod/user_aggregates_v2_ n  al_run
  
Note: desc dule   preempt vely to avo d repeatedly overwr  ng t  sa   n  al results

T n sc dule t  product on job from jenk ns us ng so th ng l ke t :

.. code-block:: scala

  CAPESOSPY_ENV=prod capesospy-v2 update user_aggregates_v2 sc ence/scald ng/ sos/t  l nes/prod.yml

All future runs (2nd onwards) w ll use t  permanent entry  n t  capesos yml conf g that does not have t  `start-t  ` spec f ed.

.. admon  on:: Job na  has to match

   's  mportant that t  product on run should share t  sa  `--job_na ` w h t   n  al_run so that eagleeye/stateb rd knows how to keep track of   correctly.

Output Aggregate Features
-------------------------

T  scald ng job us ng t  example conf g from t  earl er sect on would output a Vers onedKeyValS ce to `/user/t  l nes/processed/aggregates_v2/user_aggregates` on HDFS.

Note that `/user/t  l nes/processed/aggregates_v2`  s t  expl c ly def ned root path wh le `user_aggregates`  s t  output d rectory of t  example `AggregateGroup` def ned earl er. T  latter can be d fferent for d fferent `AggregateGroups` def ned  n y  conf g.


T  Vers onedKeyValS ce  s d ff cult to use d rectly  n y  jobs/offl ne tra n ngs, but   prov de an adapted s ce `AggregatesV2FeatureS ce` that makes   easy to jo n and use  n y  jobs:

.. code-block:: scala

   mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on._

  val p pe: DataSetP pe = AggregatesV2FeatureS ce(
    rootPath = "/user/t  l nes/processed/aggregates_v2",
    storeNa  = "user_aggregates",
    aggregates = T  l nesAggregat onConf g.aggregatesToCompute,
    tr mThreshold = 0
  )(dateRange).read

S mply replace t  `rootPath`, `storeNa ` and `aggregates` object to whatever   def ned. T  `tr mThreshold` tells t  fra work to tr m all features below a certa n cutoff: 0  s a safe default to use to beg n w h.

.. admon  on:: Usage

  T  can now be used l ke any ot r `DataSetP pe`  n offl ne ML jobs.   can wr e out t  features to a `Da lySuff xFeatureS ce`,   can jo n t m w h y  data offl ne for tra n ngs, or   can wr e t m to a Manhattan store for serv ng onl ne. 

Aggregate Features Example
--------------------------

 re  s an example of sample of t  aggregate features   just computed:

.. code-block:: scala

  user_aggregate_v2.pa r.any_label.any_feature.50.days.count: 100.0
  user_aggregate_v2.pa r.any_label.t ets ce. s_quote.50.days.count: 30.0
  user_aggregate_v2.pa r. s_favor ed.any_feature.50.days.count: 10.0
  user_aggregate_v2.pa r. s_favor ed.t ets ce. s_quote.50.days.count: 6.0
   ta.user_ d: 123456789

Aggregate feature na s match a `pref x.pa r.label.feature.half_l fe. tr c` sc ma and correspond to what was def ned  n t  aggregat on conf g for each of t se f elds.

.. admon  on:: Example

   n t  example, t  above features are captur ng that user d 123456789L has:

  .. 
  A 50-day decayed count of 100 tra n ng records w h any label or feature (“t et  mpress ons”)

  A 50-day decayed count of 30 records that are “quote t ets” (t ets ce. s_quote = true)

  A 50-day decayed count of 10 records that are favor es on any type of t et ( s_favor ed = true)

  A 50-day decayed count of 6 records that are “favor es” on “quote t ets” (both of t  above are true)

By comb n ng t  above, a model m ght  nfer that for t  spec f c user, quote t ets compr se 30% of all  mpress ons, have a favor e rate of 6/30 = 20%, compared to a favor e rate of 10/100 = 10% on t  total populat on of t ets.

T refore, be ng a quote t et makes t  spec f c user `123456789L` approx mately tw ce as l kely to favor e t  t et, wh ch  s useful for pred ct on and could result  n t  ML model g v ng h g r scores to & rank ng quote t ets h g r  n a personal zed fash on for t  user.

Tests for Feature Na s
--------------------------
W n   change or add AggregateGroup, feature na s m ght change. And t  Feature Store prov des a test ng  chan sm to assert that t  feature na s change as   expect. See `tests for feature na s <https://docb rd.tw ter.b z/ml_feature_store/catalog.html#tests-for-feature-na s>`_.
