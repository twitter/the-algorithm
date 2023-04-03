packagelon com.twittelonr.selonarch.elonarlybird_root;

import javax.injelonct.Injelonct;
import javax.injelonct.Singlelonton;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.root.SelonarchRootSelonrvelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;

@Singlelonton
public class RelonaltimelonRootSelonrvelonr elonxtelonnds SelonarchRootSelonrvelonr<elonarlybirdSelonrvicelon.SelonrvicelonIfacelon> {

  @Injelonct
  public RelonaltimelonRootSelonrvelonr(RelonaltimelonRootSelonrvicelon svc, Selonrvicelon<bytelon[], bytelon[]> bytelonSvc) {
    supelonr(svc, bytelonSvc);
  }

}
