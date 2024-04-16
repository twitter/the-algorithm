package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.thr ft.CompactThr ftCodec
 mport com.tw ter.cac .cl ent._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.storehaus.Wr ableStore
 mport com.tw ter.storehaus_ nternal.n ghthawk_kv.Cac Cl entN ghthawkConf g
 mport com.tw ter.storehaus_ nternal.n ghthawk_kv.N ghthawkStore
 mport com.tw ter.summ ngb rd.batch.Batch D
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.Aggregat onKey
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron.UserRe ndex ngN ghthawkWr ableDataRecordStore._
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logger
 mport java.n o.ByteBuffer
 mport java.ut l
 mport scala.ut l.Random

object UserRe ndex ngN ghthawkWr ableDataRecordStore {
   mpl c  val long nject on =  nject on.long2B gEnd an
   mpl c  val dataRecord nject on:  nject on[DataRecord, Array[Byte]] =
    CompactThr ftCodec[DataRecord]
  val arrayToByteBuffer =  nject on.connect[Array[Byte], ByteBuffer]
  val longToByteBuffer = long nject on.andT n(arrayToByteBuffer)
  val dataRecordToByteBuffer = dataRecord nject on.andT n(arrayToByteBuffer)

  def getBtreeStore(
    n ghthawkCac Conf g: Cac Cl entN ghthawkConf g,
    targetS ze:  nt,
    statsRece ver: StatsRece ver,
    tr mRate: Double
  ): UserRe ndex ngN ghthawkBtreeWr ableDataRecordStore =
    new UserRe ndex ngN ghthawkBtreeWr ableDataRecordStore(
      n ghthawkStore = N ghthawkStore[User d, T  stampMs, DataRecord](n ghthawkCac Conf g)
        .as nstanceOf[N ghthawkStore[User d, T  stampMs, DataRecord]],
      tableNa  = n ghthawkCac Conf g.table.toStr ng,
      targetS ze = targetS ze,
      statsRece ver = statsRece ver,
      tr mRate = tr mRate
    )

  def getHashStore(
    n ghthawkCac Conf g: Cac Cl entN ghthawkConf g,
    targetS ze:  nt,
    statsRece ver: StatsRece ver,
    tr mRate: Double
  ): UserRe ndex ngN ghthawkHashWr ableDataRecordStore =
    new UserRe ndex ngN ghthawkHashWr ableDataRecordStore(
      n ghthawkStore = N ghthawkStore[User d, Author d, DataRecord](n ghthawkCac Conf g)
        .as nstanceOf[N ghthawkStore[User d, Author d, DataRecord]],
      tableNa  = n ghthawkCac Conf g.table.toStr ng,
      targetS ze = targetS ze,
      statsRece ver = statsRece ver,
      tr mRate = tr mRate
    )

  def bu ldT  stampedByteBuffer(t  stamp: Long, bb: ByteBuffer): ByteBuffer = {
    val t  stampedBb = ByteBuffer.allocate(getLength(bb) + java.lang.Long.S ZE)
    t  stampedBb.putLong(t  stamp)
    t  stampedBb.put(bb)
    t  stampedBb
  }

  def extractT  stampFromT  stampedByteBuffer(bb: ByteBuffer): Long = {
    bb.getLong(0)
  }

  def extractValueFromT  stampedByteBuffer(bb: ByteBuffer): ByteBuffer = {
    val bytes = new Array[Byte](getLength(bb) - java.lang.Long.S ZE)
    ut l.Arrays.copyOfRange(bytes, java.lang.Long.S ZE, getLength(bb))
    ByteBuffer.wrap(bytes)
  }

