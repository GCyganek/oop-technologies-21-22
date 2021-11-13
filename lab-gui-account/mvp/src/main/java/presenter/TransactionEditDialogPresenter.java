package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import model.Category;
import model.Transaction;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionEditDialogPresenter {

	private Transaction transaction;

	@FXML
	private TextField dateTextField;

	@FXML
	private TextField payeeTextField;

	@FXML
	private TextField categoryTextField;

	@FXML
	private TextField inflowTextField;
	
	private Stage dialogStage;
	
	private boolean approved;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setData(Transaction transaction) {
		this.transaction = transaction;
		updateControls();
	}
	
	public boolean isApproved() {
		return approved;
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) {
		// TODO: implement
		try {
			updateModel();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		approved = true;
		dialogStage.close();
	}
	
	@FXML
	private void handleCancelAction(ActionEvent event) {
		// TODO: implement
		dialogStage.close();
	}
	
	private void updateModel() throws ParseException {
		// TODO: implement
		transaction.setDate(stringToDate());
		transaction.setPayee(payeeTextField.getText());
		var currentCategory = transaction.getCategory();
		transaction.setCategory(new Category(categoryTextField.getText(), currentCategory.getParent()));
		transaction.setInflow(stringToBigDecimal());
	}
	
	private void updateControls() {
		// TODO: implement
		dateTextField.setText(dateToString());
		payeeTextField.setText(transaction.getPayee());
		categoryTextField.setText(transaction.getCategory().getName());
		inflowTextField.setText(transaction.getInflow().toString());
	}

	private String dateToString() {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateStringConverter converter = new LocalDateStringConverter(formatter, formatter);
		return converter.toString(transaction.getDate());
	}

	private LocalDate stringToDate() {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateStringConverter converter = new LocalDateStringConverter(formatter, formatter);
		return converter.fromString(dateTextField.getText());
	}

	private BigDecimal stringToBigDecimal() throws ParseException {
		DecimalFormat decimalFormatter = new DecimalFormat();
		decimalFormatter.setParseBigDecimal(true);
		return (BigDecimal) decimalFormatter.parse(inflowTextField.getText());
	}
}
