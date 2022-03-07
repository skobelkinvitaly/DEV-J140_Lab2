package PersonList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {

    ArrayList<Person> people;

    @Override
    public void start(Stage primaryStage) {
//        TableView <Person> tableView = new TableView<>();
//
//        TableColumn<Person, String> firstNameCol = new TableColumn<>("First name");
//        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//        tableView.getColumns().add(firstNameCol);
//
//        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last name");
//        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        tableView.getColumns().add(lastNameCol);
//
//        TableColumn<Person, Integer> ageCol = new TableColumn<>("Age");
//        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
//        tableView.getColumns().add(ageCol);
//
//        tableView.setItems(getPersonList ());

//        StackPane root = new StackPane();
//        root.getChildren().add(tableView);

        HBox root = new HBox();
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setSpacing(20);

        //TextArea
        TextArea textArea = new TextArea();
        root.getChildren().add(textArea);

        //TextFields
        TextField name = new TextField();
        TextField lastName = new TextField();
        TextField age = new TextField();


        //VBox
        VBox vBox = new VBox();
        root.getChildren().add(vBox);
        vBox.setSpacing(20);


        //Buttons
        Button buttonAdd = new Button();
        buttonAdd.setText("Add");
        buttonAdd.setMinSize(130, 60);

        Button buttonSave = new Button();
        buttonSave.setText("Save");
        buttonSave.setMinSize(130, 60);

        Button buttonClear = new Button();
        buttonClear.setText("Clear");
        buttonClear.setMinSize(130, 60);

        Button buttonGenerate = new Button();
        buttonGenerate.setText("Generate");
        buttonGenerate.setMinSize(130, 60);

        Button buttonOpen = new Button();
        buttonOpen.setText("Open");
        buttonOpen.setMinSize(130, 60);

//        vBox.getChildren().add(buttonAdd);
        vBox.getChildren().add(buttonSave);
        vBox.getChildren().add(buttonClear);
        vBox.getChildren().add(buttonGenerate);
        vBox.getChildren().add(buttonOpen);

        //Labels
        Label info = new Label();
        info.setText("New Person");
        Label labelName = new Label();
        labelName.setText("Enter name:");
        Label labelLastName = new Label();
        labelLastName.setText("Enter Lastname:");
        Label labelAge = new Label();
        labelAge.setText("Enter age:");

        //VBox2
        VBox vBox2 = new VBox();
        root.getChildren().add(vBox2);
        vBox2.setSpacing(20);
        vBox2.getChildren().add(info);
        vBox2.getChildren().add(labelName);
        vBox2.getChildren().add(name);
        vBox2.getChildren().add(labelLastName);
        vBox2.getChildren().add(lastName);
        vBox2.getChildren().add(labelAge);
        vBox2.getChildren().add(age);
        vBox2.getChildren().add(buttonAdd);

        //Обработчики
        buttonOpen.setOnAction((e) -> {
            textArea.setWrapText(true);
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            File txt = chooser.showOpenDialog(null);
            try (FileReader reader = new FileReader(txt)) {
                char[] temp = new char[1024];
                int i = 0;
                while ((i = reader.read(temp)) > 0) {
                    textArea.appendText(new String(temp, 0, i));
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        buttonGenerate.setOnAction((e) -> {
            textArea.setText(getStringRandomPersonList());
        });

        buttonClear.setOnAction((event -> {
            textArea.clear();
        }));

        buttonSave.setOnAction((event -> {
            String text = textArea.getText();
            SaverTxt saverTxt = new SaverTxt();
            saverTxt.saveText(text);
        }));

        buttonAdd.setOnAction((event -> {
            String s = textArea.getText();
            String newPerson = name.getText() + " " + lastName.getText() + ", " + age.getText();
            s = s + newPerson  + "\n";
            textArea.setText(s);
        }));


        Scene scene = new Scene(root, 600, 430);

        primaryStage.setTitle("PersonList");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Метод для кнопки "Generate", список имён
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("Иван");
        names.add("Василий");
        names.add("Максим");
        names.add("Евгений");
        names.add("Дмитрий");
        names.add("Олег");
        names.add("Андрей");
        return names;
    }

    //Метод для кнопки "Generate", список фамилий
    public ArrayList<String> getLastNames() {
        ArrayList<String> LastNames = new ArrayList<>();
        LastNames.add("Иванов");
        LastNames.add("Петров");
        LastNames.add("Воробьев");
        LastNames.add("Медведев");
        LastNames.add("Путин");
        LastNames.add("Романов");
        LastNames.add("Сидоров");
        return LastNames;
    }

    //Метод для кнопки "Generate", создаёт список Person'ов на основе перемешанных списков имён и фамилий
    public ArrayList<Person> randomPerson(ArrayList<String> names, ArrayList<String> lastnames) {
        ArrayList<Person> resultPersonList = new ArrayList<>();
        Collections.shuffle(names);
        Collections.shuffle(lastnames);
        for (int i = 0; i < 7; i++) {
            resultPersonList.add(new Person(names.get(i), lastnames.get(i), (int) (Math.random() * 30)));
        }
        return resultPersonList;
    }

    //Метод для кнопки "Generate", создаёт строковое представление списка Person'ов для передачи в TextArea
    public String getStringRandomPersonList() {
        String result = "";
        ArrayList<String> names = getNames();
        ArrayList<String> lastNames = getLastNames();
        ArrayList<Person> arrayList = randomPerson(names, lastNames);
        result = getStringFromArraylist(arrayList);
        return result;
    }

    //Метод для кнопки "Generate", создаёт строковое представление списка
    // Person'ов для передачи в метод getStringRandomPersonList()
    public String getStringFromArraylist(ArrayList<Person> arrayList) {
        String s = "";
        for (Person person :
                arrayList) {
            s = s + person.toString();
        }
        return s;
    }


    public static void main(String[] args) {
        launch(args);
    }


}
