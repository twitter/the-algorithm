class SpamReport
  attr_reader :recent_date, :new_date

  def self.run
    new.run
  end

  def initialize
    @recent_date = 14.days.ago
    @new_date = 1.day.ago
  end

  def run
    spam = {}
    users.each do |user|
      new_works, score = score_user(user)
      if score > ArchiveConfig.SPAM_THRESHOLD
        spam[user.id] = { score: score, work_ids: new_works }
      end
    end
    spam = Hash[spam.sort_by { |_user_id, info| info[:score] }.reverse]
    if spam.length > 0
      AdminMailer.send_spam_alert(spam).deliver_later
    end
  end

  private

  def all_new_works
    Work.where("created_at > ?", new_date).posted.unhidden
  end

  def users
    all_new_works.map { |w| w.users }.flatten.uniq
  end

  # Scoring rules:
  # For every new spam work, add 4 to the score
  # For every new non-spam work, add 1
  # For every older non-spam work,
  # decrease the score by 2
  # Add the number of different ip addresses used to post
  def score_user(user)
    new_works = []
    ips = []
    score = 0
    works = user.works.
                 visible_to_registered_user.
                 where("works.created_at > ?", new_date)
    works.each do |work|
      unless work.spam_checked?
        work.check_for_spam
      end

      if work.created_at > new_date
        new_works << work.id
        ips << work.ip_address
        if work.spam?
          score += 4
        else
          score += 1
        end
      end
    end

    count = user.works.
                 where("works.created_at > ? AND works.created_at < ?",
                       recent_date,
                       new_date).
                 posted.not_spam.size
    score -= (count * 2)
    score += ips.uniq.length
    [new_works, score]
  end
end
