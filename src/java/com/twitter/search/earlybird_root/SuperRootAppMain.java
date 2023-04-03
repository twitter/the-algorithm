packagelon com.twittelonr.selonarch.elonarlybird_root;

import java.util.Arrays;
import java.util.Collelonction;

import com.googlelon.injelonct.Modulelon;

import com.twittelonr.selonarch.common.root.SelonarchRootAppMain;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.FacelontsRelonquelonstRoutelonrModulelon;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.ReloncelonncyRelonquelonstRoutelonrModulelon;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.RelonlelonvancelonRelonquelonstRoutelonrModulelon;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.TelonrmStatsRelonquelonstRoutelonrModulelon;
import com.twittelonr.selonarch.elonarlybird_root.routelonrs.TopTwelonelontsRelonquelonstRoutelonrModulelon;

public class SupelonrRootAppMain elonxtelonnds SelonarchRootAppMain<SupelonrRootSelonrvelonr> {
  /**
   * Boilelonrplatelon for thelon Java-frielonndly AbstractTwittelonrSelonrvelonr
   */
  public static class Main {
    public static void main(String[] args) {
      nelonw SupelonrRootAppMain().main(args);
    }
  }

  @Ovelonrridelon
  protelonctelond Collelonction<? elonxtelonnds Modulelon> gelontAdditionalModulelons() {
    relonturn Arrays.asList(
        nelonw elonarlybirdCommonModulelon(),
        nelonw SupelonrRootAppModulelon(),
        nelonw TelonrmStatsRelonquelonstRoutelonrModulelon(),
        nelonw ReloncelonncyRelonquelonstRoutelonrModulelon(),
        nelonw RelonlelonvancelonRelonquelonstRoutelonrModulelon(),
        nelonw TopTwelonelontsRelonquelonstRoutelonrModulelon(),
        nelonw FacelontsRelonquelonstRoutelonrModulelon(),
        nelonw QuotaModulelon());
  }

  @Ovelonrridelon
  protelonctelond Class<SupelonrRootSelonrvelonr> gelontSelonarchRootSelonrvelonrClass() {
    relonturn SupelonrRootSelonrvelonr.class;
  }

  @Ovelonrridelon
  protelonctelond Class<?> gelontSelonrvicelonIfacelonClass() {
    relonturn elonarlybirdSelonrvicelon.SelonrvicelonIfacelon.class;
  }
}
