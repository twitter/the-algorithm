// spline equation courtesy Andeeee's CRSpline (http://forum.unity3d.com/threads/32954-Waypoints-and-constant-variable-speed-problems?p=213942&viewfull=1#post213942)

using UnityEngine;
using System.IO;
using System.Collections;
using System.Collections.Generic;


public class GoSpline
{
	public int currentSegment { get; private set; }
	public bool isClosed { get; private set; }
	public GoSplineType splineType { get; private set; }
	
	// used by the visual path editor
	public List<Vector3> nodes { get { return _solver.nodes; } }

	private bool _isReversed; // internal flag that lets us know if our nodes are reversed or not
	private AbstractGoSplineSolver _solver;
	
	
	// default constructor
	public GoSpline( List<Vector3> nodes, bool useStraightLines = false )
	{
		// determine spline type and solver based on number of nodes
		if( useStraightLines || nodes.Count == 2 )
		{
			splineType = GoSplineType.StraightLine;
			_solver = new GoSplineStraightLineSolver( nodes );
		}
		else if( nodes.Count == 3 )
		{
			splineType = GoSplineType.QuadraticBezier;
			_solver = new GoSplineQuadraticBezierSolver( nodes );
		}
		else if( nodes.Count == 4 )
		{
			splineType = GoSplineType.CubicBezier;
			_solver = new GoSplineCubicBezierSolver( nodes );
		}
		else
		{
			splineType = GoSplineType.CatmullRom;
			_solver = new GoSplineCatmullRomSolver( nodes );
		}
	}
	
	
	public GoSpline( Vector3[] nodes, bool useStraightLines = false ) : this( new List<Vector3>( nodes ), useStraightLines )
	{}
	
	
	public GoSpline( string pathAssetName, bool useStraightLines = false ) : this( nodeListFromAsset( pathAssetName ), useStraightLines )
	{}
	
	
	/// <summary>
	/// helper to get a node list from an asset created with the visual editor
	/// </summary>
	private static List<Vector3> nodeListFromAsset( string pathAssetName )
	{
		/*if( Application.platform == RuntimePlatform.OSXWebPlayer || Application.platform == RuntimePlatform.WindowsWebPlayer )
		{
			Debug.LogError( "The Web Player does not support loading files from disk." );
			return null;
		}*/

		
		var path = string.Empty;
		if( !pathAssetName.EndsWith( ".asset" ) )
			pathAssetName += ".asset";
		
		
		if( Application.platform == RuntimePlatform.Android )
		{
			path = Path.Combine( "jar:file://" + Application.dataPath + "!/assets/", pathAssetName );
		
	        WWW loadAsset = new WWW( path );
	        while( !loadAsset.isDone ) { } // maybe make a safety check here
			
			return bytesToVector3List( loadAsset.bytes );
		}
		else if( Application.platform == RuntimePlatform.IPhonePlayer )
		{
			// at runtime on iOS, we load from the dataPath
			path = Path.Combine( Path.Combine( Application.dataPath, "Raw" ), pathAssetName );
		}
		else
		{
			// in the editor we default to looking in the StreamingAssets folder
			path = Path.Combine( Path.Combine( Application.dataPath, "StreamingAssets" ), pathAssetName );
		}
		
#if UNITY_WEBPLAYER || NETFX_CORE || UNITY_WINRT
		// it isnt possible to get here but the compiler needs it to be here anyway
		return null;
#else
		var bytes = File.ReadAllBytes( path );
		return bytesToVector3List( bytes );
#endif
	}
	
	
	/// <summary>
	/// helper to get a node list from an asset created with the visual editor
	/// </summary>
	public static List<Vector3> bytesToVector3List( byte[] bytes )
	{
		var vecs = new List<Vector3>();
		for( var i = 0; i < bytes.Length; i += 12 )
		{
			var newVec = new Vector3( System.BitConverter.ToSingle( bytes, i ), System.BitConverter.ToSingle( bytes, i + 4 ), System.BitConverter.ToSingle( bytes, i + 8 ) );
			vecs.Add( newVec );
		}
		
		return vecs;
	}
	
	
	/// <summary>
	/// gets the last node. used to setup relative tweens
	/// </summary>
	public Vector3 getLastNode()
	{
		return _solver.nodes[_solver.nodes.Count];
	}
	
	
	/// <summary>
	/// responsible for calculating total length, segmentStartLocations and segmentDistances
	/// </summary>
	public void buildPath()
	{
		_solver.buildPath();
	}
	
	
	/// <summary>
	/// directly gets the point for the current spline type with no lookup table to adjust for constant speed
	/// </summary>
	private Vector3 getPoint( float t )
	{
		return _solver.getPoint( t );
	}

	
	/// <summary>
	/// returns the point that corresponds to the given t where t >= 0 and t <= 1 making sure that the
	/// path is traversed at a constant speed.
	/// </summary>
	public Vector3 getPointOnPath( float t )
	{
		// if the path is closed, we will allow t to wrap. if is not we need to clamp t
		if( t < 0 || t > 1 )
		{
			if( isClosed )
			{
				if( t < 0 )
					t += 1;
				else
					t -= 1;
			}
			else
			{
				t = Mathf.Clamp01( t );
			}
		}
		
		return _solver.getPointOnPath( t );
	}

	
	/// <summary>
	/// closes the path adding a new node at the end that is equal to the start node if it isn't already equal
	/// </summary>
	public void closePath()
	{
		// dont let this get closed twice!
		if( isClosed )
			return;
		
		isClosed = true;
		_solver.closePath();
	}
	
	
	/// <summary>
	/// reverses the order of the nodes
	/// </summary>
	public void reverseNodes()
	{
		if( !_isReversed )
		{
			_solver.reverseNodes();
			_isReversed = true;
		}
	}
	
	
	/// <summary>
	/// unreverses the order of the nodes if they were reversed
	/// </summary>
	public void unreverseNodes()
	{
		if( _isReversed )
		{
			_solver.reverseNodes();
			_isReversed = false;
		}
	}
	
	
	public void drawGizmos( float resolution )
	{
		_solver.drawGizmos();
		
		var previousPoint = _solver.getPoint( 0 );
		
		resolution *= _solver.nodes.Count;
		for( var i = 1; i <= resolution; i++ )
		{
			var t = (float)i / resolution;
			var currentPoint = _solver.getPoint( t );
			Gizmos.DrawLine( currentPoint, previousPoint );
			previousPoint = currentPoint;
		}
	}
	
	
	/// <summary>
	/// helper for drawing gizmos in the editor
	/// </summary>
	public static void drawGizmos( Vector3[] path, float resolution = 50 )
	{
		// horribly inefficient but it only runs in the editor
		var spline = new GoSpline( path );
		spline.drawGizmos( resolution );
	}

}
