package org.primaldev.ppm.ui.identity;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;

public class IdentityManagementUI extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	public static final String NAME = "IdentityManagementUI";
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TabSheet tabSheet_1;
	@AutoGenerated
	private GroupManagementTab groupManagementTab_1;
	@AutoGenerated
	private UserManagentTab userManagentTab_1;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public IdentityManagementUI() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// tabSheet_1
		tabSheet_1 = buildTabSheet_1();
		mainLayout.addComponent(tabSheet_1, "top:140.0px;left:0.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private TabSheet buildTabSheet_1() {
		// common part: create layout
		tabSheet_1 = new TabSheet();
		tabSheet_1.setImmediate(true);
		tabSheet_1.setWidth("100.0%");
		tabSheet_1.setHeight("100.0%");
		
		// userManagentTab_1
		userManagentTab_1 = new UserManagentTab();
		userManagentTab_1.setImmediate(false);
		userManagentTab_1.setWidth("-1px");
		userManagentTab_1.setHeight("-1px");
		tabSheet_1.addTab(userManagentTab_1, "Users", null);
		
		// groupManagementTab_1
		groupManagementTab_1 = new GroupManagementTab();
		groupManagementTab_1.setImmediate(false);
		groupManagementTab_1.setWidth("-1px");
		groupManagementTab_1.setHeight("-1px");
		tabSheet_1.addTab(groupManagementTab_1, "Groups", null);
		
		return tabSheet_1;
	}

}