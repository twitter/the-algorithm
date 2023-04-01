class KnownIssuesController < ApplicationController

  before_action :admin_only, except: [:index]

  # GET /known_issues
  def index
    @known_issues = KnownIssue.all
  end

  # GET /known_issues/1
  def show
    @known_issue = KnownIssue.find(params[:id])
  end

  # GET /known_issues/new
  def new
    @known_issue = KnownIssue.new
  end

  # GET /known_issues/1/edit
  def edit
    @known_issue = KnownIssue.find(params[:id])
  end

  # POST /known_issues
  def create
    @known_issue = KnownIssue.new(known_issue_params)

    if @known_issue.save
      flash[:notice] = 'Known issue was successfully created.'
      redirect_to(@known_issue)
    else
      render action: "new"
    end
  end

  # PUT /known_issues/1
  def update
    @known_issue = KnownIssue.find(params[:id])

    if @known_issue.update(known_issue_params)
      flash[:notice] = 'Known issue was successfully updated.'
      redirect_to(@known_issue)
    else
      render action: "edit"
    end
  end

  # DELETE /known_issues/1
  def destroy
    @known_issue = KnownIssue.find(params[:id])
    @known_issue.destroy
    redirect_to(known_issues_path)
  end

  private

  def known_issue_params
    params.require(:known_issue).permit(:title, :content)
  end
end
