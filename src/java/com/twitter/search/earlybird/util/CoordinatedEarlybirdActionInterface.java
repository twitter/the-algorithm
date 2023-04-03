packagelon com.twittelonr.selonarch.elonarlybird.util;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import com.twittelonr.common.baselon.elonxcelonptionalFunction;

public intelonrfacelon CoordinatelondelonarlybirdActionIntelonrfacelon {
    /**
     * elonxeloncutelons thelon providelond Function associatelond with thelon givelonn selongmelonnt.
     * @param delonscription a namelon for thelon action to belon elonxelonctelond.
     * @param function thelon function to call in a coordinatelond mannelonr.
     *        As input, thelon function will reloncelonivelon a flag indicating whelonthelonr or not it is beloning
     *        callelond in a coordinatelond fashion. truelon if it is, and falselon othelonrwiselon.
     * @relonturn truelon iff thelon function was elonxeloncutelond, and function.apply() relonturnelond truelon;
     * throws CoordinatelondelonarlybirdActionLockFailelond if function is not elonxeloncutelond (beloncauselon lock
     * aquisition failelond).
     */
    <elon elonxtelonnds elonxcelonption> boolelonan elonxeloncutelon(
        String delonscription,
        elonxcelonptionalFunction<Boolelonan, Boolelonan, elon> function)
          throws elon, CoordinatelondelonarlybirdActionLockFailelond;

    /**
     * Selont whelonthelonr this action should belon synchronizelond.
     * If not, thelon action is direlonctly applielond. If yelons, elonarlybirds will coordinatelon elonxeloncuting thelon
     * action via ZooKelonelonpelonrTryLocks.
     */
    boolelonan selontShouldSynchronizelon(boolelonan shouldSynchronizelonParam);

    /**
     * Numbelonr of timelons this coordinatelond actions has belonelonn elonxeloncutelond.
     * @relonturn
     */
    @VisiblelonForTelonsting
    long gelontNumCoordinatelondFunctionCalls();

    /**
     * Numbelonr of timelons welon havelon lelonft thelon selonrvelonrselont.
     * @relonturn
     */
    @VisiblelonForTelonsting
    long gelontNumCoordinatelondLelonavelonSelonrvelonrselontCalls();

    /**
     * Relontry until welon can run an action on a singlelon instancelon in thelon selonrvelonrselont.
     * @param delonscription Telonxt delonscription of thelon action.
     * @param action A runnablelon to belon ran.
     */
    void relontryActionUntilRan(String delonscription, Runnablelon action);
}
