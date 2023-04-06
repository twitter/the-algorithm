using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;


public class GoTweenCollectionConfig
{
	public int id; // id for finding the Tween at a later time. multiple Tweens can have the same id
	public int iterations = 1; // number of times to iterate. -1 will loop indefinitely
	public GoLoopType loopType = Go.defaultLoopType;
	public GoUpdateType propertyUpdateType = Go.defaultUpdateType;

    public Action<AbstractGoTween> onInitHandler;
    public Action<AbstractGoTween> onBeginHandler;
    public Action<AbstractGoTween> onIterationStartHandler;
    public Action<AbstractGoTween> onUpdateHandler;
    public Action<AbstractGoTween> onIterationEndHandler;
    public Action<AbstractGoTween> onCompleteHandler;
	

	/// <summary>
	/// sets the number of iterations. setting to -1 will loop infinitely
	/// </summary>
	public GoTweenCollectionConfig setIterations( int iterations )
	{
		this.iterations = iterations;
		return this;
	}
	
	
	/// <summary>
	/// sets the number of iterations and the loop type. setting to -1 will loop infinitely
	/// </summary>
	public GoTweenCollectionConfig setIterations( int iterations, GoLoopType loopType )
	{
		this.iterations = iterations;
		this.loopType = loopType;
		return this;
	}
	
	
	/// <summary>
	/// sets the update type for the Tween
	/// </summary>
	public GoTweenCollectionConfig setUpdateType( GoUpdateType setUpdateType )
	{
		this.propertyUpdateType = setUpdateType;
		return this;
	}


    /// <summary>
    /// sets the onInit handler for the Tween
    /// </summary>
    public GoTweenCollectionConfig onInit( Action<AbstractGoTween> onInit )
    {
        onInitHandler = onInit;
        return this;
    }


    /// <summary>
    /// sets the onBegin handler for the Tween
    /// </summary>
    public GoTweenCollectionConfig onBegin( Action<AbstractGoTween> onBegin )
    {
        onBeginHandler = onBegin;
        return this;
    }


    /// <summary>
    /// sets the onIterationStart handler for the Tween
    /// </summary>
    public GoTweenCollectionConfig onIterationStart( Action<AbstractGoTween> onIterationStart )
    {
        onIterationStartHandler = onIterationStart;
        return this;
    }


    /// <summary>
    /// sets the onUpdate handler for the Tween
    /// </summary>
    public GoTweenCollectionConfig onUpdate( Action<AbstractGoTween> onUpdate )
    {
        onUpdateHandler = onUpdate;
        return this;
    }


    /// <summary>
    /// sets the onIterationEnd handler for the Tween
    /// </summary>
    public GoTweenCollectionConfig onIterationEnd( Action<AbstractGoTween> onIterationEnd )
    {
        onIterationEndHandler = onIterationEnd;
        return this;
    }


    /// <summary>
    /// sets the onComplete handler for the Tween
    /// </summary>
    public GoTweenCollectionConfig onComplete( Action<AbstractGoTween> onComplete )
    {
        onCompleteHandler = onComplete;
        return this;
    }
	
	
	/// <summary>
	/// sets the id for the Tween. Multiple Tweens can have the same id and you can retrieve them with the Go class
	/// </summary>
	public GoTweenCollectionConfig setId( int id )
	{
		this.id = id;
		return this;
	}

}