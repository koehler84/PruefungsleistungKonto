package gui;

// http://blog.axxg.de/javafx-custom-control-fxml/

import javafx.scene.control.TextField;

import java.util.Objects;

public class NumberTextField extends TextField {

    @Override public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || Objects.equals(text, "")) {
            super.replaceText(start, end, text);
        }
    }

    @Override public void replaceSelection(String text) {
        if (text.matches("[0-9]") || Objects.equals(text, "")) {
            super.replaceSelection(text);
        }
    }

}