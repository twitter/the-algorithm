class Homepage
  def initialize(user)
    @user = user
  end

  def rounded_counts
    @user_count = Rails.cache.fetch("/v1/home/counts/user", expires_in: 40.minutes) do
      estimate_number(User.count)
    end
    @work_count = Rails.cache.fetch("/v1/home/counts/works", expires_in: 40.minutes) do
      estimate_number(Work.posted.count)
    end
    @fandom_count = Rails.cache.fetch("/v1/home/counts/fandom", expires_in: 40.minutes) do
      estimate_number(Fandom.canonical.count)
    end
    return @user_count, @work_count, @fandom_count
  end

  def logged_in?
    @user.present?
  end

  def admin_posts
    if Rails.env.development?
      @admin_posts = AdminPost.non_translated.for_homepage.all
    else
      @admin_posts = Rails.cache.fetch("home/index/home_admin_posts", expires_in: 20.minutes) do
        AdminPost.non_translated.for_homepage.to_a
      end
    end
  end

  def favorite_tags
    return unless logged_in?
    if Rails.env.development?
      @favorite_tags ||= @user.favorite_tags.to_a.sort_by { |favorite_tag| favorite_tag.tag.sortable_name.downcase }
    else
      @favorite_tags ||= Rails.cache.fetch("home/index/#{@user.id}/home_favorite_tags") do
        @user.favorite_tags.to_a.sort_by { |favorite_tag| favorite_tag.tag.sortable_name.downcase }
      end
    end
  end

  def readings
    return unless logged_in? && @user.preference.try(:history_enabled?)
    if Rails.env.development?
      @readings ||= @user.readings.random_order.
          limit(ArchiveConfig.NUMBER_OF_ITEMS_VISIBLE_ON_HOMEPAGE).
          where(toread: true).
          all
    else
      @readings ||= Rails.cache.fetch("home/index/#{@user.id}/home_marked_for_later") do
        @user.readings.random_order.
          limit(ArchiveConfig.NUMBER_OF_ITEMS_VISIBLE_ON_HOMEPAGE).
          where(toread: true).
          to_a
      end
    end
  end

  def inbox_comments
    return unless logged_in?
    @inbox_comments ||= @user.inbox_comments.with_bad_comments_removed.for_homepage
  end

  def estimate_number(number)
    digits = [(Math.log10([number, 1].max).to_i - 3), 0].max
    divide = 10**digits
    divide * (number / divide).to_i
  end

end
