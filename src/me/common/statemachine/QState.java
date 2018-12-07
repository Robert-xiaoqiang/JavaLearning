package me.common.statemachine;

public enum QState {
	QSTATE_IDLE {
		@Override
		public QState leftButton() 
		{
			return QSTATE_IDLE;
		}

		@Override
		public QState rightButton() 
		{
			return QSTATE_IDLE;
		}
		
		@Override
		public QState rectangleButton()
		{
			return QSTATE_RECTANGLE;
		}
	},
	
	QSTATE_RECTANGLE {
		@Override
		public QState leftButton()
		{
			return QSTATE_RECTANGLE;
		}
		
		@Override
		public QState rightButton()
		{
			return QSTATE_RECTANGLE;
		}
		
		@Override
		public QState rectangleButton()
		{
			return QSTATE_IDLE;
		}
	};
	
	// left button of those not hitting
	public abstract QState leftButton();
	public abstract QState rightButton();
	public abstract QState rectangleButton();
	
}
