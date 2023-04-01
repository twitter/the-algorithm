class PseudDecorator < SimpleDelegator

  attr_reader :data

  # Pseuds need to be decorated with various stats from the "_source" when
  # viewing search results, so we first load the pseuds with the base search
  # class, and then decorate them with the data.
  def self.load_from_elasticsearch(hits, **options)
    items = Pseud.load_from_elasticsearch(hits, **options)
    decorate_from_search(items, hits)
  end

  # TODO: pull this out into a reusable module
  def self.decorate_from_search(results, search_hits)
    search_data = search_hits.group_by { |doc| doc["_id"] }
    results.map do |result|
      data = search_data[result.id.to_s].first&.dig('_source') || {}
      new_with_data(result, data)
    end
  end

  # TODO: Either eliminate this function or add definitions for work_counts and
  # bookmark_counts (and possibly fandom information, as well?). The NameError
  # that this causes isn't a problem at the moment because the function isn't
  # being called from anywhere, but it needs to be fixed before it can be used.
  def self.decorate(pseuds)
    users = User.where(id: pseuds.map(&:user_id)).group_by(&:id)
    work_counts
    bookmark_counts
    work_key = User.current_user.present? ? :general_works_count : :public_works_count
    bookmark_key = User.current_user.present? ? :general_bookmarks_count : :public_bookmarks_count
    pseuds.map do |pseud|
      data = {
        user_login: users[user_id].login,
        bookmark_key => bookmark_counts[id],
        work_key => work_counts[id]
      }
      new_with_data(pseud, data)
    end
  end

  def self.new_with_data(pseud, data)
    new(pseud).tap do |decorator|
      decorator.data = data
    end
  end

  def data=(info)
    @data = HashWithIndifferentAccess.new(info)
  end

  def works_count
    count = User.current_user.present? ? data[:general_works_count] : data[:public_works_count]
    count || 0
  end

  def bookmarks_count
    User.current_user.present? ? data[:general_bookmarks_count] : data[:public_bookmarks_count]
  end

  def byline
    data[:byline] ||= constructed_byline
  end

  def user_login
    data[:user_login]
  end

  def pseud_path
    "/users/#{user_login}/pseuds/#{name}"
  end

  def works_path
    "#{pseud_path}/works"
  end

  def works_link
    return unless works_count > 0
    text = ActionController::Base.helpers.pluralize(works_count, "works")
    "<a href='#{works_path}'>#{text}</a>"
  end

  def bookmarks_path
    "#{pseud_path}/bookmarks"
  end

  def bookmarks_link
    return unless bookmarks_count > 0
    text = ActionController::Base.helpers.pluralize(bookmarks_count, "bookmarks")
    "<a href='#{bookmarks_path}'>#{text}</a>"
  end

  def fandom_path(id)
    return unless id
    "#{works_path}?fandom_id=#{id}"
  end

  def fandom_link(fandom_id)
    fandom = fandom_stats(fandom_id)
    return unless fandom.present?
    text = ActionController::Base.helpers.pluralize(fandom[:count], "work") + " in #{fandom[:name]}"
    "<a href='#{fandom_path(fandom_id)}'>#{text}</a>"    
  end

  def authored_items_links(options = {})
    general_links = [works_link, bookmarks_link].compact.join(", ")
    if options[:fandom_id].present?
      # This can potentially be an array
      fandom_links = [options[:fandom_id]].flatten.map do |fandom_id|
        fandom_link(fandom_id)
      end
      general_links + " | " + fandom_links.compact.join(", ")
    else
      general_links
    end
  end

  def constructed_byline
    name == user_login ? name : "#{name} (#{user_login})"
  end

  def fandom_stats(id)
    key = User.current_user.present? ? "id" : "id_for_public"
    data[:fandoms]&.detect { |fandom| fandom[key].to_s == id.to_s }
  end
end
