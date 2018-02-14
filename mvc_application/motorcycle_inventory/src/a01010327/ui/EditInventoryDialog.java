/**
 * a01010327_assignment2
 * EditInventoryDialog.java
 * Jun 22, 2017
 * 11:13:28 AM
 */
package a01010327.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Inventory;
import a01010327.database.dao.InventoryDao;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;

/**
 * @author amir deris, a01010327
 *
 */
public class EditInventoryDialog extends JDialog {

	private static final long serialVersionUID = -8561395588630787433L;

	private final JPanel contentPanel = new JPanel();
	private static final Logger LOG = LogManager.getLogger();
	private Inventory origianlInventory;

	private JTextField motorcycleIdText;
	private JTextField descriptionText;
	private JTextField partNumberText;
	private JTextField priceText;
	private JTextField quantityText;

	/**
	 * Create the dialog.
	 * 
	 * @param inventory,
	 *            an Inventory to view its details.
	 */
	public EditInventoryDialog(Inventory inventory) {
		this.origianlInventory = inventory;
		setBounds(100, 100, 450, 300);
		setTitle("Edit Inventory");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][83.00][249.00,grow]", "[][][][][][]"));
		{
			JLabel lblMotorcycleId = new JLabel("Motorcycle ID");
			contentPanel.add(lblMotorcycleId, "cell 1 1,alignx left");
		}
		{
			motorcycleIdText = new JTextField();
			motorcycleIdText.setForeground(Color.BLUE);
			motorcycleIdText.setFont(new Font("Tahoma", Font.BOLD, 11));
			motorcycleIdText.setEnabled(false);
			contentPanel.add(motorcycleIdText, "cell 2 1,growx,aligny center");
			motorcycleIdText.setColumns(10);
		}
		{
			JLabel lblDescription = new JLabel("Description");
			contentPanel.add(lblDescription, "cell 1 2,alignx left");
		}
		{
			descriptionText = new JTextField();
			contentPanel.add(descriptionText, "cell 2 2,growx");
			descriptionText.setColumns(10);
		}
		{
			JLabel lblPartNumber = new JLabel("Part Number");
			contentPanel.add(lblPartNumber, "cell 1 3,alignx left");
		}
		{
			partNumberText = new JTextField();
			contentPanel.add(partNumberText, "cell 2 3,growx");
			partNumberText.setColumns(10);
		}
		{
			JLabel lblPrice = new JLabel("Price");
			contentPanel.add(lblPrice, "cell 1 4,alignx left");
		}
		{
			priceText = new JTextField();
			contentPanel.add(priceText, "cell 2 4,growx");
			priceText.setColumns(10);
		}
		{
			JLabel lblQuantity = new JLabel("Quantity");
			contentPanel.add(lblQuantity, "cell 1 5,alignx left");
		}
		{
			quantityText = new JTextField();
			contentPanel.add(quantityText, "cell 2 5,growx");
			quantityText.setColumns(10);
		}
		setPanelContent();
		setValuesOfFields();
	}

	// helper method
	private void setPanelContent() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// saving the edits
					Inventory modifiedInventory = new Inventory();
					modifiedInventory.setMotorcycleId(motorcycleIdText.getText());
					modifiedInventory.setDescription(descriptionText.getText());
					modifiedInventory.setPartNumber(partNumberText.getText());
					try {
						modifiedInventory.setPrice(Double.parseDouble(priceText.getText().replaceAll(",", "")));
						modifiedInventory.setQuantity(Integer.parseInt(quantityText.getText()));
					} catch (Exception ex0) {
						LOG.error(ex0.getMessage());
						JOptionPane.showMessageDialog(EditInventoryDialog.this, "Invalid entered value.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					try {
						InventoryDao.getInstance().update(origianlInventory, modifiedInventory);
					} catch (SQLException ex1) {
						LOG.error(ex1.getMessage());
						JOptionPane.showMessageDialog(EditInventoryDialog.this,
								"There was an error updating the record.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					dispose();
				}
			});
			buttonPane.add(okButton);

		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			getRootPane().setDefaultButton(cancelButton);
			buttonPane.add(cancelButton);

		}

	}

	// helper method to set the values of the text fields.
	private void setValuesOfFields() {
		if (origianlInventory == null) {
			return;
		}
		motorcycleIdText.setText(origianlInventory.getMotorcycleId());
		descriptionText.setText(origianlInventory.getDescription());
		partNumberText.setText(origianlInventory.getPartNumber());
		priceText.setText(String.format("%,.2f", origianlInventory.getPrice()));
		quantityText.setText(String.valueOf(origianlInventory.getQuantity()));
	}
}
