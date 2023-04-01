!function(global, $, r, _) {
  'use strict'

  var oldOnError = global.onerror

  global.onerror = function(message, file, line, character, errorType) {
    // Don't log errors from userscripts and plugins.
    var badFileNameRegex = /^(chrome:\/\/|file:\/\/)/i

    // These are special messages fired by some firefox plugins and bad
    // browsers.
    var badMessageRegex = /((^Script error\.$)|(atomicFindClose))/i

    if (badFileNameRegex.test(file) || badMessageRegex.test(message)) {
      return
    }

    var exception = {
      message: message,
      file: file,
      line: line,
      character: character,
      errorType: errorType,
    }

    r.logging.sendException(exception)

    if (oldOnError) {
      oldOnError.apply(global, arguments)
    }
  }
}(this, jQuery, r, _)

