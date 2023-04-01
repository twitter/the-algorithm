!(function(global, undefined) {
  var RE_IE_VERSION = /(?:\b(?:MS)?IE\s+|\bTrident\/7\.0;.*\s+rv:)(\d+(?:\.?\d+)?)/i;
  var uaMatches = navigator.userAgent.match(RE_IE_VERSION);
  var ieVersion = uaMatches && uaMatches[1] && parseFloat(uaMatches[1]);
  var doNotTrack = navigator.doNotTrack ||
    global.doNotTrack ||
    navigator.msDoNotTrack;

  global.DO_NOT_TRACK = /^(yes|1)$/i.test(doNotTrack) && ieVersion !== 10;

})(this);
