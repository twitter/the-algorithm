class FavoriteTagsController < ApplicationController
  skip_before_action :store_location, only: [:create, :destroy]
  before_action :users_only
  before_action :load_user
  before_action :check_ownership

  respond_to :html, :json

  # POST /favorites_tags
  def create
    @favorite_tag = current_user.favorite_tags.build(favorite_tag_params)
    success_message = ts("You have successfully added %{tag_name} to your favorite tags. You can find them on the <a href='#{root_path}'>Archive homepage</a>.", tag_name: @favorite_tag.tag_name)
    if @favorite_tag.save
      respond_to do |format|
        format.html { redirect_to tag_works_path(tag_id: @favorite_tag.tag.to_param), notice: success_message.html_safe }
        format.json { render json: { item_id: @favorite_tag.id, item_success_message: success_message }, status: :created }
      end
    else
      respond_to do |format|
        format.html do
          flash.keep
          redirect_to tag_works_path(tag_id: @favorite_tag.tag.to_param), flash: { error: @favorite_tag.errors.full_messages }
        end
        format.json { render json: { errors: @favorite_tag.errors.full_messages }, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /favorite_tags/1
  def destroy
    @favorite_tag = FavoriteTag.find(params[:id])
    @favorite_tag.destroy
    success_message = ts('You have successfully removed %{tag_name} from your favorite tags.', tag_name: @favorite_tag.tag_name)
    respond_to do |format|
      format.html { redirect_to tag_works_path(tag_id: @favorite_tag.tag.to_param), notice: success_message }
      format.json { render json: { item_success_message: success_message }, status: :ok }
    end
  end

  private

  def load_user
    @user = User.find_by(login: params[:user_id])
    @check_ownership_of = @user
  end

  def favorite_tag_params
    params.require(:favorite_tag).permit(
      :tag_id
    )
  end
end
