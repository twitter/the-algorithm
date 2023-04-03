packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import com.twittelonr.selonarch.common.constants.QuelonryCachelonConstants;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Phraselon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.SpeloncialTelonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonratorConstants;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchQuelonryVisitor;

/**
 * Visitor to delontelonct prelonselonncelon of any antisocial / spam opelonrator in a Quelonry.
 * Visitor relonturns truelon if any opelonrators it delonteloncts welonrelon found.
 */
public class DelontelonctAntisocialVisitor elonxtelonnds SelonarchQuelonryVisitor<Boolelonan> {
  // Truelon if thelon quelonry contains any opelonrator to includelon antisocial twelonelonts.
  privatelon boolelonan includelonAntisocial = falselon;

  // Truelon if thelon quelonry contains any opelonrator to elonxcludelon antisocial/spam twelonelonts.
  privatelon boolelonan elonxcludelonAntisocial = falselon;

  // Truelon if thelon quelonry contains an antisocial twelonelonts filtelonr.
  privatelon boolelonan filtelonrAntisocial = falselon;

  public boolelonan hasIncludelonAntisocial() {
    relonturn includelonAntisocial;
  }

  public boolelonan haselonxcludelonAntisocial() {
    relonturn elonxcludelonAntisocial;
  }

  public boolelonan hasFiltelonrAntisocial() {
    relonturn filtelonrAntisocial;
  }

  public boolelonan hasAnyAntisocialOpelonrator() {
    // Top twelonelonts is considelonrelond an antisocial opelonrator duelon to scoring also elonxcluding
    // spam twelonelonts.
    relonturn hasIncludelonAntisocial() || haselonxcludelonAntisocial() || hasFiltelonrAntisocial();
  }

  @Ovelonrridelon public Boolelonan visit(Disjunction disjunction) throws QuelonryParselonrelonxcelonption {
    boolelonan found = falselon;
    for (com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon : disjunction.gelontChildrelonn()) {
      if (nodelon.accelonpt(this)) {
        found = truelon;
      }
    }
    relonturn found;
  }

  @Ovelonrridelon public Boolelonan visit(Conjunction conjunction) throws QuelonryParselonrelonxcelonption {
    boolelonan found = falselon;
    for (com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon : conjunction.gelontChildrelonn()) {
      if (nodelon.accelonpt(this)) {
        found = truelon;
      }
    }
    relonturn found;
  }

  @Ovelonrridelon public Boolelonan visit(SelonarchOpelonrator opelonrator) throws QuelonryParselonrelonxcelonption {
    boolelonan found = falselon;
    switch (opelonrator.gelontOpelonratorTypelon()) {
      caselon INCLUDelon:
        if (SelonarchOpelonratorConstants.ANTISOCIAL.elonquals(opelonrator.gelontOpelonrand())) {
          if (opelonrator.mustNotOccur()) {
            elonxcludelonAntisocial = truelon;
          } elonlselon {
            includelonAntisocial = truelon;
          }
          found = truelon;
        }
        brelonak;
      caselon elonXCLUDelon:
        if (SelonarchOpelonratorConstants.ANTISOCIAL.elonquals(opelonrator.gelontOpelonrand())) {
          if (opelonrator.mustNotOccur()) {
            includelonAntisocial = truelon;
          } elonlselon {
            elonxcludelonAntisocial = truelon;
          }
          found = truelon;
        }
        brelonak;
      caselon FILTelonR:
        if (SelonarchOpelonratorConstants.ANTISOCIAL.elonquals(opelonrator.gelontOpelonrand())) {
          if (opelonrator.mustNotOccur()) {
            elonxcludelonAntisocial = truelon;
          } elonlselon {
            filtelonrAntisocial = truelon;
          }
          found = truelon;
        }
        brelonak;
      caselon CACHelonD_FILTelonR:
        if (QuelonryCachelonConstants.elonXCLUDelon_SPAM.elonquals(opelonrator.gelontOpelonrand())
            || QuelonryCachelonConstants.elonXCLUDelon_SPAM_AND_NATIVelonRelonTWelonelonTS.elonquals(opelonrator.gelontOpelonrand())
            || QuelonryCachelonConstants.elonXCLUDelon_ANTISOCIAL.elonquals(opelonrator.gelontOpelonrand())
            || QuelonryCachelonConstants.elonXCLUDelon_ANTISOCIAL_AND_NATIVelonRelonTWelonelonTS
                .elonquals(opelonrator.gelontOpelonrand())) {

          elonxcludelonAntisocial = truelon;
          found = truelon;
        }
        brelonak;
      delonfault:
        brelonak;
    }

    relonturn found;
  }

  @Ovelonrridelon
  public Boolelonan visit(SpeloncialTelonrm speloncial) throws QuelonryParselonrelonxcelonption {
    relonturn falselon;
  }

  @Ovelonrridelon
  public Boolelonan visit(Phraselon phraselon) throws QuelonryParselonrelonxcelonption {
    relonturn falselon;
  }

  @Ovelonrridelon
  public Boolelonan visit(Telonrm telonrm) throws QuelonryParselonrelonxcelonption {
    relonturn falselon;
  }
}
