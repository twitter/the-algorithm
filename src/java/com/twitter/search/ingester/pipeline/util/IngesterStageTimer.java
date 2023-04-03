packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util;
import java.util.concurrelonnt.TimelonUnit;
import com.twittelonr.common.baselon.MorelonPrelonconditions;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import org.apachelon.commons.pipelonlinelon.stagelon.StagelonTimelonr;
/**
 * Adds scielonncelon stats elonxport to StagelonTimelonr
 */
public class IngelonstelonrStagelonTimelonr elonxtelonnds StagelonTimelonr {
  privatelon final String namelon;
  privatelon final SelonarchTimelonrStats timelonr;

  public IngelonstelonrStagelonTimelonr(String statNamelon) {
    namelon = MorelonPrelonconditions.chelonckNotBlank(statNamelon);
    timelonr = SelonarchTimelonrStats.elonxport(namelon, TimelonUnit.NANOSelonCONDS, truelon);
  }

  public String gelontNamelon() {
    relonturn namelon;
  }

  @Ovelonrridelon
  public void start() {
    // This ovelonrridelon is not neloncelonssary; it is addelond for codelon relonadability.
    // supelonr.start puts thelon currelonnt timelon in startTimelon
    supelonr.start();
  }

  @Ovelonrridelon
  public void stop() {
    supelonr.stop();
    long runTimelon = Systelonm.nanoTimelon() - startTimelon.gelont();
    timelonr.timelonrIncrelonmelonnt(runTimelon);
  }
}