  def transformAndBu ldKeyValueMapp ng(
    table: Str ng,
    user d: User d,
    author dsAndDataRecords: Seq[(Author d, DataRecord)]
  ): KeyValue = {
    val t  stamp = T  .now. nM ll s
    val pkey = longToByteBuffer(user d)
    val lkeysAndT  stampedValues = author dsAndDataRecords.map {
      case (author d, dataRecord) =>
        val lkey = longToByteBuffer(author d)
        // Create a byte buffer w h a prepended t  stamp to reduce deser al zat on cost
        // w n pars ng values.   only have to extract and deser al ze t  t  stamp  n t 
        // ByteBuffer  n order to sort t  value, as opposed to deser al z ng t  DataRecord
        // and hav ng to get a t  stamp feature value from t  DataRecord.
        val dataRecordBb = dataRecordToByteBuffer(dataRecord)
        val t  stampedValue = bu ldT  stampedByteBuffer(t  stamp, dataRecordBb)
        (lkey, t  stampedValue)
    }
    bu ldKeyValueMapp ng(table, pkey, lkeysAndT  stampedValues)
  }

  def bu ldKeyValueMapp ng(
    table: Str ng,
    pkey: ByteBuffer,
    lkeysAndT  stampedValues: Seq[(ByteBuffer, ByteBuffer)]
  ): KeyValue = {
    val lkeys = lkeysAndT  stampedValues.map { case (lkey, _) => lkey }
    val t  stampedValues = lkeysAndT  stampedValues.map { case (_, value) => value }
    val kv = KeyValue(
      key = Key(table = table, pkey = pkey, lkeys = lkeys),
      value = Value(t  stampedValues)
    )
    kv
  }

  pr vate def getLength(bb: ByteBuffer):  nt = {
    // capac y can be an over-est mate of t  actual length (rema n ng - start pos  on)
    // but  's t  safest to avo d overflows.
    bb.capac y()
  }
}

/**
 *  mple nts a NH store that stores aggregate feature DataRecords us ng user d as t  pr mary key.
 *
 * T  store re- ndexes user-author keyed real-t   aggregate (RTA) features on user d by
 * wr  ng to a user d pr mary key (pkey) and t  stamp secondary key (lkey). To fetch user-author
 * RTAs for a g ven user from cac , t  caller just needs to make a s ngle RPC for t  user d pkey.
 * T  downs de of a re- ndex ng store  s that   cannot store arb rar ly many secondary keys
 * under t  pr mary key. T  spec f c  mple ntat on us ng t  NH btree backend also mandates
 * mandates an order ng of secondary keys -   t refore use t  stamp as t  secondary key
 * as opposed to say author d.
 *
 * Note that a caller of t  btree backed NH re- ndex ng store rece ves back a response w re t 
 * secondary key  s a t  stamp. T  assoc ated value  s a DataRecord conta n ng user-author related
 * aggregate features wh ch was last updated at t  t  stamp. T  caller t refore needs to handle
 * t  response and dedupe on un que, most recent user-author pa rs.
 *
 * For a d scuss on on t  and ot r  mple ntat ons, please see:
 * https://docs.google.com/docu nt/d/1yVzAbQ_ kLqwSf230URxCJmSKj5yZr5dYv6TwBlQw18/ed 
 */
