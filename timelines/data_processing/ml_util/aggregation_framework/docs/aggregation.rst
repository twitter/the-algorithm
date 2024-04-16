.. _aggregat on:

Core Concepts
=============

T  page prov des an overv ew of t  aggregat on fra work and goes through examples on how to def ne aggregate features.  n general,   can th nk of an aggregate feature as a grouped set of records, on wh ch    ncre ntally update t  aggregate feature values, crossed by t  prov ded features and cond  onal on t  prov ded labels.

AggregateGroup
--------------

An `AggregateGroup` def nes a s ngle un  of aggregate computat on, s m lar to a SQL query. T se are executed by t  underly ng jobs ( nternally, a `DataRecordAggregat onMono d <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ml_ut l/aggregat on_fra work/DataRecordAggregat onMono d.scala#n42>`_  s appl ed to `DataRecords` that conta n t  features to aggregate). Many of t se groups can ex st to def ne d fferent types of aggregate features.

Let's start w h t  follow ng examples of an `AggregateGroup` to d scuss t   an ng of each of  s constructor argu nts:

.. code-block:: scala

   val UserAggregateStore = "user_aggregates"
   val aggregatesToCompute: Set[TypedAggregateGroup[_]] = Set(
     AggregateGroup(
        nputS ce = t  l nesDa lyRecapS ce,
       aggregatePref x = "user_aggregate_v2",
       preTransformOpt = So (RemoveUser dZero),
       keys = Set(USER_ D),
       features = Set(HAS_PHOTO),
       labels = Set( S_FAVOR TED),
        tr cs = Set(Count tr c, Sum tr c),
       halfL ves = Set(50.days),
       outputStore = Offl neAggregateStore(
         na  = UserAggregateStore,
         startDate = "2016-07-15 00:00",
         commonConf g = t  l nesDa lyAggregateS nk,
         batc sToKeep = 5
       )
     )
     .flatMap(_.bu ldTypedAggregateGroups)
   )

T  `AggregateGroup` computes t  number of t  s each user has faved a t et w h a photo. T  aggregate count  s decayed w h a 50 day halfl fe.

Nam ng and preprocess ng
------------------------

`UserAggregateStore`  s a str ng val that acts as a scope of a "root path" to wh ch t  group of aggregate features w ll be wr ten. T  root path  s prov ded separately by t   mple nt ng job.

` nputS ce` def nes t   nput s ce of `DataRecords` that   aggregate on. T se records conta n t  relevant features requ red for aggregat on. 

`aggregatePref x` tells t  fra work what pref x to use for t  aggregate features   generates. A descr pt ve nam ng sc   w h vers on ng makes   eas er to ma nta n features as   add or remove t m over t  long-term.

`preTransforms`  s a `Seq[com.tw ter.ml.ap . Transform] <https://cg .tw ter.b z/s ce/tree/src/java/com/tw ter/ml/ap / Transform.java>`_ that can be appl ed to t  data records read from t   nput s ce before t y are fed  nto t  `AggregateGroup` to apply aggregat on. T se transforms are opt onal but can be useful for certa n preprocess ng operat ons for a group's raw  nput features. 

.. admon  on:: Examples
  
    can downsample  nput data records by prov d ng `preTransforms`.  n add  on,   could also jo n d fferent  nput labels (e.g. " s_push_openend" and " s_push_favor ed") and transform t m  nto a comb ned label that  s t  r un on (" s_push_engaged") on wh ch aggregate counts w ll be calculated.


Keys
----

`keys`  s a cruc al f eld  n t  conf g.   def nes a `Set[com.tw ter.ml.ap .Feature]` wh ch spec f es a set of group ng keys to use for t  `AggregateGroup`.

Keys can only be of 3 supported types currently: `D SCRETE`, `STR NG` and `SPARSE_B NARY`. Us ng a d screte or a str ng/text feature as a key spec f es t  un  to group records by before apply ng count ng/aggregat on operators.


.. admon  on:: Examples

  .. cssclass:: shortl st

  #.  f t  key  s `USER_ D`, t  tells t  fra work to group all records by `USER_ D`, and t n apply aggregat ons (sum/count/etc) w h n each user’s data to generate aggregate features for each user.

  #.  f t  key  s `(USER_ D, AUTHOR_ D)`, t n t  `AggregateGroup` w ll output features for each un que user-author pa r  n t   nput data.

  #. F nally, us ng a sparse b nary feature as key has spec al "flatten ng" or "flatMap" l ke semant cs. For example, cons der group ng by `(USER_ D, AUTHOR_ NTEREST_ DS)` w re `AUTHOR_ NTEREST_ DS`  s a sparse b nary feature wh ch represents a set of top c  Ds t  author may be t et ng about. T  creates one record for each `(user_ d,  nterest_ d)` pa r - so each record w h mult ple author  nterests  s flattened before feed ng   to t  aggregat on.

Features
--------

`features` spec f es a `Set[com.tw ter.ml.ap .Feature]` to aggregate w h n each group (def ned by t  keys spec f ed earl er).

  support 2 types of `features`: `B NARY` and `CONT NUOUS`.

T  semant cs of how t  aggregat on works  s sl ghtly d fferent based on t  type of “feature”, and based on t  “ tr c” (or aggregat on operat on):

.. cssclass:: shortl st

#. B nary Feature, Count  tr c: Suppose   have a b nary feature `HAS_PHOTO`  n t  set, and are apply ng t  “Count”  tr c (see below for more deta ls on t   tr cs), w h key `USER_ D`. T  semant cs  s that t  computes a feature wh ch  asures t  count of records w h `HAS_PHOTO` set to true for each user.

#. B nary Feature, Sum  tr c - Does not apply. No feature w ll be computed.

