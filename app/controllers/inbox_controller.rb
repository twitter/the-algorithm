class InboxController < ApplicationController
  include BlockHelper

  before_action :load_user
  before_action :check_ownership

  before_action :load_commentable, only: :reply
  before_action :check_blocked, only: :reply

  def load_user
    @user = User.find_by(login: params[:user_id])
    @check_ownership_of = @user
  end

  def show
    @inbox_total = @user.inbox_comments.with_bad_comments_removed.count
    @unread = @user.inbox_comments.with_bad_comments_removed.count_unread
    @filters = filter_params[:filters] || {}
    @inbox_comments = @user.inbox_comments.with_bad_comments_removed.find_by_filters(@filters).page(params[:page])
  end

  def reply
    @comment = Comment.new
    respond_to do |format|
      format.html do
        redirect_to comment_path(@commentable, add_comment_reply_id: @commentable.id, anchor: 'comment_' + @commentable.id.to_s)
      end
      format.js
    end
  end

  def update
    begin
      @inbox_comments = InboxComment.find(params[:inbox_comments])
      if params[:read]
        @inbox_comments.each { |i| i.update_attribute(:read, true) }
      elsif params[:unread]
        @inbox_comments.each { |i| i.update_attribute(:read, false) }
      elsif params[:delete]
        @inbox_comments.each { |i| i.destroy }
      end
    rescue
      flash[:caution] = ts("Please select something first")
    end
    success_message = ts('Inbox successfully updated.')
    respond_to do |format|
      format.html { redirect_to request.referer || user_inbox_path(@user, page: params[:page], filters: params[:filters]), notice: success_message }
      format.json { render json: { item_success_message: success_message }, status: :ok }
    end
  end

  private

  # Allow flexible params through, since we're not posting any data
  def filter_params
    params.permit!
  end

  def load_commentable
    @commentable = Comment.find(params[:comment_id])
  end

  def check_blocked
    if blocked_by?(@commentable.ultimate_parent)
      flash[:error] = t("comments.check_blocked.parent")
      redirect_back(fallback_location: user_inbox_path(@user))
    elsif blocked_by_comment?(@commentable)
      flash[:error] = t("comments.check_blocked.reply")
      redirect_back(fallback_location: user_inbox_path(@user))
    end
  end
end
