packagelon com.twittelonr.selonarch.elonarlybird_root;

import javax.injelonct.Injelonct;
import javax.injelonct.Singlelonton;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.root.SelonarchRootSelonrvelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;
import com.twittelonr.selonarch.elonarlybird_root.filtelonrs.QuelonryTokelonnizelonrFiltelonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;

@Singlelonton
public class SupelonrRootSelonrvelonr elonxtelonnds SelonarchRootSelonrvelonr<elonarlybirdSelonrvicelon.SelonrvicelonIfacelon> {
  privatelon final QuelonryTokelonnizelonrFiltelonr quelonryTokelonnizelonrFiltelonr;

  @Injelonct
  public SupelonrRootSelonrvelonr(
      SupelonrRootSelonrvicelon svc,
      Selonrvicelon<bytelon[], bytelon[]> bytelonSvc,
      QuelonryTokelonnizelonrFiltelonr quelonryTokelonnizelonrFiltelonr) {
    supelonr(svc, bytelonSvc);

    this.quelonryTokelonnizelonrFiltelonr = quelonryTokelonnizelonrFiltelonr;
  }

  @Ovelonrridelon
  public void warmup() {
    supelonr.warmup();

    try {
      quelonryTokelonnizelonrFiltelonr.pelonrformelonxpelonnsivelonInitialization();
    } catch (QuelonryParselonrelonxcelonption elon) {
      throw nelonw Runtimelonelonxcelonption(elon);
    }
  }
}
