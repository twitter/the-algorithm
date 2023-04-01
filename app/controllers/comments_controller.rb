class CommentsController < ApplicationController
  skip_before_action :store_location, except: [:show, :index, :new]
  before_action :load_commentable,
                only: [:index, :new, :create, :edit, :update, :show_comments,
                       :hide_comments, :add_comment_reply,
                       :cancel_comment_reply, :delete_comment,
                       :cancel_comment_delete, :unreviewed, :review_all]
  before_action :check_user_status, only: [:new, :create, :edit, :update, :destroy]
  before_action :load_comment, only: [:show, :edit, :update, :delete_comment, :destroy, :cancel_comment_edit, :cancel_comment_delete, :review, :approve, :reject, :freeze, :unfreeze, :hide, :unhide]
  before_action :check_visibility, only: [:show]
  before_action :check_if_restricted
  before_action :check_tag_wrangler_access
  before_action :check_parent
  before_action :check_modify_parent,
                only: [:new, :create, :edit, :update, :add_comment_reply,
                       :cancel_comment_reply, :cancel_comment_edit]
  before_action :check_pseud_ownership, only: [:create, :update]
  before_action :check_ownership, only: [:edit, :update, :cancel_comment_edit]
  before_action :check_permission_to_edit, only: [:edit, :update ]
  before_action :check_permission_to_delete, only: [:delete_comment, :destroy]
  before_action :check_parent_comment_permissions, only: [:new, :create, :add_comment_reply]
  before_action :check_unreviewed, only: [:add_comment_reply]
  before_action :check_frozen, only: [:new, :create, :add_comment_reply]
  before_action :check_hidden_by_admin, only: [:new, :create, :add_comment_reply]
  before_action :check_not_replying_to_spam, only: [:new, :create, :add_comment_reply]
  before_action :check_permission_to_review, only: [:unreviewed]
  before_action :check_permission_to_access_single_unreviewed, only: [:show]
  before_action :check_permission_to_moderate, only: [:approve, :reject]
  before_action :check_permission_to_modify_frozen_status, only: [:freeze, :unfreeze]
  before_action :check_permission_to_modify_hidden_status, only: [:hide, :unhide]

  include BlockHelper

  before_action :check_blocked, only: [:new, :create, :add_comment_reply, :edit, :update]
  def check_blocked
    parent = find_parent

    if blocked_by?(parent)
      flash[:comment_error] = t("comments.check_blocked.parent")
      redirect_to_all_comments(parent, show_comments: true)
    elsif @comment && blocked_by_comment?(@comment.commentable)
      # edit and update set @comment to the comment being edited
      flash[:comment_error] = t("comments.check_blocked.reply")
      redirect_to_all_comments(parent, show_comments: true)
    elsif @comment.nil? && blocked_by_comment?(@commentable)
      # new, create, and add_comment_reply don't set @comment, but do set @commentable
      flash[:comment_error] = t("comments.check_blocked.reply")
      redirect_to_all_comments(parent, show_comments: true)
    end
  end

  def check_pseud_ownership
    return unless params[:comment][:pseud_id]
    pseud = Pseud.find(params[:comment][:pseud_id])
    return if pseud && current_user && current_user.pseuds.include?(pseud)
    flash[:error] = ts("You can't comment with that pseud.")
    redirect_to root_path
  end

  def load_comment
    @comment = Comment.find(params[:id])
    @check_ownership_of = @comment
    @check_visibility_of = @comment
  end

  def check_parent
    parent = find_parent
    # Only admins and the owner can see comments on something hidden by an admin.
    if parent.respond_to?(:hidden_by_admin) && parent.hidden_by_admin
      logged_in_as_admin? || current_user_owns?(parent) || access_denied(redirect: root_path)
    end
    # Only admins and the owner can see comments on unrevealed works.
    if parent.respond_to?(:in_unrevealed_collection) && parent.in_unrevealed_collection
      logged_in_as_admin? || current_user_owns?(parent) || access_denied(redirect: root_path)
    end
  end

  def check_modify_parent
    parent = find_parent
    # No one can create or update comments on something hidden by an admin.
    if parent.respond_to?(:hidden_by_admin) && parent.hidden_by_admin
      flash[:error] = ts("Sorry, you can't add or edit comments on a hidden work.")
      redirect_to work_path(parent)
    end
    # No one can create or update comments on unrevealed works.
    if parent.respond_to?(:in_unrevealed_collection) && parent.in_unrevealed_collection
      flash[:error] = ts("Sorry, you can't add or edit comments on an unrevealed work.")
      redirect_to work_path(parent)
    end
  end

  def find_parent
    if @comment.present?
      @comment.ultimate_parent
    elsif @commentable.is_a?(Comment)
      @commentable.ultimate_parent
    elsif @commentable.present? && @commentable.respond_to?(:work)
      @commentable.work
    else
      @commentable
    end
  end

  # Check to see if the ultimate_parent is a Work, and if so, if it's restricted
  def check_if_restricted
    parent = find_parent

    return unless parent.respond_to?(:restricted) && parent.restricted? && !(logged_in? || logged_in_as_admin?)
    redirect_to new_user_session_path(restricted_commenting: true)
  end

  # Check to see if the ultimate_parent is a Work or AdminPost, and if so, if it allows
  # comments for the current user.
  def check_parent_comment_permissions
    parent = find_parent
    if parent.is_a?(Work)
      translation_key = "work"
    elsif parent.is_a?(AdminPost)
      translation_key = "admin_post"
    else
      return
    end

    if parent.disable_all_comments?
      flash[:error] = t("comments.commentable.permissions.#{translation_key}.disable_all")
      redirect_to parent
    elsif parent.disable_anon_comments? && !logged_in?
      flash[:error] = t("comments.commentable.permissions.#{translation_key}.disable_anon")
      redirect_to parent
    end
  end

  def check_unreviewed
    return unless @commentable.respond_to?(:unreviewed?) && @commentable.unreviewed?

    flash[:error] = ts("Sorry, you cannot reply to an unapproved comment.")
    redirect_to logged_in? ? root_path : new_user_session_path
  end

  def check_frozen
    return unless @commentable.respond_to?(:iced?) && @commentable.iced?

    flash[:error] = t("comments.check_frozen.error")
    redirect_back(fallback_location: root_path)
  end

  def check_hidden_by_admin
    return unless @commentable.respond_to?(:hidden_by_admin?) && @commentable.hidden_by_admin?

    flash[:error] = t("comments.check_hidden_by_admin.error")
    redirect_back(fallback_location: root_path)
  end

  def check_not_replying_to_spam
    return unless @commentable.respond_to?(:approved?) && !@commentable.approved?

    flash[:error] = t("comments.check_not_replying_to_spam.error")
    redirect_back(fallback_location: root_path)
  end

  def check_permission_to_review
    parent = find_parent
    return if logged_in_as_admin? || current_user_owns?(parent)
    flash[:error] = ts("Sorry, you don't have permission to see those unreviewed comments.")
    redirect_to logged_in? ? root_path : new_user_session_path
  end

  def check_permission_to_access_single_unreviewed
    return unless @comment.unreviewed?
    parent = find_parent
    return if logged_in_as_admin? || current_user_owns?(parent) || current_user_owns?(@comment)
    flash[:error] = ts("Sorry, that comment is currently in moderation.")
    redirect_to logged_in? ? root_path : new_user_session_path
  end

  def check_permission_to_moderate
    parent = find_parent
    unless logged_in_as_admin? || current_user_owns?(parent)
      flash[:error] = ts("Sorry, you don't have permission to moderate that comment.")
      redirect_to(logged_in? ? root_path : new_user_session_path)
    end
  end

  def check_tag_wrangler_access
    if @commentable.is_a?(Tag) || (@comment&.parent&.is_a?(Tag))
      logged_in_as_admin? || permit?("tag_wrangler") || access_denied
    end
  end

  # Must be able to delete other people's comments on owned works, not just owned comments!
  def check_permission_to_delete
    access_denied(redirect: @comment) unless logged_in_as_admin? || current_user_owns?(@comment) || current_user_owns?(@comment.ultimate_parent)
  end

  # Comments cannot be edited after they've been replied to or if they are frozen.
  def check_permission_to_edit
    if @comment&.iced?
      flash[:error] = t("comment.check_permission_to_edit.error.frozen")
      redirect_back(fallback_location: root_path)
    elsif !@comment&.count_all_comments&.zero?
      flash[:error] = ts("Comments with replies cannot be edited")
      redirect_back(fallback_location: root_path)
    end
  end

  # Comments on works can be frozen or unfrozen by admins with proper
  # authorization or the work creator.
  # Comments on tags can be frozen or unfrozen by admins with proper
  # authorization.
  # Comments on admin posts can be frozen or unfrozen by any admin.
  def check_permission_to_modify_frozen_status
    return if permission_to_modify_frozen_status

    flash[:error] = t(".permission_denied")
    redirect_back(fallback_location: root_path)
  end

  def check_permission_to_modify_hidden_status
    return if policy(@comment).can_hide_comment?

    flash[:error] = t(".permission_denied")
    redirect_back(fallback_location: root_path)
  end

  # Get the thing the user is trying to comment on
  def load_commentable
    @thread_view = false
    if params[:comment_id]
      @thread_view = true
      if params[:id]
        @commentable = Comment.find(params[:id])
        @thread_root = Comment.find(params[:comment_id])
      else
        @commentable = Comment.find(params[:comment_id])
        @thread_root = @commentable
      end
    elsif params[:chapter_id]
      @commentable = Chapter.find(params[:chapter_id])
    elsif params[:work_id]
      @commentable = Work.find(params[:work_id])
    elsif params[:admin_post_id]
      @commentable = AdminPost.find(params[:admin_post_id])
    elsif params[:tag_id]
      @commentable = Tag.find_by_name(params[:tag_id])
      @page_subtitle = @commentable.try(:name)
    end
  end

  def index
    return raise_not_found if @commentable.blank?

    return unless @commentable.class == Comment

    # we link to the parent object at the top
    @commentable = @commentable.ultimate_parent
  end

  def unreviewed
    @comments = @commentable.find_all_comments
      .unreviewed_only
      .for_display
      .page(params[:page])
  end

  # GET /comments/1
  # GET /comments/1.xml
  def show
    @comments = CommentDecorator.wrap_comments([@comment])
    @thread_view = true
    @thread_root = @comment
    params[:comment_id] = params[:id]
  end

  # GET /comments/new
  def new
    if @commentable.nil?
      flash[:error] = ts("What did you want to comment on?")
      redirect_back_or_default(root_path)
    else
      @comment = Comment.new
      @controller_name = params[:controller_name] if params[:controller_name]
      @name =
        case @commentable.class.name
        when /Work/
          @commentable.title
        when /Chapter/
          @commentable.work.title
        when /Tag/
          @commentable.name
        when /AdminPost/
          @commentable.title
        when /Comment/
          ts("Previous Comment")
        else
          @commentable.class.name
        end
    end
  end

  # GET /comments/1/edit
  def edit
    respond_to do |format|
      format.html
      format.js
    end
  end

  # POST /comments
  # POST /comments.xml
  def create
    if @commentable.nil?
      flash[:error] = ts("What did you want to comment on?")
      redirect_back_or_default(root_path)
    else
      @comment = Comment.new(comment_params)
      @comment.ip_address = request.remote_ip
      @comment.user_agent = request.env["HTTP_USER_AGENT"]
      @comment.commentable = Comment.commentable_object(@commentable)
      @controller_name = params[:controller_name]

      # First, try saving the comment
      if @comment.save
        if @comment.approved?
          if @comment.unreviewed?
            flash[:comment_notice] = ts("Your comment was received! It will appear publicly after the work creator has approved it.")
          else
            flash[:comment_notice] = ts("Comment created!")
          end
          respond_to do |format|
            format.html do
              if request.referer&.match(/inbox/)
                redirect_to user_inbox_path(current_user, filters: filter_params[:filters], page: params[:page])
              elsif request.referer&.match(/new/)
                # came here from the new comment page, probably via download link
                # so go back to the comments page instead of reloading full work
                redirect_to comment_path(@comment)
              elsif request.referer == "#{root_url}"
                # replying on the homepage
                redirect_to root_path
              elsif @comment.unreviewed? && current_user
                redirect_to comment_path(@comment)
              elsif @comment.unreviewed?
                redirect_to_all_comments(@commentable)
              else
                redirect_to_comment(@comment, {view_full_work: (params[:view_full_work] == "true"), page: params[:page]})
              end
            end
          end
        else
          # this shouldn't come up any more
          flash[:comment_notice] = ts("Sorry, but this comment looks like spam to us.")
          redirect_back_or_default(root_path)
        end
      else
        flash[:error] = ts("Couldn't save comment!")
        render action: "new"
      end
    end
  end

  # PUT /comments/1
  # PUT /comments/1.xml
  def update
    updated_comment_params = comment_params.merge(edited_at: Time.current)
    if @comment.update(updated_comment_params)
      flash[:comment_notice] = ts('Comment was successfully updated.')
      respond_to do |format|
        format.html do
          redirect_to comment_path(@comment) and return if @comment.unreviewed?
          redirect_to_comment(@comment)
        end
        format.js # updating the comment in place
      end
    else
      render action: "edit"
    end
  end

  # DELETE /comments/1
  # DELETE /comments/1.xml
  def destroy
    authorize @comment if logged_in_as_admin?

    parent = @comment.ultimate_parent
    parent_comment = @comment.reply_comment? ? @comment.commentable : nil
    unreviewed = @comment.unreviewed?

    if !@comment.destroy_or_mark_deleted
      # something went wrong?
      flash[:comment_error] = ts("We couldn't delete that comment.")
      redirect_to_comment(@comment)
    elsif unreviewed
      # go back to the rest of the unreviewed comments
      flash[:notice] = ts("Comment deleted.")
      redirect_back(fallback_location: unreviewed_work_comments_path(@comment.commentable))
    elsif parent_comment
      flash[:comment_notice] = ts("Comment deleted.")
      redirect_to_comment(parent_comment)
    else
      flash[:comment_notice] = ts("Comment deleted.")
      redirect_to_all_comments(parent, {show_comments: true})
    end
  end

  def review
    return unless @comment && current_user_owns?(@comment.ultimate_parent) && @comment.unreviewed?
    @comment.toggle!(:unreviewed)
    # mark associated inbox comments as read
    InboxComment.where(user_id: current_user.id, feedback_comment_id: @comment.id).update_all(read: true)
    flash[:notice] = ts("Comment approved.")
    respond_to do |format|
      format.html do
        if params[:approved_from] == "inbox"
          redirect_to user_inbox_path(current_user, page: params[:page], filters: filter_params[:filters])
        elsif params[:approved_from] == "home"
          redirect_to root_path
        else
          redirect_to unreviewed_work_comments_path(@comment.ultimate_parent)
        end
        return
      end
      format.js
    end
  end

  def review_all
    unless @commentable && current_user_owns?(@commentable)
      flash[:error] = ts("What did you want to review comments on?")
      redirect_back_or_default(root_path)
      return
    end

    @comments = @commentable.find_all_comments.unreviewed_only
    @comments.each { |c| c.toggle!(:unreviewed) }
    flash[:notice] = ts("All moderated comments approved.")
    redirect_to @commentable
  end

  def approve
    authorize @comment
    @comment.mark_as_ham!
    redirect_to_all_comments(@comment.ultimate_parent, show_comments: true)
  end

  def reject
    authorize @comment if logged_in_as_admin?
    @comment.mark_as_spam!
    redirect_to_all_comments(@comment.ultimate_parent, show_comments: true)
  end

  # PUT /comments/1/freeze
  def freeze
    # TODO: When AO3-5939 is fixed, we can use
    # @comment.full_set.each(&:mark_frozen!)
    if !@comment.iced? && @comment.save
      @comment.set_to_freeze_or_unfreeze.each(&:mark_frozen!)
      flash[:comment_notice] = t(".success")
    else
      flash[:comment_error] = t(".error")
    end
    redirect_to_all_comments(@comment.ultimate_parent, show_comments: true)
  end

  # PUT /comments/1/unfreeze
  def unfreeze
    # TODO: When AO3-5939 is fixed, we can use
    # @comment.full_set.each(&:mark_unfrozen!)
    if @comment.iced? && @comment.save
      @comment.set_to_freeze_or_unfreeze.each(&:mark_unfrozen!)
      flash[:comment_notice] = t(".success")
    else
      flash[:comment_error] = t(".error")
    end
    redirect_to_all_comments(@comment.ultimate_parent, show_comments: true)
  end

  # PUT /comments/1/hide
  def hide
    if !@comment.hidden_by_admin?
      @comment.mark_hidden!
      AdminActivity.log_action(current_admin, @comment, action: "hide comment")
      flash[:comment_notice] = t(".success")
    else
      flash[:comment_error] = t(".error")
    end
    redirect_to_all_comments(@comment.ultimate_parent, show_comments: true)
  end

  # PUT /comments/1/unhide
  def unhide
    if @comment.hidden_by_admin?
      @comment.mark_unhidden!
      AdminActivity.log_action(current_admin, @comment, action: "unhide comment")
      flash[:comment_notice] = t(".success")
    else
      flash[:comment_error] = t(".error")
    end
    redirect_to_all_comments(@comment.ultimate_parent, show_comments: true)
  end

  def show_comments
    respond_to do |format|
      format.html do
        # if non-ajax it could mean sudden javascript failure OR being redirected from login
        # so we're being extra-nice and preserving any intention to comment along with the show comments option
        options = {show_comments: true}
        options[:add_comment_reply_id] = params[:add_comment_reply_id] if params[:add_comment_reply_id]
        options[:view_full_work] = params[:view_full_work] if params[:view_full_work]
        options[:page] = params[:page]
        redirect_to_all_comments(@commentable, options)
      end

      format.js do
        @comments = CommentDecorator.for_commentable(@commentable, page: params[:page])
      end
    end
  end

  def hide_comments
    respond_to do |format|
      format.html do
        redirect_to_all_comments(@commentable)
      end
      format.js
    end
  end

  # If JavaScript is enabled, use add_comment_reply.js to load the reply form
  # Otherwise, redirect to a comment view with the form already loaded
  def add_comment_reply
    @comment = Comment.new
    respond_to do |format|
      format.html do
        options = {show_comments: true}
        options[:controller] = @commentable.class.to_s.underscore.pluralize
        options[:anchor] = "comment_#{params[:id]}"
        options[:page] = params[:page]
        options[:view_full_work] = params[:view_full_work]
        if @thread_view
          options[:id] = @thread_root
          options[:add_comment_reply_id] = params[:id]
          redirect_to_comment(@commentable, options)
        else
          options[:id] = @commentable.id # work, chapter or other stuff that is not a comment
          options[:add_comment_reply_id] = params[:id]
          redirect_to_all_comments(@commentable, options)
        end
      end
      format.js { @commentable = Comment.find(params[:id]) }
    end
  end

  def cancel_comment_reply
    respond_to do |format|
      format.html do
        options = {}
        options[:show_comments] = params[:show_comments] if params[:show_comments]
        redirect_to_all_comments(@commentable, options)
      end
      format.js { @commentable = Comment.find(params[:id]) }
    end
  end

  def cancel_comment_edit
    respond_to do |format|
      format.html { redirect_to_comment(@comment) }
      format.js
    end
  end

  def delete_comment
    respond_to do |format|
      format.html do
        options = {}
        options[:show_comments] = params[:show_comments] if params[:show_comments]
        options[:delete_comment_id] = params[:id] if params[:id]
        redirect_to_comment(@comment, options) # TO DO: deleting without javascript doesn't work and it never has!
      end
      format.js
    end
  end

  def cancel_comment_delete
    respond_to do |format|
      format.html do
        options = {}
        options[:show_comments] = params[:show_comments] if params[:show_comments]
        redirect_to_comment(@comment, options)
      end
      format.js
    end
  end

  protected

  # redirect to a particular comment in a thread, going into the thread
  # if necessary to display it
  def redirect_to_comment(comment, options = {})
    if comment.depth > ArchiveConfig.COMMENT_THREAD_MAX_DEPTH
      if comment.ultimate_parent.is_a?(Tag)
        default_options = {
           controller: :comments,
           action: :show,
           id: comment.commentable.id,
           tag_id: comment.ultimate_parent.to_param,
           anchor: "comment_#{comment.id}"
        }
      else
        default_options = {
           controller: comment.commentable.class.to_s.underscore.pluralize,
           action: :show,
           id: (comment.commentable.is_a?(Tag) ? comment.commentable.to_param : comment.commentable.id),
           anchor: "comment_#{comment.id}"
        }
      end
      # display the comment's direct parent (and its associated thread)
      redirect_to(url_for(default_options.merge(options)))
    else
      # need to redirect to the specific chapter; redirect_to_all will then retrieve full work view if applicable
      redirect_to_all_comments(comment.parent, options.merge({show_comments: true, anchor: "comment_#{comment.id}"}))
    end
  end

  def redirect_to_all_comments(commentable, options = {})
    default_options = {anchor: "comments"}
    options = default_options.merge(options)

    if commentable.is_a?(Tag)
      redirect_to comments_path(tag_id: commentable.to_param,
                  add_comment_reply_id: options[:add_comment_reply_id],
                  delete_comment_id: options[:delete_comment_id],
                  page: options[:page],
                  anchor: options[:anchor])
    else
      if commentable.is_a?(Chapter) && (options[:view_full_work] || current_user.try(:preference).try(:view_full_works))
        commentable = commentable.work
      end
      redirect_to controller: commentable.class.to_s.underscore.pluralize,
                  action: :show,
                  id: commentable.id,
                  show_comments: options[:show_comments],
                  add_comment_reply_id: options[:add_comment_reply_id],
                  delete_comment_id: options[:delete_comment_id],
                  view_full_work: options[:view_full_work],
                  anchor: options[:anchor],
                  page: options[:page]
    end
  end

  def permission_to_modify_frozen_status
    parent = find_parent
    return true if policy(@comment).can_freeze_comment?
    return true if parent.is_a?(Work) && current_user_owns?(parent)

    false
  end

  private

  def comment_params
    params.require(:comment).permit(
      :pseud_id, :comment_content, :name, :email, :edited_at
    )
  end

  def filter_params
    params.permit!
  end
end
