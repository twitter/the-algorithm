.. _jo n ng:

Jo n ng aggregates features to records
======================================

After sett ng up e  r offl ne batch jobs or onl ne real-t   summ ngb rd jobs to produce
aggregate features and query ng t m,   are left w h data records conta n ng aggregate features.
T  page w ll go over how to jo n t m w h ot r data records to produce offl ne tra n ng data.

(To d scuss: jo n ng aggregates to records onl ne)

Jo n ng Aggregates on D screte/Str ng Keys
------------------------------------------

Jo n ng aggregate features keyed on d screte or text features to y  tra n ng data  s very easy -
  can use t  bu lt  n  thods prov ded by `DataSetP pe`. For example, suppose   have aggregates
keyed by `(USER_ D, AUTHOR_ D)`:

.. code-block:: scala

  val userAuthorAggregates: DataSetP pe = AggregatesV2FeatureS ce(
      rootPath = “/path/to/ /aggregates”,
      storeNa  = “user_author_aggregates”,
      aggregates =  Conf g.aggregatesToCompute,
      tr mThreshold = 0
    )(dateRange).read

Offl ne,   can t n jo n w h y  tra n ng data set as follows:

.. code-block:: scala

  val  Tra n ngData: DataSetP pe = ...
  val jo nedData =  Tra n ngData.jo nW hLarger((USER_ D, AUTHOR_ D), userAuthorAggregates)

  can read from `AggregatesV2MostRecentFeatureS ceBeforeDate`  n order to read t  most recent aggregates
before a prov ded date `beforeDate`. Just note that `beforeDate` must be al gned w h t  date boundary so  f
 ’re pass ng  n a `dateRange`, use `dateRange.end`).

Jo n ng Aggregates on Sparse B nary Keys
----------------------------------------

W n jo n ng on sparse b nary keys, t re can be mult ple aggregate records to jo n to each tra n ng record  n
y  tra n ng data set. For example, suppose   have setup an aggregate group that  s keyed on `( NTEREST_ D, AUTHOR_ D)`
captur ng engage nt counts of users  nterested  n a part cular ` NTEREST_ D` for spec f c authors prov ded by `AUTHOR_ D`.

Suppose now that   have a tra n ng data record represent ng a spec f c user act on. T  tra n ng data record conta ns
a sparse b nary feature ` NTEREST_ DS` represent ng all t  " nterests" of that user - e.g. mus c, sports, and so on. Each ` nterest_ d`
translates to a d fferent set of count ng features found  n y  aggregates data. T refore   need a way to  rge all of
t se d fferent sets of count ng features to produce a more compact, f xed-s ze set of features. 

.. admon  on::  rge pol c es

  To do t , t  aggregate fra work prov des a tra  `SparseB nary rgePol cy <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ml_ut l/aggregat on_fra work/convers on/SparseB nary rgePol cy.scala>`_. Classes overr d ng t  tra  def ne pol c es
  that state how to  rge t   nd v dual aggregate features from each sparse b nary value ( n t  case, each ` NTEREST_ D` for a user).
  Furt rmore,   prov de `SparseB naryMult pleAggregateJo n` wh ch executes t se pol c es to  rge aggregates.

A s mple pol cy m ght s mply average all t  counts from t   nd v dual  nterests, or just take t  max, or
a spec f c quant le. More advanced pol c es m ght use custom cr er a to dec de wh ch  nterest  s most relevant and choose
features from that  nterest to represent t  user, or use so    ghted comb nat on of counts.

T  fra work prov des two s mple  n-bu lt pol c es (`P ckTopCtrPol cy <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ml_ut l/aggregat on_fra work/convers on/P ckTopCtrPol cy.scala>`_
and `Comb neCountsPol cy <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ml_ut l/aggregat on_fra work/convers on/Comb neCountsPol cy.scala>`_, wh ch keeps t  topK counts per
record) that   can get started w h, though   l kely want to  mple nt y  own pol cy based on doma n knowledge to get
t  best results for y  spec f c problem doma n.

.. admon  on:: Offl ne Code Example

  T  scald ng job `Tra n ngDataW hAggV2Generator <https://cg .tw ter.b z/s ce/tree/t  l nes/data_process ng/ad_hoc/recap/tra n ng_data_generator/Tra n ngDataW hAggV2Generator.scala>`_ shows how mult ple  rge pol c es are def ned and  mple nted to  rge aggregates on sparse b nary keys to t  TQ's tra n ng data records.

.. admon  on:: Onl ne Code Example

   n   (non-FeatureStore enabled) onl ne code path,    rge aggregates on sparse b nary keys us ng t  `Comb neCountsPol cy <https://cg .tw ter.b z/s ce/tree/t  l nem xer/server/src/ma n/scala/com/tw ter/t  l nem xer/ nject on/recapbase/aggregates/UserFeaturesHydrator.scala#n201>`_.
