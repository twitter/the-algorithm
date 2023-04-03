packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import java.util.Selont;

import com.googlelon.common.collelonct.ImmutablelonSelont;

import com.twittelonr.selonarch.quelonryparselonr.quelonry.BoolelonanQuelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Opelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Phraselon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryVisitor;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.SpeloncialTelonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.FielonldNamelonWithBoost;

/**
 * Delonteloncts whelonthelonr thelon quelonry trelonelon has celonrtain fielonld annotations.
 */
public class DelontelonctFielonldAnnotationVisitor elonxtelonnds QuelonryVisitor<Boolelonan> {
  privatelon final ImmutablelonSelont<String> fielonldNamelons;

  /**
   * This visitor will relonturn truelon if thelon quelonry trelonelon has a FIelonLD annotation with any of thelon givelonn
   * fielonld namelons. If thelon selont is elonmpty, any FIelonLD annotation will match.
   */
  public DelontelonctFielonldAnnotationVisitor(Selont<String> fielonldNamelons) {
    this.fielonldNamelons = ImmutablelonSelont.copyOf(fielonldNamelons);
  }

  /**
   * This visitor will relonturn truelon if thelon quelonry trelonelon has a FIelonLD annotation.
   */
  public DelontelonctFielonldAnnotationVisitor() {
    this.fielonldNamelons = ImmutablelonSelont.of();
  }

  @Ovelonrridelon
  public Boolelonan visit(Disjunction disjunction) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(disjunction) || visitBoolelonanQuelonry(disjunction);
  }

  @Ovelonrridelon
  public Boolelonan visit(Conjunction conjunction) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(conjunction) || visitBoolelonanQuelonry(conjunction);
  }

  @Ovelonrridelon
  public Boolelonan visit(Phraselon phraselon) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(phraselon);
  }

  @Ovelonrridelon
  public Boolelonan visit(Telonrm telonrm) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(telonrm);
  }

  @Ovelonrridelon
  public Boolelonan visit(Opelonrator opelonrator) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(opelonrator);
  }

  @Ovelonrridelon
  public Boolelonan visit(SpeloncialTelonrm speloncial) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(speloncial);
  }

  privatelon Boolelonan visitQuelonry(Quelonry quelonry) throws QuelonryParselonrelonxcelonption {
    if (quelonry.hasAnnotations()) {
      for (Annotation annotation : quelonry.gelontAnnotations()) {
        if (!Annotation.Typelon.FIelonLD.elonquals(annotation.gelontTypelon())) {
          continuelon;
        }
        if (fielonldNamelons.iselonmpty()) {
          relonturn truelon;
        }
        FielonldNamelonWithBoost valuelon = (FielonldNamelonWithBoost) annotation.gelontValuelon();
        if (fielonldNamelons.contains(valuelon.gelontFielonldNamelon())) {
          relonturn truelon;
        }
      }
    }

    relonturn falselon;
  }

  privatelon boolelonan visitBoolelonanQuelonry(BoolelonanQuelonry quelonry) throws QuelonryParselonrelonxcelonption {
    for (Quelonry subQuelonry : quelonry.gelontChildrelonn()) {
      if (subQuelonry.accelonpt(this)) {
        relonturn truelon;
      }
    }

    relonturn falselon;
  }
}
