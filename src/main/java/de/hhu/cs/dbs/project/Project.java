package de.hhu.cs.dbs.project;

import com.alexanderthelen.applicationkit.database.Connection;
import com.alexanderthelen.applicationkit.gui.MasterDetailViewController;
import com.alexanderthelen.applicationkit.gui.WindowController;
import de.hhu.cs.dbs.project.gui.AuthenticationViewController;
import de.hhu.cs.dbs.project.gui.LoginViewController;
import de.hhu.cs.dbs.project.gui.MasterViewController;
import de.hhu.cs.dbs.project.gui.RegistrationViewController;

import java.nio.file.Paths;

public class Project extends com.alexanderthelen.applicationkit.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start() throws Exception {
        setConnection(new Connection("jdbc:sqlite:" + Paths.get("project.db")));
        //setConnection(new Connection(Paths.get("project.db"));

        WindowController mainWindowController = WindowController.createWithName("window");
        mainWindowController.setTitle("Projekt");

        AuthenticationViewController authenticationViewController = AuthenticationViewController
                .createWithName("authentication");
        authenticationViewController.setTitle("Authentifizierung");

        LoginViewController loginViewController = LoginViewController.createWithName("login");
        loginViewController.setTitle("Anmeldung");
        authenticationViewController.setLoginViewController(loginViewController);

        RegistrationViewController registrationViewController = RegistrationViewController
                .createWithName("registration");
        registrationViewController.setTitle("Registrierung");
        authenticationViewController.setRegistrationViewController(registrationViewController);

        MasterDetailViewController mainViewController = MasterDetailViewController.createWithName("main");
        mainViewController.setTitle("Projekt");
        mainViewController.setMasterViewController(MasterViewController.createWithName("master"));
        authenticationViewController.setMainViewController(mainViewController);

        mainWindowController.setViewController(authenticationViewController);
        setWindowController(mainWindowController);
        show();
    }
}