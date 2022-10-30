package com.example.cda;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Text;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class DashController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label eventnameText, eventLocation, eventTime, desContent, creName, creRole, logLabel;
    @FXML
    private AnchorPane blankPane, detailsPane, editPane;
    @FXML
    private Button addBtn, submitBtn, showButton, editBtn, deletBtn;

    @FXML
    private TextField nameField, locationField, timeField;

    @FXML
    private TextArea descField;
    @FXML
    private VBox EventListVBox;

    String role;
    String name;
    HashMap<String, String> eventMap = new HashMap<>();
    @FXML
    protected void initialize() throws FileNotFoundException {
        File newFile = new File("user.txt");
        try {
            Scanner userScan = new Scanner(newFile);
             name = userScan.nextLine();
             role = userScan.nextLine();
            logLabel.setText("You are logged in as\n" + name + " (" + role + ")");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void onShowBtnClick(ActionEvent e) throws FileNotFoundException {

        loadEvent();
    }

    @FXML
    protected void onAddBtnClick(ActionEvent e) throws IOException {
        editPane.setVisible(true);
        detailsPane.setVisible(false);

    }

    @FXML
    protected void onSubmitBtnClick(ActionEvent e) throws IOException {
        //addEvent(nameField.getText(), locationField.getText(), timeField.getText(), descField.getText());
        String[] event = new String[6];
        event[0] = (nameField.getText());
        event[1] = (locationField.getText());
        event[2] = (timeField.getText());
        event[3] = (descField.getText());
        File newFile = new File("user.txt");
        Scanner userScan = new Scanner(newFile);
        event[4] = userScan.nextLine();
        event[5] = userScan.nextLine();

        try {
            FileWriter file = new FileWriter("event.txt", true);

            for (int i = 0; i < 6; i++) {
                file.write(event[i] + ", ");
            }
            file.write("\n");
            file.flush();
            file.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Alert editAlert = new Alert(Alert.AlertType.CONFIRMATION);
        editAlert.setContentText("Event saved");
        editAlert.show();

        loadEvent();

        editPane.setVisible(false);
        detailsPane.setVisible(false);
        nameField.clear();
        locationField.clear();
        timeField.clear();
        descField.clear();
    }

    protected void addEvent(String name, String location, String time, String desc) {
        Button newEventButton = new Button(name);
        EventListVBox.getChildren().add(newEventButton);
        newEventButton.setPrefSize(233, 50);
        newEventButton.setBackground(Background.fill(Color.DARKBLUE));
        newEventButton.setTextFill(Color.WHITE);
        newEventButton.setFont(Font.font("VERDANA", FontWeight.BOLD, FontPosture.ITALIC, 18));

        newEventButton.setOnAction(e -> {
            detailsPane.setVisible(true);
            editPane.setVisible(false);
            eventnameText.setText(name);

        });
    }
    protected void loadEvent() throws FileNotFoundException {
        File file = new File("event.txt");
        try{
        BufferedReader br = new BufferedReader(new FileReader("event.txt"));
        if (br.readLine() == null) {
            EventListVBox.getChildren().clear();
            return;
        }}
        catch (Exception excp) {

        }

        EventListVBox.getChildren().clear();



        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String s = scan.nextLine();

            int i = 0;
            String eventName = "";
            while (s.charAt(i) != ',') {
                char c = s.charAt(i);
                eventName += c;
                i++;
            }
            eventMap.put(eventName, s);
            Button bt = new Button(eventName);
            bt.setPrefSize(233, 50);
            bt.setBackground(Background.fill(Color.DARKBLUE));
            bt.setTextFill(Color.WHITE);
            bt.setFont(Font.font("VERDANA", FontWeight.BOLD, FontPosture.ITALIC, 18));
            bt.setOnAction(ex -> {
                detailsPane.setVisible(true);
                editPane.setVisible(false);
                String content = eventMap.get(bt.getText());
                Scanner sc = new Scanner(content);
                sc.useDelimiter(",");
                String[] cc = new String[6];
                int j = 0;
                while (j < 6) {
                    cc[j] = sc.next();
                    j++;
                }
                eventnameText.setText(cc[0]);
                eventLocation.setText(cc[1]);
                eventTime.setText(cc[2]);
                desContent.setText(cc[3]);
                creName.setText(cc[4]);
                creRole.setText(cc[5]);

                if (creName.getText().trim().equals(name) || role.equals("Admin")) {
                    editBtn.setVisible(true);
                    deletBtn.setVisible(true);
                } else {
                    editBtn.setVisible(false);
                    deletBtn.setVisible(false);
                }
            });

            EventListVBox.getChildren().add(bt);
        }

    }
    @FXML
    protected void onEditClick(ActionEvent eCC) throws IOException {

        String editedName = eventnameText.getText();
        removeLine(editedName);
        editPane.setVisible(true);
        detailsPane.setVisible(false);
        nameField.setText(eventnameText.getText());
        locationField.setText(eventLocation.getText());
        timeField.setText(eventTime.getText());
       descField.setText(desContent.getText());


    }
    @FXML
    protected void onDeletClick(ActionEvent ac) {
        String editedName = eventnameText.getText();
        removeLine(editedName);
        try {

            detailsPane.setVisible(false);
            loadEvent();
        } catch (Exception exccc) {

        }

    }
    public void removeLine(String eName){
        String value = eventMap.get(eName);
        Scanner removeScan = new Scanner("event.txt");
        String s = "";
        int count = 1;
        while (removeScan.hasNextLine() && value != s) {
             s = removeScan.nextLine();
             count++;
        }
        count--;
        File tempFile = new File("temp.txt");
        File ogFile = new File("event.txt");
        int line = 0;
        String currentLine = "";
        try {
            FileWriter fw = new FileWriter("temp.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader("event.txt");
            BufferedReader br  = new BufferedReader(fr);
             while ((currentLine = br.readLine()) != null ) {
                 line ++;
                 if (!value.equals(currentLine)) {
                     pw.println(currentLine);
                 }
             }
             pw.flush();
             pw.close();
             fr.close();
             br.close();
             bw.close();
             fw.close();

             ogFile.delete();
             File dump = new File("event.txt");
             tempFile.renameTo(dump);

        } catch(IOException exception) {

        }

    }
}