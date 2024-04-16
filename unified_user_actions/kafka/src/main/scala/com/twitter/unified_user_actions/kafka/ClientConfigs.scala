package com.tw ter.un f ed_user_act ons.kafka

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.StorageUn Ops._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.StorageUn 
 mport org.apac .kafka.common.record.Compress onType

object Cl entConf gs {
  f nal val kafkaBootstrapServerConf g = "kafka.bootstrap.servers"
  f nal val kafkaBootstrapServer lp: Str ng =
    """Kafka servers l st.    s usually a W lyNs na  at Tw ter
    """.str pMarg n

  f nal val kafkaBootstrapServerRemoteDestConf g = "kafka.bootstrap.servers.remote.dest"
  f nal val kafkaBootstrapServerRemoteDest lp: Str ng =
    """Dest nat on Kafka servers,  f t  s nk cluster  s d fferent from t  s ce cluster,
      | .e., read from one cluster and output to anot r cluster
    """.str pMarg n

  f nal val kafkaAppl cat on dConf g = "kafka.appl cat on. d"
  f nal val kafkaAppl cat on d lp: Str ng =
    """An  dent f er for t  Kafka appl cat on. Must be un que w h n t  Kafka cluster
    """.str pMarg n

  // Processor  n general
  f nal val enableTrustStore = "kafka.trust.store.enable"
  f nal val enableTrustStoreDefault = true
  f nal val enableTrustStore lp = "W t r to enable trust store locat on"

  f nal val trustStoreLocat onConf g = "kafka.trust.store.locat on"
  f nal val trustStoreLocat onDefault = "/etc/tw_truststore/ ssag ng/kafka/cl ent.truststore.jks"
  f nal val trustStoreLocat on lp = "trust store locat on"

  f nal val kafkaMaxPend ngRequestsConf g = "kafka.max.pend ng.requests"
  f nal val kafkaMaxPend ngRequests lp = "t  max mum number of concurrent pend ng requests."

  f nal val kafkaWorkerThreadsConf g = "kafka.worker.threads"
  f nal val kafkaWorkerThreads lp =
    """T  has  an ng that  s dependent on t  value of {@l nk usePerPart  onThreadPool} -
      |  f that  s false, t   s t  number of parallel worker threads that w ll execute t  processor funct on.
      |  f that  s true, t   s t  number of parallel worker threads for each part  on. So t  total number of
      | threads w ll be {@l nk workerThreads} * number_of_part  ons.
      |""".str pMarg n

  f nal val retr esConf g = "kafka.retr es"
  f nal val retr esDefault = 300
  f nal val retr es lp: Str ng =
    """Sett ng a value greater than zero w ll cause t  cl ent to resend any request that fa ls
      |w h a potent ally trans ent error
    """.str pMarg n

  f nal val retryBackoffConf g = "kafka.retry.backoff"
  f nal val retryBackoffDefault: Durat on = 1.seconds
  f nal val retryBackoff lp: Str ng =
    """T  amount of t   to wa  before attempt ng to retry a fa led request to a g ven top c
      |part  on. T  avo ds repeatedly send ng requests  n a t ght loop under so  fa lure
      |scenar os
    """.str pMarg n

  // Kafka Producer
  f nal val producerCl ent dConf g = "kafka.producer.cl ent. d"
  f nal val producerCl ent d lp: Str ng =
    """T  cl ent  d of t  Kafka producer, requ red for producers.
    """.str pMarg n

  f nal val producer dempotenceConf g = "kafka.producer. dempotence"
  f nal val producer dempotenceDefault: Boolean = false
  f nal val producer dempotence lp: Str ng =
    """"retr es due to broker fa lures, etc., may wr e dupl cates of t  retr ed  ssage  n t 
       stream. Note that enabl ng  dempotence requ res
       <code> MAX_ N_FL GHT_REQUESTS_PER_CONNECT ON </code> to be less than or equal to 5,
       <code> RETR ES_CONF G </code> to be greater than 0 and <code> ACKS_CONF G </code>
       must be 'all'.  f t se values are not expl c ly set by t  user, su able values w ll be
       chosen.  f  ncompat ble values are set, a <code>Conf gExcept on</code> w ll be thrown.
    """.str pMarg n

