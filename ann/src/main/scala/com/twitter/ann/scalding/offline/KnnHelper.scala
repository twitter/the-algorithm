package com.tw ter.ann.scald ng.offl ne

 mport com.tw ter.ann.common._
 mport com.tw ter.ann.hnsw.{HnswParams, TypedHnsw ndex}
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.cortex.ml.embedd ngs.common.{Ent yK nd,  lpers, UserK nd}
 mport com.tw ter.ent yembedd ngs.ne ghbors.thr ftscala.{Ent yKey, NearestNe ghbors, Ne ghbor}
 mport com.tw ter.ml.ap .embedd ng.Embedd ng
 mport com.tw ter.ml.ap .embedd ng.Embedd ngMath.{Float => math}
 mport com.tw ter.ml.featurestore.l b.embedd ng.Embedd ngW hEnt y
 mport com.tw ter.ml.featurestore.l b.{Ent y d, User d}
 mport com.tw ter.scald ng.typed.{TypedP pe, UnsortedGrouped}
 mport com.tw ter.scald ng.{Args, DateRange, Stat, TextL ne, Un que D}
 mport com.tw ter.search.common.f le.AbstractF le
 mport com.tw ter.ut l.{Awa , FuturePool}
 mport scala.ut l.Random

case class  ndex[T, D <: D stance[D]](
   nject on:  nject on[T, Array[Byte]],
   tr c:  tr c[D],
  d  ns on:  nt,
  d rectory: AbstractF le) {
  lazy val ann ndex = TypedHnsw ndex.load ndex[T, D](
    d  ns on,
     tr c,
     nject on,
    ReadWr eFuturePool(FuturePool. m d atePool),
    d rectory
  )
}

