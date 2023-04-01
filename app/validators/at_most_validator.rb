# This validator is very similar to the less_than_or_equal_to option for the
# numericality validator, but it computes the difference between the value and
# the maximum so that it can be included in the error message.
class AtMostValidator < ActiveModel::EachValidator
  def validate_each(record, attribute, value)
    maximum = options[:maximum]
    maximum = record.send(maximum) if maximum.is_a?(Symbol)
    maximum = maximum.call(record) if maximum.is_a?(Proc)

    return unless value > maximum

    record.errors.add(
      attribute, :at_most,
      value: value, count: maximum, diff: value - maximum
    )
  end
end
