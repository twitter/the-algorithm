module I18n
  class << self
    # A shorthand for translation that takes a string as its first argument, which
    # will be the default string.
    #
    # Deprecated.
    def translate_string(str, **options)
      translate(str, **options.merge(default: str))
    end

    alias :ts :translate_string
  end
end

module AbstractController
  module Translation
    def translate_string(str, **options)
      I18n.translate_string(str, **options)
    end
    alias :ts :translate_string
  end
end


module ActiveRecord #:nodoc:
  class Base
    def translate_string(str, **options)
      begin
        ActiveRecord::Base.connection
        I18n.translate_string(str, **options)
      rescue StandardError
        str || ""
      end
    end

    alias :ts :translate_string

    class << Base
      def translate_string(str, **options)
        begin
          ActiveRecord::Base.connection
          I18n.translate_string(str, **options)
        rescue StandardError
          str || ""
        end
      end

      alias :ts :translate_string
    end
  end
end

module ActionMailer #:nodoc:
  class Base
    def translate_string(str, **options)
      begin
        ActiveRecord::Base.connection
        I18n.translate_string(str, **options)
      rescue StandardError
        str || ""
      end
    end

    alias :ts :translate_string
  end
end

# Note: we define this separately for ActionView so that we get the controller/action name
# in the key, and use the added scoping for translate in TranslationHelper.
module ActionView
  module Helpers
    module TranslationHelper
      def translate_string(str, **options)
        translate(str, **options.merge(default: str))
      end

      alias :ts :translate_string
    end
  end
end
