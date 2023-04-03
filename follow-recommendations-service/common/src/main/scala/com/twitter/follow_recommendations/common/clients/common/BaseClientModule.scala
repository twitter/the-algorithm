packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.common

import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.thrift.Protocols
import com.twittelonr.follow_reloncommelonndations.common.constants.SelonrvicelonConstants._
import com.twittelonr.injelonct.thrift.modulelons.ThriftClielonntModulelon
import scala.relonflelonct.ClassTag

/**
 * basic clielonnt configurations that welon apply for all of our clielonnts go in helonrelon
 */
abstract class BaselonClielonntModulelon[T: ClassTag] elonxtelonnds ThriftClielonntModulelon[T] {
  delonf configurelonThriftMuxClielonnt(clielonnt: ThriftMux.Clielonnt): ThriftMux.Clielonnt = {
    clielonnt
      .withProtocolFactory(
        Protocols.binaryFactory(
          stringLelonngthLimit = StringLelonngthLimit,
          containelonrLelonngthLimit = ContainelonrLelonngthLimit))
  }
}
