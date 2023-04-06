using UnityEngine;
using System.Collections;
using System.Collections.Generic;


public class Go : MonoBehaviour
{
	// defaults used for all tweens/properties that are not explicitly set
	public static GoEaseType defaultEaseType = GoEaseType.Linear;
	public static GoLoopType defaultLoopType = GoLoopType.RestartFromBeginning;
	public static GoUpdateType defaultUpdateType = GoUpdateType.Update;

	// defines what we should do in the event that a TweenProperty is added and an already existing tween has the same
	// property and target
	public static GoDuplicatePropertyRuleType duplicatePropertyRule = GoDuplicatePropertyRuleType.None;
	public static GoLogLevel logLevel = GoLogLevel.Warn;

	// validates that the target object still exists each tick of the tween. NOTE: it is recommended
	// that you just properly remove your tweens before destroying any objects even though this might destroy them for you
	public static bool validateTargetObjectsEachTick = true;

	// Used to stop instances being created while the application is quitting
	private static bool _applicationIsQuitting = false;

	private static List<AbstractGoTween> _tweens = new List<AbstractGoTween>(); // contains Tweens, TweenChains and TweenFlows
	private bool _timeScaleIndependentUpdateIsRunning;

	// only one Go can exist
	static Go _instance = null;
	public static Go instance
	{
		get
		{
			// Don't allow new instances to be created when the application is quitting to avoid the GOKit object never being destroyed.
			// These dangling instances can't be found with FindObjectOfType and so you'd get multiple instances in a scene.
			if( !_instance && !_applicationIsQuitting )
			{
				// check if there is a GO instance already available in the scene graph
				_instance = FindObjectOfType( typeof( Go ) ) as Go;

				// possible Unity bug with FindObjectOfType workaround
				//_instance = FindObjectOfType( typeof( Go ) ) ?? GameObject.Find( "GoKit" ).GetComponent<Go>() as Go;

				// nope, create a new one
				if( !_instance )
				{
					var obj = new GameObject( "GoKit" );
					_instance = obj.AddComponent<Go>();
					DontDestroyOnLoad( obj );
				}
			}

			return _instance;
		}
	}


	/// <summary>
	/// loops through all the Tweens and updates any that are of updateType. If any Tweens are complete
	/// (the update call will return true) they are removed.
	/// </summary>
	private void handleUpdateOfType( GoUpdateType updateType, float deltaTime )
	{
		// loop backwards so we can remove completed tweens
		for( var i = _tweens.Count - 1; i >= 0; --i )
		{
			var t = _tweens[i];

			if( t.state == GoTweenState.Destroyed )
			{
				// destroy method has been called
				removeTween( t );
			}
			else
			{
				// only process tweens with our update type that are running
				if( t.updateType == updateType && t.state == GoTweenState.Running && t.update( deltaTime * t.timeScale ) )
				{
					// tween is complete if we get here. if destroyed or set to auto remove kill it
					if( t.state == GoTweenState.Destroyed || t.autoRemoveOnComplete )
					{
						removeTween( t );
						t.destroy();
					}
				}
			}
		}
	}


	#region Monobehaviour

	private void Update()
	{
		if( _tweens.Count == 0 )
			return;

		handleUpdateOfType( GoUpdateType.Update, Time.deltaTime );
	}


	private void LateUpdate()
	{
		if( _tweens.Count == 0 )
			return;

		handleUpdateOfType( GoUpdateType.LateUpdate, Time.deltaTime );
	}


	private void FixedUpdate()
	{
		if( _tweens.Count == 0 )
			return;

		handleUpdateOfType( GoUpdateType.FixedUpdate, Time.deltaTime );
	}


	private void OnApplicationQuit()
	{
		_instance = null;
		Destroy( gameObject );
		_applicationIsQuitting = true;
	}

	#endregion