class UserRe ndex ngN ghthawkBtreeWr ableDataRecordStore(
  n ghthawkStore: N ghthawkStore[User d, T  stampMs, DataRecord],
  tableNa : Str ng,
  targetS ze:  nt,
  statsRece ver: StatsRece ver,
  tr mRate: Double = 0.1 // by default, tr m on 10% of puts
) extends Wr ableStore[(Aggregat onKey, Batch D), Opt on[DataRecord]] {

  pr vate val scope = getClass.getS mpleNa 
  pr vate val fa lures = statsRece ver.counter(scope, "fa lures")
  pr vate val log = Logger.getLogger(getClass)
  pr vate val random: Random = new Random(1729L)

  overr de def put(kv: ((Aggregat onKey, Batch D), Opt on[DataRecord])): Future[Un ] = {
    val ((aggregat onKey, _), dataRecordOpt) = kv
    // F re-and-forget below because t  store  self should just be a s de effect
    // as  's just mak ng re- ndexed wr es based on t  wr es to t  pr mary store.
    for {
      user d <- aggregat onKey.d screteFeaturesBy d.get(SharedFeatures.USER_ D.getFeature d)
      dataRecord <- dataRecordOpt
    } y eld {
      SR chDataRecord(dataRecord)
        .getFeatureValueOpt(TypedAggregateGroup.t  stampFeature)
        .map(_.toLong) // convert to Scala Long
        .map { t  stamp =>
          val tr m: Future[Un ] =  f (random.nextDouble <= tr mRate) {
            val tr mKey = Tr mKey(
              table = tableNa ,
              pkey = longToByteBuffer(user d),
              targetS ze = targetS ze,
              ascend ng = true
            )
            n ghthawkStore.cl ent.tr m(Seq(tr mKey)).un 
          } else {
            Future.Un 
          }
          //   should wa  for tr m to complete above
          val f reAndForget = tr m.before {
            val kvTuple = ((user d, t  stamp), So (dataRecord))
            n ghthawkStore.put(kvTuple)
          }

          f reAndForget.onFa lure {
            case e =>
              fa lures. ncr()
              log.error("Fa lure  n UserRe ndex ngN ghthawkHashWr ableDataRecordStore", e)
          }
        }
    }
    //  gnore f re-and-forget result above and s mply return
    Future.Un 
  }
}

/**
 *  mple nts a NH store that stores aggregate feature DataRecords us ng user d as t  pr mary key.
 *
 * T  store re- ndexes user-author keyed real-t   aggregate (RTA) features on user d by
 * wr  ng to a user d pr mary key (pkey) and author d secondary key (lkey). To fetch user-author
 * RTAs for a g ven user from cac , t  caller just needs to make a s ngle RPC for t  user d pkey.
 * T  downs de of a re- ndex ng store  s that   cannot store arb rar ly
 * many secondary keys under t  pr mary key.   have to l m  t m  n so  way;
 *  re,   do so by randomly (based on tr mRate)  ssu ng an HGETALL command (v a scan) to
 * retr eve t  whole hash, sort by oldest t  stamp, and t n remove t  oldest authors to keep
 * only targetS ze authors (aka tr m), w re targetS ze  s conf gurable.
 *
 * @note T  full hash returned from scan could be as large (or even larger) than targetS ze,
 * wh ch could  an many DataRecords to deser al ze, espec ally at h gh wr e qps.
 * To reduce deser al zat on cost post-scan,   use t  stamped values w h a prepended t  stamp
 *  n t  value ByteBuffer; t  allows us to only deser al ze t  t  stamp and not t  full
 * DataRecord w n sort ng. T   s necessary  n order to  dent fy t  oldest values to tr m.
 * W n   do a put for a new (user, author) pa r,   also wr e out t  stamped values.
 *
 * For a d scuss on on t  and ot r  mple ntat ons, please see:
 * https://docs.google.com/docu nt/d/1yVzAbQ_ kLqwSf230URxCJmSKj5yZr5dYv6TwBlQw18/ed 
 */