  f nal val producerBatchS zeConf g = "kafka.producer.batch.s ze"
  f nal val producerBatchS zeDefault: StorageUn  = 512.k lobytes
  f nal val producerBatchS ze lp: Str ng =
    """T  producer w ll attempt to batch records toget r  nto fe r requests w never mult ple
      |records are be ng sent to t  sa  part  on. T   lps performance on both t  cl ent and
      |t  server. T  conf gurat on controls t  default batch s ze  n bytes.
      |No attempt w ll be made to batch records larger than t  s ze.
      |Requests sent to brokers w ll conta n mult ple batc s, one for each part  on w h data
      |ava lable to be sent. A small batch s ze w ll make batch ng less common and may reduce
      |throughput (a batch s ze of zero w ll d sable batch ng ent rely).
      |A very large batch s ze may use  mory a b  more wastefully as   w ll always allocate a
      |buffer of t  spec f ed batch s ze  n ant c pat on of add  onal records.
    """.str pMarg n

  f nal val producerBuffer mConf g = "kafka.producer.buffer. m"
  f nal val producerBuffer mDefault: StorageUn  = 256. gabytes
  f nal val producerBuffer m lp: Str ng =
    """T  total bytes of  mory t  producer can use to buffer records wa  ng to be sent to t 
      |server.  f records are sent faster than t y can be del vered to t  server t  producer
      |w ll block for MAX_BLOCK_MS_CONF G after wh ch   w ll throw an except on.
      |T  sett ng should correspond roughly to t  total  mory t  producer w ll use, but  s not
      |a hard bound s nce not all  mory t  producer uses  s used for buffer ng.
      |So  add  onal  mory w ll be used for compress on ( f compress on  s enabled) as  ll as
      |for ma nta n ng  n-fl ght requests.
    """.str pMarg n

  f nal val producerL ngerConf g = "kafka.producer.l nger"
  f nal val producerL ngerDefault: Durat on = 100.m ll seconds
  f nal val producerL nger lp: Str ng =
    """T  producer groups toget r any records that arr ve  n bet en request transm ss ons  nto
      |a s ngle batc d request. "Normally t  occurs only under load w n records arr ve faster
      |than t y can be sent out. Ho ver  n so  c rcumstances t  cl ent may want to reduce t 
      |number of requests even under moderate load. T  sett ng accompl s s t  by add ng a
      |small amount of art f c al delay&mdash;that  s, rat r than  m d ately send ng out a record
      |t  producer w ll wa  for up to t  g ven delay to allow ot r records to be sent so that
      |t  sends can be batc d toget r. T  can be thought of as analogous to Nagle's algor hm
      | n TCP. T  sett ng g ves t  upper bound on t  delay for batch ng: once   get
      |BATCH_S ZE_CONF G worth of records for a part  on   w ll be sent  m d ately regardless
      |of t  sett ng, ho ver  f   have fe r than t  many bytes accumulated for t 
      |part  on   w ll 'l nger' for t  spec f ed t   wa  ng for more records to show up.
      |T  sett ng defaults to 0 ( .e. no delay). Sett ng L NGER_MS_CONF G=5, for example,
      |would have t  effect of reduc ng t  number of requests sent but would add up to 5ms of
      |latency to records sent  n t  absence of load.
    """.str pMarg n

  f nal val producerRequestT  outConf g = "kafka.producer.request.t  out"
  f nal val producerRequestT  outDefault: Durat on = 30.seconds
  f nal val producerRequestT  out lp: Str ng =
    """"T  conf gurat on controls t  max mum amount of t   t  cl ent w ll wa 
      |for t  response of a request.  f t  response  s not rece ved before t  t  out
      |elapses t  cl ent w ll resend t  request  f necessary or fa l t  request  f
      |retr es are exhausted.
    """.str pMarg n

  f nal val compress onConf g = "kafka.producer.compress on.type"
  f nal val compress onDefault: Compress onTypeFlag = Compress onTypeFlag(Compress onType.NONE)
  f nal val compress on lp = "Producer compress on type"

  // Kafka Consu r
  f nal val kafkaGroup dConf g = "kafka.group. d"
  f nal val kafkaGroup d lp: Str ng =
    """T  group  dent f er for t  Kafka consu r
    """.str pMarg n

  f nal val kafkaComm  ntervalConf g = "kafka.comm . nterval"
  f nal val kafkaComm  ntervalDefault: Durat on = 10.seconds
  f nal val kafkaComm  nterval lp: Str ng =
    """T  frequency w h wh ch to save t  pos  on of t  processor.
    """.str pMarg n

