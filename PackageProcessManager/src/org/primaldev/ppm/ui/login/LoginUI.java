package org.primaldev.ppm.ui.login;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class LoginUI extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
  
	
	
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button loginButton;
	@AutoGenerated
	private PasswordField passWord;
	@AutoGenerated
	private TextField userName;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4036914439916582891L;
	Navigator navigator;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public LoginUI(Navigator navigator) {	
		this.navigator = navigator;
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		addClicklisteners();
		
		// TODO add user code here
	}

	@SuppressWarnings("serial")
	private void addClicklisteners() {
		loginButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (getIdentityService().checkPassword(userName.getValue(), passWord.getValue())) {
					getIdentityService().setAuthenticatedUserId(userName.getValue());					
					//fireViewEvent(new UserLoggedInEvent(getView(), userName.getValue()));
					getSession().setAttribute("user", userName.getValue());
					showLoginGood();
					navigator.navigateTo("main");			
				} else {
					
					clearForm();
				    showLoginFailed();
				}
			}
		});
	}
	
	private IdentityService getIdentityService() {
		return ProcessEngines.getDefaultProcessEngine().getIdentityService();
	}
	
	
	public void showLoginFailed() {
		
		Notification.show("Login Status",
                "Login failed. Please try again.",
                Notification.Type.TRAY_NOTIFICATION);
	}
	
	public void showLoginGood() {
		
		Notification.show("Login Successfull",
                "Welcome...",
                Notification.Type.TRAY_NOTIFICATION);
	}


	public void clearForm() {
		userName.setValue("");
		passWord.setValue("");
		userName.focus();
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
		
		// userName
		userName = new TextField();
		userName.setCaption("User Name");
		userName.setImmediate(false);
		userName.setWidth("180px");
		userName.setHeight("-1px");
		mainLayout.addComponent(userName, "top:176.0px;left:260.0px;");
		
		// passWord
		passWord = new PasswordField();
		passWord.setCaption("Password");
		passWord.setImmediate(false);
		passWord.setWidth("180px");
		passWord.setHeight("-1px");
		mainLayout.addComponent(passWord, "top:220.0px;left:260.0px;");
		
		// loginButton
		loginButton = new Button();
		loginButton.setCaption("Login");
		loginButton.setImmediate(true);
		loginButton.setWidth("-1px");
		loginButton.setHeight("-1px");
		mainLayout.addComponent(loginButton, "top:260.0px;left:380.0px;");
		
		return mainLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}