packagelon com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons;

import java.util.concurrelonnt.LinkelondBlockingQuelonuelon;
import java.util.concurrelonnt.ThrelonadPoolelonxeloncutor;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.injelonct.Providelons;
import com.googlelon.injelonct.Singlelonton;

import com.twittelonr.injelonct.TwittelonrModulelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.stats.FelonaturelonUpdatelonStats;
import com.twittelonr.util.elonxeloncutorSelonrvicelonFuturelonPool;
import com.twittelonr.util.IntelonrruptiblelonelonxeloncutorSelonrvicelonFuturelonPool;

public class FuturelonPoolModulelon elonxtelonnds TwittelonrModulelon {
  /**
   * Providelon futurelon pool backelond by elonxeloncutor selonrvicelon, with boundelond threlonad pool and boundelond backing
   * quelonuelon.
   */
  @Providelons
  @Singlelonton
  public elonxeloncutorSelonrvicelonFuturelonPool futurelonPool() {
    // Thelonselon limits arelon baselond on selonrvicelon capacity elonstimatelons and telonsting on staging,
    // attelonmpting to givelon thelon pool as many relonsourcelons as possiblelon without ovelonrloading anything.
    // 100-200 threlonads is managelonablelon, and thelon 2000 quelonuelon sizelon is baselond on a conselonrvativelon uppelonr
    // limit that tasks in thelon quelonuelon takelon 1 MB elonach, melonaning quelonuelon maxelons out at 2 GB, which should
    // belon okay givelonn 4 GB RAM with 3 GB relonselonrvelond helonap.
    relonturn crelonatelonFuturelonPool(100, 200, 2000);
  }

  /**
   * Crelonatelon a futurelon pool backelond by elonxeloncutor selonrvicelon, with boundelond threlonad pool and boundelond backing
   * quelonuelon. ONLY VISIBILelon FOR TelonSTING; don't invokelon outsidelon this class.
   */
  @VisiblelonForTelonsting
  public static elonxeloncutorSelonrvicelonFuturelonPool crelonatelonFuturelonPool(
      int corelonPoolSizelon, int maximumPoolSizelon, int quelonuelonCapacity) {
    final LinkelondBlockingQuelonuelon<Runnablelon> quelonuelon = nelonw LinkelondBlockingQuelonuelon<>(quelonuelonCapacity);

    elonxeloncutorSelonrvicelonFuturelonPool futurelonPool = nelonw IntelonrruptiblelonelonxeloncutorSelonrvicelonFuturelonPool(
        nelonw ThrelonadPoolelonxeloncutor(
            corelonPoolSizelon,
            maximumPoolSizelon,
            60L,
            TimelonUnit.SelonCONDS,
            quelonuelon));

    SelonarchCustomGaugelon.elonxport(FelonaturelonUpdatelonStats.PRelonFIX + "threlonad_pool_sizelon",
        futurelonPool::poolSizelon);
    SelonarchCustomGaugelon.elonxport(FelonaturelonUpdatelonStats.PRelonFIX + "work_quelonuelon_sizelon",
        quelonuelon::sizelon);

    relonturn futurelonPool;
  }
}
