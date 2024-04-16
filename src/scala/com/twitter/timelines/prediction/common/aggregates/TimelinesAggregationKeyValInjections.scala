package com.tw ter.t  l nes.pred ct on.common.aggregates

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.summ ngb rd.batch.Batch D
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.{
  AggregateStore,
  Aggregat onKey,
  Offl neAggregate nject ons,
  TypedAggregateGroup
}

object T  l nesAggregat onKeyVal nject ons extends T  l nesAggregat onConf gTra  {

   mport Offl neAggregate nject ons.get nject on

  type KV nject on = KeyVal nject on[Aggregat onKey, (Batch D, DataRecord)]

  val AuthorTop c: KV nject on = get nject on(f lter(AuthorTop cAggregateStore))
  val UserTop c: KV nject on = get nject on(f lter(UserTop cAggregateStore))
  val User nferredTop c: KV nject on = get nject on(f lter(User nferredTop cAggregateStore))
  val User: KV nject on = get nject on(f lter(UserAggregateStore))
  val UserAuthor: KV nject on = get nject on(f lter(UserAuthorAggregateStore))
  val UserOr g nalAuthor: KV nject on = get nject on(f lter(UserOr g nalAuthorAggregateStore))
  val Or g nalAuthor: KV nject on = get nject on(f lter(Or g nalAuthorAggregateStore))
  val UserEngager: KV nject on = get nject on(f lter(UserEngagerAggregateStore))
  val User nt on: KV nject on = get nject on(f lter(User nt onAggregateStore))
  val Tw terW deUser: KV nject on = get nject on(f lter(Tw terW deUserAggregateStore))
  val Tw terW deUserAuthor: KV nject on = get nject on(f lter(Tw terW deUserAuthorAggregateStore))
  val UserRequestH : KV nject on = get nject on(f lter(UserRequestH AggregateStore))
  val UserRequestDow: KV nject on = get nject on(f lter(UserRequestDowAggregateStore))
  val UserL st: KV nject on = get nject on(f lter(UserL stAggregateStore))
  val User d aUnderstand ngAnnotat on: KV nject on = get nject on(
    f lter(User d aUnderstand ngAnnotat onAggregateStore))

  pr vate def f lter(storeNa : Str ng): Set[TypedAggregateGroup[_]] = {
    val groups = aggregatesToCompute.f lter(_.outputStore.na  == storeNa )
    requ re(groups.nonEmpty)
    groups
  }

  overr de def outputHdfsPath: Str ng = "/user/t  l nes/processed/aggregates_v2"

  // S nce t  object  s not used to execute any onl ne or offl ne aggregates job, but  s  ant
  // to store all PDT enabled KeyVal nject ons,   do not need to construct a phys cal store.
  //   use t   dent y operat on as a default.
  overr de def mkPhys calStore(store: AggregateStore): AggregateStore = store
}
