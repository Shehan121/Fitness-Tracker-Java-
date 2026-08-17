# Fitness Tracker — JavaFX

A desktop fitness companion built with **JavaFX** and **FXML**: a BMI calculator
plus a set of daily health reminders, across two scenes.

Coursework project, TH Aschaffenburg.

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-23-5382A1)

---

## Features

| Screen | Contents |
|---|---|
| **Main** (`hello-view.fxml`) | Four health-tip buttons — step count, water intake, sleep, nutrition |
| **BMI Calculator** (`new.fxml`) | Mass and height inputs, calculated BMI, and a link back to the main page |

---

## How it works

### BMI calculation

```java
private void calculateResult() {
    try {
        double number1 = Double.parseDouble(masstext.getText());
        double number2 = Double.parseDouble(heighttext.getText());
        double result = number1 / (number2 * number2);
        resultlabel.setText(result + "kg/m^2");
    } catch (NumberFormatException e) {
        resultlabel.setText("Invalid input! Please enter numbers.");
    }
}
```

Standard BMI: mass in kilograms divided by height in metres squared. Non-numeric
input is caught rather than allowed to crash the handler.

### Health tips

Four handlers set a label each — deliberately static advice rather than
calculated values:

| Handler | Output |
|---|---|
| `onclick()` | "your Stepcount for today is 1000" |
| `waterclick()` | "You should drink 2.5l of water today" |
| `sleepclick()` | "you should get at least 8 hours of sleep" |
| `eatclick()` | "Eat your vegetables" |

### Scene navigation

Both scenes share one controller. Switching reloads an FXML layout into the
existing `Stage`:

```java
Parent root = FXMLLoader.load(getClass().getResource("new.fxml"));
stage = (Stage)((Node) event.getSource()).getScene().getWindow();
stage.setScene(new Scene(root));
```

The cast chain walks from the clicked control up to its window — the usual way
to reach the `Stage` from a controller that was never given one.

### Module configuration

```java
module com.example.projjjjj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.projjjjj to javafx.fxml;
    exports com.example.projjjjj;
}
```

`opens ... to javafx.fxml` is what allows FXML to inject the `@FXML` fields
reflectively. Without it those fields arrive as `null`.

---

## Building and running

**This project does not build from a clean clone** — see *Known issues*. No
Maven or Gradle configuration is committed.

With a `pom.xml` using the JavaFX plugin:

```bash
mvn javafx:run
```

Or compile against a local JavaFX SDK:

```bash
javac --module-path /path/to/javafx-sdk/lib \
      --add-modules javafx.controls,javafx.fxml \
      -d out $(find . -name "*.java")

java  --module-path /path/to/javafx-sdk/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp out com.example.projjjjj.HelloApplication
```

Requires Java 17+ and JavaFX 23.

---

## Known issues

1. **No build file.** Neither `pom.xml` nor `build.gradle` is committed, so the
   project cannot be compiled without reconstructing the build. The highest-value
   fix.

2. **Division by zero is not handled.** A height of `0` passes
   `Double.parseDouble` fine and yields `Infinity`, which is displayed as
   `Infinitykg/m^2`. `NumberFormatException` catches bad text, not bad numbers —
   the inputs need a range check.

3. **The result is not rounded.** `result + "kg/m^2"` prints the raw double, so a
   70 kg / 1.75 m person sees `22.857142857142858kg/m^2`. `String.format("%.1f",
   result)` would fix it. There is also no space before the unit.

4. **The package is still the IDE default** — `com.example.projjjjj`, which is
   also the directory name. Renaming touches `module-info.java` and the
   `fx:controller` attributes in both FXML files.

5. **Field and handler names are inconsistent** — `masstext`, `walktext`,
   `sleeplabel`, `onclick`. Java convention is camelCase (`massText`,
   `onStepsClicked`), and `onclick` says nothing about what it does.

6. **`javax.swing.*` is imported** into a JavaFX controller. Unused, but mixing
   the two UI toolkits is worth avoiding.

7. **The health tips are hardcoded.** Step count is a fixed string, not a real
   measurement, and water and sleep advice ignores the user's mass and height —
   which the BMI screen already collects. Deriving the water target from body
   mass would connect the two halves of the app.

## Repository history note

This repository previously contained a byte-identical copy of the
**[Dodge the Creeps](https://github.com/Shehan121/Dodge-the-creeps-Godot-)**
Godot project — 44 files of an unrelated game, and not a single `.java` file
despite the repository name. The actual JavaFX fitness tracker had never been
pushed.

The Godot content has been removed (it remains intact in its own repository) and
replaced with the real project.

## Project structure

```
projjjjj/src/main/
├── java/
│   ├── module-info.java
│   └── com/example/projjjjj/
│       ├── HelloApplication.java   entry point
│       └── HelloController.java    BMI calculation + health tips
└── resources/com/example/projjjjj/
    ├── hello-view.fxml             main screen
    └── new.fxml                    BMI calculator
```

## Author

**Shehan Nimsara** — B.Sc. Software Design (International), TH Aschaffenburg
