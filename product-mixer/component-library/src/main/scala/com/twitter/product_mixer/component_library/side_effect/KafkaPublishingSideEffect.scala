package com.tw ter.product_m xer.component_l brary.s de_effect

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.StorageUn Ops._
 mport com.tw ter.f natra.kafka.producers.F nagleKafkaProducerBu lder
 mport com.tw ter.f natra.kafka.producers.KafkaProducerBase
 mport com.tw ter.f natra.kafka.producers.Tw terKafkaProducerConf g
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.StorageUn 
 mport org.apac .kafka.cl ents.producer.ProducerRecord
 mport org.apac .kafka.common.ser al zat on.Ser al zer
 mport org.apac .kafka.common.record.Compress onType

/**
 * T  Kafka publ sh ng s de effect.
 * T  class creates a Kafka producer w h prov ded and default para ters.
 * Note that callers may not prov de arb rary params as t  class w ll do val d y c ck on so 
 * params, e.g. maxBlock, to make sure    s safe for onl ne serv ces.
 *
 * PLEASE NOTE: caller needs to add t  follow ng to t  Aurora f le to successfully enable t  TLS
 * '-com.tw ter.f natra.kafka.producers.pr nc pal={{role}}',
 *
 * @tparam K type of t  key
 * @tparam V type of t  value
 * @tparam Query p pel ne query
 */
