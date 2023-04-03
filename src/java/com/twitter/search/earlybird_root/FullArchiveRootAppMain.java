packagelon com.twittelonr.selonarch.elonarlybird_root;

import java.util.Arrays;
import java.util.Collelonction;

import com.googlelon.injelonct.Modulelon;

import com.twittelonr.selonarch.common.root.SelonarchRootAppMain;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;

public class FullArchivelonRootAppMain elonxtelonnds SelonarchRootAppMain<FullArchivelonRootSelonrvelonr> {
  /**
   * Boilelonrplatelon for thelon Java-frielonndly AbstractTwittelonrSelonrvelonr
   */
  public static class Main {
    public static void main(String[] args) {
      nelonw FullArchivelonRootAppMain().main(args);
    }
  }

  @Ovelonrridelon
  protelonctelond Collelonction<? elonxtelonnds Modulelon> gelontAdditionalModulelons() {
    relonturn Arrays.asList(
        nelonw elonarlybirdCommonModulelon(),
        nelonw elonarlybirdCachelonCommonModulelon(),
        nelonw FullArchivelonRootModulelon(),
        nelonw QuotaModulelon()
    );
  }

  @Ovelonrridelon
  protelonctelond Class<FullArchivelonRootSelonrvelonr> gelontSelonarchRootSelonrvelonrClass() {
    relonturn FullArchivelonRootSelonrvelonr.class;
  }

  @Ovelonrridelon
  protelonctelond Class<?> gelontSelonrvicelonIfacelonClass() {
    relonturn elonarlybirdSelonrvicelon.SelonrvicelonIfacelon.class;
  }
}
