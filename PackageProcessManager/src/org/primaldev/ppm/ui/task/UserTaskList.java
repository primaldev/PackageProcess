package org.primaldev.ppm.ui.task;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.primaldev.ppm.PackageprocessmanagerUI;
import org.primaldev.ppm.event.SwitchView_Event;
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


public class UserTaskList extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Table userTaskTable;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	
	private static Logger log = Logger.getLogger(UserTaskList.class.getName());
	private BeanItemContainer<Task> dataSource;
	
	public UserTaskList() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		initTable();
		updateTaskList();
		// TODO add user code here
	}
	
	private void initTable(){		
		
		dataSource = new BeanItemContainer<Task>(Task.class);
		userTaskTable.setContainerDataSource(dataSource);
		
		userTaskTable.setSizeFull();
		userTaskTable.addGeneratedColumn("name", createNameColumnGenerator());
		userTaskTable.addGeneratedColumn("product", createProductNameColumnGenerator());
		userTaskTable.setVisibleColumns(getVisibleColumns());
		userTaskTable.setColumnHeader("name", "task name");
		
	}

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
	
	
	@SuppressWarnings("serial")
	private ColumnGenerator createProductNameColumnGenerator() {
		return new ColumnGenerator() {
			@Override
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Task task = (Task) itemId;
				Label label = createTextField(task);				
				return label;
			}
			
		};
	}
	
	private Label createTextField(Task task){
		return new Label(ProcessUtil.getProductName(task));		
	}
	
	
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

		if (ProcessUtil.taskHasForm(task)) {
			Button openFormButton = new Button("Open Form");
			openFormButton.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					openFormForTask(task);
					popup.setPopupVisible(false);
				}
			});
			openFormButton.addStyleName(Reindeer.BUTTON_SMALL);
			layout.addComponent(openFormButton);
		} else {
			Button completeButton = new Button("Complete");
			completeButton.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					completeTask(task);
					popup.setPopupVisible(false);
				}
			});
			completeButton.addStyleName(Reindeer.BUTTON_SMALL);
			layout.addComponent(completeButton);
		}

		Button delegateToOtherUserButton = new Button(
				"Delegate to other user...");
		delegateToOtherUserButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				delegateToOtherUser(task);
				popup.setPopupVisible(false);
			}
		});
		delegateToOtherUserButton.addStyleName(Reindeer.BUTTON_SMALL);
		layout.addComponent(delegateToOtherUserButton);

		return popup;
	}

	

	protected List<Task> queryForTasksToShow() {
		String currentUser = ProcessUtil.getIdOfCurrentUser();
		TaskQuery query = ProcessUtil.getTaskService().createTaskQuery();
		query.taskAssignee(currentUser).orderByTaskPriority().desc()
				.orderByDueDate().desc();
		return query.list();
	}

	public void completeTask(Task task) {
		log.log(Level.INFO, "Completing task {1}", task.getId());
		try {
			ProcessUtil.getTaskService().complete(task.getId());
			updateTaskList();
			showTaskCompletedSuccess(task);
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Could not complete task", e);
			showTaskCompletedFailure(task);
		}
	}

	public void setTasks(List<Task> tasks) {
		dataSource.removeAllItems();
		dataSource.addAll(tasks);
	}

	public void openFormForTask(Task task) {
		String formKey = ProcessUtil.getFormKey(task);
		if (formKey != null) {
			Blackboard bb = ((PackageprocessmanagerUI)getUI()).getBlackboard();
	    	bb.fire(new SwitchView_Event("UserFormViewUI",task));	
		}
	}

	public void updateTaskList() {
		List<Task> tasksToShow = queryForTasksToShow();
		setTasks(tasksToShow);
	}



	protected String[] getVisibleColumns() {
		return new String[] {"product", "id", "name", "description", "priority",
				"dueDate", "createTime", "assignee" };
	}


	public void showTaskCompletedSuccess(Task task) {
		Notification.show(
				String.format("%s completed successfully", task.getName()),
				com.vaadin.ui.Notification.Type.HUMANIZED_MESSAGE);
	}


	public void showTaskCompletedFailure(Task task) {
		Notification.show(
						String.format(
								"Could not complete %s. Please check the logs for more information.",
								task.getName()),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
	}
	
	public void delegateToOtherUser(Task task) {
		//TODO Implement method
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
		
		// userTaskTable
		userTaskTable = new Table();
		userTaskTable.setImmediate(true);
		userTaskTable.setWidth("100.0%");
		userTaskTable.setHeight("100.0%");
		mainLayout.addComponent(userTaskTable,
				"top:0.0px;right:0.0px;bottom:0.0px;left:0.0px;");
		
		return mainLayout;
	}

}
