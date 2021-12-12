package controller;

import com.jfoenix.controls.JFXTextField;
import controller.Controller.clientController;
import controller.Controller.productController;
import controller.Controller.rentItemController;
import controller.Controller.salonItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Client;
import model.Product;
import model.RentItem;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class ProductController extends productController {
    public JFXTextField txtProductCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPrice;
    public JFXTextField txtQtyOnHand;
    public AnchorPane productContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblProduct;
    public TableColumn colProductCode;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colQtyOnHand;
    public TextField txtSearch;
    public TextField lblProoductId;
    public ImageView btnProduct;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        colDate.setStyle("-fx-alignment:CENTER;");
        colTime.setStyle("-fx-alignment:CENTER;");
        colPrice.setStyle("-fx-alignment:CENTER;");
        colQtyOnHand.setStyle("-fx-alignment:CENTER;");

        colProductCode.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDescription.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colPrice.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colQtyOnHand.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colDate.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");
        colTime.setStyle("-fx-border-color: #95a5a6;-fx-table-cell-border-color:#95a5a6;");


        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setProductsToTable(new productController().getAllProducts());
        btnProduct.setDisable(true);

        setProductId();
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    search(newValue);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setProductId() throws SQLException, ClassNotFoundException {
        lblProoductId.setText(new productController().getProductsIds());
    }

    private void setProductsToTable(ArrayList<Product> products) {
        ObservableList<Product> obList = FXCollections.observableArrayList();
        products.forEach(e -> {
            obList.add(
                    new Product(e.getProductCode(),e.getDescription(),e.getPrice(),e.getQtyOnHand(),e.getDate(),e.getTime()));
        });
        tblProduct.setItems(obList);
    }


    public void selectProductcodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String productCode = txtProductCode.getText();

        Product p1= new productController().getProduct(productCode);
        if (p1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(p1);
        }
    }

    private void setData(Product p1) {
        txtProductCode.setText(p1.getProductCode());
        txtDescription.setText(p1.getDescription());
        txtPrice.setText(String.valueOf(p1.getPrice()));
        txtQtyOnHand.setText(String.valueOf(p1.getQtyOnHand()));
    }


    public void productAddOnAction(ActionEvent actionEvent) {
        try {
            Product p1 = new Product(
                    lblProoductId.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    parseInt(txtQtyOnHand.getText()),
                    lblDate.getText(),
                    lblTime.getText()
            );

            if (new productController().saveProduct(p1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                setProductId();
                clearText();
            }else
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();

            setProductsToTable(new productController().getAllProducts());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void clearText() {
        txtDescription.setText("");
        txtQtyOnHand.setText("");
        txtPrice.setText("");
    }


    public void productUpdateOnAction(ActionEvent actionEvent) {
        try {
            Product p = new Product(
                    txtProductCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    parseInt(txtQtyOnHand.getText()),
                    null,
                    null
            );


            if (new productController().updateProduct(p)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                setProductsToTable(new productController().getAllProducts());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.WARNING, e.getMessage());
        }
    }


    public void productRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new productController().deleteProduct(txtProductCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

        setProductsToTable(new productController().getAllProducts());
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) productContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardAdmin.fxml"))));
        window.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<Product> products = productController.searchProduct(value);
        ObservableList<Product> obList = FXCollections.observableArrayList();
        for (Product product:products) {
            obList.add(new Product(
                    product.getProductCode(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getQtyOnHand(),
                    product.getDate(),
                    product.getTime()
            ));
        }
        tblProduct.getItems().setAll(obList);
    }

    public void DescriptionKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[A-z ]{3,70}$";
            String typeText = txtDescription.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    txtDescription.setStyle("-fx-text-fill:#474787");
                    txtPrice.requestFocus();
                } else {
                    txtDescription.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void PriceKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[1-9][0-9]*([.][0-9]{2})?$";
            String typeText = txtPrice.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches) {
                    txtPrice.setStyle("-fx-text-fill:#474787");
                    txtQtyOnHand.requestFocus();
                } else {
                    txtPrice.setStyle("-fx-text-fill: red");
                }
            }
        }
    }

    public void QtyOnHandKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {

            String regEx = "^[0-9][0-9]*$";
            String typeText = txtQtyOnHand.getText();
            Pattern compile = Pattern.compile(regEx);
            boolean matches = compile.matcher(typeText).matches();


            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (matches){
                    txtQtyOnHand.setStyle("-fx-text-fill:#474787");
                    btnProduct.setDisable(false);
                }else{
                    txtQtyOnHand.setStyle("-fx-text-fill: red");
                    btnProduct.setDisable(true);
                }
            }
        }
    }
}
