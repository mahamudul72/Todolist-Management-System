package todolist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Mahamudul
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private ListView<String> eventListView;
    @FXML
    private TextField eventName;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label title;
    @FXML
    private Label date;
    @FXML
    private ListView<CheckBox> todoListView;
    @FXML
    private ListView<CheckBox> compleatedListView;
    @FXML
    private Label saveWorning;

    ObservableList<String> eventList = FXCollections.observableArrayList();
    ObservableList<CheckBox> todoList = FXCollections.observableArrayList();
    ObservableList<CheckBox> completedList = FXCollections.observableArrayList();
    @FXML
    private TextField addTodoList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            datePicker.setValue(LocalDate.now());
            addEventList();
            eventListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void addTodoList() throws IOException {
        todoList.clear();

        File file = new File(EVENT_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String todoName = sc.nextLine();
            CheckBox c = new CheckBox(todoName);
            todoList.add(c);

            c.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (c.isSelected()) {
                        completedList.add(c);
                        c.setSelected(true);
                        todoList.remove(c);
                        todoListView.setItems(todoList);
                        compleatedListView.setItems(completedList);

                        //wwrite to completed file...
                        File file = new File("COMPLETED_" + EVENT_NAME);
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        try {

                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                            writer.println(c.getText());
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //remove from event File..
                    File efile = new File(EVENT_NAME);
                    if (!efile.exists()) {
                        try {
                            efile.createNewFile();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(efile)));
                        for (CheckBox ch : todoList) {
                            writer.println(ch.getText());
                        }
                        writer.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    }
                    
                    else{
                        completedList.remove(c);
                        todoList.add(c);
                        todoListView.setItems(todoList);
                        compleatedListView.setItems(completedList);
                        
                        //write to the todo file..
                        File file = new File(EVENT_NAME);
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        try {

                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                            writer.println(c.getText());
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        
                        //remove from completed file..
                        file=new File("COMPLETED_"+EVENT_NAME);
                        try {
                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                            for(CheckBox ch:completedList){
                                writer.println(ch.getText());
                            }
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
          
                }
                

            });

            compleatedListView.setItems(completedList);
        }
        todoListView.setItems(todoList);

    }

    ArrayList<todo> eList = new ArrayList();

    void addEventList() throws IOException {
        File file = new File("eventFile");
        if (!file.exists()) {
            file.createNewFile();
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String s = sc.nextLine();
            String parts[] = s.split("#");
            s = parts[0];
            String d = parts[1];

            todo toDo = new todo(s, LocalDate.parse(d));
            eList.add(toDo);

            eventList.add(s);
        }
        sc.close();
        eventListView.setItems(eventList);
    }

    @FXML
    private void addEventHandle(ActionEvent event) throws IOException {

        if(eventName.getText().equals("")) return ;
        
        String evName = eventName.getText();
        LocalDate d = datePicker.getValue();
        
        todo t=new todo(evName,d);
        
        eList.add(t);
        
        eventList.add(evName);
        eventListView.setItems(eventList);
        
        File file = new File("eventFile");
        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        writer.println(evName + "#" + d.toString());

        writer.close();
        eventName.clear();
    }
      
    @FXML
    private void saveButtonAction(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save");
        alert.setHeaderText("Saved successfully");
        //alert.setContentText("Ooops, you forgot to write save handler code!");

        alert.showAndWait();
    }

    String EVENT_NAME = "";

    @FXML
    private void eventListSelectionAction(MouseEvent event) throws IOException {
        completedList.clear();
        EVENT_NAME = eventListView.getSelectionModel().getSelectedItem().toString();
        title.setText(EVENT_NAME);
        //System.out.println(EVENT_NAME);

        for (todo t : eList) {
            if (t.eventName.equals(EVENT_NAME)) {
                date.setText(t.getDate().toString());
                break;
            }
        }

        //addTodoList();

        //add compleatedList..
        File file = new File("COMPLETED_" + EVENT_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String cTodo = sc.nextLine();
            CheckBox c = new CheckBox(cTodo);

            //ad action...
            c.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!c.isSelected()) {
                        completedList.remove(c);
                        todoList.add(c);
                        todoListView.setItems(todoList);
                        compleatedListView.setItems(completedList);
                        
                        //write to the todo file..
                        File file = new File(EVENT_NAME);
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        try {

                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                            writer.println(c.getText());
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        
                        //remove from completed file..
                        file=new File("COMPLETED_"+EVENT_NAME);
                        try {
                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                            for(CheckBox ch:completedList){
                                writer.println(ch.getText());
                            }
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    else{
                        
                        completedList.add(c);
                        c.setSelected(true);
                        todoList.remove(c);
                        todoListView.setItems(todoList);
                        compleatedListView.setItems(completedList);

                        //wwrite to completed file...
                        File file = new File("COMPLETED_" + EVENT_NAME);
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        try {

                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                            writer.println(c.getText());
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //remove from event File..
                    File efile = new File(EVENT_NAME);
                    if (!efile.exists()) {
                        try {
                            efile.createNewFile();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(efile)));
                        for (CheckBox ch : todoList) {
                            writer.println(ch.getText());
                        }
                        writer.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    }
                }
            });
            completedList.add(c);
            c.setSelected(true);

        }
        compleatedListView.setItems(completedList);

        addTodoList();

    }

    @FXML
    private void todoListSelectionAction(MouseEvent event) {
    }

    @FXML
    private void completedListSelectionAction(MouseEvent event) {
    }

    @FXML
    private void addTodoListHandle(ActionEvent event) throws IOException {

        System.out.println(EVENT_NAME);

        if (!EVENT_NAME.equals("")) {
            String todoName = addTodoList.getText();
            CheckBox c = new CheckBox(todoName);

            todoList.add(c);

            todoListView.setItems(todoList);

            //save in file...
            File file = new File(EVENT_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            writer.println(todoName);
            writer.close();

            //give action to the checkbox...
            c.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (c.isSelected()) {
                        completedList.add(c);
                        c.setSelected(true);
                        todoList.remove(c);
                        todoListView.setItems(todoList);
                        compleatedListView.setItems(completedList);

                        //wwrite to completed file...
                        File file = new File("COMPLETED_" + EVENT_NAME);
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        try {

                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                            writer.println(c.getText());
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //remove from event File..
                    File efile = new File(EVENT_NAME);
                    if (!efile.exists()) {
                        try {
                            efile.createNewFile();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(efile)));
                        for (CheckBox ch : todoList) {
                            writer.println(ch.getText());
                        }
                        writer.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    }
                    
                    else{
                        completedList.remove(c);
                        todoList.add(c);
                        todoListView.setItems(todoList);
                        compleatedListView.setItems(completedList);
                        
                        //write to the todo file..
                        File file = new File(EVENT_NAME);
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        try {

                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                            writer.println(c.getText());
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        
                        //remove from completed file..
                        file=new File("COMPLETED_"+EVENT_NAME);
                        try {
                            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                            for(CheckBox ch:completedList){
                                writer.println(ch.getText());
                            }
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
          
                
                }
            });

        }
        addTodoList.clear();
    }

    @FXML
    private void resetButtonAction(ActionEvent event) {
        for(String s:eventList){
           // System.out.println(s);
            File file=new File(s);
            file.delete();
            File file2=new File("COMPLETED_"+s);
            file2.delete();
            
        }
        eventList.clear();
        todoList.clear();
        completedList.clear();
        eList.clear();
        
        File file=new File("eventFile");
        file.delete();
        
        title.setText("");
        date.setText("");
        
        datePicker.setValue(LocalDate.now());
        
    }

}
