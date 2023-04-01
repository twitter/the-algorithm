class ProfileController < ApplicationController

  def show
    @user = User.find_by(login: params[:user_id])
    if @user.nil?
      flash[:error] = ts("Sorry, there's no user by that name.")
      redirect_to '/' and return
    elsif @user.profile.nil?
      Profile.create(user_id: @user.id)
      @user.reload
    end
    #code the same as the stuff in users_controller
    if current_user.respond_to?(:subscriptions)
      @subscription = current_user.subscriptions.where(subscribable_id: @user.id,
                                                       subscribable_type: 'User').first ||
                      current_user.subscriptions.build(subscribable: @user)
    end
    @page_subtitle = ts("%{username} - Profile", username: @user.login)
  end

end
