package com.tw ter.ho _m xer.product.for_ .s de_effect

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class ServedCand dateKeysKafkaS deEffectBu lder @ nject() (
   njectedServ ce dent f er: Serv ce dent f er) {
  def bu ld(
    s ce dent f ers: Set[Cand dateP pel ne dent f er]
  ): ServedCand dateKeysKafkaS deEffect = {
    val top c =  njectedServ ce dent f er.env ron nt.toLo rCase match {
      case "prod" => "tq_ct_served_cand date_keys"
      case _ => "tq_ct_served_cand date_keys_stag ng"
    }
    new ServedCand dateKeysKafkaS deEffect(top c, s ce dent f ers)
  }
}
