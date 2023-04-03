packagelon com.twittelonr.selonarch.elonarlybird.common;

import javax.injelonct.Injelonct;
import javax.injelonct.Singlelonton;

import org.apachelon.thrift.protocol.TProtocolFactory;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.util.thrift.ThriftToBytelonsFiltelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;

@Singlelonton
public class elonarlybirdThriftBackelonnd elonxtelonnds elonarlybirdSelonrvicelon.SelonrvicelonToClielonnt {

  /**
   * Wrapping thelon bytelons svc back to a elonarlybirdSelonrvicelon.SelonrvicelonToClielonnt, which
   * is a elonarlybirdSelonrvicelon.SelonrvicelonIfacelon again.
   */
  @Injelonct
  public elonarlybirdThriftBackelonnd(
      ThriftToBytelonsFiltelonr thriftToBytelonsFiltelonr,
      Selonrvicelon<bytelon[], bytelon[]> bytelonSelonrvicelon,
      TProtocolFactory protocolFactory) {

    supelonr(thriftToBytelonsFiltelonr.andThelonn(bytelonSelonrvicelon), protocolFactory);
  }

}
