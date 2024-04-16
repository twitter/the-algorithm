package com.tw ter.un f ed_user_act ons.cl ent.summ ngb rd

 mport com.tw ter.summ ngb rd.T  Extractor
 mport com.tw ter.summ ngb rd.storm.Storm
 mport com.tw ter.summ ngb rd_ nternal.s ces.App d
 mport com.tw ter.summ ngb rd_ nternal.s ces.S ceFactory
 mport com.tw ter.tor nta_ nternal.spout.Kafka2ScroogeSpoutWrapper
 mport com.tw ter.un f ed_user_act ons.cl ent.conf g.Cl entConf g
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.cl ent.conf g.KafkaConf gs

case class Un f edUserAct onsS ceScrooge(
  app d: App d,
  parallel sm:  nt,
  kafkaConf g: Cl entConf g = KafkaConf gs.ProdUn f edUserAct ons,
  sk pToLatest: Boolean = false,
  enableTls: Boolean = true)
    extends S ceFactory[Storm, Un f edUserAct on] {

  overr de def na : Str ng = "Un f edUserAct onsS ce"
  overr de def descr pt on: Str ng = "Un f ed User Act ons (UUA) events"

  // T  event t  stamps from summ ngb rd's perspect ve (cl ent),  s    nternally
  // outputted t  stamps (producer). T  ensures t  -cont nu y bet en t  cl ent and t 
  // producer.
  val t  Extractor: T  Extractor[Un f edUserAct on] = T  Extractor { e =>
    e.event tadata.rece vedT  stampMs
  }

  overr de def s ce = {
    Storm.s ce(
      Kafka2ScroogeSpoutWrapper(
        codec = Un f edUserAct on,
        cluster = kafkaConf g.cluster.na ,
        top c = kafkaConf g.top c,
        app d = app d.get,
        sk pToLatest = sk pToLatest,
        enableTls = enableTls
      ),
      So (parallel sm)
    )(t  Extractor)
  }
}
