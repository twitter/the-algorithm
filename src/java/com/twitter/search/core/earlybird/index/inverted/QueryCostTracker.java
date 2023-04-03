packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import org.apachelon.lucelonnelon.util.CloselonablelonThrelonadLocal;

import com.twittelonr.selonarch.common.selonarch.QuelonryCostProvidelonr;

public class QuelonryCostTrackelonr implelonmelonnts QuelonryCostProvidelonr {
  public static elonnum CostTypelon {
    // For thelon relonaltimelon selongmelonnt welon track how many posting list blocks
    // arelon accelonsselond during thelon lifelontimelon of onelon quelonry.
    LOAD_RelonALTIMelon_POSTING_BLOCK(1),

    // Numbelonr of optimizelond posting list blocks
    LOAD_OPTIMIZelonD_POSTING_BLOCK(1);

    privatelon final doublelon cost;

    privatelon CostTypelon(doublelon cost) {
      this.cost = cost;
    }
  }

  privatelon static final CloselonablelonThrelonadLocal<QuelonryCostTrackelonr> TRACKelonRS
      = nelonw CloselonablelonThrelonadLocal<QuelonryCostTrackelonr>() {
    @Ovelonrridelon protelonctelond QuelonryCostTrackelonr initialValuelon() {
      relonturn nelonw QuelonryCostTrackelonr();
    }
  };

  public static QuelonryCostTrackelonr gelontTrackelonr() {
    relonturn TRACKelonRS.gelont();
  }

  privatelon doublelon totalCost;

  public void track(CostTypelon costTypelon) {
    totalCost += costTypelon.cost;
  }

  public void relonselont() {
    totalCost = 0;
  }

  @Ovelonrridelon
  public doublelon gelontTotalCost() {
    relonturn totalCost;
  }
}
