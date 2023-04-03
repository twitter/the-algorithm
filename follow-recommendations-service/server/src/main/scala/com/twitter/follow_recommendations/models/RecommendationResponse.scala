packagelon com.twittelonr.follow_reloncommelonndations.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}
import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Reloncommelonndation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling

caselon class ReloncommelonndationRelonsponselon(reloncommelonndations: Selonq[Reloncommelonndation]) elonxtelonnds HasMarshalling {
  lazy val toThrift: t.ReloncommelonndationRelonsponselon =
    t.ReloncommelonndationRelonsponselon(reloncommelonndations.map(_.toThrift))

  lazy val toOfflinelonThrift: offlinelon.OfflinelonReloncommelonndationRelonsponselon =
    offlinelon.OfflinelonReloncommelonndationRelonsponselon(reloncommelonndations.map(_.toOfflinelonThrift))
}
