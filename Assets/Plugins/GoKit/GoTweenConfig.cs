using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;


public class GoTweenConfig
{
	private List<AbstractTweenProperty> _tweenProperties = new List<AbstractTweenProperty>();
	public List<AbstractTweenProperty> tweenProperties { get { return _tweenProperties; } }
	
	public int id; // id for finding the Tween at a later time. multiple Tweens can have the same id
	public float delay; // how long should we delay before starting the Tween
	public int iterations = 1; // number of times to iterate. -1 will loop indefinitely
	public int timeScale = 1;
	public GoLoopType loopType = Go.defaultLoopType;
	public GoEaseType easeType = Go.defaultEaseType;
	public bool isPaused;
	public GoUpdateType propertyUpdateType = Go.defaultUpdateType;
	public bool isFrom;

    public Action<AbstractGoTween> onInitHandler;
    public Action<AbstractGoTween> onBeginHandler;
    public Action<AbstractGoTween> onIterationStartHandler;
    public Action<AbstractGoTween> onUpdateHandler;
    public Action<AbstractGoTween> onIterationEndHandler;
    public Action<AbstractGoTween> onCompleteHandler;
	
	
	#region TweenProperty adders
	
	/// <summary>
	/// position tween
	/// </summary>
	public GoTweenConfig position( Vector3 endValue, bool isRelative = false )
	{
		var prop = new PositionTweenProperty( endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	

	/// <summary>
	/// localPosition tween
	/// </summary>
	public GoTweenConfig localPosition( Vector3 endValue, bool isRelative = false )
	{
		var prop = new PositionTweenProperty( endValue, isRelative, true );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// position path tween
	/// </summary>
	public GoTweenConfig positionPath( GoSpline path, bool isRelative = false, GoLookAtType lookAtType = GoLookAtType.None, Transform lookTarget = null )
	{
		var prop = new PositionPathTweenProperty( path, isRelative, false, lookAtType, lookTarget );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// uniform scale tween (x, y and z scale to the same value)
	/// </summary>
	public GoTweenConfig scale( float endValue, bool isRelative = false )
	{
		return this.scale( new Vector3( endValue, endValue, endValue ), isRelative );
	}
	
	
	/// <summary>
	/// scale tween
	/// </summary>
	public GoTweenConfig scale( Vector3 endValue, bool isRelative = false )
	{
		var prop = new ScaleTweenProperty( endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}

	
	/// <summary>
	/// scale through a series of Vector3s
	/// </summary>
	public GoTweenConfig scalePath( GoSpline path, bool isRelative = false )
	{
		var prop = new ScalePathTweenProperty( path, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}

	
	/// <summary>
	/// eulerAngle tween
	/// </summary>
	public GoTweenConfig eulerAngles( Vector3 endValue, bool isRelative = false )
	{
		var prop = new EulerAnglesTweenProperty( endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// local eulerAngle tween
	/// </summary>
	public GoTweenConfig localEulerAngles( Vector3 endValue, bool isRelative = false )
	{
		var prop = new EulerAnglesTweenProperty( endValue, isRelative, true );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// rotation tween
	/// </summary>
	public GoTweenConfig rotation( Vector3 endValue, bool isRelative = false )
	{
		var prop = new RotationTweenProperty( endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}


	/// <summary>
	/// localRotation tween
	/// </summary>
	public GoTweenConfig localRotation( Vector3 endValue, bool isRelative = false )
	{
		var prop = new RotationTweenProperty( endValue, isRelative, true );
		_tweenProperties.Add( prop );
		
		return this;
	}


	/// <summary>
	/// rotation tween as Quaternion
	/// </summary>
	public GoTweenConfig rotation( Quaternion endValue, bool isRelative = false )
	{
		var prop = new RotationQuaternionTweenProperty( endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}

	/// <summary>
	/// localRotation tween as Quaternion
	/// </summary>
	public GoTweenConfig localRotation( Quaternion endValue, bool isRelative = false )
	{
		var prop = new RotationQuaternionTweenProperty( endValue, isRelative, true );
		_tweenProperties.Add( prop );
		
		return this;
	}


	/// <summary>
	/// material color tween
	/// </summary>
	public GoTweenConfig materialColor( Color endValue, string colorName = "_Color", bool isRelative = false )
	{
		var prop = new MaterialColorTweenProperty( endValue, colorName, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// shake tween
	/// </summary>
	public GoTweenConfig shake( Vector3 shakeMagnitude, GoShakeType shakeType = GoShakeType.Position, int frameMod = 1, bool useLocalProperties = false )
	{
		var prop = new ShakeTweenProperty( shakeMagnitude, shakeType, frameMod, useLocalProperties );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	#region generic properties
	
	/// <summary>
	/// generic vector2 tween
	/// </summary>
	public GoTweenConfig vector2Prop( string propertyName, Vector2 endValue, bool isRelative = false )
	{
		var prop = new Vector2TweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// generic vector3 tween
	/// </summary>
	public GoTweenConfig vector3Prop( string propertyName, Vector3 endValue, bool isRelative = false )
	{
		var prop = new Vector3TweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// generic vector4 tween
	/// </summary>
	public GoTweenConfig vector4Prop( string propertyName, Vector4 endValue, bool isRelative = false )
	{
		var prop = new Vector4TweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	

	/// <summary>
	/// generic vector3 path tween
	/// </summary>
	public GoTweenConfig vector3PathProp( string propertyName, GoSpline path, bool isRelative = false )
	{
		var prop = new Vector3PathTweenProperty( propertyName, path, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// generic vector3.x tween
	/// </summary>
	public GoTweenConfig vector3XProp( string propertyName, float endValue, bool isRelative = false )
	{
		var prop = new Vector3XTweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// generic vector3.y tween
	/// </summary>
	public GoTweenConfig vector3YProp( string propertyName, float endValue, bool isRelative = false )
	{
		var prop = new Vector3YTweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// generic vector3.z tween
	/// </summary>
	public GoTweenConfig vector3ZProp( string propertyName, float endValue, bool isRelative = false )
	{
		var prop = new Vector3ZTweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// generic color tween
	/// </summary>
	public GoTweenConfig colorProp( string propertyName, Color endValue, bool isRelative = false )
	{
		var prop = new ColorTweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// generic integer tween
	/// </summary>
	public GoTweenConfig intProp( string propertyName, int endValue, bool isRelative = false )
	{
		var prop = new IntTweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	
	/// <summary>
	/// generic float tween
	/// </summary>
	public GoTweenConfig floatProp( string propertyName, float endValue, bool isRelative = false )
	{
		var prop = new FloatTweenProperty( propertyName, endValue, isRelative );
		_tweenProperties.Add( prop );
		
		return this;
	}
	
	#endregion
	
	#endregion
	
	
	/// <summary>
	/// adds a TweenProperty to the list
	/// </summary>
	public GoTweenConfig addTweenProperty( AbstractTweenProperty tweenProp )
	{
		_tweenProperties.Add( tweenProp );

		return this;
	}
	
	
	/// <summary>
	/// clears out all the TweenProperties
	/// </summary>
	public GoTweenConfig clearProperties()
	{
		_tweenProperties.Clear();

		return this;
	}

    /// <summary>
    /// clears out all the TweenProperties
    /// </summary>
    public GoTweenConfig clearEvents()
    {
        onInitHandler = null;
        onBeginHandler = null;
        onIterationStartHandler = null;
        onUpdateHandler = null;
        onIterationEndHandler = null;
        onCompleteHandler = null;

        return this;
    }
	
	/// <summary>
	/// sets the delay for the tween
	/// </summary>
	public GoTweenConfig setDelay( float seconds )
	{
		delay = seconds;

		return this;
	}
	
	
	/// <summary>
	/// sets the number of iterations. setting to -1 will loop infinitely
	/// </summary>
	public GoTweenConfig setIterations( int iterations )
	{
		this.iterations = iterations;

		return this;
	}
	
	
	/// <summary>
	/// sets the number of iterations and the loop type. setting to -1 will loop infinitely
	/// </summary>
	public GoTweenConfig setIterations( int iterations, GoLoopType loopType )
	{
		this.iterations = iterations;
		this.loopType = loopType;

		return this;
	}
	
	
	/// <summary>
	/// sets the timeScale to be used by the Tween
	/// </summary>
	public GoTweenConfig setTimeScale( int timeScale )
	{
		this.timeScale = timeScale;

		return this;
	}
	
	
	/// <summary>
	/// sets the ease type for the Tween
	/// </summary>
	public GoTweenConfig setEaseType( GoEaseType easeType )
	{
		this.easeType = easeType;

		return this;
	}
	
	
	/// <summary>
	/// sets whether the Tween should start paused
	/// </summary>
	public GoTweenConfig startPaused()
	{
		isPaused = true;

		return this;
	}
	
	
	/// <summary>
	/// sets the update type for the Tween
	/// </summary>
	public GoTweenConfig setUpdateType( GoUpdateType setUpdateType )
	{
		propertyUpdateType = setUpdateType;

		return this;
	}


    /// <summary>
    /// sets if this Tween should be a "from" Tween. From Tweens use the current property as the endValue and
    /// the endValue as the start value
    /// </summary>
    public GoTweenConfig setIsFrom()
    {
        isFrom = true;

        return this;
    }

    /// <summary>
    /// sets if this Tween should be a "to" Tween.
    /// </summary>
    public GoTweenConfig setIsTo()
    {
        isFrom = false;

        return this;
    }


    /// <summary>
    /// sets the onInit handler for the Tween
    /// </summary>
    public GoTweenConfig onInit( Action<AbstractGoTween> onInit )
    {
        onInitHandler = onInit;
        return this;
    }


	/// <summary>
    /// sets the onBegin handler for the Tween
	/// </summary>
    public GoTweenConfig onBegin( Action<AbstractGoTween> onBegin )
	{
        onBeginHandler = onBegin;

		return this;
	}


    /// <summary>
    /// sets the onIterationStart handler for the Tween
    /// </summary>
    public GoTweenConfig onIterationStart( Action<AbstractGoTween> onIterationStart )
    {
        onIterationStartHandler = onIterationStart;

        return this;
    }


    /// <summary>
    /// sets the onUpdate handler for the Tween
    /// </summary>
    public GoTweenConfig onUpdate( Action<AbstractGoTween> onUpdate )
    {
        onUpdateHandler = onUpdate;

        return this;
    }


    /// <summary>
    /// sets the onIterationEnd handler for the Tween
    /// </summary>
    public GoTweenConfig onIterationEnd( Action<AbstractGoTween> onIterationEnd )
    {
        onIterationEndHandler = onIterationEnd;

        return this;
    }


    /// <summary>
    /// sets the onComplete handler for the Tween
    /// </summary>
    public GoTweenConfig onComplete( Action<AbstractGoTween> onComplete )
    {
        onCompleteHandler = onComplete;

        return this;
    }

	
	/// <summary>
	/// sets the id for the Tween. Multiple Tweens can have the same id and you can retrieve them with the Go class
	/// </summary>
	public GoTweenConfig setId( int id )
	{
		this.id = id;

		return this;
	}

}