  f nal val consu rMaxPollRecordsConf g = "kafka.max.poll.records"
  f nal val consu rMaxPollRecordsDefault:  nt = 1000
  f nal val consu rMaxPollRecords lp: Str ng =
    """T  max mum number of records returned  n a s ngle call to poll()
    """.str pMarg n

  f nal val consu rMaxPoll ntervalConf g = "kafka.max.poll. nterval"
  f nal val consu rMaxPoll ntervalDefault: Durat on = 5.m nutes
  f nal val consu rMaxPoll nterval lp: Str ng =
    """T  max mum delay bet en  nvocat ons of poll() w n us ng consu r group manage nt.
       T  places an upper bound on t  amount of t   that t  consu r can be  dle before fetch ng more records.
        f poll()  s not called before exp rat on of t  t  out, t n t  consu r  s cons dered fa led and t  group
       w ll rebalance  n order to reass gn t  part  ons to anot r  mber.
    """.str pMarg n

  f nal val consu rSess onT  outConf g = "kafka.sess on.t  out"
  f nal val consu rSess onT  outDefault: Durat on = 1.m nute
  f nal val consu rSess onT  out lp: Str ng =
    """T  t  out used to detect cl ent fa lures w n us ng Kafka's group manage nt fac l y.
       T  cl ent sends per od c  artbeats to  nd cate  s l veness to t  broker.
        f no  artbeats are rece ved by t  broker before t  exp rat on of t  sess on t  out, t n t  broker
       w ll remove t  cl ent from t  group and  n  ate a rebalance. Note that t  value must be  n t  allowable
       range as conf gured  n t  broker conf gurat on by group.m n.sess on.t  out.ms and group.max.sess on.t  out.ms.
    """.str pMarg n

  f nal val consu rFetchM nConf g = "kafka.consu r.fetch.m n"
  f nal val consu rFetchM nDefault: StorageUn  = 1.k lobyte
  f nal val consu rFetchM n lp: Str ng =
    """T  m n mum amount of data t  server should return for a fetch request.  f  nsuff c ent
      |data  s ava lable t  request w ll wa  for that much data to accumulate before ans r ng
      |t  request. T  default sett ng of 1 byte  ans that fetch requests are ans red as soon
      |as a s ngle byte of data  s ava lable or t  fetch request t  s out wa  ng for data to
      |arr ve. Sett ng t  to so th ng greater than 1 w ll cause t  server to wa  for larger
      |amounts of data to accumulate wh ch can  mprove server throughput a b  at t  cost of
      |so  add  onal latency.
    """.str pMarg n

  f nal val consu rFetchMaxConf g = "kafka.consu r.fetch.max"
  f nal val consu rFetchMaxDefault: StorageUn  = 1. gabytes
  f nal val consu rFetchMax lp: Str ng =
    """T  max mum amount of data t  server should return for a fetch request. Records are
      |fetc d  n batc s by t  consu r, and  f t  f rst record batch  n t  f rst non-empty
      |part  on of t  fetch  s larger than t  value, t  record batch w ll st ll be returned
      |to ensure that t  consu r can make progress. As such, t   s not a absolute max mum.
      |T  max mum record batch s ze accepted by t  broker  s def ned v a  ssage.max.bytes
      |(broker conf g) or max. ssage.bytes (top c conf g).
      |Note that t  consu r performs mult ple fetc s  n parallel.
    """.str pMarg n

  f nal val consu rRece veBufferS zeConf g = "kafka.consu r.rece ve.buffer.s ze"
  f nal val consu rRece veBufferS zeDefault: StorageUn  = 1. gabytes
  f nal val consu rRece veBufferS ze lp: Str ng =
    """T  s ze of t  TCP rece ve buffer (SO_RCVBUF) to use w n read ng data.
      | f t  value  s -1, t  OS default w ll be used.
    """.str pMarg n

  f nal val consu rAp T  outConf g = "kafka.consu r.ap .t  out"
  f nal val consu rAp T  outDefault: Durat on = 120.seconds
  f nal val consu rAp T  out lp: Str ng =
    """Spec f es t  t  out ( n m ll seconds) for consu r AP s that could block.
      |T  conf gurat on  s used as t  default t  out for all consu r operat ons that do
      |not expl c ly accept a <code>t  out</code> para ter.";
    """.str pMarg n
}
