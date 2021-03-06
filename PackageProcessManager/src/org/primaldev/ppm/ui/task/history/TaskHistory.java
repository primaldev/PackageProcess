package org.primaldev.ppm.ui.task.history;

import java.util.Calendar;
import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.primaldev.ppm.util.ProcessUtil;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class TaskHistory extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label label_1;
	@AutoGenerated
	private Button weekButton;
	@AutoGenerated
	private Button monthButon;
	@AutoGenerated
	private Button todayButton;
	@AutoGenerated
	private Button showButton;
	@AutoGenerated
	private Table historyTable;
	@AutoGenerated
	private InlineDateField toDate;
	@AutoGenerated
	private InlineDateField fromDate;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "TaskHistory";
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	private BeanItemContainer<HistoricTaskInstance> dataSource;
	
	
	public TaskHistory() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		initTable();
		updateTaskList();
		addClickListeners();
	}

	private void initTable(){		
		fromDate.setShowISOWeekNumbers(true);
		Calendar cal = getStartOfdayCal();
		toDate.setValue(getEndOfDay().getTime());
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		fromDate.setValue(cal.getTime());
    	
		dataSource = new BeanItemContainer<HistoricTaskInstance>(HistoricTaskInstance.class);
		historyTable.setContainerDataSource(dataSource);
		
	}
	
	private void addClickListeners(){		
		
		showButton.addClickListener( new Button.ClickListener()  {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(Button.ClickEvent event) {		    	
		    	updateTaskList();   	
		    }
		});
		
		todayButton.addClickListener( new Button.ClickListener()  {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(Button.ClickEvent event) {	
				Calendar cal = getStartOfdayCal();
				toDate.setValue(getEndOfDay().getTime());
				fromDate.setValue(cal.getTime());
		    	updateTaskList();   	
		    }
		});
		
		weekButton.addClickListener( new Button.ClickListener()  {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(Button.ClickEvent event) {	
				Calendar cal = getStartOfdayCal();
				toDate.setValue(getEndOfDay().getTime());
				cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
				fromDate.setValue(cal.getTime());
		    	updateTaskList();   	
		    }
		});
		
		monthButon.addClickListener( new Button.ClickListener()  {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(Button.ClickEvent event) {		 
				Calendar cal = getStartOfdayCal();
				toDate.setValue(getEndOfDay().getTime());
				cal.set(Calendar.DAY_OF_MONTH, 1);
				fromDate.setValue(cal.getTime());
		    	updateTaskList();   	
		    }
		});

	}
	
	private Calendar getEndOfDay(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE,59);
		return cal;
	}
	private Calendar getStartOfdayCal(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);		
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		return cal;
	}
	
	protected void setTasks(List<HistoricTaskInstance> tasks) {		
			dataSource.removeAllItems();		
			dataSource.addAll(tasks);				
	}
	
	public void updateTaskList() {
		List<HistoricTaskInstance> tasksToShow = queryForTasksToShow();
		setTasks(tasksToShow);
	}
	
	
	protected List<HistoricTaskInstance> queryForTasksToShow() {		
		 HistoricTaskInstanceQuery query = ProcessUtil.getHistoryService().createHistoricTaskInstanceQuery();
		 query.taskCompletedAfter(fromDate.getValue()).taskCompletedBefore(toDate.getValue()).orderByTaskPriority().desc().orderByTaskDueDate().desc();
		 return query.list();
	}
	
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1158px");
		mainLayout.setHeight("680px");
		
		// top-level component properties
		setWidth("1158px");
		setHeight("680px");
		
		// fromDate
		fromDate = new InlineDateField();
		fromDate.setCaption("From");
		fromDate.setImmediate(false);
		fromDate.setWidth("-1px");
		fromDate.setHeight("-1px");
		mainLayout.addComponent(fromDate, "top:40.0px;left:38.0px;");
		
		// toDate
		toDate = new InlineDateField();
		toDate.setCaption("To");
		toDate.setImmediate(false);
		toDate.setWidth("-1px");
		toDate.setHeight("-1px");
		mainLayout.addComponent(toDate, "top:40.0px;left:318.0px;");
		
		// historyTable
		historyTable = new Table();
		historyTable.setImmediate(false);
		historyTable.setWidth("1090px");
		historyTable.setHeight("423px");
		mainLayout.addComponent(historyTable, "top:237.0px;left:20.0px;");
		
		// showButton
		showButton = new Button();
		showButton.setStyleName("link");
		showButton.setCaption("Selected");
		showButton.setImmediate(true);
		showButton.setWidth("-1px");
		showButton.setHeight("-1px");
		mainLayout.addComponent(showButton, "top:80.0px;left:580.0px;");
		
		// todayButton
		todayButton = new Button();
		todayButton.setStyleName("link");
		todayButton.setCaption("Today");
		todayButton.setImmediate(true);
		todayButton.setWidth("-1px");
		todayButton.setHeight("-1px");
		mainLayout.addComponent(todayButton, "top:180.0px;left:582.0px;");
		
		// monthButon
		monthButon = new Button();
		monthButon.setStyleName("link");
		monthButon.setCaption("This Month");
		monthButon.setImmediate(true);
		monthButon.setWidth("-1px");
		monthButon.setHeight("-1px");
		mainLayout.addComponent(monthButon, "top:110.0px;left:580.0px;");
		
		// weekButton
		weekButton = new Button();
		weekButton.setStyleName("link");
		weekButton.setCaption("This Week");
		weekButton.setImmediate(true);
		weekButton.setWidth("-1px");
		weekButton.setHeight("-1px");
		mainLayout.addComponent(weekButton, "top:145.0px;left:580.0px;");
		
		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Show");
		mainLayout.addComponent(label_1, "top:42.0px;left:580.0px;");
		
		return mainLayout;
	}

}
