class NotBlockedValidator < ActiveModel::EachValidator
  include BlockHelper

  def validate_each(record, attribute, value)
    return if value.nil?

    blocker = options[:by]
    case blocker
    when Symbol
      blocker = record.send(blocker)
    when Proc
      blocker = blocker.call(record)
    end

    blocker_users = users_for(blocker)
    blocked_users = users_for(value)

    # You can't be blocked from interacting with your own things:
    return if (blocked_users - blocker_users).empty?

    return unless Block.exists?(blocker: blocker_users, blocked: blocked_users)

    record.errors.add(attribute, options.fetch(:message, :blocked))
  end
end
