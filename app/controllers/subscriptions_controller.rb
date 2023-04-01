class SubscriptionsController < ApplicationController

  skip_before_action :store_location, only: [:create, :destroy]

  before_action :users_only
  before_action :load_user
  before_action :check_ownership

  def load_user
    @user = User.find_by(login: params[:user_id])
    @check_ownership_of = @user
  end

  # GET /subscriptions
  # GET /subscriptions.xml
  def index
    @subscriptions = @user.subscriptions.includes(:subscribable)
    if params[:type].present?
      @subscriptions = @subscriptions.where(subscribable_type: params[:type].classify)
    end
    @subscriptions = @subscriptions.to_a.sort { |a,b| a.name.downcase <=> b.name.downcase }
    @subscriptions = @subscriptions.paginate page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE
  end

  # POST /subscriptions
  # POST /subscriptions.xml
  def create
    @subscription = @user.subscriptions.build(subscription_params)

    success_message = ts("You are now following %{name}. If you'd like to stop receiving email updates, you can unsubscribe from <a href=\"#{user_subscriptions_path}\">your Subscriptions page</a>.", name: @subscription.name).html_safe
    if @subscription.save
      respond_to do |format|
        format.html { redirect_to request.referer || @subscription.subscribable, notice: success_message }
        format.json { render json: { item_id: @subscription.id, item_success_message: success_message }, status: :created }
      end
    else
      respond_to do |format|
        format.html {
          flash.keep
          redirect_to request.referer || @subscription.subscribable, flash: { error: @subscription.errors.full_messages }
        }
        format.json { render json: { errors: @subscription.errors.full_messages }, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /subscriptions/1
  # DELETE /subscriptions/1.xml
  def destroy
    @subscription = Subscription.find(params[:id])
    @subscribable = @subscription.subscribable
    @subscription.destroy

    success_message = ts("You have successfully unsubscribed from %{name}.", name: @subscription.name).html_safe
    respond_to do |format|
      format.html { redirect_to request.referer || user_subscriptions_path(current_user), notice: success_message }
      format.json { render json: { item_success_message: success_message }, status: :ok }
    end
  end

  private

  def subscription_params
    params.require(:subscription).permit(
      :subscribable_id, :subscribable_type
    )
  end

end
