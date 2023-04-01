module CreationNotifier
  def notify_after_creation # after_create
    return unless self.posted?
    do_notify
  end

  def notify_after_update
    return unless self.valid? && self.posted?

    if self.saved_change_to_posted?
      do_notify
    else
      notify_subscribers_on_reveal
    end
  end

  # send the appropriate notifications
  def do_notify
    if self.is_a?(Work)
      notify_parents
      notify_subscribers
      notify_prompters
    elsif self.is_a?(Chapter) && self.position != 1
      notify_subscribers
    end
  end

  # notify recipients that they have gotten a story!
  # we also need to check to see if the work is in a collection
  # only notify a recipient once for each work
  def notify_recipients
    return unless self.posted && self.new_gifts.present? && !self.unrevealed?

    recipient_pseuds = Pseud.parse_bylines(self.new_gifts.collect(&:recipient).join(","), assume_matching_login: true)[:pseuds]
    # check user prefs to see which recipients want to get gift notifications
    # (since each user has only one preference item, this removes duplicates)
    recip_ids = Preference.where(user_id: recipient_pseuds.map(&:user_id),
                                   recipient_emails_off: false).pluck(:user_id)
    recip_ids.each do |userid|
      if self.collections.empty? || self.collections.first.nil?
        UserMailer.recipient_notification(userid, self.id).deliver_after_commit
      else
        UserMailer.recipient_notification(userid, self.id, self.collections.first.id).deliver_after_commit
      end
    end
  end

  # notify people subscribed to this creation or its authors
  def notify_subscribers
    work = self.respond_to?(:work) ? self.work : self
    if work && !work.unrevealed?
      Subscription.for_work(work).each do |subscription|
        RedisMailQueue.queue_subscription(subscription, self)
      end
    end
  end

  # Check whether the work's creator has just been revealed (whether because
  # a collection has just revealed its works, or a collection has just revealed
  # creators). If so, queue up creator subscription emails.
  def notify_subscribers_on_reveal
    # Double-check that it's a posted work.
    return unless self.is_a?(Work) && self.posted

    # Bail out if the work or its creator is currently unrevealed.
    return if self.in_anon_collection || self.in_unrevealed_collection

    # If we've reached here, the creator of the work must be public.
    # So now we want to check whether that's a recent thing.
    pertinent = %w[in_anon_collection in_unrevealed_collection]
    if (pertinent & saved_changes.keys).any?
      # Prior to this save, the work was either anonymous or unrevealed.
      # Either way, the author was just revealed, so we should trigger
      # a creator subscription email.
      Subscription.where(
        subscribable_id: self.pseuds.pluck(:user_id),
        subscribable_type: "User"
      ).each do |subscription|
        RedisMailQueue.queue_subscription(subscription, self)
      end
    end
  end

  # notify prompters of response to their prompt
  def notify_prompters
    if !self.challenge_claims.empty? && !self.unrevealed?
      if self.collections.first.nil?
        UserMailer.prompter_notification(self.id,).deliver_after_commit
      else
        UserMailer.prompter_notification(self.id, self.collections.first.id).deliver_after_commit
      end
    end
  end

  # notify authors of related work
  def notify_parents
    return if unrevealed?

    parents_after_saving.each(&:notify_parent_owners)
  end
end
