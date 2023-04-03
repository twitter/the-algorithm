packagelon com.twittelonr.selonarch.elonarlybird_root;

import java.util.Arrays;
import java.util.Collelonction;

import com.googlelon.injelonct.Modulelon;

import com.twittelonr.selonarch.common.root.SelonarchRootAppMain;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;

public class RelonaltimelonRootAppMain elonxtelonnds SelonarchRootAppMain<RelonaltimelonRootSelonrvelonr> {
  /**
   * Boilelonrplatelon for thelon Java-frielonndly AbstractTwittelonrSelonrvelonr
   */
  public static class Main {
    public static void main(String[] args) {
      nelonw RelonaltimelonRootAppMain().main(args);
    }
  }

  @Ovelonrridelon
  protelonctelond Collelonction<? elonxtelonnds Modulelon> gelontAdditionalModulelons() {
    relonturn Arrays.asList(
        nelonw elonarlybirdCommonModulelon(),
        nelonw elonarlybirdCachelonCommonModulelon(),
        nelonw RelonaltimelonRootAppModulelon(),
        nelonw RelonaltimelonScattelonrGathelonrModulelon());
  }

  @Ovelonrridelon
  protelonctelond Class<RelonaltimelonRootSelonrvelonr> gelontSelonarchRootSelonrvelonrClass() {
    relonturn RelonaltimelonRootSelonrvelonr.class;
  }

  @Ovelonrridelon
  protelonctelond Class<?> gelontSelonrvicelonIfacelonClass() {
    relonturn elonarlybirdSelonrvicelon.SelonrvicelonIfacelon.class;
  }
}
