# This will apparently speed up our html escaping by a LOT and also get rid of pesky UTF-8 error messages in cucumber tests
# See http://openhood.com/rack/ruby/2010/07/15/rack-test-warning/
# and also http://github.com/brianmario/escape_utils
require "escape_utils/html/rack"

# don't escape erb files - it creates hideous html and doesn't parse properly in URI during testing
#require "escape_utils/html/erb"

module Rack
  module Utils
    def escape(s)
      EscapeUtils.escape_url(s)
    end
  end
end