#. Cont nuous Feature, Count  tr c - T  count  tr c treats all features as b nary features  gnor ng t  r value. For example, suppose   have a cont nuous feature `NUM_CHARACTERS_ N_TWEET`, and key `USER_ D`. T   asures t  count of records that have t  feature `NUM_CHARACTERS_ N_TWEET` present.

#. Cont nuous Feature, Sum  tr c -  n t  above example, t  features  asures t  sum of (num_characters_ n_t et) over all a user’s records. D v d ng t  sum feature by t  count feature would g ve t  average number of characters  n all t ets.

.. admon  on:: Unsupported feature types

  `D SCRETE` and `SPARSE` features are not supported by t  Sum  tr c, because t re  s no  an ng  n summ ng a d screte feature or a sparse feature.   can use t m w h t  Count tr c, but t y may not do what   would expect s nce t y w ll be treated as b nary features los ng all t   nformat on w h n t  feature. T  best way to use t se  s as “keys” and not as “features”.

.. admon  on:: Sett ng  ncludeAnyFeature

   f constructor argu nt ` ncludeAnyFeature`  s set, t  fra work w ll append a feature w h scope `any_feature` to t  set of all features   def ne. T  add  onal feature s mply  asures t  total count of records. So  f   set y  features to be equal to Set.empty, t  w ll  asure t  count of records for a g ven `USER_ D`.

Labels
------

`labels` spec f es a set of `B NARY` features that   can cross w h, pr or to apply ng aggregat ons on t  `features`. T  essent ally restr cts t  aggregate computat on to a subset of t  records w h n a part cular key.

  typ cally use t  to represent engage nt labels  n an ML model,  n t  case, ` S_FAVOR TED`.

 n t  example,   are group ng by `USER_ D`, t  feature  s `HAS_PHOTO`, t  label  s ` S_FAVOR TED`, and   are comput ng `Count tr c`. T  system w ll output a feature for each user that represents t  number of favor es on t ets hav ng photos by t  `user d`.

.. admon  on:: Sett ng  ncludeAnyLabel

   f constructor argu nt ` ncludeAnyLabel`  s set (as    s by default), t n s m lar to `any_feature`, t  fra work automat cally appends a label of type `any_label` to t  set of all labels   def ne, wh ch represents not apply ng any f lter or cross.
  
 n t  example, `any_label` and `any_feature` are set by default and t  system would actually output 4 features for each `user_ d`:

.. cssclass:: shortl st

#. T  number of ` S_FAVOR TED` (favor es) on t et  mpress ons hav ng `HAS_PHOTO=true`

#. T  number of ` S_FAVOR TED` (favor es) on all t et  mpress ons (`any_feature` aggregate)

#. T  number of t et  mpress ons hav ng `HAS_PHOTO=true` (`any_label` aggregate)

#. T  total number of t et  mpress ons for t  user  d (`any_feature.any_label` aggregate)

.. admon  on:: D sabl ng  ncludeAnyLabel

  To d sable t  automat cally generated feature   can use ` ncludeAnyLabel = false`  n y  conf g. T  w ll remove so  useful features (part cularly for counterfactual s gnal), but   can greatly save on space s nce   does not store every poss ble  mpressed set of keys  n t  output store. So use t   f   are short on space, but not ot rw se.

 tr cs
-------

` tr cs` spec f es t  aggregate operators to apply. T  most commonly used are `Count`, `Sum` and `SumSq`.

As  nt oned before, `Count` can be appl ed to all types of features, but treats every feature as b nary and  gnores t  value of t  feature. `Sum` and `SumSq` can only be appl ed to Cont nuous features - t y w ll  gnore all ot r features   spec fy. By comb n ng sum and sumsq and count,   can produce po rful “z-score” features or ot r d str but onal features us ng a post-transform.

   s also poss ble to add y  own aggregate operators (e.g. `LastReset tr c <https://phabr cator.tw ter.b z/D228537>`_) to t  fra work w h so  add  onal work.

HalfL ves
---------

`halfL ves` spec f es how fast aggregate features should be decayed.    s  mportant to note that t  fra work works on an  ncre ntal bas s:  n t  batch  mple ntat on, t  summ ngb rd-scald ng job takes  n t  most recently computed aggregate features, processed on data unt l day `N-1`, t n reads new data records for day `N` and computes updated values of t  aggregate features. S m larly, t  decay of real-t   aggregate features takes t  actual t   delta bet en t  current t   and t  last t   t  aggregate feature value was updated.

T  halfl fe `H` spec f es how fast to decay old sums/counts to s mulate a sl d ng w ndow of counts. T   mple ntat on  s such that   w ll take `H` amount of t   to decay an aggregate feature to half  s  n  al value. New observed values of sums/counts are added to t  aggregate feature value.

.. admon  on:: Batch and real-t  
  
   n t  batch use case w re aggregate features are recomputed on a da ly bas s,   typ cally take halfl ves on t  order of  eks or longer ( n T  l nes, 50 days).  n t  real-t   use case, shorter halfl ves are appropr ate (h s) s nce t y are updated as cl ent engage nts are rece ved by t  summ ngb rd job.


SQL Equ valent
--------------
Conceptually,   can also th nk of   as:

.. code-block:: sql

   NSERT  NTO <outputStore>.<aggregatePref x>
  SELECT AGG(<features>) /* AGG  s < tr cs>, wh ch  s a exponent ally decay ng SUM or COUNT etc. based on t  halfL fves */
  FROM (
    SELECT preTransformOpt(*) FROM < nputS ce>
  ) 
  GROUP BY <keys>
  WHERE <labels> = True

any_features  s AGG(*).

any_labels removes t  WHERE clause.