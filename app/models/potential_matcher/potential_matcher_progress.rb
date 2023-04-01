# frozen_string_literal: true

# A class for recording the progress of potential match generation.
#
# Designed to make it easier to record progress on subtasks. It keeps two
# stacks: @progress, and @limit. When you call start_subtask, it pushes 0 onto
# the @progress stack, and the number of ticks in the subtask onto the @limit
# stack. When you call increment, it increases the value at the very end of the
# @progress stack (so that the percent completion for that subtask, computed
# with @progress.last / @limit.last, increases). When you call end_subtask, it
# pops the last value off of the @progress and @limit, so that you go back to
# recording progress for the previous subtask.
#
# The overall percent completion is computed by examining all subtasks on the
# stack. You start from the end of the stack (with the subtask that was added
# last), and divide the progress by the limit to get the fractional completion.
# Then you move back to the previous subtask, add that fractional completion to
# that subtask's progress, and divide by the limit. Repeat until you've reached
# the first subtask.
#
# For a concrete example: If your first subtask has a limit of 10 "ticks" of
# work, and you've called increment 3 times, the percent progress will be 30%.
# If you then create a subtask with a limit of 5 ticks, and you call increment
# once, then you're 1 / 5 = 0.2 = 20% of the way through the subtask, but your
# progress on the whole work is (3 + 0.2) / 10 = 32%. Another call to increment
# will bring you up to 34%, then 36%, 38%, and finally 40% when you finish all
# five ticks of work. Then you can call end_task and increment (which will keep
# you at 40%, but without the subtask), and continue with the work that will
# bring you from 40% to 50%.
class PotentialMatcherProgress
  attr_reader :redis_progress_key
  attr_reader :progress_enabled

  # Create a new progress object.
  def initialize(collection, progress_enabled = true)
    @progress = []
    @limit = []
    @percent = 0

    @progress_enabled = progress_enabled
    @redis_progress_key = PotentialMatch.progress_key(collection)
  end

  # Signal that you're starting work on a subtask. The sole argument gives the
  # number of "ticks" in the subtask that you're about to work on, so that it's
  # possible to calculate the percent completion for the subtask.
  def start_subtask(max)
    @progress.push(0)
    @limit.push(max)
  end

  # Signal that you're finishing work on the current subtask.
  #
  # NOTE: Never call start_subtask immediately after end_subtask. You should
  # always have a call to increment in between, otherwise it'll look like your
  # progress is getting lower. (In fact, in order to keep the counter from
  # going backwards, you should always call increment immediately after
  # end_subtask.)
  def end_subtask
    @progress.pop
    @limit.pop
  end

  # Signal that you've just done a "tick" of work on the current subtask.
  def increment
    value = @progress.pop
    @progress.push(value + 1)
    update if @progress_enabled
  end

  # Calculate the percent progress.
  def percent
    value = 0.0

    (@progress.size - 1).downto 0 do |index|
      value = (@progress[index] + value) / @limit[index]
    end

    (value * 10_000).floor / 100.0
  end

  # Update the REDIS progress key with the current percent.
  def update
    return unless @progress_enabled
    REDIS_GENERAL.set @redis_progress_key, percent.to_s
  end
end
