package org.primaldev.ppm.ui.task;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.primaldev.ppm.PackageprocessmanagerUI;
import org.primaldev.ppm.event.RefreshLabels_Event;
import org.primaldev.ppm.util.ProcessUtil;

import com.github.wolfie.blackboard.Blackboard;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class UnassignedTasksUI extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Table unAssignedTasksTable;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	
	private BeanItemContainer<Task> dataSource;
	private static Logger log = Logger.getLogger(UnassignedTasksUI.class.getName());
	
	public UnassignedTasksUI() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		initTable();
		updateTaskList();
	}

	private void initTable(){
		
		dataSource = new BeanItemContainer<Task>(Task.class);
		unAssignedTasksTable.setContainerDataSource(dataSource);
		unAssignedTasksTable.setVisibleColumns(getVisibleColumns());
		unAssignedTasksTable.addGeneratedColumn("name", createNameColumnGenerator());
		
	}
	
	
	protected String[] getVisibleColumns() {
		return new String[] { "id", "name", "description", "priority",
				"dueDate", "createTime" };
	}

	
	
	@SuppressWarnings("serial")
	private ColumnGenerator createNameColumnGenerator() {
		return new ColumnGenerator() {

			@Override
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Task task = (Task) itemId;
				PopupView popupView = createTaskPopup(task);
				return popupView;
			}
		};
	}

	
	public void setTasks(List<Task> tasks) {
		dataSource.removeAllItems();
		dataSource.addAll(tasks);
	}
	
	@SuppressWarnings("serial")	
	protected PopupView createTaskPopup(final Task task) {
		final VerticalLayout layout = new VerticalLayout();
		final PopupView popup = new PopupView(task.getName(), layout);

		layout.setSizeUndefined();
		layout.setMargin(true);
		layout.setSpacing(true);
		Label header = new Label(String.format(
				"What would you like to do with <b>%s</b>?", task.getName()));
		header.setContentMode(ContentMode.HTML);
		layout.addComponent(header);

		Button assignToMeButton = new Button("Assign to me");
		assignToMeButton.addStyleName(Reindeer.BUTTON_SMALL);
		assignToMeButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				assignTaskToCurrentUser(task);
				popup.setPopupVisible(false);
			}
		});
		layout.addComponent(assignToMeButton);

		Button assignToOtherButton = new Button("Assign to other user...");
		assignToOtherButton.addStyleName(Reindeer.BUTTON_SMALL);
		// TODO Add listener
		layout.addComponent(assignToOtherButton);
		return popup;
	}

	public void assignTaskToCurrentUser(Task task) {
		String currentUserId = ProcessUtil.getIdOfCurrentUser();

		log.log(Level.INFO, "Assigning task {1} to user {2}", new Object[] {
				task.getId(), currentUserId });
		try {
			ProcessUtil.getTaskService().claim(task.getId(), currentUserId);
			updateTaskList();
	        Blackboard bb = ((PackageprocessmanagerUI)getUI()).getBlackboard();
	        bb.fire(new RefreshLabels_Event());  
			showTaskAssignmentSuccess(task);
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Could not assign task to user", e);
			showTaskAssignmentFailure(task);
		}
	}
	
	
	protected void updateTaskList() {
		List<Task> tasksToShow = queryForTasksToShow();
		setTasks(tasksToShow);
	}
	
	protected List<Task> queryForTasksToShow() {
		String currentUser = ProcessUtil.getIdOfCurrentUser();
		TaskQuery query = ProcessUtil.getTaskService().createTaskQuery();
		query.taskUnassigned().taskCandidateUser(currentUser)
				.orderByTaskPriority().desc().orderByDueDate().desc();
		return query.list();
	}


	public void assignTaskToOtherUser(Task task) {
		// TODO Implement me!
	}

	
	
	public void showTaskAssignmentSuccess(Task task) {
		Notification.show(String.format("%s assigned successfully", task.getName()),
				Notification.Type.HUMANIZED_MESSAGE);
	}

	
	public void showTaskAssignmentFailure(Task task) {
		Notification.show(
						String.format(
								"Could not assign %s. Please check the logs for more information.",
								task.getName()),
						Notification.Type.ERROR_MESSAGE);
	}
	
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("997px");
		mainLayout.setHeight("340px");
		
		// top-level component properties
		setWidth("997px");
		setHeight("340px");
		
		// unAssignedTasksTable
		unAssignedTasksTable = new Table();
		unAssignedTasksTable.setImmediate(true);
		unAssignedTasksTable.setWidth("890px");
		unAssignedTasksTable.setHeight("300px");
		mainLayout.addComponent(unAssignedTasksTable, "top:0.0px;left:0.0px;");
		
		return mainLayout;
	}

}
