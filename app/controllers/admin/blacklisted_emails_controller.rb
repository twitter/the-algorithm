class Admin::BlacklistedEmailsController < Admin::BaseController

  def index
    authorize AdminBlacklistedEmail
    @admin_blacklisted_email = AdminBlacklistedEmail.new
    if params[:query]
      @admin_blacklisted_emails = AdminBlacklistedEmail.where(["email LIKE ?", '%' + params[:query] + '%'])
      @admin_blacklisted_emails = @admin_blacklisted_emails.paginate(page: params[:page], per_page: ArchiveConfig.ITEMS_PER_PAGE)
    end
    @page_subtitle = t(".browser_title")
  end

  def create
    @admin_blacklisted_email = authorize AdminBlacklistedEmail.new(admin_blacklisted_email_params)
    @page_subtitle = t(".browser_title")

    if @admin_blacklisted_email.save
      flash[:notice] = ts("Email address #{@admin_blacklisted_email.email} banned.")
      redirect_to admin_blacklisted_emails_path
    else
      render action: "index"
    end
  end

  def destroy
    @admin_blacklisted_email = authorize AdminBlacklistedEmail.find(params[:id])
    @admin_blacklisted_email.destroy

    flash[:notice] = ts("Email address #{@admin_blacklisted_email.email} removed from banned emails list.")
    redirect_to admin_blacklisted_emails_path
  end

  private

  def admin_blacklisted_email_params
    params.require(:admin_blacklisted_email).permit(
      :email
    )
  end
end
