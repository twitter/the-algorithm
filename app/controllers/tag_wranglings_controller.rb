class TagWranglingsController < ApplicationController
  include TagWrangling

  before_action :check_user_status
  before_action :check_permission_to_wrangle
  around_action :record_wrangling_activity, only: [:wrangle]

  def index
    @counts = {}
    [Fandom, Character, Relationship, Freeform].each do |klass|
      @counts[klass.to_s.downcase.pluralize.to_sym] = Rails.cache.fetch("/wrangler/counts/sidebar/#{klass}", race_condition_ttl: 10, expires_in: 1.hour) do
        klass.unwrangled.in_use.count
      end
    end
    @counts[:UnsortedTag] = Rails.cache.fetch("/wrangler/counts/sidebar/UnsortedTag", race_condition_ttl: 10, expires_in: 1.hour) do
      UnsortedTag.count
    end
    unless params[:show].blank?
      params[:sort_column] = 'created_at' if !valid_sort_column(params[:sort_column], 'tag')
      params[:sort_direction] = 'ASC' if !valid_sort_direction(params[:sort_direction])
      sort = params[:sort_column] + " " + params[:sort_direction]
      sort = sort + ", name ASC" if sort.include?('taggings_count_cache')
      if params[:show] == "fandoms"
        @media_names = Media.by_name.pluck(:name)
        @page_subtitle = ts("fandoms")
        @tags = Fandom.unwrangled.in_use.order(sort).paginate(page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE)
      else # by fandom
        raise "Redshirt: Attempted to constantize invalid class initialize tag_wranglings_controller_index #{params[:show].classify}" unless Tag::USER_DEFINED.include?(params[:show].classify)
        klass = params[:show].classify.constantize
        @tags = klass.unwrangled.in_use.order(sort).paginate(page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE)
      end
    end
  end

  def wrangle
    params[:page] = '1' if params[:page].blank?
    params[:sort_column] = 'name' if !valid_sort_column(params[:sort_column], 'tag')
    params[:sort_direction] = 'ASC' if !valid_sort_direction(params[:sort_direction])
    options = {show: params[:show], page: params[:page], sort_column: params[:sort_column], sort_direction: params[:sort_direction]}

    error_messages, notice_messages = [], []

    # make tags canonical if allowed
    if params[:canonicals].present? && params[:canonicals].is_a?(Array)
      saved_canonicals, not_saved_canonicals = [], []
      tags = Tag.where(id: params[:canonicals])

      tags.each do |tag_to_canonicalize|
        if tag_to_canonicalize.update(canonical: true)
          saved_canonicals << tag_to_canonicalize
        else
          not_saved_canonicals << tag_to_canonicalize
        end
      end

      error_messages << ts('The following tags couldn\'t be made canonical: %{tags_not_saved}', tags_not_saved: not_saved_canonicals.collect(&:name).join(', ')) unless not_saved_canonicals.empty?
      notice_messages << ts('The following tags were successfully made canonical: %{tags_saved}', tags_saved: saved_canonicals.collect(&:name).join(', ')) unless saved_canonicals.empty?
    end

    if params[:media] && !params[:selected_tags].blank?
      options.merge!(media: params[:media])
      @media = Media.find_by_name(params[:media])
      @fandoms = Fandom.find(params[:selected_tags])
      @fandoms.each { |fandom| fandom.add_association(@media) }
    elsif params[:fandom_string].blank? && params[:selected_tags].is_a?(Array) && !params[:selected_tags].empty?
      error_messages << ts('There were no Fandom tags!')
    elsif params[:fandom_string].present? && params[:selected_tags].is_a?(Array) && !params[:selected_tags].empty?
      canonical_fandoms, noncanonical_fandom_names = [], []
      fandom_names = params[:fandom_string].split(',').map(&:squish)

      fandom_names.each do |fandom_name|
        if (fandom = Fandom.find_by_name(fandom_name)).try(:canonical?)
          canonical_fandoms << fandom
        else
          noncanonical_fandom_names << fandom_name
        end
      end

      if canonical_fandoms.present?
        saved_to_fandoms = Tag.where(id: params[:selected_tags])

        saved_to_fandoms.each do |tag_to_wrangle|
          canonical_fandoms.each do |fandom|
            tag_to_wrangle.add_association(fandom)
          end
        end

        canonical_fandom_names = canonical_fandoms.collect(&:name)
        options.merge!(fandom_string: canonical_fandom_names.join(','))
        notice_messages << ts('The following tags were successfully wrangled to %{canonical_fandoms}: %{tags_saved}', canonical_fandoms: canonical_fandom_names.join(', '), tags_saved: saved_to_fandoms.collect(&:name).join(', ')) unless saved_to_fandoms.empty?
      end

      if noncanonical_fandom_names.present?
        error_messages << ts('The following names are not canonical fandoms: %{noncanonical_fandom_names}.', noncanonical_fandom_names: noncanonical_fandom_names.join(', '))
      end
    end

    flash[:notice] = notice_messages.join('<br />').html_safe unless notice_messages.empty?
    flash[:error] = error_messages.join('<br />').html_safe unless error_messages.empty?

    redirect_to tag_wranglings_path(options)
  end
end
