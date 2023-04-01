!function(r) {

  function makeLoadHandler(testImgType, loadState, config) {
    return function() {
      config.imgState[testImgType] = loadState;
      sendHTTPSCompatResults(config);
    };
  }

  function setUpHTTPSTestImage(type, url, config) {
    var img = jQuery('<img>');
    img.on('load', makeLoadHandler(type, true, config));
    img.on('error', makeLoadHandler(type, false, config));
    img.attr('src', url);
  }

  function sendHTTPSCompatResults(config) {
    if (config.imgState['test'] === undefined ||
        config.imgState['control'] === undefined) {
      return;
    }
    // guard against handlers triggering multiple times for whatever reason
    if (config.sentReport) {
      return;
    }
    // failed due to cross-origin resource loading restrictions
    // (or an ad blocker?) ignore.
    if (config.imgState['control'] === false) {
      return;
    }

    config.sentReport = true;
    var result = config.imgState['test'];
    var pixel = new Image();
    var params = 'run_name=' + config.runName + '&valid=' + result + '&uuid=' + r.uuid();
    pixel.src = config.logPixel + '?' + params;
  }

  window.runHTTPSCertTest = function(config) {
    config = jQuery.extend({}, config);
    config.imgState = {};
    config.sentReport = false;
    setUpHTTPSTestImage('control', config.controlImg, config);
    setUpHTTPSTestImage('test', config.testImg, config);
  }
}(r);
