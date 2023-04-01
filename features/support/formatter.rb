# Adapted from https://github.com/tpope/fivemat
require "cucumber/formatter/progress"

module Ao3Cucumber
  class Formatter < ::Cucumber::Formatter::Progress
    include ElapsedTime

    def on_test_case_started(event)
      super
      feature = event.test_case.feature

      return if same_feature_as_previous_test_case?(feature)

      after_feature unless @current_feature.nil?
      before_feature(feature)
    end

    def on_test_run_finished(_event)
      after_feature
      super
    end

    private

    def before_feature(feature)
      # Print the feature's file name.
      @io.puts feature.location.file
      @io.flush
      @current_feature = feature
      @start_time = Time.now
    end

    def after_feature
      print_elapsed_time @io, @start_time
      @io.puts
      @io.puts
      @io.flush

      print_elements(@pending_step_matches, :pending, "steps")
      print_elements(@failed_results, :failed, "steps")

      @pending_step_matches = []
      @failed_results = []
    end

    def same_feature_as_previous_test_case?(feature)
      @current_feature && @current_feature.location == feature.location
    end
  end
end
