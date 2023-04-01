module Muted
  class UsersController < ApplicationController
    before_action :set_user

    before_action :check_ownership, except: :index
    before_action :check_ownership_or_admin, only: :index
    before_action :check_admin_permissions, only: :index

    before_action :build_mute, only: [:confirm_mute, :create]
    before_action :set_mute, only: [:confirm_unmute, :destroy]

    # GET /users/:user_id/muted/users
    def index
      @mutes = @user.mutes_as_muter
        .joins(:muted).includes(muted: :default_pseud)
        .order(created_at: :desc).order(id: :desc).page(params[:page])

      @pseuds = @mutes.map { |b| b.muted.default_pseud }
      @rec_counts = Pseud.rec_counts_for_pseuds(@pseuds)
      @work_counts = Pseud.work_counts_for_pseuds(@pseuds)

      @page_subtitle = t(".title")
    end

    # GET /users/:user_id/muted/users/confirm_mute
    def confirm_mute
      @hide_dashboard = true

      return if @mute.valid?

      # We can't mute this user for whatever reason
      flash[:error] = @mute.errors.full_messages.first
      redirect_to user_muted_users_path(@user)
    end

    # GET /users/:user_id/muted/users/confirm_unmute
    def confirm_unmute
      @hide_dashboard = true

      @muted = @mute.muted
    end

    # POST /users/:user_id/muted/users
    def create
      if @mute.save
        flash[:notice] = t(".muted", name: @mute.muted.login)
      else
        # We can't mute this user for whatever reason
        flash[:error] = @mute.errors.full_messages.first
      end

      redirect_to user_muted_users_path(@user)
    end

    # DELETE /users/:user_id/muted/users/:id
    def destroy
      @mute.destroy
      flash[:notice] = t(".unmuted", name: @mute.muted.login)
      redirect_to user_muted_users_path(@user)
    end

    private

    # Sets the user whose mutes we're viewing/modifying.
    def set_user
      @user = User.find_by!(login: params[:user_id])
      @check_ownership_of = @user
    end

    # Builds (but doesn't save) a mute matching the desired params:
    def build_mute
      muted_byline = params.fetch(:muted_id, "")
      @mute = @user.mutes_as_muter.build(muted_byline: muted_byline)
      @muted = @mute.muted
    end

    def set_mute
      @mute = @user.mutes_as_muter.find(params[:id])
    end

    def check_admin_permissions
      authorize Mute if logged_in_as_admin?
    end
  end
end
