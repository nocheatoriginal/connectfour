module devtools.javafx {
  requires org.slf4j;
  requires devtools.apis;
  requires devtools.components;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  opens de.hhn.it.devtools.javafx.controllers to javafx.fxml;
  opens de.hhn.it.devtools.javafx.controllers.template to javafx.fxml;
  opens de.hhn.it.devtools.javafx.connectfour to javafx.fxml;
  exports de.hhn.it.devtools.javafx;
  exports de.hhn.it.devtools.javafx.controllers;
  exports de.hhn.it.devtools.javafx.connectfour;
  exports de.hhn.it.devtools.javafx.connectfour.view;
  opens de.hhn.it.devtools.javafx.connectfour.view to javafx.fxml;
  exports de.hhn.it.devtools.javafx.connectfour.viewmodel;
  opens de.hhn.it.devtools.javafx.connectfour.viewmodel to javafx.fxml;
  exports de.hhn.it.devtools.javafx.connectfour.screens;
  opens de.hhn.it.devtools.javafx.connectfour.screens to javafx.fxml;
}
