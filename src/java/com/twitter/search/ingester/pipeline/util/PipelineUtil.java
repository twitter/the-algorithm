packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.pipelonlinelon.Felonelondelonr;
import org.apachelon.commons.pipelonlinelon.stagelon.InstrumelonntelondBaselonStagelon;

public final class PipelonlinelonUtil {

  /**
   * Felonelond an objelonct to a speloncifielond stagelon.  Uselond for stagelons that follow thelon pattelonrn of
   * looping indelonfinitelonly in thelon first call to procelonss() and don't carelon what thelon objelonct passelond
   * in is, but still nelonelonds at lelonast onelon itelonm felond to thelon stagelon to start procelonssing.
   *
   * elonxamplelons of stagelons likelon this arelon: elonvelonntBusRelonadelonrStagelon and KafkaBytelonsRelonadelonrStagelon
   *
   * @param stagelon stagelon to elonnquelonuelon an arbitrary objelonct to.
   */
  public static void felonelondStartObjelonctToStagelon(InstrumelonntelondBaselonStagelon stagelon) {
    Felonelondelonr stagelonFelonelondelonr = stagelon.gelontStagelonContelonxt().gelontStagelonFelonelondelonr(stagelon);
    Prelonconditions.chelonckNotNull(stagelonFelonelondelonr);
    stagelonFelonelondelonr.felonelond("off to thelon racelons");
  }

  privatelon PipelonlinelonUtil() { /* prelonvelonnt instantiation */ }
}