    /// <summary>
    /// this only runs as needed and handles time scale independent Tweens
    /// </summary>
    private IEnumerator timeScaleIndependentUpdate()
    {
		_timeScaleIndependentUpdateIsRunning = true;
		var time = Time.realtimeSinceStartup;

        while( _tweens.Count > 0 )
        {
            var elapsed = Time.realtimeSinceStartup - time;
            time = Time.realtimeSinceStartup;

            // update tweens
            handleUpdateOfType( GoUpdateType.TimeScaleIndependentUpdate, elapsed );

            yield return null;
        }

		_timeScaleIndependentUpdateIsRunning = false;
    }


	/// <summary>
	/// checks for duplicate properties. if one is found and the DuplicatePropertyRuleType is set to
	/// DontAddCurrentProperty it will return true indicating that the tween should not be added.
	/// this only checks tweens that are not part of an AbstractTweenCollection
	/// </summary>
	private static bool handleDuplicatePropertiesInTween( GoTween tween )
	{
		// first fetch all the current tweens with the same target object as this one
		var allTweensWithTarget = tweensWithTarget( tween.target );

		// store a list of all the properties in the tween
		var allProperties = tween.allTweenProperties();

		// TODO: perhaps only perform the check on running Tweens?

		// loop through all the tweens with the same target
		foreach( var tweenWithTarget in allTweensWithTarget )
		{
			// loop through all the properties in the tween and see if there are any dupes
			foreach( var tweenProp in allProperties )
			{
				warn( "found duplicate TweenProperty {0} in tween {1}", tweenProp, tween );

				// check for a matched property
				if( tweenWithTarget.containsTweenProperty( tweenProp ) )
				{
					// handle the different duplicate property rules
					if( duplicatePropertyRule == GoDuplicatePropertyRuleType.DontAddCurrentProperty )
					{
						return true;
					}
					else if( duplicatePropertyRule == GoDuplicatePropertyRuleType.RemoveRunningProperty )
					{
						// TODO: perhaps check if the Tween has any properties left and remove it if it doesnt?
						tweenWithTarget.removeTweenProperty( tweenProp );
					}

					return false;
				}
			}
		}

		return false;
	}


	#region Logging

	/// <summary>
	/// logging should only occur in the editor so we use a conditional
	/// </summary>
	[System.Diagnostics.Conditional( "UNITY_EDITOR" )]
	private static void log( object format, params object[] paramList )
	{
		if( format is string )
			Debug.Log( string.Format( format as string, paramList ) );
		else
			Debug.Log( format );
	}


	[System.Diagnostics.Conditional( "UNITY_EDITOR" )]
	public static void warn( object format, params object[] paramList )
	{
		if( logLevel == GoLogLevel.None || logLevel == GoLogLevel.Info )
			return;

		if( format is string )
			Debug.LogWarning( string.Format( format as string, paramList ) );
		else
			Debug.LogWarning( format );
	}


	[System.Diagnostics.Conditional( "UNITY_EDITOR" )]
	public static void error( object format, params object[] paramList )
	{
		if( logLevel == GoLogLevel.None || logLevel == GoLogLevel.Info || logLevel == GoLogLevel.Warn )
			return;

		if( format is string )
			Debug.LogError( string.Format( format as string, paramList ) );
		else
			Debug.LogError( format );
	}

	#endregion


	#region public API

	/// <summary>
	/// helper function that creates a "to" Tween and adds it to the pool
	/// </summary>
	public static GoTween to( object target, float duration, GoTweenConfig config )
	{
        config.setIsTo();
		var tween = new GoTween( target, duration, config );
		addTween( tween );

		return tween;
	}


	/// <summary>
	/// helper function that creates a "from" Tween and adds it to the pool
	/// </summary>
	public static GoTween from( object target, float duration, GoTweenConfig config )
	{
		config.setIsFrom();
		var tween = new GoTween( target, duration, config );
		addTween( tween );

		return tween;
	}


