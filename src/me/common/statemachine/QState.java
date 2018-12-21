package me.common.statemachine;

public enum QState {
	QSTATE_IDLE {
		@Override
		public QState leftButton() 
		{
			return QState.QSTATE_IDLE;
		}

		@Override
		public QState rightButton() 
		{
			return QState.QSTATE_IDLE;
		}
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_RECTANGLE;
		}

		@Override
		public QState textButton() 
		{
			return QState.QSTATE_TEXT;
		}

		@Override
		public QState ellipseButton() 
		{
			return QState.QSTATE_ELLIPSE;
		}

		@Override
		public QState lineButton() 
		{
			return QSTATE_LINE;
		}

		@Override
		public QState polylineButton() 
		{
			return QState.QSTATE_POLYLINE;
		}

		@Override
		public QState polygonButton() 
		{
			return QState.QSTATE_POLYGON;
		}
	},
	
	
	
	
	QSTATE_RECTANGLE {
		@Override
		public QState leftButton()
		{
			return QState.QSTATE_RENDER_RECTANGLE;
		}
		
		@Override
		public QState rightButton()
		{
			return QState.QSTATE_RECTANGLE;
		}
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_IDLE;
		}

		@Override
		public QState textButton() 
		{
			return QState.QSTATE_TEXT;
		}
		
		@Override
		public QState ellipseButton() 
		{
			return QState.QSTATE_ELLIPSE;
		}

		@Override
		public QState lineButton() 
		{
			return QSTATE_LINE;
		}

		@Override
		public QState polylineButton() 
		{
			return QState.QSTATE_POLYLINE;
		}

		@Override
		public QState polygonButton() 
		{
			return QState.QSTATE_POLYGON;
		}
	},
	
	QSTATE_RENDER_RECTANGLE {
		@Override
		public QState leftButton()
		{
			return QState.QSTATE_RECTANGLE;
		}
		
		@Override
		public QState rightButton()
		{
			return QState.QSTATE_RECTANGLE;
		}
		
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_IDLE;
		}
		
		// may not happen
		@Override
		public QState textButton() 
		{
			return QState.QSTATE_RENDER_RECTANGLE;
		}

		@Override
		public QState ellipseButton() {
			return QState.QSTATE_RENDER_RECTANGLE;
		}

		@Override
		public QState lineButton() {
			return QState.QSTATE_RENDER_RECTANGLE;
		}

		@Override
		public QState polylineButton() {
			return QState.QSTATE_RENDER_RECTANGLE;
		}

		@Override
		public QState polygonButton() {
			return QState.QSTATE_RENDER_RECTANGLE;
		}
		
	},
		
	QSTATE_ELLIPSE {
		@Override
		public QState leftButton()
		{
			return QState.QSTATE_RENDER_ELLIPSE;
		}
		
		@Override
		public QState rightButton()
		{
			return QState.QSTATE_ELLIPSE;
		}
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_RECTANGLE;
		}

		@Override
		public QState textButton() 
		{
			return QState.QSTATE_TEXT;
		}
		
		@Override
		public QState ellipseButton() 
		{
			return QState.QSTATE_IDLE;
		}

		@Override
		public QState lineButton() 
		{
			return QSTATE_LINE;
		}

		@Override
		public QState polylineButton() 
		{
			return QState.QSTATE_POLYLINE;
		}

		@Override
		public QState polygonButton() 
		{
			return QState.QSTATE_POLYGON;
		}
	},
	
	QSTATE_RENDER_ELLIPSE {
		@Override
		public QState leftButton()
		{
			return QState.QSTATE_ELLIPSE;
		}
		
		@Override
		public QState rightButton()
		{
			return QState.QSTATE_ELLIPSE;
		}
		
		
		@Override
		public QState ellipseButton() {
			return QState.QSTATE_IDLE;
		}

		// may not happen
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_RENDER_ELLIPSE;
		}
		
		@Override
		public QState textButton() 
		{
			return QState.QSTATE_RENDER_RECTANGLE;
		}

		@Override
		public QState lineButton() {
			return QState.QSTATE_RENDER_RECTANGLE;
		}

		@Override
		public QState polylineButton() {
			return QState.QSTATE_RENDER_RECTANGLE;
		}

		@Override
		public QState polygonButton() {
			return QState.QSTATE_RENDER_RECTANGLE;
		}
	},
	
	QSTATE_TEXT {

		@Override
		public QState leftButton() 
		{
			return QState.QSTATE_TEXT;
		}

		@Override
		public QState rightButton() 
		{
			return QState.QSTATE_TEXT;
		}

		@Override
		public QState rectangleButton() 
		{
			return QState.QSTATE_RECTANGLE;
		}

		@Override
		public QState textButton() 
		{
			return QState.QSTATE_IDLE;
		}

		@Override
		public QState ellipseButton() {
			return QState.QSTATE_ELLIPSE;
		}

		@Override
		public QState lineButton() {
			return QState.QSTATE_LINE;
		}

		@Override
		public QState polylineButton() {
			return QState.QSTATE_POLYLINE;
		}

		@Override
		public QState polygonButton() {
			return QState.QSTATE_POLYGON;
		}

	},
	
	QSTATE_LINE {
		@Override
		public QState leftButton()
		{
			return QState.QSTATE_RENDER_LINE;
		}
		
		@Override
		public QState rightButton()
		{
			return QState.QSTATE_LINE;
		}
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_RECTANGLE;
		}

		@Override
		public QState textButton() 
		{
			return QState.QSTATE_TEXT;
		}
		
		@Override
		public QState ellipseButton() 
		{
			return QState.QSTATE_ELLIPSE;
		}

		@Override
		public QState lineButton() 
		{
			return QSTATE_IDLE;
		}

		@Override
		public QState polylineButton() 
		{
			return QState.QSTATE_POLYLINE;
		}

		@Override
		public QState polygonButton() 
		{
			return QState.QSTATE_POLYGON;
		}

	},
	
	QSTATE_RENDER_LINE {
		@Override
		public QState leftButton()
		{
			return QState.QSTATE_LINE;
		}
		
		@Override
		public QState rightButton()
		{
			return QState.QSTATE_LINE;
		}
		
		@Override
		public QState polylineButton() {
			return QState.QSTATE_IDLE;
		}
		
		// may not happen
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_RENDER_LINE;
		}
		
		@Override
		public QState ellipseButton() {
			return QState.QSTATE_RENDER_LINE;
		}
		
		@Override
		public QState textButton() 
		{
			return QState.QSTATE_RENDER_LINE;
		}

		@Override
		public QState lineButton() {
			return QState.QSTATE_RENDER_LINE;
		}

		@Override
		public QState polygonButton() {
			return QState.QSTATE_RENDER_LINE;
		}
	},
	
	QSTATE_POLYLINE {
		@Override
		public QState leftButton()
		{
			return QState.QSTATE_RENDER_POLYLINE;
		}
		
		@Override
		public QState rightButton()
		{
			return QState.QSTATE_POLYLINE;
		}
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_RECTANGLE;
		}

		@Override
		public QState textButton() 
		{
			return QState.QSTATE_TEXT;
		}
		
		@Override
		public QState ellipseButton() 
		{
			return QState.QSTATE_ELLIPSE;
		}

		@Override
		public QState lineButton() 
		{
			return QSTATE_LINE;
		}

		@Override
		public QState polylineButton() 
		{
			return QState.QSTATE_IDLE;
		}

		@Override
		public QState polygonButton() 
		{
			return QState.QSTATE_POLYGON;
		}		
	},
	
	QSTATE_RENDER_POLYLINE {

		@Override
		public QState leftButton() 
		{
			return QState.QSTATE_RENDER_POLYLINE;
		}

		@Override
		public QState rightButton() 
		{
			return QState.QSTATE_POLYLINE;
		}

		@Override
		public QState polylineButton() {
			return QState.QSTATE_IDLE;
		}
		
		// may not occur
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_RENDER_POLYLINE;
		}

		@Override
		public QState ellipseButton() {
			return QState.QSTATE_RENDER_POLYLINE;
		}

		@Override
		public QState textButton() {
			return QState.QSTATE_RENDER_POLYLINE;
		}

		@Override
		public QState lineButton() {
			return QState.QSTATE_RENDER_POLYLINE;
		}

		@Override
		public QState polygonButton() {
			return QState.QSTATE_RENDER_POLYLINE;
		}
		
	},
	
	QSTATE_POLYGON {
		@Override
		public QState leftButton()
		{
			return QState.QSTATE_RENDER_POLYGON;
		}
		
		@Override
		public QState rightButton()
		{
			return QState.QSTATE_POLYGON;
		}
		
		@Override
		public QState rectangleButton()
		{
			return QState.QSTATE_RECTANGLE;
		}

		@Override
		public QState textButton() 
		{
			return QState.QSTATE_TEXT;
		}
		
		@Override
		public QState ellipseButton() 
		{
			return QState.QSTATE_ELLIPSE;
		}

		@Override
		public QState lineButton() 
		{
			return QSTATE_LINE;
		}

		@Override
		public QState polylineButton() 
		{
			return QState.QSTATE_POLYLINE;
		}

		@Override
		public QState polygonButton() 
		{
			return QState.QSTATE_IDLE;
		}		
	},
	
	QSTATE_RENDER_POLYGON {

		@Override
		public QState leftButton() 
		{
			return QState.QSTATE_RENDER_POLYGON;
		}

		@Override
		public QState rightButton() 
		{			
			return QState.QSTATE_POLYGON;
		}

		@Override
		public QState polygonButton() 
		{
			return QState.QSTATE_IDLE;
		}
		
		// may not occur
		@Override
		public QState rectangleButton() 
		{
			return QState.QSTATE_RENDER_POLYGON;
		}

		@Override
		public QState ellipseButton() 
		{
			return QState.QSTATE_RENDER_POLYGON;
		}

		@Override
		public QState textButton() 
		{
			return QState.QSTATE_RENDER_POLYGON;
		}

		@Override
		public QState lineButton() 
		{
			return QState.QSTATE_RENDER_POLYGON;
		}

		@Override
		public QState polylineButton() 
		{
			return QState.QSTATE_RENDER_POLYGON;
		}
		
	};
	
	// left button of those not hitting
	public abstract QState leftButton();
	public abstract QState rightButton();
	public abstract QState rectangleButton();
	public abstract QState ellipseButton();
	public abstract QState textButton();
	public abstract QState lineButton();
	public abstract QState polylineButton();
	public abstract QState polygonButton();
	
}
