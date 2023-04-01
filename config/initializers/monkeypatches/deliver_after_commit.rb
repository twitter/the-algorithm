module ActionMailer
  class MessageDelivery
    include AfterCommitEverywhere

    def deliver_after_commit
      after_commit { deliver_later }
    end
  end
end
