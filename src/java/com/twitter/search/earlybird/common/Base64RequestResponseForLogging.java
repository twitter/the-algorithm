package com.tw ter.search.earlyb rd.common;

 mport org.apac .commons.codec.b nary.Base64;
 mport org.apac .thr ft.TExcept on;
 mport org.apac .thr ft.TSer al zer;
 mport org.apac .thr ft.protocol.TB naryProtocol;
 mport org.slf4j.Logger;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

publ c f nal class Base64RequestResponseForLogg ng {
  pr vate stat c f nal Logger GENERAL_LOG = org.slf4j.LoggerFactory.getLogger(
      Base64RequestResponseForLogg ng.class);
  pr vate stat c f nal Logger FA LED_REQUEST_LOG = org.slf4j.LoggerFactory.getLogger(
      Base64RequestResponseForLogg ng.class.getNa () + ".Fa ledRequests");
  pr vate stat c f nal Logger RANDOM_REQUEST_LOG = org.slf4j.LoggerFactory.getLogger(
      Base64RequestResponseForLogg ng.class.getNa () + ".RandomRequests");
  pr vate stat c f nal Logger SLOW_REQUEST_LOG = org.slf4j.LoggerFactory.getLogger(
      Base64RequestResponseForLogg ng.class.getNa () + ".SlowRequests");

  pr vate enum LogType {
    FA LED,
    RANDOM,
    SLOW,
  };

  pr vate f nal LogType logtype;
  pr vate f nal Str ng logL ne;
  pr vate f nal Earlyb rdRequest request;
  pr vate f nal Earlyb rdResponse response;
  pr vate f nal Base64 base64 = new Base64();

  // TSer al zer  s not threadsafe, so create a new one for each request
  pr vate f nal TSer al zer ser al zer = new TSer al zer(new TB naryProtocol.Factory());

  pr vate Base64RequestResponseForLogg ng(
      LogType logType, Str ng logL ne, Earlyb rdRequest request, Earlyb rdResponse response) {
    t .logtype = logType;
    t .logL ne = logL ne;
    t .request = request;
    t .response = response;
  }

  publ c stat c Base64RequestResponseForLogg ng randomRequest(
      Str ng logL ne, Earlyb rdRequest request, Earlyb rdResponse response) {
    return new Base64RequestResponseForLogg ng(LogType.RANDOM, logL ne, request, response);
  }

  publ c stat c Base64RequestResponseForLogg ng fa ledRequest(
      Str ng logL ne, Earlyb rdRequest request, Earlyb rdResponse response) {
    return new Base64RequestResponseForLogg ng(LogType.FA LED, logL ne, request, response);
  }

  publ c stat c Base64RequestResponseForLogg ng slowRequest(
      Str ng logL ne, Earlyb rdRequest request, Earlyb rdResponse response) {
    return new Base64RequestResponseForLogg ng(LogType.SLOW, logL ne, request, response);
  }

  pr vate Str ng asBase64(Earlyb rdRequest clearedRequest) {
    try {
      // T  purpose of t  log  s to make   easy to re- ssue requests  n formz to reproduce
      //  ssues.  f quer es are re- ssued as  s t y w ll be treated as late-arr v ng quer es and
      // dropped due to t  cl entRequestT  Ms be ng set to t  or g nal query t  . For ease of
      // use purposes   clear cl entRequestT  Ms and log   out separately for t  rare case  
      //  s needed.
      clearedRequest.unsetCl entRequestT  Ms();
      return base64.encodeToStr ng(ser al zer.ser al ze(clearedRequest));
    } catch (TExcept on e) {
      GENERAL_LOG.error("Fa led to ser al ze request for logg ng.", e);
      return "fa led_to_ser al ze";
    }
  }

  pr vate Str ng asBase64(Earlyb rdResponse earlyb rdResponse) {
    try {
      return base64.encodeToStr ng(ser al zer.ser al ze(earlyb rdResponse));
    } catch (TExcept on e) {
      GENERAL_LOG.error("Fa led to ser al ze response for logg ng.", e);
      return "fa led_to_ser al ze";
    }
  }

  pr vate Str ng getFormatted ssage() {
    Str ng base64Request = asBase64(
        Earlyb rdRequestUt l.copyAndClearUnnecessaryValuesForLogg ng(request));
    Str ng base64Response = asBase64(response);
    return logL ne + ", cl entRequestT  Ms: " + request.getCl entRequestT  Ms()
        + ", " + base64Request + ", " + base64Response;
  }

  /**
   * Logs t  Base64-encoded request and response to t  success or fa lure log.
   */
  publ c vo d log() {
    // Do t  ser al z ng/concatt ng t  way so   happens on t  background thread for
    // async logg ng
    Object logObject = new Object() {
      @Overr de
      publ c Str ng toStr ng() {
        return getFormatted ssage();
      }
    };

    sw ch (logtype) {
      case FA LED:
        FA LED_REQUEST_LOG. nfo("{}", logObject);
        break;
      case RANDOM:
        RANDOM_REQUEST_LOG. nfo("{}", logObject);
        break;
      case SLOW:
        SLOW_REQUEST_LOG. nfo("{}", logObject);
        break;
      default:
        // Not logg ng anyth ng for ot r log types.
        break;
    }
  }
}