class UserRe ndex ngN ghthawkHashWr ableDataRecordStore(
  n ghthawkStore: N ghthawkStore[User d, Author d, DataRecord],
  tableNa : Str ng,
  targetS ze:  nt,
  statsRece ver: StatsRece ver,
  tr mRate: Double = 0.1 // by default, tr m on 10% of puts
) extends Wr ableStore[(Aggregat onKey, Batch D), Opt on[DataRecord]] {

  pr vate val scope = getClass.getS mpleNa 
  pr vate val scanM smatchErrors = statsRece ver.counter(scope, "scanM smatchErrors")
  pr vate val fa lures = statsRece ver.counter(scope, "fa lures")
  pr vate val log = Logger.getLogger(getClass)
  pr vate val random: Random = new Random(1729L)
  pr vate val arrayToByteBuffer =  nject on.connect[Array[Byte], ByteBuffer]
  pr vate val longToByteBuffer =  nject on.long2B gEnd an.andT n(arrayToByteBuffer)

  overr de def put(kv: ((Aggregat onKey, Batch D), Opt on[DataRecord])): Future[Un ] = {
    val ((aggregat onKey, _), dataRecordOpt) = kv
    // F re-and-forget below because t  store  self should just be a s de effect
    // as  's just mak ng re- ndexed wr es based on t  wr es to t  pr mary store.
    for {
      user d <- aggregat onKey.d screteFeaturesBy d.get(SharedFeatures.USER_ D.getFeature d)
      author d <- aggregat onKey.d screteFeaturesBy d.get(
        T  l nesSharedFeatures.SOURCE_AUTHOR_ D.getFeature d)
      dataRecord <- dataRecordOpt
    } y eld {
      val scanAndTr m: Future[Un ] =  f (random.nextDouble <= tr mRate) {
        val scanKey = ScanKey(
          table = tableNa ,
          pkey = longToByteBuffer(user d)
        )
        n ghthawkStore.cl ent.scan(Seq(scanKey)).flatMap { scanResults: Seq[Try[KeyValue]] =>
          scanResults. adOpt on
            .flatMap(_.toOpt on).map { keyValue: KeyValue =>
              val lkeys: Seq[ByteBuffer] = keyValue.key.lkeys
              // t se are t  stamped bytebuffers
              val t  stampedValues: Seq[ByteBuffer] = keyValue.value.values
              // t  should fa l loudly  f t   s not true.   would  nd cate
              // t re  s a m stake  n t  scan.
               f (lkeys.s ze != t  stampedValues.s ze) scanM smatchErrors. ncr()
              assert(lkeys.s ze == t  stampedValues.s ze)
               f (lkeys.s ze > targetS ze) {
                val numToRemove = targetS ze - lkeys.s ze
                // sort by oldest and take top k oldest and remove - t   s equ valent to a tr m
                val oldestKeys: Seq[ByteBuffer] = lkeys
                  .z p(t  stampedValues)
                  .map {
                    case (lkey, t  stampedValue) =>
                      val t  stamp = extractT  stampFromT  stampedByteBuffer(t  stampedValue)
                      (t  stamp, lkey)
                  }
                  .sortBy { case (t  stamp, _) => t  stamp }
                  .take(numToRemove)
                  .map { case (_, k) => k }
                val pkey = longToByteBuffer(user d)
                val key = Key(table = tableNa , pkey = pkey, lkeys = oldestKeys)
                // NOTE: `remove`  s a batch AP , and   group all lkeys  nto a s ngle batch (batch
                // s ze = s ngle group of lkeys = 1).  nstead,   could separate lkeys  nto smaller
                // groups and have batch s ze = number of groups, but t   s more complex.
                // Performance  mpl cat ons of batch ng vs non-batch ng need to be assessed.
                n ghthawkStore.cl ent
                  .remove(Seq(key))
                  .map { responses =>
                    responses.map(resp => n ghthawkStore.processValue(resp))
                  }.un 
              } else {
                Future.Un 
              }
            }.getOrElse(Future.Un )
        }
      } else {
        Future.Un 
      }
      //   should wa  for scan and tr m to complete above
      val f reAndForget = scanAndTr m.before {
        val kv = transformAndBu ldKeyValueMapp ng(tableNa , user d, Seq((author d, dataRecord)))
        n ghthawkStore.cl ent
          .put(Seq(kv))
          .map { responses =>
            responses.map(resp => n ghthawkStore.processValue(resp))
          }.un 
      }
      f reAndForget.onFa lure {
        case e =>
          fa lures. ncr()
          log.error("Fa lure  n UserRe ndex ngN ghthawkHashWr ableDataRecordStore", e)
      }
    }
    //  gnore f re-and-forget result above and s mply return
    Future.Un 
  }
}
