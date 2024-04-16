package com.tw ter.un f ed_user_act ons.serv ce.module

 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.un f ed_user_act ons.kafka.Cl entConf gs
 mport com.tw ter.un f ed_user_act ons.kafka.Compress onTypeFlag
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.StorageUn 
 mport com.tw ter.ut l.logg ng.Logg ng

object FlagsModule extends Tw terModule w h Logg ng {
  // Tw ter
  f nal val cluster = "cluster"

  // Requ red
  f nal val kafkaS ceCluster = Cl entConf gs.kafkaBootstrapServerConf g
  f nal val kafkaDestCluster = Cl entConf gs.kafkaBootstrapServerRemoteDestConf g
  f nal val kafkaS ceTop c = "kafka.s ce.top c"
  f nal val kafkaS nkTop cs = "kafka.s nk.top cs"
  f nal val kafkaGroup d = Cl entConf gs.kafkaGroup dConf g
  f nal val kafkaProducerCl ent d = Cl entConf gs.producerCl ent dConf g
  f nal val kafkaMaxPend ngRequests = Cl entConf gs.kafkaMaxPend ngRequestsConf g
  f nal val kafkaWorkerThreads = Cl entConf gs.kafkaWorkerThreadsConf g

  // Opt onal
  /// Aut nt cat on
  f nal val enableTrustStore = Cl entConf gs.enableTrustStore
  f nal val trustStoreLocat on = Cl entConf gs.trustStoreLocat onConf g

  /// Consu r
  f nal val comm  nterval = Cl entConf gs.kafkaComm  ntervalConf g
  f nal val maxPollRecords = Cl entConf gs.consu rMaxPollRecordsConf g
  f nal val maxPoll nterval = Cl entConf gs.consu rMaxPoll ntervalConf g
  f nal val sess onT  out = Cl entConf gs.consu rSess onT  outConf g
  f nal val fetchMax = Cl entConf gs.consu rFetchMaxConf g
  f nal val fetchM n = Cl entConf gs.consu rFetchM nConf g
  f nal val rece veBuffer = Cl entConf gs.consu rRece veBufferS zeConf g
  /// Producer
  f nal val batchS ze = Cl entConf gs.producerBatchS zeConf g
  f nal val l nger = Cl entConf gs.producerL ngerConf g
  f nal val buffer m = Cl entConf gs.producerBuffer mConf g
  f nal val compress onType = Cl entConf gs.compress onConf g
  f nal val retr es = Cl entConf gs.retr esConf g
  f nal val retryBackoff = Cl entConf gs.retryBackoffConf g
  f nal val requestT  out = Cl entConf gs.producerRequestT  outConf g

  // Tw ter
  flag[Str ng](
    na  = cluster,
     lp = "T  zone (or DC) that t  serv ce runs, used to potent ally f lter events"
  )

  // Requ red
  flag[Str ng](
    na  = kafkaS ceCluster,
     lp = Cl entConf gs.kafkaBootstrapServer lp
  )
  flag[Str ng](
    na  = kafkaDestCluster,
     lp = Cl entConf gs.kafkaBootstrapServerRemoteDest lp
  )
  flag[Str ng](
    na  = kafkaS ceTop c,
     lp = "Na  of t  s ce Kafka top c"
  )
  flag[Seq[Str ng]](
    na  = kafkaS nkTop cs,
     lp = "A l st of s nk Kafka top cs, separated by comma (,)"
  )
  flag[Str ng](
    na  = kafkaGroup d,
     lp = Cl entConf gs.kafkaGroup d lp
  )
  flag[Str ng](
    na  = kafkaProducerCl ent d,
     lp = Cl entConf gs.producerCl ent d lp
  )
  flag[ nt](
    na  = kafkaMaxPend ngRequests,
     lp = Cl entConf gs.kafkaMaxPend ngRequests lp
  )
  flag[ nt](
    na  = kafkaWorkerThreads,
     lp = Cl entConf gs.kafkaWorkerThreads lp
  )

  // Opt onal
  /// Aut nt cat on
  flag[Boolean](
    na  = enableTrustStore,
    default = Cl entConf gs.enableTrustStoreDefault,
     lp = Cl entConf gs.enableTrustStore lp
  )
  flag[Str ng](
    na  = trustStoreLocat on,
    default = Cl entConf gs.trustStoreLocat onDefault,
     lp = Cl entConf gs.trustStoreLocat on lp
  )

  /// Consu r
  flag[Durat on](
    na  = comm  nterval,
    default = Cl entConf gs.kafkaComm  ntervalDefault,
     lp = Cl entConf gs.kafkaComm  nterval lp
  )
  flag[ nt](
    na  = maxPollRecords,
    default = Cl entConf gs.consu rMaxPollRecordsDefault,
     lp = Cl entConf gs.consu rMaxPollRecords lp
  )
  flag[Durat on](
    na  = maxPoll nterval,
    default = Cl entConf gs.consu rMaxPoll ntervalDefault,
     lp = Cl entConf gs.consu rMaxPoll nterval lp
  )
  flag[Durat on](
    na  = sess onT  out,
    default = Cl entConf gs.consu rSess onT  outDefault,
     lp = Cl entConf gs.consu rSess onT  out lp
  )
  flag[StorageUn ](
    na  = fetchMax,
    default = Cl entConf gs.consu rFetchMaxDefault,
     lp = Cl entConf gs.consu rFetchMax lp
  )
  flag[StorageUn ](
    na  = fetchM n,
    default = Cl entConf gs.consu rFetchM nDefault,
     lp = Cl entConf gs.consu rFetchM n lp
  )
  flag[StorageUn ](
    na  = rece veBuffer,
    default = Cl entConf gs.consu rRece veBufferS zeDefault,
     lp = Cl entConf gs.consu rRece veBufferS ze lp
  )

  /// Producer
  flag[StorageUn ](
    na  = batchS ze,
    default = Cl entConf gs.producerBatchS zeDefault,
     lp = Cl entConf gs.producerBatchS ze lp
  )
  flag[Durat on](
    na  = l nger,
    default = Cl entConf gs.producerL ngerDefault,
     lp = Cl entConf gs.producerL nger lp
  )
  flag[StorageUn ](
    na  = buffer m,
    default = Cl entConf gs.producerBuffer mDefault,
     lp = Cl entConf gs.producerBuffer m lp
  )
  flag[Compress onTypeFlag](
    na  = compress onType,
    default = Cl entConf gs.compress onDefault,
     lp = Cl entConf gs.compress on lp
  )
  flag[ nt](
    na  = retr es,
    default = Cl entConf gs.retr esDefault,
     lp = Cl entConf gs.retr es lp
  )
  flag[Durat on](
    na  = retryBackoff,
    default = Cl entConf gs.retryBackoffDefault,
     lp = Cl entConf gs.retryBackoff lp
  )
  flag[Durat on](
    na  = requestT  out,
    default = Cl entConf gs.producerRequestT  outDefault,
     lp = Cl entConf gs.producerRequestT  out lp
  )
}
