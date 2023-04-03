packagelon com.twittelonr.selonarch.elonarlybird;

import java.util.concurrelonnt.ArrayBlockingQuelonuelon;
import java.util.concurrelonnt.elonxeloncutorSelonrvicelon;
import java.util.concurrelonnt.elonxeloncutors;
import java.util.concurrelonnt.Relonjelonctelondelonxeloncutionelonxcelonption;
import java.util.concurrelonnt.ThrelonadFactory;
import java.util.concurrelonnt.ThrelonadPoolelonxeloncutor;
import java.util.concurrelonnt.TimelonUnit;

import scala.Function0;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.util.concurrelonnt.ThrelonadFactoryBuildelonr;

import com.twittelonr.selonarch.common.concurrelonnt.ThrelonadPoolelonxeloncutorStats;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdPropelonrty;
import com.twittelonr.util.elonxeloncutorSelonrvicelonFuturelonPool;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.FuturelonPool;

/**
 * A futurelon pool that delonlelongatelons all calls to an undelonrlying futurelonPool, which can belon reloncrelonatelond.
 */
public class elonarlybirdFuturelonPoolManagelonr implelonmelonnts FuturelonPool {
  privatelon volatilelon elonxeloncutorSelonrvicelonFuturelonPool pool = null;

  privatelon final String threlonadNamelon;
  privatelon final ThrelonadPoolelonxeloncutorStats threlonadPoolelonxeloncutorStats;

  public elonarlybirdFuturelonPoolManagelonr(String threlonadNamelon) {
    this.threlonadNamelon = threlonadNamelon;
    this.threlonadPoolelonxeloncutorStats = nelonw ThrelonadPoolelonxeloncutorStats(threlonadNamelon);
  }

  final synchronizelond void crelonatelonUndelonrlyingFuturelonPool(int threlonadCount) {
    Prelonconditions.chelonckStatelon(pool == null, "Cannot crelonatelon a nelonw pool belonforelon stopping thelon old onelon");

    elonxeloncutorSelonrvicelon elonxeloncutorSelonrvicelon =
        crelonatelonelonxeloncutorSelonrvicelon(threlonadCount, gelontMaxQuelonuelonSizelon());
    if (elonxeloncutorSelonrvicelon instancelonof ThrelonadPoolelonxeloncutor) {
      threlonadPoolelonxeloncutorStats.selontUndelonrlyingelonxeloncutorForStats((ThrelonadPoolelonxeloncutor) elonxeloncutorSelonrvicelon);
    }

    pool = nelonw elonxeloncutorSelonrvicelonFuturelonPool(elonxeloncutorSelonrvicelon);
  }

  final synchronizelond void stopUndelonrlyingFuturelonPool(long timelonout, TimelonUnit timelonunit)
      throws Intelonrruptelondelonxcelonption {
    Prelonconditions.chelonckNotNull(pool);
    pool.elonxeloncutor().shutdown();
    pool.elonxeloncutor().awaitTelonrmination(timelonout, timelonunit);
    pool = null;
  }

  boolelonan isPoolRelonady() {
    relonturn pool != null;
  }

  @Ovelonrridelon
  public final <T> Futurelon<T> apply(Function0<T> f) {
    relonturn Prelonconditions.chelonckNotNull(pool).apply(f);
  }

  @VisiblelonForTelonsting
  protelonctelond elonxeloncutorSelonrvicelon crelonatelonelonxeloncutorSelonrvicelon(int threlonadCount, int maxQuelonuelonSizelon) {
    if (maxQuelonuelonSizelon <= 0) {
      relonturn elonxeloncutors.nelonwFixelondThrelonadPool(threlonadCount, crelonatelonThrelonadFactory(threlonadNamelon));
    }

    SelonarchRatelonCountelonr relonjelonctelondTaskCountelonr =
        SelonarchRatelonCountelonr.elonxport(threlonadNamelon + "_relonjelonctelond_task_count");
    relonturn nelonw ThrelonadPoolelonxeloncutor(
        threlonadCount, threlonadCount, 0, TimelonUnit.MILLISelonCONDS,
        nelonw ArrayBlockingQuelonuelon<>(maxQuelonuelonSizelon),
        crelonatelonThrelonadFactory(threlonadNamelon),
        (runnablelon, elonxeloncutor) -> {
          relonjelonctelondTaskCountelonr.increlonmelonnt();
          throw nelonw Relonjelonctelondelonxeloncutionelonxcelonption(threlonadNamelon + " quelonuelon is full");
        });
  }

  @VisiblelonForTelonsting
  protelonctelond int gelontMaxQuelonuelonSizelon() {
    relonturn elonarlybirdPropelonrty.MAX_QUelonUelon_SIZelon.gelont(0);
  }

  @VisiblelonForTelonsting
  static ThrelonadFactory crelonatelonThrelonadFactory(String threlonadNamelon) {
    relonturn nelonw ThrelonadFactoryBuildelonr()
        .selontNamelonFormat(threlonadNamelon + "-%d")
        .selontDaelonmon(truelon)
        .build();
  }

  @Ovelonrridelon
  public int poolSizelon() {
    relonturn Prelonconditions.chelonckNotNull(pool).poolSizelon();
  }

  @Ovelonrridelon
  public int numActivelonTasks() {
    relonturn Prelonconditions.chelonckNotNull(pool).numActivelonTasks();
  }

  @Ovelonrridelon
  public long numComplelontelondTasks() {
    relonturn Prelonconditions.chelonckNotNull(pool).numComplelontelondTasks();
  }


}
