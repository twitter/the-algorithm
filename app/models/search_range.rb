class SearchRange

  attr_accessor :text_range

  TIME_REGEX = /^([<>]*)\s*([\d -]+)\s*(year|week|month|day|hour)s?(\s*ago)?\s*$/
  NUMBER_REGEX = /^([<>]*)\s*([\d,. -]+)\s*$/
  OUT_OF_RANGE_DATE = 1000.years.ago

  # Takes a string that includes a time or number range and
  # returns it as a hash that can be used in elasticsearch queries
  def self.parsed(str)
    new(str).parse
  end

  def initialize(str)
    @text_range = str || ""
  end

  def parse
    standardize_text
    range = {}
    if match = text_range.match(TIME_REGEX)
      range = time_range(match[1], match[2], match[3])
    elsif match = text_range.match(NUMBER_REGEX)
      range = numerical_range(match[1], match[2].gsub(",", ""))
    end
    range
  end

  private

  def standardize_text
    @text_range = @text_range.gsub("&gt;", ">").
                              gsub("&lt;", "<").
                              downcase
  end


  # create numerical range from operand and string
  # operand can be "<", ">" or ""
  # string must be an integer unless operand is ""
  # in which case it can be two numbers connected by "-"
  def numerical_range(operand, string)
    case operand
    when "<"
      { lt: string.to_i }
    when ">"
      { gt: string.to_i }
    when ""
      match = string.match(/-/)
      if match
        { gte: match.pre_match.to_i, lte: match.post_match.to_i }
      else
        { gte: string.to_i, lte: string.to_i }
      end
    end
  end

  # create time range from operand, amount and period
  # period must be one known by time_from_string
  def time_range(operand, amount, period)
    case operand
    when "<"
      time = time_from_string(amount, period)
      { gt: time }
    when ">"
      time = time_from_string(amount, period)
      { lt: time }
    when ""
      match = amount.match(/-/)
      if match
        time1 = time_from_string(match.pre_match, period)
        time2 = time_from_string(match.post_match, period)
        { gte: time2, lte: time1 }
      else
        range_from_string(amount, period)
      end
    end
  end

  # helper method to create times from two strings
  # Elasticsearch gets grumpy with negative years, so the simple fix
  # is just to go back a thousand years
  # TODO: rework date query formats if Homer ever starts posting his stuff to AO3
  def time_from_string(amount, period)
    date = amount.to_i.send(period).ago
    date.year.negative? ? OUT_OF_RANGE_DATE : date
  end

  # Generate a range based on one number
  # Interval is based on period used, ie 1 month ago = range from beginning to end of month
  def range_from_string(amount, period)
    case period
    when /year/
      a = amount.to_i.year.ago.beginning_of_year
      a2 = a.end_of_year
    when /month/
      a = amount.to_i.month.ago.beginning_of_month
      a2 = a.end_of_month
    when /week/
      a = amount.to_i.week.ago.beginning_of_week
      a2 = a.end_of_week
    when /day/
      a = amount.to_i.day.ago.beginning_of_day
      a2 = a.end_of_day
    when /hour/
      a = amount.to_i.hour.ago.change(min: 0, sec: 0, usec: 0)
      a2 = (a + 60.minutes)
    else
      raise "unknown period: " + period
    end
    a, a2 = [a, a2].map do |date|
      date.year.negative? ? OUT_OF_RANGE_DATE : date
    end
    { gte: a, lte: a2 }
  end

end