	/// <summary>
	/// adds an AbstractTween (Tween, TweenChain or TweenFlow) to the current list of running Tweens
	/// </summary>
	public static void addTween( AbstractGoTween tween )
	{
		// early out for invalid items
		if( !tween.isValid() )
			return;

		// dont add the same tween twice
		if( _tweens.Contains( tween ) )
			return;

		// check for dupes and handle them before adding the tween. we only need to check for Tweens
		if( duplicatePropertyRule != GoDuplicatePropertyRuleType.None && tween is GoTween )
		{
			// if handleDuplicatePropertiesInTween returns true it indicates we should not add this tween
			if( handleDuplicatePropertiesInTween( tween as GoTween ) )
				return;

			// if we became invalid after handling dupes dont add the tween
			if( !tween.isValid() )
				return;
		}

		_tweens.Add( tween );

		// enable ourself if we are not enabled
		if( !instance.enabled ) // purposely using the static instace property just once for initialization
			_instance.enabled = true;

		// if the Tween isn't paused and it is a "from" tween jump directly to the start position
		if( tween is GoTween && ((GoTween)tween).isFrom && tween.state != GoTweenState.Paused )
			tween.update( 0 );

		// should we start up the time scale independent update?
		if( !_instance._timeScaleIndependentUpdateIsRunning && tween.updateType == GoUpdateType.TimeScaleIndependentUpdate )
			_instance.StartCoroutine( _instance.timeScaleIndependentUpdate() );

#if UNITY_EDITOR
		_instance.gameObject.name = string.Format( "GoKit ({0} tweens)", _tweens.Count );
#endif
	}


	/// <summary>
	/// removes the Tween returning true if it was removed or false if it was not found
	/// </summary>
	public static bool removeTween( AbstractGoTween tween )
	{
		if( _tweens.Contains( tween ) )
		{
			_tweens.Remove( tween );

#if UNITY_EDITOR
		if( _instance != null && _tweens != null )
			_instance.gameObject.name = string.Format( "GoKit ({0} tweens)", _tweens.Count );
#endif

			if( _instance != null && _tweens.Count == 0 )
			{
				// disable ourself if we have no more tweens
				_instance.enabled = false;
			}

			return true;
		}

		return false;
	}


	/// <summary>
	/// returns a list of all Tweens, TweenChains and TweenFlows with the given id
	/// </summary>
	public static List<AbstractGoTween> tweensWithId( int id )
	{
		List<AbstractGoTween> list = null;

		foreach( var tween in _tweens )
		{
			if( tween.id == id )
			{
				if( list == null )
					list = new List<AbstractGoTween>();
				list.Add( tween );
			}
		}

		return list;
	}


	/// <summary>
	/// returns a list of all Tweens with the given target. TweenChains and TweenFlows can optionally
	/// be traversed and matching Tweens returned as well.
	/// </summary>
	public static List<GoTween> tweensWithTarget( object target, bool traverseCollections = false )
	{
		List<GoTween> list = new List<GoTween>();

		foreach( var item in _tweens )
		{
			// we always check Tweens so handle them first
			var tween = item as GoTween;
			if( tween != null && tween.target == target )
				list.Add( tween );

			// optionally check TweenChains and TweenFlows. if tween is null we have a collection
			if( traverseCollections && tween == null )
			{
				var tweenCollection = item as AbstractGoTweenCollection;
				if( tweenCollection != null )
				{
					var tweensInCollection = tweenCollection.tweensWithTarget( target );
					if( tweensInCollection.Count > 0 )
						list.AddRange( tweensInCollection );
				}
			}
		}

		return list;
	}


	/// <summary>
	/// kills all tweens with the given target by calling the destroy method on each one
	/// </summary>
	public static void killAllTweensWithTarget( object target )
	{
		foreach( var tween in tweensWithTarget( target, true ) )
			tween.destroy();
	}

	#endregion

}
