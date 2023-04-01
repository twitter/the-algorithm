# Croatian has categories "one", "few", and "other", according to the CLDR
# plural rules used by Phrase. However, the rails-i18n implementation also
# requires "many".
#
# Note that Croatian has rules for fraction digits, but like rails-i18n
# we will only handle integers for now.
#
# https://unicode-org.github.io/cldr-staging/charts/latest/supplemental/language_plural_rules.html#hr

module RailsI18n
  module Pluralization
    module Croatian
      def self.rule
        lambda do |n|
          n ||= 0
          mod10 = n % 10
          mod100 = n % 100

          if mod10 == 1 && mod100 != 11
            :one
          elsif [2, 3, 4].include?(mod10) && ![12, 13, 14].include?(mod100)
            :few
          else
            :other
          end
        end
      end
    end
  end
end

{
  hr: {
    i18n: {
      plural: {
        keys: [:one, :few, :other],
        rule: RailsI18n::Pluralization::Croatian.rule
      }
    }
  }
}
