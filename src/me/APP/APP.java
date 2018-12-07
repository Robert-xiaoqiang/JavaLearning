package me.APP;

import me.model.*;
import me.view.*;
import me.control.*;
import me.common.*;
import me.common.statemachine.QState;

public final class APP {

	public APP() 
	{
		//statemachine.QState
		QState automata = QState.QSTATE_IDLE;
		
		// serialization model 
		Pages pages = new Pages();
		
		// view
		MainWindow window = new MainWindow("QiniCAD");
		window.bindStateMachine(automata);
		// view bind model & 
		// sink bind notification
		window.bindModel(pages);
		
		// control
		QController controller = new QController();
		controller.bindStateMachine(automata);
		// view bind model
		controller.bindModel(pages);
		
		// event -> controller
		window.bindRectangleLable(controller.getRectangleButtonListener());
		window.bindMainPanel(controller.getMainPanelListener());
	}
	
}