object Knn lper {
  def getF lteredUserEmbedd ngs(
    args: Args,
    f lterPath: Opt on[Str ng],
    reducers:  nt,
    useHashJo n: Boolean
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[Embedd ngW hEnt y[User d]] = {
    val userEmbedd ngs: TypedP pe[Embedd ngW hEnt y[User d]] =
      UserK nd.parser.getEmbedd ngFormat(args, "consu r").getEmbedd ngs
    f lterPath match {
      case So (f leNa : Str ng) =>
        val f lterUser ds: TypedP pe[User d] = TypedP pe
          .from(TextL ne(f leNa ))
          .flatMap {  dL ne =>
             lpers.opt onalToLong( dL ne)
          }
          .map {  d =>
            User d( d)
          }
         lpers
          .adjustableJo n(
            left = userEmbedd ngs.groupBy(_.ent y d),
            r ght = f lterUser ds.asKeys,
            useHashJo n = useHashJo n,
            reducers = So (reducers)
          ).map {
            case (_, (embedd ng, _)) => embedd ng
          }
      case None => userEmbedd ngs
    }
  }

  def getNe ghborsP pe[T <: Ent y d, D <: D stance[D]](
    args: Args,
    uncastEnt yK nd: Ent yK nd[_],
    uncast tr c:  tr c[_],
    ef:  nt,
    consu rEmbedd ngs: TypedP pe[Embedd ngW hEnt y[User d]],
    abstractF le: Opt on[AbstractF le],
    reducers:  nt,
    numNe ghbors:  nt,
    d  ns on:  nt
  )(
     mpl c  dateRange: DateRange
  ): TypedP pe[(Ent yKey, NearestNe ghbors)] = {
    val ent yK nd = uncastEnt yK nd.as nstanceOf[Ent yK nd[T]]
    val  nject on = ent yK nd.byte nject on
    val  tr c = uncast tr c.as nstanceOf[ tr c[D]]
    abstractF le match {
      case So (d rectory: AbstractF le) =>
        val  ndex =  ndex( nject on,  tr c, d  ns on, d rectory)
        consu rEmbedd ngs
          .map { embedd ng =>
            val knn = Awa .result(
               ndex.ann ndex.queryW hD stance(
                Embedd ng(embedd ng.embedd ng.toArray),
                numNe ghbors,
                HnswParams(ef)
              )
            )
            val ne ghborL st = knn
              .f lter(_.ne ghbor.toStr ng != embedd ng.ent y d.user d.toStr ng)
              .map(nn =>
                Ne ghbor(
                  ne ghbor = Ent yKey(nn.ne ghbor.toStr ng),
                  s m lar y = So (1 - nn.d stance.d stance)))
            Ent yKey(embedd ng.ent y d.toStr ng) -> NearestNe ghbors(ne ghborL st)
          }
      case None =>
        val producerEmbedd ngs: TypedP pe[Embedd ngW hEnt y[User d]] =
          UserK nd.parser.getEmbedd ngFormat(args, "producer").getEmbedd ngs

        bruteForceNearestNe ghbors(
          consu rEmbedd ngs,
          producerEmbedd ngs,
          numNe ghbors,
          reducers
        )
    }
  }

  def bruteForceNearestNe ghbors(
    consu rEmbedd ngs: TypedP pe[Embedd ngW hEnt y[User d]],
    producerEmbedd ngs: TypedP pe[Embedd ngW hEnt y[User d]],
    numNe ghbors:  nt,
    reducers:  nt
  ): TypedP pe[(Ent yKey, NearestNe ghbors)] = {
    consu rEmbedd ngs
      .cross(producerEmbedd ngs)
      .map {
        case (cEmbed: Embedd ngW hEnt y[User d], pEmbed: Embedd ngW hEnt y[User d]) =>
          // Cos ne s m lar y
          val cEmbedNorm = math.l2Norm(cEmbed.embedd ng).toFloat
          val pEmbedNorm = math.l2Norm(pEmbed.embedd ng).toFloat
          val d stance: Float = -math.dotProduct(
            (math.scalarProduct(cEmbed.embedd ng, 1 / cEmbedNorm)),
            math.scalarProduct(pEmbed.embedd ng, 1 / pEmbedNorm))
          (
            UserK nd.str ng nject on(cEmbed.ent y d),
            (d stance, UserK nd.str ng nject on(pEmbed.ent y d)))
      }
      .groupBy(_._1).w hReducers(reducers)
      .sortW hTake(numNe ghbors) {
        case ((_: Str ng, (s m1: Float, _: Str ng)), (_: Str ng, (s m2: Float, _: Str ng))) =>
          s m1 < s m2
      }
      .map {
        case (consu r d: Str ng, (prodS ms: Seq[(Str ng, (Float, Str ng))])) =>
          Ent yKey(consu r d) -> NearestNe ghbors(
            prodS ms.map {
              case (consu r d: Str ng, (s m: Float, prod d: Str ng)) =>
                Ne ghbor(ne ghbor = Ent yKey(prod d), s m lar y = So (-s m.toDouble))
            }
          )
      }
  }

  /**
   * Calculate t  nearest ne ghbors exhaust vely bet en two ent y embedd ngs us ng one as query and ot r as t  search space.
   * @param queryEmbedd ngs ent y embedd ngs for quer es
   * @param searchSpaceEmbedd ngs ent y embedd ngs for search space
   * @param  tr c d stance  tr c
   * @param numNe ghbors number of ne ghbors
   * @param query dsF lter opt onal query  ds to f lter to query ent y embedd ngs
   * @param reducers number of reducers for group ng
   * @param  sSearchSpaceLarger Used for opt m zat on:  s t  search space larger than t  query space?  gnored  f numOfSearchGroups > 1.
   * @param numOfSearchGroups   d v de t  search space  nto t se groups (randomly). Useful w n t  search space  s too large. Overr des  sSearchSpaceLarger.
   * @param numRepl cas Each search group w ll be respons ble for 1/numRepl cas queryE bedd ngs.
   *                    T  m ght speed up t  search w n t  s ze of t   ndex embedd ngs  s
   *                    large.
   * @tparam A type of query ent y
   * @tparam B type of search space ent y
   * @tparam D type of d stance
   */
  def f ndNearestNe ghb s[A <: Ent y d, B <: Ent y d, D <: D stance[D]](
    queryEmbedd ngs: TypedP pe[Embedd ngW hEnt y[A]],
    searchSpaceEmbedd ngs: TypedP pe[Embedd ngW hEnt y[B]],
     tr c:  tr c[D],
    numNe ghbors:  nt = 10,
    query dsF lter: Opt on[TypedP pe[A]] = Opt on.empty,
    reducers:  nt = 100,
    mappers:  nt = 100,
     sSearchSpaceLarger: Boolean = true,
    numOfSearchGroups:  nt = 1,
    numRepl cas:  nt = 1,
    useCounters: Boolean = true
  )(
     mpl c  order ng: Order ng[A],
    u d: Un que D
  ): TypedP pe[(A, Seq[(B, D)])] = {
    val f lteredQueryEmbedd ngs = query dsF lter match {
      case So (f lter) => {
        queryEmbedd ngs.groupBy(_.ent y d).hashJo n(f lter.asKeys).map {
          case (x, (embedd ng, _)) => embedd ng
        }
      }
      case None => queryEmbedd ngs
    }

     f (numOfSearchGroups > 1) {
      val  ndex ngStrategy = BruteForce ndex ngStrategy( tr c)
      f ndNearestNe ghb sW h ndex ngStrategy(
        queryEmbedd ngs,
        searchSpaceEmbedd ngs,
        numNe ghbors,
        numOfSearchGroups,
         ndex ngStrategy,
        numRepl cas,
        So (reducers),
        useCounters = useCounters
      )
    } else {
      f ndNearestNe ghb sV aCross(
        f lteredQueryEmbedd ngs,
        searchSpaceEmbedd ngs,
         tr c,
        numNe ghbors,
        reducers,
        mappers,
         sSearchSpaceLarger)
    }
  }

  /**
   * Calculate t  nearest ne ghbors us ng t  spec f ed  ndex ng strategy bet en two ent y
   * embedd ngs us ng one as query and ot r as t  search space.
   * @param queryEmbedd ngs ent y embedd ngs for quer es
   * @param searchSpaceEmbedd ngs ent y embedd ngs for search space.   should be able to f 
   *                              searchSpaceEmbedd ngs.s ze / numOfSearchGroups  nto  mory.
   * @param numNe ghbors number of ne ghbors
   * @param reducersOpt on number of reducers for t  f nal sortedTake.
   * @param numOfSearchGroups   d v de t  search space  nto t se groups (randomly). Useful w n
   *                          t  search space  s too large. Search groups are shards. Choose t 
   *                          number by ensur ng searchSpaceEmbedd ngs.s ze / numOfSearchGroups
   *                          embedd ngs w ll f   nto  mory.
   * @param numRepl cas Each search group w ll be respons ble for 1/numRepl cas queryE bedd ngs.
   *                    By  ncreas ng t  number,   can parallel ze t  work and reduce end to end
   *                    runn ng t  s.
   * @param  ndex ngStrategy How   w ll search for nearest ne ghbors w h n a search group
   * @param queryShards one step   have  s to fan out t  query embedd ngs.   create one entry
   *                    per search group.  f numOfSearchGroups  s large, t n t  fan out can take
   *                    a long t  .   can shard t  query shard f rst to parallel ze t 
   *                    process. One way to est mate what value to use:
   *                    queryEmbedd ngs.s ze * numOfSearchGroups / queryShards should be around 1GB.
   * @param searchSpaceShards t  param  s s m lar to queryShards. Except   shards t  search
   *                          space w n numRepl cas  s too large. One way to est mate what value
   *                          to use: searchSpaceEmbedd ngs.s ze * numRepl cas / searchSpaceShards
   *                          should be around 1GB.
   * @tparam A type of query ent y
   * @tparam B type of search space ent y
   * @tparam D type of d stance
   * @return a p pe keyed by t   ndex embedd ng. T  values are t  l st of numNe ghbors nearest
   *         ne ghbors along w h t  r d stances.
   */
  def f ndNearestNe ghb sW h ndex ngStrategy[A <: Ent y d, B <: Ent y d, D <: D stance[D]](
    queryEmbedd ngs: TypedP pe[Embedd ngW hEnt y[A]],
    searchSpaceEmbedd ngs: TypedP pe[Embedd ngW hEnt y[B]],
    numNe ghbors:  nt,
    numOfSearchGroups:  nt,
     ndex ngStrategy:  ndex ngStrategy[D],
    numRepl cas:  nt = 1,
    reducersOpt on: Opt on[ nt] = None,
    queryShards: Opt on[ nt] = None,
    searchSpaceShards: Opt on[ nt] = None,
    useCounters: Boolean = true
  )(
     mpl c  order ng: Order ng[A],
    u d: Un que D
  ): UnsortedGrouped[A, Seq[(B, D)]] = {

     mpl c  val ord: Order ng[NNKey] = Order ng.by(NNKey.unapply)

    val ent yEmbedd ngs = searchSpaceEmbedd ngs.map { embedd ng: Embedd ngW hEnt y[B] =>
      val ent yEmbedd ng =
        Ent yEmbedd ng(embedd ng.ent y d, Embedd ng(embedd ng.embedd ng.toArray))
      ent yEmbedd ng
    }

    val shardedSearchSpace = shard(ent yEmbedd ngs, searchSpaceShards)

    val groupedSearchSpaceEmbedd ngs = shardedSearchSpace
      .flatMap { ent yEmbedd ng =>
        val searchGroup = Random.next nt(numOfSearchGroups)
        (0 unt l numRepl cas).map { repl ca =>
          (NNKey(searchGroup, repl ca, So (numRepl cas)), ent yEmbedd ng)
        }
      }

    val shardedQuer es = shard(queryEmbedd ngs, queryShards)

    val groupedQueryEmbedd ngs = shardedQuer es
      .flatMap { ent y =>
        val repl ca = Random.next nt(numRepl cas)
        (0 unt l numOfSearchGroups).map { searchGroup =>
          (NNKey(searchGroup, repl ca, So (numRepl cas)), ent y)
        }
      }.group
      .w hReducers(reducersOpt on.getOrElse(numOfSearchGroups * numRepl cas))

    val numberAnn ndexQuer es = Stat("NumberAnn ndexQuer es")
    val ann ndexQueryTotalMs = Stat("Ann ndexQueryTotalMs")
    val number ndexBu lds = Stat("Number ndexBu lds")
    val ann ndexBu ldTotalMs = Stat("Ann ndexBu ldTotalMs")
    val groupedKnn = groupedQueryEmbedd ngs
      .cogroup(groupedSearchSpaceEmbedd ngs) {
        case (_, query er, searchSpace er) =>
          // T   ndex bu ld happens numRepl cas t  s.  deally   could ser al ze t  queryable.
          // And only bu ld t   ndex once per search group.
          // T   ssues w h that now are:
          // - T  HNSW queryable  s not ser al zable  n scald ng
          // - T  way that map reduce works requ res that t re  s a job that wr e out t  search
          //   space embedd ngs numRepl cas t  s.  n t  current setup,   can do that by shard ng
          //   t  embedd ngs f rst and t n fann ng out. But  f   had a s ngle queryable,   would
          //   not be able to shard   eas ly and wr  ng t  out would take a long t  .
          val  ndexBu ldStartT   = System.currentT  M ll s()
          val queryable =  ndex ngStrategy.bu ld ndex(searchSpace er)
           f (useCounters) {
            number ndexBu lds. nc()
            ann ndexBu ldTotalMs. ncBy(System.currentT  M ll s() -  ndexBu ldStartT  )
          }
          query er.flatMap { query =>
            val queryStartT   = System.currentT  M ll s()
            val embedd ng = Embedd ng(query.embedd ng.toArray)
            val result = Awa .result(
              queryable.queryW hD stance(embedd ng, numNe ghbors)
            )
            val queryToTopNe ghbors = result
              .map { ne ghbor =>
                (query.ent y d, (ne ghbor.ne ghbor, ne ghbor.d stance))
              }
             f (useCounters) {
              numberAnn ndexQuer es. nc()
              ann ndexQueryTotalMs. ncBy(System.currentT  M ll s() - queryStartT  )
            }
            queryToTopNe ghbors
          }
      }
      .values
      .group

    val groupedKnnW hReducers = reducersOpt on
      .map { reducers =>
        groupedKnn
          .w hReducers(reducers)
      }.getOrElse(groupedKnn)

    groupedKnnW hReducers
      .sortedTake(numNe ghbors) {
        Order ng
          .by[(B, D), D] {
            case (_, d stance) => d stance
          }
      }
  }

  pr vate[t ] def shard[T](
    p pe: TypedP pe[T],
    numberOfShards: Opt on[ nt]
  ): TypedP pe[T] = {
    numberOfShards
      .map { shards =>
        p pe.shard(shards)
      }.getOrElse(p pe)
  }

  pr vate[t ] def f ndNearestNe ghb sV aCross[A <: Ent y d, B <: Ent y d, D <: D stance[D]](
    queryEmbedd ngs: TypedP pe[Embedd ngW hEnt y[A]],
    searchSpaceEmbedd ngs: TypedP pe[Embedd ngW hEnt y[B]],
     tr c:  tr c[D],
    numNe ghbors:  nt,
    reducers:  nt,
    mappers:  nt,
     sSearchSpaceLarger: Boolean
  )(
     mpl c  order ng: Order ng[A]
  ): TypedP pe[(A, Seq[(B, D)])] = {

    val crossed: TypedP pe[(A, (B, D))] =  f ( sSearchSpaceLarger) {
      searchSpaceEmbedd ngs
        .shard(mappers)
        .cross(queryEmbedd ngs).map {
          case (searchSpaceEmbedd ng, queryEmbedd ng) =>
            val d stance =  tr c.d stance(searchSpaceEmbedd ng.embedd ng, queryEmbedd ng.embedd ng)
            (queryEmbedd ng.ent y d, (searchSpaceEmbedd ng.ent y d, d stance))
        }
    } else {
      queryEmbedd ngs
        .shard(mappers)
        .cross(searchSpaceEmbedd ngs).map {
          case (queryEmbedd ng, searchSpaceEmbedd ng) =>
            val d stance =  tr c.d stance(searchSpaceEmbedd ng.embedd ng, queryEmbedd ng.embedd ng)
            (queryEmbedd ng.ent y d, (searchSpaceEmbedd ng.ent y d, d stance))
        }
    }

    crossed
      .groupBy(_._1)
      .w hReducers(reducers)
      .sortedTake(numNe ghbors) {
        Order ng
          .by[(A, (B, D)), D] {
            case (_, (_, d stance)) => d stance
          } // Sort by d stance  tr c  n ascend ng order
      }.map {
        case (query d, ne ghbors) =>
          (query d, ne ghbors.map(_._2))
      }
  }

  /**
   * Convert nearest ne ghbors to str ng format.
   * By default format would be (query d  ne ghb  d:d stance  ne ghb  d:d stance .....)  n ascend ng order of d stance.
   * @param nearestNe ghbors nearest ne ghbors tuple  n form of (query d, Seq[(ne ghbor d, d stance)]
   * @param queryEnt yK nd ent y k nd of query
   * @param ne ghborEnt yK nd ent y k nd of search space/ne ghbors
   * @param  dD stanceSeparator Str ng separator to separate a s ngle ne ghbor d and d stance. Default to colon (:)
   * @param ne ghborSeparator Str ng operator to separate ne ghbors. Default to tab
   * @tparam A type of query ent y
   * @tparam B type of search space ent y
   * @tparam D type of d stance
   */
  def nearestNe ghborsToStr ng[A <: Ent y d, B <: Ent y d, D <: D stance[D]](
    nearestNe ghbors: (A, Seq[(B, D)]),
    queryEnt yK nd: Ent yK nd[A],
    ne ghborEnt yK nd: Ent yK nd[B],
     dD stanceSeparator: Str ng = ":",
    ne ghborSeparator: Str ng = "\t"
  ): Str ng = {
    val (query d, ne ghbors) = nearestNe ghbors
    val formattedNe ghbors = ne ghbors.map {
      case (ne ghb  d, d stance) =>
        s"${ne ghborEnt yK nd.str ng nject on.apply(ne ghb  d)}$ dD stanceSeparator${d stance.d stance}"
    }
    (queryEnt yK nd.str ng nject on.apply(query d) +: formattedNe ghbors)
      .mkStr ng(ne ghborSeparator)
  }

  pr vate[t ] case class NNKey(
    searchGroup:  nt,
    repl ca:  nt,
    maxRepl ca: Opt on[ nt] = None) {
    overr de def hashCode():  nt =
      maxRepl ca.map(_ * searchGroup + repl ca).getOrElse(super.hashCode())
  }
}
