class UnsortedTagsController < ApplicationController

  before_action :check_user_status
  before_action :check_permission_to_wrangle

  def index
    @tags = UnsortedTag.page(params[:page])
    @counts = {}
    [Fandom, Character, Relationship, Freeform].each do |klass|
      @counts[klass.to_s.downcase.pluralize.to_sym] = Rails.cache.fetch("/wrangler/counts/sidebar/#{klass}", race_condition_ttl: 10, expires_in: 1.hour) do
        klass.unwrangled.in_use.count
      end
    end
    @counts[:UnsortedTag] = Rails.cache.fetch("/wrangler/counts/sidebar/UnsortedTag", race_condition_ttl: 10, expires_in: 1.hour) do 
      UnsortedTag.count 
    end
  end

  def mass_update
    unless params[:tags].blank?
      params[:tags].delete_if {|tag_id, tag_type| tag_type.blank? }
      tags = UnsortedTag.where(id: params[:tags].keys)
      tags.each do |tag|
        new_type = params[:tags][tag.id.to_s]
        if %w(Fandom Character Relationship Freeform).include?(new_type)
          tag.update_attribute(:type, new_type)
        else
          raise ts("#{new_type} is not a valid tag type")
        end
      end
      flash[:notice] = ts("Tags were successfully sorted.")
    end
    redirect_to unsorted_tags_path(page: params[:page])
  end

end
