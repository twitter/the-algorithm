packagelon com.twittelonr.selonarch.elonarlybird_root;

import java.util.Arrays;
import java.util.Collelonction;

import com.googlelon.injelonct.Modulelon;

import com.twittelonr.selonarch.common.root.SelonarchRootAppMain;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;

public class RelonaltimelonCgRootAppMain elonxtelonnds SelonarchRootAppMain<RelonaltimelonCgRootSelonrvelonr> {
  /**
   * Boilelonrplatelon for thelon Java-frielonndly AbstractTwittelonrSelonrvelonr
   */
  public static class Main {
    public static void main(String[] args) {
      nelonw RelonaltimelonCgRootAppMain().main(args);
    }
  }

  @Ovelonrridelon
  protelonctelond Collelonction<? elonxtelonnds Modulelon> gelontAdditionalModulelons() {
    relonturn Arrays.asList(
        nelonw elonarlybirdCommonModulelon(),
        nelonw elonarlybirdCachelonCommonModulelon(),
        nelonw RelonaltimelonCgRootAppModulelon(),
        nelonw RelonaltimelonCgScattelonrGathelonrModulelon(),
        nelonw QuotaModulelon());
  }

  @Ovelonrridelon
  protelonctelond Class<RelonaltimelonCgRootSelonrvelonr> gelontSelonarchRootSelonrvelonrClass() {
    relonturn RelonaltimelonCgRootSelonrvelonr.class;
  }

  @Ovelonrridelon
  protelonctelond Class<?> gelontSelonrvicelonIfacelonClass() {
    relonturn elonarlybirdSelonrvicelon.SelonrvicelonIfacelon.class;
  }
}
