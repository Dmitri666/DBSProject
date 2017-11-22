package de.hhu.cs.dbs.project.gui;

import com.alexanderthelen.applicationkit.database.Table;
import com.alexanderthelen.applicationkit.gui.TableViewController;
import com.alexanderthelen.applicationkit.gui.ViewController;
import de.hhu.cs.dbs.project.table.account.Account;
import de.hhu.cs.dbs.project.table.account.Favorites;
import de.hhu.cs.dbs.project.table.account.Friends;
import de.hhu.cs.dbs.project.table.blog.*;
import de.hhu.cs.dbs.project.table.comment.Comments;
import de.hhu.cs.dbs.project.table.user.ChiefEditors;
import de.hhu.cs.dbs.project.table.user.Editors;
import de.hhu.cs.dbs.project.table.user.Users;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.util.ArrayList;

public class MasterViewController extends com.alexanderthelen.applicationkit.gui.MasterViewController {
    protected MasterViewController(String name) {
        super(name);
    }

    public static MasterViewController createWithName(String name) throws IOException {
        MasterViewController controller = new MasterViewController(name);
        controller.loadView();
        return controller;
    }

    @Override
    protected ArrayList<TreeItem<ViewController>> getTreeItems() {
        ArrayList<TreeItem<ViewController>> treeItems = new ArrayList<>();
        TreeItem<ViewController> treeItem;
        TreeItem<ViewController> subTreeItem;
        TableViewController tableViewController;
        Table table;

        table = new Account();
        table.setTitle("Account");
        try {
            tableViewController = TableViewController.createWithNameAndTable("account", table);
            tableViewController.setTitle("Account");
        } catch (IOException e) {
            tableViewController = null;
        }
        treeItem = new TreeItem<>(tableViewController);
        treeItem.setExpanded(true);
        treeItems.add(treeItem);

        table = new Favorites();
        table.setTitle("Favoriten");
        try {
            tableViewController = TableViewController.createWithNameAndTable("favorites", table);
            tableViewController.setTitle("Favoriten");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Friends();
        table.setTitle("Freunde");
        try {
            tableViewController = TableViewController.createWithNameAndTable("friends", table);
            tableViewController.setTitle("Freunde");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Blogentries();
        table.setTitle("Blogeintraege");
        try {
            tableViewController = TableViewController.createWithNameAndTable("blogentries", table);
            tableViewController.setTitle("Blogeintraege");
        } catch (IOException e) {
            tableViewController = null;
        }
        treeItem = new TreeItem<>(tableViewController);
        treeItem.setExpanded(true);
        treeItems.add(treeItem);

        table = new Top5();
        table.setTitle("Top 5");
        try {
            tableViewController = TableViewController.createWithNameAndTable("top5", table);
            tableViewController.setTitle("Top 5");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new de.hhu.cs.dbs.project.table.blog.Favorites();
        table.setTitle("Favoriten");
        try {
            tableViewController = TableViewController.createWithNameAndTable("favorites", table);
            tableViewController.setTitle("Favoriten");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Albums();
        table.setTitle("Alben");
        try {
            tableViewController = TableViewController.createWithNameAndTable("albums", table);
            tableViewController.setTitle("Alben");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Images();
        table.setTitle("Bilder");
        try {
            tableViewController = TableViewController.createWithNameAndTable("images", table);
            tableViewController.setTitle("Bilder");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Reviews();
        table.setTitle("Produktrezensionen");
        try {
            tableViewController = TableViewController.createWithNameAndTable("reviews", table);
            tableViewController.setTitle("Produktrezensionen");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Tags();
        table.setTitle("Schlagworte");
        try {
            tableViewController = TableViewController.createWithNameAndTable("tags", table);
            tableViewController.setTitle("Schlagworte");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Videoblogs();
        table.setTitle("Videoblogs");
        try {
            tableViewController = TableViewController.createWithNameAndTable("videoblogs", table);
            tableViewController.setTitle("Videoblogs");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Votings();
        table.setTitle("Votings");
        try {
            tableViewController = TableViewController.createWithNameAndTable("votings", table);
            tableViewController.setTitle("Votings");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Comments();
        table.setTitle("Kommentare");
        try {
            tableViewController = TableViewController.createWithNameAndTable("comments", table);
            tableViewController.setTitle("Kommentare");
        } catch (IOException e) {
            tableViewController = null;
        }
        treeItem = new TreeItem<>(tableViewController);
        treeItem.setExpanded(true);
        treeItems.add(treeItem);

        table = new de.hhu.cs.dbs.project.table.comment.Votings();
        table.setTitle("Votings");
        try {
            tableViewController = TableViewController.createWithNameAndTable("votings", table);
            tableViewController.setTitle("Votings");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new Users();
        table.setTitle("Nutzer");
        try {
            tableViewController = TableViewController.createWithNameAndTable("users", table);
            tableViewController.setTitle("Nutzer");
        } catch (IOException e) {
            tableViewController = null;
        }
        treeItem = new TreeItem<>(tableViewController);
        treeItem.setExpanded(true);
        treeItems.add(treeItem);

        table = new Editors();
        table.setTitle("Redakteure");
        try {
            tableViewController = TableViewController.createWithNameAndTable("editors", table);
            tableViewController.setTitle("Redakteure");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        table = new ChiefEditors();
        table.setTitle("Chefredakteure");
        try {
            tableViewController = TableViewController.createWithNameAndTable("chiefEditors", table);
            tableViewController.setTitle("Chefredakteure");
        } catch (IOException e) {
            tableViewController = null;
        }
        subTreeItem = new TreeItem<>(tableViewController);
        treeItem.getChildren().add(subTreeItem);

        return treeItems;
    }
}
