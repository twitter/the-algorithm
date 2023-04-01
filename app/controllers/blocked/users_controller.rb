module Blocked
  class UsersController < ApplicationController
    before_action :set_user

    before_action :check_ownership, except: :index
    before_action :check_ownership_or_admin, only: :index
    before_action :check_admin_permissions, only: :index

    before_action :build_block, only: [:confirm_block, :create]
    before_action :set_block, only: [:confirm_unblock, :destroy]

    # GET /users/:user_id/blocked/users
    def index
      @blocks = @user.blocks_as_blocker
        .joins(:blocked).includes(blocked: :default_pseud)
        .order(created_at: :desc).order(id: :desc).page(params[:page])

      @pseuds = @blocks.map { |b| b.blocked.default_pseud }
      @rec_counts = Pseud.rec_counts_for_pseuds(@pseuds)
      @work_counts = Pseud.work_counts_for_pseuds(@pseuds)

      @page_subtitle = "Blocked Users"
    end

    # GET /users/:user_id/blocked/users/confirm_block
    def confirm_block
      @hide_dashboard = true

      return if @block.valid?

      # We can't block this user for whatever reason
      flash[:error] = @block.errors.full_messages.first
      redirect_to user_blocked_users_path(@user)
    end

    # GET /users/:user_id/blocked/users/confirm_unblock
    def confirm_unblock
      @hide_dashboard = true

      @blocked = @block.blocked
    end

    # POST /users/:user_id/blocked/users
    def create
      if @block.save
        flash[:notice] = t(".blocked", name: @block.blocked.login)
      else
        # We can't block this user for whatever reason
        flash[:error] = @block.errors.full_messages.first
      end

      redirect_to user_blocked_users_path(@user)
    end

    # DELETE /users/:user_id/blocked/users/:id
    def destroy
      @block.destroy
      flash[:notice] = t(".unblocked", name: @block.blocked.login)
      redirect_to user_blocked_users_path(@user)
    end

    private

    # Sets the user whose blocks we're viewing/modifying.
    def set_user
      @user = User.find_by!(login: params[:user_id])
      @check_ownership_of = @user
    end

    # Builds (but doesn't save) a block matching the desired params:
    def build_block
      blocked_byline = params.fetch(:blocked_id, "")
      @block = @user.blocks_as_blocker.build(blocked_byline: blocked_byline)
      @blocked = @block.blocked
    end

    def set_block
      @block = @user.blocks_as_blocker.find(params[:id])
    end

    def check_admin_permissions
      authorize Block if logged_in_as_admin?
    end
  end
end
