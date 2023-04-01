class GiftsController < ApplicationController

  before_action :load_collection

  def index
    @user = User.find_by!(login: params[:user_id]) if params[:user_id]
    @recipient_name = params[:recipient]
    @page_subtitle = ts("Gifts for %{name}", name: (@user ? @user.login : @recipient_name))
    unless @user || @recipient_name
      flash[:error] = ts("Whose gifts did you want to see?")
      redirect_to(@collection || root_path) and return
    end
    if @user
      if current_user.nil?
        @works = @user.gift_works.visible_to_all
      else
        if @user == current_user && params[:refused]
          @works = @user.rejected_gift_works.visible_to_registered_user
        else
          @works = @user.gift_works.visible_to_registered_user
        end
      end
    else
      pseud = Pseud.parse_byline(@recipient_name, assume_matching_login: true).first
      if pseud
        if current_user.nil?
          @works = pseud.gift_works.visible_to_all
        else
          @works = pseud.gift_works.visible_to_registered_user
        end
      else
        if current_user.nil?
          @works = Work.giftworks_for_recipient_name(@recipient_name).visible_to_all
        else
          @works = Work.giftworks_for_recipient_name(@recipient_name).visible_to_registered_user
        end
      end
    end
    @works = @works.in_collection(@collection) if @collection
    @works = @works.order('revised_at DESC').paginate(page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE)
  end

  def toggle_rejected
    @gift = Gift.find(params[:id])
    # have to have the gift, be logged in, and the owner of the gift
    if @gift && current_user && @gift.user == current_user
      @gift.rejected = !@gift.rejected?
      @gift.save!
      if @gift.rejected?
        flash[:notice] = ts("This work will no longer be listed among your gifts.")
      else
        flash[:notice] = ts("This work will now be listed among your gifts.")
      end
    else
      # user doesn't have permission
      access_denied
      return
    end
    redirect_to user_gifts_path(current_user) and return
  end
end
