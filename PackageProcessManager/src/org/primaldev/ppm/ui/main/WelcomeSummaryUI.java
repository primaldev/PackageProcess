package org.primaldev.ppm.ui.main;

import org.primaldev.ppm.ui.main.MainUI;
import org.primaldev.ppm.PackageprocessmanagerUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;

public class WelcomeSummaryUI extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private GridLayout gridLayout_1;
	@AutoGenerated
	private Button identityManagementButton;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public WelcomeSummaryUI() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		addClickListeners();
	}

	
	
	@SuppressWarnings("serial")
	private void addClickListeners(){
		identityManagementButton.addClickListener( new Button.ClickListener()  {
		    public void buttonClick(Button.ClickEvent event) {
		    	((PackageprocessmanagerUI)getUI()).setMainUIToIndentManagement();
		    	
		    }
		});
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
		
		// gridLayout_1
		gridLayout_1 = buildGridLayout_1();
		mainLayout.addComponent(gridLayout_1, "top:0.0px;left:0.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private GridLayout buildGridLayout_1() {
		// common part: create layout
		gridLayout_1 = new GridLayout();
		gridLayout_1.setImmediate(false);
		gridLayout_1.setWidth("740px");
		gridLayout_1.setHeight("480px");
		gridLayout_1.setMargin(false);
		
		// identityManagementButton
		identityManagementButton = new Button();
		identityManagementButton.setStyleName("link");
		identityManagementButton.setCaption("Identity Management");
		identityManagementButton.setImmediate(true);
		identityManagementButton.setWidth("-1px");
		identityManagementButton.setHeight("-1px");
		gridLayout_1.addComponent(identityManagementButton, 0, 0);
		gridLayout_1.setComponentAlignment(identityManagementButton,
				new Alignment(48));
		
		return gridLayout_1;
	}

}
