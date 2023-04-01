module LogQuerySource
  def debug(*args, &block)
    return unless super

    backtrace = Rails.backtrace_cleaner.clean caller

    relevant_caller_line = backtrace.detect do |caller_line|
      !caller_line.include?('/initializers/')
    end

    if relevant_caller_line
      logger.debug("  ↳ #{ relevant_caller_line.sub("#{ Rails.root }/", '') }")
    end
  end
end
ActiveRecord::LogSubscriber.send :prepend, LogQuerySource
