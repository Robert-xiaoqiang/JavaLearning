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
	};
	
	// left button of those not hitting
	public abstract QState leftButton();
	public abstract QState rightButton();
	public abstract QState rectangleButton();
	
}