tra  KafkaPubl sh ngS deEffect[K, V, Query <: P pel neQuery, ResponseType <: HasMarshall ng]
    extends P pel neResultS deEffect[Query, ResponseType] {

  /**
   * Kafka servers l st.    s usually a W lyNs na  at Tw ter
   */
  val bootstrapServer: Str ng

  /**
   * T  serde of t  key
   */
  val keySerde: Ser al zer[K]

  /**
   * T  serde of t  value
   */
  val valueSerde: Ser al zer[V]

  /**
   * An  d str ng to pass to t  server w n mak ng requests.
   * T  purpose of t   s to be able to track t  s ce of requests beyond just  p/port by
   * allow ng a log cal appl cat on na  to be  ncluded  n server-s de request logg ng.
   */
  val cl ent d: Str ng

  /**
   * T  conf gurat on controls how long <code>KafkaProducer.send()</code> and
   * <code>KafkaProducer.part  onsFor()</code> w ll block.
   * T se  thods can be blocked e  r because t  buffer  s full or  tadata unava lable.
   * Block ng  n t  user-suppl ed ser al zers or part  oner w ll not be counted aga nst t  t  out.
   *
   * Set 200ms by default to not block ng t  thread too long wh ch  s cr  cal to most ProM xer
   * po red serv ces. Please note that t re  s a hard l m  c ck of not greater than 1 second.
   *
   */
  val maxBlock: Durat on = 200.m ll seconds

  /**
   * Retr es due to broker fa lures, etc., may wr e dupl cates of t  retr ed  ssage  n t 
   * stream. Note that enabl ng  dempotence requ res
   * <code> MAX_ N_FL GHT_REQUESTS_PER_CONNECT ON </code> to be less than or equal to 5,
   * <code> RETR ES_CONF G </code> to be greater than 0 and <code> ACKS_CONF G </code>
   * must be 'all'.  f t se values are not expl c ly set by t  user, su able values w ll be
   * chosen.  f  ncompat ble values are set, a <code>Conf gExcept on</code> w ll be thrown.
   *
   * false by default, sett ng to true may  ntroduce  ssues to brokers s nce brokers w ll keep
   * track ng all requests wh ch  s res ce expens ve.
   */
  val  dempotence: Boolean = false

  /**
   * T  producer w ll attempt to batch records toget r  nto fe r requests w never mult ple
   * records are be ng sent to t  sa  part  on. T   lps performance on both t  cl ent and
   * t  server. T  conf gurat on controls t  default batch s ze  n bytes.
   * No attempt w ll be made to batch records larger than t  s ze.
   * Requests sent to brokers w ll conta n mult ple batc s, one for each part  on w h data
   * ava lable to be sent. A small batch s ze w ll make batch ng less common and may reduce
   * throughput (a batch s ze of zero w ll d sable batch ng ent rely).
   * A very large batch s ze may use  mory a b  more wastefully as   w ll always allocate a
   * buffer of t  spec f ed batch s ze  n ant c pat on of add  onal records.
   *
   * Default 16KB wh ch co s from Kafka's default
   */
  val batchS ze: StorageUn  = 16.k lobytes

  /**
   * T  producer groups toget r any records that arr ve  n bet en request transm ss ons  nto
   * a s ngle batc d request. "Normally t  occurs only under load w n records arr ve faster
   * than t y can be sent out. Ho ver  n so  c rcumstances t  cl ent may want to reduce t 
   * number of requests even under moderate load. T  sett ng accompl s s t  by add ng a
   * small amount of art f c al delay&mdash;that  s, rat r than  m d ately send ng out a record
   * t  producer w ll wa  for up to t  g ven delay to allow ot r records to be sent so that
   * t  sends can be batc d toget r. T  can be thought of as analogous to Nagle's algor hm
   *  n TCP. T  sett ng g ves t  upper bound on t  delay for batch ng: once   get
   * BATCH_S ZE_CONF G worth of records for a part  on   w ll be sent  m d ately regardless
   * of t  sett ng, ho ver  f   have fe r than t  many bytes accumulated for t 
   * part  on   w ll 'l nger' for t  spec f ed t   wa  ng for more records to show up.
   * T  sett ng defaults to 0 ( .e. no delay). Sett ng L NGER_MS_CONF G=5, for example,
   * would have t  effect of reduc ng t  number of requests sent but would add up to 5ms of
   * latency to records sent  n t  absence of load.
   *
   * Default 0ms, wh ch  s Kafka's default.  f t  record s ze  s much larger than t  batchS ze,
   *   may cons der to enlarge both batchS ze and l nger to have better compress on (only w n
   * compress on  s enabled.)
   */
  val l nger: Durat on = 0.m ll seconds

  /**
   * T  total bytes of  mory t  producer can use to buffer records wa  ng to be sent to t 
   * server.  f records are sent faster than t y can be del vered to t  server t  producer
   * w ll block for MAX_BLOCK_MS_CONF G after wh ch   w ll throw an except on.
   * T  sett ng should correspond roughly to t  total  mory t  producer w ll use, but  s not
   * a hard bound s nce not all  mory t  producer uses  s used for buffer ng.
   * So  add  onal  mory w ll be used for compress on ( f compress on  s enabled) as  ll as
   * for ma nta n ng  n-fl ght requests.
   *
   * Default 32MB wh ch  s Kafka's default. Please cons der to enlarge t  value  f t  EPS and
   * t  per-record s ze  s large (m ll ons EPS w h >1KB per-record s ze)  n case t  broker has
   *  ssues (wh ch f lls t  buffer pretty qu ckly.)
   */
  val buffer moryS ze: StorageUn  = 32. gabytes

  /**
   * Producer compress on type
   *
   * Default LZ4 wh ch  s a good tradeoff bet en compress on and eff c ency.
   * Please be careful of choos ng ZSTD, wh ch t  compress on rate  s better   m ght  ntroduce
   * huge burden to brokers once t  top c  s consu d, wh ch needs decompress on at t  broker s de.
   */
  val compress onType: Compress onType = Compress onType.LZ4

  /**
   * Sett ng a value greater than zero w ll cause t  cl ent to resend any request that fa ls
   * w h a potent ally trans ent error
   *
   * Default set to 3, to  ntent onally reduce t  retr es.
   */
  val retr es:  nt = 3

  /**
   * T  amount of t   to wa  before attempt ng to retry a fa led request to a g ven top c
   * part  on. T  avo ds repeatedly send ng requests  n a t ght loop under so  fa lure
   * scenar os
   */
  val retryBackoff: Durat on = 1.second

  /**
   * T  conf gurat on controls t  max mum amount of t   t  cl ent w ll wa 
   * for t  response of a request.  f t  response  s not rece ved before t  t  out
   * elapses t  cl ent w ll resend t  request  f necessary or fa l t  request  f
   * retr es are exhausted.
   *
   * Default 5 seconds wh ch  s  ntent onally low but not too low.
   * S nce Kafka's publ sh ng  s async t   s  n general safe (as long as t  buffer m  s not full.)
   */
  val requestT  out: Durat on = 5.seconds

  requ re(
    maxBlock. nM ll seconds <= 1000,
    "   ntent onally set t  maxBlock to be smaller than 1 second to not block t  thread for too long!")

  lazy val kafkaProducer: KafkaProducerBase[K, V] = {
    val jaasConf g = Tw terKafkaProducerConf g().conf gMap
    val bu lder = F nagleKafkaProducerBu lder[K, V]()
      .keySer al zer(keySerde)
      .valueSer al zer(valueSerde)
      .dest(bootstrapServer, 1.second) // NOTE: t   thod blocks!
      .cl ent d(cl ent d)
      .maxBlock(maxBlock)
      .batchS ze(batchS ze)
      .l nger(l nger)
      .buffer moryS ze(buffer moryS ze)
      .maxRequestS ze(4. gabytes)
      .compress onType(compress onType)
      .enable dempotence( dempotence)
      .max nFl ghtRequestsPerConnect on(5)
      .retr es(retr es)
      .retryBackoff(retryBackoff)
      .requestT  out(requestT  out)
      .w hConf g("acks", "all")
      .w hConf g("del very.t  out.ms", requestT  out + l nger)

    bu lder.w hConf g(jaasConf g).bu ld()
  }

  /**
   * Bu ld t  record to be publ s d to Kafka from query, select ons and response
   * @param query P pel neQuery
   * @param selectedCand dates Result after Selectors are executed
   * @param rema n ngCand dates Cand dates wh ch  re not selected
   * @param droppedCand dates Cand dates dropped dur ng select on
   * @param response Result after Unmarshall ng
   * @return A sequence of to-be-publ s d ProducerRecords
   */
  def bu ldRecords(
    query: Query,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: ResponseType
  ): Seq[ProducerRecord[K, V]]

  f nal overr de def apply(
     nputs: P pel neResultS deEffect. nputs[Query, ResponseType]
  ): St ch[Un ] = {
    val records = bu ldRecords(
      query =  nputs.query,
      selectedCand dates =  nputs.selectedCand dates,
      rema n ngCand dates =  nputs.rema n ngCand dates,
      droppedCand dates =  nputs.droppedCand dates,
      response =  nputs.response
    )

    St ch
      .collect(
        records
          .map { record =>
            St ch.callFuture(kafkaProducer.send(record))
          }
      ).un 
  }
}
